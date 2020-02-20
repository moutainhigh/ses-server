package com.redescooter.ses.service.mobile.c.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.EditUserProfile2CEnter;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserProfileMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import com.redescooter.ses.service.mobile.c.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.c.service.base.ConUserProfileService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:test
 * @description: test
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:57
 */
@Service
public class UserProfileProServiceImpl implements UserProfileProService {

    @Autowired
    private ConUserProfileMapper conUserProfileMapper;

    @Autowired
    private ConUserProfileService userProfileService;
    @Reference
    private IdAppService idAppService;

    /**
     * 保存个人信息
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveUserPofile(SaveUserProfileEnter enter) {
        ConUserProfile userProfile = null;
        if (enter.getId() == null || enter.getId() == 0) {
            userProfile = new ConUserProfile();
            BeanUtils.copyProperties(enter, userProfile);
            userProfile.setId(idAppService.getId(SequenceName.CON_USER_PROFILE));
            userProfile.setDr(0);
            userProfile.setTenantId(enter.getTenantId());
            userProfile.setJoinDate(new Date());
            userProfile.setCreatedBy(enter.getUserId());
            userProfile.setCreatedTime(new Date());
            userProfile.setUpdatedBy(enter.getUserId());
            userProfile.setUpdatedTime(new Date());
        } else {
            // 修改 个人信息
            userProfile = checkUserProfile(enter);
        }

        if (userProfile != null) {
            conUserProfileMapper.insertOrUpdate(userProfile);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除用户信息
     *
     * @param longs
     */
    @Override
    public GeneralResult deleteUserProfile2C(List<Long> longs) {

        QueryWrapper<ConUserProfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ConUserProfile.COL_DR,0);
        queryWrapper.in(ConUserProfile.COL_USER_ID,longs);
        List<ConUserProfile> list = userProfileService.list(queryWrapper);

        if (list.size() > 0) {
            list.forEach(userProfile -> {
                userProfileService.removeById(userProfile.getId());
            });
        }

        return new GeneralResult();
    }

    /**
     * 修改个人信息 暂时只支持名字
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult editUserProfile(EditUserProfile2CEnter enter) {
        QueryWrapper<ConUserProfile> conUserProfileQueryWrapper = new QueryWrapper<>();
        conUserProfileQueryWrapper.eq(ConUserProfile.COL_TENANT_ID, enter.getInputTenantId());
        conUserProfileQueryWrapper.eq(ConUserProfile.COL_EMAIL_1, enter.getEmail());
        conUserProfileQueryWrapper.eq(ConUserProfile.COL_DR, 0);
        ConUserProfile conUserProfile = conUserProfileMapper.selectOne(conUserProfileQueryWrapper);
        if (conUserProfile != null) {
            if (StringUtils.isNotBlank(enter.getFirstName()) && StringUtils.isNotBlank(enter.getLastName())) {
                conUserProfile.setFirstName(enter.getFirstName());
                conUserProfile.setLastName(enter.getLastName());
                conUserProfile.setFullName(new StringBuilder().append(enter.getFirstName()).append(" ").append(enter.getLastName()).toString());
            }
            conUserProfileMapper.updateById(conUserProfile);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * checkUserProfile
     *
     * @param enter
     */
    private ConUserProfile checkUserProfile(SaveUserProfileEnter enter) {
        // 查询个人信息
        ConUserProfile conUserProfile = userProfileService.getById(enter.getId());
        if (conUserProfile == null) {
            throw new MobileCException(ExceptionCodeEnums.USERPROFILE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USERPROFILE_IS_NOT_EXIST.getMessage());
        }

        if (StringUtils.isNotBlank(enter.getPicture())) {
            conUserProfile.setPicture(enter.getPicture());
        }
        if (StringUtils.isNotBlank(enter.getFirstName())) {
            conUserProfile.setFirstName(enter.getFirstName());
            conUserProfile.setFullName((new StringBuilder().append(conUserProfile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getLastName())) {
            conUserProfile.setLastName(enter.getLastName());
            conUserProfile.setFullName((new StringBuilder().append(conUserProfile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getTelNumber1()) && StringUtils.isNotBlank(enter.getCountryCode1())) {
            conUserProfile.setTelNumber1(enter.getTelNumber1());
            conUserProfile.setCountryCode1(enter.getCountryCode1());
        }
        if (StringUtils.isNotBlank(enter.getCertificateType()) && StringUtils.isNotBlank(enter.getCertificateNegativeAnnex()) && StringUtils.isNotBlank(enter.getCertificatePositiveAnnex())) {
            conUserProfile.setCertificateType(enter.getCertificateType());
            conUserProfile.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
            conUserProfile.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
        }
        conUserProfile.setUpdatedBy(enter.getUserId());
        conUserProfile.setUpdatedTime(new Date());
        return conUserProfile;
    }
}
