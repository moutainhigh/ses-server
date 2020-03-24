package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionDelivery;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionDeliveryService extends IService<OpeProductionDelivery> {


    int updateBatch(List<OpeProductionDelivery> list);

    int batchInsert(List<OpeProductionDelivery> list);

    int insertOrUpdate(OpeProductionDelivery record);

    int insertOrUpdateSelective(OpeProductionDelivery record);

}
