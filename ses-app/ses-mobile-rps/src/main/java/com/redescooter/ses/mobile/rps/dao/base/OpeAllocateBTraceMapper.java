package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateBTraceMapper extends BaseMapper<OpeAllocateBTrace> {
    int updateBatch(List<OpeAllocateBTrace> list);

    int batchInsert(@Param("list") List<OpeAllocateBTrace> list);

    int insertOrUpdate(OpeAllocateBTrace record);

    int insertOrUpdateSelective(OpeAllocateBTrace record);
}