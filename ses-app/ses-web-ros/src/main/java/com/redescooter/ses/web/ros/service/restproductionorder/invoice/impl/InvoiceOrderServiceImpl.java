package com.redescooter.ses.web.ros.service.restproductionorder.invoice.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.InvoiceOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.QueryStaffResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.SaveConsignEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOutboundOrderEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeInvoiceScooterBService opeInvoiceScooterBService;

    @Autowired
    private OpeInvoiceCombinBService opeInvoiceCombinBService;

    @Autowired
    private OpeInvoicePartsBService opeInvoicePartsBService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private ConsignOrderService consignOrderService;

    @Autowired
    private OutboundOrderService outboundOrderService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private AllocateOrderService allocateOrderService;

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
        List<CountByStatusResult> countByStatusResult = invoiceOrderServiceMapper.countByType(enter);
        Map<Integer, Integer> result = countByStatusResult.stream().collect(Collectors.toMap(item -> Integer.valueOf(item.getStatus()), CountByStatusResult::getTotalCount));
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
    public  Map<Integer, String> statusList(GeneralEnter enter) {
        Map<Integer, String> result = new HashMap<>();
        for (InvoiceOrderStatusEnums item : InvoiceOrderStatusEnums.values()) {
            result.put(item.getValue(),item.getMessage());
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
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new ListByBussIdEnter(detail.getId(), OrderTypeEnums.INVOICE.getValue()));
        //查询产品列表
        List<OrderProductDetailResult> orderProductDetailResultList = this.productListById(new IdEnter(enter.getId()));
        //查询关联订单
        List<AssociatedOrderResult> associatedOrderResultList = this.associatedOrderList(new IdEnter(enter.getId()));

        detail.setOrderOperatingList(CollectionUtils.isEmpty(opTraceResultList) ? new ArrayList<>() : opTraceResultList);
        detail.setInvoiceProductList(CollectionUtils.isEmpty(orderProductDetailResultList) ? new ArrayList<>() : orderProductDetailResultList);
        detail.setAssociatedOrderList(CollectionUtils.isEmpty(associatedOrderResultList) ? new ArrayList<>() : associatedOrderResultList);
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
        OpeAllocateOrder opeAllocateOrder = opeAllocateOrderService.getById(opePurchaseOrder.getAllocateId());
        if (opeAllocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        resultList.add(new AssociatedOrderResult(opePurchaseOrder.getId(), opePurchaseOrder.getAllocateNo(), OrderTypeEnums.SHIPPING.getValue(), opeAllocateOrder.getCreatedTime()));
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
        if (opeInvoiceOrder.getInvoiceType().equals(ProductTypeEnums.SCOOTER.getValue())) {
            //整车
            productList = invoiceOrderServiceMapper.scooterProductList(opeInvoiceOrder.getId());
            if (CollectionUtils.isNotEmpty(productList)) {
                snList = invoiceOrderServiceMapper.scooterSnList(enter.getId());
            }
        } else if (opeInvoiceOrder.getInvoiceType().equals(ProductTypeEnums.COMBINATION.getValue())) {
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
    @Transactional
    @Override
    public GeneralResult stockUp(IdEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeInvoiceOrder.getInvoiceStatus().equals(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //创建出库单
        saveOutbound(enter, opeInvoiceOrder);

        //发货单更新
        opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.STOCKING.getValue());
        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.updateById(opeInvoiceOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.STOCK_UP.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);

        //采购单修改状态
        purchaseOrderService.purchaseStocking(opeInvoiceOrder.getId(), enter.getUserId());
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
    @Transactional
    @Override
    public GeneralResult save(SaveInvoiceEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = new OpeInvoiceOrder();
        //关联单据校验
        OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getById(enter.getPurchaseId());
        if (opePurchaseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }

        //通知人校验
        List<OpeSysStaff> opeSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId, enter.getConsigneeUser(), enter.getConsignorUser()));
        if (CollectionUtils.isEmpty(opeSysStaffList)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }

        if (!enter.getInvoiceStatus().equals(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        BeanUtils.copyProperties(enter, opeInvoiceOrder);
        SaveOpTraceEnter saveOpTraceEnter = null;
        if (enter.getId() == null || enter.getId() == 0) {
            opeInvoiceOrder.setId(idAppService.getId(SequenceName.OPE_INVOICE_ORDER));
            opeInvoiceOrder.setDr(0);
            opeInvoiceOrder.setInvoiceNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.INVOICE.getValue())).getValue());
            opeInvoiceOrder.setCreatedBy(enter.getUserId());
            opeInvoiceOrder.setCreatedTime(new Date());
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.CREATE.getValue(),
                    opeInvoiceOrder.getRemark());
            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(),
                    opeInvoiceOrder.getRemark());
            orderStatusFlowEnter.setUserId(enter.getUserId());
            orderStatusFlowService.save(orderStatusFlowEnter);
        } else {
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.EDIT.getValue(),
                    opeInvoiceOrder.getRemark());
        }
        //操作动态
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);


        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.saveOrUpdate(opeInvoiceOrder);

        //保存子单据
        enter.setId(opeInvoiceOrder.getId());
        saveInvoiceB(enter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 10:28
     * @Param: enter
     * @Return: QueryStaffResult
     * @desc: 员工列表
     * @param enter
     */
    @Override
    public List<QueryStaffResult> staffList(StringEnter enter) {
        List<QueryStaffResult> result = new ArrayList<>();

        QueryWrapper<OpeSysStaff> queryStaffResultQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(enter.getSt())) {
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_FIRST_NAME, enter.getSt());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_LAST_NAME, enter.getSt());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_TELEPHONE, enter.getSt());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_EMAIL, enter.getSt());
        }
        List<OpeSysStaff> opeSysStaffList = opeSysStaffService.list(queryStaffResultQueryWrapper);
        if (CollectionUtils.isNotEmpty(opeSysStaffList)) {
            opeSysStaffList.forEach(item -> {
                QueryStaffResult queryStaffResult = new QueryStaffResult();
                BeanUtils.copyProperties(item, queryStaffResult);
                result.add(queryStaffResult);
            });
        }
        return result;
    }

    /**
     * @Description
     * @Author: enter
     * @Date: 2020/10/28 20:42
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 装车
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult loading(IdEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeInvoiceOrder.getInvoiceStatus().equals(InvoiceOrderStatusEnums.BE_LOADED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
//        OpePurchaseOrder opePurchaseOrder = opePurchaseOrderService.getById(enter.getId());
//        if (opePurchaseOrder == null) {
//            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
//        }
        opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.BE_DELIVERED.getValue());
        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.updateById(opeInvoiceOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.LOADING.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowEnter.setId(null);
        orderStatusFlowService.save(orderStatusFlowEnter);

        //保存委托单
        saveBuildConsignOrder(enter, opeInvoiceOrder);

        //采购单修改状态
        purchaseOrderService.purchaseWaitDeliver(opeInvoiceOrder.getPurchaseId(), enter.getUserId());
        //调拨单修改状态
//        allocateOrderService.allocateWaitDeliver(opePurchaseOrder.getAllocateId(), enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/30 13:45
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 签收
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult signFor(IdEnter enter) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeInvoiceOrder.getInvoiceStatus().equals(InvoiceOrderStatusEnums.BE_SIGNED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.RECEIVED.getValue());
        opeInvoiceOrder.setUpdatedBy(enter.getUserId());
        opeInvoiceOrder.setUpdatedTime(new Date());
        opeInvoiceOrderService.updateById(opeInvoiceOrder);

        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeInvoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.SIGN_FOR.getValue(), null);
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 10:52
     * @Param: 保存出库单
     * @Return:
     * @desc:
     */
    private void saveOutbound(IdEnter enter, OpeInvoiceOrder opeInvoiceOrder) {
        List<ProductEnter> productEnterList = new ArrayList<>();
        switch (opeInvoiceOrder.getInvoiceType()) {
            case 1:
                List<OpeInvoiceScooterB> opeInvoiceScooterBList = opeInvoiceScooterBService.list(new LambdaQueryWrapper<OpeInvoiceScooterB>().eq(OpeInvoiceScooterB::getInvoiceId,
                        opeInvoiceOrder.getId()));
                if (CollectionUtils.isNotEmpty(opeInvoiceScooterBList)) {
                    productEnterList =
                            opeInvoiceScooterBList.stream().map(item -> new ProductEnter(null, item.getColorId(), item.getGroupId(), item.getQty(), item.getRemark())).collect(Collectors.toList());
                }
                break;

            case 2:
                List<OpeInvoiceCombinB> invoiceCombinBList = opeInvoiceCombinBService.list(new LambdaQueryWrapper<OpeInvoiceCombinB>().eq(OpeInvoiceCombinB::getInvoiceId, opeInvoiceOrder.getId()));
                if (CollectionUtils.isNotEmpty(invoiceCombinBList)) {
                    productEnterList =
                            invoiceCombinBList.stream().map(item -> new ProductEnter(item.getProductionCombinBomId(), null, null, item.getQty(), item.getRemark())).collect(Collectors.toList());
                }
                break;

            default:
                List<OpeInvoicePartsB> opeInvoicePartsBList = opeInvoicePartsBService.list(new LambdaQueryWrapper<OpeInvoicePartsB>().eq(OpeInvoicePartsB::getInvoiceId, opeInvoiceOrder.getId()));
                if (CollectionUtils.isNotEmpty(opeInvoicePartsBList)) {
                    productEnterList =
                            opeInvoicePartsBList.stream().map(item -> new ProductEnter(item.getPartsId(), null, null, item.getQty(), item.getRemark())).collect(Collectors.toList());
                }
                break;
        }
        //发货单
        SaveOutboundOrderEnter saveOutboundOrderEnter = new SaveOutboundOrderEnter();
        BeanUtils.copyProperties(enter, saveOutboundOrderEnter);
        saveOutboundOrderEnter.setId(null);
        saveOutboundOrderEnter.setInvoiceId(enter.getId());
        saveOutboundOrderEnter.setInvoiceNo(opeInvoiceOrder.getInvoiceNo());
        saveOutboundOrderEnter.setOutWhType(opeInvoiceOrder.getInvoiceType());
        saveOutboundOrderEnter.setOutType(OutBoundOrderTypeEnums.SALES.getValue());
        saveOutboundOrderEnter.setOutWhQty(opeInvoiceOrder.getInvoiceQty());
        saveOutboundOrderEnter.setRemark(opeInvoiceOrder.getRemark());
        saveOutboundOrderEnter.setProductEnterList(productEnterList);
        outboundOrderService.save(saveOutboundOrderEnter);
    }


    private void saveInvoiceB(SaveInvoiceEnter enter) {
        switch (enter.getInvoiceType()) {
            case 1:
                List<OpeInvoiceScooterB> saveOpeInvoiceScooterBList = new ArrayList<>();
                enter.getProductEnterList().forEach(item -> {
                    OpeInvoiceScooterB opeInvoiceScooterB = new OpeInvoiceScooterB();
                    BeanUtils.copyProperties(item, opeInvoiceScooterB);
                    opeInvoiceScooterB.setId(idAppService.getId(SequenceName.OPE_INVOICE_SCOOTER_B));
                    opeInvoiceScooterB.setDr(0);
                    opeInvoiceScooterB.setInvoiceId(enter.getId());
                    opeInvoiceScooterB.setCreatedBy(enter.getUserId());
                    opeInvoiceScooterB.setCreatedTime(new Date());
                    opeInvoiceScooterB.setUpdatedBy(enter.getUserId());
                    opeInvoiceScooterB.setUpdatedTime(new Date());
                    saveOpeInvoiceScooterBList.add(opeInvoiceScooterB);
                });
                //查询是否有字单据
                opeInvoiceScooterBService.remove(new LambdaQueryWrapper<OpeInvoiceScooterB>().eq(OpeInvoiceScooterB::getInvoiceId, enter.getId()));

                //保存子单据
                opeInvoiceScooterBService.saveOrUpdateBatch(saveOpeInvoiceScooterBList);
                break;

            case 2:
                List<OpeInvoiceCombinB> saveOpeInvoiceCombinBList = new ArrayList<>();
                List<OpeProductionCombinBom> opeProductionCombinBomList =
                        opeProductionCombinBomService.listByIds(enter.getProductEnterList().stream().map(ProductEnter::getProductId).collect(Collectors.toSet()));
                opeProductionCombinBomList.forEach(item -> {
                    ProductEnter productEnter = enter.getProductEnterList().stream().filter(product -> item.getId().equals(product.getProductId())).findFirst().orElse(null);
                    if (productEnter == null) {
                        throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
                    }
                    OpeInvoiceCombinB opeInvoiceCombinB = new OpeInvoiceCombinB();
                    opeInvoiceCombinB.setId(idAppService.getId(SequenceName.OPE_INVOICE_COMBIN_B));
                    opeInvoiceCombinB.setQty(productEnter.getQty());
                    opeInvoiceCombinB.setRemark(productEnter.getRemark());
                    opeInvoiceCombinB.setInvoiceId(enter.getId());
                    opeInvoiceCombinB.setProductionCombinBomId(item.getId());
                    opeInvoiceCombinB.setCombinName(item.getEnName());
                    opeInvoiceCombinB.setDr(0);
                    opeInvoiceCombinB.setCreatedBy(enter.getUserId());
                    opeInvoiceCombinB.setCreatedTime(new Date());
                    opeInvoiceCombinB.setUpdatedBy(enter.getUserId());
                    opeInvoiceCombinB.setUpdatedTime(new Date());
                    saveOpeInvoiceCombinBList.add(opeInvoiceCombinB);
                });
                //删除是否
                opeInvoiceCombinBService.remove(new LambdaQueryWrapper<OpeInvoiceCombinB>().eq(OpeInvoiceCombinB::getInvoiceId, enter.getId()));
                opeInvoiceCombinBService.saveOrUpdateBatch(saveOpeInvoiceCombinBList);
                break;

            default:
                List<OpeInvoicePartsB> opeInvoicePartsBList = new ArrayList<>();
                List<OpeProductionParts> opeProductionPartList = opeProductionPartsService.listByIds(enter.getProductEnterList().stream().map(ProductEnter::getProductId).collect(Collectors.toSet()));
                opeProductionPartList.forEach(item -> {
                    ProductEnter productEnter = enter.getProductEnterList().stream().filter(product -> item.getId().equals(product.getProductId())).findFirst().orElse(null);
                    if (productEnter == null) {
                        throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                    }

                    OpeInvoicePartsB opeInvoicePartsB = new OpeInvoicePartsB();
                    opeInvoicePartsB.setId(idAppService.getId(SequenceName.OPE_INVOICE_PARTS_B));
                    opeInvoicePartsB.setDr(0);
                    opeInvoicePartsB.setInvoiceId(enter.getId());
                    opeInvoicePartsB.setPartsId(item.getId());
                    opeInvoicePartsB.setPartsNo(item.getPartsNo());
                    opeInvoicePartsB.setPartsType(item.getPartsType());
                    opeInvoicePartsB.setPartsName(item.getEnName());
                    opeInvoicePartsB.setQty(productEnter.getQty());
                    opeInvoicePartsB.setRemark(productEnter.getRemark());
                    opeInvoicePartsB.setCreatedBy(enter.getUserId());
                    opeInvoicePartsB.setCreatedTime(new Date());
                    opeInvoicePartsB.setUpdatedBy(enter.getUserId());
                    opeInvoicePartsB.setUpdatedTime(new Date());
                    opeInvoicePartsBList.add(opeInvoicePartsB);
                });
                opeInvoicePartsBService.remove(new LambdaQueryWrapper<OpeInvoicePartsB>().eq(OpeInvoicePartsB::getInvoiceId, enter.getId()));
                opeInvoicePartsBService.saveOrUpdateBatch(opeInvoicePartsBList);
                break;
        }
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/30 10:54
     * @Param: enter opeInvoiceOrder
     * @Return: 保存 委托单
     * @desc:
     */
    private void saveBuildConsignOrder(IdEnter enter, OpeInvoiceOrder opeInvoiceOrder) {
        SaveConsignEnter saveConsignEnter = new SaveConsignEnter();
        BeanUtils.copyProperties(enter, saveConsignEnter);
        saveConsignEnter.setId(null);
        saveConsignEnter.setInvoiceId(opeInvoiceOrder.getId());
        saveConsignEnter.setInvoiceNo(opeInvoiceOrder.getInvoiceNo());
        saveConsignEnter.setEntrustType(opeInvoiceOrder.getTransType());
        saveConsignEnter.setConsignorQty(opeInvoiceOrder.getInvoiceQty());
        saveConsignEnter.setDeliveryDate(opeInvoiceOrder.getDeliveryDate());
        saveConsignEnter.setTransType(opeInvoiceOrder.getTransType());
        saveConsignEnter.setConsigneeUser(opeInvoiceOrder.getConsigneeUser());
        saveConsignEnter.setConsigneeCountryCode(opeInvoiceOrder.getConsigneeUserTelephone());
        saveConsignEnter.setConsigneeAddress(opeInvoiceOrder.getConsigneeAddress());
        saveConsignEnter.setConsigneeUserMail(opeInvoiceOrder.getConsigneeUserMail());
        saveConsignEnter.setConsigneePostCode(opeInvoiceOrder.getConsigneePostCode());
        saveConsignEnter.setConsignorCountryCode(opeInvoiceOrder.getConsignorCountryCode());
        saveConsignEnter.setConsignorUser(opeInvoiceOrder.getConsignorUser());
        saveConsignEnter.setConsignorMail(opeInvoiceOrder.getConsignorMail());
        saveConsignEnter.setNotifyUserName(opeInvoiceOrder.getNotifyUserName());
        saveConsignEnter.setNotifyUser(opeInvoiceOrder.getNotifyUser());
        saveConsignEnter.setNotifyCountryCode(opeInvoiceOrder.getNotifyCountryCode());
        saveConsignEnter.setNotifyUserMail(opeInvoiceOrder.getNotifyUserMail());
        saveConsignEnter.setNotifyUserTelephone(opeInvoiceOrder.getNotifyUserTelephone());
        saveConsignEnter.setRemark(opeInvoiceOrder.getRemark());
        consignOrderService.save(saveConsignEnter);
    }


     /**
      * @Author Aleks
      * @Description  采购单取消的时候  下面的发货单也要取消
      * @Date  2020/10/30 16:11
      * @Param [purchaseId, userId, remark]
      * @return
      **/
    @Override
    public void cancelInvoice(Long purchaseId,Long userId,String remark) {
        QueryWrapper<OpeInvoiceOrder> qw = new QueryWrapper<>();
        qw.eq(OpeInvoiceOrder.COL_PURCHASE_ID,purchaseId);
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getOne(qw);
        if(invoiceOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        invoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.CANCEL.getValue());
        opeInvoiceOrderService.saveOrUpdate(invoiceOrder);
        // 操作动态
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(idAppService.getId(SequenceName.OPE_OP_TRACE),invoiceOrder.getId(),OrderTypeEnums.INVOICE.getValue(),OrderOperationTypeEnums.CANCEL.getValue(),remark);
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW),invoiceOrder.getInvoiceStatus(),OrderTypeEnums.INVOICE.getValue(),invoiceOrder.getId(),remark);
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 取消下面的出库单
        outboundOrderService.cancelOutWh(invoiceOrder.getId(),userId,remark);
    }
}
