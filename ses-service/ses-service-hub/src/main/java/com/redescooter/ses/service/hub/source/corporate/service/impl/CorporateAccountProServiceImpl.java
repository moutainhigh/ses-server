package com.redescooter.ses.service.hub.source.corporate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.corporate.CorporateAccountProService;
import com.redescooter.ses.api.hub.vo.UserProfileHubEnter;
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
 * @ClassName:CorporateAccountProServiceImpl
 * @description: CorporateAccountProServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:31
 */
@DS("corporate")
@Service
public class CorporateAccountProServiceImpl implements CorporateAccountProService {

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Reference
    private IdAppService idAppService;
    /**
     * 保存个人信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveUserProfileHub(UserProfileHubEnter enter) {
        CorUserProfile corUserProfile = new CorUserProfile();
        BeanUtils.copyProperties(enter,corUserProfile);
        corUserProfile.setId(idAppService.getId(SequenceName.COR_USER_PROFILE));
        corUserProfile.setJoinDate(new Date());
        corUserProfile.setCreatedBy(enter.getUserId());
        corUserProfile.setCreatedTime(new Date());
        corUserProfile.setUpdatedBy(enter.getUserId());
        corUserProfile.setUpdatedTime(new Date());
        corUserProfileMapper.insert(corUserProfile);
        return new GeneralResult(enter.getRequestId());
    }
}
