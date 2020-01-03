package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDeliveryMapper extends BaseMapper<CorDelivery> {
    int updateBatch(List<CorDelivery> list);

    int batchInsert(@Param("list") List<CorDelivery> list);

    int insertOrUpdate(CorDelivery record);

    int insertOrUpdateSelective(CorDelivery record);
}