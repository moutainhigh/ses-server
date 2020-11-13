package com.redescooter.ses.service.mobile.b.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.service.customer.ConsumerUserProfileService;
import com.redescooter.ses.api.hub.vo.QueryUserProfileResult;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.api.mobile.b.service.UserProfileMobileService;
import com.redescooter.ses.api.mobile.b.vo.SaveUserProfileEnter;
import com.redescooter.ses.api.mobile.b.vo.UserProfileResult;
import com.redescooter.ses.service.mobile.b.dao.base.CorUserProfileMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:UserProfileServiceImpl
 * @description: UserProfileServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 15:24
 */
@Service
public class UserProfileServiceImpl implements UserProfileMobileService {

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Reference
    private ConsumerUserProfileService consumerUserProfileService;

    @Reference
    private AccountBaseService accountBaseService;

    @Reference
    private UserProfileService userProfileService;

    @Reference
    private UserBaseService userBaseService;

    /**
     * 个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public UserProfileResult userProfile(GeneralEnter enter) {
        UserProfileResult result = new UserProfileResult();
        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(enter.getUserId());

        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType()) || queryUserResult.getUserType().equals(AccountTypeEnums.APP_EXPRESS.getAccountType())) {
            QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
            CorUserProfile toBProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
            if (toBProfile != null) {
                BeanUtils.copyProperties(toBProfile, result);
            }
        } else {
            QueryUserProfileResult toCProfile = consumerUserProfileService.queryUserProfile(idEnter);
            if (toCProfile != null) {
                BeanUtils.copyProperties(toCProfile, result);
            }
        }

        return result;
    }

    /**
     * 保存客户信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveUserProfile(SaveUserProfileEnter enter) {

        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);

        //更新2B用户信息
        if (queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType()) ||
                queryUserResult.getUserType().equals(AccountTypeEnums.APP_EXPRESS.getAccountType())) {

            CorUserProfile update = new CorUserProfile();
            update.setId(enter.getId());
            if (StringUtils.isNotEmpty(enter.getPicture())){
                update.setPicture(enter.getPicture());
            }
            if (!StringUtils.isAllBlank(enter.getCertificateType(),enter.getCertificateNegativeAnnex(),enter.getCertificatePositiveAnnex())){
                update.setCertificateType(enter.getCertificateType());
                update.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
                update.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
            }
            if (StringUtils.isAllBlank(enter.getCountryCode1(),enter.getTelNumber1())){
                update.setCountryCode1(enter.getCountryCode1());
                update.setTelNumber1(enter.getTelNumber1());
            }
            corUserProfileMapper.updateById(update);
        } else {
            // 更新2C用户信息
            SaveUserProfileHubEnter saveUserProfileHubEnter = new SaveUserProfileHubEnter();
            BeanUtils.copyProperties(enter, saveUserProfileHubEnter);
            saveUserProfileHubEnter.setId(enter.getId());
            userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
            // 回传ros 数据

        }
        return new GeneralResult(enter.getRequestId());
    }

}
