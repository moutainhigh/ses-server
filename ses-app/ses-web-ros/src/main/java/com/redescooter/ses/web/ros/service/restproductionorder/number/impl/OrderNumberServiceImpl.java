package com.redescooter.ses.web.ros.service.restproductionorder.number.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import lombok.extern.slf4j.Slf4j;
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
    private OpeOutwhOrderService opeOutwhOrderService;

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
                return null;
            case 2:
                //采购单
                OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getOne(new LambdaQueryWrapper<OpePurchaseOrder>().orderByDesc(OpePurchaseOrder::getCreatedTime).last("limit 1"));
                return null;
            case 3:
                //发货单
                OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getOne(new LambdaQueryWrapper<OpeInvoiceOrder>().orderByDesc(OpeInvoiceOrder::getCreatedTime).last("limit 1"));
                return null;
            case 4:
                //出库单
                OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getOne(new LambdaQueryWrapper<OpeOutwhOrder>().orderByDesc(OpeOutwhOrder::getCreatedTime).last("limit 1"));
                return null;
            case 5:
                //调拨单
                OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getOne(new LambdaQueryWrapper<OpeEntrustOrder>().orderByDesc(OpeEntrustOrder::getCreatedTime).last("limit 1"));
                return null;
            default:
                break;
        }
        return null;
    }


//    private String generalOrderNum(String orderNumber,String orderNumType){
//        StringBuilder result=new StringBuilder();
//        String dateStamp = DateUtil.getSimpleDateStamp();
//        //OO 2020 1020 001
//        if (StringUtils.isBlank(orderNumber)){
//            return  result.append(orderNumType).append(dateStamp).append("001").toString();
//        }
//        if (!StringUtils.isBlank(orderNumber)){
//            if (StringUtils.equals(String.valueOf(orderNumber.substring(2,9)),dateStamp)){
//                Integer serialNumber=
//                return  orderNumber.substring(0,9);
//            }
//        }
//        return null;
//
//    }
}
