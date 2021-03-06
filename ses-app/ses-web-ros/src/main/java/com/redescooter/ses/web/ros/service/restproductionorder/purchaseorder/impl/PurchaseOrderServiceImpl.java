package com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.PurchaseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
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
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.AllocateOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InvoiceOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.PurchaseOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpePurchaseCombinB;
import com.redescooter.ses.web.ros.dm.OpePurchaseOrder;
import com.redescooter.ses.web.ros.dm.OpePurchasePartsB;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeEntrustOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOpTraceService;
import com.redescooter.ses.web.ros.service.base.OpeOrderStatusFlowService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseOrderService;
import com.redescooter.ses.web.ros.service.base.OpePurchasePartsBService;
import com.redescooter.ses.web.ros.service.base.OpePurchaseScooterBService;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.ProductEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.SaveInvoiceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PuraseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PuraseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseCalendarEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseCalendarResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseCombinEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchasePartsEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseScooterEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassNamePurchaseOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:21
 * @Version V1.0
 **/
@Service
@Slf4j
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

    @Autowired
    private AllocateOrderServiceMapper allocateOrderServiceMapper;

    @Autowired
    private InvoiceOrderServiceMapper invoiceOrderServiceMapper;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private InvoiceOrderService invoiceOrderService;

    @Autowired
    private AllocateOrderService allocateOrderService;

    @Autowired
    private RosProductionProductServiceMapper rosProductionProductServiceMapper;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @DubboReference
    private IdAppService idAppService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult purchaseSave(PurchaseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpePurchaseOrder purchaseOrder = new OpePurchaseOrder();
        BeanUtils.copyProperties(enter,purchaseOrder);
        if (StringManaConstant.entityIsNotNull(enter.getPlannedPaymentTime()) && StringManaConstant.entityIsNotNull(enter.getPaymentDay())){
            purchaseOrder.setPaymentTime(DateUtil.addDays(enter.getPlannedPaymentTime(),enter.getPaymentDay()));
        }
        purchaseOrder.setPurchaseType(enter.getClassType());
        purchaseOrder.setCreatedBy(enter.getUserId());
        purchaseOrder.setCreatedTime(new Date());
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        // ????????????????????????????????????????????????
        countQtyAmount(enter,purchaseOrder);
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.DRAFT.getValue());
        purchaseOrder.setId(idAppService.getId(SequenceName.OPE_PURCHASE_ORDER));
        // ???????????????
        purchaseOrder.setPurchaseNo(createPurchaseNo(OrderNumberTypeEnums.PURHCAS.getValue()));
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ????????????
        createPurchaseB(enter,purchaseOrder);
        // ???????????????
        createOpTrace(purchaseOrder.getId(),enter.getUserId(),1,2,enter.getRemark());
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),enter.getUserId(),purchaseOrder.getPurchaseStatus(),2,enter.getRemark());
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
                    if (StringManaConstant.entityIsNull(partsB.getPartsId())){
                        partsB.setPartsId(partsEnter.getId());
                    }
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


    // ????????????????????????
    public String createPurchaseNo(String orderNoEnum) {
        String code = "";
        // ???????????????????????????????????????????????????
        QueryWrapper<OpePurchaseOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(OpePurchaseOrder.COL_PURCHASE_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.orderByDesc(OpePurchaseOrder.COL_PURCHASE_NO);
        queryWrapper.last("limit 1");
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getOne(queryWrapper);
        if(StringManaConstant.entityIsNotNull(purchaseOrder)){
            // ?????????????????????????????????  ????????????????????????
            code = OrderNoGenerateUtil.orderNoGenerate(purchaseOrder.getPurchaseNo(),orderNoEnum);
        }else {
            // ?????????????????????????????????????????????????????????????????????
            code = orderNoEnum + DateUtil.getSimpleDateStamp() + "001";
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
                // ??????????????????
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
                // ??????????????????
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
                // ??????????????????
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult purchaseEdit(PurchaseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter,purchaseOrder);
        if (StringManaConstant.entityIsNotNull(enter.getPlannedPaymentTime()) && StringManaConstant.entityIsNotNull(enter.getPaymentDay())){
            purchaseOrder.setPaymentTime(DateUtil.addDays(enter.getPlannedPaymentTime(),enter.getPaymentDay()));
        }
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        // ????????????????????????????????????????????????
        countQtyAmount(enter,purchaseOrder);
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ??????????????? ???????????????????????????  ???????????????
        deleteOrderB(purchaseOrder);
        // ????????????
        createPurchaseB(enter,purchaseOrder);
        // ???????????????
        createOpTrace(purchaseOrder.getId(),enter.getUserId(),2,2,enter.getRemark());
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
                    opePurchaseCombinBService.removeByIds(combinBS.stream().map(OpePurchaseCombinB::getId).collect(Collectors.toList()));
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpePurchasePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpePurchasePartsB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchasePartsB> partsBS = opePurchasePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)){
                    opePurchasePartsBService.removeByIds(partsBS.stream().map(OpePurchasePartsB::getId).collect(Collectors.toList()));
                }
                break;
        }
    }


    @Override
    public PageResult<PuraseListResult> purchaseList(PuraseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = purchaseOrderServiceMapper.purchaseListTotal(enter);
        if (NumberUtil.eqZero(totalNum)){
            return PageResult.createZeroRowResult(enter);
        }
        List<PuraseListResult> list = purchaseOrderServiceMapper.purchaseList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    public PurchaseDetailResult purchaseDetail(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        PurchaseDetailResult result = new PurchaseDetailResult();
        // ??????????????????????????????
        result = purchaseOrderServiceMapper.purchaseDetail(enter.getId());
        // ?????????????????????
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
        // ????????????
        List<OpTraceResult> traces = purchaseOrderServiceMapper.purchaseTrace(enter.getId());
        result.setOpTraces(traces);
        // ???????????????
        List<PurchaseRelationOrderResult> relationOrders = new ArrayList<>();
        // ?????????
        List<PurchaseRelationOrderResult> allocates = allocateOrderServiceMapper.purchaseAllocate(purchaseOrder.getAllocateId());
        if(CollectionUtils.isNotEmpty(allocates)){
            for (PurchaseRelationOrderResult allocate : allocates) {
                allocate.setOrderType(1);
            }
            relationOrders.addAll(allocates);
        }
        // ?????????
        List<PurchaseRelationOrderResult> invoices = invoiceOrderServiceMapper.purchaseInvoice(purchaseOrder.getId());
        if (CollectionUtils.isNotEmpty(invoices)){
            List<Long> invoiceIds = new ArrayList<>();
            for (PurchaseRelationOrderResult invoice : invoices) {
                invoice.setOrderType(3);
                invoiceIds.add(invoice.getId());
            }
            relationOrders.addAll(invoices);
            // ?????????
            QueryWrapper<OpeEntrustOrder> wrapper = new QueryWrapper<>();
            wrapper.in(OpeEntrustOrder.COL_INVOICE_ID,invoiceIds);
            List<OpeEntrustOrder> entrustOrders = opeEntrustOrderService.list(wrapper);
            if(CollectionUtils.isNotEmpty(entrustOrders)){
                List<PurchaseRelationOrderResult> entrusts = new ArrayList<>();
                for (OpeEntrustOrder entrustOrder : entrustOrders) {
                    PurchaseRelationOrderResult entrust = new PurchaseRelationOrderResult();
                    entrust.setOrderType(5);
                    entrust.setCreatedTime(entrustOrder.getCreatedTime());
                    entrust.setId(entrustOrder.getId());
                    entrust.setOrderNo(entrustOrder.getEntrustNo());
                    entrusts.add(entrust);
                }
                relationOrders.addAll(entrusts);
            }
        }
        result.setRelationOrders(relationOrders);
        return result;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult confirmOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.WAIT_STOCK.getValue());
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ???????????????
        createOpTrace(purchaseOrder.getId(),enter.getUserId(),3,2,"");
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),enter.getUserId(),purchaseOrder.getPurchaseStatus(),2,"");
        // ?????????
        createInvoice(purchaseOrder,enter);
        // ??????????????????????????????
        if (purchaseOrder.getPurchaseOriginType().equals(1)){
            // ?????????????????????????????????id
            allocateOrderService.allocatePurchaseing(purchaseOrder.getAllocateId(),enter.getUserId());
        }
        return new GeneralResult(enter.getRequestId());
    }


    private void createInvoice(OpePurchaseOrder purchaseOrder,IdEnter enter) {
        SaveInvoiceEnter invoiceEnter = new SaveInvoiceEnter();
        invoiceEnter.setPurchaseId(purchaseOrder.getId());
        invoiceEnter.setPurchaseNo(purchaseOrder.getPurchaseNo());
        invoiceEnter.setInvoiceStatus(InvoiceOrderStatusEnums.MATERIALS_PRE.getValue());
        invoiceEnter.setInvoiceType(purchaseOrder.getPurchaseType());
        invoiceEnter.setTransType(purchaseOrder.getTransType());
        invoiceEnter.setInvoiceQty(purchaseOrder.getPurchaseQty());
        invoiceEnter.setConsigneeUser(purchaseOrder.getConsigneeUser());
        invoiceEnter.setConsigneeCountryCode(purchaseOrder.getConsigneeCountryCode());
        invoiceEnter.setConsigneeUserTelephone(purchaseOrder.getConsigneeUserTelephone());
        invoiceEnter.setConsigneeUserMail(purchaseOrder.getConsigneeUserMail());
        invoiceEnter.setConsigneeAddress(purchaseOrder.getConsigneeAddress());
        invoiceEnter.setConsigneePostCode(purchaseOrder.getConsigneePostCode());
        invoiceEnter.setFactoryId(purchaseOrder.getFactoryId());
        invoiceEnter.setDeliveryDate(purchaseOrder.getDeliveryDate());
        invoiceEnter.setConsignorUser(purchaseOrder.getConsignorUser());
        invoiceEnter.setConsignorCountryCode(purchaseOrder.getConsignorCountryCode());
        invoiceEnter.setConsignorTelephone(purchaseOrder.getConsignorTelephone());
        invoiceEnter.setConsignorMail(purchaseOrder.getConsignorMail());
        invoiceEnter.setNotifyUser(purchaseOrder.getNotifyUser());
        invoiceEnter.setNotifyUserName(purchaseOrder.getNotifyUserName());
        invoiceEnter.setNotifyCountryCode(purchaseOrder.getNotifyCountryCode());
        invoiceEnter.setNotifyUserTelephone(purchaseOrder.getNotifyUserTelephone());
        invoiceEnter.setNotifyUserMail(purchaseOrder.getNotifyUserMail());
        invoiceEnter.setRemark(purchaseOrder.getRemark());
        invoiceEnter.setUserId(enter.getUserId());
        // ???????????????????????????????????????
        List<ProductEnter> productEnters = new ArrayList<>();
        switch (purchaseOrder.getPurchaseType()){
            case 1:
                // scooter ????????????????????????????????? ?????????????????????????????????
                QueryWrapper<OpePurchaseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpePurchaseScooterB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchaseScooterB> scooterBS = opePurchaseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)){
                    for (OpePurchaseScooterB scooterB : scooterBS) {
                        ProductEnter productEnter = new ProductEnter();
                        BeanUtils.copyProperties(scooterB,productEnter);
//                        productEnter.setProductId(scooterB.getId());
                        productEnters.add(productEnter);
                    }
                }
            default:
                    break;
            case 2:
                // combin
                QueryWrapper<OpePurchaseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpePurchaseCombinB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchaseCombinB> combinBS = opePurchaseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)){
                    for (OpePurchaseCombinB combinB : combinBS) {
                        ProductEnter productEnter = new ProductEnter();
                        BeanUtils.copyProperties(combinB,productEnter);
                        productEnter.setProductId(combinB.getProductionCombinBomId());
                        productEnters.add(productEnter);
                    }
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpePurchasePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpePurchasePartsB.COL_PURCHASE_ID,purchaseOrder.getId());
                List<OpePurchasePartsB> partsBS = opePurchasePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)){
                    for (OpePurchasePartsB partsB : partsBS) {
                        ProductEnter productEnter = new ProductEnter();
                        BeanUtils.copyProperties(partsB,productEnter);
                        productEnter.setProductId(partsB.getPartsId());
                        productEnters.add(productEnter);
                    }
                }
                break;
        }
        invoiceEnter.setProductEnterList(productEnters);
        invoiceOrderService.save(invoiceEnter);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult cancelOrder(CancelOrderEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        // ????????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????????????????
        int whNum = purchaseOrderServiceMapper.whNum(purchaseOrder.getId());
        if (0 < whNum){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_NOT_CANCEL.getCode(), ExceptionCodeEnums.STOCK_NOT_CANCEL.getMessage());
        }
        // ???????????????
        invoiceOrderService.cancelInvoice(purchaseOrder.getId(),enter.getUserId(),enter.getRemark());

        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.CANCEL.getValue());
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // 2020 11 16?????????????????????????????????????????????????????? ??????????????????????????????  ??????????????????????????????????????????  ???????????????????????????
        // ?????????????????????????????????????????????????????????  ??????????????????????????????  ???????????????  ?????????????????????????????????  ???????????? ??????????????????
        // ????????????????????????????????????
        try {
            allocateOrderService.allocateFinishOrSignByPurchaseCalcal(purchaseOrder.getAllocateId(),purchaseOrder.getId(),enter.getUserId());
        }catch (Exception e){}

        // ???????????????
        createOpTrace(purchaseOrder.getId(),enter.getUserId(),5,2,enter.getRemark());
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),enter.getUserId(),purchaseOrder.getPurchaseStatus(),2,enter.getRemark());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult closeOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.SIGNED.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.FINISHED.getValue());
        purchaseOrder.setUpdatedBy(enter.getUserId());
        purchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ???????????????
        createOpTrace(purchaseOrder.getId(),enter.getUserId(),6,2,"");
        // ???????????????????????????????????????
        if(purchaseOrder.getPurchaseOriginType() == 1){
            // ??????????????????????????????
            allocateOrderService.allocateFinish(purchaseOrder.getAllocateId(),purchaseOrder.getId(),enter.getUserId());
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult deleteOrder(IdEnter enter) {
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(purchaseOrder)){
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
        // 1 2 3???????????????????????????????????????
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
     * @Description ????????????
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
     * @Description  ??????????????????
     * @Date  2020/10/28 17:31
     * @Param [allocateId, userId, orderStatus, orderType, remark]
     * @return
     **/
    public void createStatusFlow(Long purchaseId,Long userId,Integer orderStatus,Integer orderType,String remark){
        OpeOrderStatusFlow statusFlow = new OpeOrderStatusFlow();
        statusFlow.setRelationId(purchaseId);
        statusFlow.setRemark(remark);
        statusFlow.setOrderType(orderType);
        statusFlow.setOrderStatus(orderStatus);
        statusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
        statusFlow.setCreatedBy(userId);
        statusFlow.setCreatedTime(new Date());
        statusFlow.setUpdatedBy(userId);
        statusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(statusFlow);
    }


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void purchaseStocking(Long purchaseId, Long userId) {
        // ?????????
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(purchaseId);
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.WAIT_STOCK.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.STOCKING.getValue());
        purchaseOrder.setUpdatedBy(userId);
        purchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),userId,purchaseOrder.getPurchaseStatus(),OrderTypeEnums.SHIPPING.getValue(),"");
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void purchaseWaitDeliver(Long purchaseId,Long userId) {
       // ?????????
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(purchaseId);
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.STOCKING.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.WAIT_DELIVER.getValue());
        purchaseOrder.setUpdatedBy(userId);
        purchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),userId,purchaseOrder.getPurchaseStatus(),OrderTypeEnums.SHIPPING.getValue(),"");
        // ??????????????????????????????
        if (1 == purchaseOrder.getPurchaseOriginType()){
            // ??????????????????????????????id
            allocateOrderService.allocateWaitDeliver(purchaseOrder.getAllocateId(),userId);
        }
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void purchaseSign(Long purchaseId,Long userId) {
        // ??????
        OpePurchaseOrder purchaseOrder = opePurchaseOrderService.getById(purchaseId);
        if (StringManaConstant.entityIsNull(purchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if(!purchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.WAIT_SIGN.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        purchaseOrder.setUpdatedBy(userId);
        purchaseOrder.setUpdatedTime(new Date());
        purchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.SIGNED.getValue());
        opePurchaseOrderService.saveOrUpdate(purchaseOrder);
        // ???????????????
        createStatusFlow(purchaseOrder.getId(),purchaseId,purchaseOrder.getPurchaseStatus(),OrderTypeEnums.SHIPPING.getValue(),"");
        // ??????????????????????????????
        if (1 == purchaseOrder.getPurchaseOriginType()){
            // ??????????????????????????????id
            allocateOrderService.allocateSign(purchaseOrder.getAllocateId(),purchaseOrder.getId(),userId);
        }
    }

    @Override
    public List<OpeProductionScooterBom> getByGroupAndColorIds(List<Map<String, Object>> listMap) {
        log.info("????????????****************************");
        List<OpeProductionScooterBom>  scooterBomList = rosProductionProductServiceMapper.getByGroupAndColorIds(listMap);
        // ????????????
        Map<Long, Map<Long, List<OpeProductionScooterBom>>> map = scooterBomList.stream().collect(Collectors.groupingBy(OpeProductionScooterBom::getGroupId, Collectors.groupingBy(OpeProductionScooterBom::getColorId)));
        System.out.println(map.size());
        return scooterBomList;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void purchaseWaitSign(Long purchaseId, Long userId) {
        // ??????????????????????????????
        OpePurchaseOrder opePurchaseOrder  = opePurchaseOrderService.getById(purchaseId);
        if (StringManaConstant.entityIsNull(opePurchaseOrder)){
            throw new SesWebRosException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if(!opePurchaseOrder.getPurchaseStatus().equals(PurchaseOrderStatusEnum.WAIT_DELIVER.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opePurchaseOrder.setUpdatedBy(userId);
        opePurchaseOrder.setUpdatedTime(new Date());
        opePurchaseOrder.setPurchaseStatus(PurchaseOrderStatusEnum.WAIT_SIGN.getValue());
        opePurchaseOrderService.saveOrUpdate(opePurchaseOrder);
        // ????????????
        OrderStatusFlowEnter opeOrderStatusFlow = new OrderStatusFlowEnter(null,opePurchaseOrder.getPurchaseStatus(), OrderTypeEnums.SHIPPING.getValue(),opePurchaseOrder.getId(),"");
        opeOrderStatusFlow.setUserId(userId);
        orderStatusFlowService.save(opeOrderStatusFlow);
        // ???????????????????????????????????????????????????
        if (1 == opePurchaseOrder.getPurchaseOriginType()){
            // ??????????????????????????????id
            allocateOrderService.allocateWaitSign(opePurchaseOrder.getAllocateId(),userId);
        }
    }
}
