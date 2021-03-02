package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpePurchaseOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchaseOrderService extends IService<OpePurchaseOrder> {


    int updateBatch(List<OpePurchaseOrder> list);

    int batchInsert(List<OpePurchaseOrder> list);

    int insertOrUpdate(OpePurchaseOrder record);

    int insertOrUpdateSelective(OpePurchaseOrder record);

}
