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
        corUserProfile.setId(idAppService.getId(SequenceName.COR_USER_PROFILE));
        corUserProfile.setDr(0);
        corUserProfile.setTenantId(enter.getInputTenantId());
        corUserProfile.setUserId(enter.getInputTenantId());
        corUserProfile.setFirstName(enter.getFirstName());
        corUserProfile.setLastName(enter.getLastName());
        corUserProfile.setLastName(enter.getFirstName()+" " +enter.getLastName());
        corUserProfile.setEmail1(enter.getEmail1());
        corUserProfile.setTelNumber1(enter.getTelNumber1());
        corUserProfile.setCertificateType(enter.getCertificateType());
        corUserProfile.setCertificateNegativeAnnex(enter.getCertificateNegativeAnnex());
        corUserProfile.setCertificatePositiveAnnex(enter.getCertificatePositiveAnnex());
        corUserProfile.setJoinDate(new Date());
        corUserProfile.setCreatedBy(enter.getUserId());
        corUserProfile.setCreatedTime(new Date());
        corUserProfile.setUpdatedBy(enter.getUserId());
        corUserProfile.setUpdatedTime(new Date());
        corUserProfileMapper.insertOrUpdateSelective(corUserProfile);
        return new GeneralResult(enter.getRequestId());
    }
}
