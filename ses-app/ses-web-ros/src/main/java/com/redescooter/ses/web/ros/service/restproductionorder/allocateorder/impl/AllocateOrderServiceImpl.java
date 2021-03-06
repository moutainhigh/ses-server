package com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.AllocateOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PurchaseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.NewOutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.OrderNoGenerateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproductionorder.AllocateOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.PurchaseOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateCombinB;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;
import com.redescooter.ses.web.ros.dm.OpeAllocatePartsB;
import com.redescooter.ses.web.ros.dm.OpeAllocateScooterB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpePurchaseCombinB;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import com.redescooter.ses.web.ros.dm.OpePurchasePartsB;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAllocateCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeAllocateOrderService;
import com.redescooter.ses.web.ros.service.base.OpeAllocatePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeAllocateScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhousePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOpTraceService;
import com.redescooter.ses.web.ros.service.base.OpeOrderStatusFlowService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;
import com.redescooter.ses.web.ros.service.base.OpePurchasePartsBService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseScooterBService;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateEntrustResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderCombinDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderCombinEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderOrUpdateSaveEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderPartsDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderPartsEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderScooterDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderScooterEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.WhDataEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.WhDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import io.seata.spring.annotation.GlobalTransactional;
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

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpePurchaseScooterBService opePurchaseScooterBService;

    @Autowired
    private OpePurchaseCombinBService opePurchaseCombinBService;

    @Autowired
    private OpePurchasePartsBService opePurchasePartsBService;

    @DubboReference
    private IdAppService idAppService;


    @Override
    public PageResult<AllocateOrderListResult> allocateList(AllocateOrderListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = allocateOrderServiceMapper.allocateTotal(enter);
        if (NumberUtil.eqZero(totalNum)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AllocateOrderListResult> list = allocateOrderServiceMapper.allocateList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult allocateSave(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // ?????????????????????
        OpeAllocateOrder allocateOrder = new OpeAllocateOrder();
        BeanUtils.copyProperties(enter, allocateOrder);
        allocateOrder.setAllocateType(enter.getClassType());
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setCreatedTime(new Date());
        allocateOrder.setUpdatedBy(enter.getUserId());
        allocateOrder.setUpdatedTime(new Date());
        // ???????????????classType ????????????????????? ???????????????????????? 1.??????  2.?????????  3.??????
        // ????????????????????????
        getConuntQty(enter, allocateOrder);
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.DRAFT.getValue());
        allocateOrder.setId(idAppService.getId(SequenceName.OPE_ALLOCATE_ORDER));
        // ?????????
        allocateOrder.setAllocateNo(createAllocateNo(OrderNumberTypeEnums.ALLOCAT.getValue()));
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ????????????????????????
        createAllocateB(enter, allocateOrder);
        // ???????????????
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 1, 1, "");
        // ???????????????
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    // ????????????????????????
    public String createAllocateNo(String orderNoEnum) {
        String code = "";
        // ???????????????????????????????????????????????????
        QueryWrapper<OpeAllocateOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpeAllocateOrder.COL_ALLOCATE_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.orderByDesc(OpeAllocateOrder.COL_ALLOCATE_NO);
        queryWrapper.last("limit 1");
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getOne(queryWrapper);
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // ?????????????????????????????????  ????????????????????????
            code = OrderNoGenerateUtil.orderNoGenerate(allocateOrder.getAllocateNo(), orderNoEnum);
        } else {
            // ?????????????????????????????????????????????????????????????????????
            code = orderNoEnum + DateUtil.getSimpleDateStamp() + "001";
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult allocateEdit(AllocateOrderOrUpdateSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter, allocateOrder);
        allocateOrder.setUpdatedTime(new Date());
        allocateOrder.setUpdatedBy(enter.getUserId());
        getConuntQty(enter, allocateOrder);
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ??????????????? ???????????????????????????  ???????????????
        deleteOrderB(allocateOrder);
        createAllocateB(enter, allocateOrder);
        // ????????????
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 2, 1, "");
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????
    public void deleteOrderB(OpeAllocateOrder allocateOrder) {
        switch (allocateOrder.getAllocateType()) {
            case 1:
                // ??????
                QueryWrapper<OpeAllocateScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateScooterB> scooterBList = opeAllocateScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    opeAllocateScooterBService.removeByIds(scooterBList.stream().map(OpeAllocateScooterB::getId).collect(Collectors.toList()));
                }
            default:
                break;
            case 2:
                // ?????????
                QueryWrapper<OpeAllocateCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeAllocateCombinB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateCombinB> combinBList = opeAllocateCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)) {
                    opeAllocateCombinBService.removeByIds(combinBList.stream().map(OpeAllocateCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // ??????
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
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ????????????????????????????????????
        AllocateOrderDetailResult result = allocateOrderServiceMapper.allocateDateil(enter.getId());
        // ?????????????????????????????????
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
        // ??????????????????
        List<AllocateEntrustResult> entrustResults = allocateOrderServiceMapper.allocateEntrust(enter.getId());
        if (CollectionUtils.isNotEmpty(entrustResults)) {
            for (AllocateEntrustResult entrustResult : entrustResults) {
                entrustResult.setOrderType(OrderTypeEnums.ORDER.getValue());
            }
        }
        result.setEntrusts(entrustResults);
        // ????????????
        List<OpTraceResult> traces = allocateOrderServiceMapper.allocateTrace(enter.getId(), OrderTypeEnums.ALLOCATE.getValue());
        result.setOpTraces(traces);
        return result;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult allocateConfirmOrder(IdEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_HANDLE.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ????????????
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 3, 1, "");

        // ???????????????
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, "");
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult allocateCancelOrder(CancelOrderEnter enter) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ?????????????????????????????????????????????????????????????????????????????????  ????????? ????????????  ???????????? ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        Integer num = allocateOrderServiceMapper.allocateOutWh(allocateOrder.getId());
        if (0 < num) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_NOT_CANCEL.getCode(), ExceptionCodeEnums.STOCK_NOT_CANCEL.getMessage());
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.CANCEL.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ?????????
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateOrder.getId());
        List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(purchaseOrderQueryWrapper);
        if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
            // ????????????id
            List<Long> purchaseIds = new ArrayList<>();
            for (OpePurchaseOrder purchaseOrder : purchaseOrderList) {
                purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.CANCEL.getValue());
                purchaseIds.add(purchaseOrder.getId());
            }
            opePurchaseOrderService.saveOrUpdateBatch(purchaseOrderList);
            // ????????????????????????
            orderOpTrace(purchaseOrderList.stream().map(OpePurchaseOrder::getId).collect(Collectors.toList()), OrderTypeEnums.SHIPPING.getValue(), enter.getRemark(), enter.getUserId());
            // ???????????????????????????
            QueryWrapper<OpeInvoiceOrder> invoiceOrderQueryWrapper = new QueryWrapper<>();
            invoiceOrderQueryWrapper.in(OpeInvoiceOrder.COL_PURCHASE_ID, purchaseIds);
            List<OpeInvoiceOrder> invoiceOrderList = opeInvoiceOrderService.list(invoiceOrderQueryWrapper);
            if (CollectionUtils.isNotEmpty(invoiceOrderList)) {
                // f?????????id
                List<Long> invoiceIds = new ArrayList<>();
                for (OpeInvoiceOrder invoiceOrder : invoiceOrderList) {
                    invoiceOrder.setInvoiceStatus(InvoiceOrderStatusEnums.CANCEL.getValue());
                    invoiceIds.add(invoiceOrder.getId());
                }
                opeInvoiceOrderService.saveOrUpdateBatch(invoiceOrderList);
                // ????????????????????????
                orderOpTrace(invoiceOrderList.stream().map(OpeInvoiceOrder::getId).collect(Collectors.toList()), OrderTypeEnums.INVOICE.getValue(), enter.getRemark(), enter.getUserId());
                // ???????????????????????????
                QueryWrapper<OpeOutWhouseOrder> opeOutWhouseOrderQueryWrapper = new QueryWrapper<>();
                opeOutWhouseOrderQueryWrapper.in(OpeOutWhouseOrder.COL_RELATION_ID, invoiceIds);
                List<OpeOutWhouseOrder> whouseOrderList = opeOutWhouseOrderService.list(opeOutWhouseOrderQueryWrapper);
                if (CollectionUtils.isNotEmpty(whouseOrderList)) {
                    for (OpeOutWhouseOrder outWhouseOrder : whouseOrderList) {
                        outWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.CANCEL.getValue());
                    }
                    opeOutWhouseOrderService.saveOrUpdateBatch(whouseOrderList);
                    // ????????????????????????
                    orderOpTrace(whouseOrderList.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()), OrderTypeEnums.OUTBOUND.getValue(), enter.getRemark(), enter.getUserId());
                }
            }
        }

        // ????????????
        createOpTrace(allocateOrder.getId(), enter.getUserId(), 5, 1, enter.getRemark());
        // ???????????????
        createStatusFlow(allocateOrder.getId(), enter.getUserId(), allocateOrder.getAllocateStatus(), 1, enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????????????????????????????
    public void orderOpTrace(List<Long> ids, Integer orderType, String remark, Long userId) {
        // ????????????
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

        // ????????????
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
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeAllocateOrderService.removeById(enter.getId());
        // ????????????
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
        // 1 2 3???????????????????????????????????????
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
     * @Description ????????????
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
     * @Description ??????????????????
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public void allocatePurchaseing(Long allocateId, Long userId) {
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????1???????????????  ?????????????????????  ???????????????  ?????????????????????
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.PURCHASING.getValue()) {
            // ????????????????????????????????????????????????????????? ??????????????????
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.PURCHASING.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ???????????????
        createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
    }


    @Override
    public void allocateWaitDeliver(Long allocateId, Long userId) {
        // ?????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????1???????????????  ?????????????????????  ???????????????  ?????????????????????
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.WAIT_DELIVER.getValue()) {
            // ??????????????????????????????????????????????????? ??????????????????
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_DELIVER.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ???????????????
        createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
    }


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void allocateSign(Long allocateId, Long purchaseId, Long userId) {
        // ?????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????  ?????????????????????????????????????????? ??????????????????????????????
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateId);
        purchaseOrderQueryWrapper.ne(OpePurchaseOrder.COL_ID, purchaseId);
        purchaseOrderQueryWrapper.lt(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.SIGNED.getValue());
        int count = opePurchaseOrderService.count(purchaseOrderQueryWrapper);
        if (NumberUtil.eqZero(count)) {
            if (allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.SIGNED.getValue())) {
                return;
            }
            // ?????????check??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (checkNum(allocateOrder, purchaseId)) {
                // ?????????????????????????????????????????????
                allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.SIGNED.getValue());
                opeAllocateOrderService.saveOrUpdate(allocateOrder);
                // ???????????????
                createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
            }
        }
    }


    public boolean checkNum(OpeAllocateOrder allocateOrder, Long purchaseId) {
        boolean flag = true;
        switch (allocateOrder.getAllocateType()) {
            case 1:
                // ??????
                // ??????????????????????????????
                QueryWrapper<OpeAllocateScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateScooterB> scooterBs = opeAllocateScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBs)) {
                    // ?????????????????????????????????
                    Map<String, List<OpeAllocateScooterB>> scooterAllMap = new HashMap<>();
                    for (OpeAllocateScooterB scooterB : scooterBs) {
                        String key = scooterB.getGroupId() + scooterB.getColorId() + "";
                        List<OpeAllocateScooterB> scooterBList = scooterAllMap.get(key);
                        if (CollectionUtils.isEmpty(scooterBList)) {
                            scooterBList = new ArrayList<>();
                            scooterAllMap.put(key, scooterBList);
                        }
                        scooterBList.add(scooterB);
                    }

                    // ????????????????????????????????????????????????????????????????????????
                    List<OpePurchaseScooterB> purchaseScooterBS = purchaseOrderServiceMapper.purchaseScooterBS(allocateOrder.getId(), purchaseId);
                    if (CollectionUtils.isEmpty(purchaseScooterBS)) {
                        flag = false;
                        break;
                    }
                    // ?????????????????????????????????
                    Map<String, List<OpePurchaseScooterB>> scooterMap = new HashMap<>();
                    for (OpePurchaseScooterB scooterB : purchaseScooterBS) {
                        String key = scooterB.getGroupId() + scooterB.getColorId() + "";
                        List<OpePurchaseScooterB> scooterbList = scooterMap.get(key);
                        if (CollectionUtils.isEmpty(scooterbList)) {
                            scooterbList = new ArrayList<>();
                            scooterMap.put(key, scooterbList);
                        }
                        scooterbList.add(scooterB);
                    }
                    if (scooterAllMap.size() > scooterMap.size()) {
                        flag = false;
                        break;
                    }
                    for (String scooterKey1 : scooterAllMap.keySet()) {
                        for (String scooterKey2 : scooterMap.keySet()) {
                            if (scooterKey1.equals(scooterKey2)) {
                                Integer allscooterNum = scooterAllMap.get(scooterKey1).stream().mapToInt(OpeAllocateScooterB::getQty).sum();
                                Integer scooterNum = scooterMap.get(scooterKey2).stream().mapToInt(OpePurchaseScooterB::getQty).sum();
                                if (allscooterNum > scooterNum) {
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
                // ?????????
                // ?????????????????????????????????
                QueryWrapper<OpeAllocateCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocateCombinB> combinBs = opeAllocateCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBs)) {
                    Map<Long, List<OpeAllocateCombinB>> allBMap = combinBs.stream().collect(Collectors.groupingBy(OpeAllocateCombinB::getProductionCombinBomId));
                    // ???????????????????????????????????????????????????
                    List<OpePurchaseCombinB> purchaseCombinBS = purchaseOrderServiceMapper.purchaseCombinB(allocateOrder.getId(), purchaseId);
                    if (CollectionUtils.isEmpty(purchaseCombinBS)) {
                        flag = false;
                        break;
                    }
                    Map<Long, List<OpePurchaseCombinB>> bMap = purchaseCombinBS.stream().collect(Collectors.groupingBy(OpePurchaseCombinB::getProductionCombinBomId));
                    if (allBMap.size() > bMap.size()) {
                        flag = false;
                        break;
                    }
                    for (Long key1 : allBMap.keySet()) {
                        for (Long key2 : bMap.keySet()) {
                            if (key1.equals(key2)) {
                                Integer allocateNum = allBMap.get(key1).stream().mapToInt(OpeAllocateCombinB::getQty).sum();
                                Integer purchaseNum = bMap.get(key2).stream().mapToInt(OpePurchaseCombinB::getQty).sum();
                                if (allocateNum > purchaseNum) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                // ??????
                // ??????????????????????????????
                QueryWrapper<OpeAllocatePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeAllocateScooterB.COL_ALLOCATE_ID, allocateOrder.getId());
                List<OpeAllocatePartsB> partsBs = opeAllocatePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBs)) {
                    Map<Long, List<OpeAllocatePartsB>> partsAllBMap = partsBs.stream().collect(Collectors.groupingBy(OpeAllocatePartsB::getPartsId));
                    // ????????????????????????????????????????????????????????????????????????
                    List<OpePurchasePartsB> purchasePartsBS = purchaseOrderServiceMapper.purchasePartsBS(allocateOrder.getId(), purchaseId);
                    if (CollectionUtils.isEmpty(purchasePartsBS)) {
                        flag = false;
                        break;
                    }
                    Map<Long, List<OpePurchasePartsB>> partsMap = purchasePartsBS.stream().collect(Collectors.groupingBy(OpePurchasePartsB::getPartsId));
                    if (partsAllBMap.size() > partsMap.size()) {
                        flag = false;
                        break;
                    }
                    for (Long partsKey1 : partsAllBMap.keySet()) {
                        for (Long partsKey2 : partsMap.keySet()) {
                            if (partsKey1.equals(partsKey2)) {
                                Integer allocatePartsNum = partsAllBMap.get(partsKey1).stream().mapToInt(OpeAllocatePartsB::getQty).sum();
                                Integer purchasePartsNum = partsMap.get(partsKey2).stream().mapToInt(OpePurchasePartsB::getQty).sum();
                                if (allocatePartsNum > purchasePartsNum) {
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public void allocateWaitSign(Long allocateId, Long userId) {
        // ??????????????????????????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        // ??????????????????????????????1???????????????  ?????????????????????  ???????????????  ?????????????????????
        if (allocateOrder.getAllocateStatus() >= AllocateOrderStatusEnum.WAIT_SIGN.getValue()) {
            // ????????????????????????????????????????????????????????? ??????????????????
            return;
        }
        allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.WAIT_SIGN.getValue());
        opeAllocateOrderService.saveOrUpdate(allocateOrder);
        // ????????????
        OrderStatusFlowEnter opeOrderStatusFlow = new OrderStatusFlowEnter(null, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), allocateOrder.getId(), "");
        opeOrderStatusFlow.setUserId(userId);
        orderStatusFlowService.save(opeOrderStatusFlow);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void allocateFinish(Long allocateId, Long purchaseId, Long userId) {
        // ?????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ??????  ????????????????????????????????????????????? ??????????????????????????????
        QueryWrapper<OpePurchaseOrder> purchaseOrderQueryWrapper = new QueryWrapper<>();
        purchaseOrderQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateId);
        purchaseOrderQueryWrapper.ne(OpePurchaseOrder.COL_ID, purchaseId);
        purchaseOrderQueryWrapper.lt(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.FINISHED.getValue());
        int count = opePurchaseOrderService.count(purchaseOrderQueryWrapper);
        if (NumberUtil.eqZero(count)) {
            // ?????????????????????????????????????????????
            if (!allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.SIGNED.getValue()) || allocateOrder.getAllocateStatus().equals(AllocateOrderStatusEnum.FINISHED.getValue())) {
                return;
            }
            allocateOrder.setAllocateStatus(AllocateOrderStatusEnum.FINISHED.getValue());
            opeAllocateOrderService.saveOrUpdate(allocateOrder);
            // ???????????????
            createStatusFlow(allocateOrder.getId(), userId, allocateOrder.getAllocateStatus(), OrderTypeEnums.ALLOCATE.getValue(), "");
        }
    }

    @Override
    public AllocateProductListResult allocateProductData(IdEnter enter) {
        AllocateProductListResult allocateProductListResult = new AllocateProductListResult();
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        allocateProductListResult.setAllocateId(allocateOrder.getId());
        allocateProductListResult.setAllocateType(allocateOrder.getAllocateType());
        // ?????????????????????
        switch (allocateOrder.getAllocateType()) {
            case 1:
                List<AllocateOrderScooterDetailResult> scooters = allocateOrderServiceMapper.allocateScooter(enter.getId());
                if (CollectionUtils.isNotEmpty(scooters)) {
                    // ?????? ???????????????????????????
                    inWhOrderRelationScooterAllocate(enter, scooters);
                    // ?????? ???????????????0????????????
                    List<AllocateOrderScooterDetailResult> scooterList = new ArrayList<>();
                    for (AllocateOrderScooterDetailResult scooter : scooters) {
                        if (scooter.getAbleQty() > 0) {
                            scooterList.add(scooter);
                        }
                    }
                    allocateProductListResult.setScooters(scooterList);
                } else {
                    allocateProductListResult.setScooters(new ArrayList<>());
                }
            default:
                break;
            case 2:
                List<AllocateOrderCombinDetailResult> combins = allocateOrderServiceMapper.allocateCombin(enter.getId());
                if (CollectionUtils.isNotEmpty(combins)) {
                    // ?????? ??????????????????????????????
                    inWhOrderRelationCombinAllocate(enter, combins);
                    // ?????? ???????????????0????????????
                    List<AllocateOrderCombinDetailResult> combinList = new ArrayList<>();
                    for (AllocateOrderCombinDetailResult combin : combins) {
                        if (0 < combin.getAbleQty()) {
                            combinList.add(combin);
                        }
                    }
                    allocateProductListResult.setCombins(combinList);
                } else {
                    allocateProductListResult.setCombins(new ArrayList<>());
                }
                break;
            case 3:
                List<AllocateOrderPartsDetailResult> parts = allocateOrderServiceMapper.allocateParts(enter.getId());
                if (CollectionUtils.isNotEmpty(parts)) {
                    // ?????? ???????????????????????????
                    inWhOrderRelationPartsAllocate(enter, parts);
                    // ?????? ???????????????0????????????
                    List<AllocateOrderPartsDetailResult> partsList = new ArrayList<>();
                    for (AllocateOrderPartsDetailResult part : parts) {
                        if (0 < part.getAbleQty()) {
                            partsList.add(part);
                        }
                    }
                    allocateProductListResult.setParts(partsList);
                } else {
                    allocateProductListResult.setParts(new ArrayList<>());
                }
                break;
        }
        return allocateProductListResult;
    }


    // ????????????????????????????????? ?????????????????????
    private void inWhOrderRelationPartsAllocate(IdEnter enter, List<AllocateOrderPartsDetailResult> partsResults) {
        // ??????????????????  ???????????????????????????
        Map<Long, List<OpeOutWhPartsB>> outMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<Long, List<OpeOutWhPartsB>> unOutMap = new HashMap<>();

        // ??????????????????  ???????????????????????????
        Map<Long, List<OpeInWhousePartsB>> frInMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<Long, List<OpeInWhousePartsB>> frUnInMap = new HashMap<>();

        // ?????? ???????????????/?????????  ?????????????????????
        Map<Long, List<OpePurchasePartsB>> purMap = new HashMap<>();

        // ???????????????????????????????????????  ??????????????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // ????????????????????????????????????????????????????????????????????????
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // ??????????????????????????????
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhPartsB> outpartsQw = new QueryWrapper<>();
                    outpartsQw.in(OpeOutWhPartsB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhPartsB> outpartss = opeOutWhPartsBService.list(outpartsQw);
                    if (CollectionUtils.isNotEmpty(outpartss)) {
                        outMap = outpartss.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                    }
                }
                // ?????????????????????????????????
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhPartsB> unOutpartsQw = new QueryWrapper<>();
                    unOutpartsQw.in(OpeOutWhPartsB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhPartsB> unOutpartss = opeOutWhPartsBService.list(unOutpartsQw);
                    if (CollectionUtils.isNotEmpty(unOutpartss)) {
                        unOutMap = unOutpartss.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                    }
                }
            }
            // ????????????????????????????????????????????????
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // ????????????????????????????????????
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhousePartsB> partsQw = new QueryWrapper<>();
                    partsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> inpartss = opeInWhousePartsBService.list(partsQw);
                    if (CollectionUtils.isNotEmpty(inpartss)) {
                        frInMap = inpartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                // ???????????????????????????????????????
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhousePartsB> inpartsQw = new QueryWrapper<>();
                    inpartsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> unincombins = opeInWhousePartsBService.list(inpartsQw);
                    if (CollectionUtils.isNotEmpty(unincombins)) {
                        frUnInMap = unincombins.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
            }
            // ??????  ?????????????????????????????????????????????
            QueryWrapper<OpePurchaseOrder> wrapper = new QueryWrapper<>();
            wrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateOrder.getId());
            wrapper.ne(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.CANCEL.getValue());
            List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(wrapper);
            if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
                QueryWrapper<OpePurchasePartsB> queryWrapper = new QueryWrapper<>();
                queryWrapper.in(OpePurchasePartsB.COL_PURCHASE_ID, purchaseOrderList.stream().map(OpePurchaseOrder::getId).collect(Collectors.toList()));
                List<OpePurchasePartsB> purchasePartsBList = opePurchasePartsBService.list(queryWrapper);
                if (CollectionUtils.isNotEmpty(purchasePartsBList)) {
                    purMap = purchasePartsBList.stream().collect(Collectors.groupingBy(OpePurchasePartsB::getPartsId));
                }
            }

        }
        for (AllocateOrderPartsDetailResult partsResult : partsResults) {
            // ???????????????????????????
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // ???????????? ????????????????????????
                for (Long key : outMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhPartsB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // ???????????? ???????????????????????????
                for (Long key : unOutMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhPartsB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // ????????????  ???????????????????????????
                for (Long key : frInMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // ????????????  ???????????????????????????
                for (Long key : frUnInMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhousePartsB::getInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(purMap) && 0 < purMap.size()) {
                // ?????? ??????????????????
                for (Long key : purMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (purMap.get(key).stream().mapToInt(OpePurchasePartsB::getQty).sum());
                    }
                }
            }
            partsResult.setAbleQty(partsResult.getQty() - alreadyNum);
        }
    }


    // ????????????????????????????????? ?????????????????????
    private void inWhOrderRelationCombinAllocate(IdEnter enter, List<AllocateOrderCombinDetailResult> combinResults) {
        // ??????????????????  ???????????????????????????
        Map<Long, List<OpeOutWhCombinB>> outMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<Long, List<OpeOutWhCombinB>> unOutMap = new HashMap<>();

        // ??????????????????  ???????????????????????????
        Map<Long, List<OpeInWhouseCombinB>> frInMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<Long, List<OpeInWhouseCombinB>> frUnInMap = new HashMap<>();

        // ?????? ???????????????/?????????  ?????????????????????
        Map<Long, List<OpePurchaseCombinB>> purMap = new HashMap<>();

        // ???????????????????????????????????????  ??????????????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // ????????????????????????????????????????????????????????????????????????
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // ??????????????????????????????
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhCombinB> outcombinQw = new QueryWrapper<>();
                    outcombinQw.in(OpeOutWhCombinB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhCombinB> outcombins = opeOutWhCombinBService.list(outcombinQw);
                    if (CollectionUtils.isNotEmpty(outcombins)) {
                        outMap = outcombins.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                    }
                }
                // ?????????????????????????????????
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhCombinB> unOutcombinQw = new QueryWrapper<>();
                    unOutcombinQw.in(OpeOutWhCombinB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhCombinB> unOutcombins = opeOutWhCombinBService.list(unOutcombinQw);
                    if (CollectionUtils.isNotEmpty(unOutcombins)) {
                        unOutMap = unOutcombins.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                    }
                }
            }
            // ????????????????????????????????????????????????
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // ????????????????????????????????????
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhouseCombinB> incombinQw = new QueryWrapper<>();
                    incombinQw.in(OpeInWhouseCombinB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> incombins = opeInWhouseCombinBService.list(incombinQw);
                    if (CollectionUtils.isNotEmpty(incombins)) {
                        frInMap = incombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                // ???????????????????????????????????????
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhouseCombinB> incombinQw = new QueryWrapper<>();
                    incombinQw.in(OpeInWhouseCombinB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> unincombins = opeInWhouseCombinBService.list(incombinQw);
                    if (CollectionUtils.isNotEmpty(unincombins)) {
                        frUnInMap = unincombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
            }
            // ??????  ?????????????????????????????????????????????
            QueryWrapper<OpePurchaseOrder> wrapper = new QueryWrapper<>();
            wrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateOrder.getId());
            wrapper.ne(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.CANCEL.getValue());
            List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(wrapper);
            if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
                QueryWrapper<OpePurchaseCombinB> queryWrapper = new QueryWrapper<>();
                queryWrapper.in(OpePurchaseCombinB.COL_PURCHASE_ID, purchaseOrderList.stream().map(OpePurchaseOrder::getId).collect(Collectors.toList()));
                List<OpePurchaseCombinB> purchaseCombinBList = opePurchaseCombinBService.list(queryWrapper);
                if (CollectionUtils.isNotEmpty(purchaseCombinBList)) {
                    purMap = purchaseCombinBList.stream().collect(Collectors.groupingBy(OpePurchaseCombinB::getProductionCombinBomId));
                }
            }

        }
        for (AllocateOrderCombinDetailResult combinResult : combinResults) {
            // ???????????????????????????
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // ???????????? ????????????????????????
                for (Long key : outMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhCombinB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // ???????????? ???????????????????????????
                for (Long key : unOutMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhCombinB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // ????????????  ???????????????????????????
                for (Long key : frInMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // ????????????  ???????????????????????????
                for (Long key : frUnInMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(purMap) && 0 < purMap.size()) {
                // ?????? ??????????????????
                for (Long key : purMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (purMap.get(key).stream().mapToInt(OpePurchaseCombinB::getQty).sum());
                    }
                }
            }
            combinResult.setAbleQty(combinResult.getQty() - alreadyNum);
        }
    }


    // ?????????????????????????????? ?????????????????????
    private void inWhOrderRelationScooterAllocate(IdEnter enter, List<AllocateOrderScooterDetailResult> scooters) {
        // ??????????????????  ???????????????????????????
        Map<String, List<OpeOutWhScooterB>> outMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<String, List<OpeOutWhScooterB>> unOutMap = new HashMap<>();

        // ??????????????????  ???????????????????????????
        Map<String, List<OpeInWhouseScooterB>> frInMap = new HashMap<>();
        // ??????????????????  ??????????????????????????????
        Map<String, List<OpeInWhouseScooterB>> frUnInMap = new HashMap<>();

        // ?????? ?????????????????????/???????????????  ?????????????????????
        Map<String, List<OpePurchaseScooterB>> purMap = new HashMap<>();

        // ???????????????????????????????????????  ??????????????????
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // ????????????????????????????????????????????????????????????????????????
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // ??????????????????????????????
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhScooterB> outscooterQw = new QueryWrapper<>();
                    outscooterQw.in(OpeOutWhScooterB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhScooterB> outscooters = opeOutWhScooterBService.list(outscooterQw);
                    if (CollectionUtils.isNotEmpty(outscooters)) {
                        outMap = outscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                    }
                }
                // ?????????????????????????????????
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhScooterB> unOutscooterQw = new QueryWrapper<>();
                    unOutscooterQw.in(OpeOutWhScooterB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhScooterB> unOutscooters = opeOutWhScooterBService.list(unOutscooterQw);
                    if (CollectionUtils.isNotEmpty(unOutscooters)) {
                        unOutMap = unOutscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                    }
                }
            }
            // ????????????????????????????????????????????????
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // ????????????????????????????????????
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhouseScooterB> inscooterQw = new QueryWrapper<>();
                    inscooterQw.in(OpeInWhouseScooterB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> inscooters = opeInWhouseScooterBService.list(inscooterQw);
                    if (CollectionUtils.isNotEmpty(inscooters)) {
                        frInMap = inscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                // ???????????????????????????????????????
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 30).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhouseScooterB> inscooterQw = new QueryWrapper<>();
                    inscooterQw.in(OpeInWhouseScooterB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> uninscooters = opeInWhouseScooterBService.list(inscooterQw);
                    if (CollectionUtils.isNotEmpty(uninscooters)) {
                        frUnInMap = uninscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
            }
            // ??????  ?????????????????????????????????????????????
            QueryWrapper<OpePurchaseOrder> wrapper = new QueryWrapper<>();
            wrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateOrder.getId());
            wrapper.ne(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.CANCEL.getValue());
            List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(wrapper);
            if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
                QueryWrapper<OpePurchaseScooterB> queryWrapper = new QueryWrapper<>();
                queryWrapper.in(OpePurchaseScooterB.COL_PURCHASE_ID, purchaseOrderList.stream().map(OpePurchaseOrder::getId).collect(Collectors.toList()));
                List<OpePurchaseScooterB> purchaseScooterBList = opePurchaseScooterBService.list(queryWrapper);
                if (CollectionUtils.isNotEmpty(purchaseScooterBList)) {
                    purMap = purchaseScooterBList.stream().collect(Collectors.groupingBy(o -> purchaseScooterGroup(o)));
                }
            }
        }
        for (AllocateOrderScooterDetailResult scooterResult : scooters) {
            // ???????????????????????????
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // ???????????? ????????????????????????
                for (String key : outMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhScooterB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // ???????????? ???????????????????????????
                for (String key : unOutMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhScooterB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // ????????????  ???????????????????????????
                for (String key : frInMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // ????????????  ???????????????????????????
                for (String key : frUnInMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(purMap) && 0 < purMap.size()) {
                // ?????? ??????????????????
                for (String key : purMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (purMap.get(key).stream().mapToInt(OpePurchaseScooterB::getQty).sum());
                    }
                }
            }
            scooterResult.setAbleQty(scooterResult.getQty() - alreadyNum);
        }
    }


    // ?????????????????????  ?????????????????? (??????)
    private static String fetchGroupKey(OpeOutWhScooterB scooterB) {
        // ?????????????????????????????????
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }

    // ?????????????????????  ??????????????????????????????
    private static String fetchGroupKey1(OpeInWhouseScooterB scooterB) {
        // ?????????????????????????????????
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }

    // ?????????????????????  ??????????????????????????????
    private static String purchaseScooterGroup(OpePurchaseScooterB scooterB) {
        // ?????????????????????????????????
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }


    // ???????????????????????? ?????????????????????????????????????????????????????????
    // ????????????????????????????????????????????????????????????????????????????????????  ?????????????????????????????????
    @Override
    public void allocateFinishOrSignByPurchaseCalcal(Long allocateId, Long purchaseId, Long userId) {
        AllocateProductListResult allocateProductListResult = new AllocateProductListResult();
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(allocateId);
        if (StringManaConstant.entityIsNull(allocateOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ???????????? ???????????????????????????
        // ????????????????????????????????????????????????????????????????????????????????????
        QueryWrapper<OpePurchaseOrder> purchaseQueryWrapper = new QueryWrapper<>();
        purchaseQueryWrapper.eq(OpePurchaseOrder.COL_ALLOCATE_ID, allocateId);
        purchaseQueryWrapper.ne(OpePurchaseOrder.COL_ID, purchaseId);
        purchaseQueryWrapper.ne(OpePurchaseOrder.COL_PURCHASE_STATUS, PurchaseOrderStatusEnum.CANCEL.getValue());
        List<OpePurchaseOrder> purchaseOrderList = opePurchaseOrderService.list(purchaseQueryWrapper);
        if (CollectionUtils.isEmpty(purchaseOrderList)) {
            return;
        }
        // ???????????????????????????????????????
        List<OpePurchaseOrder> noSignPurchaseOrderList = purchaseOrderList.stream().filter(o -> o.getPurchaseStatus() < PurchaseOrderStatusEnum.SIGNED.getValue()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(noSignPurchaseOrderList)) {
            // ?????????????????????????????????  ??????return
            return;
        }
        // ????????? ??????????????????????????????????????????????????????????????????
        // ???????????????????????????????????????
        List<OpePurchaseOrder> signPurchaseOrderList = purchaseOrderList.stream().filter(o -> o.getPurchaseStatus().equals(PurchaseOrderStatusEnum.SIGNED.getValue())).collect(Collectors.toList());
        List<OpePurchaseOrder> finishPurchaseOrderList = purchaseOrderList.stream().filter(o -> o.getPurchaseStatus().equals(PurchaseOrderStatusEnum.FINISHED.getValue())).collect(Collectors.toList());
        if (signPurchaseOrderList.size() == purchaseOrderList.size()) {
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????

        }
        if (finishPurchaseOrderList.size() == purchaseOrderList.size()) {
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????

        }
    }

}
