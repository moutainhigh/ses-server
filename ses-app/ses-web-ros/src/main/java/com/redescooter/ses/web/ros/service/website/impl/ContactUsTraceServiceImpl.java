package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.web.ros.dao.website.ContactUsTraceMapper;
import com.redescooter.ses.web.ros.service.website.ContactUsTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ContactUsTraceService contactUsTraceService;

    @Autowired
    private ContactUsTraceMapper contactUsTraceMapper;


}
