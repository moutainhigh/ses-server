package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeInvoiceOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInvoiceOrderService extends IService<OpeInvoiceOrder> {


    int updateBatch(List<OpeInvoiceOrder> list);

    int batchInsert(List<OpeInvoiceOrder> list);

    int insertOrUpdate(OpeInvoiceOrder record);

    int insertOrUpdateSelective(OpeInvoiceOrder record);

}
