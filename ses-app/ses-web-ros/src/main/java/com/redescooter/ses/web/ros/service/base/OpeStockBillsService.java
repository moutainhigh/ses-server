package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeStockBills;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeStockBillsService extends IService<OpeStockBills> {


    int updateBatch(List<OpeStockBills> list);

    int batchInsert(List<OpeStockBills> list);

    int insertOrUpdate(OpeStockBills record);

    int insertOrUpdateSelective(OpeStockBills record);

}
