package com.redescooter.ses.web.delivery.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
@Transactional
public interface CorExpressDeliveryService extends IService<CorExpressDelivery>{


    int updateBatch(List<CorExpressDelivery> list);

    int batchInsert(List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);

}
