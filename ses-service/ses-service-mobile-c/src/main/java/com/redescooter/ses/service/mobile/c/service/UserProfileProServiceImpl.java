package com.redescooter.ses.service.mobile.c.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.ScooterMobileCService;
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
    public GeneralResult saveUserPofile(SaveUserProfileEnter enter) {
        ConUserProfile userProfile = null;
        if (enter.getId() == null || enter.getId() == 0) {
            userProfile = new ConUserProfile();
            BeanUtils.copyProperties(enter, userProfile);
            userProfile.setId(idAppService.getId(SequenceName.CON_USER_PROFILE));
            userProfile.setDr(0);
            userProfile.setAddress(enter.getAddress());
            userProfile.setTenantId(enter.getTenantId());
            userProfile.setJoinDate(new Date());
            userProfile.setCreatedBy(enter.getUserId());
            userProfile.setCreatedTime(new Date());
            userProfile.setUpdatedBy(enter.getUserId());
            userProfile.setUpdatedTime(new Date());
        } else {
            //更新客户信息
            BaseCustomerEnter baseCustomerEnter = new BaseCustomerEnter();
            // 修改 个人信息
            userProfile = checkUserProfile(enter, baseCustomerEnter);
            //更新客户个人信息
            customerService.updateCustomerInfoByEmail(baseCustomerEnter);
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
    public void deleteUserProfile(String email) {
        LambdaQueryWrapper<ConUserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConUserProfile::getEmail1, email);
        ConUserProfile conUserProfile = conUserProfileMapper.selectOne(wrapper);
        if (null != conUserProfile) {
            userProfileMapper.deleteUserProfile(email);
            LambdaQueryWrapper<ConUserScooter> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ConUserScooter::getUserId, conUserProfile.getUserId());
            ConUserScooter conUserScooter = conUserScooterMapper.selectOne(lambdaQueryWrapper);
            if (null != conUserScooter) {
                userScooterMapper.deleteUserScooter(conUserProfile.getUserId());
            }
        }
    }

    /**
     * checkUserProfile
     *
     * @param enter
     */
    private ConUserProfile checkUserProfile(SaveUserProfileEnter enter, BaseCustomerEnter baseCustomerEnter) {
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
            baseCustomerEnter.setCustomerFirstName(enter.getFirstName());
            conUserProfile.setFullName((new StringBuilder().append(conUserProfile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (StringUtils.isNotBlank(enter.getLastName())) {
            conUserProfile.setLastName(enter.getLastName());
            baseCustomerEnter.setCustomerLastName(enter.getLastName());
            conUserProfile.setFullName((new StringBuilder().append(conUserProfile.getFirstName() + " " + enter.getLastName()).toString()));
        }
        if (!StringUtils.isAllBlank(enter.getCountryCode1(), enter.getTelNumber1())) {
            conUserProfile.setTelNumber1(enter.getTelNumber1());
            conUserProfile.setCountryCode1(enter.getCountryCode1());
            baseCustomerEnter.setTelephone(enter.getTelNumber1());
        }
        if (StringUtils.isNotEmpty(enter.getPicture())) {
            conUserProfile.setPicture(enter.getPicture());
        }
        if (!StringUtils.isAllBlank(enter.getCertificateType(), enter.getCertificateNegativeAnnex(), enter.getCertificatePositiveAnnex())) {
            conUserProfile.setCertificateType(enter.getCertificateType());
            conUserProfile.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
            conUserProfile.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
        }
        conUserProfile.setAddress(enter.getAddress());
        baseCustomerEnter.setEmail(conUserProfile.getEmail1());
        conUserProfile.setUpdatedBy(enter.getUserId());
        conUserProfile.setUpdatedTime(new Date());
        return conUserProfile;
    }
}
