package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.restproductionorder.AllocateOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PurchaseOrderStatusEnum;
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
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
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
        createOpTrace(allocateOrder.getId(),enter.getUserId(),1,1,"");
        // 状态流转表
        createStatusFlow(allocateOrder.getId(),enter.getUserId(),allocateOrder.getAllocateStatus(),1,enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    // 调拨单号生成规则
    public String createAllocateNo() {
        String code = "";
        // 先判断当前的日期有没有生成过单据号
        QueryWrapper<OpeAllocateOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpeAllocateOrder.COL_ALLOCATE_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.orderByDesc(OpeAllocateOrder.COL_CREATED_TIME);
        queryWrapper.last("limit 1");
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getOne(queryWrapper);
        if(allocateOrder != null){
            // 说明今天已经有过单据了  只需要流水号递增
            if (!Strings.isNullOrEmpty(allocateOrder.getAllocateNo())) {
                String oldCode = allocateOrder.getAllocateNo();
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
        createOpTrace(allocateOrder.getId(),enter.getUserId(),2,1,"");
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
        result.setEntrusts(allocateOrderServiceMapper.allocateEntrust(enter.getId()));
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
        createOpTrace(allocateOrder.getId(),enter.getUserId(),3,1,"");

        // 状态流转表
        createStatusFlow(allocateOrder.getId(),enter.getUserId(),allocateOrder.getAllocateStatus(),1,"");
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
        if(num > 0){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_NOT_CANCEL.getCode(), ExceptionCodeEnums.STOCK_NOT_CANCEL.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.CANCEL.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 需要把与该调拨单关联的所有采购单、发货单、出库单的状态都更新为【已取消】
        // 采购单
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID,allocateOrder.getId());
        List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(purchaseOrderQueryWrapper);
        if(CollectionUtils.isNotEmpty(purchaseOrderList)){
            // 采购单的id
            List<Long> purchaseIds = new ArrayList<>();
            for (OpePurchaseOrder purchaseOrder : purchaseOrderList) {
                purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.CANCEL.getValue());
                purchaseIds.add(purchaseOrder.getId());
            }
            opePurchaseOrderService.saveOrUpdateBatch(purchaseOrderList);
            // 找到采购单的发货单
            QueryWrapper<OpeInvoiceOrder> invoiceOrderQueryWrapper = new QueryWrapper<>();
            invoiceOrderQueryWrapper.in(OpeInvoiceOrder.COL_PURCHASE_ID,purchaseIds);
            List<OpeInvoiceOrder> invoiceOrderList = opeInvoiceOrderService.list(invoiceOrderQueryWrapper);
            if (CollectionUtils.isNotEmpty(invoiceOrderList)){
                // f发货单id
                List<Long> invoiceIds = new ArrayList<>();
                for (OpeInvoiceOrder invoiceOrder : invoiceOrderList) {
                    invoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.CANCEL.getValue());
                    invoiceIds.add(invoiceOrder.getId());
                }
                opeInvoiceOrderService.saveOrUpdateBatch(invoiceOrderList);
                // 找到发货单的出库单
                QueryWrapper<OpeOutWhouseOrder> opeOutWhouseOrderQueryWrapper = new QueryWrapper<>();
                opeOutWhouseOrderQueryWrapper.in(OpeOutWhouseOrder.COL_INVOICE_ID,invoiceIds);
                List<OpeOutWhouseOrder> whouseOrderList = opeOutWhouseOrderService.list(opeOutWhouseOrderQueryWrapper);
                if (CollectionUtils.isNotEmpty(whouseOrderList)){
                    for (OpeOutWhouseOrder outWhouseOrder : whouseOrderList) {
                        outWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.CANCEL.getValue());
                    }
                    opeOutWhouseOrderService.saveOrUpdateBatch(whouseOrderList);
                }
            }
        }

        // 操作记录
        createOpTrace(allocateOrder.getId(),enter.getUserId(),5,1,enter.getRemark());
        // 状态流转表
        createStatusFlow(allocateOrder.getId(),enter.getUserId(),allocateOrder.getAllocateStatus(),1,enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult allocateDelete(IdEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.DRAFT.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeAllocateOrderService.removeById(enter.getId());
        // 操作记录
        createOpTrace(allocateOrder.getId(),enter.getUserId(),4,1,"");
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
    * @Author Aleks
    * @Description 操作动态
    * @Date  2020/10/28 17:25
    * @Param [allocateId, userId, opType, orderType, remark]
    * @return
    **/
    public void createOpTrace(Long allocateId,Long userId,Integer opType,Integer orderType,String remark){
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
     * @Author Aleks
     * @Description  单据状态流转
     * @Date  2020/10/28 17:31
     * @Param [allocateId, userId, orderStatus, orderType, remark]
     * @return
     **/
    public void createStatusFlow(Long allocateId,Long userId,Integer orderStatus,Integer orderType,String remark){
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
    public void allocatePurchaseing(Long allocateId,Long userId) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.WAIT_HANDLE.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.PURCHASING.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 状态流转表
        createStatusFlow(allocateOrder.getId(),userId,allocateOrder.getAllocateStatus(),OrderTypeEnums.ALLOCATE.getValue(),"");
    }



    @Override
    public void allocateWaitDeliver(Long allocateId, Long userId) {
        // 待发货
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.PURCHASING.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_DELIVER.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // 操作记录
        createOpTrace(allocateOrder.getId(),userId, OrderOperationTypeEnums.LOADING.getValue(), OrderTypeEnums.ALLOCATE.getValue(),"");
        // 状态流转表
        createStatusFlow(allocateOrder.getId(),userId,allocateOrder.getAllocateStatus(),OrderTypeEnums.ALLOCATE.getValue(),"");
    }


    @Transactional
    @Override
    public void allocateSign(Long allocateId,Long purchaseId, Long userId) {
        // 已签收
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (allocateOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.WAIT_SIGN.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        // 校验  调拨单下面的所有采购单都签收 调拨单才能变签收状态
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID,allocateId);
        purchaseOrderQueryWrapper.ne(OpePurchaseOrder.COL_ID,purchaseId);
        purchaseOrderQueryWrapper.lt(OpePurchaseOrder.COL_PURCHASE_STATUS,PurchaseOrderStatusEnum.SIGNED.getValue());
        int count = opePurchaseOrderService.count(purchaseOrderQueryWrapper);
        if(count == 0){
            // 说明调拨单下面的采购都签收了，
            allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.SIGNED.getValue());
            opeAllocateOrderService.saveOrUpdate(allocateOrder);
            // 操作记录
            createOpTrace(allocateOrder.getId(),userId, OrderOperationTypeEnums.SIGN_FOR.getValue(), OrderTypeEnums.ALLOCATE.getValue(),"");
            // 状态流转表
            createStatusFlow(allocateOrder.getId(),userId,allocateOrder.getAllocateStatus(),OrderTypeEnums.ALLOCATE.getValue(),"");
        }
    }

}
