package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeStockPurchasService extends IService<OpeStockPurchas> {


    int updateBatch(List<OpeStockPurchas> list);

    int batchInsert(List<OpeStockPurchas> list);

    int insertOrUpdate(OpeStockPurchas record);

    int insertOrUpdateSelective(OpeStockPurchas record);

    int updateBatchSelective(List<OpeStockPurchas> list);
}






