package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.hub.vo.operation.SyncCustomerDataEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.enums.AccountFlagEnums;
import com.redescooter.ses.web.website.enums.CustomerTypeEnums;
import com.redescooter.ses.web.website.enums.WebSiteCustomerSourceEnums;
import com.redescooter.ses.web.website.enums.WebSiteCustomerStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import com.redescooter.ses.web.website.service.WebSiteCustomerService;
import com.redescooter.ses.web.website.service.base.SiteCustomerService;
import com.redescooter.ses.web.website.service.base.SiteUserService;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import com.redescooter.ses.web.website.vo.customer.CustomerDetailsResult;
import com.redescooter.ses.web.website.vo.customer.EditSiteCustomerEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/6 9:29 下午
 * @Description 官网客户服务
 **/
@Slf4j
@Service
public class WebSiteCustomerServiceImpl implements WebSiteCustomerService {

    @Autowired
    private SiteCustomerService siteCustomerService;

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private TokenWebsiteService tokenWebsiteService;

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private CustomerService customerService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addCustomer(AddCustomerEnter enter) {
        SesStringUtils.objStringTrim(enter);
        String decryptEamil = null;
        String psd = null;
        String confirmPsd = null;
        String phone = null;
        if (StringUtils.isNotEmpty(enter.getEmail())) {
            try {
                //邮箱解密
                decryptEamil = RsaUtils.decrypt(enter.getEmail(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setEmail(decryptEamil);
        }
        if (StringUtils.isNotEmpty(enter.getTelephone())) {
            try {
                //手机号解密
                phone = RsaUtils.decrypt(enter.getTelephone(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setTelephone(phone);
        }
        if (StringUtils.isNotEmpty(enter.getPassword())) {
            try {
                //密码解密
                psd = RsaUtils.decrypt(enter.getPassword(), requestsKeyProperties.getPrivateKey());
                confirmPsd = RsaUtils.decrypt(enter.getCfmPassword(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setPassword(psd);
            enter.setCfmPassword(confirmPsd);
        }
        if (!enter.getCfmPassword().equals(enter.getPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(),
                    ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        checkParamLength(enter);

        checkEmail(enter.getEmail());
        Long customerID = saveCustomer(enter);

        // 官网创建客户数据同步到ros
        syncData(enter);

        // 官网创建客户后发送创建账号成功邮件
        log.info("开始给客户发送邮件");
        BaseMailTaskEnter mailTask = new BaseMailTaskEnter();
        mailTask.setName(new StringBuffer().append(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
        mailTask.setEvent(MailTemplateEventEnums.WEBSITE_SIGN_UP.getEvent());
        mailTask.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
        mailTask.setAppId(AppIDEnums.SES_ROS.getValue());
        mailTask.setEmail(enter.getEmail());
        mailTask.setRequestId(enter.getRequestId());
        mailTask.setUserId(0L);
        log.info("封装发送邮件的参数是:[{}]", mailTask);
        mailMultiTaskService.addMultiMailTask(mailTask);
        log.info("给客户发送邮件完成");

        LoginEnter signUp = new LoginEnter();
        signUp.setLoginName(enter.getEmail());
        signUp.setPassword(enter.getCfmPassword().trim());
        signUp.setCustomerId(customerID);
        return tokenWebsiteService.signUp(signUp);
    }

    public void checkParamLength(AddCustomerEnter enter) {
        if (StringUtils.isNotBlank(enter.getEmail()) && enter.getEmail().length() > 64) {
            throw new SesWebsiteException(ExceptionCodeEnums.EMAIL_TOO_LONG.getCode(), ExceptionCodeEnums.EMAIL_TOO_LONG.getMessage());
        }
        if (StringUtils.isNotBlank(enter.getTelephone()) && enter.getTelephone().length() > 32) {
            throw new SesWebsiteException(ExceptionCodeEnums.PHONE_LENGTH_OUT.getCode(), ExceptionCodeEnums.PHONE_LENGTH_OUT.getMessage());
        }
        if (StringUtils.isNotBlank(enter.getAddress()) && enter.getAddress().length() > 256) {
            throw new SesWebsiteException(ExceptionCodeEnums.ADDRESS_LENGTH_OUT.getCode(), ExceptionCodeEnums.ADDRESS_LENGTH_OUT.getMessage());
        }
    }

    @Async
    void syncData(AddCustomerEnter enter) {
        SyncCustomerDataEnter model = new SyncCustomerDataEnter();
        model.setDr(Constant.DR_FALSE);
        model.setTimeZone("08:00");
        model.setDef1(enter.getCountryName());
        model.setDef2(enter.getCityName());
        model.setDef3(enter.getPostcode());
        model.setCountryCode(enter.getCountryCode());
        model.setStatus("4");
        model.setCustomerFirstName(enter.getCustomerFirstName());
        model.setCustomerLastName(enter.getCustomerLastName());
        if (StringUtils.isNoneBlank(enter.getCustomerFirstName(), enter.getCustomerLastName())) {
            model.setCustomerFullName(new StringBuffer().append(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
        }
        model.setCustomerSource(String.valueOf(WebSiteCustomerSourceEnums.OFFICIAL.getValue()));
        model.setCustomerType(String.valueOf(CustomerTypeEnums.PERSONAL.getValue()));
        model.setAddress(enter.getAddress());
        model.setPlaceId(enter.getPlaceId());
        model.setLongitude(enter.getLongitude());
        model.setLatitude(enter.getLatitude());
        model.setTelephone(enter.getTelephone());
        model.setEmail(enter.getEmail());
        model.setScooterQuantity(1);
        model.setAssignationScooterQty(0);
        model.setAccountFlag(String.valueOf(AccountFlagEnums.INACTIVATED.getValue()));
        model.setCreatedBy(0L);
        model.setCreatedTime(new Date());
        model.setUpdatedTime(new Date());
        model.setUpdatedBy(0L);
        customerService.syncCustomerData(model);
    }

    /**
     * 获取客户详情
     *
     * @param enter
     */
    @Override
    public CustomerDetailsResult getCustomerDetails(GeneralEnter enter) {

        /*用户ID*/
        Long userId = enter.getUserId();

        if (userId == 0L) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        SiteUser siteUser = siteUserService.getById(enter.getUserId());
        if (siteUser == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        SiteCustomer customer = siteCustomerService.getById(siteUser.getCustomerId());
        CustomerDetailsResult result = new CustomerDetailsResult();

        if (customer != null) {
            BeanUtils.copyProperties(customer, result);
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 客户编辑
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult editCustomer(EditSiteCustomerEnter enter) {
        log.info("**********************开始编辑客户的信息*************************");
        String phone = "";
        String decryptEamil = "";
        if (StringUtils.isNotEmpty(enter.getTelephone())) {
            try {
                //手机号解密
                phone = RsaUtils.decrypt(enter.getTelephone(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setTelephone(phone);
        }
        if (enter.getTelephone().length() > 20) {
            throw new SesWebsiteException(ExceptionCodeEnums.PHONE_LENGTH_OUT.getCode(), ExceptionCodeEnums.PHONE_LENGTH_OUT.getMessage());
        }

        if (enter.getAddress().length() > 255) {
            throw new SesWebsiteException(ExceptionCodeEnums.ADDRESS_LENGTH_OUT.getCode(), ExceptionCodeEnums.ADDRESS_LENGTH_OUT.getMessage());
        }

        if (StringUtils.isNotEmpty(enter.getEmail())) {
            try {
                //邮箱解密
                decryptEamil = RsaUtils.decrypt(enter.getEmail(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setEmail(decryptEamil);
        }

        checkEditEmail(enter.getEmail());

        SiteCustomer edit = new SiteCustomer();
        BeanUtils.copyProperties(enter, edit);
        siteCustomerService.updateById(edit);

        // 官网编辑客户数据同步到ros
        log.info("**********************准备把客户的信息同步到ROS中*************************");
        syncEditData(edit);

        return new GeneralResult(enter.getRequestId());
    }

    public void checkEditEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            int firstIndex = email.indexOf("@");
            int secondIndex = email.indexOf(".");
            // 1.必须包含@ 2.必须包含. 3.@必须在.之前 4..后至少要有一位
            if (firstIndex == -1 || secondIndex == -1 || firstIndex > secondIndex || email.endsWith(".")) {
                throw new SesWebsiteException(ExceptionCodeEnums.EMAIL_ERROR.getCode(), ExceptionCodeEnums.EMAIL_ERROR.getMessage());
            }
        }
    }

    @Async
    void syncEditData(SiteCustomer enter) {
        SyncCustomerDataEnter model = new SyncCustomerDataEnter();
        model.setDr(Constant.DR_FALSE);
        model.setTimeZone("08:00");
        model.setDef1(enter.getCountryName());
        model.setDef2(enter.getCityName());
        model.setDef3(enter.getPostcode());
        model.setCountryCode(enter.getCountryCode());
        model.setStatus("1");
        model.setCustomerFirstName(enter.getCustomerFirstName());
        model.setCustomerLastName(enter.getCustomerLastName());
        if (StringUtils.isNoneBlank(enter.getCustomerFirstName(), enter.getCustomerLastName())) {
            model.setCustomerFullName(new StringBuffer().append(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
        }
        model.setCustomerSource(String.valueOf(WebSiteCustomerSourceEnums.OFFICIAL.getValue()));
        model.setCustomerType(String.valueOf(CustomerTypeEnums.PERSONAL.getValue()));
        model.setAddress(enter.getAddress());
        model.setPlaceId(enter.getPlaceId());
        model.setLongitude(enter.getLongitude());
        model.setLatitude(enter.getLatitude());
        model.setTelephone(enter.getTelephone());
        model.setEmail(enter.getEmail());
        model.setScooterQuantity(1);
        model.setAssignationScooterQty(0);
        model.setAccountFlag(String.valueOf(AccountFlagEnums.INACTIVATED.getValue()));
        model.setUpdatedBy(0L);
        model.setUpdatedTime(new Date());
        log.info("**********************开始调用方法把客户的信息同步到ROS中*************************");
        customerService.syncCustomerData(model);
    }


    private void checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new SesWebsiteException(ExceptionCodeEnums.EMAIL_EMPTY.getCode(),
                    ExceptionCodeEnums.EMAIL_EMPTY.getMessage());
        }

        int firstIndex = email.indexOf("@");
        int secondIndex = email.indexOf(".");
        // 1.必须包含@ 2.必须包含. 3.@必须在.之前 4..后至少要有一位
        if (firstIndex == -1 || secondIndex == -1 || firstIndex > secondIndex || email.endsWith(".")) {
            throw new SesWebsiteException(ExceptionCodeEnums.EMAIL_ERROR.getCode(), ExceptionCodeEnums.EMAIL_ERROR.getMessage());
        }

        SiteCustomer addCustomer = siteCustomerService.getOne(new QueryWrapper<SiteCustomer>()
                .eq(SiteCustomer.COL_DR, Constant.DR_FALSE)
                .eq(SiteCustomer.COL_EMAIL, email.trim()));
        if (addCustomer != null) {
            throw new SesWebsiteException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        SiteUser user = siteUserService.getOne(new QueryWrapper<SiteUser>()
                .eq(SiteUser.COL_DR, Constant.DR_FALSE)
                .eq(SiteUser.COL_LOGIN_NAME, email.trim()));
        if (user != null) {
            throw new SesWebsiteException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());

        }
    }

    private Long saveCustomer(AddCustomerEnter enter) {
        SiteCustomer addCustomer = new SiteCustomer();
        addCustomer.setId(idAppService.getId(SequenceName.SITE_CUSTOMER));
        addCustomer.setDr(Constant.DR_FALSE);
        addCustomer.setStatus(WebSiteCustomerStatusEnums.INTENTION.getValue());
        addCustomer.setCustomerCode(new StringBuffer().append("CR_").append(MainCode.generateByShuffle()).toString());
        addCustomer.setCustomerSource(WebSiteCustomerSourceEnums.OFFICIAL.getValue());
        addCustomer.setCustomerType(CustomerTypeEnums.PERSONAL.getValue());
        addCustomer.setCustomerFirstName(enter.getCustomerFirstName());
        addCustomer.setCustomerLastName(enter.getCustomerLastName());
        if (StringUtils.isNoneBlank(enter.getCustomerFirstName(), enter.getCustomerLastName())) {
            addCustomer.setCustomerFullName(new StringBuffer().append(enter.getCustomerFirstName()).append(enter.getCustomerLastName()).toString());
        }
        addCustomer.setCityName(enter.getCityName());
        addCustomer.setAreaName(enter.getAreaName());
        addCustomer.setPostcode(enter.getPostcode());
        addCustomer.setAddress(enter.getAddress());
        addCustomer.setPlaceId(enter.getPlaceId());
        addCustomer.setLongitude(enter.getLongitude());
        addCustomer.setLatitude(enter.getLatitude());
        addCustomer.setCountryCode(enter.getCountryCode());
        addCustomer.setTelephone(enter.getTelephone());
        addCustomer.setEmail(enter.getEmail());
        addCustomer.setPurchasedScooterQty(1);
        addCustomer.setAssignationScooterQty(0);
        addCustomer.setAccountFlag(AccountFlagEnums.INACTIVATED.getValue());
        addCustomer.setSynchronizeFlag(false);
        addCustomer.setRevision(0);
        addCustomer.setCreatedBy(0L);
        addCustomer.setCreatedTime(new Date());
        addCustomer.setUpdatedBy(0L);
        addCustomer.setUpdatedTime(new Date());
        addCustomer.setCountryName(enter.getCountryName());
        siteCustomerService.save(addCustomer);

        return addCustomer.getId();
    }
}
