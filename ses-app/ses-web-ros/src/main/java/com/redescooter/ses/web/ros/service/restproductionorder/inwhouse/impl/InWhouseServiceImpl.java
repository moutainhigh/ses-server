package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.*;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNameInWhouseServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
@Service
@Slf4j
public class InWhouseServiceImpl implements InWhouseService {

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private InWhouseOrderScooterBServiceMapper inWhouseOrderScooterBServiceMapper;

    @Autowired
    private InWhouseOrderCombinBServiceMapper inWhouseOrderCombinBServiceMapper;

    @Autowired
    private InWhouseOrderPartsBServiceMapper inWhouseOrderPartsBServiceMapper;

    @Autowired
    private AllocateOrderServiceMapper allocateOrderServiceMapper;

    @Autowired
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;

    @Autowired
    private OpeCombinOrderService opeCombinOrderService;

    @Autowired
    private ProductionPurchasServiceMapper productionPurchasServiceMapper;

    @Autowired
    private ProductionAssemblyOrderServiceMapper productionAssemblyOrderServiceMapper;

    @Autowired
    private ProductionPurchasService productionPurchasService;

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Reference
    private IdAppService idAppService;


    @Override
    public PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = inWhouseOrderServiceMapper.totalNum(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InWhouseListResult> list = inWhouseOrderServiceMapper.inWhList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @Transactional
    public GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeInWhouseOrder inWhouseOrder = new OpeInWhouseOrder();
        BeanUtils.copyProperties(enter,inWhouseOrder);
        inWhouseOrder.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
        inWhouseOrder.setCreatedBy(enter.getUserId());
        inWhouseOrder.setCreatedTime(new Date());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.DRAFT.getValue());
        // 单据号
        inWhouseOrder.setInWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_INBOUND.getValue())));
        // 统计入库数量
        countQty(inWhouseOrder,enter.getSt());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 处理子表
        createInWhouseB(inWhouseOrder,enter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        try {
            // 如果是整车入库单或者是组装件入库单 生成的时候就是待入库状态 这个时候 要处理库存
            if (inWhouseOrder.getOrderType() == 1 || inWhouseOrder.getOrderType() == 2){
                wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(),inWhouseOrder.getId(),1,enter.getUserId());
            }
        }catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }



    public void createInWhouseB(OpeInWhouseOrder inWhouseOrder,InWhouseSaveOrUpdateEnter enter){
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseScooterB> scooterBList = new ArrayList<>();
                for (SaveOrUpdateScooterBEnter scooterEnter : scooterEnters) {
                    OpeInWhouseScooterB scooterB = new OpeInWhouseScooterB();
                    BeanUtils.copyProperties(scooterEnter,scooterB);
                    scooterB.setInWhId(inWhouseOrder.getId());
                    scooterB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_SCOOTER_B));
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterBList.add(scooterB);
                }
                opeInWhouseScooterBService.saveOrUpdateBatch(scooterBList);
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseCombinB> combinBList = new ArrayList<>();
                for (SaveOrUpdateCombinBEnter combinBEnter : combinBEnters) {
                    OpeInWhouseCombinB combinB = new OpeInWhouseCombinB();
                    BeanUtils.copyProperties(combinBEnter,combinB);
                    combinB.setInWhId(inWhouseOrder.getId());
                    combinB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_COMBIN_B));
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinBList.add(combinB);
                }
                opeInWhouseCombinBService.saveOrUpdateBatch(combinBList);
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdatePartsBEnter.class);
                    log.info("本次传过来的数据条数：：：：：：" + partsBEnters.size());
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhousePartsB> partsBList = new ArrayList<>();
                for (SaveOrUpdatePartsBEnter partsBEnter : partsBEnters) {
                    OpeInWhousePartsB partsB = new OpeInWhousePartsB();
                    BeanUtils.copyProperties(partsBEnter,partsB);
                    partsB.setInWhId(inWhouseOrder.getId());
                    partsB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_PARTS_B));
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsBList.add(partsB);
                }
                log.info("本次保存的数据条数：：：：：：" + partsBList.size());
                opeInWhousePartsBService.saveOrUpdateBatch(partsBList);
                break;
        }
    }



    public void countQty(OpeInWhouseOrder inWhouseOrder,String st){
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(st, SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(scooterEnters.stream().mapToInt(SaveOrUpdateScooterBEnter::getInWhQty).sum());
                if (null != inWhouseOrder.getRelationOrderId()){
                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                }
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(st, SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(combinBEnters.stream().mapToInt(SaveOrUpdateCombinBEnter::getInWhQty).sum());
                if (null != inWhouseOrder.getRelationOrderId()){
                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                }
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(st, SaveOrUpdatePartsBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                inWhouseOrder.setInWhQty(partsBEnters.stream().mapToInt(SaveOrUpdatePartsBEnter::getInWhQty).sum());
                if (null != inWhouseOrder.getRelationOrderId()){
                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
                }
                break;
        }
    }



    @Override
    @Transactional
    public GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        BeanUtils.copyProperties(enter,inWhouseOrder);
        inWhouseOrder.setUpdatedTime(new Date());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        countQty(inWhouseOrder,enter.getSt());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 编辑的时候 先把下面的产品删除  再重新插入
        deleteOrderB(inWhouseOrder);
        // 处理子表
        createInWhouseB(inWhouseOrder,enter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        return new GeneralResult(enter.getRequestId());
    }


    // 删除入库单下面的子表数据
    public void deleteOrderB(OpeInWhouseOrder inWhouseOrder){
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                QueryWrapper<OpeInWhouseScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeInWhouseScooterB.COL_IN_WH_ID,inWhouseOrder.getId());
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)){
                    opeInWhouseScooterBService.removeByIds(scooterBList.stream().map(OpeInWhouseScooterB::getId).collect(Collectors.toList()));
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeInWhouseCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeInWhouseCombinB.COL_IN_WH_ID,inWhouseOrder.getId());
                List<OpeInWhouseCombinB> combinBList = opeInWhouseCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBList)){
                    opeInWhouseCombinBService.removeByIds(combinBList.stream().map(OpeInWhouseCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInWhousePartsB> parts = new QueryWrapper<>();
                parts.eq(OpeInWhousePartsB.COL_IN_WH_ID,inWhouseOrder.getId());
                List<OpeInWhousePartsB> partsBList = opeInWhousePartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBList)){
                    opeInWhousePartsBService.removeByIds(partsBList.stream().map(OpeInWhousePartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
    }


    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        QueryWrapper<OpeInWhouseOrder> scooter = new QueryWrapper<>();
        scooter.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.SCOOTER.getValue());
        map.put("1",opeInWhouseOrderService.count(scooter));

        QueryWrapper<OpeInWhouseOrder> combin = new QueryWrapper<>();
        combin.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.COMBINATION.getValue());
        map.put("2",opeInWhouseOrderService.count(combin));

        QueryWrapper<OpeInWhouseOrder> parts = new QueryWrapper<>();
        parts.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.PARTS.getValue());
        map.put("3",opeInWhouseOrderService.count(parts));
        return map;
    }

    @Override
    @Transactional
    public GeneralResult inWhouseDelete(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeInWhouseOrderService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public InWhouseDetailResult inWhouseDetail(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        InWhouseDetailResult result = new InWhouseDetailResult();
        result.setId(inWhouseOrder.getId());
        result.setInWhType(inWhouseOrder.getInWhType());
        result.setOrderType(inWhouseOrder.getOrderType());
        result.setInWhNo(inWhouseOrder.getInWhNo());
        result.setInWhStatus(inWhouseOrder.getInWhStatus());
        result.setRelationOrderId(inWhouseOrder.getRelationOrderId());
        result.setRelationOrderNo(inWhouseOrder.getRelationOrderNo());
        result.setRelationOrderType(inWhouseOrder.getRelationOrderType());
        result.setWhType(inWhouseOrder.getWhType());
        // 入库单下面的产品明细
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                result.setScooters(inWhouseOrderScooterBServiceMapper.scooterBs(inWhouseOrder.getId()));
                default:
                    break;
            case 2:
                // combin
                result.setCombins(inWhouseOrderCombinBServiceMapper.combinBs(inWhouseOrder.getId()));
                break;
            case 3:
                // parts
                result.setParts(inWhouseOrderPartsBServiceMapper.partsBs(inWhouseOrder.getId()));
                break;
        }
        // 操作动态
        List<OpTraceResult> traces = allocateOrderServiceMapper.allocateTrace(enter.getId(),OrderTypeEnums.FACTORY_INBOUND.getValue());
        result.setOpTraces(traces);
        // 关联的单据（可能关联生产采购单或者组装单）
        // 先判断关联的是哪种单据
        List<PurchaseRelationOrderResult> relationOrderResults = new ArrayList<>();
        if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue()) && InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue().equals(inWhouseOrder.getInWhType())){
            // 生产采购单
            QueryWrapper<OpeProductionPurchaseOrder> purchaseQueryWrapper = new QueryWrapper<>();
            purchaseQueryWrapper.eq(OpeProductionPurchaseOrder.COL_ID,inWhouseOrder.getRelationOrderId());
            purchaseQueryWrapper.last("limit 1");
            OpeProductionPurchaseOrder purchaseOrder = opeProductionPurchaseOrderService.getOne(purchaseQueryWrapper);
            if (purchaseOrder != null){
                PurchaseRelationOrderResult relationOrderResult = new PurchaseRelationOrderResult();
                relationOrderResult.setId(purchaseOrder.getId());
                relationOrderResult.setOrderNo(purchaseOrder.getPurchaseNo());
                relationOrderResult.setOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
                relationOrderResult.setCreatedTime(purchaseOrder.getCreatedTime());
                relationOrderResults.add(relationOrderResult);
            }
        }else if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue()) && InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue().equals(inWhouseOrder.getInWhType())){
            // 组装单
            QueryWrapper<OpeCombinOrder> combinOrderQueryWrapper = new QueryWrapper<>();
            combinOrderQueryWrapper.eq(OpeCombinOrder.COL_ID,inWhouseOrder.getRelationOrderId());
            combinOrderQueryWrapper.last("limit 1");
            OpeCombinOrder combinOrder = opeCombinOrderService.getOne(combinOrderQueryWrapper);
            if (combinOrder != null){
                PurchaseRelationOrderResult relationOrderResult = new PurchaseRelationOrderResult();
                relationOrderResult.setId(combinOrder.getId());
                relationOrderResult.setOrderNo(combinOrder.getCombinNo());
                relationOrderResult.setOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                relationOrderResult.setCreatedTime(combinOrder.getCreatedTime());
                relationOrderResults.add(relationOrderResult);
            }
        }
        result.setRelationOrders(relationOrderResults);
        return result;
    }

    @Override
    @Transactional
    public GeneralResult inWhConfirm(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if ((inWhouseOrder.getOrderType().equals(ProductTypeEnums.SCOOTER.getValue()) || inWhouseOrder.getOrderType().equals(ProductTypeEnums.COMBINATION.getValue())) && !inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.DRAFT.getValue())){
            // 整车和组装件 是草稿状态的时候才能点击
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        if (inWhouseOrder.getOrderType().equals(ProductTypeEnums.PARTS.getValue()) && !inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.WAIT_IN_WH.getValue())){
            // 部件 是待入库状态的时候才能点击
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
//        if (inWhouseOrder.getOrderType().equals(ProductTypeEnums.PARTS.getValue())){
        if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())){
            // 如果是部件入库单  点击确认入库  需要改变部件采购单的状态
            productionPurchasService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(),inWhouseOrder.getId(),enter.getUserId());
        }else if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())){
            // 如果是关联的组装单  点击确认入库  需要改变组装单的状态
            productionAssemblyOrderService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(),inWhouseOrder.getId(),enter.getUserId());
        }
        // 入库单确认入库的是时候  库存待入库数量减少  可用数量增加
        try {
            wmsMaterialStockService.inStock(inWhouseOrder.getOrderType(),inWhouseOrder.getId(),1,enter.getUserId(),inWhouseOrder.getInWhType());
        }catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult inWhReadyQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.READY_QC.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        // todo 生成质检单
        if(null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())){
            // 如果是部件 将对应的部件采购单的状态变为待入库
            productionPurchasService.statusToBeStored(inWhouseOrder.getRelationOrderId(),enter.getUserId());
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<InWhRelationOrderResult> relationPurchaseOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = productionPurchasServiceMapper.relationOrderData(enter);
        return list;
    }

    @Override
    public List<SaveOrUpdatePartsBEnter> relationPurchaseOrderPartsData(IdEnter enter) {
        List<SaveOrUpdatePartsBEnter> list = productionPurchasServiceMapper.relationPurchaseOrderPartsData(enter);
        return list;
    }

    @Override
    public List<InWhRelationOrderResult> relationCombinOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = productionAssemblyOrderServiceMapper.relationOrderData(enter);
        return list;
    }

    @Override
    public List<SaveOrUpdateCombinBEnter> relationCombinOrderCombinData(IdEnter enter) {
        List<SaveOrUpdateCombinBEnter> list = productionAssemblyOrderServiceMapper.relationCombinOrderCombinData(enter);
        return list;
    }

    @Override
    public List<SaveOrUpdateScooterBEnter> relationCombinOrderScooterData(IdEnter enter) {
        List<SaveOrUpdateScooterBEnter> list = productionAssemblyOrderServiceMapper.relationCombinOrderScooterData(enter);
        return list;
    }


    // 模拟rps的操作 开始质检
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.INSPECTING.getValue());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    // 模拟rps的操作 完成质检
    @Override
    public GeneralResult finishQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.INSPECTING.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_IN_WH.getValue());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // todo 为了流程能走下去  这里先把部件的明细实际入库数量设置为可入库数量
        QueryWrapper<OpeInWhousePartsB> qw = new QueryWrapper<>();
        qw.eq(OpeInWhousePartsB.COL_IN_WH_ID,inWhouseOrder.getId());
        List<OpeInWhousePartsB> whousePartsBList = opeInWhousePartsBService.list(qw);
        if(CollectionUtils.isNotEmpty(whousePartsBList)){
            for (OpeInWhousePartsB partsB : whousePartsBList) {
                partsB.setActInWhQty(partsB.getInWhQty());
            }
            opeInWhousePartsBService.saveOrUpdateBatch(whousePartsBList);
        }
        // 入库单变为待入库的时候，需要在库存中插入数据，待入库数量（这里应该是库存的第一步）
        try {
            wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(),inWhouseOrder.getId(),1,enter.getUserId());
        }catch (Exception e) {

        }
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
