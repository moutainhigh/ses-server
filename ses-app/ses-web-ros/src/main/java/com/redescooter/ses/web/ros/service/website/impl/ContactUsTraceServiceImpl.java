package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.ContactUsTraceMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUsEntity;
import com.redescooter.ses.web.ros.dm.OpeContactUsTraceEntity;
import com.redescooter.ses.web.ros.service.base.OpeContactUsTraceService;
import com.redescooter.ses.web.ros.service.website.ContactUsTraceService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassNameContactUsTraceServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:08
 * @Version V1.0
 **/
@Service
public class ContactUsTraceServiceImpl implements ContactUsTraceService {

    @Autowired
    private OpeContactUsTraceService opeContactUsTraceService;

    @Autowired
    private ContactUsTraceMapper contactUsTraceMapper;

    @Reference
    private IdAppService idAppService;

    @Override
    public void createContactUsTrace(OpeContactUsEntity contactUsEntity) {
        OpeContactUsTraceEntity opeContactUsTraceEntity = new OpeContactUsTraceEntity();
        opeContactUsTraceEntity.setId(idAppService.getId(SequenceName.OPE_CONTACT_US_TRACE));
        opeContactUsTraceEntity.setCreatedBy(0L);
        opeContactUsTraceEntity.setCreatedTime(new Date());
        opeContactUsTraceEntity.setUpdatedBy(0L);
        opeContactUsTraceEntity.setUpdatedTime(new Date());
        opeContactUsTraceEntity.setContactUsId(contactUsEntity.getId());
        opeContactUsTraceEntity.setFirstName(contactUsEntity.getFirstName());
        opeContactUsTraceEntity.setLastName(contactUsEntity.getLastName());
        opeContactUsTraceEntity.setFullName(contactUsEntity.getFullName());
        opeContactUsTraceEntity.setTelephone(contactUsEntity.getTelephone());
        opeContactUsTraceEntity.setCountryName(contactUsEntity.getCountryName());
        opeContactUsTraceEntity.setCityName(contactUsEntity.getCityName());
        opeContactUsTraceEntity.setDistrictName(contactUsEntity.getDistrictName());
        opeContactUsTraceEntity.setAddress(contactUsEntity.getAddress());
        opeContactUsTraceEntity.setRemark(contactUsEntity.getRemark());
        opeContactUsTraceService.saveOrUpdate(opeContactUsTraceEntity);
    }
}
