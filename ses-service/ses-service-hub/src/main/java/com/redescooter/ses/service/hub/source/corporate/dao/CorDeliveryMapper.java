package com.redescooter.ses.service.hub.source.corporate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDelivery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDeliveryMapper extends BaseMapper<CorDelivery> {
    int updateBatch(List<CorDelivery> list);

    int updateBatchSelective(List<CorDelivery> list);

    int batchInsert(@Param("list") List<CorDelivery> list);

    int insertOrUpdate(CorDelivery record);

    int insertOrUpdateSelective(CorDelivery record);
}