package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.ContactUsMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUsEntity;
import com.redescooter.ses.web.ros.service.base.OpeContactUsService;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.service.website.ContactUsTraceService;
import com.redescooter.ses.web.ros.vo.customer.*;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassNameContactUsServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:07
 * @Version V1.0
 **/
@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    private OpeContactUsService opeContactUsService;

    @Autowired
    private ContactUsMapper contactUsMapper;

    @Reference
    private CityBaseService cityBaseService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private  ContactUsTraceService contactUsTraceService;


    @Override
    public PageResult<ContactUsListResult> list(ContactUsListEnter enter) {
        ;
        if (enter.getKeyWord() != null && enter.getKeyWord().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        int totalRows = contactUsMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ContactUsListResult> list = contactUsMapper.list(enter);
        return PageResult.create(enter, totalRows, list);
    }

    @Override
    public List<ContactUsDetailResult> detail(ContactUsEnter enter) {
        return contactUsMapper.detailList(enter);
    }

    @Override
    public List<ContactUsHistoryResult> trace(ContactUsEnter enter) {
        return contactUsMapper.historyList(enter);
    }

    @Override
    @Transactional
    public void websiteContactUs(SaveInquiryEnter enter) {
        // 先看这个邮箱是否已存在
        QueryWrapper<OpeContactUsEntity> qw = new QueryWrapper<>();
        qw.eq(OpeContactUsEntity.COL_EMAIL,enter.getEmail());
        qw.last("limit 1");
        OpeContactUsEntity  contactUsEntity = opeContactUsService.getOne(qw);
        contactUsEntity = createContactUsEntity(enter,contactUsEntity);
        // 再处理联系我们的历史记录
        contactUsTraceService.createContactUsTrace(contactUsEntity);
    }


    public OpeContactUsEntity createContactUsEntity(SaveInquiryEnter enter,OpeContactUsEntity contactUsEntity){
        if(contactUsEntity != null){
            // 说明这个邮箱已经存在
            contactUsEntity.setFrequency(contactUsEntity.getFrequency() + 1);
        }else {
            // 说明这个邮箱是第一次联系我们
            contactUsEntity = new OpeContactUsEntity();
            contactUsEntity.setId(idAppService.getId(SequenceName.OPE_CONTACT_US));
            contactUsEntity.setEmail(enter.getEmail());
            contactUsEntity.setFrequency(1);
            contactUsEntity.setCreatedTime(new Date());
        }
        contactUsEntity.setFirstName(enter.getFirstName());
        contactUsEntity.setLastName(enter.getLastName());
        contactUsEntity.setFullName(contactUsEntity.getFirstName()+" "+contactUsEntity.getLastName());
        contactUsEntity.setTelephone(enter.getTelephone());
        contactUsEntity.setCountry(enter.getCountryId());
        contactUsEntity.setCountryName(enter.getCustomerCountry());
        contactUsEntity.setCityName(enter.getCity());
        contactUsEntity.setDistrictName(enter.getDistrust());
        contactUsEntity.setDistrict(cityBaseService.getDistrictId(contactUsEntity.getCityName(),contactUsEntity.getDistrictName()));
        contactUsEntity.setAddress(enter.getAddress());
        contactUsEntity.setRemark(enter.getRemark());
        contactUsEntity.setUpdatedTime(new Date());
        opeContactUsService.saveOrUpdate(contactUsEntity);
        return contactUsEntity;
    }

}
