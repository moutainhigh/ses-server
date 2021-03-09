package com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceOrder;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.invoice.InvoiceUpdateStatusEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName:InvoiceOrderServiceImpl
 * @description: InvoiceOrderServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/05 16:44
 */
@Service
@Slf4j
public class InvoiceOrderServiceImpl implements InvoiceOrderService {

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/5 4:55 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 更新发货单状态
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updateStatus(InvoiceUpdateStatusEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        opeInvoiceOrder.setInvoiceStatus(enter.getStatus());
        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.updateById(opeInvoiceOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), enter.getOperatingDynamics(), opeInvoiceOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, enter.getStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(), opeInvoiceOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
