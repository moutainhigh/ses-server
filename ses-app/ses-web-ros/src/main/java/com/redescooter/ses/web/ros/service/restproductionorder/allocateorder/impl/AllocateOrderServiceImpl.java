package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.AllocateOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.PurchaseOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.utils.OrderGenerateUtil;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNameAllocateOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:34
 * @Version V1.0
 **/
@Service
public class AllocateOrderServiceImpl implements AllocateOrderService {


    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

    @Autowired
    private OpeAllocateScooterBService opeAllocateScooterBService;

    @Autowired
    private OpeAllocateCombinBService opeAllocateCombinBService;

    @Autowired
    private OpeAllocatePartsBService opeAllocatePartsBService;

    @Autowired
    private OpeOpTraceService opeOpTraceService;

    @Autowired
    private OpeOrderStatusFlowService opeOrderStatusFlowService;

    @Autowired
    private AllocateOrderServiceMapper allocateOrderServiceMapper;

    @Autowired
    private StaffService staffService;

    @Autowired
    private OpePurchaseOrderService opePurchaseOrderService;

    @Autowired
    private OpeInvoiceOrderService opeInvoiceOrderService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private PurchaseOrderServiceMapper purchaseOrderServiceMapper;

    @Reference
    private IdAppService idAppService;


    @Override
    public PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = allocateOrderServiceMapper.allocateTotal(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AllocateOrderListResult> list = allocateOrderServiceMapper.allocateList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @Transactional
    public GeneralResult allocateSave(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // 先处理主表数据
        OpeAllocateOrder allocateOrder = new OpeAllocateOrder();
        BeanUtils.copyProperties(enter, allocateOrder);
        allocateOrder.setAllocateType(enter.getClassType());
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setCreatedTime(new Date());
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setUpdatedTime(new Date());
        // 传参里面有classType 判断是哪个类型 对应的调拨产品为 1.车辆  2.组装件  3.部件
        // 合计总的调拨数量
        getConuntQty(enter, allocateOrder);
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.DRAFT.getValue());
        allocateOrder.setId(idAppService.getId(SequenceName.OPE_ALLOCATE_ORDER));
        // 单据号
        allocateOrder.setAllocateNo("RTO" + createAllocateNo());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 处理调拨单的子表
        createAllocateB(enter, allocateOrder);
        // 操作动态表
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 1, 1, "");
        // 状态流转表
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    // 调拨单号生成规则
    public String createAllocateNo() {
        String code = "";
        // 先判断当前的日期有没有生成过单据号
        QueryWrapper<OpeAllocateOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpeAllocateOrder.COL_ALLOCATE_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.orderByDesc(OpeAllocateOrder.COL_ALLOCATE_NO);
        queryWrapper.last("limit 1");
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getOne(queryWrapper);
        if (allocateOrder != null) {
            // 说明今天已经有过单据了  只需要流水号递增
            code = OrderGenerateUtil.orderGenerate(allocateOrder.getAllocateNo());
        } else {
            // 说明今天还没有产生过单据号，给今天的第一个就好
            code = DateUtil.getSimpleDateStamp() + "001";
        }
        return code;
    }


