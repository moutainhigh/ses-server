package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesOrderDetailMapper extends BaseMapper<OpeSalesOrderDetail> {
    int updateBatch(List<OpeSalesOrderDetail> list);

    int batchInsert(@Param("list") List<OpeSalesOrderDetail> list);

    int insertOrUpdate(OpeSalesOrderDetail record);

    int insertOrUpdateSelective(OpeSalesOrderDetail record);
}