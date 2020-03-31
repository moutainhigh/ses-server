package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAssembiyOrderTraceMapper extends BaseMapper<OpeAssembiyOrderTrace> {
    int updateBatch(List<OpeAssembiyOrderTrace> list);

    int batchInsert(@Param("list") List<OpeAssembiyOrderTrace> list);

    int insertOrUpdate(OpeAssembiyOrderTrace record);

    int insertOrUpdateSelective(OpeAssembiyOrderTrace record);
}