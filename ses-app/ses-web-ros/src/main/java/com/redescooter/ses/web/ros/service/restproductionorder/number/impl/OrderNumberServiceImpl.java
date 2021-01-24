package com.redescooter.ses.web.ros.service.restproductionorder.number.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.tool.utils.OrderNoGenerateUtil;
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

    @Autowired
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;

    @Autowired
    private OpeCombinOrderService opeCombinOrderService;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

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

    @Override
    public String generateOrderNo(OrderNumberEnter enter) {
        String orderNo = "";
        switch (enter.getOrderType()) {
            case 1:
                // 调拨单
                QueryWrapper<OpeAllocateOrder> queryWrapper = new QueryWrapper<>();
                queryWrapper.like(OpeAllocateOrder.COL_ALLOCATE_NO, DateUtil.getSimpleDateStamp());
                queryWrapper.orderByDesc(OpeAllocateOrder.COL_ALLOCATE_NO);
                queryWrapper.last("limit 1");
                OpeAllocateOrder allocateOrder = opeAllocateOrderService.getOne(queryWrapper);
                if (allocateOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.ALLOCAT.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(allocateOrder.getAllocateNo(), OrderNumberTypeEnums.ALLOCAT.getValue());
                }
            default:
                break;
            case 2:
                // 采购单
                QueryWrapper<OpePurchaseOrder> purchaseOrderqueryWrapper = new QueryWrapper<>();
                purchaseOrderqueryWrapper.like(OpePurchaseOrder.COL_PURCHASE_NO, DateUtil.getSimpleDateStamp());
                purchaseOrderqueryWrapper.orderByDesc(OpePurchaseOrder.COL_PURCHASE_NO);
                purchaseOrderqueryWrapper.last("limit 1");
                OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getOne(purchaseOrderqueryWrapper);
                if (purchaseOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.PURHCAS.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(purchaseOrder.getPurchaseNo(), OrderNumberTypeEnums.PURHCAS.getValue());
                }
                break;
            case 3:
                // 发货单
                QueryWrapper<OpeInvoiceOrder> invoiceOrderQueryWrapper = new QueryWrapper<>();
                invoiceOrderQueryWrapper.like(OpeInvoiceOrder.COL_INVOICE_NO, DateUtil.getSimpleDateStamp());
                invoiceOrderQueryWrapper.orderByDesc(OpeInvoiceOrder.COL_INVOICE_NO);
                invoiceOrderQueryWrapper.last("limit 1");
                OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getOne(invoiceOrderQueryWrapper);
                if (invoiceOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.INVOICE.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(invoiceOrder.getInvoiceNo(), OrderNumberTypeEnums.INVOICE.getValue());
                }
                break;
            case 4:
                // 出库单
                QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
                outWhouseOrderQueryWrapper.like(OpeOutWhouseOrder.COL_OUT_WH_NO, DateUtil.getSimpleDateStamp());
                outWhouseOrderQueryWrapper.orderByDesc(OpeOutWhouseOrder.COL_OUT_WH_NO);
                outWhouseOrderQueryWrapper.last("limit 1");
                OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getOne(outWhouseOrderQueryWrapper);
                if (outWhouseOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.STOCK.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(outWhouseOrder.getOutWhNo(), OrderNumberTypeEnums.STOCK.getValue());
                }
                break;
            case 5:
                // 委托单
                QueryWrapper<OpeEntrustOrder> entrustOrderQueryWrapper = new QueryWrapper<>();
                entrustOrderQueryWrapper.like(OpeEntrustOrder.COL_ENTRUST_NO, DateUtil.getSimpleDateStamp());
                entrustOrderQueryWrapper.orderByDesc(OpeEntrustOrder.COL_ENTRUST_NO);
                entrustOrderQueryWrapper.last("limit 1");
                OpeEntrustOrder entrustOrder = opeEntrustOrderService.getOne(entrustOrderQueryWrapper);
                if (entrustOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.CONSIGN.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(entrustOrder.getEntrustNo(), OrderNumberTypeEnums.CONSIGN.getValue());
                }
                break;
            case 6:
                // 物流运输单

                break;
            case 7:
                // 工厂采购单
                QueryWrapper<OpeProductionPurchaseOrder> productionPurchaseOrderQueryWrapper = new QueryWrapper<>();
                productionPurchaseOrderQueryWrapper.like(OpeProductionPurchaseOrder.COL_PURCHASE_NO, DateUtil.getSimpleDateStamp());
                productionPurchaseOrderQueryWrapper.orderByDesc(OpeProductionPurchaseOrder.COL_PURCHASE_NO);
                productionPurchaseOrderQueryWrapper.last("limit 1");
                OpeProductionPurchaseOrder productionPurchaseOrder = opeProductionPurchaseOrderService.getOne(productionPurchaseOrderQueryWrapper);
                if (productionPurchaseOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.PRODUCTION_PURCHASE.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(productionPurchaseOrder.getPurchaseNo(), OrderNumberTypeEnums.PRODUCTION_PURCHASE.getValue());
                }
                break;
            case 8:
                // 工厂入库单
                QueryWrapper<OpeInWhouseOrder> inWhouseOrderQueryWrapper = new QueryWrapper<>();
                inWhouseOrderQueryWrapper.like(OpeInWhouseOrder.COL_IN_WH_NO, DateUtil.getSimpleDateStamp());
                inWhouseOrderQueryWrapper.orderByDesc(OpeInWhouseOrder.COL_IN_WH_NO);
                inWhouseOrderQueryWrapper.last("limit 1");
                OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getOne(inWhouseOrderQueryWrapper);
                if (inWhouseOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.IN_WHOUSE.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(inWhouseOrder.getInWhNo(), OrderNumberTypeEnums.IN_WHOUSE.getValue());
                }
                break;
            case 9:
                // 组装单
                QueryWrapper<OpeCombinOrder> combinOrderQueryWrapper = new QueryWrapper<>();
                combinOrderQueryWrapper.like(OpeCombinOrder.COL_COMBIN_NO, DateUtil.getSimpleDateStamp());
                combinOrderQueryWrapper.orderByDesc(OpeCombinOrder.COL_COMBIN_NO);
                combinOrderQueryWrapper.last("limit 1");
                OpeCombinOrder combinOrder = opeCombinOrderService.getOne(combinOrderQueryWrapper);
                if (combinOrder == null) {
                    // 今天还没有产生过单据 直接就是初始的单据号
                    orderNo = OrderNumberTypeEnums.COMBIN.getValue() + DateUtil.getSimpleDateStamp() + "001";
                } else {
                    orderNo = OrderNoGenerateUtil.orderNoGenerate(combinOrder.getCombinNo(), OrderNumberTypeEnums.COMBIN.getValue());
                }
                break;
        }
        return orderNo;
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
                if (serialNumber < 100 && serialNumber >= 10) {
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


//    public static void main(String[] args) {
//        String no = generalOrderNum("RSO20201106099", "RSO");
//        System.out.println(no);
//    }

}
