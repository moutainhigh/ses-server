package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;

import java.util.List;

public interface OpePurchasTraceService extends IService<OpePurchasTrace> {


    int updateBatch(List<OpePurchasTrace> list);

    int batchInsert(List<OpePurchasTrace> list);

    int insertOrUpdate(OpePurchasTrace record);

    int insertOrUpdateSelective(OpePurchasTrace record);

}


