package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressDeliveryMapper extends BaseMapper<CorExpressDelivery> {
    int updateBatch(List<CorExpressDelivery> list);

    int updateBatchSelective(List<CorExpressDelivery> list);

    int batchInsert(@Param("list") List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);
}