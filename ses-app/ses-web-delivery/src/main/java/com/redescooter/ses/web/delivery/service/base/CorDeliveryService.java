package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CorDeliveryService extends IService<CorDelivery> {


    int updateBatch(List<CorDelivery> list);

    int updateBatchSelective(List<CorDelivery> list);

    int batchInsert(List<CorDelivery> list);

    int insertOrUpdate(CorDelivery record);

    int insertOrUpdateSelective(CorDelivery record);

}


