package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.SellsyInvoiceTotal;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface SellsyInvoiceTotalService extends IService<SellsyInvoiceTotal> {


    int updateBatch(List<SellsyInvoiceTotal> list);

    int batchInsert(List<SellsyInvoiceTotal> list);

    int insertOrUpdate(SellsyInvoiceTotal record);

    int insertOrUpdateSelective(SellsyInvoiceTotal record);

}





