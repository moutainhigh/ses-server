package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.scooter.service.ScooterService;
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
import java.util.*;
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
    @Reference
    private ScooterService scooterService;
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
         * 根据入参productType调整为质检模板的产品类型
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeProductionScooterBom opeProductionScooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                resultDTO.setName(opeProductionScooterBom.getEnName());
                break;
            case 2 :
                OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(paramDTO.getBomId());
                resultDTO.setName(opeProductionCombinBom.getCnName());
                break;
            default:
                OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByBomId(paramDTO.getBomId());
                resultDTO.setName(opeProductionParts.getCnName());
                break;
        }

        List<ProductQcTemplateDTO> productQcTemplateList = templateMapper.getQcTemplateByProductId(paramDTO.getBomId());
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
        queryParam.setBomId(paramDTO.getBomId());
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
        opeOrderQcItem.setCreatedBy(userId);
        opeOrderQcItem.setCreatedTime(new Date());
        opeOrderQcItem.setUpdatedBy(userId);
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

        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        /**
         * 入库单质检流程 start
         */
        if (1 == paramDTO.getType()) {
            OpeOrderSerialBind opeOrderSerialBindNew = new OpeOrderSerialBind();

            /**
             * 部件入库单表示当前是入原料库,因为是第一次入库质检此时ope_order_serial_bind表中是没有数据的,所以这个时候只能去
             * 入库单部件表中查询数据
             */
            if (ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType())) {
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());

                RpsAssert.isNull(opeInWhousePartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeOrderSerialBindNew.setOrderBId(paramDTO.getProductId());
                opeOrderSerialBindNew.setOrderType(OrderTypeEnums.SHIPPING.getValue());
                opeOrderSerialBindNew.setProductId(opeInWhousePartsB.getPartsId());
                opeOrderSerialBindNew.setProductType(paramDTO.getProductType());
            } else {
                BeanUtils.copyProperties(opeOrderSerialBind, opeOrderSerialBindNew);
            }

            // 订单序列号绑定表(这张表放的都是入库单质检完成所产生的数据)
            Long orderSerialId = idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND);
            opeOrderSerialBindNew.setId(orderSerialId);
            opeOrderSerialBindNew.setSerialNum(getProductSerialNum(paramDTO.getProductType()));
            opeOrderSerialBindNew.setLot(paramDTO.getLot());
            opeOrderSerialBindNew.setCreatedBy(userId);
            opeOrderSerialBindNew.setCreatedTime(new Date());
            opeOrderSerialBindNew.setUpdatedBy(userId);
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

            /**
             * 1.修改入库单产品的出库数量
             * 2.库存增减操作
             * 这里有个地方容易把人搞乱,订单序列号绑定表里面那个productId理论上应该是叫bomId的(由于表字段命名问题,所以导致现在有点乱)
             */
            updateInWhOrder(paramDTO.getProductId(), paramDTO.getProductType(), qcResultFlag, opeOrderSerialBindNew);
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

            /**
             * 1.修改出库单产品的出库数量
             * 2.库存增减操作
             */
            if (qcResultFlag) {
                updateOutWhOrder(paramDTO.getProductId(), paramDTO.getProductType(), opeInvoiceProductSerialNum);
            }
            /**
             * 出库单库存增减操作
             */
            updateWmsStock(paramDTO.getBomId(), paramDTO.getProductType(), 2, userId, qcResultFlag);

        }

        /**
         * 保存产品质检记录
         */
        opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
        opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

        return saveQcResultDTO;
    }


    /**
     * 修改出库单信息
     * @param productId 产品id
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param opeInvoiceProductSerialNum
     */
    private void updateOutWhOrder(Long productId, Integer productType, OpeInvoiceProductSerialNum opeInvoiceProductSerialNum) {

        OutWhOrderProductDetailDTO outWhOrderProduct;
        switch (productType) {
            case 1:
                outWhOrderProduct = outWhScooterBMapper.getScooterProductDetailByProductId(productId);
                // 出库单产品数量全部质检完后无需再质检
                RpsAssert.isTrue(outWhOrderProduct.getQty().equals(outWhOrderProduct.getAlreadyOutWhQty()), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                // 出库单直接改的就是已出库的数量, 出库直接就是由仓库这边的人操作了,不需要像入库单那边一样从质检完成到入库需要走两步
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
            opeOutWhouseOrder.setId(null);
            opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue());
            outWarehouseOrderMapper.updateOutWarehouseOrder(opeOutWhouseOrder);
        }

        /**
         * 保存出库单序列号绑定信息
         */
        invoiceProductSerialNumMapper.insertInvoiceProductSerialNum(opeInvoiceProductSerialNum);

    }

    /**
     * 修改入库单信息
     * @param productId 产品id,入库单子单据id
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param qcResultFlag 质检结果 true/false
     * @param opeOrderSerialBind 订单序列号绑定信息
     */
    private void updateInWhOrder(Long productId, Integer productType, Boolean qcResultFlag, OpeOrderSerialBind opeOrderSerialBind) {

        switch (productType) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(productId);

                RpsAssert.isTrue(opeInWhouseScooterB.getInWhQty().equals(opeInWhouseScooterB.getQcQty()),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                // 质检成功, 已质检数量+1; 质检失败,不合格数量+1(实际入库数量在入库单那边操作)
                if (qcResultFlag) {
                    opeInWhouseScooterB.setQcQty(opeInWhouseScooterB.getQcQty() + 1);
                } else {
                    opeInWhouseScooterB.setUnqualifiedQty(opeInWhouseScooterB.getUnqualifiedQty() + 1);
                }

                opeInWhouseScooterB.setUpdatedTime(new Date());
                inWhouseScooterBMapper.updateInWhouseScooter(opeInWhouseScooterB);
                break;
            case 2:
                OpeInWhouseCombinB opeInWhouseCombinB = inWhouseCombinBMapper.getInWhouseCombinById(productId);
                RpsAssert.isTrue(opeInWhouseCombinB.getInWhQty().equals(opeInWhouseCombinB.getQcQty()),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                if (qcResultFlag) {
                    opeInWhouseCombinB.setQcQty(opeInWhouseCombinB.getQcQty() + 1);
                } else {
                    opeInWhouseCombinB.setUnqualifiedQty(opeInWhouseCombinB.getUnqualifiedQty() + 1);
                }

                opeInWhouseCombinB.setUpdatedTime(new Date());
                inWhouseCombinBMapper.updateInWhouseCombin(opeInWhouseCombinB);
                break;
            default:
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(productId);
                RpsAssert.isTrue(opeInWhousePartsB.getInWhQty().equals(opeInWhousePartsB.getQcQty()),
                        ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

                if (qcResultFlag) {
                    opeInWhousePartsB.setQcQty(opeInWhousePartsB.getQcQty() + 1);
                } else {
                    opeInWhousePartsB.setUnqualifiedQty(opeInWhousePartsB.getUnqualifiedQty() + 1);
                }

                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);
                break;
        }

        /**
         * 保存订单序列号绑定信息
         */
        if (qcResultFlag) {
            orderSerialBindMapper.insertOrderSerialBind(opeOrderSerialBind);
        }
    }

    /**
     * 库存增减, 这里会涉及到原料库、成品库和不合格品库
     * @param bomId
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param type 单据类型 1入库单 2出库单
     * @param userId
     * @param qcResultFlag 质检结果标记 true/false
     */
    private void updateWmsStock(Long bomId, Integer productType, Integer type, Long userId, Boolean qcResultFlag) {
        if (1 == type) {
            /**
             * 入库单车辆、组装件和部件库存增减操作
             * 入库：可用库存+, 待入库-
             */
            switch (productType) {
                case 1:
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(bomId);
                    if (qcResultFlag) {
                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                                scooterBom.getColorId());
                        opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() + 1);
                        opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - 1);

                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                    } else {
                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = wmsQualifiedScooterStockMapper
                                .getWmsQualifiedScooterStockByGroupIdAndColorId(scooterBom.getGroupId(), scooterBom.getColorId());
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    if (qcResultFlag) {
                        OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(bomId);
                        opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + 1);
                        opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - 1);

                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                    } else {
                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = wmsQualifiedCombinStockMapper.getWmsQualifiedCombinStockByBomId(bomId);
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    if (qcResultFlag) {
                        OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(bomId);
                        opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + 1);
                        opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - 1);

                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                    } else {
                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = wmsQualifiedPartsStockMapper.getWmsQualifiedPartsStockByBomId(bomId);
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
            Long relationId;
            Integer relationType;
            switch (productType) {
                case 1:
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(bomId);
                    if (qcResultFlag) {
                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                                scooterBom.getColorId());
                        opeWmsScooterStock.setUsedStockQty(opeWmsScooterStock.getUsedStockQty() + 1);
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - 1);

                        relationId = opeWmsScooterStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_WAREHOUSE.getType();
                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                    } else {
                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = wmsQualifiedScooterStockMapper
                                .getWmsQualifiedScooterStockByGroupIdAndColorId(scooterBom.getGroupId(), scooterBom.getColorId());
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        relationId = opeWmsQualifiedScooterStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    if (qcResultFlag) {
                        OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(bomId);
                        opeWmsCombinStock.setUsedStockQty(opeWmsCombinStock.getUsedStockQty() + 1);
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - 1);

                        relationId = opeWmsCombinStock.getId();
                        relationType = WmsTypeEnum.COMBINATION_WAREHOUSE.getType();
                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                    } else {
                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = wmsQualifiedCombinStockMapper.getWmsQualifiedCombinStockByBomId(bomId);
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        relationId = opeWmsQualifiedCombinStock.getId();
                        relationType = WmsTypeEnum.COMBINATION_UNQUALIFIED_WAREHOUSE.getType();
                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    if (qcResultFlag) {
                        OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(bomId);
                        opeWmsPartsStock.setUsedStockQty(opeWmsPartsStock.getUsedStockQty() + 1);
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - 1);

                        relationId = opeWmsPartsStock.getId();
                        relationType = WmsTypeEnum.PARTS_WAREHOUSE.getType();
                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                    } else {
                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = wmsQualifiedPartsStockMapper.getWmsQualifiedPartsStockByBomId(bomId);
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + 1);

                        relationId = opeWmsQualifiedPartsStock.getId();
                        relationType = WmsTypeEnum.PARTS_UNQUALIFIED_WAREHOUSE.getType();
                        wmsQualifiedPartsStockMapper.updateWmsQualifiedPartsStock(opeWmsQualifiedPartsStock);
                    }
                    break;
            }
            /**
             * 添加出入库记录
             */
            OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
            opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            opeWmsStockRecord.setDr(0);
            opeWmsStockRecord.setRelationId(relationId);
            opeWmsStockRecord.setRelationType(relationType);
            opeWmsStockRecord.setInWhQty(1);
            opeWmsStockRecord.setInWhType(null); // 出库操作不需要填入库类型
            opeWmsStockRecord.setRecordType(Integer.valueOf(InOutWhEnums.OUT.getValue()));
            opeWmsStockRecord.setStockType(1);
            opeWmsStockRecord.setCreatedBy(userId);
            opeWmsStockRecord.setCreatedTime(new Date());
            opeWmsStockRecord.setUpdatedBy(userId);
            opeWmsStockRecord.setUpdatedTime(new Date());

            opeWmsStockRecordMapper.insert(opeWmsStockRecord);
        }
    }

    /**
     * 获取产品序列号
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @return
     */
    private String getProductSerialNum(Integer productType) {
        Calendar cal = Calendar.getInstance();
        // 年、月、日
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH ) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // 查询当前数据库车辆数量
        int count = 0;

        String productRange = null;
        String structureType = null;
        /**
         * 获取产品范围和结构类型, 现在组装件和车辆暂时随机生成
         */
        switch (productType) {
            case 1:
                productRange = "ED";
                structureType = "D";
                break;
            case 2:
                return "COMBINATION" + System.currentTimeMillis();
            default:
                return "PARTS" + System.currentTimeMillis();
        }

        /**
         * 编号规则：
         * 1.目标市场(默认法国)
         * 2.产品范围
         * 3.结构类型
         * 4.生产地点(默认中国南通)
         * 5.年份
         * 6.月份
         * 7.生产流水号(生产日 + 工单号 + 流水号)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append(productRange);
        sb.append(structureType);
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", count + 1);
        sb.append(number);

        return sb.toString();
    }

}
