package com.redescooter.ses.web.delivery.service.base;

import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CorExpressDeliveryService extends IService<CorExpressDelivery> {


    int updateBatch(List<CorExpressDelivery> list);

    int updateBatchSelective(List<CorExpressDelivery> list);

    int batchInsert(List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);

}
