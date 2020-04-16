package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasQcTraceService extends IService<OpePurchasQcTrace> {


    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

}

