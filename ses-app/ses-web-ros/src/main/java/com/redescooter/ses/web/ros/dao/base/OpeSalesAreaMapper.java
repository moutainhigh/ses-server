package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesArea;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesAreaMapper extends BaseMapper<OpeSalesArea> {
    int updateBatch(List<OpeSalesArea> list);

    int batchInsert(@Param("list") List<OpeSalesArea> list);

    int insertOrUpdate(OpeSalesArea record);

    int insertOrUpdateSelective(OpeSalesArea record);
}