package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesDeliveryOrderTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesDeliveryOrderTraceMapper extends BaseMapper<OpeSalesDeliveryOrderTrace> {
    int updateBatch(List<OpeSalesDeliveryOrderTrace> list);

    int batchInsert(@Param("list") List<OpeSalesDeliveryOrderTrace> list);

    int insertOrUpdate(OpeSalesDeliveryOrderTrace record);

    int insertOrUpdateSelective(OpeSalesDeliveryOrderTrace record);
}