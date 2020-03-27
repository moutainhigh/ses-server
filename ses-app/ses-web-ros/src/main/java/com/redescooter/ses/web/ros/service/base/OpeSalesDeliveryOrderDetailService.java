package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesDeliveryOrderDetailService extends IService<OpeSalesDeliveryOrderDetail> {


    int updateBatch(List<OpeSalesDeliveryOrderDetail> list);

    int batchInsert(List<OpeSalesDeliveryOrderDetail> list);

    int insertOrUpdate(OpeSalesDeliveryOrderDetail record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderDetail record);

}
