package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFrStockBill;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeFrStockBillService extends IService<OpeFrStockBill> {


    int updateBatch(List<OpeFrStockBill> list);

    int batchInsert(List<OpeFrStockBill> list);

    int insertOrUpdate(OpeFrStockBill record);

    int insertOrUpdateSelective(OpeFrStockBill record);

}

