package com.redescooter.ses.web.ros.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerAccountFlagEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerCertificateTypeEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
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
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.CheckOpenAccountEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountCountStatusEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.foundation.vo.tenant.SynchTenantEnter;
import com.redescooter.ses.api.foundation.vo.user.DeleteUserEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeDetailResult;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeEnter;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.EditUserProfileEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.OrderNoGenerateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.VerificationCodeImgUtil;
import com.redescooter.ses.tool.utils.accountType.AccountTypeUtils;
import com.redescooter.ses.tool.utils.chart.StatisticalUtil;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSalePriceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeSalePriceService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName:CustomerImpl
 * @description: CustomerImpl
 * @author: Alex
 * @Version???1.0
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
    private OpeSalePriceMapper opeSalePriceMapper;

    @Autowired
    private OpeSalePriceService opeSalePriceService;

    @Autowired
    private OpeSysUserProfileMapper sysUserProfileMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private CityBaseService cityBaseService;

    @DubboReference
    private AccountBaseService accountBaseService;

    @DubboReference
    private TenantBaseService tenantBaseService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private UserBaseService userBaseService;

    @DubboReference
    private UserProfileService userProfileService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    /**
     * ????????????
     *
     * @param mail
     * @return
     */
    @Override
    public BooleanResult checkMail(String mail) {
        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, mail);
        wrapper.eq(OpeCustomer.COL_DR, Constant.DR_FALSE);
        Boolean mailBoolean = opeCustomerMapper.selectCount(wrapper) == 1 ? Boolean.TRUE : Boolean.FALSE;
        return new BooleanResult(mailBoolean);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public IntResult checkMailCount(StringEnter enter) {
        //???????????????
        enter.setKeyword(SesStringUtils.stringTrim(enter.getKeyword()));
        QueryWrapper<OpeCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeCustomer.COL_EMAIL, enter.getKeyword());
        wrapper.eq(OpeCustomer.COL_DR, Constant.DR_FALSE);
        wrapper.ne(OpeCustomer.COL_STATUS, CustomerStatusEnum.TRASH_CUSTOMER.getValue());
        Integer count = opeCustomerMapper.selectCount(wrapper);
        return new IntResult(count);
    }

    /**
     * ????????????
     *
     * @param createCustomerEnter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(CreateCustomerEnter createCustomerEnter) {
        //employeeListEnter??????????????????
        CreateCustomerEnter enter = SesStringUtils.objStringTrim(createCustomerEnter);
        //??????????????????
        checkSaveCustomerFiledSingle(enter);

        //??????????????? ??????????????????
        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        OpeCustomer saveVo = new OpeCustomer();
        BeanUtils.copyProperties(enter, saveVo);
        saveVo.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveVo.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        saveVo.setCustomerSource(CustomerSourceEnum.SYSTEM.getValue());
        if (enter.getCustomerType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            saveVo.setContactFullName(new StringBuffer().append(saveVo.getContactFirstName()).append(" ").append(saveVo.getContactLastName()).toString());
//            // ?????????????????? ??????????????????????????????
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
        // ??????????????????????????? ??????????????????????????????
        creatInquiry(saveVo);
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????(??????????????????)
    public void creatInquiry(OpeCustomer customer) {
        OpeCustomerInquiry inquiry = new OpeCustomerInquiry();
        inquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        inquiry.setOrderNo(createOrderNo(OrderNumberTypeEnums.INQUIRY_ORDER.getValue()));
        inquiry.setCustomerId(customer.getId());
//        inquiry.setCountry(customer.getCountry());
        inquiry.setCity(customer.getDef2());
        inquiry.setDistrict(customer.getDef3());
        inquiry.setPostCode(customer.getDef3());
        inquiry.setCustomerSource(CustomerSourceEnum.SYSTEM.getValue());
        inquiry.setSalesId(customer.getCreatedBy());
        inquiry.setStatus(InquiryStatusEnums.UNPAY_DEPOSIT.getValue());
//        inquiry.setCustomerType();
        inquiry.setFirstName(customer.getCustomerFirstName());
        inquiry.setLastName(customer.getCustomerLastName());
        inquiry.setFullName(customer.getCustomerFullName());
        inquiry.setCountryCode(customer.getCountryCode());
        inquiry.setTelephone(customer.getTelephone());
        inquiry.setEmail(customer.getEmail());
        inquiry.setAddress(customer.getAddress());
        inquiry.setContactFirst(customer.getContactFirstName());
        inquiry.setContactLast(customer.getContactLastName());
        inquiry.setContantFullName(customer.getContactFullName());
        inquiry.setPayStatus(InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue());
        if (1 != customer.getScooterQuantity()) {
            inquiry.setScooterQuantity(customer.getScooterQuantity());
        } else {
            inquiry.setScooterQuantity(1);
        }
//        inquiry.setRemarks();
        inquiry.setSource(InquirySourceEnums.SYS_FORM.getValue());
        inquiry.setBankCardName(null);
        inquiry.setCvv(null);
        inquiry.setCardNum(null);
        inquiry.setCreatedBy(customer.getCreatedBy());
        inquiry.setUpdatedBy(customer.getUpdatedBy());
        inquiry.setCreatedTime(new Date());
        inquiry.setUpdatedTime(new Date());
        inquiry.setDef1(customer.getDef1());
        inquiry.setDef2(customer.getDef2());
        inquiry.setDef3(customer.getDef3());

        // ??????E50?????????ID
        Long productId = customerServiceMapper.getProductId();
        if (StringManaConstant.entityIsNull(productId)) {
            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_MAINTAIN.getCode(), ExceptionCodeEnums.PLEASE_MAINTAIN.getMessage());
        }
        inquiry.setProductId(productId);
        inquiry.setProductModel(ProductModelEnums.SCOOTER_50_CC.getValue());


        LambdaQueryWrapper<OpeSalePrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSalePrice::getDr,Constant.DR_FALSE);
        wrapper.like(OpeSalePrice::getScooterBattery,"E50");
        wrapper.eq(OpeSalePrice::getType,2);
        wrapper.last("limit 1");
        OpeSalePrice opeSalePrice = opeSalePriceMapper.selectOne(wrapper);
        if (opeSalePrice==null){
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_NOT_FOUND.getCode(), ExceptionCodeEnums.SALE_PRICE_NOT_FOUND.getMessage());
        }
        inquiry.setProductPrice(opeSalePrice.getDeposit().add(opeSalePrice.getBalance()));
        inquiry.setTotalPrice(opeSalePrice.getDeposit().add(opeSalePrice.getBalance()));
        inquiry.setAmountPaid(new BigDecimal(0));
        inquiry.setAmountObligation(opeSalePrice.getDeposit().add(opeSalePrice.getBalance()));
        inquiry.setAmountDiscount(new BigDecimal(0));
        inquiry.setPrepaidDeposit(opeSalePrice.getDeposit());
        opeCustomerInquiryService.saveOrUpdate(inquiry);

        // ??????????????????
        OpeCustomerInquiryB inquiryB = new OpeCustomerInquiryB();
        inquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        inquiryB.setInquiryId(inquiry.getId());
        inquiryB.setProductQty(1);
        inquiryB.setProductType("1");
        inquiryB.setCreatedTime(new Date());
        inquiryB.setUpdatedTime(new Date());
        inquiryB.setCreatedBy(customer.getCreatedBy());
        inquiryB.setUpdatedBy(customer.getUpdatedBy());
        inquiryB.setProductId(inquiry.getProductId());
        inquiryB.setProductPrice(inquiry.getProductPrice());
        opeCustomerInquiryBService.saveOrUpdate(inquiryB);
    }


    // ????????????????????????
    public String createOrderNo(String orderNoEnum) {
        String code = "";
        // ???????????????????????????????????????????????????
        QueryWrapper<OpeCustomerInquiry> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpeCustomerInquiry.COL_ORDER_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.like(OpeCustomerInquiry.COL_ORDER_NO, "R");
        queryWrapper.orderByDesc(OpeCustomerInquiry.COL_ORDER_NO);
        queryWrapper.last("limit 1");
        OpeCustomerInquiry inquiry = opeCustomerInquiryService.getOne(queryWrapper);
        if (StringManaConstant.entityIsNotNull(inquiry)) {
            // ?????????????????????????????????  ????????????????????????
            code = OrderNoGenerateUtil.orderNoGenerate(inquiry.getOrderNo(), orderNoEnum);
        } else {
            // ?????????????????????????????????????????????????????????????????????
            code = orderNoEnum + DateUtil.getSimpleDateStamp() + "001";
        }
        //????????????????????? ????????????????????????R?????????ROS????????????
        String frond = code.substring(0,code.length()-3);
        String back = code.substring(code.length()-3,code.length());
        return frond +"R"+back;
    }


    /**
     * ??????????????????
     *
     * @param editCustomerEnter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult edit(EditCustomerEnter editCustomerEnter) {
        //employeeListEnter??????????????????
        EditCustomerEnter enter = SesStringUtils.objStringTrim(editCustomerEnter);
        //????????????????????????
        checkEditCustomerFiledSingle(enter);
        //??????????????? ??????????????????
        QueryWrapper<OpeCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
        queryWrapper.ne(OpeCustomer.COL_ID, enter.getId());
        Integer count = opeCustomerMapper.selectCount(queryWrapper);

        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        Long tenantId = customer.getTenantId();
        if (CustomerStatusEnum.TRASH_CUSTOMER.getValue().equals(customer.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getCode(), ExceptionCodeEnums.TRASH_CAN_NOT_BE_EDITED.getMessage());
        }
        if (CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue().equals(customer.getStatus())) {
            //????????????
            checkCustomer(enter);
            // ???????????? ??????????????????
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
            editUserProfileEnter.setTelNumber1(enter.getTelephone());
            List<Integer> userTypeList = new ArrayList<>();
            // ???????????????web ??????
            if (0 != customer.getTenantId()) {
                // corporate???????????????????????????????????????????????????????????????????????????platform???userId??????????????????????????????
                userTypeList.add(AccountTypeEnums.WEB_RESTAURANT.getAccountType().intValue());
                userTypeList.add(AccountTypeEnums.WEB_EXPRESS.getAccountType().intValue());
                editUserProfileEnter.setUserId(userBaseService.getUserId(customer.getEmail(), userTypeList));
                // saas ??????????????????
                userProfileService.editUserProfile2B(editUserProfileEnter);
            }
            if (0 == customer.getTenantId() && StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), customer.getCustomerType())) {
                // TOc ??????????????????
                userTypeList.add(AccountTypeEnums.APP_PERSONAL.getAccountType().intValue());
                editUserProfileEnter.setUserId(userBaseService.getUserId(customer.getEmail(), userTypeList));
                userProfileService.editUserProfile2C(editUserProfileEnter);
            }
        }
        if (!enter.getEmail().equals(customer.getEmail())) {
            //???????????????????????????????????????????????????
            if (0 < checkMailCount(new StringEnter(enter.getEmail())).getValue()) {
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

        // ??????????????????
        UpdateWrapper<OpeCustomerInquiry> updateWrapper = new UpdateWrapper();
        updateWrapper.eq(OpeCustomerInquiry.COL_CUSTOMER_ID, enter.getId());
        updateWrapper.eq(OpeCustomerInquiry.COL_DR, Constant.DR_FALSE);
        updateWrapper.set(OpeCustomerInquiry.COL_SCOOTER_QUANTITY, enter.getScooterQuantity());
        updateWrapper.set(OpeCustomerInquiry.COL_UPDATED_BY, enter.getUserId());
        updateWrapper.set(OpeCustomerInquiry.COL_UPDATED_TIME, new Date());
        opeCustomerInquiryService.update(updateWrapper);

        // ???????????????????????????????????????platform?????????????????????(????????????????????????????????????????????????)
        SynchTenantEnter tenantEnter = new SynchTenantEnter();
        tenantEnter.setEmail(enter.getEmail());
        tenantEnter.setCompanyName(enter.getCompanyName());
        tenantEnter.setAddress(enter.getAddress());
        userBaseService.custDataSynchTenant(tenantEnter);
        return new GeneralResult(enter.getRequestId());
    }


    private void checkEditCustomerFiledSingle(EditCustomerEnter enter) {
        //????????????
        if (StringUtils.isNotEmpty(enter.getContactFirstName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getContactFirstName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getContactLastName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getContactLastName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCompanyName())) {
            if (NumberUtil.ltTwoOrGtThirty(enter.getCompanyName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        //????????????
        if (StringUtils.isNotEmpty(enter.getCustomerFirstName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getCustomerFirstName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCustomerLastName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getCustomerLastName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getRemark())) {
            if (NumberUtil.ltTwoOrGtTwoHundred(enter.getRemark().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getInvoiceNum())) {
            //?????????
            if (NumberUtil.ltTwoOrGtThirty(enter.getInvoiceNum().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getBusinessLicenseNum())) {
            //?????????????????? ??????
            if (NumberUtil.ltTwoOrGtThirty(enter.getBusinessLicenseNum().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringManaConstant.entityIsNotNull(enter.getScooterQuantity())) {
            if (NumberUtil.ltOneOrGtSix(String.valueOf(enter.getScooterQuantity()).length())) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (Strings.isNotBlank(enter.getTelephone())) {
            if (NumberUtil.ltEightOrGtTwenty(enter.getTelephone().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotBlank(enter.getEmail())) {
            String email = enter.getEmail();
            int firstIndex = email.indexOf("@");
            int secondIndex = email.lastIndexOf(".");

            // 1.????????????@ 2.????????????. 3.@?????????.?????? 4..?????????????????????
            if (-1 == firstIndex || -1 == secondIndex || firstIndex > secondIndex || email.endsWith(".")) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
        }
    }


    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public DetailsCustomerResult details(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
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
        //??????????????? ??????????????????
        if (booleanResult.isSuccess()) {
            result.setTtl(null);
        } else {
            //????????????????????????????????????
            Boolean exists = jedisCluster.exists(new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString());
            if (exists) {
                Long ttl = jedisCluster.ttl(new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString());
                result.setTtl(ttl);
            } else {
                result.setTtl(new Long(0));
            }
        }

        // ??????????????? ??????
        result.setInformationPerfectionNum(checkCustomerInformation(opeCustomer));
        return result;
    }


    private OpeSysUserProfile getOpeSysUserProfile(Long userId) {
        QueryWrapper<OpeSysUserProfile> updated = new QueryWrapper<>();
        updated.eq(OpeSysUserProfile.COL_SYS_USER_ID, userId);
        updated.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
        updated.last("limit 1");
        OpeSysUserProfile profile = sysUserProfileMapper.selectOne(updated);
        return profile;
    }


    /**
     * ????????????
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
     * @desc: ??????????????????
     * @param: enter
     * @return: CustomerListByPageResult
     * @auther: alex
     * @date: 2019/12/18 11:51
     * @Version: Ros 1.0
     */
    @Override
    public PageResult<DetailsCustomerResult> list(ListCustomerEnter page) {
        if (NumberUtil.notNullAndGtFifty(page.getKeyword())) {
            return PageResult.createZeroRowResult(page);
        }
        int totalRows = customerServiceMapper.customerListCount(page);
        if (NumberUtil.eqZero(totalRows)) {
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult delete(IdEnter enter) {
        //????????????????????????SaaS???????????????
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (!opeCustomer.getStatus().equals(CustomerStatusEnum.TRASH_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        opeCustomerMapper.deleteById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult trash(TrashCustomerEnter enter) {

        //????????????????????????SaaS???????????????
        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????
        if (userBaseService.checkActivat(customer.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.ACTIVATION_CUSTOMER_NOT_DELETE.getCode(), ExceptionCodeEnums.ACTIVATION_CUSTOMER_NOT_DELETE.getMessage());
        }
        if (CustomerAccountFlagEnum.ACTIVATION.getValue().equals(customer.getAccountFlag())) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }
        if (CustomerAccountFlagEnum.INACTIVATED.getValue().equals(customer.getAccountFlag())) {
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult change(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());
        EditCustomerEnter checkCustomer = new EditCustomerEnter();
        BeanUtils.copyProperties(customer, checkCustomer);
        checkCustomer.setCityName(customer.getDef2());
        checkCustomer(checkCustomer);

        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!customer.getStatus().equals(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue())) {
            return new GeneralResult(enter.getRequestId());
        }
        // ?????????????????????????????????
        if (CustomerSourceEnum.WEBSITE.getValue().equals(customer.getCustomerSource())) {
            // ?????????????????????????????? ?????????????????? ????????????????????????(???????????????????????? ????????????????????????????????? ?????????)
            QueryWrapper<OpeCustomerInquiry> query = new QueryWrapper<>();
            query.eq(OpeCustomerInquiry.COL_CUSTOMER_ID, customer.getId());
            query.eq(OpeCustomerInquiry.COL_PAY_STATUS, InquiryPayStatusEnums.PAY_LAST_PARAGRAPH.getValue());
            int count = opeCustomerInquiryService.count(query);
            if (NumberUtil.eqZero(count)) {
                // ??????????????????????????????????????????
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_ORDER_NOT_PAY.getCode(), ExceptionCodeEnums.CUSTOMER_ORDER_NOT_PAY.getMessage());
            }
        }
        customer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue());
        customer.setUpdatedBy(enter.getUserId());
        customer.setUpdatedTime(new Date());
        opeCustomerMapper.updateById(customer);
        // 2021 3 3 ??????????????????????????????????????? ??????????????????????????????????????????
        changeInquiryStatus(customer.getId(), enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????????????????
    public void changeInquiryStatus(Long customerId, Long userId) {
        QueryWrapper<OpeCustomerInquiry> inquiryQueryWrapper = new QueryWrapper<OpeCustomerInquiry>();
        inquiryQueryWrapper.eq(OpeCustomerInquiry.COL_CUSTOMER_ID, customerId);
        inquiryQueryWrapper.eq(OpeCustomerInquiry.COL_PAY_STATUS, InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue());
        List<OpeCustomerInquiry> inquiryList = opeCustomerInquiryService.list(inquiryQueryWrapper);
        if (!CollectionUtils.isEmpty(inquiryList)) {
            for (OpeCustomerInquiry inquiry : inquiryList) {
                inquiry.setPayStatus(InquiryPayStatusEnums.PAY_LAST_PARAGRAPH.getValue());
                inquiry.setStatus(InquiryStatusEnums.PAY_LAST_PARAGRAPH.getValue());
                inquiry.setAmountPaid(inquiry.getTotalPrice());
                inquiry.setAmountObligation(new BigDecimal(0));
                inquiry.setUpdatedTime(new Date());
                inquiry.setUpdatedBy(userId);
            }
            opeCustomerInquiryService.saveOrUpdateBatch(inquiryList);
        }
    }


    /**
     * @param enter
     * @desc: ??????????????????
     * @param: enter
     * @return: GeneralResult
     * @auther: alex
     * @date: 2019/12/18 17:39
     * @Version: ROS 1.0
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult openAccount(OpenAccountEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // todo ???????????? ????????? Driver ?????? ---??? web??????TOC ??????????????????????????????
        //BooleanResult checkMail = checkMail(opeCustomer.getEmail());
        BaseUserResult userResult = null;

        /*if (checkMail.isSuccess()) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }*/

        // ??????????????????
        BaseCustomerResult baseCustomer = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomer);

        DateTimeParmEnter<BaseCustomerResult> parmEnter = new DateTimeParmEnter();
        BeanUtils.copyProperties(enter, parmEnter);
        parmEnter.setStartDateTime(DateUtil.stringToDate(enter.getStartActivationTime()));
        parmEnter.setEndDateTime(DateUtil.stringToDate(enter.getEndActivationTime()));
        parmEnter.setT(baseCustomer);

        // ????????????,??????pla_user
        userResult = accountBaseService.open(parmEnter);

        // ????????????????????????????????????????????????
        opeCustomer.setTenantId(userResult.getTenantId());
        opeCustomer.setAccountFlag("1");
        opeCustomer.setUpdatedBy(enter.getUserId());
        opeCustomer.setUpdatedTime(new Date());
        opeCustomerMapper.updateById(opeCustomer);

        //??????????????????????????????
        String key = new StringBuffer().append("send::").append(opeCustomer.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AccountListResult> accountList(AccountListEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        int countCustomer = customerServiceMapper.customerAccountCount(enter);
        if (NumberUtil.eqZero(countCustomer)) {
            return PageResult.createZeroRowResult(enter);
        }
        // ????????????
        List<AccountListResult> accountList = customerServiceMapper.queryAccountRecord(enter);
        List<String> emailList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accountList)) {
            emailList = accountList.stream().map(AccountListResult::getEmail).collect(Collectors.toList());
        }

        QueryAccountListEnter queryAccountListEnter = new QueryAccountListEnter();
        BeanUtils.copyProperties(enter, queryAccountListEnter);
        queryAccountListEnter.setEmailList(emailList);
        Integer customerAccountCount = accountBaseService.customerAccountCount(queryAccountListEnter);
        if (NumberUtil.eqZero(customerAccountCount)) {
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> accountCountStatus(GeneralEnter enter) {
        // ????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccountNodeResult> accountNode(IdEnter enter) {
        List<AccountNodeResult> resultList = new ArrayList<>();
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
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
            opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public AccountDeatilResult accountDeatil(IdEnter enter) {
        OpeCustomer customerInfo = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(customerInfo)) {
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

        // ??????????????????
        QueryAccountResult queryAccountResult = accountBaseService.customerAccountDeatil(customerInfo.getEmail());
        if (StringManaConstant.entityIsNotNull(queryAccountResult) && StringUtils.equals(queryAccountResult.getEmail(), customerInfo.getEmail())) {
            accountReslut.setStatus(queryAccountResult.getStatus());
            accountReslut.setActivationTime(queryAccountResult.getActivationTime());
            accountReslut.setExpireTime(queryAccountResult.getExpirationTime());
        }

//            QueryTenantResult tenantResult = tenantBaseService.queryTenantById(idEnter);
        return accountReslut;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult freezeAccount(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult unFreezeAccount(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult renewAccont(RenewAccountEnter enter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
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
     * ????????? ??????base64 ?????? ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public VerificationCodeResult verificationCode(GeneralEnter enter) {
        // ?????? ????????????
        VerificationCodeImgUtil vCode = new VerificationCodeImgUtil(103, 32, 5, 16);
        // ???????????????
        VerificationCodeImgUtil.write();
        //??????code???
        String code = VerificationCodeImgUtil.code;
        // redis ??????
        jedisCluster.set(enter.getRequestId(), code);
        // ??????????????????
        jedisCluster.expire(enter.getRequestId(), new Long(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
        VerificationCodeResult result = VerificationCodeResult.builder().base64Img(VerificationCodeImgUtil.base64String).build();
        result.setRequestId(enter.getRequestId());
        log.info("??????code?????????" + code);
        return result;
    }

    /**
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult customerSetPassword(SetPasswordEnter enter) {

        //???????????????
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getConfirmPassword())) {
            enter.setConfirmPassword(SesStringUtils.stringTrim(enter.getConfirmPassword()));
        }

        // ????????????
        String code = jedisCluster.get(enter.getRequestId());
        if (!StringUtils.equals(code, enter.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        if (!StringUtils.equals(enter.getConfirmPassword(), enter.getNewPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (StringManaConstant.entityIsNull(opeCustomer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        BaseCustomerResult baseCustomerResult = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomerResult);
        enter.setT(baseCustomerResult);
        return accountBaseService.setPassword(enter);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BooleanResult sendEmailAgian(IdEnter enter) {

        OpeCustomer customer = opeCustomerMapper.selectById(enter.getId());

        if (customer.getAccountFlag().equals(CustomerAccountFlagEnum.ACTIVATION)) {
            return new BooleanResult(true);
        }

        List<BaseUserResult> userList = userBaseService.queryEmailInfo(new StringEnter(customer.getEmail()));

        if (CollectionUtils.isEmpty(userList)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //??????????????????
        Integer accountType = AccountTypeUtils.getAccountType(customer.getCustomerType(), customer.getIndustryType());
        BooleanResult booleanResult = accountBaseService.checkOpenAccount(CheckOpenAccountEnter.builder().accountType(accountType).email(customer.getEmail()).build());
        if (booleanResult.isSuccess()) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_ALRADY_ACTIVATION.getCode(), ExceptionCodeEnums.ACCOUNT_IS_ALRADY_ACTIVATION.getMessage());
        }

        //????????????????????????????????????
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
        //?????????????????????????????????
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setToMail(customer.getEmail());
        baseMailTaskEnter.setToUserId(userId);

        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);

        //??????????????????????????????
        String key = new StringBuffer().append("send::").append(customer.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());

        return new BooleanResult(true);
    }

    /**
     * ??????????????????
     *
     * @param enter
     */
    public void checkCustomer(EditCustomerEnter enter) {
        if (StringManaConstant.entityIsNull(enter.getId())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.PRIMARY_KEY_CANNOT_EMPTY.getMessage());
        }
        if (StringManaConstant.entityIsNull(enter.getCityName())) {
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
        // ??????????????? 15??? ????????????(???????????????????????????????????????)
        //??????????????? 16?????????????????????????????????????????????????????????

        //??????
        //??????????????? 17/16(?????????/????????????) ???????????????
        //??????????????? 19/28(?????????/????????????) ???????????????

        //???????????? 13??? ?????????????????? ?????? customerFirstName???customerLastName
        //?????????????????? 5??? companyName???constantFirstName,constantLastName,bussinessnex(2)

        int result = 11;
        int count = 0;

        //??????????????? ??????????????????????????????
        if (StringUtils.isNotEmpty(customer.getIndustryType()) && StringUtils.isNotEmpty(customer.getCustomerType())) {
            count += 2;
        }
        if (StringUtils.isNotEmpty(customer.getEmail())) {
            count++;
        }
        //def2=??????
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
        //?????????????????? ???????????????
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
        if (StringManaConstant.entityIsNotNull(customer.getScooterQuantity()) && 0 != customer.getScooterQuantity()) {
            count++;
        }

        //??????????????????
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

        //????????????
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
     * ????????????????????????
     *
     * @param enter
     */
    private void checkSaveCustomerFiledSingle(CreateCustomerEnter enter) {
        if (StringUtils.isNotEmpty(enter.getContactFirstName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getContactFirstName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getContactLastName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getContactLastName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCompanyName())) {
            if (NumberUtil.ltTwoOrGtThirty(enter.getCompanyName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.COMPANY_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        //????????????
        if (StringUtils.isNotEmpty(enter.getCustomerFirstName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getCustomerFirstName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getCustomerLastName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getCustomerLastName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CUSTOMER_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getRemark())) {
            if (NumberUtil.ltTwoOrGtTwoHundred(enter.getRemark().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getInvoiceNum())) {
            //?????????
            if (NumberUtil.ltTwoOrGtThirty(enter.getInvoiceNum().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.INVOICE_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getBusinessLicenseNum())) {
            //?????????????????? ??????
            if (NumberUtil.ltTwoOrGtThirty(enter.getBusinessLicenseNum().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.BUSSINESS_NUM_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringManaConstant.entityIsNotNull(enter.getScooterQuantity())) {
            if (NumberUtil.ltOneOrGtSix(String.valueOf(enter.getScooterQuantity()).length())) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SCOOTER_QTY_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (Strings.isNotBlank(enter.getTelephone())) {
            if (NumberUtil.ltEightOrGtTwenty(enter.getTelephone().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotBlank(enter.getEmail())) {
            String email = enter.getEmail();
            int firstIndex = email.indexOf("@");
            int secondIndex = email.lastIndexOf(".");

            // 1.????????????@ 2.????????????. 3.@?????????.?????? 4..?????????????????????
            if (-1 == firstIndex || -1 == secondIndex || firstIndex > secondIndex || email.endsWith(".")) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
        }
    }
}
