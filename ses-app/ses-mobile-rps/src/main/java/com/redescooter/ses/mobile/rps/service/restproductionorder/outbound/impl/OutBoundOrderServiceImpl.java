package com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.production.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.restproductionorder.outbound.OutBoundOrderSrviceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhCombinB;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhPartsB;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpeInvoiceProductSerialNumService;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhCombinBService;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhPartsBService;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhScooterBService;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhouseOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPartsService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionQualityTempateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.OutBoundOrderService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.qctrace.ProductQcTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteItemResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteResultResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.QcTempleteEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.SaveQcResultEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.SaveQcTempleteResultEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundDetailProductResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundDetailResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundUpdateStatusEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.ProductDetailResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.ProductSnResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcInfoEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace.SaveProductQcTraceEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: aleks
 * @Date: 2020/10/26 14:25
 * @version???V ROS 1.8.3
 * @Description:
 */
@Service
@Slf4j
public class OutBoundOrderServiceImpl implements OutBoundOrderService {

    @Resource
    private OutBoundOrderSrviceMapper outBoundOrderSrviceMapper;

    @Resource
    private ProductionOrderTraceService productionOrderTraceService;

    @Resource
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Resource
    private OpeProductionQualityTempateBService opeProductionQualityTempateBService;

    @Resource
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Resource
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Resource
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Resource
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Resource
    private ProductQcTraceService productQcTraceService;

    @Resource
    private OrderStatusFlowService orderStatusFlowService;

    @Resource
    private OpeProductionPartsService opeProductionPartsService;

    @Resource
    private OpeInvoiceProductSerialNumService opeInvoiceProductSerialNumService;

    @Resource
    private InvoiceOrderService invoiceOrderService;

