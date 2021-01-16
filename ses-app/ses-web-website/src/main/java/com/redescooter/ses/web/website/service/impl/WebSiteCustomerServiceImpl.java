package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建客户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult addCustomer(AddCustomerEnter enter) {

        if (!enter.getConfirmPassword().trim().equals(enter.getPassword().trim())) {
            throw new SesWebsiteException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(),
                    ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        checkEmail(enter.getEmail());
        Long customerID = saveCustomer(enter);

        LoginEnter signUp = new LoginEnter();
        signUp.setLoginName(enter.getEmail());
        signUp.setPassword(enter.getConfirmPassword().trim());
        signUp.setCustomerId(customerID);
        return tokenWebsiteService.signUp(signUp);
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

    private void checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new SesWebsiteException(ExceptionCodeEnums.EMAIL_EMPTY.getCode(),
                    ExceptionCodeEnums.EMAIL_EMPTY.getMessage());
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
        addCustomer.setCountryCode(new StringBuffer().append("CR_").append(MainCode.generateByShuffle()).toString());
        addCustomer.setCustomerSource(WebSiteCustomerSourceEnums.OFFICIAL.getValue());
        addCustomer.setCustomerType(CustomerTypeEnums.PERSONAL.getValue());
        addCustomer.setCustomerHeadPicture(enter.getCustomerHeadPicture());
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
        addCustomer.setTelephone(enter.getTelephone());
        addCustomer.setEmail(enter.getEmail());
        addCustomer.setPurchasedScooterQty(1);
        addCustomer.setAssignationScooterQty(0);
        addCustomer.setAccountFlag(AccountFlagEnums.INACTIVATED.getValue());
        addCustomer.setCvv(enter.getCvv());
        addCustomer.setCardNum(enter.getCardNum());
        addCustomer.setSynchronizeFlag(false);
        addCustomer.setRevision(0);
        addCustomer.setCreatedBy(0L);
        addCustomer.setCreatedTime(new Date());
        addCustomer.setUpdatedBy(0L);
        addCustomer.setUpdatedTime(new Date());
        siteCustomerService.save(addCustomer);

        return addCustomer.getId();
    }
}
