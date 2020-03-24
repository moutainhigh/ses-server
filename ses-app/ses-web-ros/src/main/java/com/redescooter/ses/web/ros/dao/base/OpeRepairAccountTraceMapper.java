package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairAccountTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairAccountTraceMapper extends BaseMapper<OpeRepairAccountTrace> {
    int updateBatch(List<OpeRepairAccountTrace> list);

    int batchInsert(@Param("list") List<OpeRepairAccountTrace> list);

    int insertOrUpdate(OpeRepairAccountTrace record);

    int insertOrUpdateSelective(OpeRepairAccountTrace record);
}