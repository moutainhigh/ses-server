package com.redescooter.ses.web.ros.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.customer.*;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.CheckOpenAccountEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountCountStatusEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.foundation.vo.user.DeleteUserEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeDetailResult;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeEnter;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.EditUserProfileEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.StatisticalUtil;
import com.redescooter.ses.tool.utils.VerificationCodeImgUtil;
import com.redescooter.ses.tool.utils.accountType.AccountTypeUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.*;
import com.redescooter.ses.web.ros.vo.customer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:CustomerImpl
 * @description: CustomerImpl
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
@Slf4j
@Service
public class CustomerRosServiceImpl implements CustomerRosService {

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;
    @Autowired
    private CustomerServiceMapper customerServiceMapper;
    @Autowired
    private OpeSysUserProfileMapper sysUserProfileMapper;
    @Autowired
    private JedisCluster jedisCluster;
    @Reference
    private IdAppService idAppService;
    @Reference
    private CityBaseService cityBaseService;
    @Reference
    private AccountBaseService accountBaseService;
    @Reference
    private TenantBaseService tenantBaseService;
    @Reference
    private MailMultiTaskService mailMultiTaskService;
    @Reference
    private UserBaseService userBaseService;
    @Reference
    private UserProfileService userProfileService;

    @Autowired
    private OpeSysUserService opeSysUserService;


    /**
     * 邮箱验证
     *
     * @param mail
     * @return
     */
    @Override
    public BooleanResult checkMail(String mail) {

        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, mail);
        wrapper.eq(OpeCustomer.COL_DR, 0);

        Boolean mailBoolean = opeCustomerMapper.selectCount(wrapper) == 1 ? Boolean.TRUE : Boolean.FALSE;

