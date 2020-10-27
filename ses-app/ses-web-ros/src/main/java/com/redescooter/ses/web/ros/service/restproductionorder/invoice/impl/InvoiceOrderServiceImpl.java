package com.redescooter.ses.web.ros.service.restproductionorder.invoice.impl;


import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dao.restproductionorder.InvoiceOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAllocateOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.ChanageStatusEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:25
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class InvoiceOrderServiceImpl implements InvoiceOrderService {

    @Autowired
    private InvoiceOrderServiceMapper invoiceOrderServiceMapper;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Reference
    private IdAppService idAppService;

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
        Map<Integer, Integer> map = invoiceOrderServiceMapper.countByType(enter);

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
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
        int count = invoiceOrderServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, invoiceOrderServiceMapper.list(enter));
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
        InvoiceOrderDetailResult detail = invoiceOrderServiceMapper.detail(enter);
        if (detail == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        //查询操作日志
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new IdEnter(detail.getId()));
        //查询产品列表
        List<OrderProductDetailResult> orderProductDetailResultList = this.productListById(new IdEnter(enter.getId()));
        //查询关联订单
        List<AssociatedOrderResult> associatedOrderResultList = this.associatedOrderList(new IdEnter(enter.getId()));

        detail.setOrderOperatingList(opTraceResultList);
        detail.setInvoiceProductList(orderProductDetailResultList);
        detail.setAssociatedOrderList(associatedOrderResultList);
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 18:14
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单列表
     * @param enter
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(IdEnter enter) {
        List<AssociatedOrderResult> resultList = new ArrayList<>();
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getById(opeInvoiceOrder.getPurchaseId());
        if (opePurchaseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OpeAllocateOrder opeAllocateOrder = opeAllocateOrderService.getById(opePurchaseOrder.getId());
        if (opeAllocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        resultList.add(new AssociatedOrderResult(opeAllocateOrder.getId(), opeAllocateOrder.getAllocateNo(), OrderTypeEnums.ALLOCATE.getValue(), opeAllocateOrder.getCreatedTime()));
        resultList.add(new AssociatedOrderResult(opeAllocateOrder.getId(), opeAllocateOrder.getAllocateNo(), OrderTypeEnums.ALLOCATE.getValue(), opeAllocateOrder.getCreatedTime()));
        resultList.add(new AssociatedOrderResult(opeAllocateOrder.getId(), opeAllocateOrder.getAllocateNo(), OrderTypeEnums.ALLOCATE.getValue(), opeAllocateOrder.getCreatedTime()));
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 17:37
     * @Param: enter
     * @Return: OrderProductDetailResult
     * @desc: 查询发货单产品列表
     * @param enter
     */
    @Override
    public List<OrderProductDetailResult> productListById(IdEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        //查询产品列表
        List<OrderProductDetailResult> productList = new ArrayList<>();
        List<InvoiceSnResult> snList = new ArrayList<>();
        if (StringUtils.equals(String.valueOf(opeInvoiceOrder.getInvoiceType()), BomCommonTypeEnums.SCOOTER.getValue())) {
            //整车
            productList = invoiceOrderServiceMapper.scooterProductList(opeInvoiceOrder.getId());
            if (CollectionUtils.isNotEmpty(productList)) {
                snList = invoiceOrderServiceMapper.scooterSnList(enter.getId());
            }
        } else if (StringUtils.equals(String.valueOf(opeInvoiceOrder.getInvoiceType()), BomCommonTypeEnums.COMBINATION.getValue())) {
            productList = invoiceOrderServiceMapper.combinationProductList(opeInvoiceOrder.getId());
            if (CollectionUtils.isNotEmpty(productList)) {
                snList = invoiceOrderServiceMapper.combinationSnList(enter.getId());
            }
        } else {
            productList = invoiceOrderServiceMapper.partProductList(opeInvoiceOrder.getId());
            if (CollectionUtils.isNotEmpty(productList)) {
                snList = invoiceOrderServiceMapper.partSnList(enter.getId());
            }
        }

        if (CollectionUtils.isNotEmpty(snList)) {
            Map<Long, String> snMap = new HashMap<>();
            Long qty = 0L;
            for (OrderProductDetailResult product : productList) {
                //序列号集合
                if (opeInvoiceOrder.getInvoiceType().equals(ProductTypeEnums.SCOOTER.getValue())) {

                    snMap =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).collect(Collectors.toMap(InvoiceSnResult::getId, InvoiceSnResult::getSn));
                    //已发货数量
                    qty =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getQty).count();
                } else {
                    snMap =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).collect(Collectors.toMap(InvoiceSnResult::getId, InvoiceSnResult::getSn));
                    //已发货数量
                    qty =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getQty).count();
                }
                product.setQty(qty.intValue());
                product.setSnMap(snMap);
            }
        }
        return productList;

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
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeInvoiceOrder.getInvoiceStatus().equals(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.STOCKING.getValue());
        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.updateById(opeInvoiceOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderOperationTypeEnums.STOCK_UP.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), opeInvoiceOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 15:49
     * @Param: enter
     * @Return: GeneralResult
     * @desc:
     * @param enter
     */
    @Override
    public GeneralResult save(SaveInvoiceEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = new OpeInvoiceOrder();
        if (enter.getId() == null || enter.getId() == 0) {
            opeInvoiceOrder.setId(idAppService.getId(""));
            opeInvoiceOrder.setCreatedBy(enter.getUserId());
            opeInvoiceOrder.setCreatedTime(new Date());
            opeInvoiceOrder.setUpdatedBy(enter.getUserId());
            opeInvoiceOrder.setUpdatedTime(new Date());
            //保存动态
            //保存节点
        } else {
            opeInvoiceOrder.setUpdatedBy(enter.getUserId());
            opeInvoiceOrder.setUpdatedTime(new Date());
            //更新动态
        }

        return new GeneralResult(enter.getRequestId());
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
}
