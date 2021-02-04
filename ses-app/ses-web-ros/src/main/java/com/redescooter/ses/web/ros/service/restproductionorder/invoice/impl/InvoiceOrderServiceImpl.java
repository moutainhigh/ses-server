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
import com.redescooter.ses.web.ros.dao.base.OpeOutWhCombinBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhPartsBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhScooterBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InvoiceOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
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

    @Autowired
    private RosProductionProductServiceMapper rosProductionProductServiceMapper;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeOutWhouseOrderMapper opeOutWhouseOrderMapper;

    @Autowired
    private OpeOutWhCombinBMapper opeOutWhCombinBMapper;

    @Autowired
    private OpeOutWhPartsBMapper opeOutWhPartsBMapper;

    @Autowired
    private OpeOutWhScooterBMapper opeOutWhScooterBMapper;

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
     * @desc: 关联订单列表 关联采购单和委托单
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
        if (opePurchaseOrder != null) {
            resultList.add(new AssociatedOrderResult(opePurchaseOrder.getId(), opePurchaseOrder.getPurchaseNo(), OrderTypeEnums.SHIPPING.getValue(), opePurchaseOrder.getCreatedTime(),""));
        }
        OpeEntrustOrder opeEntrustOrder = opeEntrustOrderService.getOne(new LambdaQueryWrapper<OpeEntrustOrder>().eq(OpeEntrustOrder::getInvoiceId,opeInvoiceOrder.getId()));
        if (opeEntrustOrder != null) {
            resultList.add(new AssociatedOrderResult(opeEntrustOrder.getId(), opeEntrustOrder.getEntrustNo(), OrderTypeEnums.ORDER.getValue(), opeEntrustOrder.getCreatedTime(),""));
        }
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
            List<String> snMap =new ArrayList<>();
            Long qty = 0L;
            for (OrderProductDetailResult product : productList) {
                //序列号集合
                /*if (opeInvoiceOrder.getInvoiceType().equals(ProductTypeEnums.SCOOTER.getValue())) {

                    snMap =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getSn).collect(Collectors.toList());
                    //已发货数量
                    qty =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getQty).count();
                } else {
                    snMap =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getSn).collect(Collectors.toList());
                    //已发货数量
                    qty =
                            snList.stream().filter(item -> (item.getColorId().equals(product.getColorId()) && item.getGroupId().equals(product.getCategoryId()))).map(InvoiceSnResult::getQty).count();
                }*/
                product.setQty(qty.intValue());
                product.setSnMap(snMap);
            }
        }else {
            productList.stream().forEach(item->{item.setQty(0);item.setSnMap(new ArrayList<>());});
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
        // 追加  如果是整车的话 判断是否有符合颜色和车型的产品，没有的话抛异常、
        if (opeInvoiceOrder.getInvoiceType() == 1){
            checkProtion(opeInvoiceOrder);
        }
        // 追加 校验中国仓库的库存是否充足
        checkStockNum(opeInvoiceOrder);
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
        purchaseOrderService.purchaseStocking(opeInvoiceOrder.getPurchaseId(), enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    // 发货单点击备货  校验中国库存是否足够
    public void checkStockNum(OpeInvoiceOrder invoiceOrder) {
        switch (invoiceOrder.getInvoiceType()) {
            case 1:
                // scooter
                QueryWrapper<OpeInvoiceScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInvoiceScooterB.COL_INVOICE_ID, invoiceOrder.getId());
                List<OpeInvoiceScooterB> scooterBList = opeInvoiceScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeInvoiceScooterB scooterB : scooterBList) {
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE, 1);
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        if (scooterStock == null) {
                            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                        }
                        if (scooterStock.getAbleStockQty() < scooterB.getQty()) {
                            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                        }

                        // 到出库单里看是否有已生成但未出库的发货单,如果有,将数量+此次的数量,如果大于库存数量,抛异常提示库存不足
                        LambdaQueryWrapper<OpeOutWhouseOrder> outWrapper = new LambdaQueryWrapper<>();
                        outWrapper.eq(OpeOutWhouseOrder::getDr, DelStatusEnum.VALID.getCode());
                        outWrapper.eq(OpeOutWhouseOrder::getRelationType, 3);
                        outWrapper.eq(OpeOutWhouseOrder::getRelationId, scooterB.getInvoiceId());
                        outWrapper.last("limit 1");
                        OpeOutWhouseOrder outOrder = opeOutWhouseOrderMapper.selectOne(outWrapper);
                        if (null != outOrder) {
                            LambdaQueryWrapper<OpeOutWhScooterB> qw = new LambdaQueryWrapper<>();
                            qw.eq(OpeOutWhScooterB::getDr, DelStatusEnum.VALID.getCode());
                            qw.eq(OpeOutWhScooterB::getOutWhId, outOrder.getId());
                            qw.eq(OpeOutWhScooterB::getAlreadyOutWhQty, 0);
                            qw.last("limit 1");
                            OpeOutWhScooterB outScooterB = opeOutWhScooterBMapper.selectOne(qw);
                            if (null != outScooterB) {
                                Integer outQty = outScooterB.getQty();
                                Integer invoiceQty = scooterB.getQty();
                                int total = invoiceQty + outQty;
                                if (total < scooterStock.getAbleStockQty()) {
                                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                                }
                            }
                        }
                    }
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeInvoiceCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInvoiceCombinB.COL_INVOICE_ID, invoiceOrder.getId());
                List<OpeInvoiceCombinB> combinBList = opeInvoiceCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)) {
                    // 找到中国仓库的组装件的库存
                    QueryWrapper<OpeWmsCombinStock> wmsCombinStockQueryWrapper = new QueryWrapper<>();
                    wmsCombinStockQueryWrapper.in(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinBList.stream().map(OpeInvoiceCombinB::getProductionCombinBomId).collect(Collectors.toList()));
                    wmsCombinStockQueryWrapper.eq(OpeWmsCombinStock.COL_STOCK_TYPE, 1);
                    List<OpeWmsCombinStock> combinStockList = opeWmsCombinStockService.list(wmsCombinStockQueryWrapper);
                    if (CollectionUtils.isEmpty(combinStockList)) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    if (combinBList.size() > combinStockList.size()) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    for (OpeInvoiceCombinB combinB : combinBList) {
                        for (OpeWmsCombinStock combinStock : combinStockList) {
                            if (combinB.getProductionCombinBomId().equals(combinStock.getProductionCombinBomId()) && combinB.getQty() > combinStock.getAbleStockQty()) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }

                            // 到出库单里看是否有已生成但未出库的发货单,如果有,将数量+此次的数量,如果大于库存数量,抛异常提示库存不足
                            LambdaQueryWrapper<OpeOutWhouseOrder> outWrapper = new LambdaQueryWrapper<>();
                            outWrapper.eq(OpeOutWhouseOrder::getDr, DelStatusEnum.VALID.getCode());
                            outWrapper.eq(OpeOutWhouseOrder::getRelationType, 3);
                            outWrapper.eq(OpeOutWhouseOrder::getRelationId, combinB.getInvoiceId());
                            outWrapper.last("limit 1");
                            OpeOutWhouseOrder outOrder = opeOutWhouseOrderMapper.selectOne(outWrapper);
                            if (null != outOrder) {
                                LambdaQueryWrapper<OpeOutWhCombinB> qw = new LambdaQueryWrapper<>();
                                qw.eq(OpeOutWhCombinB::getDr, DelStatusEnum.VALID.getCode());
                                qw.eq(OpeOutWhCombinB::getOutWhId, outOrder.getId());
                                qw.eq(OpeOutWhCombinB::getAlreadyOutWhQty, 0);
                                qw.last("limit 1");
                                OpeOutWhCombinB outCombinB = opeOutWhCombinBMapper.selectOne(qw);
                                if (null != outCombinB) {
                                    Integer outQty = outCombinB.getQty();
                                    Integer invoiceQty = combinB.getQty();
                                    int total = invoiceQty + outQty;
                                    if (total < combinStock.getAbleStockQty()) {
                                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInvoicePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeInvoicePartsB.COL_INVOICE_ID, invoiceOrder.getId());
                List<OpeInvoicePartsB> partsBList = opeInvoicePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)) {
                    // 找到中国仓库的原料库存
                    QueryWrapper<OpeWmsPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                    partsStockQueryWrapper.in(OpeWmsPartsStock.COL_PARTS_ID, partsBList.stream().map(OpeInvoicePartsB::getPartsId).collect(Collectors.toList()));
                    partsStockQueryWrapper.eq(OpeWmsPartsStock.COL_STOCK_TYPE, 1);
                    List<OpeWmsPartsStock> partsStockList = opeWmsPartsStockService.list(partsStockQueryWrapper);
                    if (CollectionUtils.isEmpty(partsStockList)) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    if (partsBList.size() > partsStockList.size()) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    for (OpeInvoicePartsB partsB : partsBList) {
                        for (OpeWmsPartsStock partsStock : partsStockList) {
                            if (partsB.getPartsId().equals(partsStock.getPartsId()) && partsB.getQty() > partsStock.getAbleStockQty()) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }

                            // 到出库单里看是否有已生成但未出库的发货单,如果有,将数量+此次的数量,如果大于库存数量,抛异常提示库存不足
                            LambdaQueryWrapper<OpeOutWhouseOrder> outWrapper = new LambdaQueryWrapper<>();
                            outWrapper.eq(OpeOutWhouseOrder::getDr, DelStatusEnum.VALID.getCode());
                            outWrapper.eq(OpeOutWhouseOrder::getRelationType, 3);
                            outWrapper.eq(OpeOutWhouseOrder::getRelationId, partsB.getInvoiceId());
                            outWrapper.last("limit 1");
                            OpeOutWhouseOrder outOrder = opeOutWhouseOrderMapper.selectOne(outWrapper);
                            if (null != outOrder) {
                                LambdaQueryWrapper<OpeOutWhPartsB> qw = new LambdaQueryWrapper<>();
                                qw.eq(OpeOutWhPartsB::getDr, DelStatusEnum.VALID.getCode());
                                qw.eq(OpeOutWhPartsB::getOutWhId, outOrder.getId());
                                qw.eq(OpeOutWhPartsB::getAlreadyOutWhQty, 0);
                                qw.last("limit 1");
                                OpeOutWhPartsB outPartsB = opeOutWhPartsBMapper.selectOne(qw);
                                if (null != outPartsB) {
                                    Integer outQty = outPartsB.getQty();
                                    Integer invoiceQty = partsB.getQty();
                                    int total = invoiceQty + outQty;
                                    if (total < partsStock.getAbleStockQty()) {
                                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }



    // 如果是整车的话 判断是否有符合颜色和车型的产品
    public void checkProtion(OpeInvoiceOrder invoiceOrder){
        QueryWrapper<OpeInvoiceScooterB> scooterBQueryWrapper = new QueryWrapper<>();
        scooterBQueryWrapper.eq(OpeInvoiceScooterB.COL_INVOICE_ID,invoiceOrder.getId());
        List<OpeInvoiceScooterB> scooterBS = opeInvoiceScooterBService.list(scooterBQueryWrapper);
        if(CollectionUtils.isNotEmpty(scooterBS)){
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (OpeInvoiceScooterB scooterB : scooterBS) {
                Map<String,Object> map = new HashMap<>();
                map.put(OpeProductionScooterBom.COL_GROUP_ID,scooterB.getGroupId());
                map.put(OpeProductionScooterBom.COL_COLOR_ID,scooterB.getColorId());
                listMap.add(map);
            }
            // 找到符合条件的整车产品
            List<OpeProductionScooterBom>  scooterBomList = rosProductionProductServiceMapper.getByGroupAndColorIds(listMap);
            if (CollectionUtils.isEmpty(scooterBomList)){
                // 说明存在没有这种产品的整车
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_DOES_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_DOES_NOT_EXIST.getMessage());
            }
            // 对查询出来的结果 根据分组和颜色进行分组 (嵌套分组)
//            Map<Long, Map<Long, List<OpeProductionScooterBom>>> map = scooterBomList.stream().collect(Collectors.groupingBy(OpeProductionScooterBom::getGroupId, Collectors.groupingBy(OpeProductionScooterBom::getColorId)));
            Map<String, List<OpeProductionScooterBom>> map = scooterBomList.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
            // 因为下单的时候 可能会出现 同分组颜色的情况，所以scooterBS需要先根据分组颜色来先进行分组 （多字段自定义分组）  再比较
            Map<String, List<OpeInvoiceScooterB>> map1 = scooterBS.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
            if (map1.size() > map.size()) {
                // 说明存在没有这种产品的整车
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_DOES_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_DOES_NOT_EXIST.getMessage());
            }
        }
    }



    // 多字段自定义分组
    private static String fetchGroupKey(OpeInvoiceScooterB scooterB){
        // 按照分组和颜色进行分组
        return scooterB.getGroupId() +""+scooterB.getColorId();
    }

    // 多字段自定义分组
    private static String fetchGroupKey1(OpeProductionScooterBom scooterB){
        // 按照分组和颜色进行分组
        return scooterB.getGroupId() +""+scooterB.getColorId();
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
        if (StringUtils.isNotBlank(enter.getKeyword())) {
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_FIRST_NAME, enter.getKeyword());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_LAST_NAME, enter.getKeyword());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_TELEPHONE, enter.getKeyword());
            queryStaffResultQueryWrapper.like(OpeSysStaff.COL_EMAIL, enter.getKeyword());
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

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeInvoiceOrder.getInvoiceStatus(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getId(), null);
        BeanUtils.copyProperties(enter, orderStatusFlowEnter);
        orderStatusFlowService.save(orderStatusFlowEnter);
        //采购单签收
        purchaseOrderService.purchaseSign(opeInvoiceOrder.getPurchaseId(), enter.getUserId());
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
                            opeInvoiceScooterBList.stream().map(item -> new ProductEnter(null, item.getColorId(), item.getGroupId(), item.getQty(), item.getRemark(),item.getQty())).collect(Collectors.toList());
                }
                break;

            case 2:
                List<OpeInvoiceCombinB> invoiceCombinBList = opeInvoiceCombinBService.list(new LambdaQueryWrapper<OpeInvoiceCombinB>().eq(OpeInvoiceCombinB::getInvoiceId, opeInvoiceOrder.getId()));
                if (CollectionUtils.isNotEmpty(invoiceCombinBList)) {
                    productEnterList =
                            invoiceCombinBList.stream().map(item -> new ProductEnter(item.getProductionCombinBomId(), null, null, item.getQty(), item.getRemark(),item.getQty())).collect(Collectors.toList());
                }
                break;

            default:
                List<OpeInvoicePartsB> opeInvoicePartsBList = opeInvoicePartsBService.list(new LambdaQueryWrapper<OpeInvoicePartsB>().eq(OpeInvoicePartsB::getInvoiceId, opeInvoiceOrder.getId()));
                if (CollectionUtils.isNotEmpty(opeInvoicePartsBList)) {
                    productEnterList =
                            opeInvoicePartsBList.stream().map(item -> new ProductEnter(item.getPartsId(), null, null, item.getQty(), item.getRemark(),item.getQty())).collect(Collectors.toList());
                }
                break;
        }
        //出库单
        SaveOutboundOrderEnter saveOutboundOrderEnter = new SaveOutboundOrderEnter();
        BeanUtils.copyProperties(enter, saveOutboundOrderEnter);
        saveOutboundOrderEnter.setId(null);
        saveOutboundOrderEnter.setRelationId(enter.getId());
        saveOutboundOrderEnter.setRelationType(OrderTypeEnums.INVOICE.getValue());
        saveOutboundOrderEnter.setRelationNo(opeInvoiceOrder.getInvoiceNo());
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
        saveConsignEnter.setEntrustType(opeInvoiceOrder.getInvoiceType());
        saveConsignEnter.setConsignorQty(opeInvoiceOrder.getInvoiceQty());
        saveConsignEnter.setDeliveryDate(opeInvoiceOrder.getDeliveryDate());
        saveConsignEnter.setTransType(opeInvoiceOrder.getTransType());
        saveConsignEnter.setConsigneeUser(opeInvoiceOrder.getConsigneeUser());
        saveConsignEnter.setConsigneeUserTelephone(opeInvoiceOrder.getConsigneeUserTelephone());
        saveConsignEnter.setConsigneeCountryCode(opeInvoiceOrder.getConsigneeCountryCode());
        saveConsignEnter.setConsigneeAddress(opeInvoiceOrder.getConsigneeAddress());
        saveConsignEnter.setConsigneeUserMail(opeInvoiceOrder.getConsigneeUserMail());
        saveConsignEnter.setConsigneePostCode(opeInvoiceOrder.getConsigneePostCode());
        saveConsignEnter.setConsignorCountryCode(opeInvoiceOrder.getConsignorCountryCode());
        saveConsignEnter.setConsignorTelephone(opeInvoiceOrder.getConsignorTelephone());
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
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, invoiceOrder.getId(), OrderTypeEnums.INVOICE.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), remark);
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,invoiceOrder.getInvoiceStatus(),OrderTypeEnums.INVOICE.getValue(),invoiceOrder.getId(),remark);
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 取消下面的出库单
        outboundOrderService.cancelOutWh(invoiceOrder.getId(),userId,remark);
    }


    // 发货单状态变待装车
    @Override
    @Transactional
    public void invoiceWaitLoading(Long invoiceId) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(invoiceId);
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (opeInvoiceOrder.getInvoiceStatus() < InvoiceOrderStatusEnums.BE_LOADED.getValue()) {
            opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.BE_LOADED.getValue());
            opeInvoiceOrderService.saveOrUpdate(opeInvoiceOrder);
            // 状态流转
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeInvoiceOrder.getInvoiceStatus(),OrderTypeEnums.INVOICE.getValue(),opeInvoiceOrder.getId(),"");
            orderStatusFlowService.save(orderStatusFlowEnter);
        }
    }


    // 发货单状态变待签收
    @Override
    public void invoiceWaitSign(Long invoiceId,Long userId) {
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(invoiceId);
        if (opeInvoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeInvoiceOrder.getInvoiceStatus().equals(InvoiceOrderStatusEnums.BE_DELIVERED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeInvoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.BE_SIGNED.getValue());
        opeInvoiceOrderService.saveOrUpdate(opeInvoiceOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeInvoiceOrder.getInvoiceStatus(),OrderTypeEnums.INVOICE.getValue(),opeInvoiceOrder.getId(),"");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 将发货单对应的采购单状态变为待签收
        purchaseOrderService.purchaseWaitSign(opeInvoiceOrder.getPurchaseId(),userId);
    }
}