        return new BooleanResult(mailBoolean);
    }

    /**
     * 邮箱验证
     *
     * @param enter
     * @return
     */
    @Override
    public IntResult checkMailCount(StringEnter enter) {
        //邮箱去空格
        enter.setSt(SesStringUtils.stringTrim(enter.getSt()));

        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, enter.getSt());
        wrapper.eq(OpeCustomer.COL_DR, 0);
        wrapper.ne(OpeCustomer.COL_STATUS, CustomerStatusEnum.TRASH_CUSTOMER.getValue());
        Integer count = opeCustomerMapper.selectCount(wrapper);
        return new IntResult(count);
    }

    /**
     * 创建客户
     *
     * @param createCustomerEnter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(CreateCustomerEnter createCustomerEnter) {
        //employeeListEnter参数值去空格
        CreateCustomerEnter enter = SesStringUtils.objStringTrim(createCustomerEnter);
        //客户字段校验
        checkSaveCustomerFiledSingle(enter);

        //已存在客户 不可重复添加
        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        OpeCustomer saveVo = new OpeCustomer();
        BeanUtils.copyProperties(enter, saveVo);
        saveVo.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveVo.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        saveVo.setCustomerSource(CustomerSourceEnum.SYSTEM.getValue());
        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            saveVo.setContactFullName(new StringBuffer().append(saveVo.getContactFirstName()).append(" ").append(saveVo.getContactLastName()).toString());
//            // 企业个人信息 也要赋值给客户的名字
//            saveVo.setCustomerFirstName(saveVo.getContactFirstName());
//            saveVo.setCustomerLastName(saveVo.getContactLastName());
//            saveVo.setCustomerFullName(saveVo.getContactFullName());
        } else {
            saveVo.setCustomerFullName(new StringBuffer().append(saveVo.getCustomerFirstName()).append(" ").append(saveVo.getCustomerLastName()).toString());
        }

        saveVo.setMemo(enter.getRemark());
        saveVo.setAssignationScooterQty(0);
        saveVo.setAccountFlag(CustomerAccountFlagEnum.NORMAL.getValue());
        saveVo.setCreatedBy(enter.getUserId());
        saveVo.setCreatedTime(new Date());
        saveVo.setUpdatedBy(enter.getUserId());
        saveVo.setUpdatedTime(new Date());
        saveVo.setDef2(enter.getCityName());
        saveVo.setDef3(enter.getDistrustName());
        opeCustomerMapper.insert(saveVo);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 编辑更新客户
     *
     * @param editCustomerEnter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult edit(EditCustomerEnter editCustomerEnter) {
        //employeeListEnter参数值去空格
        EditCustomerEnter enter = SesStringUtils.objStringTrim(editCustomerEnter);
        //客户字段长度校验
        checkEditCustomerFiledSingle(enter);
        //已存在客户 不可重复添加
        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        queryWrapper.ne(OpeCustomer.COL_ID,enter.getId());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        Long tenantId = customer.getTenantId();
        if (customer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getCode(), ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getMessage());
        }
        if (customer.getStatus().equals(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            //客户验证
            checkCustomer(enter);
            // 客户行业 类型不可修改
            if (!StringUtils.equals(enter.getCustomerType(), customer.getCustomerType())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TYPE_IS_NOT_EDIT.getCode(), ExceptionCodeEnums.CUSTOMER_TYPE_IS_NOT_EDIT.getMessage());
            }
            if (!StringUtils.equals(enter.getIndustryType(), customer.getIndustryType())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_INDUSTRYTYPE_IS_NOT_EDIT.getCode(), ExceptionCodeEnums.CUSTOMER_INDUSTRYTYPE_IS_NOT_EDIT.getMessage());
            }
            EditUserProfileEnter editUserProfileEnter = new EditUserProfileEnter();
            BeanUtils.copyProperties(enter, editUserProfileEnter);
            editUserProfileEnter.setInputTenantId(customer.getTenantId());
            editUserProfileEnter.setEmail(customer.getEmail());
            editUserProfileEnter.setFirstName(enter.getCustomerFirstName());
            editUserProfileEnter.setLastName(enter.getCustomerLastName());

            // 已创建的是web 账户
            if (customer.getTenantId() != 0) {
                // saas 更新个人信息
                userProfileService.editUserProfile2B(editUserProfileEnter);
            }
            if (customer.getTenantId() == 0 && StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), customer.getCustomerType())) {
                // TOc 更新个人信息
                userProfileService.editUserProfile2C(editUserProfileEnter);
            }
        }
        if (!enter.getEmail().equals(customer.getEmail())) {
            //潜在客户允许编辑邮箱，但不允许重复
            if (checkMailCount(new StringEnter(enter.getEmail())).getValue() > 0) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
        }
        OpeCustomer update = new OpeCustomer();
        BeanUtils.copyProperties(enter, update);
        update.setDef2(enter.getCityName());
        update.setDef3(enter.getDistrustName());
        update.setTenantId(tenantId);
        update.setMemo(enter.getRemark());
        update.setUpdatedBy(enter.getUserId());
        update.setUpdatedTime(new Date());
        update.setCustomerFullName(enter.getCustomerFirstName() + " " + enter.getCustomerLastName());
        update.setContactFullName(enter.getContactFirstName() + " " + enter.getContactLastName());
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }


    private void checkEditCustomerFiledSingle(EditCustomerEnter enter) {
        //字段校验
        if (StringUtils.isNotEmpty(enter.getContactFirstName())) {
            if (enter.getContactFirstName().length() < 2 || enter.getContactFirstName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getContactLastName())) {
            if (enter.getContactLastName().length() < 2 || enter.getContactLastName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCompanyName())) {
            if (enter.getCompanyName().length() < 2 || enter.getCompanyName().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        //名称校验
        if (StringUtils.isNotEmpty(enter.getCustomerFirstName())) {
            if (enter.getCustomerFirstName().length() < 2 || enter.getCustomerFirstName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCustomerLastName())) {
            if (enter.getCustomerLastName().length() < 2 || enter.getCustomerLastName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getRemark())) {
            if (enter.getRemark().length() < 2 || enter.getRemark().length() > 200) {
                throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getInvoiceNum())) {
            //发票号
            if (enter.getInvoiceNum().length() < 2 || enter.getInvoiceNum().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getBusinessLicenseNum())) {
            //营业执照编号 校验
            if (enter.getBusinessLicenseNum().length() < 2 || enter.getBusinessLicenseNum().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (enter.getScooterQuantity() != null) {
            if (String.valueOf(enter.getScooterQuantity()).length() < 1 || String.valueOf(enter.getScooterQuantity()).length() > 6) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if(Strings.isNotBlank(enter.getTelephone())){
            if(enter.getTelephone().length() < 8 || enter.getTelephone().length() > 20){
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
        }
    }


    /**
     * 客户详情查询
     *
     * @param enter
     * @return
     */
    @Override
    public DetailsCustomerResult details(IdEnter enter) {

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        DetailsCustomerResult result = new DetailsCustomerResult();
        BeanUtils.copyProperties(opeCustomer, result);
        result.setCityName(opeCustomer.getDef2());
        result.setDistrustName(opeCustomer.getDef3());
        result.setRemark(opeCustomer.getMemo());
        if (opeCustomer.getCustomerSource().equals(CustomerSourceEnum.WEBSITE.getValue())) {
            if (opeCustomer.getId().equals(opeCustomer.getUpdatedBy())) {
                result.setUpdatedName(opeCustomer.getCustomerFullName());
            } else {
                OpeSysUserProfile profile = getOpeSysUserProfile(result.getUpdatedBy());
                result.setUpdatedName(profile == null ? null : profile.getFullName());
            }
            result.setCreatedName(opeCustomer.getCustomerFullName());
            result.setAccountFlag(Integer.valueOf(opeCustomer.getAccountFlag()));
        } else {
            OpeSysUserProfile create = getOpeSysUserProfile(result.getCreatedBy());
            result.setCreatedName(create == null ? null : create.getFullName());
            result.setAccountFlag(Integer.valueOf(opeCustomer.getAccountFlag()));

            OpeSysUserProfile update = getOpeSysUserProfile(result.getUpdatedBy());
            result.setUpdatedName(update == null ? null : update.getFullName());
        }

        result.setRequestId(enter.getRequestId());
//        if (opeCustomer.getCity() != null) {
//            result.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getCity()).build()).getName());
//            if (opeCustomer.getDistrust() != null) {
//                result.setDistrustName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getDistrust()).build()).getName());
//            }
//        }

        Integer accountType = AccountTypeUtils.getAccountType(opeCustomer.getCustomerType(), opeCustomer.getIndustryType());
        BooleanResult booleanResult = accountBaseService.checkOpenAccount(CheckOpenAccountEnter.builder().accountType(accountType).email(opeCustomer.getEmail()).build());
        //账号已激活 不在发送邮件
        if (booleanResult.isSuccess()) {
            result.setTtl(null);
        } else {
            //验证是否可以再次发生邮件
            Boolean exists = jedisCluster.exists(new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString());
            if (exists) {
                Long ttl = jedisCluster.ttl(new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString());
                result.setTtl(ttl);
            } else {
                result.setTtl(new Long(0));
            }
        }

        // 信息完善度 计算
        result.setInformationPerfectionNum(checkCustomerInformation(opeCustomer));
        return result;
    }


    private OpeSysUserProfile getOpeSysUserProfile(Long userId) {
        QueryWrapper<OpeSysUserProfile> updated = new QueryWrapper<>();
        updated.eq(OpeSysUserProfile.COL_SYS_USER_ID, userId);
        updated.eq(OpeSysUserProfile.COL_DR, 0);
        updated.last("limit 1");
        OpeSysUserProfile profile = sysUserProfileMapper.selectOne(updated);
        return profile;
    }


    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByCustomerStatus = customerServiceMapper.countStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByCustomerStatus) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (CustomerStatusEnum status : CustomerStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }


    /**
     * @param page
     * @desc: 客户列表分页
     * @param: enter
     * @return: CustomerListByPageResult
     * @auther: alex
     * @date: 2019/12/18 11:51
     * @Version: Ros 1.0
     */
    @Override
    public PageResult<DetailsCustomerResult> list(ListCustomerEnter page) {
        if (page.getKeyword()!=null && page.getKeyword().length()>50){
           return PageResult.createZeroRowResult(page);
        }
        int totalRows = customerServiceMapper.customerListCount(page);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<DetailsCustomerResult> detailsCustomerList = customerServiceMapper.customerList(page);

//        detailsCustomerList.forEach(customer -> {
//            if (customer.getCity() != null) {
//                customer.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(customer.getCity()).build()).getName());
//            }
//
//            if (customer.getDistrust() != null) {
//                customer.setDistrustName(cityBaseService
//                        .queryCityDeatliById(IdEnter.builder().id(customer.getDistrust()).build()).getName());
//            }
//        });

        return PageResult.create(page, totalRows, detailsCustomerList);
    }

    /**
     * 客户逻辑
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(IdEnter enter) {
        //验证客户是否开通SaaS账户等信息
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (!opeCustomer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }

        opeCustomerMapper.deleteById(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户垃圾
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult trash(TrashCustomerEnter enter) {

        //验证客户是否开通SaaS账户等信息
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if(customer == null){
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        // 校验该客户是否已激活
        if(checkActivat(customer.getEmail())){
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (customer.getAccountFlag().equals(CustomerAccountFlagEnum.ACTIVATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        if (customer.getAccountFlag().equals(CustomerAccountFlagEnum.INACTIVATED.getValue())) {
            accountBaseService.deleteUser(DeleteUserEnter.builder().email(customer.getEmail()).build());
        }
        OpeCustomer update = new OpeCustomer();
        update.setId(enter.getId());
        update.setStatus(CustomerStatusEnum.TRASH_CUSTOMER.getValue());
        update.setDescription(enter.getReason());
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Author Aleks
     * @Description  是否激活的校验，跟Jerry确认判断这个邮箱对应的账号是否登录过，登录过则为已激活
     * @Date  2020/8/25 14:16
     * @Param [email]
     * @return
     **/
    public boolean checkActivat(String email){
        boolean flag = false;
        QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
        qw.eq(OpeSysUser.COL_LOGIN_NAME,email);
        qw.isNotNull(OpeSysUser.COL_LAST_LOGIN_TIME);
        qw.last(" limit 1");
        OpeSysUser sysUser = opeSysUserService.getOne(qw);
        if(sysUser != null){
            flag = true;
        }
        return flag;
    }


    /**
     * 客户转正
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult change(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        EditCustomerEnter checkCustomer = new EditCustomerEnter();
        BeanUtils.copyProperties(customer, checkCustomer);
        checkCustomer.setCityName(customer.getDef2());
        checkCustomer(checkCustomer);

        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!customer.getStatus().equals(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        customer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue());
        customer.setUpdatedBy(enter.getUserId());
        customer.setUpdatedTime(new Date());
        opeCustomerMapper.updateById(customer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 客户开通账户
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 17:39
     * @Version: ROS 1.0
     */
    @Transactional
    @Override
    public GeneralResult openAccount(OpenAccountEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // todo 存在漏洞 先创建 Driver 账户 ---》 web或者TOC 账户是能够通过校验的
        BooleanResult checkMail = checkMail(opeCustomer.getEmail());
        BaseUserResult userResult = null;
        if (checkMail.isSuccess()) {
            BaseCustomerResult baseCustomer = new BaseCustomerResult();
            BeanUtils.copyProperties(opeCustomer, baseCustomer);

            DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
            BeanUtils.copyProperties(enter, parmEnter);
            parmEnter.setStartDateTime(DateUtil.stringToDate(enter.getStartActivationTime()));
            parmEnter.setEndDateTime(DateUtil.stringToDate(enter.getEndActivationTime()));
            parmEnter.setT(baseCustomer);

            userResult = accountBaseService.open(parmEnter);

            opeCustomer.setTenantId(userResult.getTenantId());
            opeCustomer.setAccountFlag(CustomerAccountFlagEnum.INACTIVATED.getValue());
            opeCustomer.setUpdatedBy(enter.getUserId());
            opeCustomer.setUpdatedTime(new Date());
            opeCustomerMapper.updateById(opeCustomer);
        } else {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        //设置邮箱发送有效时间
        String key = new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 账户列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AccountListResult> accountList(AccountListEnter enter) {
      if (enter.getKeyword()!=null && enter.getKeyword().length()>50){
        return PageResult.createZeroRowResult(enter);
      }
        int countCustomer = customerServiceMapper.customerAccountCount(enter);
        if (countCustomer == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        // 查询内容
        List<AccountListResult> accountList = customerServiceMapper.queryAccountRecord(enter);
        List<String> emailList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accountList)) {
            emailList = accountList.stream().map(AccountListResult::getEmail).collect(Collectors.toList());
        }

        QueryAccountListEnter queryAccountListEnter = new QueryAccountListEnter();
        BeanUtils.copyProperties(enter, queryAccountListEnter);
        queryAccountListEnter.setEmailList(emailList);
        Integer customerAccountCount = accountBaseService.customerAccountCount(queryAccountListEnter);
        if (customerAccountCount == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<QueryAccountResult> queryAccountListResult = accountBaseService.customerAccountList(queryAccountListEnter);

        List<AccountListResult> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(queryAccountListResult)) {
            queryAccountListResult.forEach(account -> {
                for (AccountListResult item : accountList) {
                    if (StringUtils.equals(account.getEmail(), item.getEmail())) {
                        item.setStatus(account.getStatus());
                        item.setActivationTime(account.getActivationTime());
                        item.setExpirationTime(account.getExpirationTime());
                        resultList.add(item);
                        break;
                    }
                }
            });
        }
        return PageResult.create(enter, customerAccountCount, resultList);
    }

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> accountCountStatus(GeneralEnter enter) {
        // 查询内容
      List<AccountListResult> accountList = customerServiceMapper.queryAccountRecordEamil(enter);
      List<String> emailList = new ArrayList<>();
      if (!CollectionUtils.isEmpty(accountList)) {
          emailList = accountList.stream().map(AccountListResult::getEmail).collect(Collectors.toList());
      }
      QueryAccountCountStatusEnter queryAccountCountStatusEnter = new QueryAccountCountStatusEnter();
      queryAccountCountStatusEnter.setEmailList(emailList);
      return accountBaseService.customerAccountCountByStatus(queryAccountCountStatusEnter);
    }

    /**
     * 账户节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccountNodeResult> accountNode(IdEnter enter) {
        List<AccountNodeResult> resultList = new ArrayList<>();
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        List<QueryAccountNodeDetailResult> queryAccountNodeDetailResultList = userBaseService.accountNodeDetail(QueryAccountNodeEnter.builder().email(opeCustomer.getEmail()).build());
        if (!CollectionUtils.isEmpty(queryAccountNodeDetailResultList)) {
            Set<Long> sysUseIds = new HashSet<>();
            queryAccountNodeDetailResultList.forEach(item -> {
                sysUseIds.add(item.getCreateBy());
            });
            QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
            opeSysUserProfileQueryWrapper.in(OpeSysUserProfile.COL_SYS_USER_ID, new ArrayList<>(sysUseIds));
            opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
            List<OpeSysUserProfile> sysUserProfileList = sysUserProfileMapper.selectList(opeSysUserProfileQueryWrapper);
            queryAccountNodeDetailResultList.forEach(node -> {
                sysUserProfileList.forEach(sysUser -> {
                    if (node.getCreateBy().equals(sysUser.getSysUserId())) {
                        AccountNodeResult result = AccountNodeResult.builder()
                                .id(node.getId())
                                .event(node.getEvent())
                                .eventTime(DateUtil.format(node.getEventTime(), DateConstant.DEFAULT_DATETIME_FORMAT))
                                .createdBy(node.getCreateBy())
                                .createdFirstName(sysUser.getFirstName())
                                .createdLastName(sysUser.getLastName())
                                .build();
                        resultList.add(result);
                    }
                });
            });
        }
        return resultList;
    }

    /**
     * 账户详情
     *
     * @param enter
     * @return
     */
    @Override
    public AccountDeatilResult accountDeatil(IdEnter enter) {
        OpeCustomer customerInfo = opeCustomerMapper.selectById(enter.getId());
        if (customerInfo == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        AccountDeatilResult accountReslut = AccountDeatilResult.builder()
                .id(customerInfo.getId())
                .customerType(customerInfo.getCustomerType())
                .customerFirstName(StringUtils.isNotEmpty(customerInfo.getCustomerFirstName()) ? customerInfo.getCustomerFirstName() : null)
                .customerLastName(StringUtils.isNotEmpty(customerInfo.getCustomerLastName()) ? customerInfo.getCustomerLastName() : null)
                .customerFullName(StringUtils.isNotEmpty(customerInfo.getCustomerFullName()) ? customerInfo.getCustomerFullName() : null)
                .companyName(StringUtils.isNotEmpty(customerInfo.getCompanyName()) ? customerInfo.getCompanyName() : null)
                .contactFirstName(StringUtils.isNotEmpty(customerInfo.getContactFirstName()) ? customerInfo.getContactFirstName() : null)
                .contactLastName(StringUtils.isNotEmpty(customerInfo.getContactLastName()) ? customerInfo.getContactLastName() : null)
                .contactFullName(StringUtils.isNotEmpty(customerInfo.getContactFullName()) ? customerInfo.getContactFullName() : null)
                .industryType(customerInfo.getIndustryType())
                .email(customerInfo.getEmail())
                .build();

        // 获取账户信息
        QueryAccountResult queryAccountResult = accountBaseService.customerAccountDeatil(customerInfo.getEmail());
        if (queryAccountResult != null && StringUtils.equals(queryAccountResult.getEmail(), customerInfo.getEmail())) {
            accountReslut.setStatus(queryAccountResult.getStatus());
            accountReslut.setActivationTime(queryAccountResult.getActivationTime());
            accountReslut.setExpireTime(queryAccountResult.getExpirationTime());
        }

//            QueryTenantResult tenantResult = tenantBaseService.queryTenantById(idEnter);
        return accountReslut;
    }

    /**
     * 账户冻结
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult freezeAccount(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        BaseCustomerResult baseCustomer = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomer);

        DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
        BeanUtils.copyProperties(enter, parmEnter);
        parmEnter.setT(baseCustomer);
        accountBaseService.freeze(parmEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 解冻账户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult unFreezeAccount(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        BaseCustomerResult baseCustomer = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomer);

        DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
        BeanUtils.copyProperties(enter, parmEnter);
        parmEnter.setT(baseCustomer);
        accountBaseService.unFreezeAccount(parmEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 账户续期
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult renewAccont(RenewAccountEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        BaseCustomerResult baseCustomer = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomer);

        DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
        BeanUtils.copyProperties(enter, parmEnter);
        parmEnter.setStartDateTime(DateUtil.stringToDate(enter.getStartRenewAccountTime()));
        parmEnter.setEndDateTime(DateUtil.stringToDate(enter.getEndRenewAccountTime()));
        parmEnter.setT(baseCustomer);
        accountBaseService.renewAccont(parmEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 验证码 返回base64 加密 格式
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public VerificationCodeResult verificationCode(GeneralEnter enter) {
        // 定义 图片大小
        VerificationCodeImgUtil vCode = new VerificationCodeImgUtil(103, 32, 5, 16);
        // 调用写操作
        VerificationCodeImgUtil.write();
        //获取code码
        String code = VerificationCodeImgUtil.code;
        // redis 存储
        jedisCluster.set(enter.getRequestId(), code);
        // 设置超时时间
        jedisCluster.expire(enter.getRequestId(), new Long(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
        VerificationCodeResult result = VerificationCodeResult.builder().base64Img(VerificationCodeImgUtil.base64String).build();
        result.setRequestId(enter.getRequestId());
        log.info("获取code码为："+ code);
        return result;
    }

    /**
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult customerSetPassword(SetPasswordEnter enter) {

        //密码去空格
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getConfirmPassword())) {
            enter.setConfirmPassword(SesStringUtils.stringTrim(enter.getConfirmPassword()));
        }

        // 数据校验
        String code = jedisCluster.get(enter.getRequestId());
        if (!StringUtils.equals(code, enter.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        if (!StringUtils.equals(enter.getConfirmPassword(), enter.getNewPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        BaseCustomerResult baseCustomerResult = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomerResult);
        enter.setT(baseCustomerResult);
        return accountBaseService.setPassword(enter);
    }

    /**
     * 邮件再次发生
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public BooleanResult sendEmailAgian(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());

        if (customer.getAccountFlag().equals(CustomerAccountFlagEnum.ACTIVATION)) {
            return new BooleanResult(true);
        }

        List<BaseUserResult> userList = userBaseService.queryEmailInfo(new StringEnter(customer.getEmail()));

        if (userList == null || userList.size() == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //获取账户类型
        Integer accountType = AccountTypeUtils.getAccountType(customer.getCustomerType(), customer.getIndustryType());
        BooleanResult booleanResult = accountBaseService.checkOpenAccount(CheckOpenAccountEnter.builder().accountType(accountType).email(customer.getEmail()).build());
        if (booleanResult.isSuccess()) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_ALRADY_ACTIVATION.getCode(), ExceptionCodeEnums.ACCOUNT_IS_ALRADY_ACTIVATION.getMessage());
        }

        //验证是否可以再次发生邮件
        if (jedisCluster.exists(new StringBuffer().append("send::").append(customer.getEmail()).toString())) {
            return new BooleanResult(true);
        }
        long userId = 0;
        for (BaseUserResult userResult : userList) {
            if (userResult.getUserType().equals(accountType)) {
                userId = userResult.getId();
                break;
            }
        }

        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        BeanUtils.copyProperties(enter, baseMailTaskEnter);

        if (StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), customer.getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
            baseMailTaskEnter.setName(customer.getCustomerFullName());
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), customer.getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
            baseMailTaskEnter.setName(customer.getContactFullName());
        }
        baseMailTaskEnter.setMailAppId(AccountTypeUtils.getAppId(accountType));
        baseMailTaskEnter.setMailSystemId(AccountTypeUtils.getSystemId(accountType));
        //此处是为了获取用户缓存
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setToMail(customer.getEmail());
        baseMailTaskEnter.setToUserId(userId);

        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);

        //设置邮箱发送有效时间
        String key = new StringBuffer().append("send::").append(customer.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());

        return new BooleanResult(true);
    }

    /**
     * 校验客户信息
     *
     * @param enter
     */
    public void checkCustomer(EditCustomerEnter enter) {
        if (enter.getId() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCityName() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CITY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CITY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCustomerType().equals(CustomerTypeEnum.PERSONAL.getValue())) {

            if (StringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getCustomerType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getIndustryType())) {
            throw new SesWebRosException(ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getAddress())) {
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isAllBlank(String.valueOf(enter.getLongitude()), String.valueOf(enter.getLatitude()))) {
            throw new SesWebRosException(ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getTelephone())) {
            throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getCertificateType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCertificateType().equals(CustomerCertificateTypeEnum.ID_CARD.getValue())) {
            if (StringUtils.isAllBlank(enter.getCertificatePositiveAnnex(), enter.getCertificateNegativeAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCertificatePositiveAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getInvoiceNum())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getInvoiceAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getContractAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getMessage());
        }
    }

    private Integer checkCustomerInformation(OpeCustomer customer) {
        // 个人客户为 15个 基本字段(不包括经纬度、身份证件图片)
        //企业客户为 16个基本字段（不包括经纬度、身份证图片）

        //修改
        //个人客户为 17/16(身份证/其他证件) 个基本字段
        //企业客户为 19/28(身份证/其他证件) 个基本字段

        //公共信息 13个 个人有的信息 两个 customerFirstName、customerLastName
        //企业有的信息 5个 companyName、constantFirstName,constantLastName,bussinessnex(2)

        int result = 11;
        int count = 0;

        //一下信息时 个人、企都具有的信息
        if (StringUtils.isNotEmpty(customer.getIndustryType()) && StringUtils.isNotEmpty(customer.getCustomerType())) {
            count += 2;
        }
        if (StringUtils.isNotEmpty(customer.getEmail())) {
            count++;
        }
        //def2=城市
        if (StringUtils.isNotEmpty(customer.getDef2())) {
            count++;
        }
        if (StringUtils.isNotEmpty(customer.getAddress())) {
            count++;
        }
//        if (StringUtils.isNotEmpty(customer.getCountryCode())) {
//            count++;
//        }
        if (StringUtils.isNotEmpty(customer.getTelephone())) {
            count++;
        }
        //比较证件类型 及证件图片
        if (StringUtils.isNotEmpty(customer.getCertificateType())) {
            count++;
        }
        if (StringUtils.equals(customer.getCertificateType(), CustomerCertificateTypeEnum.ID_CARD.getValue())) {
            if (StringUtils.isNotEmpty(customer.getCertificateNegativeAnnex())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getCertificatePositiveAnnex())) {
                count++;
            }
            result += 2;
        }
        if (StringUtils.equals(customer.getCertificateType(), CustomerCertificateTypeEnum.DRIVER_LICENSE.getValue()) ||
                StringUtils.equals(customer.getCertificateType(), CustomerCertificateTypeEnum.PASSPORT.getValue())) {
            if (StringUtils.isNotEmpty(customer.getCertificatePositiveAnnex())) {
                count++;
            }
            result++;
        }

        if (StringUtils.isNotEmpty(customer.getInvoiceNum())) {
            count++;
        }
        if (StringUtils.isNotEmpty(customer.getInvoiceAnnex())) {
            count++;
        }
        if (StringUtils.isNotEmpty(customer.getContractAnnex())) {
            count++;
        }
        if (customer.getScooterQuantity() != null && customer.getScooterQuantity() != 0) {
            count++;
        }

        //企业独有信息
        if (StringUtils.equals(customer.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            result += 5;
            if (StringUtils.isNotEmpty(customer.getCompanyName())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getContactFirstName())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getContactLastName())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getBusinessLicenseNum())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getBusinessLicenseAnnex())) {
                count++;
            }
        }

        //个人信息
        if (StringUtils.equals(customer.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            result += 2;
            if (StringUtils.isNotEmpty(customer.getCustomerFirstName())) {
                count++;
            }
            if (StringUtils.isNotEmpty(customer.getCustomerLastName())) {
                count++;
            }
        }
        return Integer.valueOf(StatisticalUtil.percentageUtil(count, result, 0));
    }

    /**
     * 客户字段字段校验
     *
     * @param enter
     */
    private void checkSaveCustomerFiledSingle(CreateCustomerEnter enter) {
        if (StringUtils.isNotEmpty(enter.getContactFirstName())) {
            if (enter.getContactFirstName().length() < 2 || enter.getContactFirstName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getContactLastName())) {
            if (enter.getContactLastName().length() < 2 || enter.getContactLastName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCompanyName())) {
            if (enter.getCompanyName().length() < 2 || enter.getCompanyName().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        //名称校验
        if (StringUtils.isNotEmpty(enter.getCustomerFirstName())) {
            if (enter.getCustomerFirstName().length() < 2 || enter.getCustomerFirstName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCustomerLastName())) {
            if (enter.getCustomerLastName().length() < 2 || enter.getCustomerLastName().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getRemark())) {
            if (enter.getRemark().length() < 2 || enter.getRemark().length() > 200) {
                throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getInvoiceNum())) {
            //发票号
            if (enter.getInvoiceNum().length() < 2 || enter.getInvoiceNum().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getBusinessLicenseNum())) {
            //营业执照编号 校验
            if (enter.getBusinessLicenseNum().length() < 2 || enter.getBusinessLicenseNum().length() > 30) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (enter.getScooterQuantity() != null) {
            if (String.valueOf(enter.getScooterQuantity()).length() < 1 || String.valueOf(enter.getScooterQuantity()).length() > 6) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if(Strings.isNotBlank(enter.getTelephone())){
            if(enter.getTelephone().length() < 8 || enter.getTelephone().length() > 20){
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
        }
    }
}
