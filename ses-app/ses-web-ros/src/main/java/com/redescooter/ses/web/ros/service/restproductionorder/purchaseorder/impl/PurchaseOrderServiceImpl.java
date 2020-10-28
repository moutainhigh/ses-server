package com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.restproductionorder.PurchaseOrderStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.PurchaseOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNamePurchaseOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:21
 * @Version V1.0
 **/
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private OpePurchaseScooterBService opePurchaseScooterBService;

    @Autowired
    private OpePurchaseCombinBService opePurchaseCombinBService;

    @Autowired
    private OpePurchasePartsBService opePurchasePartsBService;

    @Autowired
    private OpeOpTraceService opeOpTraceService;

    @Autowired
    private OpeOrderStatusFlowService opeOrderStatusFlowService;

    @Autowired
    private PurchaseOrderServiceMapper purchaseOrderServiceMapper;


    @Reference
    private IdAppService idAppService;


    @Override
    @Transactional
    public GeneralResult purchaseSave(PurchaseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpePurchaseOrder purchaseOrder = new OpePurchaseOrder();
        BeanUtils.copyProperties(enter,purchaseOrder);
        purchaseOrder.setPaymentTime(DateUtil.dateAddHour(purchaseOrder.getPlannedPaymentTime(),purchaseOrder.getPaymentDay()));
        purchaseOrder.setPurchaseType(enter.getClassType());
        purchaseOrder.setCreatedBy(enter.getUserId());
        purchaseOrder.setCreatedTime(new Date());
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        // 统计采购单产品里面的数量和总金额
        countQtyAmount(enter,purchaseOrder);
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.DRAFT.getValue());
        purchaseOrder.setId(idAppService.getId(SequenceName.OPE_PURCHASE_ORDER));
        // 生成单据号
        purchaseOrder.setPurchaseNo("RPO"+createPurchaseNo());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // 处理子表
        createPurchaseB(enter,purchaseOrder);
        // 操作动态表
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(purchaseOrder.getId());
        opTrace.setOpType(1);
        opTrace.setOrderType(2);
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
        opeOpTraceService.saveOrUpdate(opTrace);
        // 状态流转表
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(purchaseOrder.getId());
        statusFlow.setOrderType(2);
        statusFlow.setOrderStatus(purchaseOrder.getPurchaseStatus());
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(enter.getUserId());
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(enter.getUserId());
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
        return new GeneralResult(enter.getRequestId());
    }


    public void createPurchaseB(PurchaseSaveOrUpdateEnter enter,OpePurchaseOrder purchaseOrder) {
        switch (purchaseOrder.getPurchaseType()) {
            case 1:
                // scooter
                List<PurchaseScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), PurchaseScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpePurchaseScooterB> scooterBS = new ArrayList<>();
                for (PurchaseScooterEnter scooterEnter : scooterEnters) {
                    OpePurchaseScooterB scooterB = new OpePurchaseScooterB();
                    BeanUtils.copyProperties(scooterEnter,scooterB);
                    scooterB.setPurchaseId(purchaseOrder.getId());
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterB.setId(idAppService.getId(SequenceName.OPE_PURCHASE_SCOOTER_B));
                    scooterBS.add(scooterB);
                }
                opePurchaseScooterBService.saveOrUpdateBatch(scooterBS);
            default:
                break;
            case 2:
                // combin
                List<PurchaseCombinEnter> combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), PurchaseCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpePurchaseCombinB> combinBS = new ArrayList<>();
                for (PurchaseCombinEnter combinEnter : combinEnters) {
                    OpePurchaseCombinB combinB = new OpePurchaseCombinB();
                    BeanUtils.copyProperties(combinEnter,combinB);
                    combinB.setPurchaseId(purchaseOrder.getId());
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinB.setId(idAppService.getId(SequenceName.OPE_PURCHASE_COMBIN_B));
                    combinBS.add(combinB);
                }
                opePurchaseCombinBService.saveOrUpdateBatch(combinBS);
                break;
            case 3:
                // parts
                List<PurchasePartsEnter> partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), PurchasePartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpePurchasePartsB> partsBS = new ArrayList<>();
                for (PurchasePartsEnter partsEnter : partsEnters) {
                    OpePurchasePartsB partsB = new OpePurchasePartsB();
                    BeanUtils.copyProperties(partsEnter,partsB);
                    partsB.setPurchaseId(purchaseOrder.getId());
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsB.setId(idAppService.getId(SequenceName.OPE_PURCHASE_PARTS_B));
                    partsBS.add(partsB);
                }
                opePurchasePartsBService.saveOrUpdateBatch(partsBS);
                break;
        }
    }


    // 采购单号生成规则
    public String createPurchaseNo() {
        String code = "";
        // 先判断当前的日期有没有生成过单据号
        QueryWrapper<OpePurchaseOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpePurchaseOrder.COL_PURCHASE_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.orderByDesc(OpePurchaseOrder.COL_CREATED_TIME);
        queryWrapper.last("limit 1");
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getOne(queryWrapper);
        if(purchaseOrder != null){
            // 说明今天已经有过单据了  只需要流水号递增
            if (!Strings.isNullOrEmpty(purchaseOrder.getPurchaseNo())) {
                String oldCode = purchaseOrder.getPurchaseNo();
                Integer i = Integer.parseInt(oldCode.substring(oldCode.length() - 3));
                i++;
                code = DateUtil.getSimpleDateStamp() + String.format("%3d", i).replace(" ", "0");
            }
        }else {
            // 说明今天还没有产生过单据号，给今天的第一个就好
            code = DateUtil.getSimpleDateStamp() + "001";
        }
        return code;
    }


    public void countQtyAmount(PurchaseSaveOrUpdateEnter enter,OpePurchaseOrder purchaseOrder) {
        BigDecimal amount = new BigDecimal(0);
        switch (purchaseOrder.getPurchaseType()) {
            case 1:
                // scooter
                List<PurchaseScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), PurchaseScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                // 统计采购数量
                purchaseOrder.setPurchaseQty(scooterEnters.stream().mapToInt(PurchaseScooterEnter::getQty).sum());
                for (PurchaseScooterEnter scooterEnter : scooterEnters) {
                    BigDecimal multiply = scooterEnter.getUnitPrice().multiply(new BigDecimal(scooterEnter.getQty()));
                    amount = multiply.add(amount);
                }
                purchaseOrder.setPurchaseAmount(amount);
            default:
                break;
            case 2:
                // combin
                List<PurchaseCombinEnter> combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), PurchaseCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                // 统计采购数量
                purchaseOrder.setPurchaseQty(combinEnters.stream().mapToInt(PurchaseCombinEnter::getQty).sum());
                for (PurchaseCombinEnter combinEnter : combinEnters) {
                    BigDecimal multiply = combinEnter.getUnitPrice().multiply(new BigDecimal(combinEnter.getQty()));
                    amount = multiply.add(amount);
                }
                purchaseOrder.setPurchaseAmount(amount);
                break;
            case 3:
                // parts
                List<PurchasePartsEnter> partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), PurchasePartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                // 统计采购数量
                purchaseOrder.setPurchaseQty(partsEnters.stream().mapToInt(PurchasePartsEnter::getQty).sum());
                for (PurchasePartsEnter partsEnter : partsEnters) {
                    BigDecimal multiply = partsEnter.getUnitPrice().multiply(new BigDecimal(partsEnter.getQty()));
                    amount = multiply.add(amount);
                }
                purchaseOrder.setPurchaseAmount(amount);
                break;
        }
    }


    @Override
    @Transactional
    public GeneralResult purchaseEdit(PurchaseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter,purchaseOrder);
        purchaseOrder.setPaymentTime(DateUtil.dateAddHour(purchaseOrder.getPlannedPaymentTime(),purchaseOrder.getPaymentDay()));
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        // 统计采购单产品里面的数量和总金额
        countQtyAmount(enter,purchaseOrder);
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // 编辑的时候 先把下面的产品删除  再重新插入
        deleteOrderB(purchaseOrder);
        // 处理子表
        createPurchaseB(enter,purchaseOrder);
        // 操作动态表
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(purchaseOrder.getId());
        opTrace.setOpType(2);
        opTrace.setOrderType(2);
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
        opeOpTraceService.saveOrUpdate(opTrace);
        return new GeneralResult(enter.getRequestId());
    }


    public void deleteOrderB(OpePurchaseOrder purchaseOrder){
        switch (purchaseOrder.getPurchaseType()){
            case 1:
                // scooter
                QueryWrapper<OpePurchaseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpePurchaseScooterB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchaseScooterB> scooterBS = opePurchaseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)){
                    opePurchaseScooterBService.removeByIds(scooterBS.stream().map(OpePurchaseScooterB::getId).collect(Collectors.toList()));
                }
            default:
                    break;
            case 2:
                // combin
                QueryWrapper<OpePurchaseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpePurchaseCombinB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchaseCombinB> combinBS = opePurchaseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)){
                    opePurchaseScooterBService.removeByIds(combinBS.stream().map(OpePurchaseCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpePurchasePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpePurchasePartsB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchasePartsB> partsBS = opePurchasePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)){
                    opePurchaseScooterBService.removeByIds(partsBS.stream().map(OpePurchasePartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
    }


    @Override
    public PageResult<PuraseListResult> purchaseList(PuraseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = purchaseOrderServiceMapper.purchaseListTotal(enter);
        if (totalNum == 0){
            return PageResult.createZeroRowResult(enter);
        }
        List<PuraseListResult> list = purchaseOrderServiceMapper.purchaseList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    public PurchaseDetailResult purchaseDetail(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        PurchaseDetailResult result = new PurchaseDetailResult();
        // 先找采购单自己的信息
        result = purchaseOrderServiceMapper.purchaseDetail(enter.getId());
        // 再处理产品明细
        switch (purchaseOrder.getPurchaseType()){
            case 1:
                // scooter
                result.setScooters(purchaseOrderServiceMapper.purchaseScooter(enter.getId()));
                default:
                    break;
            case 2:
                // combin
                result.setCombins(purchaseOrderServiceMapper.purchaseCombin(enter.getId()));
                break;
            case 3:
                // parts
                result.setParts(purchaseOrderServiceMapper.purchaseParts(enter.getId()));
                break;
        }
        // 操作动态
        List<OpTraceResult> traces = purchaseOrderServiceMapper.purchaseTrace(enter.getId());
        result.setOpTraces(traces);
        return result;
    }


    @Override
    @Transactional
    public GeneralResult confirmOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.WAIT_STOCK.getValue());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // 操作动态表
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(purchaseOrder.getId());
        opTrace.setOpType(3);
        opTrace.setOrderType(2);
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
        opeOpTraceService.saveOrUpdate(opTrace);
        // 状态流转表
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(purchaseOrder.getId());
        statusFlow.setOrderType(2);
        statusFlow.setOrderStatus(purchaseOrder.getPurchaseStatus());
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(enter.getUserId());
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(enter.getUserId());
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);

        // todo 调用发货单那边的接口 生成一个发货单
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult cancelOrder(CancelOrderEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // todo 校验，与该采购单相关联的出库单状态是否是“质检中”或“已出库” ，取消后，与该采购单关联的发货单、出库单都将被取消
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.CANCEL.getValue());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);

        // 操作动态表
//        createOpTrace();
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(purchaseOrder.getId());
        opTrace.setOpType(5);
        opTrace.setOrderType(2);
        opTrace.setRemark(enter.getRemark());
        opTrace.setCreatedBy(enter.getUserId());
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(enter.getUserId());
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
        opeOpTraceService.saveOrUpdate(opTrace);
        // 状态流转表
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(purchaseOrder.getId());
        statusFlow.setOrderType(2);
        statusFlow.setRemark(enter.getRemark());
        statusFlow.setOrderStatus(purchaseOrder.getPurchaseStatus());
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(enter.getUserId());
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(enter.getUserId());
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult closeOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.SIGNED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.FINISHED.getValue());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult deleteOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (purchaseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opePurchaseOrderService.removeById(purchaseOrder.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<AllocateNoDataResult> allocateNoData(KeywordEnter enter) {
        List<AllocateNoDataResult> resultList = purchaseOrderServiceMapper.allocateNoData(enter);
        return resultList;
    }


    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3分别对应整车、组装件、部件
        QueryWrapper<OpePurchaseOrder> scooter = new QueryWrapper<>();
        scooter.eq(OpePurchaseOrder.COL_PURCHASE_TYPE,1);
        map.put("1",opePurchaseOrderService.count(scooter));

        QueryWrapper<OpePurchaseOrder> combin = new QueryWrapper<>();
        combin.eq(OpePurchaseOrder.COL_PURCHASE_TYPE,2);
        map.put("2",opePurchaseOrderService.count(combin));

        QueryWrapper<OpePurchaseOrder> parts = new QueryWrapper<>();
        parts.eq(OpePurchaseOrder.COL_PURCHASE_TYPE,3);
        map.put("3",opePurchaseOrderService.count(parts));
        return map;
    }

    @Override
    public List<PurchaseCalendarResult> purchaseCalendar(PurchaseCalendarEnter enter) {
        List<PurchaseCalendarResult> resultList = purchaseOrderServiceMapper.purchaseCalendar(enter);
        return resultList;
    }


    /**
     * @Author Aleks
     * @Description 操作动态
     * @Date  2020/10/28 17:25
     * @Param [allocateId, userId, opType, orderType, remark]
     * @return
     **/
    public void createOpTrace(Long purchaseId,Long userId,Integer opType,Integer orderType,String remark){
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(purchaseId);
        opTrace.setOpType(opType);
        opTrace.setOrderType(orderType);
        opTrace.setRemark(remark);
        opTrace.setCreatedBy(userId);
        opTrace.setCreatedTime(new Date());
        opTrace.setUpdatedBy(userId);
        opTrace.setUpdatedTime(new Date());
        opTrace.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
        opeOpTraceService.saveOrUpdate(opTrace);
    }


    /**
     * @Author Aleks
     * @Description  单据状态流转
     * @Date  2020/10/28 17:31
     * @Param [allocateId, userId, orderStatus, orderType, remark]
     * @return
     **/
    public void createStatusFlow(Long purchaseId,Long userId,Integer orderStatus,Integer orderType,String remark){
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(purchaseId);
        statusFlow.setRemark(remark);
        statusFlow.setOrderType(2);
        statusFlow.setOrderStatus(orderStatus);
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(userId);
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(userId);
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
    }
}
