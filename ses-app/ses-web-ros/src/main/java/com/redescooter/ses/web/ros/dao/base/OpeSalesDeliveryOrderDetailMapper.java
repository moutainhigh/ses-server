package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesDeliveryOrderDetailMapper extends BaseMapper<OpeSalesDeliveryOrderDetail> {
    int updateBatch(List<OpeSalesDeliveryOrderDetail> list);

    int batchInsert(@Param("list") List<OpeSalesDeliveryOrderDetail> list);

    int insertOrUpdate(OpeSalesDeliveryOrderDetail record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderDetail record);
}