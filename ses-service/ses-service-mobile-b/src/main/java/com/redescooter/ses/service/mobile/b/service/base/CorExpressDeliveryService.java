package com.redescooter.ses.service.mobile.b.service.base;

import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import com.baomidou.mybatisplus.extension.service.IService;import java.util.List;
@Transactional
public interface CorExpressDeliveryService extends IService<CorExpressDelivery> {


    int updateBatch(List<CorExpressDelivery> list);

    int updateBatchSelective(List<CorExpressDelivery> list);

    int batchInsert(List<CorExpressDelivery> list);

    int insertOrUpdate(CorExpressDelivery record);

    int insertOrUpdateSelective(CorExpressDelivery record);
}




