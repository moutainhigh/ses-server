package com.redescooter.ses.service.mobile.b.service.base.impl;

import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorDeliveryService extends IService<CorDelivery>{


    int updateBatch(List<CorDelivery> list);

    int batchInsert(List<CorDelivery> list);

    int insertOrUpdate(CorDelivery record);

    int insertOrUpdateSelective(CorDelivery record);

}
