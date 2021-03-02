package com.redescooter.ses.mobile.rps.service.restproductionorder.purchaseorder;

/**
 * @ClassNamePurchaseOrderService
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:20
 * @Version V1.0
 **/
public interface PurchaseOrderService {

    /**
     * @Author Aleks
     * @Description  采购单状态变为待签收
     * @Date  2020/10/30 19:24
     * @Param [purchaseId, userId]
     * @return
     **/
     void purchaseWaitSign(Long purchaseId ,Long userId);

}
