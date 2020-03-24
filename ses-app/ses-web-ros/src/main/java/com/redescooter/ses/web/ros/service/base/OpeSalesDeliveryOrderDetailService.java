package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderDetail;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesDeliveryOrderDetailService extends IService<OpeSalesDeliveryOrderDetail> {


    int updateBatch(List<OpeSalesDeliveryOrderDetail> list);

    int batchInsert(List<OpeSalesDeliveryOrderDetail> list);

    int insertOrUpdate(OpeSalesDeliveryOrderDetail record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderDetail record);

}