    @Resource
    private ProductionQualityTemplateMapper templateMapper;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 12:04 ??????
     * @Param: enter
     * @Return: Map
     * @desc: countByProductType
     */
    @Override
    public Map<Integer, Integer> countByProductType(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = outBoundOrderSrviceMapper.countByProductType(enter);

        /**
         * {outWhType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * @param paramDTO
     * @Description
     * @Author: enter
     * @Date: 2020/11/3 2:28 ??????
     * @Param: enter
     * @Return: Map
     * @des??? ??????????????????
     */
    @Override
    public Map<Integer, Integer> countByOrderType(CountByOrderTypeParamDTO paramDTO) {
        List<CountByStatusResult> countByStatusResultList = outBoundOrderSrviceMapper.countByOrderType(paramDTO);

        /**
         * {outType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (OutBoundOrderTypeEnums item : OutBoundOrderTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:12 ??????
     * @Param: enter
     * @Return: OutboundOrderResult
     * @desc: ???????????????
     */
    @Override
    public PageResult<OutboundOrderResult> list(OutboundOrderEnter enter) {
        int count = outBoundOrderSrviceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, outBoundOrderSrviceMapper.list(enter));
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:25 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ????????????
     */
    @Override
    public GeneralResult startQc(IdEnter enter) {
//        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
//        if (opeOutWhouseOrder==null){
//            throw  new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
//        }
//        if (!opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue())){
//            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
//        }
//        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue());
//        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
//        opeOutWhouseOrder.setUpdatedTime(new Date());
//        opeOutWhouseOrderService.updateById(opeOutWhouseOrder);
//        //????????????
//        SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.START_QC.getValue(),
//                opeOutWhouseOrder.getRemark());
//        saveOpTraceEnter.setUserId(enter.getUserId());
//        productionOrderTraceService.save(saveOpTraceEnter);
//
//        //????????????
//        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(),
//                opeOutWhouseOrder.getRemark());
//        orderStatusFlowEnter.setUserId(enter.getUserId());
//        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 1:37 ??????
     * @Param: enter
     * @Return: OutboundDetailResult
     * @desc: ????????????
     */
    @Override
    public OutboundDetailResult detail(IdEnter enter) {
        OutboundDetailResult detail = outBoundOrderSrviceMapper.detail(enter);
        if (detail == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }
        //??????????????????
        detail.setOutboundDetailProductResultList(this.detailProductList(enter));
        return detail;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 5:40 ??????
     * @Param: enter
     * @Return: List<OutboundDetailProductResult>
     * @desc: ?????????????????????
     */
    @Override
    public List<OutboundDetailProductResult> detailProductList(IdEnter enter) {
//        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
//        if (opeOutWhouseOrder==null){
//            throw  new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
//        }
//        List<OutboundDetailProductResult> result = new ArrayList<>();
//        switch (opeOutWhouseOrder.getOutWhType()){
//            case 1:
//                result=outBoundOrderSrviceMapper.detailProductListByScooter(enter);
//                break;
//            case 2 :
//                result=outBoundOrderSrviceMapper.detailProductListByCombin(enter);
//                break;
//            default:
//                result=outBoundOrderSrviceMapper.detailProductListByPart(enter);
//                break;
//        }
        return null;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 2:48 ??????
     * @Param: enter
     * @Return: ProductDetailResult
     * @desc: ????????????
     */
    @Override
    public ProductDetailResult productOutWhDetail(ProductOutWhDetailEnter enter) {
        ProductDetailResult productDetailResult = null;
        List<ProductSnResult> productSnResultList = new ArrayList<>();
        switch (enter.getProductType()) {
            case 1:
                productDetailResult = outBoundOrderSrviceMapper.productOutWhDetailByScooter(enter);
                //????????????????????????
                if (productDetailResult != null) {
                    productSnResultList = outBoundOrderSrviceMapper.productOutWhDetailByScooterSnList(enter);
                }
                //????????????bom??????
                OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getOne(new LambdaQueryWrapper<OpeProductionScooterBom>()
                        .eq(OpeProductionScooterBom::getColorId, productDetailResult.getColorId())
                        .eq(OpeProductionScooterBom::getGroupId, productDetailResult.getGroupId())
                        .eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue())
                        .last("limit 1"));
                if (opeProductionScooterBom == null) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
                }
                //??????????????????????????????Id???bom??? ?????????BomId
                productDetailResult.setProductId(opeProductionScooterBom.getId());
                break;
            case 2:
                productDetailResult = outBoundOrderSrviceMapper.productOutWhDetailByCombin(enter);
                //????????????????????????
                if (productDetailResult != null) {
                    productSnResultList = outBoundOrderSrviceMapper.productOutWhDetailByCombinSnList(enter);
                }
                break;
            default:
                productDetailResult = outBoundOrderSrviceMapper.productOutWhDetailByPart(enter);
                //????????????????????????
                if (productDetailResult != null) {
                    productSnResultList = outBoundOrderSrviceMapper.productOutWhDetailByPartSnList(enter);
                }
                break;
        }
        productDetailResult.setProductSnResultList(productSnResultList);
        return productDetailResult;
    }

    /**
     * ???????????? -- [????????????????????????????????????????????????????????????????????????????????????????????????(assert)]
     * ???????????????????????????,???????????????????????????{@link com.redescooter.ses.mobile.rps.controller.outwhorder.OutWarehouseOrderController}
     *
     * @param enter
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteItemResult>
     * @author assert
     * @date 2021/1/4
     */
    @Override
    public List<ProductQcTempleteItemResult> qcTemplete(QcTempleteEnter enter) {
        Integer productionType;
        switch (enter.getProductType()) {
            case 1:
                productionType = Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue());
                break;
            case 2:
                productionType = Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue());
                break;
            default:
                productionType = Integer.valueOf(BomCommonTypeEnums.PARTS.getValue());
                break;
        }

        /**
         * query ope_production_quality_tempate info
         */
        enter.setProductType(productionType);
        List<ProductQcTempleteItemResult> qcTemplateList = outBoundOrderSrviceMapper.qcTemplete(enter);
        if (CollectionUtils.isEmpty(qcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
        }

        /**
         * query ope_production_quality_tempate_b info
         */
        List<Long> productionTemplateIdList = qcTemplateList.stream().map(ProductQcTempleteItemResult::getId).collect(Collectors.toList());

        List<ProductQcTempleteResultResult> productionQualityTemplateBList = null;
        if (CollectionUtils.isEmpty(productionQualityTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
        }

        /**
         * {productQualityTempateId, List<ProductQcTempleteResultResult>}
         */
        Map<Long, List<ProductQcTempleteResultResult>> templateBMap = productionQualityTemplateBList.stream().collect(
                Collectors.groupingBy(ProductQcTempleteResultResult::getTemplateId)
        );

        qcTemplateList.forEach(template ->
                template.setQcTemplateList(templateBMap.get(template.getId()))
        );
        return qcTemplateList;
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/2 3:28 ??????
     * @Param: SaveQcResultEnter
     * @Return: GeneralResult
     * @desc: ??????????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BooleanResult saveQcResult(SaveQcResultEnter enter) {
        List<SaveQcTempleteResultEnter> templeteEnterList = new ArrayList<>();
        try {
            templeteEnterList = JSON.parseArray(enter.getSt(), SaveQcTempleteResultEnter.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        //????????????
        Boolean booleanSerial = Boolean.TRUE;
        //?????????Id
        Long orderId = null;
        switch (enter.getProductType()) {
            case 1:
                OpeOutWhScooterB opeOutWhScooterB = opeOutWhScooterBService.getById(enter.getId());
                if (opeOutWhScooterB == null) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                orderId = opeOutWhScooterB.getOutWhId();
                break;
            case 2:
                OpeOutWhCombinB opeOutWhCombinB = opeOutWhCombinBService.getById(enter.getId());
                if (opeOutWhCombinB == null) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                orderId = opeOutWhCombinB.getOutWhId();
                break;
            default:
                OpeOutWhPartsB opeOutWhPartsB = opeOutWhPartsBService.getById(enter.getId());
                if (opeOutWhPartsB == null) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
                }
                OpeProductionParts opeProductionParts = opeProductionPartsService.getById(opeOutWhPartsB.getPartsId());
                if (opeProductionParts == null) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                }
                orderId = opeOutWhPartsB.getOutWhId();
                booleanSerial = opeProductionParts.getIdCalss().equals(0) ? Boolean.FALSE : Boolean.TRUE;
                break;
        }
        //????????????????????????
        List<ProductQcTempleteItemResult> productQcTempleteItemResultList = qcTemplete(new QcTempleteEnter(enter.getId(), enter.getProductType()));
        //???????????????????????????
        Map<Long, ProductQcTempleteResultResult> qcPassMap = new HashMap<>();
        productQcTempleteItemResultList.forEach(item -> {
            ProductQcTempleteResultResult productQcTempleteResultResult = item.getQcTemplateList().stream().filter(result -> result.getPassFlag()).findFirst().orElse(null);
            if (productQcTempleteResultResult == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }
            qcPassMap.put(item.getId(), productQcTempleteResultResult);
        });

        //????????????
        List<SaveProductQcInfoEnter> saveProductQcInfoEnters = new ArrayList<>();

//        for (SaveQcTempleteResultEnter item : templeteEnterList) {
//            if (!qcPassMap.containsKey(item.getItemId())) {
//                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
//            }
//            if (!qcPassMap.get(item.getItemId()).equals(item.getQcResultId())) {
//                qcResult = Boolean.FALSE;
//                break;
//            }
//            ProductQcTempleteItemResult productQcTempleteItemResult = productQcTempleteItemResultList.stream().filter(qcitem -> qcitem.getId().equals(item.getItemId())).findFirst().orElse(null);
//
//            SaveProductQcInfoEnter saveProductQcInfoEnter = new SaveProductQcInfoEnter(item.getItemId(),productQcTempleteItemResult.getItemName(),item.getQcResultId(),qcPassMap.get(item.getItemId()).getQcResult(),item.getImageUrls(),item.getRemark());
//            saveProductQcInfoEnter.setUserId(enter.getUserId());
//            saveProductQcInfoEnters.add(saveProductQcInfoEnter);
//        }

        /**
         * ?????????????????? -- T_T
         */
        boolean qcResult = true;
        for (SaveQcTempleteResultEnter item : templeteEnterList) {
            if (null == qcPassMap.get(item.getItemId())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }

            if (!qcPassMap.get(item.getItemId()).getPassFlag()) {
                qcResult = false;
                break;
            }

            /**
             * ???????????????????????????????????????,???????????????????????????????????????Null -> findFirst-orElse
             */
            ProductQcTempleteItemResult itemResult = productQcTempleteItemResultList.stream().filter(
                    itemR -> itemR.getId().equals(item.getItemId())
            ).findFirst().orElse(null);

            SaveProductQcInfoEnter productQcInfoEnter = new SaveProductQcInfoEnter();
            productQcInfoEnter.setProductQcTemplateBId(item.getItemId());
            productQcInfoEnter.setProductQcTemplateBName(itemResult.getItemName());
            productQcInfoEnter.setProductQcTemplateId(item.getQcResultId());
            productQcInfoEnter.setProductQcTemplateName(qcPassMap.get(item.getItemId()).getQcResult());
            productQcInfoEnter.setPicture(item.getImageUrls());
            productQcInfoEnter.setRemark(item.getRemark());
            productQcInfoEnter.setUserId(enter.getUserId());

            saveProductQcInfoEnters.add(productQcInfoEnter);
        }

        //todo booleanSerial ????????????????????????????????????

        //????????????????????????????????? ??????????????????
        Long opeInvoiceProductSerialNumId = null;
        QueryWrapper<OpeInvoiceProductSerialNum> opeInvoiceProductSerialNumQueryWrapper = new QueryWrapper<>();
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_RELATION_TYPE, OrderTypeEnums.OUTBOUND.getValue());
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_RELATION_ID, enter.getId());
        opeInvoiceProductSerialNumQueryWrapper.eq(OpeEntrustProductSerialNum.COL_LOT, enter.getLot());
        OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = opeInvoiceProductSerialNumService.getOne(opeInvoiceProductSerialNumQueryWrapper);

        if (opeInvoiceProductSerialNum != null) {
            opeInvoiceProductSerialNumId = opeInvoiceProductSerialNum.getId();
        }
        if (!qcResult) {
            opeInvoiceProductSerialNumId = idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM);
            opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
            opeInvoiceProductSerialNum.setId(opeInvoiceProductSerialNumId);
            opeInvoiceProductSerialNum.setDr(0);
            opeInvoiceProductSerialNum.setRelationType(OrderTypeEnums.OUTBOUND.getValue());
            opeInvoiceProductSerialNum.setRelationId(enter.getId());
            opeInvoiceProductSerialNum.setIdClass(enter.getIdClass() ? 1 : 0);
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
        } else {
            opeInvoiceProductSerialNum.setQty(enter.getQty());
        }
        //????????????????????????????????????
        opeInvoiceProductSerialNumService.saveOrUpdate(opeInvoiceProductSerialNum);

        Boolean updateOrderStatus = Boolean.FALSE;
        //???????????????
        if (qcResult) {
            switch (enter.getProductType()) {
                case 1:
                    OpeOutWhScooterB opeOutWhScooterB = opeOutWhScooterBService.getById(enter.getId());
                    opeOutWhScooterB.setAlreadyOutWhQty(opeOutWhScooterB.getAlreadyOutWhQty() + enter.getQty());
                    opeOutWhScooterB.setUpdatedBy(enter.getUserId());
                    opeOutWhScooterB.setUpdatedTime(new Date());
                    opeOutWhScooterBService.updateById(opeOutWhScooterB);
                    updateOrderStatus = opeOutWhScooterB.getAlreadyOutWhQty().equals(opeOutWhScooterB.getQty()) ? Boolean.TRUE : Boolean.FALSE;
                    break;
                case 2:
                    OpeOutWhCombinB opeOutWhCombinB = opeOutWhCombinBService.getById(enter.getId());
                    opeOutWhCombinB.setAlreadyOutWhQty(opeOutWhCombinB.getAlreadyOutWhQty() + enter.getQty());
                    opeOutWhCombinB.setUpdatedBy(enter.getUserId());
                    opeOutWhCombinB.setUpdatedTime(new Date());
                    opeOutWhCombinBService.updateById(opeOutWhCombinB);
                    updateOrderStatus = opeOutWhCombinB.getAlreadyOutWhQty().equals(opeOutWhCombinB.getQty()) ? Boolean.TRUE : Boolean.FALSE;
                    break;
                default:
                    OpeOutWhPartsB opeOutWhPartsB = opeOutWhPartsBService.getById(enter.getId());
                    opeOutWhPartsB.setAlreadyOutWhQty(opeOutWhPartsB.getAlreadyOutWhQty() + enter.getQty());
                    opeOutWhPartsB.setUpdatedBy(enter.getUserId());
                    opeOutWhPartsB.setUpdatedTime(new Date());
                    opeOutWhPartsBService.updateById(opeOutWhPartsB);
                    updateOrderStatus = opeOutWhPartsB.getAlreadyOutWhQty().equals(opeOutWhPartsB.getQty()) ? Boolean.TRUE : Boolean.FALSE;
                    break;
            }
            //??????
            //???????????????
//            OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(orderId);
//            //????????????????????????
//            if (opeOutWhouseOrder.getOutWhStatus().equals(OutBoundOrderStatusEnums.BE_OUTBOUND.getValue())){
//                OutboundUpdateStatusEnter outboundUpdateStatusEnter = new OutboundUpdateStatusEnter(orderId, OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue(), null,enter.getQty());
//                outboundUpdateStatusEnter.setUserId(enter.getUserId());
//                this.updateStatus(outboundUpdateStatusEnter);
//            }else {
//                //????????????
//                if (updateOrderStatus){
//                    OutboundUpdateStatusEnter outboundUpdateStatusEnter = new OutboundUpdateStatusEnter(orderId, OutBoundOrderStatusEnums.OUT_STOCK.getValue(), OrderOperationTypeEnums.OUT_STOCK.getValue(),enter.getQty());
//                    outboundUpdateStatusEnter.setUserId(enter.getUserId());
//                    this.updateStatus(outboundUpdateStatusEnter);
//
//                    //???????????????
//                    InvoiceUpdateStatusEnter invoiceUpdateStatusEnter = new InvoiceUpdateStatusEnter(opeOutWhouseOrder.getInvoiceId(), InvoiceOrderStatusEnums.BE_LOADED.getValue(),
//                            OrderOperationTypeEnums.LOADING.getValue());
//                    invoiceUpdateStatusEnter.setUserId(enter.getUserId());
//                    invoiceOrderService.updateStatus(invoiceUpdateStatusEnter);
//                }
//            }
        }
        //??????????????????
        SaveProductQcTraceEnter saveProductQcTraceEnter = new SaveProductQcTraceEnter(opeInvoiceProductSerialNumId, enter.getSerialNum(), enter.getLot(), qcResult ? 1 : 0, enter.getImageUrl(), saveProductQcInfoEnters);
        saveProductQcTraceEnter.setUserId(enter.getUserId());
        productQcTraceService.save(saveProductQcTraceEnter);
        return new BooleanResult(qcResult);
    }

    /**
     * @param enter
     * @Description
     * @Author: alex
     * @Date: 2020/11/5 4:00 ??????
     * @Param: enter
     * @Return: GeneralResult
     * @desc: ??????????????????
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updateStatus(OutboundUpdateStatusEnter enter) {
//        OpeOutWhouseOrder opeOutWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
//        if (opeOutWhouseOrder==null){
//            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
//        }
//        if (enter.getQty()!=null && enter.getQty()!=0){
//            opeOutWhouseOrder.setAlreadyOutWhQty(opeOutWhouseOrder.getAlreadyOutWhQty()+enter.getQty());
//        }
//        opeOutWhouseOrder.setOutWhStatus(enter.getStatus());
//        opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
//        opeOutWhouseOrder.setUpdatedTime(new Date());
//        opeOutWhouseOrderService.updateById(opeOutWhouseOrder);
//
//        if (enter.getOperatingDynamics()!=null && enter.getOperatingDynamics()!=0){
//            //????????????
//            SaveOpTraceEnter saveOpTraceEnter = new SaveOpTraceEnter(null, opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), enter.getOperatingDynamics(),
//                    opeOutWhouseOrder.getRemark());
//            saveOpTraceEnter.setUserId(enter.getUserId());
//            productionOrderTraceService.save(saveOpTraceEnter);
//        }
//
//        //????????????
//        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, enter.getStatus(), OrderTypeEnums.OUTBOUND.getValue(), opeOutWhouseOrder.getId(),
//                opeOutWhouseOrder.getRemark());
//        orderStatusFlowEnter.setUserId(enter.getUserId());
//        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
