package com.redescooter.ses.web.ros.service.restproductionorder.outbound.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.NewOutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.OutboundOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionAssemblyOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsRelationService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.ProductEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOrUpdateOutCombinBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOrUpdateOutOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOrUpdateOutPartsBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOrUpdateOutScooterBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOutboundOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: alex
 * @Date: 2020/10/22 13:27
 * @version：V ROS 1.8.3
 * @Description:
 */
@Slf4j
@Service
public class OutboundOrderServiceImpl implements OutboundOrderService {

    @Autowired
    private OutboundOrderServiceMapper outboundOrderServiceMapper;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private InvoiceOrderService invoiceOrderService;

    @Autowired
    private OpeCombinOrderService opeCombinOrderService;

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Autowired
    private OpeCombinOrderScooterBService opeCombinOrderScooterBomService;

    @Autowired
    private OpeCombinOrderCombinBService opeCombinOrderCombinBomService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    @Autowired
    private ProductionAssemblyOrderServiceMapper productionAssemblyOrderServiceMapper;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     */
    @Override
    public Map<Integer, Integer> countByType(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = outboundOrderServiceMapper.countByType(enter);
        //Map 集合
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(Collectors.toMap(item -> {
            return Integer.valueOf(item.getStatus());
        }, CountByStatusResult::getTotalCount));

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    @Override
    public Map<Integer, String> statusList(GeneralEnter enter) {
        Map<Integer, String> result = new HashMap<>();
        for (OutBoundOrderStatusEnums item : OutBoundOrderStatusEnums.values()) {
            result.put(item.getValue(), item.getMessage());
        }
        return result;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:50
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 出库单列表
     */
    @Override
    public PageResult<OutboundOrderListResult> list(OutboundOrderListEnter enter) {
        int count = outboundOrderServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, outboundOrderServiceMapper.list(enter));
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:52
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 出库单列表
     */
    @Override
    public OutboundOrderDetailResult detail(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OutboundOrderDetailResult detail = outboundOrderServiceMapper.detail(enter);
        if (detail == null) {
            return new OutboundOrderDetailResult();
        }
        //关联单据
        List<AssociatedOrderResult> associatedOrderResultList = associatedOrderList(opeOutWhouseOrder);
        detail.setAssociatedOrderList(associatedOrderResultList);
        //操作动态
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.OUTBOUND.getValue()));
        detail.setOrderOperatingList(CollectionUtils.isEmpty(opTraceResultList) ? new ArrayList<>() : opTraceResultList);
        //产品列表
        List<OrderProductDetailResult> orderProductDetailResultList = productListById(enter);
        detail.setInvoiceProductList(CollectionUtils.isEmpty(orderProductDetailResultList) ? new ArrayList<>() : orderProductDetailResultList);
        return detail;
    }

