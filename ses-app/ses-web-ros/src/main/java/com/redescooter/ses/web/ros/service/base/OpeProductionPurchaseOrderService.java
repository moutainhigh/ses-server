package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionPurchaseOrder;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionPurchaseOrderService extends IService<OpeProductionPurchaseOrder> {


    int updateBatch(List<OpeProductionPurchaseOrder> list);

    int batchInsert(List<OpeProductionPurchaseOrder> list);

    int insertOrUpdate(OpeProductionPurchaseOrder record);

    int insertOrUpdateSelective(OpeProductionPurchaseOrder record);

}


