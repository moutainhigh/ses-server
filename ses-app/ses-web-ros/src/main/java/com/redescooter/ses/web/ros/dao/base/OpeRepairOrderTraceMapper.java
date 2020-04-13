package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairOrderTraceMapper extends BaseMapper<OpeRepairOrderTrace> {
    int updateBatch(List<OpeRepairOrderTrace> list);

    int batchInsert(@Param("list") List<OpeRepairOrderTrace> list);

    int insertOrUpdate(OpeRepairOrderTrace record);

    int insertOrUpdateSelective(OpeRepairOrderTrace record);
}