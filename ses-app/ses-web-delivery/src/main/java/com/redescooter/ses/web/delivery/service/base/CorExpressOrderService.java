package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;

import java.util.List;

public interface CorExpressOrderService extends IService<CorExpressOrder> {


    int updateBatch(List<CorExpressOrder> list);

    int updateBatchSelective(List<CorExpressOrder> list);

    int batchInsert(List<CorExpressOrder> list);

    int insertOrUpdate(CorExpressOrder record);

    int insertOrUpdateSelective(CorExpressOrder record);

}
