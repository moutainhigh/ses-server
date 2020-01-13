package com.redescooter.ses.service.hub.common;
import	java.util.LinkedList;
import	java.util.Queue;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.corporate.dao.CorUserProfileMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorUserProfileService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
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
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Reference
    private UserProfileProService userProfileProService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Autowired
    private CorUserProfileService userProfileService;

    /**
     * Toc 保存个人信息
     *
     * @param enter
     */
    @Override
    public void saveUserProfile2C(SaveUserProfileHubEnter enter) {
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
        queryWrapper.eq(CorUserProfile.COL_DR,0);
        queryWrapper.in(CorUserProfile.COL_USER_ID,longs);
        List<CorUserProfile> corUserProfiles = userProfileService.list(queryWrapper);

        if (corUserProfiles.size()>0){
         corUserProfiles.forEach(corUserProfile -> {
             userProfileService.removeById(corUserProfile.getId());
         });
        }

    }
}
