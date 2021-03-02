package com.redescooter.ses.mobile.rps.dao.purchaseorder;

import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;

/**
 * 采购单 Mapper接口
 * @author assert
 * @date 2021-01-21
 */
public interface PurchaseOrderMapper {

    /**
     * 修改采购单信息
     * @param opeProductionPurchaseOrder
     * @return int
     * @author assert
     * @date 2021/1/22
    */
    int updatePurchaseOrder(OpeProductionPurchaseOrder opeProductionPurchaseOrder);

}
