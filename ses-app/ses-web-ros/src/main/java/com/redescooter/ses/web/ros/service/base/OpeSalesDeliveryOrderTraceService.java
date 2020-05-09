package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesDeliveryOrderTraceService extends IService<OpeSalesDeliveryOrderTrace> {


    int updateBatch(List<OpeSalesDeliveryOrderTrace> list);

    int batchInsert(List<OpeSalesDeliveryOrderTrace> list);

    int insertOrUpdate(OpeSalesDeliveryOrderTrace record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderTrace record);

}
