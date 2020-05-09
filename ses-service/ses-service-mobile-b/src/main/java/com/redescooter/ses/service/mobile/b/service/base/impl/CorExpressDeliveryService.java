package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CorExpressDeliveryService extends IService<CorExpressDelivery> {


    int updateBatch(List<CorExpressDelivery> list);

    int batchInsert(List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);

}

