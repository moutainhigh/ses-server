package com.redescooter.ses.service.hub.source.corporate.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDeliveryTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@DS("corporate")
public interface CorDeliveryTraceMapper extends BaseMapper<CorDeliveryTrace> {
    int batchInsert(@Param("list") List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);
}