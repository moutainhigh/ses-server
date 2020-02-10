package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressDeliveryDetailMapper extends BaseMapper<CorExpressDeliveryDetail> {
    int updateBatch(List<CorExpressDeliveryDetail> list);

    int updateBatchSelective(List<CorExpressDeliveryDetail> list);

    int batchInsert(@Param("list") List<CorExpressDeliveryDetail> list);

    int insertOrUpdate(CorExpressDeliveryDetail record);

    int insertOrUpdateSelective(CorExpressDeliveryDetail record);
}