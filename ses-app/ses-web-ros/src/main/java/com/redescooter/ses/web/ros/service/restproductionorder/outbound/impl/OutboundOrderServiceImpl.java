package com.redescooter.ses.web.ros.service.restproductionorder.outbound.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrdrStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.ChanageStatusEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOutboundOrderEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:27
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class OutboundOrderServiceImpl implements OutboundOrderService {
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
        Map<Integer, Integer> map = new HashMap<>();
        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
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
        for (OutBoundOrdrStatusEnums item : OutBoundOrdrStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:50
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public PageResult<OutboundOrderListResult> list(OutboundOrderListEnter enter) {
        List<OutboundOrderListResult> outboundOrderListResults = new ArrayList<>();
        OutboundOrderListResult outboundOrderListResult = new OutboundOrderListResult();
        outboundOrderListResult.setId(313131L);
        outboundOrderListResult.setPurhcasId(3131L);
        outboundOrderListResult.setPurhcasNo("343243423");
        outboundOrderListResult.setOutWhNo("42342dasda");
        outboundOrderListResult.setOutWhStatus(OutBoundOrdrStatusEnums.OUT_STOCK.getValue());
        outboundOrderListResult.setOutWhType(1);
        outboundOrderListResult.setOutWhQty(1);
        outboundOrderListResult.setCreateById(34242L);
        outboundOrderListResult.setCreateBFirstName("asdasd");
        outboundOrderListResult.setCreateByLastName("dasdad");
        outboundOrderListResult.setCreateByCountryCodeId(23442L);
        outboundOrderListResult.setCreateByCountryCode("sdadad");
        outboundOrderListResult.setCreateDate(new Date());
        outboundOrderListResults.add(outboundOrderListResult);
        return PageResult.create(enter, 1, outboundOrderListResults);
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:52
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public OutboundOrderDetailResult detail(IdEnter enter) {
        OutboundOrderDetailResult outboundOrderDetailResult = new OutboundOrderDetailResult();
        outboundOrderDetailResult.setId(32312L);
        outboundOrderDetailResult.setOutWhNo("4424");
        outboundOrderDetailResult.setOutWhStatus(OutBoundOrdrStatusEnums.BE_OUTBOUND.getValue());
        outboundOrderDetailResult.setOutWhType(1);
        outboundOrderDetailResult.setCreateById(4324242L);
        outboundOrderDetailResult.setCreateByFirstName("4324223");
        outboundOrderDetailResult.setCreateByLastName("dadada");
        outboundOrderDetailResult.setCreateByCountryCodeId(423242L);
        outboundOrderDetailResult.setCreateByCountryCode("dasdaa");
        outboundOrderDetailResult.setCreateByTelephone("423424");
        outboundOrderDetailResult.setCreateByMail("rw@qq.com");
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
        outboundOrderDetailResult.setInvoiceProductList(invoiceOrderDetailProductResults);
        List<AssociatedOrderResult> assList = new ArrayList<>();
        AssociatedOrderResult invoiceOrderDetailAssociatedOrderResult = new AssociatedOrderResult();
        invoiceOrderDetailAssociatedOrderResult.setId(312312L);
        invoiceOrderDetailAssociatedOrderResult.setOrderNo("23123131");
        invoiceOrderDetailAssociatedOrderResult.setCreatedDate(new Date());
        invoiceOrderDetailAssociatedOrderResult.setOrderType(OrderTypeEnums.INVOICE.getValue());
        assList.add(invoiceOrderDetailAssociatedOrderResult);
        outboundOrderDetailResult.setAssociatedOrderList(assList);
        return outboundOrderDetailResult;
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
     * @Date: 2020/10/26 16:52
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存
     * @param enter
     */
    @Override
    public GeneralResult save(SaveOutboundOrderEnter enter) {
        return null;
    }
}
