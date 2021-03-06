package com.redescooter.ses.web.delivery.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;

public interface CorExpressDeliveryMapper extends BaseMapper<CorExpressDelivery> {
    int updateBatch(List<CorExpressDelivery> list);

    int batchInsert(@Param("list") List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);

    int updateBatchSelective(List<CorExpressDelivery> list);
}