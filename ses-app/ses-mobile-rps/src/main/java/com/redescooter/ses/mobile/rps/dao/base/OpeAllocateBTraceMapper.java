package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateBTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAllocateBTrace record);

    int insertOrUpdate(OpeAllocateBTrace record);

    int insertOrUpdateSelective(OpeAllocateBTrace record);

    int insertSelective(OpeAllocateBTrace record);

    OpeAllocateBTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAllocateBTrace record);

    int updateByPrimaryKey(OpeAllocateBTrace record);

    int updateBatch(List<OpeAllocateBTrace> list);

    int updateBatchSelective(List<OpeAllocateBTrace> list);

    int batchInsert(@Param("list") List<OpeAllocateBTrace> list);
}