    private void createAllocateB(AllocateOrderOrUpdateSaveEnter enter, OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocateScooterB> scooterBS = new ArrayList<>();
                for (AllocateOrderScooterEnter scooterEnter : scooterEnters) {
                    OpeAllocateScooterB scooterB = new OpeAllocateScooterB();
                    BeanUtils.copyProperties(scooterEnter, scooterB);
                    scooterB.setAllocateId(allocateOrder.getId());
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterB.setId(idAppService.getId(SequenceName.OPE_ALLOCATE_SCOOTER_B));
                    scooterBS.add(scooterB);
                }
                opeAllocateScooterBService.saveOrUpdateBatch(scooterBS);
            default:
                break;
            case 2:
                List<AllocateOrderCombinEnter> combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocateCombinB> combinBS = new ArrayList<>();
                for (AllocateOrderCombinEnter combinEnter : combinEnters) {
                    OpeAllocateCombinB combinB = new OpeAllocateCombinB();
                    BeanUtils.copyProperties(combinEnter, combinB);
                    combinB.setAllocateId(allocateOrder.getId());
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinB.setId(idAppService.getId(SequenceName.OPE_ALLOCATE_COMBIN_B));
                    combinBS.add(combinB);
                }
                opeAllocateCombinBService.saveOrUpdateBatch(combinBS);
                break;
            case 3:
                List<AllocateOrderPartsEnter> partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderPartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeAllocatePartsB> partsBS = new ArrayList<>();
                for (AllocateOrderPartsEnter partsEnter : partsEnters) {
                    OpeAllocatePartsB partsB = new OpeAllocatePartsB();
                    BeanUtils.copyProperties(partsEnter, partsB);
                    partsB.setAllocateId(allocateOrder.getId());
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsB.setId(idAppService.getId(SequenceName.OPE_ALLOCATE_PARTS_B));
                    partsBS.add(partsB);
                }
                opeAllocatePartsBService.saveOrUpdateBatch(partsBS);
                break;
        }
    }

    private void getConuntQty(AllocateOrderOrUpdateSaveEnter enter, OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderScooterEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(scooterEnters.stream().mapToInt(AllocateOrderScooterEnter::getQty).sum());
            default:
                break;
            case 2:
                List<AllocateOrderCombinEnter> combinEnters = new ArrayList<>();
                try {
                    combinEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderCombinEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(combinEnters.stream().mapToInt(AllocateOrderCombinEnter::getQty).sum());
                break;
            case 3:
                List<AllocateOrderPartsEnter> partsEnters = new ArrayList<>();
                try {
                    partsEnters = JSONArray.parseArray(enter.getSt(), AllocateOrderPartsEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                allocateOrder.setAllocateQty(partsEnters.stream().mapToInt(AllocateOrderPartsEnter::getQty).sum());
                break;
        }
    }


    @Override
    @Transactional
    public GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter, allocateOrder);
        allocateOrder.setUpdatedTime(new Date());
        allocateOrder.setUpdatedBy(enter.getUserId());
        getConuntQty(enter, allocateOrder);
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 编辑的时候 先把下面的产品删除  再重新插入
        deleteOrderB(allocateOrder);
        createAllocateB(enter, allocateOrder);
        // 操作记录
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 2, 1, "");
        return new GeneralResult(enter.getRequestId());
    }


    // 删除调拨单的产品数据
    public void deleteOrderB(OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                // 车辆
                QueryWrapper<OpeAllocateScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateScooterB> scooterBList = opeAllocateScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    opeAllocateScooterBService.removeByIds(scooterBList.stream().map(OpeAllocateScooterB::getId).collect(Collectors.toList()));
                }
            default:
                break;
            case 2:
                // 组装件
                QueryWrapper<OpeAllocateCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeAllocateCombinB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateCombinB> combinBList = opeAllocateCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)) {
                    opeAllocateCombinBService.removeByIds(combinBList.stream().map(OpeAllocateCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // 部件
                QueryWrapper<OpeAllocatePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeAllocatePartsB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocatePartsB> partsBList = opeAllocatePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)) {
                    opeAllocatePartsBService.removeByIds(partsBList.stream().map(OpeAllocatePartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
    }


    @Override
    public AllocateOrderDetailResult allocateDetail(IdEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 先找调拨单自己的详情信息
        AllocateOrderDetailResult result = allocateOrderServiceMapper.allocateDateil(enter.getId());
        // 再找对应的产品列表信息
        switch (allocateOrder.getAllocateType()) {
            case 1:
                result.setScooters(allocateOrderServiceMapper.allocateScooter(enter.getId()));
            default:
                break;
            case 2:
                result.setCombins(allocateOrderServiceMapper.allocateCombin(enter.getId()));
                break;
            case 3:
                result.setParts(allocateOrderServiceMapper.allocateParts(enter.getId()));
                break;
        }
        // 关联的委托单
        List<AllocateEntrustResult> entrustResults = allocateOrderServiceMapper.allocateEntrust(enter.getId());
        if (CollectionUtils.isNotEmpty(entrustResults)) {
            for (AllocateEntrustResult entrustResult : entrustResults) {
                entrustResult.setOrderType(OrderTypeEnums.ORDER.getValue());
            }
        }
        result.setEntrusts(entrustResults);
        // 操作动态
        List<OpTraceResult> traces = allocateOrderServiceMapper.allocateTrace(enter.getId());
        result.setOpTraces(traces);
        return result;
    }


    @Override
    public GeneralResult allocateConfirmOrder(IdEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_HANDLE.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 操作记录
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 3, 1, "");

        // 状态流转表
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, "");
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult allocateCancelOrder(CancelOrderEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 校验该调拨单下是否有“质检中”和“已出库”状态的出库单  有的话 不能取消  没有的话 需要把与该调拨单关联的所有采购单、发货单、出库单的状态都更新为【已取消】
        Integer num = allocateOrderServiceMapper.allocateOutWh(allocateOrder.getId());
        if (num > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_NOT_CANCEL.getCode(), ExceptionCodeEnums.STOCK_NOT_CANCEL.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.CANCEL.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 需要把与该调拨单关联的所有采购单、发货单、出库单的状态都更新为【已取消】
        // 采购单
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateOrder.getId());
        List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(purchaseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
            // 采购单的id
            List<Long> purchaseIds = new ArrayList<>();
            for (OpePurchaseOrder purchaseOrder : purchaseOrderList) {
                purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.CANCEL.getValue());
                purchaseIds.add(purchaseOrder.getId());
            }
            opePurchaseOrderService.saveOrUpdateBatch(purchaseOrderList);
            // 采购单的操作动态
            orderOpTrace(purchaseOrderList.stream().map(OpePurchaseOrder::getId).collect(Collectors.toList()), OrderTypeEnums.SHIPPING.getValue(), enter.getRemark(), enter.getUserId());
            // 找到采购单的发货单
            QueryWrapper<OpeInvoiceOrder> invoiceOrderQueryWrapper = new QueryWrapper<>();
            invoiceOrderQueryWrapper.in(OpeInvoiceOrder.COL_PURCHASE_ID, purchaseIds);
            List<OpeInvoiceOrder> invoiceOrderList = opeInvoiceOrderService.list(invoiceOrderQueryWrapper);
            if (CollectionUtils.isNotEmpty(invoiceOrderList)) {
                // f发货单id
                List<Long> invoiceIds = new ArrayList<>();
                for (OpeInvoiceOrder invoiceOrder : invoiceOrderList) {
                    invoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.CANCEL.getValue());
                    invoiceIds.add(invoiceOrder.getId());
                }
                opeInvoiceOrderService.saveOrUpdateBatch(invoiceOrderList);
                // 发货单的操作动态
                orderOpTrace(invoiceOrderList.stream().map(OpeInvoiceOrder::getId).collect(Collectors.toList()), OrderTypeEnums.INVOICE.getValue(), enter.getRemark(), enter.getUserId());
                // 找到发货单的出库单
                QueryWrapper<OpeOutWhouseOrder> opeOutWhouseOrderQueryWrapper = new QueryWrapper<>();
                opeOutWhouseOrderQueryWrapper.in(OpeOutWhouseOrder.COL_INVOICE_ID, invoiceIds);
                List<OpeOutWhouseOrder> whouseOrderList = opeOutWhouseOrderService.list(opeOutWhouseOrderQueryWrapper);
                if (CollectionUtils.isNotEmpty(whouseOrderList)) {
                    for (OpeOutWhouseOrder outWhouseOrder : whouseOrderList) {
                        outWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.CANCEL.getValue());
                    }
                    opeOutWhouseOrderService.saveOrUpdateBatch(whouseOrderList);
                    // 出库单的操作动态
                    orderOpTrace(whouseOrderList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()), OrderTypeEnums.OUTBOUND.getValue(), enter.getRemark(), enter.getUserId());
                }
            }
        }

        // 操作记录
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 5, 1, enter.getRemark());
        // 状态流转表
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    // 批量处理单据的操作动态和单据状态流转
    public void orderOpTrace(List<Long> ids, Integer orderType, String remark, Long userId) {
        // 操作动态
        List<OpeOpTrace> opList = new ArrayList<>();
        for (Long id : ids) {
            OpeOpTrace opPurchase = new OpeOpTrace();
            opPurchase.setId(idAppService.getId(SequenceName.OPE_OP_TRACE));
            opPurchase.setRelationId(id);
            opPurchase.setOrderType(orderType);
            opPurchase.setOpType(OrderOperationTypeEnums.CANCEL.getValue());
            opPurchase.setRemark(remark);
            opPurchase.setCreatedBy(userId);
            opPurchase.setCreatedTime(new Date());
            opPurchase.setUpdatedBy(userId);
            opPurchase.setUpdatedTime(new Date());
            opList.add(opPurchase);
        }
        opeOpTraceService.saveOrUpdateBatch(opList);

        // 状态流转
        List<OpeOrderStatusFlow> statusFlowList = new ArrayList<>();
        for (Long id : ids) {
            OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
            statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
            statusFlow.setRelationId(id);
            statusFlow.setOrderType(orderType);
            statusFlow.setRemark(remark);
            statusFlow.setCreatedBy(userId);
            statusFlow.setCreatedTime(new Date());
            statusFlow.setUpdatedBy(userId);
            statusFlow.setUpdatedTime(new Date());
            statusFlowList.add(statusFlow);
        }
        opeOrderStatusFlowService.saveOrUpdateBatch(statusFlowList);
    }


    @Override
    public GeneralResult allocateDelete(IdEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeAllocateOrderService.removeById(enter.getId());
        // 操作记录
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 4, 1, "");
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<UserDataResult> userData(UserDataEnter enter) {
        List<UserDataResult> resultList = new ArrayList<>();
        resultList = staffService.userData(enter);
        return resultList;
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 3分别对应整车、组装件、部件
        QueryWrapper<OpeAllocateOrder> scooter = new QueryWrapper<>();
        scooter.eq(OpeAllocateOrder.COL_ALLOCATE_TYPE, 1);
        map.put("1", opeAllocateOrderService.count(scooter));

        QueryWrapper<OpeAllocateOrder> combin = new QueryWrapper<>();
        combin.eq(OpeAllocateOrder.COL_ALLOCATE_TYPE, 2);
        map.put("2", opeAllocateOrderService.count(combin));

        QueryWrapper<OpeAllocateOrder> parts = new QueryWrapper<>();
        parts.eq(OpeAllocateOrder.COL_ALLOCATE_TYPE, 3);
        map.put("3", opeAllocateOrderService.count(parts));
        return map;
    }

    @Override
    public List<WhDataResult> whData(WhDataEnter enter) {
        List<WhDataResult> list = allocateOrderServiceMapper.whData(enter);
        return list;
    }


    /**
     * @return
     * @Author Aleks
     * @Description 操作动态
     * @Date 2020/10/28 17:25
     * @Param [allocateId, userId, opType, orderType, remark]
     **/
    public void createOpTrace(Long allocateId, Long userId, Integer opType, Integer orderType, String remark) {
        OpeOpTrace opTrace = new OpeOpTrace();
        opTrace.setRelationId(allocateId);
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
     * @return
     * @Author Aleks
     * @Description 单据状态流转
     * @Date 2020/10/28 17:31
     * @Param [allocateId, userId, orderStatus, orderType, remark]
     **/
    public void createStatusFlow(Long allocateId, Long userId, Integer orderStatus, Integer orderType, String remark) {
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(allocateId);
        statusFlow.setRemark(remark);
        statusFlow.setOrderType(1);
        statusFlow.setOrderStatus(orderStatus);
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(userId);
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(userId);
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
    }


    @Override
    @Transactional
    public void allocatePurchaseing(Long allocateId, Long userId) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 因为调拨单和采购单是1对多的关系  第一次会变状态  第二次不变  需要加一个判断
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.PURCHASING.getValue()) {
            // 这个调拨单下已经有采购单走过这个流程了 直接返回即可
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.PURCHASING.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 状态流转表
        createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
    }


    @Override
    public void allocateWaitDeliver(Long allocateId, Long userId) {
        // 待发货
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 因为调拨单和采购单是1对多的关系  第一次会变状态  第二次不变  需要加一个判断
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.WAIT_DELIVER.getValue()) {
            // 调拨单下已经有采购单走过这个流程了 直接返回即可
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_DELIVER.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 状态流转表
        createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
    }


    @Transactional
    @Override
    public void allocateSign(Long allocateId, Long purchaseId, Long userId) {
        // 已签收
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 校验  调拨单下面的所有采购单都签收 调拨单才能变签收状态
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateId);
        purchaseOrderQueryWrapper.ne(OpePurchaseOrder.COL_ID, purchaseId);
        purchaseOrderQueryWrapper.lt(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.SIGNED.getValue());
        int count = opePurchaseOrderService.count(purchaseOrderQueryWrapper);
        if (count == 0) {
            if (allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.SIGNED.getValue())) {
                return;
            }
            // 追加：check调拨单下面的所有的采购单的明细签收的数量是否大于等于调拨单下面的产品明细数量
            if (checkNum(allocateOrder,purchaseId)){
                // 说明调拨单下面的采购都签收了，
                allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.SIGNED.getValue());
                opeAllocateOrderService.saveOrUpdate(allocateOrder);
                // 状态流转表
                createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
            }
        }
    }


    public boolean checkNum(OpeAllocateOrder allocateOrder,Long purchaseId){
        boolean flag = true;
        switch (allocateOrder.getAllocateType()){
            case 1:
                // 整车
                // 先找到整车的产品数据
                QueryWrapper<OpeAllocateScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocateScooterB> scooterBs = opeAllocateScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBs)){
                    // 按照车型和颜色进行分组
                    Map<String,List<OpeAllocateScooterB>> scooterAllMap = new HashMap<>();
                    for (OpeAllocateScooterB scooterB : scooterBs) {
                        String key = scooterB.getGroupId() + scooterB.getColorId() + "";
                        List<OpeAllocateScooterB> scooterBList = scooterAllMap.get(key);
                        if (CollectionUtils.isEmpty(scooterBList)){
                            scooterBList = new ArrayList<>();
                            scooterAllMap.put(key,scooterBList);
                        }
                        scooterBList.add(scooterB);
                    }

                    // 找到这个调拨单下面的所有的已签收的采购的整车信息
                    List<OpePurchaseScooterB> purchaseScooterBS = purchaseOrderServiceMapper.purchaseScooterBS(allocateOrder.getId(),purchaseId);
                    if (CollectionUtils.isEmpty(purchaseScooterBS)){
                        flag = false;
                        break;
                    }
                    // 按照车型和颜色进行分组
                    Map<String,List<OpePurchaseScooterB>> scooterMap = new HashMap<>();
                    for (OpePurchaseScooterB scooterB : purchaseScooterBS) {
                        String key = scooterB.getGroupId() + scooterB.getColorId() + "";
                        List<OpePurchaseScooterB> scooterbList = scooterMap.get(key);
                        if (CollectionUtils.isEmpty(scooterbList)){
                            scooterbList = new ArrayList<>();
                            scooterMap.put(key,scooterbList);
                        }
                        scooterbList.add(scooterB);
                    }
                    if (scooterAllMap.size() > scooterMap.size()){
                        flag = false;
                        break;
                    }
                    for (String scooterKey1 : scooterAllMap.keySet()) {
                        for (String scooterKey2 : scooterMap.keySet()) {
                            if (scooterKey1.equals(scooterKey2)){
                                Integer allscooterNum = scooterAllMap.get(scooterKey1).stream().mapToInt(OpeAllocateScooterB::getQty).sum();
                                Integer scooterNum = scooterMap.get(scooterKey2).stream().mapToInt(OpePurchaseScooterB::getQty).sum();
                                if (allscooterNum > scooterNum){
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                default:
                    break;
            case 2:
                // 组装件
                // 先找到组装件的产品数据
                QueryWrapper<OpeAllocateCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocateCombinB> combinBs = opeAllocateCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBs)){
                    Map<Long, List<OpeAllocateCombinB>> allBMap = combinBs.stream().collect(Collectors.groupingBy(OpeAllocateCombinB::getProductionCombinBomId));
                    // 找到这个调拨单下面的所有的产品明细
                    List<OpePurchaseCombinB> purchaseCombinBS = purchaseOrderServiceMapper.purchaseCombinB(allocateOrder.getId(),purchaseId);
                    if (CollectionUtils.isEmpty(purchaseCombinBS)){
                        flag = false;
                        break;
                    }
                    Map<Long, List<OpePurchaseCombinB>> bMap = purchaseCombinBS.stream().collect(Collectors.groupingBy(OpePurchaseCombinB::getProductionCombinBomId));
                    if (allBMap.size() > bMap.size()){
                        flag = false;
                        break;
                    }
                    for (Long key1 : allBMap.keySet()) {
                        for (Long key2 : bMap.keySet()) {
                            if (key1.equals(key2)){
                                Integer allocateNum = allBMap.get(key1).stream().mapToInt(OpeAllocateCombinB::getQty).sum();
                                Integer purchaseNum = bMap.get(key2).stream().mapToInt(OpePurchaseCombinB::getQty).sum();
                                if (allocateNum > purchaseNum){
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                // 部件
                // 先找到部件的产品数据
                QueryWrapper<OpeAllocatePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID,allocateOrder.getId());
                List<OpeAllocatePartsB> partsBs = opeAllocatePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBs)){
                    Map<Long, List<OpeAllocatePartsB>> partsAllBMap = partsBs.stream().collect(Collectors.groupingBy(OpeAllocatePartsB::getPartsId));
                    // 找到这个调拨单下面的所有的已签收的采购的部件信息
                    List<OpePurchasePartsB> purchasePartsBS = purchaseOrderServiceMapper.purchasePartsBS(allocateOrder.getId(),purchaseId);
                    if (CollectionUtils.isEmpty(purchasePartsBS)){
                        flag = false;
                        break;
                    }
                    Map<Long, List<OpePurchasePartsB>> partsMap = purchasePartsBS.stream().collect(Collectors.groupingBy(OpePurchasePartsB::getPartsId));
                    if (partsAllBMap.size() > partsMap.size()){
                        flag = false;
                        break;
                    }
                    for (Long partsKey1 : partsAllBMap.keySet()) {
                        for (Long partsKey2 : partsMap.keySet()) {
                            if (partsKey1.equals(partsKey2)){
                                Integer allocatePartsNum = partsAllBMap.get(partsKey1).stream().mapToInt(OpeAllocatePartsB::getQty).sum();
                                Integer purchasePartsNum = partsMap.get(partsKey2).stream().mapToInt(OpePurchasePartsB::getQty).sum();
                                if (allocatePartsNum > purchasePartsNum){
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return flag;
    }



    @Override
    @Transactional
    public void allocateWaitSign(Long allocateId, Long userId) {
        // 调拨单状态变为待签收
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        // 因为调拨单和采购单是1对多的关系  第一次会变状态  第二次不变  需要加一个判断
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.WAIT_SIGN.getValue()) {
            // 这个调拨单下已经有采购单走过这个流程了 直接返回即可
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_SIGN.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 状态流转
        OrderStatusFlowEnter opeOrderStatusFlow = new OrderStatusFlowEnter(null, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), allocateOrder.getId(), "");
        opeOrderStatusFlow.setUserId(userId);
        orderStatusFlowService.save(opeOrderStatusFlow);
    }


    @Override
    public void allocateFinish(Long allocateId, Long purchaseId, Long userId) {
        // 已完成
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // 校验  调拨单下面的所有采购单都已完成 调拨单才能变完成状态
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateId);
        purchaseOrderQueryWrapper.ne(OpePurchaseOrder.COL_ID, purchaseId);
        purchaseOrderQueryWrapper.lt(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.FINISHED.getValue());
        int count = opePurchaseOrderService.count(purchaseOrderQueryWrapper);
        if (count == 0) {
            // 说明调拨单下面的采购单都完成了
            if (allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.FINISHED.getValue())) {
                return;
            }
            allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.FINISHED.getValue());
            opeAllocateOrderService.saveOrUpdate(allocateOrder);
            // 状态流转表
            createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
        }
    }

    @Override
    public AllocateProductListResult allocateProductData(IdEnter enter) {
        AllocateProductListResult allocateProductListResult = new AllocateProductListResult();
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        allocateProductListResult.setAllocateId(allocateOrder.getId());
        allocateProductListResult.setAllocateType(allocateOrder.getAllocateType());
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterDetailResult> scooters = allocateOrderServiceMapper.allocateScooter(enter.getId());
                allocateProductListResult.setScooters(CollectionUtils.isEmpty(scooters) ? new ArrayList<>() : scooters);
            default:
                break;
            case 2:
                List<AllocateOrderCombinDetailResult> combins = allocateOrderServiceMapper.allocateCombin(enter.getId());
                allocateProductListResult.setCombins(CollectionUtils.isEmpty(combins)?new ArrayList<>():combins);
                break;
            case 3:
                List<AllocateOrderPartsDetailResult> parts = allocateOrderServiceMapper.allocateParts(enter.getId());
                allocateProductListResult.setParts(CollectionUtils.isEmpty(parts)?new ArrayList<>():parts);
                break;
        }
        return allocateProductListResult;
    }

}
