package com.redescooter.ses.service.hub.source.operation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomerInquiry;

import java.util.List;

public interface OpeCustomerInquiryService extends IService<OpeCustomerInquiry> {


    int updateBatch(List<OpeCustomerInquiry> list);

    int batchInsert(List<OpeCustomerInquiry> list);

    int insertOrUpdate(OpeCustomerInquiry record);

    int insertOrUpdateSelective(OpeCustomerInquiry record);




}







