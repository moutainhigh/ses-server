package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.SellsyInvoice;

import java.util.List;

public interface SellsyInvoiceService extends IService<SellsyInvoice> {


    int updateBatch(List<SellsyInvoice> list);

    int updateBatchSelective(List<SellsyInvoice> list);

    int batchInsert(List<SellsyInvoice> list);

    int insertOrUpdate(SellsyInvoice record);

    int insertOrUpdateSelective(SellsyInvoice record);

}
