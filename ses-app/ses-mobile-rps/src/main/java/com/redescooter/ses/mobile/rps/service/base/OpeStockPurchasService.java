package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeStockPurchasService extends IService<OpeStockPurchas> {


    int updateBatch(List<OpeStockPurchas> list);

    int batchInsert(List<OpeStockPurchas> list);

    int insertOrUpdate(OpeStockPurchas record);

    int insertOrUpdateSelective(OpeStockPurchas record);

}

