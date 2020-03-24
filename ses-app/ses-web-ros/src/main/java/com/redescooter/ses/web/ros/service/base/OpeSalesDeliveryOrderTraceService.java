package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesDeliveryOrderTraceService extends IService<OpeSalesDeliveryOrderTrace> {


    int updateBatch(List<OpeSalesDeliveryOrderTrace> list);

    int batchInsert(List<OpeSalesDeliveryOrderTrace> list);

    int insertOrUpdate(OpeSalesDeliveryOrderTrace record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderTrace record);

}
