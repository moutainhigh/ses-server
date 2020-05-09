package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderTraceMapper extends BaseMapper<CorExpressOrderTrace> {
    int updateBatch(List<CorExpressOrderTrace> list);

    int batchInsert(@Param("list") List<CorExpressOrderTrace> list);

    int insertOrUpdate(CorExpressOrderTrace record);

    int insertOrUpdateSelective(CorExpressOrderTrace record);
}