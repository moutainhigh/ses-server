package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcItemMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcTraceMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.order.OrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.wms.*;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.qc.QcService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QcProductResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/12 17:59
 */
@Slf4j
@Service
public class QcServiceImpl implements QcService {

    @Reference
    private IdAppService idAppService;
    @Resource
    private OpeOrderQcItemMapper opeOrderQcItemMapper;
    @Resource
    private OpeOrderQcTraceMapper opeOrderQcTraceMapper;
    @Resource
    private OrderSerialBindMapper orderSerialBindMapper;
    @Resource
    private ProductionQualityTemplateMapper templateMapper;
    @Resource
    private ProductionScooterBomMapper scooterBomMapper;
    @Resource
    private ProductionCombinBomMapper combinBomMapper;
    @Resource
    private ProductionPartsMapper partsMapper;
    @Resource
    private InWhouseScooterBMapper inWhouseScooterBMapper;
    @Resource
    private InWhouseCombinBMapper inWhouseCombinBMapper;
    @Resource
    private InWhousePartsBMapper inWhousePartsBMapper;
    @Resource
    private OutWhScooterBMapper outWhScooterBMapper;
    @Resource
    private OutWhCombinBMapper outWhCombinBMapper;
    @Resource
    private OutWhPartsBMapper outWhPartsBMapper;
    @Resource
    private OutWarehouseOrderMapper outWarehouseOrderMapper;
    @Resource
    private InvoiceProductSerialNumMapper invoiceProductSerialNumMapper;
    @Resource
    private WmsPartsStockMapper wmsPartsStockMapper;
    @Resource
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Resource
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Resource
    private WmsQualifiedScooterStockMapper wmsQualifiedScooterStockMapper;
    @Resource
    private WmsQualifiedCombinStockMapper wmsQualifiedCombinStockMapper;
    @Resource
    private WmsQualifiedPartsStockMapper wmsQualifiedPartsStockMapper;
    @Resource
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public QueryQcTemplateResultDTO getQcTemplateByIdAndType(QueryQcTemplateParamDTO paramDTO) {
        QueryQcTemplateResultDTO resultDTO = new QueryQcTemplateResultDTO();

        /**
         * 入库单和出库单区别在于：如果是原料入库单的时候ope_order_serial_bind表是没有数据的,这个时候只能通过扫码得到的
         * 部件号去ope_production_parts表中查询部件的信息(原料入库只会有部件), 至于出库单ope_order_serial_bind这张表肯定是有数据的
         */
        OpeOrderSerialBind opeOrderSerialBind = null;
        if (ProductTypeEnums.SCOOTER.getValue().equals(paramDTO.getProductType())
                || ProductTypeEnums.COMBINATION.getValue().equals(paramDTO.getProductType())) {
            opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());

            RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
        }

        /**
         * 根据入参productType调整为质检模板的产品类型
         */
        Long productionId;
        Integer productionType;
        switch (paramDTO.getProductType()) {
            case 1:
                OpeProductionScooterBom opeProductionScooterBom = scooterBomMapper.getScooterBomById(opeOrderSerialBind.getProductId());
                resultDTO.setName(opeProductionScooterBom.getEnName());

                productionId = opeProductionScooterBom.getId();
                productionType = QcTemplateProductTypeEnum.SCOOTER.getType();
                break;
            case 2 :
                OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(opeOrderSerialBind.getProductId());
                resultDTO.setName(opeProductionCombinBom.getCnName());

                productionId = opeProductionCombinBom.getId();
                productionType = QcTemplateProductTypeEnum.COMBINATION.getType();
                break;
            default:
                OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByPartsNo(paramDTO.getPartsNo());
                resultDTO.setName(opeProductionParts.getCnName());

                productionId = opeProductionParts.getId();
                productionType = QcTemplateProductTypeEnum.PARTS.getType();
                break;
        }

        List<ProductQcTemplateDTO> productQcTemplateList = templateMapper.getQcTemplateByProductIdAndType(productionId, productionType);
        RpsAssert.isEmpty(productQcTemplateList, ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());

        List<Long> templateIdList = productQcTemplateList.stream().map(ProductQcTemplateDTO::getId).collect(Collectors.toList());

        List<ProductQcTemplateResultDTO> qcResultList = templateMapper.getProductQcTemplateResultByTemplateIds(templateIdList);
        RpsAssert.isEmpty(qcResultList, ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());

        /**
         * {productQualityTempateId, List<ProductQcTemplateResultDTO>}
         */
        Map<Long, List<ProductQcTemplateResultDTO>> templateBMap = qcResultList.stream().collect(
                Collectors.groupingBy(ProductQcTemplateResultDTO::getTemplateId)
        );

        productQcTemplateList.forEach(template ->{
            template.setQcTemplateResultList(templateBMap.get(template.getId()));
        });

