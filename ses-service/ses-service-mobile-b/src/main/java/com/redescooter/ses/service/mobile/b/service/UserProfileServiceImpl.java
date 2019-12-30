package com.redescooter.ses.service.mobile.b.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.b.service.UserProfileMobileService;
import com.redescooter.ses.api.mobile.b.vo.SaveUserProfileEnter;
import com.redescooter.ses.api.mobile.b.vo.UserProfileResult;
import com.redescooter.ses.service.mobile.b.dao.base.CorUserProfileMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public UserProfileResult userProfile(GeneralEnter enter) {
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);

        UserProfileResult result = new UserProfileResult();
        BeanUtils.copyProperties(corUserProfile, result);
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
        CorUserProfile corUserProfile = null;
        if (enter.getId() != null || enter.getId() != 0) {
            QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
            corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
            corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
            checkUserProfile(enter, corUserProfile);
        } else {
            //保存
        }

        corUserProfileMapper.insertOrUpdate(corUserProfile);
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
