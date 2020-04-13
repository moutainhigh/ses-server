package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDelivery;
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
