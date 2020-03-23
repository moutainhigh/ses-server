package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpePurchasBQcTrace;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasBQcTraceService extends IService<OpePurchasBQcTrace> {


    int updateBatch(List<OpePurchasBQcTrace> list);

    int batchInsert(List<OpePurchasBQcTrace> list);

    int insertOrUpdate(OpePurchasBQcTrace record);

    int insertOrUpdateSelective(OpePurchasBQcTrace record);

}
