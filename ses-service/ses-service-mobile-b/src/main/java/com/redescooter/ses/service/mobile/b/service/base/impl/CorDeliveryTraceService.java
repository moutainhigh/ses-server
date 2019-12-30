package com.redescooter.ses.service.mobile.b.service.base.impl;

import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorDeliveryTraceService extends IService<CorDeliveryTrace>{


    int batchInsert(List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);

}
