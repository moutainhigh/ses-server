package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;

import java.util.List;
public interface CorExpressOrderTraceService extends IService<CorExpressOrderTrace> {


    int updateBatch(List<CorExpressOrderTrace> list);

    int updateBatchSelective(List<CorExpressOrderTrace> list);

    int batchInsert(List<CorExpressOrderTrace> list);

    int insertOrUpdate(CorExpressOrderTrace record);

    int insertOrUpdateSelective(CorExpressOrderTrace record);
}




