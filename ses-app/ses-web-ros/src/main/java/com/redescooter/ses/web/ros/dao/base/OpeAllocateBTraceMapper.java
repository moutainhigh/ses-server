package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateBTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateBTraceMapper extends BaseMapper<OpeAllocateBTrace> {
    int updateBatch(List<OpeAllocateBTrace> list);

    int batchInsert(@Param("list") List<OpeAllocateBTrace> list);

    int insertOrUpdate(OpeAllocateBTrace record);

    int insertOrUpdateSelective(OpeAllocateBTrace record);
}