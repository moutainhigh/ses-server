package com.redescooter.ses.web.ros.service.restproductionorder.consign.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.consign.ConsignOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.restproductionorder.ConsignOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeEntrustOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.SaveConsignEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {

    @Autowired
    private ConsignOrderServiceMapper consignOrderServiceMapper;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private InvoiceOrderService invoiceOrderService;

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
        List<CountByStatusResult> mapList = consignOrderServiceMapper.countByType(enter);
        result = mapList.stream().collect(Collectors.toMap(item -> {
            return Integer.valueOf(item.getStatus());
        }, CountByStatusResult::getTotalCount));
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
    public  Map<Integer, String> statusList(GeneralEnter enter) {
        Map<Integer, String> result = new HashMap<>();
        for (ConsignOrderStatusEnums item : ConsignOrderStatusEnums.values()) {
            result.put(item.getValue(),item.getMessage());
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
        int count = consignOrderServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, consignOrderServiceMapper.list(enter));
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
        ConsignOrderDetailResult detail = consignOrderServiceMapper.detail(enter);
        //产品详情
        detail.setProductList(invoiceOrderService.productListById(new IdEnter(detail.getInvoiceId())));
        //关联订单
        detail.setAssociatedOrderList(this.associatedOrderList(enter));
        //操作动态
        detail.setOrderOperatingList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.ORDER.getValue())));
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 13:46
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单
     * @param enter
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(IdEnter enter) {
        List<AssociatedOrderResult> result=new ArrayList<>();
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getById(enter.getId());
        if (opeEntrustOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(opeEntrustOrder.getInvoiceId());
        if (opeInvoiceOrder==null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        result.add(new AssociatedOrderResult(opeInvoiceOrder.getId(),opeInvoiceOrder.getInvoiceNo(),OrderTypeEnums.INVOICE.getValue(),opeInvoiceOrder.getCreatedTime()));
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
     * @Author: alex
     * @Date: 2020/10/26 16:54
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存出库单
     * @param enter
     */
    @Override
    public GeneralResult save(SaveConsignEnter enter) {

        return null;
    }
}
