package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderPayment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSalesOrderPaymentMapper extends BaseMapper<OpeSalesOrderPayment> {
    int updateBatch(List<OpeSalesOrderPayment> list);

    int batchInsert(@Param("list") List<OpeSalesOrderPayment> list);

    int insertOrUpdate(OpeSalesOrderPayment record);

    int insertOrUpdateSelective(OpeSalesOrderPayment record);
}