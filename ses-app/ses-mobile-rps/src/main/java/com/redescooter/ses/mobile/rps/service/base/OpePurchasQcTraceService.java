package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;

import java.util.List;

public interface OpePurchasQcTraceService extends IService<OpePurchasQcTrace> {


    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

    int updateBatchSelective(List<OpePurchasQcTrace> list);
}





















