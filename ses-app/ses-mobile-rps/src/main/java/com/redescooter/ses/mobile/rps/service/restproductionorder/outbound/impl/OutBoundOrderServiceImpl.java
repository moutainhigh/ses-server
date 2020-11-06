package com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.invoice.InvoiceOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.outbound.OutBoundOrderSrviceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.OutBoundOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.qctrace.ProductQcTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.invoice.InvoiceUpdateStatusEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcInfoEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcTraceEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.websocket.SessionException;
import java.rmi.server.ServerCloneException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  @author: aleks
 *  @Date: 2020/10/26 14:25
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class OutBoundOrderServiceImpl implements OutBoundOrderService {

    @Autowired
    private OutBoundOrderSrviceMapper outBoundOrderSrviceMapper;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private OpeProductionQualityTempateService opeProductionQualityTempateService;

    @Autowired
    private OpeProductionQualityTempateBService opeProductionQualityTempateBService;

    @Autowired
    private OpeOrderQcItemService opeOrderQcItemService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private ProductQcTraceService productQcTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeEntrustProductSerialNumService opeEntrustProductSerialNumService;

    @Autowired
    private OpeInvoiceProductSerialNumService opeInvoiceProductSerialNumService;

    @Autowired
    private OpeEntrustOrderService opeEntrustOrderService;

    @Autowired
    private InvoiceOrderService invoiceOrderService;

    @Reference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 下午
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByProductType(GeneralEnter enter) {
        Map<Integer, Integer> map = new HashMap<>();
        List<CountByStatusResult> countByStatusResultList= outBoundOrderSrviceMapper.countByProductType(enter);
        map=countByStatusResultList.stream().collect(Collectors.toMap(item->{return Integer.valueOf(item.getStatus());},CountByStatusResult::getTotalCount));

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (!map.containsKey(item.getValue())){
                map.put(item.getValue(),0);
            }
        }
        return map;
    }

    /**
     * @Description
     * @Author: enter
     * @Date: 2020/11/3 2:28 下午
     * @Param: enter
     * @Return: Map
     * @des： 单据类型统计
     * @param enter
     */
    @Override
    public Map<Integer, Integer> countByOrderType(GeneralEnter enter) {
        Map<Integer, Integer> map = new HashMap<>();
        List<CountByStatusResult> countByStatusResultList= outBoundOrderSrviceMapper.countByOrderType(enter);
        map=countByStatusResultList.stream().collect(Collectors.toMap(item->{return Integer.valueOf(item.getStatus());},CountByStatusResult::getTotalCount));

        for (OutBoundOrderTypeEnums item : OutBoundOrderTypeEnums.values()) {
            if (!map.containsKey(item.getValue())){
                map.put(item.getValue(),0);
            }
        }
        return map;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:12 下午
     * @Param: enter
     * @Return: OutboundOrderResult
     * @desc: 出库单列表
     * @param enter
     */
    @Override
    public PageResult<OutboundOrderResult> list(OutboundOrderEnter enter) {
        int count= outBoundOrderSrviceMapper.listCount(enter);
        if (count==0){
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter,count, outBoundOrderSrviceMapper.list(enter));
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:25 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 开始质检
     * @param enter
     */
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder==null){
            throw  new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue())){
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue());
        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        opeOutWhouseOrderService.updateById(opeOutWhouseOrder);
        //操作动态
        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.START_QC.getValue(),
                opeOutWhouseOrder.getRemark());
        saveOpTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(saveOpTraceEnter);

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(),
                opeOutWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:37 下午
     * @Param: enter
     * @Return: OutboundDetailResult
     * @desc: 单据详情
     * @param enter
     */
    @Override
    public OutboundDetailResult detail(IdEnter enter) {
        OutboundDetailResult detail=outBoundOrderSrviceMapper.detail(enter);
        if (detail==null){
            throw  new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        //获取产品信息
        detail.setOutboundDetailProductResultList(this.detailProductList(enter));
        return detail;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:40 下午
     * @Param: enter
     * @Return: List<OutboundDetailProductResult>
     * @desc: 详情中产品信息
     * @param enter
     */
    @Override
    public List<OutboundDetailProductResult> detailProductList(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder==null){
            throw  new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        List<OutboundDetailProductResult> result = new ArrayList<>();
        switch (opeOutWhouseOrder.getOutWhType()){
            case 1:
                result=outBoundOrderSrviceMapper.detailProductListByScooter(enter);
                break;
            case 2 :
                result=outBoundOrderSrviceMapper.detailProductListByCombin(enter);
                break;
            default:
                result=outBoundOrderSrviceMapper.detailProductListByPart(enter);
                break;
        }
        return result;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 2:48 下午
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: 产品详情
     * @param enter
     */
    @Override
    public ProductDetailResult productOutWhDetail(ProductOutWhDetailEnter enter) {
        ProductDetailResult productDetailResult =null;
        List<ProductSnResult> productSnResultList=new ArrayList<>();
        switch (enter.getProductType()){
            case 1 :
                productDetailResult=outBoundOrderSrviceMapper.productOutWhDetailByScooter(enter);
                //产品出库列表查询
                if (productDetailResult != null) {
                    productSnResultList=outBoundOrderSrviceMapper.productOutWhDetailByScooterSnList(enter);
                }
                //查询整车bom信息
                OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getOne(new LambdaQueryWrapper<OpeProductionScooterBom>()
                        .eq(OpeProductionScooterBom::getColorId, productDetailResult.getColorId())
                        .eq(OpeProductionScooterBom::getGroupId, productDetailResult.getGroupId())
                        .eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue())
                        .last("limit 1"));
                if (opeProductionScooterBom==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
                }
                //注意：整车返回的产品Id是bom中 整车的BomId
                productDetailResult.setProductId(opeProductionScooterBom.getId());
                break;
            case 2 :
                productDetailResult=outBoundOrderSrviceMapper.productOutWhDetailByCombin(enter);
                //产品出库列表查询
                if (productDetailResult != null) {
                    productSnResultList=outBoundOrderSrviceMapper.productOutWhDetailByCombinSnList(enter);
                }
                break;
            default:
                productDetailResult=outBoundOrderSrviceMapper.productOutWhDetailByPart(enter);
                //产品出库列表查询
                if (productDetailResult != null) {
                    productSnResultList=outBoundOrderSrviceMapper.productOutWhDetailByPartSnList(enter);
                }
                break;
        }
        productDetailResult.setProductSnResultList(productSnResultList);
        return productDetailResult;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 2:51 下午
     * @Param: enter
     * @Return: ProductQcTempleteResult
     * @desc: 质检模版
     * @param enter
     */
    @Override
    public List<ProductQcTempleteItemResult> qcTemplete(QcTempleteEnter enter) {
        List<ProductQcTempleteItemResult> productQcTempleteItemResultList = new ArrayList<>();
        Integer produtionType=null;
        switch (enter.getProductType()){
            case 1:
                produtionType= Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue());
                break;
            case 2 :
                produtionType= Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue());
                break;
            default:
                produtionType= Integer.valueOf(BomCommonTypeEnums.PARTS.getValue());
                break;

        }
        productQcTempleteItemResultList=outBoundOrderSrviceMapper.qcTemplete(enter,produtionType);
        if (CollectionUtils.isEmpty(productQcTempleteItemResultList)){
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
        }
        //查询质检结果项
        List<OpeProductionQualityTempateB> productionQualityTempateBList =
                opeProductionQualityTempateBService.list(new LambdaQueryWrapper<OpeProductionQualityTempateB>().in(OpeProductionQualityTempateB::getProductQualityTempateId,
                        productQcTempleteItemResultList.stream().map(ProductQcTempleteItemResult::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(productionQualityTempateBList)){
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
        }
        productQcTempleteItemResultList.forEach(item->{
            List<ProductQcTempleteResultResult> qcTempleteList=new ArrayList<>();
            productionQualityTempateBList.forEach(tempateB -> {
                qcTempleteList.add(new ProductQcTempleteResultResult(tempateB.getId(),tempateB.getProductQualityTempateId(),tempateB.getQcResult(),tempateB.getPassFlag(),tempateB.getUploadFlag(),
                        tempateB.getResultsSequence()));
            });
            if (CollectionUtils.isEmpty(qcTempleteList)){
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }
        });
        return productQcTempleteItemResultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 3:28 下午
     * @Param: SaveQcResultEnter
     * @Return: GeneralResult
     * @desc: 保存质检结果
     * @param enter
     */
    @Transactional
    @Override
    public BooleanResult saveQcResult(SaveQcResultEnter enter) {
        List<SaveQcTempleteResultEnter> templeteEnterList = new ArrayList<>();
        try {
            templeteEnterList = JSON.parseArray(enter.getSt(), SaveQcTempleteResultEnter.class);
        }catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        //单据校验
        Boolean booleanSerial=Boolean.TRUE;
        //主订单Id
        Long orderId=null;
        switch (enter.getProductType()){
            case 1:
                OpeOutWhScooterB opeOutWhScooterB = opeOutWhScooterBService.getById(enter.getId());
                if (opeOutWhScooterB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                orderId=opeOutWhScooterB.getOutWhId();
                break;
            case 2 :
                OpeOutWhCombinB opeOutWhCombinB = opeOutWhCombinBService.getById(enter.getId());
                if (opeOutWhCombinB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                orderId=opeOutWhCombinB.getOutWhId();
                break;
            default :
                OpeOutWhPartsB opeOutWhPartsB = opeOutWhPartsBService.getById(enter.getId());
                if (opeOutWhPartsB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                OpeProductionParts opeProductionParts = opeProductionPartsService.getById(opeOutWhPartsB.getPartsId());
                if (opeProductionParts==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                }
                orderId=opeOutWhPartsB.getOutWhId();
                booleanSerial=opeProductionParts.getIdCalss().equals(0)?Boolean.FALSE:Boolean.TRUE;
                break;
        }
        //查询产品质检模版
        List<ProductQcTempleteItemResult> productQcTempleteItemResultList = qcTemplete(new QcTempleteEnter(enter.getId(), enter.getProductType()));
        //质检模版通过的集合
        Map<Long, ProductQcTempleteResultResult> qcPassMap = new HashMap<>();
        productQcTempleteItemResultList.forEach(item->{
            ProductQcTempleteResultResult productQcTempleteResultResult = item.getQcTempleteList().stream().filter(result -> result.getPassFlag()).findFirst().orElse(null);
            if (productQcTempleteResultResult==null){
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }
            qcPassMap.put(item.getId(),productQcTempleteResultResult);
        });

        //质检信息
        List<SaveProductQcInfoEnter> saveProductQcInfoEnters = new ArrayList<>();
        //质检结果判断
        Boolean qcResult=Boolean.TRUE;
        for (SaveQcTempleteResultEnter item : templeteEnterList) {
            if (!qcPassMap.containsKey(item.getItemId())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            if (!qcPassMap.get(item.getItemId()).equals(item.getQcResultId())) {
                qcResult = Boolean.FALSE;
                break;
            }
            ProductQcTempleteItemResult productQcTempleteItemResult = productQcTempleteItemResultList.stream().filter(qcitem -> qcitem.getId().equals(item.getItemId())).findFirst().orElse(null);

            SaveProductQcInfoEnter saveProductQcInfoEnter = new SaveProductQcInfoEnter(item.getItemId(),productQcTempleteItemResult.getItemName(),item.getQcResultId(),qcPassMap.get(item.getItemId()).getQcResult(),item.getImageUrls(),item.getRemark());
            saveProductQcInfoEnter.setUserId(enter.getUserId());
            saveProductQcInfoEnters.add(saveProductQcInfoEnter);
        }

        //todo booleanSerial 判断有序列号需要验证库存

        //校验单据质检记录绑定表 校验绑定关系
        Long opeInvoiceProductSerialNumId=null;
        QueryWrapper<OpeInvoiceProductSerialNum> opeInvoiceProductSerialNumQueryWrapper = new QueryWrapper<>();
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_RELATION_TYPE,OrderTypeEnums.OUTBOUND.getValue());
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_RELATION_ID,enter.getId());
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_LOT,enter.getLot());
        OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = opeInvoiceProductSerialNumService.getOne(opeInvoiceProductSerialNumQueryWrapper);

        if (opeInvoiceProductSerialNum!=null){
            opeInvoiceProductSerialNumId=opeInvoiceProductSerialNum.getId();
        }
        if (!qcResult){
            opeInvoiceProductSerialNumId= idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND);
            opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
            opeInvoiceProductSerialNum.setId(opeInvoiceProductSerialNumId);
            opeInvoiceProductSerialNum.setDr(0);
            opeInvoiceProductSerialNum.setRelationType(OrderTypeEnums.OUTBOUND.getValue());
            opeInvoiceProductSerialNum.setRelationId(enter.getId());
            opeInvoiceProductSerialNum.setIdClass(enter.getIdClass()?1:0);
            opeInvoiceProductSerialNum.setProductId(enter.getProductId());
            opeInvoiceProductSerialNum.setProductType(enter.getProductType());
            opeInvoiceProductSerialNum.setSerialNum(enter.getSerialNum());
            opeInvoiceProductSerialNum.setLot(enter.getLot());
            opeInvoiceProductSerialNum.setQty(0);
            opeInvoiceProductSerialNum.setRemark(null);
            opeInvoiceProductSerialNum.setCreatedBy(enter.getUserId());
            opeInvoiceProductSerialNum.setCreatedTime(new Date());
            opeInvoiceProductSerialNum.setUpdatedBy(enter.getUserId());
            opeInvoiceProductSerialNum.setUpdatedTime(new Date());
        }else {
            opeInvoiceProductSerialNum.setQty(enter.getQty());
        }
        //生成出库单序列号绑定关系
        opeInvoiceProductSerialNumService.saveOrUpdate(opeInvoiceProductSerialNum);

        Boolean updateOrderStatus=Boolean.FALSE;
        //更新子单据
        if (qcResult){
            switch (enter.getProductType()){
                case 1:
                    OpeOutWhScooterB opeOutWhScooterB = opeOutWhScooterBService.getById(enter.getId());
                    opeOutWhScooterB.setAlreadyOutWhQty(opeOutWhScooterB.getAlreadyOutWhQty()+enter.getQty());
                    opeOutWhScooterB.setUpdatedBy(enter.getUserId());
                    opeOutWhScooterB.setUpdatedTime(new Date());
                    opeOutWhScooterBService.updateById(opeOutWhScooterB);
                    updateOrderStatus=opeOutWhScooterB.getAlreadyOutWhQty().equals(opeOutWhScooterB.getQty())?Boolean.TRUE : Boolean.FALSE;
                    break;
                case 2 :
                    OpeOutWhCombinB opeOutWhCombinB = opeOutWhCombinBService.getById(enter.getId());
                    opeOutWhCombinB.setAlreadyOutWhQty(opeOutWhCombinB.getAlreadyOutWhQty()+enter.getQty());
                    opeOutWhCombinB.setUpdatedBy(enter.getUserId());
                    opeOutWhCombinB.setUpdatedTime(new Date());
                    opeOutWhCombinBService.updateById(opeOutWhCombinB);
                    updateOrderStatus=opeOutWhCombinB.getAlreadyOutWhQty().equals(opeOutWhCombinB.getQty())?Boolean.TRUE : Boolean.FALSE;
                    break;
                default:
                    OpeOutWhPartsB opeOutWhPartsB = opeOutWhPartsBService.getById(enter.getId());
                    opeOutWhPartsB.setAlreadyOutWhQty(opeOutWhPartsB.getAlreadyOutWhQty()+enter.getQty());
                    opeOutWhPartsB.setUpdatedBy(enter.getUserId());
                    opeOutWhPartsB.setUpdatedTime(new Date());
                    opeOutWhPartsBService.updateById(opeOutWhPartsB);
                    updateOrderStatus=opeOutWhPartsB.getAlreadyOutWhQty().equals(opeOutWhPartsB.getQty())?Boolean.TRUE : Boolean.FALSE;
                    break;
            }
            //更新
            //查询主单据
            OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(orderId);
            //部分出库单据更新
            if (opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue())){
                OutboundUpdateStatusEnter outboundUpdateStatusEnter = new OutboundUpdateStatusEnter(orderId, OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue(), null,enter.getQty());
                outboundUpdateStatusEnter.setUserId(enter.getUserId());
                this.updateStatus(outboundUpdateStatusEnter);
            }else {
                //订单出库
                if (updateOrderStatus){
                    OutboundUpdateStatusEnter outboundUpdateStatusEnter = new OutboundUpdateStatusEnter(orderId, OutBoundOrderStatusEnums.OUT_STOCK.getValue(), OrderOperationTypeEnums.OUT_STOCK.getValue(),enter.getQty());
                    outboundUpdateStatusEnter.setUserId(enter.getUserId());
                    this.updateStatus(outboundUpdateStatusEnter);

                    //发货单装车
                    InvoiceUpdateStatusEnter invoiceUpdateStatusEnter = new InvoiceUpdateStatusEnter(opeOutWhouseOrder.getInvoiceId(), InvoiceOrderStatusEnums.BE_LOADED.getValue(),
                            OrderOperationTypeEnums.LOADING.getValue());
                    invoiceUpdateStatusEnter.setUserId(enter.getUserId());
                    invoiceOrderService.updateStatus(invoiceUpdateStatusEnter);
                }
            }
        }
        //保存质检记录
        SaveProductQcTraceEnter saveProductQcTraceEnter = new SaveProductQcTraceEnter(opeInvoiceProductSerialNumId,enter.getSerialNum(), enter.getLot(),qcResult?1:0,enter.getImageUrl(),
                saveProductQcInfoEnters);
        saveProductQcTraceEnter.setUserId(enter.getUserId());
        productQcTraceService.save(saveProductQcTraceEnter);

        return new BooleanResult(qcResult);
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/5 4:00 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 单据状态更新
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult updateStatus(OutboundUpdateStatusEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (opeOutWhouseOrder==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        if (enter.getQty()!=null && enter.getQty()!=0){
            opeOutWhouseOrder.setAlreadyOutWhQty(opeOutWhouseOrder.getAlreadyOutWhQty()+enter.getQty());
        }
        opeOutWhouseOrder.setOutWhStatus(enter.getStatus());
        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        opeOutWhouseOrderService.updateById(opeOutWhouseOrder);

        if (enter.getOperatingDynamics()!=null && enter.getOperatingDynamics()!=0){
            //操作动态
            SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), enter.getOperatingDynamics(),
                    opeOutWhouseOrder.getRemark());
            saveOpTraceEnter.setUserId(enter.getUserId());
            productionOrderTraceService.save(saveOpTraceEnter);
        }

        //订单节点
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, enter.getStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(),
                opeOutWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
