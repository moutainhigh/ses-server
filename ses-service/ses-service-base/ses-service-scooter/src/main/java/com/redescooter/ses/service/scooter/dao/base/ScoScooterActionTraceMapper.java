package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import org.apache.ibatis.annotations.Param;

public interface ScoScooterActionTraceMapper extends BaseMapper<ScoScooterActionTrace> {
    int updateBatch(List<ScoScooterActionTrace> list);

    int batchInsert(@Param("list") List<ScoScooterActionTrace> list);

    int insertOrUpdate(ScoScooterActionTrace record);

    int insertOrUpdateSelective(ScoScooterActionTrace record);
}