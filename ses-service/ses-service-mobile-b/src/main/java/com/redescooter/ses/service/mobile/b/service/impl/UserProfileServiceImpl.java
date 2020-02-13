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

import java.util.Date;

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

        CorUserProfile corUserProfile = null;
        // 保存TOC 信息
        if (queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType()) ||
                queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType())) {
            if (null != enter.getId() && 0 != enter.getId()) {
                QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
                corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
                corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
                corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
                corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
                checkUserProfile(enter, corUserProfile);
            } else {
                //保存
            }
            if (corUserProfile != null) {
                corUserProfileMapper.insertOrUpdate(corUserProfile);
            }
        } else {
            // 保存、修改TO B 人信息
            SaveUserProfileHubEnter saveUserProfileHubEnter = new SaveUserProfileHubEnter();
            BeanUtils.copyProperties(enter, saveUserProfileHubEnter);
            userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * checkUserProfile
     *
     * @param enter
     * @param corUserProfile
     */
    private void checkUserProfile(SaveUserProfileEnter enter, CorUserProfile corUserProfile) {
        if (StringUtils.isNotBlank(enter.getPicture())) {
            corUserProfile.setPicture(enter.getPicture());
        }
        if (StringUtils.isNotBlank(enter.getFirstName())) {
            corUserProfile.setFirstName(enter.getFirstName());
            corUserProfile.setFullName((new StringBuilder().append(corUserProfile.getLastName() + " " + enter.getFirstName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getLastName())) {
            corUserProfile.setLastName(enter.getLastName());
            corUserProfile.setFullName((new StringBuilder().append(enter.getLastName() + " " + corUserProfile.getFirstName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getTelNumber1()) && StringUtils.isNotBlank(enter.getCountryCode1())) {
            corUserProfile.setTelNumber1(enter.getTelNumber1());
            corUserProfile.setCountryCode1(enter.getCountryCode1());
        }
        if (StringUtils.isNotBlank(enter.getCertificateType()) && StringUtils.isNotBlank(enter.getCertificateNegativeAnnex()) && StringUtils.isNotBlank(enter.getCertificatePositiveAnnex())) {
            corUserProfile.setCertificateType(enter.getCertificateType());
            corUserProfile.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
            corUserProfile.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
        }
        if (StringUtils.isNotBlank(enter.getPlaceBirth())) {
            corUserProfile.setPlaceBirth(enter.getPlaceBirth());
        }
        corUserProfile.setUpdatedBy(enter.getUserId());
        corUserProfile.setUpdatedTime(new Date());
    }

}
