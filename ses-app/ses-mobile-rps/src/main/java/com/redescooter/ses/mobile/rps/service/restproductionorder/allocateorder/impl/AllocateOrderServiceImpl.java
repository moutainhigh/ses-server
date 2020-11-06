package com.redescooter.ses.mobile.rps.service.restproductionorder.allocateorder.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.AllocateOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateOrder;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassNameAllocateOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:34
 * @Version V1.0
 **/
@Service
public class AllocateOrderServiceImpl implements AllocateOrderService {

    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Override
    @Transactional
    public void allocateWaitSign(Long allocateId, Long userId) {
        // 调拨单状态变为待签收
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null){
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.WAIT_DELIVER.getValue())){
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_SIGN.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 状态流转
        OrderStatusFlowEnter opeOrderStatusFlow = new OrderStatusFlowEnter(null,allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(),allocateOrder.getId(),"");
        opeOrderStatusFlow.setUserId(userId);
        orderStatusFlowService.save(opeOrderStatusFlow);
    }
}
