package com.redescooter.ses.mobile.rps.service.restproductionorder.purchaseorder.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PurchaseOrderStatusEnum;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseOrder;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpePurchaseOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassNamePurchaseOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:21
 * @Version V1.0
 **/
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private AllocateOrderService allocateOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @DubboReference
    private IdAppService idAppService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void purchaseWaitSign(Long purchaseId, Long userId) {
        // 采购单变为待签收状态
        OpePurchaseOrder opePurchaseOrder  = opePurchaseOrderService.getById(purchaseId);
        if (opePurchaseOrder == null){
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if(!opePurchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.WAIT_DELIVER.getValue())){
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        opePurchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.WAIT_SIGN.getValue());
        opePurchaseOrderService.saveOrUpdate(opePurchaseOrder);
        // 状态流转
        OrderStatusFlowEnter opeOrderStatusFlow = new OrderStatusFlowEnter(null,opePurchaseOrder.getPurchaseStatus(), OrderTypeEnums.SHIPPING.getValue(),opePurchaseOrder.getId(),"");
        opeOrderStatusFlow.setUserId(userId);
        orderStatusFlowService.save(opeOrderStatusFlow);
        // 把采购单对应的调拨单变为待签收状态
        allocateOrderService.allocateWaitSign(opePurchaseOrder.getAllocateId(),userId);
    }

}
