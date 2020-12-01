package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeInvoiceProductSerialNum;

import java.util.List;
public interface OpeInvoiceProductSerialNumService extends IService<OpeInvoiceProductSerialNum> {


    int updateBatch(List<OpeInvoiceProductSerialNum> list);

    int batchInsert(List<OpeInvoiceProductSerialNum> list);

    int insertOrUpdate(OpeInvoiceProductSerialNum record);

    int insertOrUpdateSelective(OpeInvoiceProductSerialNum record);

}
