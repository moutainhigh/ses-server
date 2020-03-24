package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryTrace;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionDeliveryTraceService extends IService<OpeProductionDeliveryTrace> {


    int updateBatch(List<OpeProductionDeliveryTrace> list);

    int batchInsert(List<OpeProductionDeliveryTrace> list);

    int insertOrUpdate(OpeProductionDeliveryTrace record);

    int insertOrUpdateSelective(OpeProductionDeliveryTrace record);

}
