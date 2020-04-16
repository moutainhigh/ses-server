package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyQcTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAssemblyQcTrace record);

    int insertOrUpdate(OpeAssemblyQcTrace record);

    int insertOrUpdateSelective(OpeAssemblyQcTrace record);

    int insertSelective(OpeAssemblyQcTrace record);

    OpeAssemblyQcTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAssemblyQcTrace record);

    int updateByPrimaryKey(OpeAssemblyQcTrace record);

    int updateBatch(List<OpeAssemblyQcTrace> list);

    int updateBatchSelective(List<OpeAssemblyQcTrace> list);

    int batchInsert(@Param("list") List<OpeAssemblyQcTrace> list);
}