        resultDTO.setProductQcTemplateList(productQcTemplateList);
        return resultDTO;
    }

    @Override
    public SaveQcResultDTO saveQcResult(SaveQcResultParamDTO paramDTO) {
        /**
         * 公共参数 ---- userId、qcResultFlag、saveQcResultDTO、opeOrderQcTraceList
         */
        Long userId = paramDTO.getUserId();
        boolean qcResultFlag = true;
        // 质检结果返回对象
        SaveQcResultDTO saveQcResultDTO = new SaveQcResultDTO();
        // 质检记录集合对象
        List<OpeOrderQcTrace> opeOrderQcTraceList = new ArrayList<>();

        List<QcProductResultDTO> qcProductResultList = null;
        try {
            qcProductResultList = JSONArray.parseArray(paramDTO.getSt(), QcProductResultDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        /**
         * 查询质检模板信息, 用于检查产品质检结果是否通过
         */
        QueryQcTemplateParamDTO queryParam = new QueryQcTemplateParamDTO();
        queryParam.setPartsNo(paramDTO.getPartsNo());
        queryParam.setSerialNum(paramDTO.getSerialNum());
        queryParam.setProductType(paramDTO.getProductType());

        QueryQcTemplateResultDTO qcTemplateResult = getQcTemplateByIdAndType(queryParam);
        List<ProductQcTemplateDTO> productQcTemplateList = qcTemplateResult.getProductQcTemplateList();

        /**
         * {templateId, List<ProductQcTemplateResultDTO>}
         */
        Map<Long, List<ProductQcTemplateResultDTO>> qcResultMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t.getQcTemplateResultList())
        );

        /**
         * {templateId, ProductQcTemplateDTO}
         */
        Map<Long, ProductQcTemplateDTO> qcTemplateMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t)
        );

        // 质检条目
        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        Long qcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(qcItemId);
        opeOrderQcItem.setRevision(1);
        opeOrderQcItem.setCreatedBy(paramDTO.getUserId());
        opeOrderQcItem.setCreatedTime(new Date());
        opeOrderQcItem.setUpdatedBy(paramDTO.getUserId());
        opeOrderQcItem.setUpdatedTime(new Date());

        /**
         * 检查质检结果是否通过
         */
        for (QcProductResultDTO qc : qcProductResultList) {
            // 组装质检记录数据
            OpeOrderQcTrace opeOrderQcTrace = new OpeOrderQcTrace();
            opeOrderQcTrace.setId(idAppService.getId(SequenceName.OPE_ORDER_QC_TRACE));
            opeOrderQcTrace.setProductQcTemplateBId(qc.getTemplateResultId());
            opeOrderQcTrace.setProductQcTemplateId(qc.getTemplateId());
            opeOrderQcTrace.setProductQcTemplateName(qcTemplateMap.get(qc.getTemplateId()).getQcItemName());
            opeOrderQcTrace.setQcItemId(qcItemId);
            opeOrderQcTrace.setPicture(qc.getImageUrls());
            opeOrderQcTrace.setRevision(1);
            opeOrderQcTrace.setRemark(qc.getRemark());
            opeOrderQcTrace.setCreatedTime(new Date());
            opeOrderQcTrace.setUpdatedTime(new Date());

            // 质检结果校验
            for (ProductQcTemplateResultDTO result : qcResultMap.get(qc.getTemplateId())) {
                if (qc.getTemplateResultId().equals(result.getId())) {
                    opeOrderQcTrace.setProductQcTemplateBName(result.getQcResult());
                    if (!result.getPassFlag()) {
                        qcResultFlag = false;
                    }
                }
            }
            opeOrderQcTraceList.add(opeOrderQcTrace);
        }

        // 质检结果 pass/ng
        opeOrderQcItem.setQcResult(qcResultFlag ? 1 : 2);

        /**
         * 保存订单序列号绑定信息,后面逻辑都是基于质检成功才会走的
         */
        Long bomId = null;
        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        /**
         * 入库单质检流程 start
         */
        if (1 == paramDTO.getType()) {
            OpeOrderSerialBind opeOrderSerialBindNew = new OpeOrderSerialBind();

            /**
             * 部件入库单表示当前是入原料库,因为是第一次入库质检此时ope_order_serial_bind表中是没有数据的,所以这个时候只能去
             * 入库单部件表中查询数据,或者查询部件表也行,不过部件表拿不到子单据id
             */
            if (ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType())) {
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsByPartsNoAndInWhId(paramDTO.getPartsNo(),
                        paramDTO.getOrderId());

                RpsAssert.isNull(opeInWhousePartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeOrderSerialBindNew.setOrderBId(opeInWhousePartsB.getPartsId());
                opeOrderSerialBindNew.setOrderType(OrderTypeEnums.SHIPPING.getValue());
                opeOrderSerialBindNew.setProductId(opeInWhousePartsB.getId());
                opeOrderSerialBindNew.setProductType(paramDTO.getProductType());
            } else {
                BeanUtils.copyProperties(opeOrderSerialBind, opeOrderSerialBindNew);
            }

            // 订单序列号绑定表(这张表放的都是入库单质检完成所产生的数据)
            Long orderSerialId = idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND);
            opeOrderSerialBindNew.setId(orderSerialId);
            opeOrderSerialBindNew.setSerialNum(String.format("%s-TEST", System.currentTimeMillis()));
            opeOrderSerialBindNew.setLot(paramDTO.getLot());
            opeOrderSerialBindNew.setCreatedBy(paramDTO.getUserId());
            opeOrderSerialBindNew.setCreatedTime(new Date());
            opeOrderSerialBindNew.setUpdatedBy(paramDTO.getUserId());
            opeOrderSerialBindNew.setUpdatedTime(new Date());

            // 质检条目表
            BeanUtils.copyProperties(opeOrderSerialBindNew, opeOrderQcItem);
            opeOrderQcItem.setId(qcItemId);
            opeOrderQcItem.setOrderSerialId(orderSerialId);
            opeOrderQcItem.setOrderType(OrderTypeEnums.SHIPPING.getValue());

            // 质检结果返回对象,用于打印使用(只针对于入库单)
            saveQcResultDTO.setSerialNum(opeOrderSerialBindNew.getSerialNum());
            saveQcResultDTO.setLot(paramDTO.getLot());
            saveQcResultDTO.setPartsNo(paramDTO.getPartsNo());

            bomId = opeOrderSerialBindNew.getProductId();
            /**
             * 1.修改入库单产品的出库数量
             * 2.库存增减操作
             * 这里有个地方容易把人搞乱,订单序列号绑定表里面那个productId理论上应该是叫bomId的(由于表字段命名问题,所以导致现在有点乱)
             */
            if (qcResultFlag) {
                updateInWhOrder(opeOrderSerialBindNew.getOrderBId(), paramDTO.getProductType(), paramDTO.getOrderId());
            }
        } else {
            /**
             * 出库单质检流程 start
             */
            // 出库单产品序列号绑定表(它虽然表名是叫发货单产品序列号表,但是现在只有出库单才会对这张表做操作)
            OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
            BeanUtils.copyProperties(opeOrderSerialBind, opeInvoiceProductSerialNum);

            opeInvoiceProductSerialNum.setId(idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM));
            opeInvoiceProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
            opeInvoiceProductSerialNum.setRelationType(paramDTO.getProductType());
            opeInvoiceProductSerialNum.setCreatedBy(userId);
            opeInvoiceProductSerialNum.setCreatedTime(new Date());
            opeInvoiceProductSerialNum.setUpdatedBy(userId);
            opeInvoiceProductSerialNum.setUpdatedTime(new Date());

            // 质检条目表
            BeanUtils.copyProperties(opeOrderSerialBind, opeOrderQcItem);
            opeOrderQcItem.setId(qcItemId);
            opeOrderQcItem.setOrderSerialId(opeOrderSerialBind.getId());
            opeOrderQcItem.setOrderType(OrderTypeEnums.OUTBOUND.getValue());

            bomId = opeInvoiceProductSerialNum.getProductId();
            /**
             * 1.修改出库单产品的出库数量
             * 2.库存增减操作
             */
            if (qcResultFlag) {
                updateOutWhOrder(opeInvoiceProductSerialNum.getRelationId(), paramDTO.getProductType(), paramDTO.getOrderId());
            }
        }

        /**
         * 保存产品质检记录
         */
        opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
        opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

        /**
         * 库存增减, 这里会涉及到原料库、成品库和不合格品库
         */
        updateWmsStock(bomId,paramDTO.getProductType(), qcResultFlag, paramDTO.getType());

        return saveQcResultDTO;
    }


    /**
     * 修改出库单信息
     * @param productId
     * @param productType
     * @param orderId
     */
    private void updateOutWhOrder(Long productId, Integer productType, Long orderId) {
        OutWhOrderProductDetailDTO outWhOrderProduct = null;
        switch (productType) {
            case 1:
                outWhOrderProduct = outWhScooterBMapper.getScooterProductDetailByProductId(productId);
                // 出库单产品数量全部质检完后无需再质检
                RpsAssert.isTrue(outWhOrderProduct.getQty().equals(outWhOrderProduct.getAlreadyOutWhQty()), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                OpeOutWhScooterB opeOutWhScooterB = new OpeOutWhScooterB();
                opeOutWhScooterB.setId(outWhOrderProduct.getId());
                opeOutWhScooterB.setAlreadyOutWhQty(outWhOrderProduct.getAlreadyOutWhQty() + 1);
                opeOutWhScooterB.setUpdatedTime(new Date());

                outWhScooterBMapper.updateOutWhScooterB(opeOutWhScooterB);
                break;
            case 2:
                outWhOrderProduct = outWhCombinBMapper.getCombinProductDetailByProductId(productId);
                RpsAssert.isTrue(outWhOrderProduct.getQty().equals(outWhOrderProduct.getAlreadyOutWhQty()), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                OpeOutWhCombinB opeOutWhCombinB = new OpeOutWhCombinB();
                opeOutWhCombinB.setId(outWhOrderProduct.getId());
                opeOutWhCombinB.setAlreadyOutWhQty(outWhOrderProduct.getAlreadyOutWhQty() + 1);
                opeOutWhCombinB.setUpdatedTime(new Date());

                outWhCombinBMapper.updateOutWhCombinB(opeOutWhCombinB);
                break;
            default:
                outWhOrderProduct = outWhPartsBMapper.getPartsProductDetailByProductId(productId);
                RpsAssert.isTrue(outWhOrderProduct.getQty().equals(outWhOrderProduct.getAlreadyOutWhQty()), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                OpeOutWhPartsB opeOutWhPartsB = new OpeOutWhPartsB();
                opeOutWhPartsB.setId(outWhOrderProduct.getId());
                opeOutWhPartsB.setAlreadyOutWhQty(outWhOrderProduct.getAlreadyOutWhQty() + 1);
                opeOutWhPartsB.setUpdatedTime(new Date());

                outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);
                break;
        }

        // 将出库单状态改为【部分出库】, 这里只会在产品第一次质检的时候才会修改出库单状态
        if (outWhOrderProduct.getAlreadyOutWhQty() < 1) {
            OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
            opeOutWhouseOrder.setId(orderId);
            opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue());
            outWarehouseOrderMapper.updateOutWarehouseOrder(opeOutWhouseOrder);
        }
    }

    /**
     * 修改入库单信息：
     * @param productId
     * @param productType
     * @param orderId
     */
    private void updateInWhOrder(Long productId, Integer productType, Long orderId) {

        switch (productType) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = null;

                break;
            case 2:
                OpeInWhouseCombinB opeInWhouseCombinB = null;

                break;
            default:
                OpeInWhousePartsB opeInWhousePartsB = null;

                break;
        }
    }

    /**
     * 库存增减, 这里会涉及到原料库、成品库和不合格品库
     * @param bomId
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param qcResultFlag 质检结果标记 true/false
     * @param type 单据类型 1入库单 2出库单
     */
    private void updateWmsStock(Long bomId, Integer productType, Boolean qcResultFlag, Integer type) {

        if (1 == type) {
            /**
             * 入库单车辆、组装件和部件库存增减操作
             * 入库：可用库存+, 待入库-
             */
            switch (productType) {
                case 1:
                    if (qcResultFlag) {
                        OpeProductionScooterBom opeProductionScooterBom = null;

                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(opeProductionScooterBom.getGroupId(),
                                opeProductionScooterBom.getColorId());
                        opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() + 1);
                        opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - 1);

                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                    } else {
                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = null;
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    if (qcResultFlag) {
                        OpeWmsCombinStock opeWmsCombinStock = null;
                        opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + 1);
                        opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - 1);

                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                    } else {
                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = null;
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    if (qcResultFlag) {
                        OpeWmsPartsStock opeWmsPartsStock = null;
                        opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + 1);
                        opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - 1);

                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                    } else {
                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = null;
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + 1);

                        wmsQualifiedPartsStockMapper.updateWmsQualifiedPartsStock(opeWmsQualifiedPartsStock);
                    }
                    break;
            }
        } else {
            /**
             * 出库单车辆、组装件和部件库存增减操作
             * 出库：已用库存+, 待出库-
             */
            switch (productType) {
                case 1:
                    if (qcResultFlag) {
                        OpeWmsScooterStock opeWmsScooterStock = null;
                        opeWmsScooterStock.setUsedStockQty(opeWmsScooterStock.getUsedStockQty() + 1);
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - 1);

                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                    } else {
                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = null;
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    if (qcResultFlag) {
                        OpeWmsCombinStock opeWmsCombinStock = null;
                        opeWmsCombinStock.setUsedStockQty(opeWmsCombinStock.getUsedStockQty() + 1);
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - 1);

                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                    } else {
                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = null;
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    if (qcResultFlag) {
                        OpeWmsPartsStock opeWmsPartsStock = null;
                        opeWmsPartsStock.setUsedStockQty(opeWmsPartsStock.getUsedStockQty() + 1);
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - 1);

                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                    } else {
                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = null;
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + 1);

                        wmsQualifiedPartsStockMapper.updateWmsQualifiedPartsStock(opeWmsQualifiedPartsStock);
                    }
                    break;
            }

        }

    }

}
