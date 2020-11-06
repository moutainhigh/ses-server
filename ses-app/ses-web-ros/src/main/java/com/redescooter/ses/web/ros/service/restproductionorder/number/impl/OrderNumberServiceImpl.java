package com.redescooter.ses.web.ros.service.restproductionorder.number.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderNumberServiceImpl implements OrderNumberService {

    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 12:51
     * @Param: enter
     * @Return: StringResult
     * @desc: 订单编号
     * @param enter
     */
    @Override
    public StringResult orderNumber(OrderNumberEnter enter) {
        switch (enter.getOrderType()) {
            case 1:
                //调拨单
                OpeAllocateOrder opeAllocateOrder = opeAllocateOrderService.getOne(new LambdaQueryWrapper<OpeAllocateOrder>().orderByDesc(OpeAllocateOrder::getCreatedTime).last("limit 1"));
                return new StringResult(opeAllocateOrder == null ? generalOrderNum(null, OrderNumberTypeEnums.ALLOCAT.getValue()) : generalOrderNum(opeAllocateOrder.getAllocateNo(),
                        OrderNumberTypeEnums.ALLOCAT.getValue()));
            case 2:
                //采购单
                OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getOne(new LambdaQueryWrapper<OpePurchaseOrder>().orderByDesc(OpePurchaseOrder::getCreatedTime).last("limit 1"));
                return new StringResult(opePurchaseOrder == null ? generalOrderNum(null, OrderNumberTypeEnums.PURHCAS.getValue()) : generalOrderNum(opePurchaseOrder.getPurchaseNo(),
                        OrderNumberTypeEnums.PURHCAS.getValue()));
            case 3:
                //发货单
                OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getOne(new LambdaQueryWrapper<OpeInvoiceOrder>().orderByDesc(OpeInvoiceOrder::getCreatedTime).last("limit 1"));
                return new StringResult(opeInvoiceOrder == null ? generalOrderNum(null, OrderNumberTypeEnums.INVOICE.getValue()) : generalOrderNum(opeInvoiceOrder.getInvoiceNo(),
                        OrderNumberTypeEnums.INVOICE.getValue()));
            case 4:
                //出库单
                OpeOutWhouseOrder opeOutwhOrder = opeOutWhouseOrderService.getOne(new LambdaQueryWrapper<OpeOutWhouseOrder>().orderByDesc(OpeOutWhouseOrder::getCreatedTime).last("limit 1"));
                return new StringResult(opeOutwhOrder == null ? generalOrderNum(null, OrderNumberTypeEnums.STOCK.getValue()) : generalOrderNum(opeOutwhOrder.getOutWhNo(),
                        OrderNumberTypeEnums.STOCK.getValue()));
            case 5:
                //调拨单
                OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getOne(new LambdaQueryWrapper<OpeEntrustOrder>().orderByDesc(OpeEntrustOrder::getCreatedTime).last("limit 1"));
                return new StringResult(opeEntrustOrder == null ? generalOrderNum(null, OrderNumberTypeEnums.CONSIGN.getValue()) : generalOrderNum(opeEntrustOrder.getEntrustNo(),
                        OrderNumberTypeEnums.CONSIGN.getValue()));
            default:
                break;
        }
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 13:36
     * @Param: orderNumber orderNumType
     * @Return: String
     * @desc:
     */
    private String generalOrderNum(String orderNumber, String orderNumType) {
        StringBuilder result = new StringBuilder();
        String dateStamp = DateUtil.getSimpleDateStamp();
        //OO 2020 1020 001
        if (!StringUtils.isBlank(orderNumber)) {
            if (StringUtils.equals(String.valueOf(orderNumber.substring(3, 11)), dateStamp)) {
                Integer serialNumber = Integer.valueOf(orderNumber.substring(orderNumber.length() - 3, orderNumber.length())) + 1;
                if (serialNumber < 100 && serialNumber > 10) {
                    return result.append(orderNumber.substring(0, 11)).append("0").append(String.valueOf(serialNumber)).toString();
                } else if (serialNumber < 10) {
                    return result.append(orderNumber.substring(0, 11)).append("00").append(String.valueOf(serialNumber)).toString();
                } else {
                    return result.append(orderNumber.substring(0, 11)).append(String.valueOf(serialNumber)).toString();
                }
            }
        }
        return result.append(orderNumType).append(dateStamp).append("001").toString();

    }
}
