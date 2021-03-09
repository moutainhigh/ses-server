package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;

import java.util.List;

public interface CorDeliveryTraceService extends IService<CorDeliveryTrace> {


    int batchInsert(List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);

}

