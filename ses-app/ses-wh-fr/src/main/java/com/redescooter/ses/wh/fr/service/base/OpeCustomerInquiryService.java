package com.redescooter.ses.wh.fr.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.wh.fr.dm.OpeCustomerInquiry;

import java.util.List;

public interface OpeCustomerInquiryService extends IService<OpeCustomerInquiry> {


    int updateBatch(List<OpeCustomerInquiry> list);

    int batchInsert(List<OpeCustomerInquiry> list);

    int insertOrUpdate(OpeCustomerInquiry record);

    int insertOrUpdateSelective(OpeCustomerInquiry record);

}







