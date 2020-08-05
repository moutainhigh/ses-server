package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.website.ContactUsMapper;
import com.redescooter.ses.web.ros.service.base.OpeContactUsService;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.vo.customer.*;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;
import com.redescooter.ses.web.ros.vo.wms.WmsStockAvailableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void websiteContactUs(SaveInquiryEnter enter) {

    }
}
