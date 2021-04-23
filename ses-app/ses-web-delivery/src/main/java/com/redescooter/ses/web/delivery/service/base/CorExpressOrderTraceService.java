package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;

import java.util.List;

public interface CorExpressOrderTraceService extends IService<CorExpressOrderTrace> {


    int batchInsert(List<CorExpressOrderTrace> list);

    int insertOrUpdate(CorExpressOrderTrace record);

    int insertOrUpdateSelective(CorExpressOrderTrace record);

}
