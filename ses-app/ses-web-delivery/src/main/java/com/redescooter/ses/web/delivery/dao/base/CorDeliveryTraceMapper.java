package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDeliveryTraceMapper extends BaseMapper<CorDeliveryTrace> {
    int batchInsert(@Param("list") List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);
}