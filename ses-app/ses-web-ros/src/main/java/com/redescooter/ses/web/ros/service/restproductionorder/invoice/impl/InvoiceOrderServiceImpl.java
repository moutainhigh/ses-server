package com.redescooter.ses.web.ros.service.restproductionorder.invoice.impl;


import com.redescooter.ses.api.common.enums.production.restproduction.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.production.restproduction.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.production.restproduction.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:25
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class InvoiceOrderServiceImpl implements InvoiceOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:13
     * @Param: enter
     * @Return: Map
     * @desc: 状态分组
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByType(GeneralEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:16
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     * @param enter
     */
    @Override
    public Map<Integer, Integer> statusList(GeneralEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        for (InvoiceOrderStatusEnums item : InvoiceOrderStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 17:32
     * @Param: enter
     * @Return: ShippingListResult
     * @desc: 发货单列表
     * @param enter
     */
    @Override
    public PageResult<InvoiceOrderListResult> list(InvoiceOrderListEnter enter) {
        InvoiceOrderListResult invoiceOrderListResult = new InvoiceOrderListResult();
        List<InvoiceOrderListResult> list = new ArrayList<>();
        invoiceOrderListResult.setId(1L);
        invoiceOrderListResult.setInvoiceNo("你猜");
        invoiceOrderListResult.setInvoiceStatus(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue());
        invoiceOrderListResult.setInvoiceType(ProductTypeEnums.PARTS.getValue());
        invoiceOrderListResult.setInvoiceQty(1);
        invoiceOrderListResult.setPurchaseId(1L);
        invoiceOrderListResult.setPurchaseNo("你才");
        invoiceOrderListResult.setDeliveryDate(new Date());
        invoiceOrderListResult.setSupplierId(1L);
        invoiceOrderListResult.setSupplierName("你才");
        invoiceOrderListResult.setReceiverId(1231L);
        invoiceOrderListResult.setReceiverFirstName("你才");
        invoiceOrderListResult.setReceiverLastName("你才");
        invoiceOrderListResult.setCreateById(2312L);
        invoiceOrderListResult.setCreateByFirstName("dadad");
        invoiceOrderListResult.setCreateByLastName("dasdsada");
        invoiceOrderListResult.setCreateByDate(new Date());
        list.add(invoiceOrderListResult);
        return PageResult.create(enter, 1, list);
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 12:28
     * @Param: enter
     * @Return: ShippingDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public InvoiceOrderDetailResult detail(IdEnter enter) {
        InvoiceOrderDetailResult result = new InvoiceOrderDetailResult();
        result.setId(1L);
        result.setInvoiceNo("dasdad");
        result.setInvoiceStatus(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue());
        result.setDeliveryDate(new Date());
        result.setConsignorUserId(31231L);
        result.setConsignorUserFistName("dadasd");
        result.setConsignorUserLastName("dadas");
        result.setConsignorUserCountryCode("+84");
        result.setConsignorUserTelephone("343424224");
        result.setConsignorUserEmail("321312@qq.com");
        result.setConsigneeUserId(31231L);
        result.setConsigneeUserFistName("dadasd");
        result.setConsigneeUserLastName("dadas");
        result.setConsigneeUserCountryCode("+84");
        result.setConsigneeUserTelephone("343424224");
        result.setConsigneeUserEmail("321312@qq.com");
        result.setNotifyUserId(31231L);
        result.setNotifyUserFistName("dadasd");
        result.setNotifyUserLastName("dadas");
        result.setNotifyUserCountryCode("+84");
        result.setNotifyUserTelephone("343424224");
        result.setNotifyUserEmail("321312@qq.com");
        result.setZipCodeId("32234343423");
        result.setZipCodeName("噶过");
        result.setAddress("法国");
        List<InvoiceOrderDetailProductResult> invoiceOrderDetailProductResults = new ArrayList<>();
        InvoiceOrderDetailProductResult invoiceOrderDetailProductResult = new InvoiceOrderDetailProductResult();
        invoiceOrderDetailProductResult.setId(242423L);
        invoiceOrderDetailProductResult.setCategoryId(3434234L);
        invoiceOrderDetailProductResult.setCategoryName("高速");
        invoiceOrderDetailProductResult.setColorId(4234234L);
        invoiceOrderDetailProductResult.setColorName("红色");
        HashMap<Long, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(123231L, "o1");
        invoiceOrderDetailProductResult.setSnMap(objectObjectHashMap);
        invoiceOrderDetailProductResult.setQty(1);
        result.setInvoiceProductList(invoiceOrderDetailProductResults);
        List<InvoiceOrderDetailAssociatedOrderResult> assList = new ArrayList<>();
        InvoiceOrderDetailAssociatedOrderResult invoiceOrderDetailAssociatedOrderResult = new InvoiceOrderDetailAssociatedOrderResult();
        invoiceOrderDetailAssociatedOrderResult.setId(312312L);
        invoiceOrderDetailAssociatedOrderResult.setOrderNo("23123131");
        invoiceOrderDetailAssociatedOrderResult.setCreatedDate(new Date());
        invoiceOrderDetailAssociatedOrderResult.setOrderType(OrderTypeEnums.INVOICE.getValue());
        assList.add(invoiceOrderDetailAssociatedOrderResult);
        result.setAssociatedOrderList(assList);
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 14:00
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 备货
     * @param enter
     */
    @Override
    public GeneralResult stockUp(IdEnter enter) {
        return null;
    }
}
