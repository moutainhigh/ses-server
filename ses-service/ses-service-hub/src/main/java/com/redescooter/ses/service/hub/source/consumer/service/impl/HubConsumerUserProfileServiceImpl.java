package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.country.CountryCodeEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.customer.ConsumerUserProfileService;
import com.redescooter.ses.api.hub.vo.QueryUserProfileResult;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.consumer.dao.HubConUserProfileMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserProfile;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CorporateAccountProServiceImpl
 * @description: CorporateAccountProServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:31
 */
@DS("consumer")
@DubboService
public class HubConsumerUserProfileServiceImpl implements ConsumerUserProfileService {

    @Autowired
    private HubConUserProfileMapper hubConUserProfileMapper;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @DubboReference
    private UserProfileProService userProfileProService;

    /**
     * 查询个人信息
     *
     * @param enter id为 userProfile Id
     * @return
     */
    @Override
    public QueryUserProfileResult queryUserProfile(IdEnter enter) {

        // 查询con_user_profile表
        QueryWrapper<HubConUserProfile> qw = new QueryWrapper<>();
        qw.eq(HubConUserProfile.COL_DR, Constant.DR_FALSE);
        qw.eq(HubConUserProfile.COL_USER_ID, enter.getId());
        qw.last("limit 1");
        HubConUserProfile profile = hubConUserProfileMapper.selectOne(qw);
        if (profile == null) {
            throw new SeSHubException(ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getMessage());
        }

        QueryUserProfileResult result = new QueryUserProfileResult();
        BeanUtils.copyProperties(profile, result);

        // 给countryCodeNumber字段赋值
        if (StringUtils.isNotBlank(result.getCountryCode1())) {
            boolean flag = isNumeric(result.getCountryCode1());
            if (flag) {
                result.setCountryCodeNumber(result.getCountryCode1());
            } else {
                String number = CountryCodeEnum.getValue(result.getCountryCode1());
                result.setCountryCodeNumber(number);
            }
        }

        // 查询客户信息
        QueryWrapper<OpeCustomer> lqw = new QueryWrapper();
        lqw.eq(OpeCustomer.COL_DR, Constant.DR_FALSE);
        lqw.eq(OpeCustomer.COL_EMAIL, profile.getEmail1());
        lqw.eq(OpeCustomer.COL_CUSTOMER_TYPE, CustomerTypeEnum.PERSONAL.getValue());
        lqw.eq(OpeCustomer.COL_TENANT_ID, 0);
        lqw.last("limit 1");
        OpeCustomer customer = opeCustomerService.getOne(lqw);
        if (customer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }

        result.setInvoiceNum(customer.getInvoiceNum());
        result.setInvoiceAnnex(customer.getInvoiceAnnex());
        result.setContractAnnex(customer.getContractAnnex());
        return result;
    }

    /**
     * 判断字符串是否全部为数字
     * 全部为数字 返回true
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
