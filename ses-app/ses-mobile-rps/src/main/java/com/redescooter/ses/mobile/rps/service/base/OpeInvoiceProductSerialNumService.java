package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;

import java.util.List;

public interface OpeInvoiceProductSerialNumService extends IService<OpeInvoiceProductSerialNum> {


    int updateBatch(List<OpeInvoiceProductSerialNum> list);

    int batchInsert(List<OpeInvoiceProductSerialNum> list);

    int insertOrUpdate(OpeInvoiceProductSerialNum record);

    int insertOrUpdateSelective(OpeInvoiceProductSerialNum record);

}





