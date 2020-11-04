package com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.outbound.OutBoundOrderSrviceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.OutBoundOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private OpeOrderSerialBindService opeOrderSerialBindService;

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
    private OrderStatusFlowService orderStatusFlowService;

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
                //opeProductionScooterBomService.getById();
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
                qcTempleteList.add(new ProductQcTempleteResultResult(tempateB.getId(),tempateB.getProductQualityTempateId(),tempateB.getQcResult(),tempateB.getUploadFlag(),tempateB.getResultsSequence()));
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
        switch (enter.getProductType()){
            case 1:
                OpeOutWhScooterB opeOutWhScooterB = opeOutWhScooterBService.getById(enter.getId());
                if (opeOutWhScooterB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                break;
            case 2 :
                OpeOutWhCombinB opeOutWhCombinB = opeOutWhCombinBService.getById(enter.getId());
                if (opeOutWhCombinB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                break;
            default :
                OpeOutWhPartsB opeOutWhPartsB = opeOutWhPartsBService.getById(enter.getId());
                if (opeOutWhPartsB==null){
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                break;
        }
        //查询产品质检模版
        //获取质检结果
        //保存质检记录
        return null;
    }
}
