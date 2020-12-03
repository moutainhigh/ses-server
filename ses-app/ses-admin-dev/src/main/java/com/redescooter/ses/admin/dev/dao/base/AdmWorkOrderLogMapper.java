package com.redescooter.ses.admin.dev.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmWorkOrderLogMapper extends BaseMapper<AdmWorkOrderLog> {
    int updateBatch(List<AdmWorkOrderLog> list);

    int updateBatchSelective(List<AdmWorkOrderLog> list);

    int batchInsert(@Param("list") List<AdmWorkOrderLog> list);

    int insertOrUpdate(AdmWorkOrderLog record);

    int insertOrUpdateSelective(AdmWorkOrderLog record);
}