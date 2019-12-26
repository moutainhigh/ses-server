package com.redescooter.ses.web.ros.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerAccountFlagEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerCertificateTypeEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.QueryTenantNodeResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.StatisticalUtil;
import com.redescooter.ses.tool.utils.VerificationCodeImgUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import com.redescooter.ses.web.ros.vo.account.AccountNodeResult;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import com.redescooter.ses.web.ros.vo.account.RenewAccountEnter;
import com.redescooter.ses.web.ros.vo.account.VerificationCodeResult;
import com.redescooter.ses.web.ros.vo.customer.AccountListEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListResult;
import com.redescooter.ses.web.ros.vo.customer.CreateCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.DetailsCustomerResult;
import com.redescooter.ses.web.ros.vo.customer.EditCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.ListCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.TrashCustomerEnter;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:CustomerImpl
 * @description: CustomerImpl
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:06
 */
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
        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, enter.getSt());
        wrapper.eq(OpeCustomer.COL_DR, 0);
        Integer count = opeCustomerMapper.selectCount(wrapper);

        return new IntResult(count);
    }

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(CreateCustomerEnter enter) {

        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            if (StringUtils.isBlank(enter.getContactFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getContactLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
            enter.setScooterQuantity(1);
        }
        if (StringUtils.isBlank(enter.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        OpeCustomer saveVo = new OpeCustomer();
        BeanUtils.copyProperties(enter, saveVo);
        saveVo.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveVo.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        saveVo.setCustomerSource(CustomerSourceEnum.SYSTEM.getValue());
        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            saveVo.setContactFullName(new StringBuffer().append(saveVo.getContactFirstName()).append(" ").append(saveVo.getContactLastName()).toString());
            // 企业个人信息 也要赋值给客户的名字
            saveVo.setCustomerFirstName(saveVo.getContactFirstName());
            saveVo.setCustomerLastName(saveVo.getContactLastName());
            saveVo.setCustomerFullName(saveVo.getContactFullName());
        } else {
            saveVo.setCustomerFullName(new StringBuffer().append(saveVo.getCustomerFirstName()).append(" ").append(saveVo.getCustomerLastName()).toString());
        }
        saveVo.setAccountFlag(CustomerAccountFlagEnum.NORMAL.getValue());
        saveVo.setCreatedBy(enter.getUserId());
        saveVo.setCreatedTime(new Date());
        saveVo.setUpdatedBy(enter.getUserId());
        saveVo.setUpdatedTime(new Date());

        opeCustomerMapper.insert(saveVo);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑更新客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult edit(EditCustomerEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (customer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getCode(), ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getMessage());
        }
        if (customer.getStatus().equals(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            //客户验证
            checkCustomer(enter);
        }
        if(!enter.getEmail().equals(customer.getEmail())){
            //潜在客户允许编辑邮箱，但不允许重复
            if (checkMailCount(new StringEnter(enter.getEmail())).getValue() > 0) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
        }

        OpeCustomer update = new OpeCustomer();
        BeanUtils.copyProperties(enter, update);
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
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
        QueryWrapper<OpeSysUserProfile> created = new QueryWrapper<>();
        created.eq(OpeSysUserProfile.COL_SYS_USER_ID, result.getCreatedBy());
        created.eq(OpeSysUserProfile.COL_DR, 0);
        result.setCreatedName(sysUserProfileMapper.selectOne(created).getFullName());
        result.setAccountFlag(Integer.valueOf(opeCustomer.getAccountFlag()));

        QueryWrapper<OpeSysUserProfile> updated = new QueryWrapper<>();
        updated.eq(OpeSysUserProfile.COL_SYS_USER_ID, result.getUpdatedBy());
        updated.eq(OpeSysUserProfile.COL_DR, 0);
        result.setUpdatedName(sysUserProfileMapper.selectOne(updated).getFullName());

        result.setRequestId(enter.getRequestId());
        if (opeCustomer.getCity() != null || opeCustomer.getDistrust() != null) {
            result.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getCity()).build()).getName());
            result.setDistrustName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(result.getDistrust()).build()).getName());
        }
        // 信息完善度 计算
        result.setInformationPerfectionNum(checkCustomerInformation(opeCustomer));
        return result;
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

        int totalRows = customerServiceMapper.customerListCount(page);

        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<DetailsCustomerResult> detailsCustomerList = customerServiceMapper.customerList(page);

        detailsCustomerList.forEach(customer -> {
            if (customer.getCity() != null || customer.getDistrust() != null) {
                customer.setCityName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(customer.getCity()).build()).getName());
                customer.setDistrustName(cityBaseService.queryCityDeatliById(IdEnter.builder().id(customer.getDistrust()).build()).getName());
            }
        });

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
        if (opeCustomer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
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
    @Override
    public GeneralResult trash(TrashCustomerEnter enter) {

        //验证客户是否开通SaaS账户等信息
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer.getAccountFlag().equals(CustomerAccountFlagEnum.ACTIVATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        if (opeCustomer.getAccountFlag().equals(CustomerAccountFlagEnum.INACTIVATED.getValue())) {
            accountBaseService.deleteUserbyTenantId(IdEnter.builder().id(opeCustomer.getTenantId()).build());
        }
        OpeCustomer update = new OpeCustomer();
        update.setId(enter.getId());
        update.setStatus(CustomerStatusEnum.TRASH_CUSTOMER.getValue());
        update.setMemo(enter.getReason());
        opeCustomerMapper.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户转正
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult change(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        EditCustomerEnter checkCustomer = new EditCustomerEnter();
        BeanUtils.copyProperties(customer, checkCustomer);
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
    @Override
    public GeneralResult openAccount(OpenAccountEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
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

        // 邮件通知
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
        baseMailTaskEnter.setName(opeCustomer.getCustomerFullName());
        baseMailTaskEnter.setToMail(opeCustomer.getEmail());
        baseMailTaskEnter.setToUserId(userResult.getId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setMailAppId(AccountTypeEnums.APP_PERSONAL.getAppId());
        baseMailTaskEnter.setMailSystemId(AccountTypeEnums.APP_PERSONAL.getSystemId());
        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);

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
        int countCustomer = customerServiceMapper.customerAccountCount(enter);
        if (countCustomer == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        // 查询内容
        List<AccountListResult> accountList = customerServiceMapper.queryAccountRecord(enter);
        List<Long> tenantIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accountList)) {
            accountList.forEach(item -> {
                tenantIdList.add(item.getTenantId());
            });
        }

        // 查询时间
        QueryAccountListEnter queryAccountListEnter = new QueryAccountListEnter();
        queryAccountListEnter.setInputTenantId(tenantIdList);
        BeanUtils.copyProperties(enter, queryAccountListEnter);
        int countTenantAccount = accountBaseService.countTenantAccount(queryAccountListEnter);
        if (countTenantAccount == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<QueryAccountListResult> tenantAccountRecords = accountBaseService.tenantAccountRecords(queryAccountListEnter);

        List<AccountListResult> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accountList) && !CollectionUtils.isEmpty(tenantAccountRecords)) {
            tenantAccountRecords.forEach(tenantAccount -> {
                accountList.forEach(item -> {
                    if (tenantAccount.getInputTenantId().equals(item.getTenantId())) {
                        item.setTenantId(tenantAccount.getId());
                        item.setStatus(tenantAccount.getStatus());
                        item.setActivationTime(tenantAccount.getActivationTime());
                        item.setExpirationTime(tenantAccount.getExpirationTime());
                        resultList.add(item);
                    }
                });
            });
        }
        return PageResult.create(enter, countTenantAccount, resultList);
    }

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> accountCountStatus(GeneralEnter enter) {
        return accountBaseService.accountCountStatus();
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
        enter.setId(opeCustomer.getTenantId());
        List<QueryTenantNodeResult> tenantNodeResultList = tenantBaseService.queryTenantNdoe(enter);

        // todo 需优化 调用数据库过于频繁
        if (!CollectionUtils.isEmpty(tenantNodeResultList)) {
            tenantNodeResultList.forEach(item -> {
                QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
                opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, item.getCreateBy());
                OpeSysUserProfile opeSysUserProfile = sysUserProfileMapper.selectOne(opeSysUserProfileQueryWrapper);
                AccountNodeResult result = AccountNodeResult.builder()
                        .id(item.getId())
                        .event(item.getEvent())
                        .eventTime(DateUtil.format(item.getEventTime(), DateUtil.DEFAULT_DATETIME_FORMAT))
                        .createdBy(item.getCreateBy())
                        .createdFirstName(opeSysUserProfile.getFirstName())
                        .createdLastName(opeSysUserProfile.getLastName())
                        .build();
                resultList.add(result);
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
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        enter.setId(opeCustomer.getTenantId());

        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(opeCustomer.getTenantId());
        QueryTenantResult queryTenantResult = tenantBaseService.queryTenantById(idEnter);
        AccountDeatilResult reslut = AccountDeatilResult.builder()
                .id(opeCustomer.getId())
                .customerType(opeCustomer.getCustomerType())
                .customerFirstName(opeCustomer.getCustomerFirstName())
                .customerLastName(opeCustomer.getCustomerLastName())
                .customerFullName(opeCustomer.getCustomerFullName())
                .companyName(opeCustomer.getCompanyName())
                .industryType(opeCustomer.getIndustryType())
                .status(queryTenantResult.getStatus())
                .email(opeCustomer.getEmail())
                .activationTime(DateUtil.getTimeStr(queryTenantResult.getEffectiveTime(), DateUtil.DEFAULT_DATETIME_FORMAT))
                .expireTime(DateUtil.getTimeStr(queryTenantResult.getExpireTime(), DateUtil.DEFAULT_DATETIME_FORMAT))
                .build();
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), opeCustomer.getCustomerType())) {
            reslut.setContactFirstName(opeCustomer.getContactFirstName());
            reslut.setContactLastName(opeCustomer.getContactLastName());
            reslut.setContactFullName(opeCustomer.getContactFullName());
        }
        return reslut;
    }

    /**
     * 账户冻结
     *
     * @param enter
     * @return
     */
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
    @Override
    public VerificationCodeResult verificationCode(GeneralEnter enter) {
        // 定义 图片大小
        VerificationCodeImgUtil vCode = new VerificationCodeImgUtil(103, 32, 5, 16);
        // 调用写操作
        vCode.write();
        //获取code码
        String code = vCode.code;
        // redis 存储
        jedisCluster.set(enter.getRequestId(), code);
        // 设置超时时间
        jedisCluster.expire(enter.getRequestId(), 60);
        VerificationCodeResult result = VerificationCodeResult.builder().base64Img(vCode.base64String).build();
        result.setRequestId(enter.getRequestId());
        System.out.println(code);
        return result;
    }

    /**
     * @param enter
     * @return
     */
    @Override
    public GeneralResult customerSetPassword(SetPasswordEnter enter) {
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

    private void checkCustomer(EditCustomerEnter enter) {
        if (enter.getId() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCity() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CITY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CITY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getDistrust() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DISTRUST_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.DISTRUST_CANNOT_EMPTY.getMessage());
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

    private String checkCustomerInformation(OpeCustomer customer) {
        // 个人客户为 14个 基本字段
        //企业客户为 16个基本字段

        int result = 14;
        int count = 0;

        if (customer.getCity() != null) {
            count++;
        }
        if (customer.getDistrust() != null) {
            count++;
        }
        if (customer.getCustomerType().equals(CustomerTypeEnum.PERSONAL.getValue())) {
            if (!StringUtils.isBlank(customer.getCustomerFirstName())) {
                count++;
            }
            if (!StringUtils.isBlank(customer.getCustomerLastName())) {
                count++;
            }
        } else {
            // 企业 为16个字段 所以这里现加 2
            result += 2;
            if (!StringUtils.isBlank(customer.getCompanyName())) {
                count++;
            }
            if (!StringUtils.isBlank(customer.getBusinessLicenseNum())) {
                count++;
            }
            if (!StringUtils.isBlank(customer.getBusinessLicenseNum())) {
                count++;
            }
            if (!StringUtils.isBlank(customer.getBusinessLicenseAnnex())) {
                count++;
            }
        }
        if (!StringUtils.isBlank(customer.getCustomerType())) {
            count++;
        }
        if (!StringUtils.isBlank(customer.getIndustryType())) {
            count++;
        }
        if (!StringUtils.isBlank(customer.getAddress())) {
            count++;
        }
        if (!StringUtils.isAllBlank(String.valueOf(customer.getLongitude()), String.valueOf(customer.getLatitude()))) {
            count += 2;
        }
        if (!StringUtils.isBlank(customer.getTelephone())) {
            count++;
        }
        if (!StringUtils.isBlank(customer.getCertificateType())) {
            count++;
        }
        if (customer.getCertificateType().equals(CustomerCertificateTypeEnum.ID_CARD.getValue())) {
            //如果 IdCard 所有的字段个数 +2 否则 +1
            result += 2;
            if (!StringUtils.isAllBlank(customer.getCertificatePositiveAnnex(), customer.getCertificateNegativeAnnex())) {
                count += 2;
            }
        } else {
            result++;
            if (!StringUtils.isBlank(customer.getCertificatePositiveAnnex())) {
                count++;
            }
        }
        if (!StringUtils.isBlank(customer.getInvoiceNum())) {
            count++;
        }
        if (!StringUtils.isBlank(customer.getInvoiceAnnex())) {
            count++;
        }
        if (!StringUtils.isBlank(customer.getContractAnnex())) {
            count++;
        }
        return StatisticalUtil.percentageUtil(count, result);
    }

}