    @Override
    public GeneralResult delete(IdEnter enter) {
        opeOutWhouseOrderService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 17:37
     * @Param: enter
     * @Return: OrderProductDetailResult
     * @desc: 查询发货单产品列表
     */
    @Override
    public List<OrderProductDetailResult> productListById(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<OrderProductDetailResult> resultList = null;
        switch (opeOutWhouseOrder.getOutWhType()) {
            case 1:
                resultList = outboundOrderServiceMapper.productionScooterByBussId(enter.getId());
                if (CollectionUtils.isNotEmpty(resultList)) {
                    // 查看库存的可用数量
                    for (OrderProductDetailResult result : resultList) {
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE, opeOutWhouseOrder.getCountryType());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, result.getCategoryId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_COLOR_ID, result.getColorId());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock wmsScooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        if (wmsScooterStock != null) {
                            result.setAbleQty(wmsScooterStock.getAbleStockQty());
                        }
                    }
                }
                break;
            case 2:
                resultList = outboundOrderServiceMapper.productionCombinByBussId(enter.getId());
                if (CollectionUtils.isNotEmpty(resultList)) {
                    QueryWrapper<OpeWmsCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                    combinStockQueryWrapper.in(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, resultList.stream().map(OrderProductDetailResult::getProductId).collect(Collectors.toList()));
                    List<OpeWmsCombinStock> wmsCombinStockList = opeWmsCombinStockService.list(combinStockQueryWrapper);
                    if (CollectionUtils.isNotEmpty(wmsCombinStockList)) {
                        for (OrderProductDetailResult result : resultList) {
                            for (OpeWmsCombinStock combinStock : wmsCombinStockList) {
                                if (result.getProductId().equals(combinStock.getProductionCombinBomId())) {
                                    result.setAbleQty(combinStock.getAbleStockQty());
                                }
                            }
                        }
                    }
                }
                break;
            default:
                resultList = outboundOrderServiceMapper.productionPartByBussId(enter.getId());
                if (CollectionUtils.isNotEmpty(resultList)) {
                    QueryWrapper<OpeWmsPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                    partsStockQueryWrapper.in(OpeWmsPartsStock.COL_PARTS_ID, resultList.stream().map(OrderProductDetailResult::getProductId).collect(Collectors.toList()));
                    List<OpeWmsPartsStock> wmsPartsStockList = opeWmsPartsStockService.list(partsStockQueryWrapper);
                    if (CollectionUtils.isNotEmpty(wmsPartsStockList)) {
                        for (OrderProductDetailResult result : resultList) {
                            for (OpeWmsPartsStock partsStock : wmsPartsStockList) {
                                if (result.getProductId().equals(partsStock.getPartsId())) {
                                    result.setAbleQty(partsStock.getAbleStockQty());
                                }
                            }
                        }
                    }
                }
                break;
        }
        return resultList;
    }

    /**
     * @param opeOutWhouseOrder
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 18:14
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单列表 只关联发货单
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(OpeOutWhouseOrder opeOutWhouseOrder) {
        List<AssociatedOrderResult> associatedOrderList = new ArrayList<>();
        // 先判断关联的是哪个单据
        if (null != opeOutWhouseOrder.getRelationType() && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            //发货单
            OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(opeOutWhouseOrder.getRelationId());
            if (opeInvoiceOrder != null) {
                associatedOrderList.add(new AssociatedOrderResult(opeInvoiceOrder.getId(), opeInvoiceOrder.getInvoiceNo(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getCreatedTime(), ""));
            }
        } else if (null != opeOutWhouseOrder.getRelationType() && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // 2020 11 18 追加  出库单还可能会关联组装单
            OpeCombinOrder combinOrder = opeCombinOrderService.getById(opeOutWhouseOrder.getRelationId());
            if (combinOrder != null) {
                associatedOrderList.add(new AssociatedOrderResult(combinOrder.getId(), combinOrder.getCombinNo(), OrderTypeEnums.COMBIN_ORDER.getValue(), combinOrder.getCreatedTime(), ""));
            }
        }
        return associatedOrderList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:52
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存
     */
    @Transactional
    @Override
    public GeneralResult save(SaveOutboundOrderEnter enter) {
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getUserId());
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
//        if (OutBoundOrderTypeEnums.SALES.getValue().equals(enter.getRelationType())){
        if (OrderTypeEnums.INVOICE.getValue().equals(enter.getRelationType())) {
            OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getRelationId());
            if (opeInvoiceOrder == null) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
        } else {
            OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getRelationId());
            if (opeCombinOrder == null) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
        }

        OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
        BeanUtils.copyProperties(enter, opeOutWhouseOrder);
        opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.DRAFT.getValue());
        opeOutWhouseOrder.setCountryCode(opeSysStaff.getCountryCode());
        opeOutWhouseOrder.setMail(opeSysStaff.getEmail());
        opeOutWhouseOrder.setTelephone(opeSysStaff.getTelephone());
        opeOutWhouseOrder.setIfWh(0);
        SaveOpTraceEnter saveOpTraceEnter = null;
        if (enter.getId() == null || enter.getId() == 0) {
            opeOutWhouseOrder.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER));
            opeOutWhouseOrder.setOutWhNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())).getValue());
            opeOutWhouseOrder.setDr(0);
            opeOutWhouseOrder.setCreatedBy(enter.getUserId());
            opeOutWhouseOrder.setCreatedTime(new Date());

            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), enter.getRemark());
            orderStatusFlowEnter.setUserId(enter.getUserId());
            orderStatusFlowService.save(orderStatusFlowEnter);
        } else {
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }
        //操作动态
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setRelationId(opeOutWhouseOrder.getId());
        saveOpTraceEnter.setUserId(enter.getUserId());
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        // 由发货单备货产生的出库单  是为中国仓库的书库单
        opeOutWhouseOrder.setCountryType(1);
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);

        //保存子单据
        saveOutWhB(enter, opeOutWhouseOrder.getId());
        // 发货单生成出库单的时候 待出库的库存增加
        try {
            wmsMaterialStockService.waitOutUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 关联的发货单号下拉接口
     *
     * @param enter
     * @return
     */
    @Override
    public List<InWhRelationOrderResult> invoiceData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = outboundOrderServiceMapper.invoiceData(enter);
        return list;
    }


    /**
     * 关联的整车产品信息
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutScooterBEnter> relationInvoiceScooterData(IdEnter enter) {
        // 这里是整车
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (invoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutScooterBEnter> resultList = outboundOrderServiceMapper.relationInvoiceScooterData(enter.getId());
        List<SaveOrUpdateOutScooterBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // 追加 计算车辆的上限数量
            scooterAbleNum(invoiceOrder, resultList);
            // 追加 把上限数量为0的过滤掉
            for (SaveOrUpdateOutScooterBEnter bEnter : resultList) {
                if (bEnter.getAbleQty() > 0) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    // 计算车辆的上限数量
    private void scooterAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutScooterBEnter> resultList) {
        // 已经出库的出库单集合
        Map<String, List<OpeOutWhScooterB>> outMap = new HashMap<>();
        // 还没有出库的出库单集合
        Map<String, List<OpeOutWhScooterB>> unOutMap = new HashMap<>();
        // 找到发货单关联的所有的出库单
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // 过滤已出库的出库单
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.in(OpeOutWhScooterB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    outMap = scooterBS.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                }
            }

            // 过滤出还没有出库的出库单
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.in(OpeOutWhScooterB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                }
            }
            for (SaveOrUpdateOutScooterBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (outMap != null && outMap.size() > 0) {
                    for (String key : outMap.keySet()) {
                        if ((bEnter.getGroupId() + "" + bEnter.getColorId()).equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhScooterB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (unOutMap != null && unOutMap.size() > 0) {
                    for (String key : unOutMap.keySet()) {
                        if ((bEnter.getGroupId() + "" + bEnter.getColorId()).equals(key)) {
                            alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhScooterB::getQty).sum());
                        }
                    }
                }
                bEnter.setAbleQty(bEnter.getQty() - alreadyNum);
            }
        }
    }


    // 计算组装件的上限数量
    private void combinAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutCombinBEnter> resultList) {
        // 已经出库的出库单集合
        Map<Long, List<OpeOutWhCombinB>> outMap = new HashMap<>();
        // 还没有出库的出库单集合
        Map<Long, List<OpeOutWhCombinB>> unOutMap = new HashMap<>();
        // 找到发货单关联的所有的出库单
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // 过滤已出库的出库单
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.in(OpeOutWhCombinB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    outMap = combinBS.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                }
            }

            // 过滤出还没有出库的出库单
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.in(OpeOutWhCombinB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhCombinB> scooterBS = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                }
            }
            for (SaveOrUpdateOutCombinBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (outMap != null && outMap.size() > 0) {
                    for (Long key : outMap.keySet()) {
                        if (bEnter.getProductionCombinBomId().equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhCombinB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (unOutMap != null && unOutMap.size() > 0) {
                    for (Long key : unOutMap.keySet()) {
                        if (bEnter.getProductionCombinBomId().equals(key)) {
                            alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhCombinB::getQty).sum());
                        }
                    }
                }
                bEnter.setAbleQty(bEnter.getQty() - alreadyNum);
            }
        }
    }


    // 计算部件的上限数量
    private void partsAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutPartsBEnter> resultList) {
        // 已经出库的出库单集合
        Map<Long, List<OpeOutWhPartsB>> outMap = new HashMap<>();
        // 还没有出库的出库单集合
        Map<Long, List<OpeOutWhPartsB>> unOutMap = new HashMap<>();
        // 找到发货单关联的所有的出库单
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // 过滤已出库的出库单
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.in(OpeOutWhPartsB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhPartsB> partsBS = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    outMap = partsBS.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                }
            }

            // 过滤出还没有出库的出库单
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // 找到已出库的出库单下面的所有的车辆数据
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.in(OpeOutWhPartsB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhPartsB> scooterBS = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                }
            }
            for (SaveOrUpdateOutPartsBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (outMap != null && outMap.size() > 0) {
                    for (Long key : outMap.keySet()) {
                        if (bEnter.getPartsId().equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhPartsB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (unOutMap != null && unOutMap.size() > 0) {
                    for (Long key : unOutMap.keySet()) {
                        if (bEnter.getPartsId().equals(key)) {
                            alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhPartsB::getQty).sum());
                        }
                    }
                }
                bEnter.setAbleQty(bEnter.getQty() - alreadyNum);
            }
        }
    }


    // 按照颜色和车型  进行嵌套分组 (车辆)
    private static String fetchGroupKey(OpeOutWhScooterB scooterB) {
        // 按照分组和颜色进行分组
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }


    /**
     * 关联的组装件产品信息
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutCombinBEnter> relationInvoiceCombinData(IdEnter enter) {
        // 这里是组装件
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (invoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutCombinBEnter> resultList = outboundOrderServiceMapper.relationInvoiceCombinData(enter.getId());
        List<SaveOrUpdateOutCombinBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // 追加 计算车辆的上限数量
            combinAbleNum(invoiceOrder, resultList);
            // 追加 把上限数量为0的过滤掉
            for (SaveOrUpdateOutCombinBEnter bEnter : resultList) {
                if (bEnter.getAbleQty() > 0) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    @Override
    public List<SaveOrUpdateOutPartsBEnter> relationInvoicePartsData(IdEnter enter) {
        // 这里是部件
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (invoiceOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutPartsBEnter> resultList = outboundOrderServiceMapper.relationInvoicePartsData(enter.getId());
        List<SaveOrUpdateOutPartsBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // 追加 计算部件的上限数量
            partsAbleNum(invoiceOrder, resultList);
            // 追加 把上限数量为0的过滤掉
            for (SaveOrUpdateOutPartsBEnter bEnter : resultList) {
                if (bEnter.getAbleQty() > 0) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    /**
     * 出库单新增
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional
    public GeneralResult outOrderSave(SaveOrUpdateOutOrderEnter enter) {
        // 去空格
        SesStringUtils.objStringTrim(enter);
        OpeOutWhouseOrder orderOrder = new OpeOutWhouseOrder();
        BeanUtils.copyProperties(enter, orderOrder);
        orderOrder.setRelationId(enter.getRelationOrderId());
        orderOrder.setRelationNo(enter.getRelationOrderNo());
        orderOrder.setRelationType(enter.getRelationOrderType());
        orderOrder.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER));
        orderOrder.setOutWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())));
//        orderOrder.setOutWhNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())).getValue());
        orderOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.DRAFT.getValue());
        orderOrder.setCreatedBy(enter.getUserId());
        orderOrder.setCreatedTime(new Date());
        orderOrder.setUpdatedBy(enter.getUserId());
        orderOrder.setUpdatedTime(new Date());
        // 拿国家编码  电话  邮箱
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getUserId());
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        orderOrder.setCountryCode(opeSysStaff.getCountryCode());
        orderOrder.setTelephone(opeSysStaff.getTelephone());
        orderOrder.setMail(opeSysStaff.getEmail());
        // 处理字表 并统计出库数量
        createOutOrderB(orderOrder, enter.getSt(), enter.getUserId());
        opeOutWhouseOrderService.saveOrUpdate(orderOrder);
        // 操作状态
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, orderOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), orderOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, orderOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), orderOrder.getId(), orderOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 出库单编辑
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional
    public GeneralResult outOrderEdit(SaveOrUpdateOutOrderEnter enter) {
        OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (outWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 只有新建状态才能编辑
        if (!outWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        BeanUtils.copyProperties(enter, outWhouseOrder);
        outWhouseOrder.setUpdatedTime(new Date());
        outWhouseOrder.setUpdatedBy(enter.getUserId());
        // 编辑的时候  删除原来的产品明细
        deleteOutOrderB(outWhouseOrder);
        // 重新生成字表数据
        createOutOrderB(outWhouseOrder, enter.getSt(), enter.getUserId());
        opeOutWhouseOrderService.saveOrUpdate(outWhouseOrder);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, outWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(), outWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult outOrderSubmit(IdEnter enter) {
        OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (outWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 只有新建状态才能点击提交按钮
        if (!outWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        outWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
        outWhouseOrder.setUpdatedBy(enter.getUserId());
        outWhouseOrder.setUpdatedTime(new Date());
        opeOutWhouseOrderService.updateById(outWhouseOrder);

        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, outWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.RELEASE.getValue(), outWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);

        // 出库单提交 可用库存减少，待出库的库存增加
        try {
            wmsMaterialStockService.waitOutUp(outWhouseOrder.getOutWhType(), outWhouseOrder.getId(), 1, enter.getUserId(), outWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 关联的组装单单据号下拉数据源,由组装单创建的出库单，只可能是部件出库单
     *
     * @param enter
     * @return
     */
    @Override
    public List<InWhRelationOrderResult> relationCombinOrderData(CombinationListEnter enter) {
        List<InWhRelationOrderResult> list = productionAssemblyOrderServiceMapper.relationCombineOrderData(enter);
        return list;
    }


    /**
     * 关联的组装单的产品信息，转为部件数据,由组装单创建的出库单，只可能是部件出库单
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutPartsBEnter> relationCombinOrderDataPartsData(IdEnter enter) {
        List<SaveOrUpdateOutPartsBEnter> resultList = new ArrayList<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(enter.getId());
        if (combinOrder == null) {
            return resultList;
        }
        switch (combinOrder.getCombinType()) {
            case 1:
                // scooter
                // 车辆，先找到该组装单需要的车辆类型（车型和颜色），再找到这些车需要哪些部件组成
                QueryWrapper<OpeCombinOrderScooterB> scooterBs = new QueryWrapper<>();
                scooterBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinOrder.getId());
                List<OpeCombinOrderScooterB> combinOrderScooterBS = opeCombinOrderScooterBomService.list(scooterBs);
                if (CollectionUtils.isNotEmpty(combinOrderScooterBS)) {
                    // 到这里已经找到了需要哪些车，再找到对应的部件
                    QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                    partsRelation.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderScooterBS.stream().map(OpeCombinOrderScooterB::getScooterBomId).collect(Collectors.toList()));
                    partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 2);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // 到这里已经找到所有的部件信息了  下面就开始拼数据了
                            for (OpeProductionPartsRelation relation : partsRelations) {
                                for (OpeProductionParts productionParts : partsList) {
                                    if (relation.getPartsId().equals(productionParts.getId())) {
                                        SaveOrUpdateOutPartsBEnter partsBEnter = new SaveOrUpdateOutPartsBEnter();
                                        partsBEnter.setPartsName(productionParts.getEnName());
                                        partsBEnter.setPartsNo(productionParts.getPartsNo());
                                        partsBEnter.setPartsType(productionParts.getPartsType());
                                        Integer multipleNumber = 1;
                                        for (OpeCombinOrderScooterB combinOrderScooterB : combinOrderScooterBS) {
                                            if (combinOrderScooterB.getScooterBomId().equals(relation.getProductionId())) {
                                                multipleNumber = combinOrderScooterB.getQty();
                                            }
                                        }
                                        partsBEnter.setQty(relation.getPartsQty() * multipleNumber);
                                        partsBEnter.setPartsId(productionParts.getId());
                                        resultList.add(partsBEnter);
                                    }
                                }
                            }
                        }
                    }
                }
            default:
                break;
            case 2:
                // combin
                // 组装件，先找到该组装单需要的组装件，再找到这些组装件需要哪些部件组成
                QueryWrapper<OpeCombinOrderCombinB> combinBs = new QueryWrapper<>();
                combinBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, enter.getId());
                List<OpeCombinOrderCombinB> combinOrderCombinBS = opeCombinOrderCombinBomService.list(combinBs);
                if (CollectionUtils.isNotEmpty(combinOrderCombinBS)) {
                    // 找到该组装单需要的组装件
                    QueryWrapper<OpeProductionPartsRelation> partsRelation1 = new QueryWrapper<>();
                    partsRelation1.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderCombinBS.stream().map(OpeCombinOrderCombinB::getProductionCombinBomId).collect(Collectors.toList()));
                    partsRelation1.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 4);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation1);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // 到这里已经找到所有的部件信息了  下面就开始拼数据了
                            for (OpeProductionPartsRelation relation : partsRelations) {
                                for (OpeProductionParts productionParts : partsList) {
                                    if (relation.getPartsId().equals(productionParts.getId())) {
                                        SaveOrUpdateOutPartsBEnter partsBEnter = new SaveOrUpdateOutPartsBEnter();
                                        partsBEnter.setPartsName(productionParts.getEnName());
                                        partsBEnter.setPartsNo(productionParts.getPartsNo());
                                        partsBEnter.setPartsType(productionParts.getPartsType());
                                        Integer multipleNumber = 1;
                                        for (OpeCombinOrderCombinB combinOrderScooterB : combinOrderCombinBS) {
                                            if (combinOrderScooterB.getProductionCombinBomId().equals(relation.getProductionId())) {
                                                multipleNumber = combinOrderScooterB.getQty();
                                            }
                                        }
                                        partsBEnter.setQty(relation.getPartsQty() * multipleNumber);
                                        partsBEnter.setQty(relation.getPartsQty());
                                        partsBEnter.setPartsId(productionParts.getId());
                                        resultList.add(partsBEnter);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
        return resultList;
    }

    @Override
    @Transactional
    public GeneralResult outWhConfirm(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // 出库单出库  处理字表的数据
        comfirmOutHandleBs(opeOutWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 2020 11 17 追加  判断关联的是哪种单据类型  可能是发货单 可能是组装单
        if (opeOutWhouseOrder.getRelationType() != null && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            // 更改发货单的状态为待装车
            invoiceOrderService.invoiceWaitLoading(opeOutWhouseOrder.getRelationId());
        } else if (opeOutWhouseOrder.getRelationType() != null && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // 如果关联的是组装单  把组装单的状态变为备料完成
            productionAssemblyOrderService.materialPreparationFinish(opeOutWhouseOrder.getRelationId(), enter.getUserId());
        }
        // 出库单变为已出库 可已用库存增加，可用库存减少，待出库的库存减少
        try {
            wmsMaterialStockService.waitOutLowAbleLowUsedUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult();
    }

    private void comfirmOutHandleBs(OpeOutWhouseOrder opeOutWhouseOrder) {
        // 处理字表的数据
        switch (opeOutWhouseOrder.getOutWhType()) {
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, opeOutWhouseOrder.getId());
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    for (OpeOutWhScooterB scooterB : scooterBS) {
                        scooterB.setAlreadyOutWhQty(scooterB.getQty());
                    }
                    opeOutWhScooterBService.saveOrUpdateBatch(scooterBS);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, opeOutWhouseOrder.getId());
                List<OpeOutWhCombinB> combinBs = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBs)) {
                    for (OpeOutWhCombinB combinB : combinBs) {
                        combinB.setAlreadyOutWhQty(combinB.getQty());
                    }
                    opeOutWhCombinBService.saveOrUpdateBatch(combinBs);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, opeOutWhouseOrder.getId());
                List<OpeOutWhPartsB> partsBs = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBs)) {
                    for (OpeOutWhPartsB partsB : partsBs) {
                        partsB.setAlreadyOutWhQty(partsB.getQty());
                    }
                    opeOutWhPartsBService.saveOrUpdateBatch(partsBs);
                }
                break;
        }
    }


    public void deleteOutOrderB(OpeOutWhouseOrder outWhouseOrder) {
        switch (outWhouseOrder.getOutWhType()) {
            case 1:
                // scooter
                // 找到原来的明细  全部删除
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    opeOutWhScooterBService.removeByIds(scooterBS.stream().map(OpeOutWhScooterB::getId).collect(Collectors.toList()));
                }
            default:
                break;
            case 2:
                // combin
                // 找到原来的明细  全部删除
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    opeOutWhCombinBService.removeByIds(combinBS.stream().map(OpeOutWhCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // parts
                // 找到原来的明细  全部删除
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhPartsB> partsBS = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    opeOutWhPartsBService.removeByIds(partsBS.stream().map(OpeOutWhPartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
    }


    public void createOutOrderB(OpeOutWhouseOrder orderOrder, String st, Long userId) {
        switch (orderOrder.getOutWhType()) {
            case 1:
                // scooter
                List<SaveOrUpdateOutScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(st, SaveOrUpdateOutScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (CollectionUtils.isNotEmpty(scooterEnters)) {
                    List<OpeOutWhScooterB> scooterBList = new ArrayList<>();
                    orderOrder.setOutWhQty(scooterEnters.stream().mapToInt(SaveOrUpdateOutScooterBEnter::getQty).sum());
                    for (SaveOrUpdateOutScooterBEnter scooterEnter : scooterEnters) {
                        OpeOutWhScooterB scooterB = new OpeOutWhScooterB();
                        BeanUtils.copyProperties(scooterEnter, scooterB);
                        scooterB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_SCOOTER_B));
                        scooterB.setOutWhId(orderOrder.getId());
                        scooterB.setCreatedBy(userId);
                        scooterB.setCreatedTime(new Date());
                        scooterB.setUpdatedBy(userId);
                        scooterB.setUpdatedTime(new Date());
                        scooterBList.add(scooterB);
                    }
                    opeOutWhScooterBService.saveOrUpdateBatch(scooterBList);
                }
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateOutCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(st, SaveOrUpdateOutCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (CollectionUtils.isNotEmpty(combinBEnters)) {
                    List<OpeOutWhCombinB> combinBList = new ArrayList<>();
                    orderOrder.setOutWhQty(combinBEnters.stream().mapToInt(SaveOrUpdateOutCombinBEnter::getQty).sum());
                    for (SaveOrUpdateOutCombinBEnter combinBEnter : combinBEnters) {
                        OpeOutWhCombinB combinB = new OpeOutWhCombinB();
                        BeanUtils.copyProperties(combinBEnter, combinB);
                        combinB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_COMBIN_B));
                        combinB.setCreatedBy(userId);
                        combinB.setOutWhId(orderOrder.getId());
                        combinB.setCreatedTime(new Date());
                        combinB.setUpdatedBy(userId);
                        combinB.setUpdatedTime(new Date());
                        combinBList.add(combinB);
                    }
                    opeOutWhCombinBService.saveOrUpdateBatch(combinBList);
                }
                break;
            case 3:
                // parts
                List<SaveOrUpdateOutPartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(st, SaveOrUpdateOutPartsBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (CollectionUtils.isNotEmpty(partsBEnters)) {
                    List<OpeOutWhPartsB> partsBList = new ArrayList<>();
                    orderOrder.setOutWhQty(partsBEnters.stream().mapToInt(SaveOrUpdateOutPartsBEnter::getQty).sum());
                    for (SaveOrUpdateOutPartsBEnter partsBEnter : partsBEnters) {
                        OpeOutWhPartsB partsB = new OpeOutWhPartsB();
                        BeanUtils.copyProperties(partsBEnter, partsB);
                        partsB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_PARTS_B));
                        partsB.setOutWhId(orderOrder.getId());
                        partsB.setCreatedBy(userId);
                        partsB.setCreatedTime(new Date());
                        partsB.setUpdatedBy(userId);
                        partsB.setUpdatedTime(new Date());
                        partsBList.add(partsB);
                    }
                    opeOutWhPartsBService.saveOrUpdateBatch(partsBList);
                }
                break;
        }
    }


    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/29 11:05
     * @Param: enter
     * @Return: void
     * @desc: 保存出库单子单据
     */
    private void saveOutWhB(SaveOutboundOrderEnter enter, Long outWhId) {
        switch (enter.getOutWhType()) {
            case 1:
                List<OpeOutWhScooterB> opeOutWhScooterBList = new ArrayList<>();
                if (enter.getId() != null && enter.getId() != 0) {
                    opeOutWhScooterBService.remove(new LambdaQueryWrapper<OpeOutWhScooterB>().eq(OpeOutWhScooterB::getId, enter.getId()));
                }
                enter.getProductEnterList().forEach(item -> {
                    OpeOutWhScooterB opeOutWhScooterB = new OpeOutWhScooterB();
                    opeOutWhScooterB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_SCOOTER_B));
                    opeOutWhScooterB.setDr(0);
                    opeOutWhScooterB.setOutWhId(outWhId);
                    opeOutWhScooterB.setGroupId(item.getGroupId());
                    opeOutWhScooterB.setColorId(item.getColorId());
                    opeOutWhScooterB.setQty(item.getQty());
                    opeOutWhScooterB.setCreatedBy(enter.getUserId());
                    opeOutWhScooterB.setCreatedTime(new Date());
                    opeOutWhScooterB.setUpdatedBy(enter.getUserId());
                    opeOutWhScooterB.setUpdatedTime(new Date());
                    opeOutWhScooterBList.add(opeOutWhScooterB);
                });
                opeOutWhScooterBService.saveBatch(opeOutWhScooterBList);
                break;
            case 2:
                List<OpeOutWhCombinB> opeOutWhCombinBList = new ArrayList<>();
                if (enter.getId() != null && enter.getId() != 0) {
                    opeOutWhCombinBService.remove(new LambdaQueryWrapper<OpeOutWhCombinB>().eq(OpeOutWhCombinB::getId, enter.getId()));
                }
                //查询组装件
                List<OpeProductionCombinBom> opeProductionCombinBomList = opeProductionCombinBomService.listByIds(enter.getProductEnterList().stream().map(ProductEnter::getProductId).collect(Collectors.toSet()));
                if (CollectionUtils.isEmpty(opeProductionCombinBomList)) {
                    throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
                }
                enter.getProductEnterList().forEach(item -> {
                    OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomList.stream().filter(combin -> item.getProductId().equals(combin.getId())).findFirst().orElse(null);

                    OpeOutWhCombinB opeOutWhCombinB = new OpeOutWhCombinB();
                    opeOutWhCombinB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_COMBIN_B));
                    opeOutWhCombinB.setDr(0);
                    opeOutWhCombinB.setOutWhId(outWhId);
                    opeOutWhCombinB.setCombinName(opeProductionCombinBom.getEnName());
                    opeOutWhCombinB.setProductionCombinBomId(opeProductionCombinBom.getId());
                    opeOutWhCombinB.setQty(item.getQty());
                    opeOutWhCombinB.setCreatedBy(enter.getUserId());
                    opeOutWhCombinB.setCreatedTime(new Date());
                    opeOutWhCombinB.setUpdatedBy(enter.getUserId());
                    opeOutWhCombinB.setUpdatedTime(new Date());
                    opeOutWhCombinBList.add(opeOutWhCombinB);
                });
                opeOutWhCombinBService.saveBatch(opeOutWhCombinBList);
                break;
            default:
                List<OpeOutWhPartsB> opeOutWhPartsBList = new ArrayList<>();
                if (enter.getId() != null && enter.getId() != 0) {
                    opeOutWhPartsBService.remove(new LambdaQueryWrapper<OpeOutWhPartsB>().eq(OpeOutWhPartsB::getId, enter.getId()));
                }
                List<OpeProductionParts> opeProductionPartsList = opeProductionPartsService.listByIds(enter.getProductEnterList().stream().map(ProductEnter::getProductId).collect(Collectors.toSet()));
                if (CollectionUtils.isEmpty(opeProductionPartsList)) {
                    throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
                }
                enter.getProductEnterList().forEach(item -> {
                    OpeProductionParts opeProductionParts = opeProductionPartsList.stream().filter(combin -> item.getProductId().equals(combin.getId())).findFirst().orElse(null);

                    OpeOutWhPartsB opeOutWhPartsB = new OpeOutWhPartsB();
                    opeOutWhPartsB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_PARTS_B));
                    opeOutWhPartsB.setDr(0);
                    opeOutWhPartsB.setOutWhId(outWhId);
                    opeOutWhPartsB.setPartsId(opeProductionParts.getId());
                    opeOutWhPartsB.setPartsName(opeProductionParts.getEnName());
                    opeOutWhPartsB.setPartsNo(opeProductionParts.getPartsNo());
                    opeOutWhPartsB.setPartsType(opeProductionParts.getPartsType());
                    opeOutWhPartsB.setQty(item.getQty());
                    opeOutWhPartsB.setCreatedBy(enter.getUserId());
                    opeOutWhPartsB.setCreatedTime(new Date());
                    opeOutWhPartsB.setUpdatedBy(enter.getUserId());
                    opeOutWhPartsB.setUpdatedTime(new Date());
                    opeOutWhPartsBList.add(opeOutWhPartsB);
                });
                opeOutWhPartsBService.saveBatch(opeOutWhPartsBList);
                break;
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description 取消发货单下面的出库单
     * @Date 2020/10/30 16:20
     * @Param [invoiceId, userId, remark]
     **/
    @Override
    public void cancelOutWh(Long invoiceId, Long userId, String remark) {
        QueryWrapper<OpeOutWhouseOrder> qw = new QueryWrapper<>();
        qw.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceId);
        OpeOutWhouseOrder whouseOrder = opeOutWhouseOrderService.getOne(qw);
        if (whouseOrder != null) {
            whouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.CANCEL.getValue());
            opeOutWhouseOrderService.saveOrUpdate(whouseOrder);
            // 操作记录
            SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, whouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), remark);
            productionOrderTraceService.save(opTraceEnter);
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, whouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), whouseOrder.getId(), remark);
            orderStatusFlowService.save(orderStatusFlowEnter);
            // 出库单取消 待出库的库存减少
            try {
                wmsMaterialStockService.waitOutLow(whouseOrder.getOutWhType(), whouseOrder.getId(), 1, userId, whouseOrder.getWhType());
            } catch (Exception e) {

            }
        }
    }


    /**
     * 模拟ROS开始质检
     */
    @Override
    @Transactional
    public GeneralResult startQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 模拟ROS结束质检
     */
    @Override
    @Transactional
    public GeneralResult endQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeOutWhouseOrder.setOutStockTime(new Date());
        opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // 出库单出库  处理字表的数据
        comfirmOutHandleBs(opeOutWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 2020 11 17 追加  判断关联的是哪种单据类型  可能是发货单 可能是组装单
        if (opeOutWhouseOrder.getRelationType() != null && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            // 更改发货单的状态为待装车
            invoiceOrderService.invoiceWaitLoading(opeOutWhouseOrder.getRelationId());
        } else if (opeOutWhouseOrder.getRelationType() != null && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // 如果关联的是组装单  把组装单的状态变为备料完成
            productionAssemblyOrderService.materialPreparationFinish(opeOutWhouseOrder.getRelationId(), enter.getUserId());
        }
        // 出库单变为已出库 可已用库存增加，可用库存减少，待出库的库存减少
//        try {
//                wmsMaterialStockService.waitOutLowAbleLowUsedUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
//            } catch (Exception e) {
//
//        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 组装件点击备料  产生出库单（不管是哪种组装单都是生成部件出库单）
     *
     * @param combinId
     */
    @Override
    @Transactional
    public void createOutWhByCombin(Long combinId, Long userId) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(combinId);
        if (opeCombinOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutPartsBEnter> partsBEnterList = new ArrayList<>();
        SaveOrUpdateOutOrderEnter outOrderEnter = new SaveOrUpdateOutOrderEnter();
        outOrderEnter.setRelationOrderId(combinId);
        outOrderEnter.setRelationOrderNo(opeCombinOrder.getCombinNo());
        outOrderEnter.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
        // 由组装单生成的出库单 是部件出库单
        outOrderEnter.setOutWhType(3);
        // 由组装单生成的出库单 是组装备料出库
        outOrderEnter.setOutType(2);
        // 由组装单生成的出库单 是原料出库单
        outOrderEnter.setWhType(2);
        // 由于组装单生成的出库单  是为中国仓库的出库单
        outOrderEnter.setCountryType(1);
        // 组装单分为车辆和组装件两种  不管是哪种  都要生成部件的出库单
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                // 车辆，先找到该组装单需要的车辆类型（车型和颜色），再找到这些车需要哪些部件组成
                QueryWrapper<OpeCombinOrderScooterB> scooterBs = new QueryWrapper<>();
                scooterBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinId);
                List<OpeCombinOrderScooterB> combinOrderScooterBS = opeCombinOrderScooterBomService.list(scooterBs);
                if (CollectionUtils.isNotEmpty(combinOrderScooterBS)) {
                    // 到这里已经找到了需要哪些车，再找到对应的部件
                    QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                    partsRelation.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderScooterBS.stream().map(OpeCombinOrderScooterB::getScooterBomId).collect(Collectors.toList()));
                    partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 2);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // 到这里已经找到所有的部件信息了  下面就开始拼数据了
                            for (OpeProductionPartsRelation relation : partsRelations) {
                                for (OpeProductionParts productionParts : partsList) {
                                    if (relation.getPartsId().equals(productionParts.getId())) {
                                        SaveOrUpdateOutPartsBEnter partsBEnter = new SaveOrUpdateOutPartsBEnter();
                                        partsBEnter.setPartsName(productionParts.getEnName());
                                        partsBEnter.setPartsNo(productionParts.getPartsNo());
                                        partsBEnter.setPartsType(productionParts.getPartsType());
                                        Integer multipleNumber = 1;
                                        for (OpeCombinOrderScooterB combinOrderScooterB : combinOrderScooterBS) {
                                            if (combinOrderScooterB.getScooterBomId().equals(relation.getProductionId())) {
                                                multipleNumber = combinOrderScooterB.getQty();
                                            }
                                        }
                                        partsBEnter.setQty(relation.getPartsQty() * multipleNumber);
                                        partsBEnter.setPartsId(productionParts.getId());
                                        partsBEnterList.add(partsBEnter);
                                    }
                                }
                            }
                            // 调方法  生成出库单
                            createOutWh(outOrderEnter, partsBEnterList, userId);
                        }
                    }
                }
            default:
                break;
            case 2:
                // 组装件，先找到该组装单需要的组装件，再找到这些组装件需要哪些部件组成
                QueryWrapper<OpeCombinOrderCombinB> combinBs = new QueryWrapper<>();
                combinBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinId);
                List<OpeCombinOrderCombinB> combinOrderCombinBS = opeCombinOrderCombinBomService.list(combinBs);
                if (CollectionUtils.isNotEmpty(combinOrderCombinBS)) {
                    // 找到该组装单需要的组装件
                    QueryWrapper<OpeProductionPartsRelation> partsRelation1 = new QueryWrapper<>();
                    partsRelation1.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderCombinBS.stream().map(OpeCombinOrderCombinB::getProductionCombinBomId).collect(Collectors.toList()));
                    partsRelation1.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 4);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation1);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // 到这里已经找到所有的部件信息了  下面就开始拼数据了
                            for (OpeProductionPartsRelation relation : partsRelations) {
                                for (OpeProductionParts productionParts : partsList) {
                                    if (relation.getPartsId().equals(productionParts.getId())) {
                                        SaveOrUpdateOutPartsBEnter partsBEnter = new SaveOrUpdateOutPartsBEnter();
                                        partsBEnter.setPartsName(productionParts.getEnName());
                                        partsBEnter.setPartsNo(productionParts.getPartsNo());
                                        partsBEnter.setPartsType(productionParts.getPartsType());
                                        Integer multipleNumber = 1;
                                        for (OpeCombinOrderCombinB combinOrderScooterB : combinOrderCombinBS) {
                                            if (combinOrderScooterB.getProductionCombinBomId().equals(relation.getProductionId())) {
                                                multipleNumber = combinOrderScooterB.getQty();
                                            }
                                        }
                                        partsBEnter.setQty(relation.getPartsQty() * multipleNumber);
                                        partsBEnter.setQty(relation.getPartsQty());
                                        partsBEnter.setPartsId(productionParts.getId());
                                        partsBEnterList.add(partsBEnter);
                                    }
                                }
                            }

                            // 调方法  生成出库单
                            createOutWh(outOrderEnter, partsBEnterList, userId);
                        }
                    }
                }
                break;
        }
    }

    public void createOutWh(SaveOrUpdateOutOrderEnter outOrderEnter, List<SaveOrUpdateOutPartsBEnter> partsBEnterList, Long userId) {
        OpeOutWhouseOrder orderOrder = new OpeOutWhouseOrder();
        BeanUtils.copyProperties(outOrderEnter, orderOrder);
        orderOrder.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER));
        orderOrder.setOutWhNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())).getValue());
        orderOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
        orderOrder.setRelationId(outOrderEnter.getRelationOrderId());
        orderOrder.setRelationNo(outOrderEnter.getRelationOrderNo());
        orderOrder.setRelationType(outOrderEnter.getRelationOrderType());
        orderOrder.setCreatedBy(userId);
        orderOrder.setCreatedTime(new Date());
        orderOrder.setUpdatedBy(userId);
        orderOrder.setUpdatedTime(new Date());
        // 拿国家编码  电话  邮箱
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(userId);
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        orderOrder.setCountryCode(opeSysStaff.getCountryCode());
        orderOrder.setTelephone(opeSysStaff.getTelephone());
        orderOrder.setMail(opeSysStaff.getEmail());

        List<OpeOutWhPartsB> partsBList = new ArrayList<>();
        orderOrder.setOutWhQty(partsBEnterList.stream().mapToInt(SaveOrUpdateOutPartsBEnter::getQty).sum());
        for (SaveOrUpdateOutPartsBEnter partsBEnter : partsBEnterList) {
            OpeOutWhPartsB partsB = new OpeOutWhPartsB();
            BeanUtils.copyProperties(partsBEnter, partsB);
            partsB.setId(idAppService.getId(SequenceName.OPE_OUT_WH_PARTS_B));
            partsB.setOutWhId(orderOrder.getId());
            partsB.setCreatedBy(userId);
            partsB.setCreatedTime(new Date());
            partsB.setUpdatedBy(userId);
            partsB.setUpdatedTime(new Date());
            partsBList.add(partsB);
        }
        opeOutWhouseOrderService.saveOrUpdate(orderOrder);
        opeOutWhPartsBService.saveOrUpdateBatch(partsBList);

        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, orderOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), orderOrder.getRemark());
        productionOrderTraceService.save(opTraceEnter);

        // 创建完出库单(部件出库单)之后  需要把中国仓库的原料库的待出库数量更改
        try {
            wmsMaterialStockService.waitOutUp(orderOrder.getOutWhType(), orderOrder.getId(), 1, userId, orderOrder.getOutType());
        } catch (Exception e) {

        }
    }
}
