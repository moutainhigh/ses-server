package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaWorkOrderLogMapper extends BaseMapper<PlaWorkOrderLog> {
    int updateBatch(List<PlaWorkOrderLog> list);

    int updateBatchSelective(List<PlaWorkOrderLog> list);

    int batchInsert(@Param("list") List<PlaWorkOrderLog> list);

    int insertOrUpdate(PlaWorkOrderLog record);

    int insertOrUpdateSelective(PlaWorkOrderLog record);
}