package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesDeliveryOrderService extends IService<OpeSalesDeliveryOrder> {


    int updateBatch(List<OpeSalesDeliveryOrder> list);

    int batchInsert(List<OpeSalesDeliveryOrder> list);

    int insertOrUpdate(OpeSalesDeliveryOrder record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrder record);

}
