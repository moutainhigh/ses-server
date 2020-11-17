package com.redescooter.ses.web.ros.service.restproductionorder.outbound.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.OutboundOrderServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.ProductEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOutboundOrderEnter;
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
 *  @author: alex
 *  @Date: 2020/10/22 13:27
 *  @version：V ROS 1.8.3
 *  @Description:
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
    private OpeCombinOrderService opecombinOrderService;

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @Reference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     * @param enter
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
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     * @param enter
     */
    @Override
    public  Map<Integer, String> statusList(GeneralEnter enter) {
        Map<Integer, String> result = new HashMap<>();
        for (OutBoundOrderStatusEnums item : OutBoundOrderStatusEnums.values()) {
            result.put(item.getValue(),item.getMessage());
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:50
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 出库单列表
     * @param enter
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
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:52
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public OutboundOrderDetailResult detail(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        OutboundOrderDetailResult detail = outboundOrderServiceMapper.detail(enter);
        //关联单据
        List<AssociatedOrderResult> associatedOrderResultList = associatedOrderList(detail.getInvoiceId());
        detail.setAssociatedOrderList(CollectionUtils.isEmpty(associatedOrderResultList) ? new ArrayList<>() : associatedOrderResultList);
        //操作动态
        List<OpTraceResult> opTraceResultList = productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.OUTBOUND.getValue()));
        detail.setOrderOperatingList(CollectionUtils.isEmpty(opTraceResultList) ? new ArrayList<>() : opTraceResultList);
        //产品列表
        List<OrderProductDetailResult> orderProductDetailResultList = productListById(enter);
        detail.setInvoiceProductList(CollectionUtils.isEmpty(orderProductDetailResultList) ? new ArrayList<>() : orderProductDetailResultList);
        return detail;
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
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<OrderProductDetailResult> resultList = null;
        switch (opeOutWhouseOrder.getOutWhType()) {
            case 1:
                resultList = outboundOrderServiceMapper.productionScooterByBussId(enter.getId());
                break;
            case 2:
                resultList = outboundOrderServiceMapper.productionCombinByBussId(enter.getId());
                break;
            default:
                resultList = outboundOrderServiceMapper.productionPartByBussId(enter.getId());
                break;
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 18:14
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单列表 只关联发货单
     * @param invoiceId
     */
    @Override
    public List<AssociatedOrderResult> associatedOrderList(Long invoiceId) {
        List<AssociatedOrderResult> associatedOrderList = new ArrayList<>();
        //发货单
        OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(invoiceId);
        if (opeInvoiceOrder != null) {
            associatedOrderList.add(new AssociatedOrderResult(opeInvoiceOrder.getId(), opeInvoiceOrder.getInvoiceNo(), OrderTypeEnums.INVOICE.getValue(), opeInvoiceOrder.getCreatedTime(),""));
        }
        return associatedOrderList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:52
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveOutboundOrderEnter enter) {
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getUserId());
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (OutBoundOrderTypeEnums.SALES.getValue().equals(enter.getRelationType())){
            OpeInvoiceOrder opeInvoiceOrder = opeInvoiceOrderService.getById(enter.getRelationId());
            if (opeInvoiceOrder == null) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
        }else {
            OpeCombinOrder opeCombinOrder = opecombinOrderService.getById(enter.getRelationId());
            if (opeCombinOrder == null) {
                throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
            }
        }

        OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
        BeanUtils.copyProperties(enter, opeOutWhouseOrder);
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
        opeOutWhouseOrder.setCountryCode(opeSysStaff.getCountryCode());
        opeOutWhouseOrder.setMail(opeSysStaff.getEmail());
        opeOutWhouseOrder.setTelephone(opeSysStaff.getTelephone());
        SaveOpTraceEnter saveOpTraceEnter = null;
        if (enter.getId() == null || enter.getId() == 0) {
            opeOutWhouseOrder.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER));
            opeOutWhouseOrder.setOutWhNo(orderNumberService.orderNumber(new OrderNumberEnter(OrderTypeEnums.OUTBOUND.getValue())).getValue());
            opeOutWhouseOrder.setDr(0);
            opeOutWhouseOrder.setCreatedBy(enter.getUserId());
            opeOutWhouseOrder.setCreatedTime(new Date());

            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(),
                    enter.getRemark());
            //订单节点
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, opeOutWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), enter.getId(),
                    enter.getRemark());
            orderStatusFlowEnter.setUserId(enter.getUserId());
            orderStatusFlowService.save(orderStatusFlowEnter);
        } else {
            //操作动态
            saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(),
                    enter.getRemark());
        }
        //操作动态
        BeanUtils.copyProperties(enter, saveOpTraceEnter);
        saveOpTraceEnter.setUserId(enter.getUserId());
        saveOpTraceEnter.setId(null);
        productionOrderTraceService.save(saveOpTraceEnter);

        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);

        //保存子单据
        saveOutWhB(enter, opeOutWhouseOrder.getId());
        return new GeneralResult(enter.getRequestId());
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
                List<OpeProductionCombinBom> opeProductionCombinBomList =
                        opeProductionCombinBomService.listByIds(enter.getProductEnterList().stream().map(ProductEnter::getProductId).collect(Collectors.toSet()));
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
     * @Author Aleks
     * @Description  取消发货单下面的出库单
     * @Date  2020/10/30 16:20
     * @Param [invoiceId, userId, remark]
     * @return
     **/
    @Override
    public void cancelOutWh(Long invoiceId, Long userId, String remark) {
        QueryWrapper<OpeOutWhouseOrder> qw = new QueryWrapper<>();
        qw.eq(OpeOutWhouseOrder.COL_RELATION_ID,invoiceId);
        OpeOutWhouseOrder whouseOrder = opeOutWhouseOrderService.getOne(qw);
        if(whouseOrder != null){
            whouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.CANCEL.getValue());
            opeOutWhouseOrderService.saveOrUpdate(whouseOrder);
            // 操作记录
            SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, whouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CANCEL.getValue(), remark);
            productionOrderTraceService.save(opTraceEnter);
            OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,whouseOrder.getOutWhStatus(),OrderTypeEnums.OUTBOUND.getValue(),whouseOrder.getId(),remark);
            orderStatusFlowService.save(orderStatusFlowEnter);
        }
    }


    @Override
    @Transactional
    public GeneralResult startQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeOutWhouseOrder.getOutWhStatus(),OrderTypeEnums.OUTBOUND.getValue(),opeOutWhouseOrder.getId(),"");
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult endQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue())){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(opeOutWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null,opeOutWhouseOrder.getOutWhStatus(),OrderTypeEnums.OUTBOUND.getValue(),opeOutWhouseOrder.getId(),"");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 2020 11 17 追加  判断关联的是哪种单据类型  可能是发货单 可能是组装单
        if (opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())){
            // 更改发货单的状态为待装车
            invoiceOrderService.invoiceWaitLoading(opeOutWhouseOrder.getRelationId());
        }else if (opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())){
            // 如果关联的是组装单  把组装单的状态变为备料完成
            productionAssemblyOrderService.materialPreparationFinish(opeOutWhouseOrder.getRelationId(),enter.getUserId());
        }
        return new GeneralResult(enter.getRequestId());
    }
}
