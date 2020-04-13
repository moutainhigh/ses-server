package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesDeliveryOrderService extends IService<OpeSalesDeliveryOrder> {


    int updateBatch(List<OpeSalesDeliveryOrder> list);

    int batchInsert(List<OpeSalesDeliveryOrder> list);

    int insertOrUpdate(OpeSalesDeliveryOrder record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrder record);

}
