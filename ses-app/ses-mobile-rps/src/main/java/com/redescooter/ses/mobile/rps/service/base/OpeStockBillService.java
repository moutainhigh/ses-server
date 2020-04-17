package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeStockBill;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeStockBillService extends IService<OpeStockBill> {


    int updateBatch(List<OpeStockBill> list);

    int batchInsert(List<OpeStockBill> list);

    int insertOrUpdate(OpeStockBill record);

    int insertOrUpdateSelective(OpeStockBill record);

}




