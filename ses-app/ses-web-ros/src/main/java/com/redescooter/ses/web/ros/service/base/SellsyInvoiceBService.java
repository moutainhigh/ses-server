package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceB;

import java.util.List;

public interface SellsyInvoiceBService extends IService<SellsyInvoiceB> {


    int updateBatch(List<SellsyInvoiceB> list);

    int updateBatchSelective(List<SellsyInvoiceB> list);

    int batchInsert(List<SellsyInvoiceB> list);

    int insertOrUpdate(SellsyInvoiceB record);

    int insertOrUpdateSelective(SellsyInvoiceB record);

}
