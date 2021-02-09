package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

        QueryWrapper<HubConUserProfile> conUserProfileQueryWrapper = new QueryWrapper<>();
        conUserProfileQueryWrapper.eq(HubConUserProfile.COL_USER_ID, enter.getId());
        conUserProfileQueryWrapper.eq(HubConUserProfile.COL_DR, 0);
        HubConUserProfile hubConUserProfile = hubConUserProfileMapper.selectOne(conUserProfileQueryWrapper);
        if (hubConUserProfile == null) {
            throw new SeSHubException(ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getMessage());
        }
        QueryUserProfileResult queryUserProfileResult = new QueryUserProfileResult();
        BeanUtils.copyProperties(hubConUserProfile, queryUserProfileResult);

        // 查询客户信息
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_DR, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_EMAIL, hubConUserProfile.getEmail1());
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_CUSTOMER_TYPE, CustomerTypeEnum.PERSONAL.getValue());
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_TENANT_ID, 0);
        OpeCustomer opeCustomer = opeCustomerService.getOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        queryUserProfileResult.setInvoiceNum(opeCustomer.getInvoiceNum());
        queryUserProfileResult.setInvoiceAnnex(opeCustomer.getInvoiceAnnex());
        queryUserProfileResult.setContractAnnex(opeCustomer.getContractAnnex());
        return queryUserProfileResult;
    }
}
