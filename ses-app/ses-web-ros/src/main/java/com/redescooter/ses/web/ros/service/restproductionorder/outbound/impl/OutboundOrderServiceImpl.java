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
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.utils.NumberUtil;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: alex
 * @Date: 2020/10/22 13:27
 * @version???V ROS 1.8.3
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
     * @desc: ???????????????????????????
     */
    @Override
    public Map<Integer, Integer> countByType(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = outboundOrderServiceMapper.countByType(enter);
        //Map ??????
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
     * @desc: ????????????
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
     * @desc: ???????????????
     */
    @Override
    public PageResult<OutboundOrderListResult> list(OutboundOrderListEnter enter) {
        int count = outboundOrderServiceMapper.listCount(enter);
        if (NumberUtil.eqZero(count)) {
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
     * @desc: ???????????????
     */
    @Override
    public OutboundOrderDetailResult detail(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OutboundOrderDetailResult detail = outboundOrderServiceMapper.detail(enter);
        if (StringManaConstant.entityIsNull(detail)) {
            return new OutboundOrderDetailResult();
        }
        //????????????
        List<AssociatedOrderResult> associatedOrderResultList = associatedOrderList(opeOutWhouseOrder);
        detail.setAssociatedOrderList(associatedOrderResultList);
        //????????????
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.OUTBOUND.getValue()));
        detail.setOrderOperatingList(CollectionUtils.isEmpty(opTraceResultList) ? new ArrayList<>() : opTraceResultList);
        //????????????
        List<OrderProductDetailResult> orderProductDetailResultList = productListById(enter);
        detail.setInvoiceProductList(CollectionUtils.isEmpty(orderProductDetailResultList) ? new ArrayList<>() : orderProductDetailResultList);
        return detail;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
     * @desc: ???????????????????????????
     */
    @Override
    public List<OrderProductDetailResult> productListById(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<OrderProductDetailResult> resultList = null;
        switch (opeOutWhouseOrder.getOutWhType()) {
            case 1:
                resultList = outboundOrderServiceMapper.productionScooterByBussId(enter.getId());
                if (CollectionUtils.isNotEmpty(resultList)) {
                    // ???????????????????????????
                    for (OrderProductDetailResult result : resultList) {
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE, opeOutWhouseOrder.getCountryType());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, result.getCategoryId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_COLOR_ID, result.getColorId());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock wmsScooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        if (StringManaConstant.entityIsNotNull(wmsScooterStock)) {
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
     * @desc: ?????????????????? ??????????????????
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(OpeOutWhouseOrder opeOutWhouseOrder) {
        List<AssociatedOrderResult> associatedOrderList = new ArrayList<>();
        // ?????????????????????????????????
        if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            //?????????
            OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(opeOutWhouseOrder.getRelationId());
            if (StringManaConstant.entityIsNotNull(opeInvoiceOrder)) {
                associatedOrderList.add(new AssociatedOrderResult(opeInvoiceOrder.getId(), opeInvoiceOrder.getInvoiceNo(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getCreatedTime(), ""));
            }
        } else if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // 2020 11 18 ??????  ????????????????????????????????????
            OpeCombinOrder combinOrder = opeCombinOrderService.getById(opeOutWhouseOrder.getRelationId());
            if (StringManaConstant.entityIsNotNull(combinOrder)) {
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
     * @desc: ??????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveOutboundOrderEnter enter) {
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(opeSysStaff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
//        if (OutBoundOrderTypeEnums.SALES.getValue().equals(enter.getRelationType())){
        if (OrderTypeEnums.INVOICE.getValue().equals(enter.getRelationType())) {
            OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getRelationId());
            if (StringManaConstant.entityIsNull(opeInvoiceOrder)) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
        } else {
            OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(enter.getRelationId());
            if (StringManaConstant.entityIsNull(opeCombinOrder)) {
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
        if (StringManaConstant.entityIsNull(enter.getId()) || 0 == enter.getId()) {
            opeOutWhouseOrder.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER));
            opeOutWhouseOrder.setOutWhNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())).getValue());
            opeOutWhouseOrder.setDr(0);
            opeOutWhouseOrder.setCreatedBy(enter.getUserId());
            opeOutWhouseOrder.setCreatedTime(new Date());

            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), enter.getRemark());
            //????????????
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), enter.getRemark());
            orderStatusFlowEnter.setUserId(enter.getUserId());
            orderStatusFlowService.save(orderStatusFlowEnter);
        } else {
            //????????????
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(), enter.getRemark());
        }
        //????????????
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setRelationId(opeOutWhouseOrder.getId());
        saveOpTraceEnter.setUserId(enter.getUserId());
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        // ????????????????????????????????????  ??????????????????????????????
        opeOutWhouseOrder.setCountryType(1);
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);

        //???????????????
        saveOutWhB(enter, opeOutWhouseOrder.getId());
        // ????????????????????????????????? ????????????????????????
        try {
            wmsMaterialStockService.waitOutUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ?????????????????????????????????
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
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutScooterBEnter> relationInvoiceScooterData(IdEnter enter) {
        // ???????????????
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(invoiceOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutScooterBEnter> resultList = outboundOrderServiceMapper.relationInvoiceScooterData(enter.getId());
        List<SaveOrUpdateOutScooterBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // ?????? ???????????????????????????
            scooterAbleNum(invoiceOrder, resultList);
            // ?????? ??????????????????0????????????
            for (SaveOrUpdateOutScooterBEnter bEnter : resultList) {
                if (0 < bEnter.getAbleQty()) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    // ???????????????????????????
    private void scooterAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutScooterBEnter> resultList) {
        // ??????????????????????????????
        Map<String, List<OpeOutWhScooterB>> outMap = new HashMap<>();
        // ?????????????????????????????????
        Map<String, List<OpeOutWhScooterB>> unOutMap = new HashMap<>();
        // ??????????????????????????????????????????
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // ???????????????????????????
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.in(OpeOutWhScooterB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    outMap = scooterBS.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                }
            }

            // ????????????????????????????????????
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.in(OpeOutWhScooterB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                }
            }
            for (SaveOrUpdateOutScooterBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                    for (String key : outMap.keySet()) {
                        if ((bEnter.getGroupId() + "" + bEnter.getColorId()).equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhScooterB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
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


    // ??????????????????????????????
    private void combinAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutCombinBEnter> resultList) {
        // ??????????????????????????????
        Map<Long, List<OpeOutWhCombinB>> outMap = new HashMap<>();
        // ?????????????????????????????????
        Map<Long, List<OpeOutWhCombinB>> unOutMap = new HashMap<>();
        // ??????????????????????????????????????????
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // ???????????????????????????
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.in(OpeOutWhCombinB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    outMap = combinBS.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                }
            }

            // ????????????????????????????????????
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.in(OpeOutWhCombinB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhCombinB> scooterBS = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                }
            }
            for (SaveOrUpdateOutCombinBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                    for (Long key : outMap.keySet()) {
                        if (bEnter.getProductionCombinBomId().equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhCombinB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
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


    // ???????????????????????????
    private void partsAbleNum(OpeInvoiceOrder invoiceOrder, List<SaveOrUpdateOutPartsBEnter> resultList) {
        // ??????????????????????????????
        Map<Long, List<OpeOutWhPartsB>> outMap = new HashMap<>();
        // ?????????????????????????????????
        Map<Long, List<OpeOutWhPartsB>> unOutMap = new HashMap<>();
        // ??????????????????????????????????????????
        QueryWrapper<OpeOutWhouseOrder> outWhouseOrderQueryWrapper = new QueryWrapper<>();
        outWhouseOrderQueryWrapper.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceOrder.getId());
        List<OpeOutWhouseOrder> outWhouseOrderList = opeOutWhouseOrderService.list(outWhouseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(outWhouseOrderList)) {
            // ???????????????????????????
            List<OpeOutWhouseOrder> outWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(outWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.in(OpeOutWhPartsB.COL_OUT_WH_ID, outWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhPartsB> partsBS = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    outMap = partsBS.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                }
            }

            // ????????????????????????????????????
            List<OpeOutWhouseOrder> unOutWhList = outWhouseOrderList.stream().filter(o -> o.getOutWhStatus() == -1 || o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unOutWhList)) {
                // ?????????????????????????????????????????????????????????
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.in(OpeOutWhPartsB.COL_OUT_WH_ID, unOutWhList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                List<OpeOutWhPartsB> scooterBS = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    unOutMap = scooterBS.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                }
            }
            for (SaveOrUpdateOutPartsBEnter bEnter : resultList) {
                Integer alreadyNum = 0;
                if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                    for (Long key : outMap.keySet()) {
                        if (bEnter.getPartsId().equals(key)) {
                            alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhPartsB::getAlreadyOutWhQty).sum());
                        }
                    }
                }
                if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
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


    // ?????????????????????  ?????????????????? (??????)
    private static String fetchGroupKey(OpeOutWhScooterB scooterB) {
        // ?????????????????????????????????
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }


    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutCombinBEnter> relationInvoiceCombinData(IdEnter enter) {
        // ??????????????????
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(invoiceOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutCombinBEnter> resultList = outboundOrderServiceMapper.relationInvoiceCombinData(enter.getId());
        List<SaveOrUpdateOutCombinBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // ?????? ???????????????????????????
            combinAbleNum(invoiceOrder, resultList);
            // ?????? ??????????????????0????????????
            for (SaveOrUpdateOutCombinBEnter bEnter : resultList) {
                if (0 < bEnter.getAbleQty()) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    @Override
    public List<SaveOrUpdateOutPartsBEnter> relationInvoicePartsData(IdEnter enter) {
        // ???????????????
        OpeInvoiceOrder invoiceOrder = opeInvoiceOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(invoiceOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutPartsBEnter> resultList = outboundOrderServiceMapper.relationInvoicePartsData(enter.getId());
        List<SaveOrUpdateOutPartsBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            // ?????? ???????????????????????????
            partsAbleNum(invoiceOrder, resultList);
            // ?????? ??????????????????0????????????
            for (SaveOrUpdateOutPartsBEnter bEnter : resultList) {
                if (0 < bEnter.getAbleQty()) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult outOrderSave(SaveOrUpdateOutOrderEnter enter) {
        // ?????????
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
        // ???????????????  ??????  ??????
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(opeSysStaff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        orderOrder.setCountryCode(opeSysStaff.getCountryCode());
        orderOrder.setTelephone(opeSysStaff.getTelephone());
        orderOrder.setMail(opeSysStaff.getEmail());
        // ???????????? ?????????????????????
        createOutOrderB(orderOrder, enter.getSt(), enter.getUserId());
        opeOutWhouseOrderService.saveOrUpdate(orderOrder);
        // ????????????
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, orderOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), orderOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // ????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, orderOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), orderOrder.getId(), orderOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(String.valueOf(orderOrder.getId()));
    }


    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult outOrderEdit(SaveOrUpdateOutOrderEnter enter) {
        OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(outWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????
        if (!outWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        BeanUtils.copyProperties(enter, outWhouseOrder);
        outWhouseOrder.setUpdatedTime(new Date());
        outWhouseOrder.setUpdatedBy(enter.getUserId());
        // ???????????????  ???????????????????????????
        deleteOutOrderB(outWhouseOrder);
        // ????????????????????????
        createOutOrderB(outWhouseOrder, enter.getSt(), enter.getUserId());
        opeOutWhouseOrderService.saveOrUpdate(outWhouseOrder);
        // ????????????
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, outWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(), outWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        return new GeneralResult(String.valueOf(outWhouseOrder.getId()));
    }

    @Override
    public GeneralResult outOrderSubmit(IdEnter enter) {
        OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(outWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????????????????
        if (!outWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        outWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
        outWhouseOrder.setUpdatedBy(enter.getUserId());
        outWhouseOrder.setUpdatedTime(new Date());
        opeOutWhouseOrderService.updateById(outWhouseOrder);

        // ????????????
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, outWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.RELEASE.getValue(), outWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);

        // ??????????????? ?????????????????????????????????????????????
        try {
            wmsMaterialStockService.waitOutUp(outWhouseOrder.getOutWhType(), outWhouseOrder.getId(), 1, enter.getUserId(), outWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ??????????????????????????????????????????,????????????????????????????????????????????????????????????
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
     * ??????????????????????????????????????????????????????,????????????????????????????????????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveOrUpdateOutPartsBEnter> relationCombinOrderDataPartsData(IdEnter enter) {
        List<SaveOrUpdateOutPartsBEnter> resultList = new ArrayList<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(combinOrder)) {
            return resultList;
        }
        switch (combinOrder.getCombinType()) {
            case 1:
                // scooter
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<OpeCombinOrderScooterB> scooterBs = new QueryWrapper<>();
                scooterBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinOrder.getId());
                List<OpeCombinOrderScooterB> combinOrderScooterBS = opeCombinOrderScooterBomService.list(scooterBs);
                if (CollectionUtils.isNotEmpty(combinOrderScooterBS)) {
                    // ??????????????????????????????????????????????????????????????????
                    QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                    partsRelation.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderScooterBS.stream().map(OpeCombinOrderScooterB::getScooterBomId).collect(Collectors.toList()));
                    partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 2);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // ?????????????????????????????????????????????  ???????????????????????????
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
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<OpeCombinOrderCombinB> combinBs = new QueryWrapper<>();
                combinBs.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, enter.getId());
                List<OpeCombinOrderCombinB> combinOrderCombinBS = opeCombinOrderCombinBomService.list(combinBs);
                if (CollectionUtils.isNotEmpty(combinOrderCombinBS)) {
                    // ????????????????????????????????????
                    QueryWrapper<OpeProductionPartsRelation> partsRelation1 = new QueryWrapper<>();
                    partsRelation1.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderCombinBS.stream().map(OpeCombinOrderCombinB::getProductionCombinBomId).collect(Collectors.toList()));
                    partsRelation1.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 4);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation1);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // ?????????????????????????????????????????????  ???????????????????????????
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult outWhConfirm(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // ???????????????  ?????????????????????
        comfirmOutHandleBs(opeOutWhouseOrder);
        // ????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 2020 11 17 ??????  ????????????????????????????????????  ?????????????????? ??????????????????
        if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            // ????????????????????????????????????
            invoiceOrderService.invoiceWaitLoading(opeOutWhouseOrder.getRelationId());
        } else if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // ???????????????????????????  ???????????????????????????????????????
            productionAssemblyOrderService.materialPreparationFinish(opeOutWhouseOrder.getRelationId(), enter.getUserId());
        }
        // ???????????????????????? ?????????????????????????????????????????????????????????????????????
        try {
            wmsMaterialStockService.waitOutLowAbleLowUsedUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
        } catch (Exception e) {

        }
        return new GeneralResult();
    }

    private void comfirmOutHandleBs(OpeOutWhouseOrder opeOutWhouseOrder) {
        // ?????????????????????
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
                // ?????????????????????  ????????????
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
                // ?????????????????????  ????????????
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    opeOutWhCombinBService.removeByIds(combinBS.stream().map(OpeOutWhCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // parts
                // ?????????????????????  ????????????
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
        boolean flag = false;
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
                    for (SaveOrUpdateOutScooterBEnter scooterEnter : scooterEnters) {
                        if (StringManaConstant.entityIsNull(scooterEnter.getQty())) {
                            flag = true;
                            break;
                        }
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
                    if (flag) {
                        throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                    }
                    orderOrder.setOutWhQty(scooterEnters.stream().mapToInt(SaveOrUpdateOutScooterBEnter::getQty).sum());
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
                    for (SaveOrUpdateOutCombinBEnter combinBEnter : combinBEnters) {
                        if (StringManaConstant.entityIsNull(combinBEnter.getQty())) {
                            flag = true;
                            break;
                        }
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
                    if (flag) {
                        throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                    }
                    orderOrder.setOutWhQty(combinBEnters.stream().mapToInt(SaveOrUpdateOutCombinBEnter::getQty).sum());
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
                    for (SaveOrUpdateOutPartsBEnter partsBEnter : partsBEnters) {
                        if (StringManaConstant.entityIsNull(partsBEnter.getQty())) {
                            flag = true;
                            break;
                        }
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
                    if (flag) {
                        throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                    }
                    orderOrder.setOutWhQty(partsBEnters.stream().mapToInt(SaveOrUpdateOutPartsBEnter::getQty).sum());
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
     * @desc: ????????????????????????
     */
    private void saveOutWhB(SaveOutboundOrderEnter enter, Long outWhId) {
        switch (enter.getOutWhType()) {
            case 1:
                List<OpeOutWhScooterB> opeOutWhScooterBList = new ArrayList<>();
                if (StringManaConstant.entityIsNotNull(enter.getId()) && 0 != enter.getId()) {
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
                if (StringManaConstant.entityIsNotNull(enter.getId()) && 0 != enter.getId()) {
                    opeOutWhCombinBService.remove(new LambdaQueryWrapper<OpeOutWhCombinB>().eq(OpeOutWhCombinB::getId, enter.getId()));
                }
                //???????????????
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
                if (StringManaConstant.entityIsNotNull(enter.getId()) && 0 != enter.getId()) {
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
     * @Description ?????????????????????????????????
     * @Date 2020/10/30 16:20
     * @Param [invoiceId, userId, remark]
     **/
    @Override
    public void cancelOutWh(Long invoiceId, Long userId, String remark) {
        QueryWrapper<OpeOutWhouseOrder> qw = new QueryWrapper<>();
        qw.eq(OpeOutWhouseOrder.COL_RELATION_ID, invoiceId);
        OpeOutWhouseOrder whouseOrder = opeOutWhouseOrderService.getOne(qw);
        if (StringManaConstant.entityIsNotNull(whouseOrder)) {
            whouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.CANCEL.getValue());
            opeOutWhouseOrderService.saveOrUpdate(whouseOrder);
            // ????????????
            SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, whouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), remark);
            productionOrderTraceService.save(opTraceEnter);
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, whouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), whouseOrder.getId(), remark);
            orderStatusFlowService.save(orderStatusFlowEnter);
            // ??????????????? ????????????????????????
            try {
                wmsMaterialStockService.waitOutLow(whouseOrder.getOutWhType(), whouseOrder.getId(), 1, userId, whouseOrder.getWhType());
            } catch (Exception e) {

            }
        }
    }


    /**
     * ??????ROS????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult startQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // ????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????ROS????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult endQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        opeOutWhouseOrder.setOutStockTime(new Date());
        opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // ???????????????  ?????????????????????
        comfirmOutHandleBs(opeOutWhouseOrder);
        // ????????????
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 2020 11 17 ??????  ????????????????????????????????????  ?????????????????? ??????????????????
        if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())) {
            // ????????????????????????????????????
            invoiceOrderService.invoiceWaitLoading(opeOutWhouseOrder.getRelationId());
        } else if (StringManaConstant.entityIsNotNull(opeOutWhouseOrder.getRelationType()) && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // ???????????????????????????  ???????????????????????????????????????
            productionAssemblyOrderService.materialPreparationFinish(opeOutWhouseOrder.getRelationId(), enter.getUserId());
        }
        // ???????????????????????? ?????????????????????????????????????????????????????????????????????
//        try {
//                wmsMaterialStockService.waitOutLowAbleLowUsedUp(opeOutWhouseOrder.getOutWhType(), opeOutWhouseOrder.getId(), 1, enter.getUserId(), opeOutWhouseOrder.getWhType());
//            } catch (Exception e) {
//
//        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ?????????????????????  ????????????????????????????????????????????????????????????????????????
     *
     * @param combinId
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void createOutWhByCombin(Long combinId, Long userId) {
        OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(combinId);
        if (StringManaConstant.entityIsNull(opeCombinOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<SaveOrUpdateOutPartsBEnter> partsBEnterList = new ArrayList<>();
        SaveOrUpdateOutOrderEnter outOrderEnter = new SaveOrUpdateOutOrderEnter();
        outOrderEnter.setRelationOrderId(combinId);
        outOrderEnter.setRelationOrderNo(opeCombinOrder.getCombinNo());
        outOrderEnter.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
        // ?????????????????????????????? ??????????????????
        outOrderEnter.setOutWhType(3);
        // ?????????????????????????????? ?????????????????????
        outOrderEnter.setOutType(2);
        // ?????????????????????????????? ??????????????????
        outOrderEnter.setWhType(2);
        // ?????????????????????????????????  ??????????????????????????????
        outOrderEnter.setCountryType(1);
        // ???????????????????????????????????????  ???????????????  ??????????????????????????????
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<OpeCombinOrderScooterB> scooterBs = new QueryWrapper<>();
                scooterBs.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinId);
                List<OpeCombinOrderScooterB> combinOrderScooterBS = opeCombinOrderScooterBomService.list(scooterBs);
                if (CollectionUtils.isNotEmpty(combinOrderScooterBS)) {
                    // ??????????????????????????????????????????????????????????????????
                    QueryWrapper<OpeProductionPartsRelation> partsRelation = new QueryWrapper<>();
                    partsRelation.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderScooterBS.stream().map(OpeCombinOrderScooterB::getScooterBomId).collect(Collectors.toList()));
                    partsRelation.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 2);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // ?????????????????????????????????????????????  ???????????????????????????
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
                            // ?????????  ???????????????
                            createOutWh(outOrderEnter, partsBEnterList, userId);
                        }
                    }
                }
            default:
                break;
            case 2:
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<OpeCombinOrderCombinB> combinBs = new QueryWrapper<>();
                combinBs.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, combinId);
                List<OpeCombinOrderCombinB> combinOrderCombinBS = opeCombinOrderCombinBomService.list(combinBs);
                if (CollectionUtils.isNotEmpty(combinOrderCombinBS)) {
                    // ????????????????????????????????????
                    QueryWrapper<OpeProductionPartsRelation> partsRelation1 = new QueryWrapper<>();
                    partsRelation1.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, combinOrderCombinBS.stream().map(OpeCombinOrderCombinB::getProductionCombinBomId).collect(Collectors.toList()));
                    partsRelation1.eq(OpeProductionPartsRelation.COL_PRODUCTION_TYPE, 4);
                    List<OpeProductionPartsRelation> partsRelations = opeProductionPartsRelationService.list(partsRelation1);
                    if (CollectionUtils.isNotEmpty(partsRelations)) {
                        QueryWrapper<OpeProductionParts> parts = new QueryWrapper<>();
                        parts.in(OpeProductionParts.COL_ID, partsRelations.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toList()));
                        List<OpeProductionParts> partsList = opeProductionPartsService.list(parts);
                        if (CollectionUtils.isNotEmpty(partsList)) {
                            // ?????????????????????????????????????????????  ???????????????????????????
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
                                        partsBEnter.setPartsId(productionParts.getId());
                                        partsBEnterList.add(partsBEnter);
                                    }
                                }
                            }

                            // ?????????  ???????????????
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
        // ???????????????  ??????  ??????
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(userId);
        if (StringManaConstant.entityIsNull(opeSysStaff)) {
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

        // ????????????
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, orderOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), orderOrder.getRemark());
        productionOrderTraceService.save(opTraceEnter);

        // ??????????????????(???????????????)??????  ?????????????????????????????????????????????????????????
        try {
            wmsMaterialStockService.waitOutUp(orderOrder.getOutWhType(), orderOrder.getId(), 1, userId, orderOrder.getOutType());
        } catch (Exception e) {

        }
    }
}
