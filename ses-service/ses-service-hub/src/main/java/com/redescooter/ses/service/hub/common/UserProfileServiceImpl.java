package com.redescooter.ses.service.hub.common;

import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.corporate.dao.CorUserProfileMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile;
import com.redescooter.ses.starter.common.service.IdAppService;
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
 * @create: 2019/12/26 15:58
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Reference
    private UserProfileProService userProfileProService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

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
        userProfile.setJoinDate(new Date());
        userProfile.setCreatedBy(enter.getUserId());
        userProfile.setCreatedTime(new Date());
        userProfile.setUpdatedBy(enter.getUserId());
        userProfile.setUpdatedTime(new Date());
        corUserProfileMapper.insert(userProfile);
    }
}
