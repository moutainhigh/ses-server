package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;

public interface OpePurchasLotTraceService extends IService<OpePurchasLotTrace> {


    int updateBatch(List<OpePurchasLotTrace> list);

    int batchInsert(List<OpePurchasLotTrace> list);

    int insertOrUpdate(OpePurchasLotTrace record);

    int insertOrUpdateSelective(OpePurchasLotTrace record);

}

