package com.redescooter.ses.web.ros.service.restproductionorder.consign.impl;

import com.redescooter.ses.api.common.enums.restproduction.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproduction.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproduction.consign.ConsignOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproduction.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.ChanageStatusEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
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
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     * @param enter
     */
    @Override
    public Map<Integer, Integer> statusList(GeneralEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        for (ConsignOrderStatusEnums item : ConsignOrderStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:59
     * @Param: enter
     * @Return: ConsignOrderListResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public PageResult<ConsignOrderListResult> list(ConsignOrderListEnter enter) {
        ConsignOrderListResult invoiceOrderListResult = new ConsignOrderListResult();
        List<ConsignOrderListResult> list = new ArrayList<>();
        invoiceOrderListResult.setId(1L);
        invoiceOrderListResult.setConsignOrderNo("你猜");
        invoiceOrderListResult.setStatus(ConsignOrderStatusEnums.BE_SIGNED.getValue());
        invoiceOrderListResult.setOutwhId(432442L);
        invoiceOrderListResult.setQty(1);
        invoiceOrderListResult.setConsigneeId(1L);
        invoiceOrderListResult.setConsigneeCountryCodeId(1L);
        invoiceOrderListResult.setDeliveryDate(new Date());
        invoiceOrderListResult.setConsigneeCountryCode("dsadasd");
        invoiceOrderListResult.setConsigneeTelephone("你才");
        invoiceOrderListResult.setConsigneeMail("3213213@qq.com");
        invoiceOrderListResult.setCreateById(1312L);
        invoiceOrderListResult.setCreateByFirstName("你才");
        invoiceOrderListResult.setCreateByLastName("dasdsada");
        invoiceOrderListResult.setCreateByDate(new Date());
        list.add(invoiceOrderListResult);
        return PageResult.create(enter, 1, list);
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 14:02
     * @Param: enter
     * @Return: ConsignOrderDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public ConsignOrderDetailResult detail(IdEnter enter) {

        ConsignOrderDetailResult result = new ConsignOrderDetailResult();
        result.setId(1L);
        result.setConsignOrderNo("dasdad");
        result.setStatus(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue());
        result.setDeliveryDate(new Date());
        result.setConsignorUserId(31231L);
        result.setConsignorUserFirstName("dadasd");
        result.setConsignorUserLastName("dadas");
        result.setConsignorUserCountryCode("+84");
        result.setConsignorUserTelephone("343424224");
        result.setConsignorUserMail("321312@qq.com");
        result.setConsigneeUserId(31231L);
        result.setConsigneeUserFirstName("dadasd");
        result.setConsigneeUserLastName("dadas");
        result.setConsigneeUserCountryCode("+84");
        result.setConsigneeUserTelephone("343424224");
        result.setConsigneeUserMail("321312@qq.com");
        result.setNotifyUserId(31231L);
        result.setNotifyUserFirstName("dadasd");
        result.setNotifyUserLastName("dadas");
        result.setNotifyUserCountryCode("+84");
        result.setNotifyUserTelephone("343424224");
        result.setNotifyUserMail("321312@qq.com");
        result.setZipCodeId(32234343423L);
        result.setZipcodeName("噶过");
        result.setAddress("法国");
        List<OrderProductDetailResult> invoiceOrderDetailProductResults = new ArrayList<>();
        OrderProductDetailResult invoiceOrderDetailProductResult = new OrderProductDetailResult();
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
        List<AssociatedOrderResult> assList = new ArrayList<>();
        AssociatedOrderResult invoiceOrderDetailAssociatedOrderResult = new AssociatedOrderResult();
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
     * @Date: 2020/10/22 14:04
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 委托单签收
     * @param enter
     */
    @Override
    public GeneralResult signFor(IdEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: enter
     * @Date: 2020/10/26 15:57
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 状态修改
     * @param enter
     */
    @Override
    public GeneralResult chanageStatus(ChanageStatusEnter enter) {
        return null;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:54
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存出库单
     * @param enter
     */
    @Override
    public GeneralResult save(GeneralEnter enter) {
        return null;
    }
}
