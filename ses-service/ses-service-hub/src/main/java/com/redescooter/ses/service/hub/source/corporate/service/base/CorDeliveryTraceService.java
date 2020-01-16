package com.redescooter.ses.service.hub.source.corporate.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDeliveryTrace;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("corporate")
public interface CorDeliveryTraceService extends IService<CorDeliveryTrace> {

    int batchInsert(List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);

}
