package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCustomerInquiryBService extends IService<OpeCustomerInquiryB>{


    int updateBatch(List<OpeCustomerInquiryB> list);

    int batchInsert(List<OpeCustomerInquiryB> list);

    int insertOrUpdate(OpeCustomerInquiryB record);

    int insertOrUpdateSelective(OpeCustomerInquiryB record);

}
