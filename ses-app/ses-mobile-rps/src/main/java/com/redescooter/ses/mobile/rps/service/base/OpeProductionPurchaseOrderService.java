package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
public interface OpeProductionPurchaseOrderService extends IService<OpeProductionPurchaseOrder> {
    int updateBatch(List<OpeProductionPurchaseOrder> list);

    int updateBatchSelective(List<OpeProductionPurchaseOrder> list);

    int batchInsert(List<OpeProductionPurchaseOrder> list);

    int insertOrUpdate(OpeProductionPurchaseOrder record);

    int insertOrUpdateSelective(OpeProductionPurchaseOrder record);
}






