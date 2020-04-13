package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesOrderMapper extends BaseMapper<OpeSalesOrder> {
    int updateBatch(List<OpeSalesOrder> list);

    int batchInsert(@Param("list") List<OpeSalesOrder> list);

    int insertOrUpdate(OpeSalesOrder record);

    int insertOrUpdateSelective(OpeSalesOrder record);
}