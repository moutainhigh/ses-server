package com.redescooter.ses.service.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.mobile.c.vo.SaveUserProfileEnter;
import com.redescooter.ses.service.mobile.c.constant.SequenceName;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserProfileMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

    @Reference
    private IdAppService idAppService;

    /**
     * 保存个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveUserPofile(SaveUserProfileEnter enter) {
        ConUserProfile userProfile=new ConUserProfile();
        BeanUtils.copyProperties(enter,userProfile);
        userProfile.setId(idAppService.getId(SequenceName.CON_USER_PROFILE));
        userProfile.setJoinDate(new Date());
        userProfile.setCreatedBy(enter.getUserId());
        userProfile.setCreatedTime(new Date());
        userProfile.setUpdatedBy(enter.getUserId());
        userProfile.setUpdatedTime(new Date());
        conUserProfileMapper.insert(userProfile);
        return new GeneralResult(enter.getRequestId());
    }
}
