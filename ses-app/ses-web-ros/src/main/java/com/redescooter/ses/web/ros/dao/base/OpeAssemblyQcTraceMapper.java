package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyQcTraceMapper extends BaseMapper<OpeAssemblyQcTrace> {
    int updateBatch(List<OpeAssemblyQcTrace> list);

    int batchInsert(@Param("list") List<OpeAssemblyQcTrace> list);

    int insertOrUpdate(OpeAssemblyQcTrace record);

    int insertOrUpdateSelective(OpeAssemblyQcTrace record);
}