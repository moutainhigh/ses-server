package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeWarehouse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeWarehouseMapper extends BaseMapper<OpeWarehouse> {
    int updateBatch(List<OpeWarehouse> list);

    int batchInsert(@Param("list") List<OpeWarehouse> list);

    int insertOrUpdate(OpeWarehouse record);

    int insertOrUpdateSelective(OpeWarehouse record);
}