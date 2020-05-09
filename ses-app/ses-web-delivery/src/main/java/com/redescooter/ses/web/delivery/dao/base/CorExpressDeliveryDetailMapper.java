package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorExpressDeliveryDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CorExpressDeliveryDetailMapper extends BaseMapper<CorExpressDeliveryDetail> {
    int updateBatch(List<CorExpressDeliveryDetail> list);

    int updateBatchSelective(List<CorExpressDeliveryDetail> list);

    int batchInsert(@Param("list") List<CorExpressDeliveryDetail> list);

    int insertOrUpdate(CorExpressDeliveryDetail record);

    int insertOrUpdateSelective(CorExpressDeliveryDetail record);
}