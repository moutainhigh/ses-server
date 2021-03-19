package com.redescooter.ses.service.hub.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.vo.EditUserProfileEnter;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailEnter;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailResult;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileBService;
import com.redescooter.ses.api.mobile.c.service.ScooterMobileCService;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.EditUserProfile2CEnter;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.corporate.dao.CorUserProfileMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorUserProfileService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:UserProfileServiceImpl
 * @description: UserProfileServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 15:58
 */
@Slf4j
@DubboService
public class UserProfileServiceImpl implements UserProfileService {

    @DubboReference
    private UserProfileProService userProfileProService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Autowired
    private CorUserProfileService userProfileService;

    @DubboReference
    private UserBaseService userBaseService;

    @DubboReference
    private ScooterMobileBService scooterMobileBService;

    @DubboReference
    private ScooterMobileCService scooterMobileCService;

    /**
     * Toc 保存个人信息
     *
     * @param enter
     */
    @Override
    public void saveUserProfile2C(SaveUserProfileHubEnter enter) {
        //TOC 修改个人信息
        SaveUserProfileEnter saveUserProfileEnter = new SaveUserProfileEnter();
        BeanUtils.copyProperties(enter, saveUserProfileEnter);
        userProfileProService.saveUserPofile(saveUserProfileEnter);
    }

    /**
     * Tob 保存个人信息
     *
     * @param enter
     */
    @Override
    public void saveUserProfile2B(SaveUserProfileHubEnter enter) {
        CorUserProfile userProfile = new CorUserProfile();
        BeanUtils.copyProperties(enter, userProfile);
        userProfile.setId(idAppService.getId(SequenceName.COR_USER_PROFILE));
        userProfile.setTenantId(enter.getTenantId());
        userProfile.setJoinDate(new Date());
        userProfile.setCreatedBy(enter.getUserId());
        userProfile.setCreatedTime(new Date());
        userProfile.setUpdatedBy(enter.getUserId());
        userProfile.setUpdatedTime(new Date());
        userProfileService.save(userProfile);
    }

    /**
     * Toc 删除个人信息
     *
     * @param longs
     */
    @Override
    public void deleteUserProfile2C(List<Long> longs) {

    userProfileProService.deleteUserProfile2C(longs);

    }

    /**
     * Tob 删除个人信息
     *
     * @param longs
     */
    @Override
    public void deleteUserProfile2B(List<Long> longs) {

        QueryWrapper<CorUserProfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CorUserProfile.COL_DR, 0);
        queryWrapper.in(CorUserProfile.COL_USER_ID, longs);
        List<CorUserProfile> corUserProfiles = userProfileService.list(queryWrapper);

        if (corUserProfiles.size() > 0) {
            corUserProfiles.forEach(corUserProfile -> {
                userProfileService.removeById(corUserProfile.getId());
            });
        }

    }

    /**
     * 修改个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult editUserProfile2B(EditUserProfileEnter enter) {
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getInputTenantId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_EMAIL_1, enter.getEmail());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.last("limit 1");
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile != null) {
            corUserProfile.setFirstName(enter.getFirstName());
            corUserProfile.setLastName(enter.getLastName());
            corUserProfile.setFullName(new StringBuilder().append(enter.getFirstName()).append(" ").append(enter.getLastName()).toString());
            userProfileService.updateById(corUserProfile);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 修改个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult editUserProfile2C(EditUserProfileEnter enter) {
        EditUserProfile2CEnter editUserProfile = new EditUserProfile2CEnter();
        BeanUtils.copyProperties(enter, editUserProfile);
        userProfileProService.editUserProfile(editUserProfile);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取用户信息cor
     * @param enter
     * @return
     */

    @Override
    public List<QueryUserProfileByEmailResult> getUserPicture(QueryUserProfileByEmailEnter enter) {
        List<QueryUserProfileByEmailResult> queryUserProfileByEmailResults = corUserProfileMapper.QueryUserProfileByEmail(enter);

        if (CollectionUtils.isEmpty(queryUserProfileByEmailResults)){
            throw new SeSHubException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return queryUserProfileByEmailResults;
    }

    /**
     * 用户退出app时,通过emq给车发送一个关锁的指令
     */
    @Override
    public GeneralResult sendLockInstructionByEMQ(GeneralEnter enter) {
        GeneralResult result = null;
        // 根据用户类型判断是2C还是2B用户
        int userServiceType;
        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (null == queryUserResult) {
            throw new SeSHubException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (AccountTypeEnums.APP_EXPRESS.getAccountType().equals(queryUserResult.getUserType())) {
            //2B -- APP
            userServiceType = 1;
        } else if (AccountTypeEnums.APP_RESTAURANT.getAccountType().equals(queryUserResult.getUserType())) {
            //2B -- APP
            userServiceType = 1;
        } else if (AccountTypeEnums.APP_PERSONAL.getAccountType().equals(queryUserResult.getUserType())) {
            //2C --APP
            userServiceType = 2;
        } else {
            //维修端
            userServiceType = 3;
        }

        ScooterLockDTO lockParam = new ScooterLockDTO();
        BeanUtils.copyProperties(enter, lockParam);
        lockParam.setEvent(CommonEvent.START.getValue());
        lockParam.setLat("0");
        lockParam.setLng("0");

        // 业务分发
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            result = scooterMobileBService.lock(lockParam);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            result = scooterMobileCService.lock(lockParam);
        }
        return result;
    }

}
