package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesDeliveryOrderMapper extends BaseMapper<OpeSalesDeliveryOrder> {
    int updateBatch(List<OpeSalesDeliveryOrder> list);

    int batchInsert(@Param("list") List<OpeSalesDeliveryOrder> list);

    int insertOrUpdate(OpeSalesDeliveryOrder record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrder record);
}