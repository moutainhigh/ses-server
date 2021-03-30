package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;

import java.util.List;
public interface CorExpressDeliveryDetailService extends IService<CorExpressDeliveryDetail> {


    int updateBatch(List<CorExpressDeliveryDetail> list);

    int updateBatchSelective(List<CorExpressDeliveryDetail> list);

    int batchInsert(List<CorExpressDeliveryDetail> list);

    int insertOrUpdate(CorExpressDeliveryDetail record);

    int insertOrUpdateSelective(CorExpressDeliveryDetail record);
}




