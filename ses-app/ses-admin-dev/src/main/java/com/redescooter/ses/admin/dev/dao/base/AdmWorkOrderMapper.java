package com.redescooter.ses.admin.dev.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.admin.dev.dm.AdmWorkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmWorkOrderMapper extends BaseMapper<AdmWorkOrder> {
    int updateBatch(List<AdmWorkOrder> list);

    int updateBatchSelective(List<AdmWorkOrder> list);

    int batchInsert(@Param("list") List<AdmWorkOrder> list);

    int insertOrUpdate(AdmWorkOrder record);

    int insertOrUpdateSelective(AdmWorkOrder record);
}