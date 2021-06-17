package com.redescooter.ses.service.mobile.c.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.EditUserProfile2CEnter;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dao.UserProfileMapper;
import com.redescooter.ses.service.mobile.c.dao.UserScooterMapper;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserProfileMapper;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserScooterMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import com.redescooter.ses.service.mobile.c.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.c.service.base.ConUserProfileService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:test
 * @description: test
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:57
 */
@DubboService
public class UserProfileProServiceImpl implements UserProfileProService {

    @Autowired
    private ConUserProfileMapper conUserProfileMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserScooterMapper userScooterMapper;

    @Autowired
    private ConUserProfileService userProfileService;

    @Autowired
    private ConUserScooterMapper conUserScooterMapper;

    @DubboReference
    private CustomerService customerService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 保存个人信息
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveUserProfile(SaveUserProfileEnter enter) {
        ConUserProfile userProfile = null;
        if (enter.getId() == null || enter.getId() == 0) {
            // 新增con_user_profile
            userProfile = new ConUserProfile();
            BeanUtils.copyProperties(enter, userProfile);
            userProfile.setId(idAppService.getId(SequenceName.CON_USER_PROFILE));
            userProfile.setDr(Constant.DR_FALSE);
            userProfile.setAddress(enter.getAddress());
            userProfile.setTenantId(enter.getTenantId());
            userProfile.setJoinDate(new Date());
            userProfile.setCreatedBy(enter.getUserId());
            userProfile.setCreatedTime(new Date());
            userProfile.setUpdatedBy(enter.getUserId());
            userProfile.setUpdatedTime(new Date());
            userProfileService.save(userProfile);
        } else {
            // 更新客户信息
            BaseCustomerEnter customerEnter = new BaseCustomerEnter();
            // 修改 个人信息
            userProfile = checkUserProfile(enter, customerEnter);

            // 修改con_user_profile
            ConUserProfile model = new ConUserProfile();
            model.setId(enter.getId());
            if (StringUtils.isNotBlank(enter.getFirstName())) {
                model.setFirstName(enter.getFirstName());
            }
            if (StringUtils.isNotBlank(enter.getLastName())) {
                model.setLastName(enter.getLastName());
            }
            if (StringUtils.isNotBlank(enter.getFullName())) {
                model.setFullName(enter.getFullName());
            }
            if (StringUtils.isNotBlank(enter.getPicture())) {
                model.setPicture(enter.getPicture());
            }
            if (StringUtils.isNotBlank(enter.getEmail1())) {
                model.setEmail1(enter.getEmail1());
            }
            if (StringUtils.isNotBlank(enter.getTelNumber1())) {
                model.setTelNumber1(enter.getTelNumber1());
            }
            if (StringUtils.isNotBlank(enter.getCertificateType())) {
                model.setCertificateType(enter.getCertificateType());
            }
            if (StringUtils.isNotBlank(enter.getCertificateNegativeAnnex())) {
                model.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
            }
            if (StringUtils.isNotBlank(enter.getCertificatePositiveAnnex())) {
                model.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
            }
            if (StringUtils.isNotBlank(enter.getAddress())) {
                model.setAddress(enter.getAddress());
            }
            model.setUpdatedBy(enter.getUserId());
            model.setUpdatedTime(new Date());
            userProfileService.updateById(model);

            // 更新ope_customer
            customerService.updateCustomerInfoByEmail(customerEnter);
        }

        /*if (userProfile != null) {
            conUserProfileMapper.insertOrUpdate(userProfile);
        }*/
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
        queryWrapper.eq(ConUserProfile.COL_DR, 0);
        queryWrapper.in(ConUserProfile.COL_USER_ID, longs);
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
    @GlobalTransactional(rollbackFor = Exception.class)
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
            if (StringUtils.isNotBlank(enter.getTelNumber1())) {
                conUserProfile.setTelNumber1(enter.getTelNumber1());
            }
            conUserProfileMapper.updateById(conUserProfile);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteUserProfile(String email) {
        LambdaQueryWrapper<ConUserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConUserProfile::getEmail1, email);
        List<ConUserProfile> list = conUserProfileMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ConUserProfile profile : list) {
                userProfileMapper.deleteUserProfile(profile.getEmail1());

                LambdaQueryWrapper<ConUserScooter> qw = new LambdaQueryWrapper<>();
                qw.eq(ConUserScooter::getUserId, profile.getUserId());
                List<ConUserScooter> userScooterList = conUserScooterMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(userScooterList)) {
                    for (ConUserScooter userScooter : userScooterList) {
                        userScooterMapper.deleteUserScooter(userScooter.getUserId());
                    }
                }
            }
        }
    }

    /**
     * checkUserProfile
     *
     * @param enter
     */
    private ConUserProfile checkUserProfile(SaveUserProfileEnter enter, BaseCustomerEnter customerEnter) {
        // 查询个人信息
        ConUserProfile profile = userProfileService.getById(enter.getId());
        if (profile == null) {
            throw new MobileCException(ExceptionCodeEnums.USERPROFILE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USERPROFILE_IS_NOT_EXIST.getMessage());
        }

        if (StringUtils.isNotBlank(enter.getPicture())) {
            profile.setPicture(enter.getPicture());
        }
        if (StringUtils.isNotBlank(enter.getFirstName())) {
            profile.setFirstName(enter.getFirstName());
            customerEnter.setCustomerFirstName(enter.getFirstName());
            profile.setFullName((new StringBuilder().append(profile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getLastName())) {
            profile.setLastName(enter.getLastName());
            customerEnter.setCustomerLastName(enter.getLastName());
            profile.setFullName((new StringBuilder().append(profile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (!StringUtils.isAllBlank(enter.getCountryCode1(), enter.getTelNumber1())) {
            profile.setTelNumber1(enter.getTelNumber1());
            profile.setCountryCode1(enter.getCountryCode1());
            customerEnter.setTelephone(enter.getTelNumber1());
        }
        if (StringUtils.isNotEmpty(enter.getPicture())) {
            profile.setPicture(enter.getPicture());
        }
        if (!StringUtils.isAllBlank(enter.getCertificateType(), enter.getCertificateNegativeAnnex(), enter.getCertificatePositiveAnnex())) {
            profile.setCertificateType(enter.getCertificateType());
            profile.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
            profile.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
        }
        profile.setAddress(enter.getAddress());
        customerEnter.setEmail(profile.getEmail1());
        profile.setUpdatedBy(enter.getUserId());
        profile.setUpdatedTime(new Date());
        return profile;
    }
}
