package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaWorkOrderMapper extends BaseMapper<PlaWorkOrder> {
    int updateBatch(List<PlaWorkOrder> list);

    int updateBatchSelective(List<PlaWorkOrder> list);

    int batchInsert(@Param("list") List<PlaWorkOrder> list);

    int insertOrUpdate(PlaWorkOrder record);

    int insertOrUpdateSelective(PlaWorkOrder record);
}