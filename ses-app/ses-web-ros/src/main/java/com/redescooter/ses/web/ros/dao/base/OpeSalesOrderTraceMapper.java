package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesOrderTraceMapper extends BaseMapper<OpeSalesOrderTrace> {
    int updateBatch(List<OpeSalesOrderTrace> list);

    int batchInsert(@Param("list") List<OpeSalesOrderTrace> list);

    int insertOrUpdate(OpeSalesOrderTrace record);

    int insertOrUpdateSelective(OpeSalesOrderTrace record);
}