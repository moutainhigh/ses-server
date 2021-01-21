package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
import com.redescooter.ses.mobile.rps.service.qc.QcOrderService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QcProductResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
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
public class QcOrderServiceImpl implements QcOrderService {

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
    public Map<Integer, Integer> getQcOrderTypeCount(GeneralEnter enter) {
        return null;
    }

    @Override
    public Map<Integer, Integer> getQcTypeCount(CountByOrderTypeParamDTO paramDTO) {
        return null;
    }

    @Override
    public QueryQcTemplateResultDTO getQcTemplateByIdAndType(QueryQcTemplateParamDTO paramDTO) {
        QueryQcTemplateResultDTO resultDTO = new QueryQcTemplateResultDTO();
        /**
         * 根据入参productType调整为质检模板的产品类型
         */
        Integer productType;
        switch (paramDTO.getProductType()) {
            case 1:
                String scooterModel = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                resultDTO.setName(scooterModel);

                productType = QcTemplateProductTypeEnum.SCOOTER.getType();
                break;
            case 2 :
                OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(paramDTO.getBomId());
                resultDTO.setName(opeProductionCombinBom.getCnName());

                productType = QcTemplateProductTypeEnum.COMBINATION.getType();
                break;
            default:
                OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByBomId(paramDTO.getBomId());
                resultDTO.setName(opeProductionParts.getCnName());

                productType = QcTemplateProductTypeEnum.PARTS.getType();
                break;
        }

        List<ProductQcTemplateDTO> productQcTemplateList = templateMapper.getQcTemplateByProductIdAndType(paramDTO.getBomId(), productType);
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
         * 公共参数 ---- userId、qcQty、qcResultFlag、saveQcResultDTO、opeOrderQcTraceList
         */
        Long userId = paramDTO.getUserId();
        Integer qcQty = null == paramDTO.getQcQty() ? 1 : paramDTO.getQcQty();
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

        /**
         * 已经质检的产品不需要再次质检
         */
        if (1 == paramDTO.getType()) {
            OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindByOrderBIdAndOrderType(paramDTO.getProductId(),
                    OrderTypeEnums.FACTORY_INBOUND.getValue());

            RpsAssert.isNotNull(opeOrderSerialBind, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        } else {
            OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = invoiceProductSerialNumMapper
                    .getInvoiceProductSerialNumByRelationIdAndType(paramDTO.getProductId(), paramDTO.getProductType());

            RpsAssert.isNotNull(opeInvoiceProductSerialNum, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        // 质检条目
        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        Long qcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(qcItemId);
        opeOrderQcItem.setSerialNum(paramDTO.getSerialNum());
        opeOrderQcItem.setLot(paramDTO.getLot());
        opeOrderQcItem.setOrderBId(paramDTO.getProductId());
        opeOrderQcItem.setProductId(paramDTO.getBomId());
        opeOrderQcItem.setProductType(paramDTO.getProductType());
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
            opeOrderQcTrace.setCreatedBy(userId);
            opeOrderQcTrace.setCreatedTime(new Date());
            opeOrderQcTrace.setUpdatedBy(userId);
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

        boolean qcResultFlagFinal = qcResultFlag;
        boolean result = transactionTemplate.execute(saveQcResultStatus -> {
            boolean flag = true;
            try {
                /**
                 * 入库单质检流程 start
                 */
                if (1 == paramDTO.getType()) {
                    // 订单序列号绑定表(这张表放的都是入库单质检完成所产生的数据)
                    OpeOrderSerialBind opeOrderSerialBindNew = new OpeOrderSerialBind();
                    Long orderSerialId = idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND);

                    opeOrderSerialBindNew.setId(orderSerialId);
                    opeOrderSerialBindNew.setOrderBId(paramDTO.getProductId());
                    opeOrderSerialBindNew.setOrderType(OrderTypeEnums.FACTORY_INBOUND.getValue());
                    opeOrderSerialBindNew.setProductType(paramDTO.getProductType());
                    opeOrderSerialBindNew.setSerialNum(getProductSerialNum(paramDTO.getProductType()));
                    opeOrderSerialBindNew.setLot(paramDTO.getLot());
                    opeOrderSerialBindNew.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
                    opeOrderSerialBindNew.setCreatedBy(userId);
                    opeOrderSerialBindNew.setCreatedTime(new Date());
                    opeOrderSerialBindNew.setUpdatedBy(userId);
                    opeOrderSerialBindNew.setUpdatedTime(new Date());

                    // 质检条目表
                    opeOrderQcItem.setOrderType(OrderTypeEnums.FACTORY_INBOUND.getValue());

                    // 质检结果返回对象,用于打印使用(只针对于入库单)
                    saveQcResultDTO.setSerialNum(opeOrderSerialBindNew.getSerialNum());
                    saveQcResultDTO.setLot(paramDTO.getLot());
                    saveQcResultDTO.setPartsNo(paramDTO.getPartsNo());

                    /**
                     * 1.修改入库单产品的出库数量
                     * 2.库存增减操作
                     * 这里有个地方容易把人搞乱,订单序列号绑定表里面那个productId理论上应该是叫bomId的(由于表字段命名问题,所以导致现在有点乱)
                     */
                    updateInWhOrder(paramDTO.getProductId(), paramDTO.getProductType(), qcResultFlagFinal, qcQty, opeOrderSerialBindNew);

                    /**
                     * 入库单库存增减操作
                     */
                    updateWmsStock(paramDTO.getBomId(), paramDTO.getProductType(), 1, userId, qcQty, qcResultFlagFinal);
                } else {
                    /**
                     * 出库单质检流程 start
                     */
                    // 出库单产品序列号绑定表(它虽然表名是叫发货单产品序列号表,但是现在只有出库单才会对这张表做操作)
                    OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
                    Long id = idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM);

                    opeInvoiceProductSerialNum.setId(id);
                    opeInvoiceProductSerialNum.setRelationId(paramDTO.getProductId());
                    opeInvoiceProductSerialNum.setRelationType(paramDTO.getProductType());
                    opeInvoiceProductSerialNum.setLot(paramDTO.getLot());
                    opeInvoiceProductSerialNum.setIdClass(null == paramDTO.getQcQty() ? 1 : 0);
                    opeInvoiceProductSerialNum.setProductId(paramDTO.getBomId());
                    opeInvoiceProductSerialNum.setProductType(paramDTO.getProductType());
                    opeInvoiceProductSerialNum.setSerialNum(paramDTO.getSerialNum());
                    opeInvoiceProductSerialNum.setQty(qcQty);
                    opeInvoiceProductSerialNum.setCreatedBy(userId);
                    opeInvoiceProductSerialNum.setCreatedTime(new Date());
                    opeInvoiceProductSerialNum.setUpdatedBy(userId);
                    opeInvoiceProductSerialNum.setUpdatedTime(new Date());

                    // 质检条目表
                    opeOrderQcItem.setOrderType(OrderTypeEnums.OUTBOUND.getValue());

                    /**
                     * 1.修改出库单产品的出库数量
                     * 2.库存增减操作
                     */
                    updateOutWhOrder(paramDTO.getProductId(), paramDTO.getProductType(), qcResultFlagFinal, userId, qcQty, opeInvoiceProductSerialNum);

                    /**
                     * 出库单库存增减操作
                     */
                    updateWmsStock(paramDTO.getBomId(), paramDTO.getProductType(), 2, userId, qcQty, qcResultFlagFinal);
                }

                /**
                 * 保存产品质检记录
                 */
                opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
                opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);
            } catch (Exception e) {
                flag = false;
                log.error("【保存质检结果失败】----{}", ExceptionUtils.getStackTrace(e));
                saveQcResultStatus.setRollbackOnly();
            }
            return flag;
        });

        // 手动抛出事务失败异常
        RpsAssert.isFalse(result, ExceptionCodeEnums.QC_ERROR.getCode(), ExceptionCodeEnums.QC_ERROR.getMessage());

        return saveQcResultDTO;
    }


    /**
     * 修改出库单信息
     * @param productId 产品id
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param qcResultFlag 质检结果 true/false
     * @param userId 用户id
     * @param qcQty 质检数量
     * @param opeInvoiceProductSerialNum
     */
    private void updateOutWhOrder(Long productId, Integer productType, boolean qcResultFlag, Long userId, Integer qcQty,
                                  OpeInvoiceProductSerialNum opeInvoiceProductSerialNum) {
        Long outWhId = null;
        Integer alreadyOutWhQty = 0;

        switch (productType) {
            case 1:
                OpeOutWhScooterB opeOutWhScooterB = outWhScooterBMapper.getOutWhOrderScooterById(productId);
                if (qcResultFlag) {
                    opeOutWhScooterB.setAlreadyOutWhQty(opeOutWhScooterB.getAlreadyOutWhQty() + 1);
                    opeOutWhScooterB.setQcQty(opeOutWhScooterB.getQcQty() + qcQty);
                } else {
                    opeOutWhScooterB.setUnqualifiedQty(opeOutWhScooterB.getUnqualifiedQty() + 1);
                }

                outWhId = opeOutWhScooterB.getOutWhId();
                alreadyOutWhQty = opeOutWhScooterB.getAlreadyOutWhQty();

                opeOutWhScooterB.setUpdatedBy(userId);
                opeOutWhScooterB.setUpdatedTime(new Date());
                outWhScooterBMapper.updateOutWhScooterB(opeOutWhScooterB);
                break;
            case 2:
                OpeOutWhCombinB opeOutWhCombinB = outWhCombinBMapper.getOutWhOrderCombinById(productId);
                if (qcResultFlag) {
                    opeOutWhCombinB.setAlreadyOutWhQty(opeOutWhCombinB.getAlreadyOutWhQty() + 1);
                    opeOutWhCombinB.setQcQty(opeOutWhCombinB.getQcQty() + 1);
                } else {
                    opeOutWhCombinB.setUnqualifiedQty(opeOutWhCombinB.getUnqualifiedQty() + 1);
                }

                outWhId = opeOutWhCombinB.getOutWhId();
                alreadyOutWhQty = opeOutWhCombinB.getAlreadyOutWhQty();

                opeOutWhCombinB.setUpdatedBy(userId);
                opeOutWhCombinB.setUpdatedTime(new Date());
                outWhCombinBMapper.updateOutWhCombinB(opeOutWhCombinB);
                break;
            default:
                OpeOutWhPartsB opeOutWhPartsB = outWhPartsBMapper.getOutWhOrderPartsById(productId);
                // 部件这边因为会存在有码跟无码的质检,所以质检数量需要根据入参来调整
                if (qcResultFlag) {
                    opeOutWhPartsB.setAlreadyOutWhQty(opeOutWhPartsB.getAlreadyOutWhQty() + qcQty);
                    opeOutWhPartsB.setQcQty(opeOutWhPartsB.getQcQty() + qcQty);
                } else {
                    opeOutWhPartsB.setUnqualifiedQty(opeOutWhPartsB.getUnqualifiedQty() + qcQty);
                }

                outWhId = opeOutWhPartsB.getOutWhId();
                alreadyOutWhQty = opeOutWhPartsB.getAlreadyOutWhQty();

                opeOutWhPartsB.setUpdatedBy(userId);
                opeOutWhPartsB.setUpdatedTime(new Date());
                outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);
                break;
        }

        // 将出库单状态改为【部分出库】, 这里只会在产品第一次质检的时候才会修改出库单状态
        if (alreadyOutWhQty == 1) {
            OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
            opeOutWhouseOrder.setId(outWhId);
            opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue());
            opeOutWhouseOrder.setUpdatedBy(userId);
            opeOutWhouseOrder.setUpdatedTime(new Date());

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
     * @param qcQty 质检数量
     * @param opeOrderSerialBind 订单序列号绑定信息
     */
    private void updateInWhOrder(Long productId, Integer productType, Boolean qcResultFlag, Integer qcQty, OpeOrderSerialBind opeOrderSerialBind) {

        switch (productType) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(productId);
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
                // 部件这边因为会存在有码跟无码的质检,所以质检数量需要根据入参来调整
                if (qcResultFlag) {
                    opeInWhousePartsB.setQcQty(opeInWhousePartsB.getQcQty() + qcQty);
                } else {
                    opeInWhousePartsB.setUnqualifiedQty(opeInWhousePartsB.getUnqualifiedQty() + qcQty);
                }

                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);
                break;
        }

        /**
         * 保存订单序列号绑定信息
         */
        orderSerialBindMapper.insertOrderSerialBind(opeOrderSerialBind);
    }

    /**
     * 库存增减, 这里会涉及到原料库、成品库和不合格品库
     * @param bomId
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param type 单据类型 1入库单 2出库单
     * @param userId 用户id
     * @param qcQty 质检数量
     * @param qcResultFlag 质检结果标记 true/false
     */
    private void updateWmsStock(Long bomId, Integer productType, Integer type, Long userId, Integer qcQty, Boolean qcResultFlag) {
        // 公用参数：relationId、relationType、inWhType、recordType
        Long relationId = null;
        Integer relationType = null;
        Integer inWhType = null;
        Integer recordType = null;

        if (1 == type) {
            /**
             * 入库单车辆、组装件和部件库存增减操作
             * 入库：可用库存+, 待入库-
             */
            recordType = Integer.valueOf(InOutWhEnums.IN.getValue());
            switch (productType) {
                case 1:
                    /**
                     * 入库单质检成功最终入库是在入库单那边扫码入库, 质检失败会在这边入不合格品库
                     */
                    if (!qcResultFlag) {
                        OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(bomId);
                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                                scooterBom.getColorId());

                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = wmsQualifiedScooterStockMapper
                                .getWmsQualifiedScooterStockByGroupIdAndColorId(scooterBom.getGroupId(), scooterBom.getColorId());
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        // 设置出入库记录所需参数值
                        relationId = opeWmsQualifiedScooterStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                        inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                        // 质检失败同时也需要将待入库的数量-1, 因为此时会入不合格品库
                        opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - 1);
                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);

                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    if (!qcResultFlag) {
                        OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(bomId);

                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = wmsQualifiedCombinStockMapper.getWmsQualifiedCombinStockByBomId(bomId);
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        // 设置出入库记录所需参数值
                        relationId = opeWmsQualifiedCombinStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                        inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                        // 质检失败同时也需要将待入库的数量-1, 因为此时会入不合格品库
                        opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - 1);
                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);

                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    if (!qcResultFlag) {
                        OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(bomId);

                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = wmsQualifiedPartsStockMapper.getWmsQualifiedPartsStockByBomId(bomId);
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + qcQty);

                        // 设置出入库记录所需参数值
                        relationId = opeWmsQualifiedPartsStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                        inWhType = InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue();

                        // 质检失败同时也需要将待入库的数量-1, 因为此时会入不合格品库
                        opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - qcQty);
                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);

                        wmsQualifiedPartsStockMapper.updateWmsQualifiedPartsStock(opeWmsQualifiedPartsStock);
                    }
                    break;
            }

        } else {
            /**
             * 出库单车辆、组装件和部件库存增减操作
             * 出库：已用库存+, 待出库-
             */
            recordType = Integer.valueOf(InOutWhEnums.OUT.getValue());
            switch (productType) {
                case 1:
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(bomId);

                    OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                            scooterBom.getColorId());
                    if (qcResultFlag) {
                        opeWmsScooterStock.setUsedStockQty(opeWmsScooterStock.getUsedStockQty() + 1);
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - 1);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsScooterStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_WAREHOUSE.getType();

                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                    } else {
                        OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = wmsQualifiedScooterStockMapper
                                .getWmsQualifiedScooterStockByGroupIdAndColorId(scooterBom.getGroupId(), scooterBom.getColorId());
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + 1);

                        // 质检失败同时也需要将待出库的数量-1, 因为此时会入不合格品库
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - 1);
                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsQualifiedScooterStock.getId();
                        relationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();

                        wmsQualifiedScooterStockMapper.updateWmsQualifiedScooterStock(opeWmsQualifiedScooterStock);
                    }
                    break;
                case 2:
                    OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(bomId);
                    if (qcResultFlag) {
                        opeWmsCombinStock.setUsedStockQty(opeWmsCombinStock.getUsedStockQty() + 1);
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - 1);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsCombinStock.getId();
                        relationType = WmsTypeEnum.COMBINATION_WAREHOUSE.getType();

                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                    } else {
                        OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = wmsQualifiedCombinStockMapper.getWmsQualifiedCombinStockByBomId(bomId);
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + 1);

                        // 质检失败同时也需要将待出库的数量-1, 因为此时会入不合格品库
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - 1);
                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsQualifiedCombinStock.getId();
                        relationType = WmsTypeEnum.COMBINATION_UNQUALIFIED_WAREHOUSE.getType();

                        wmsQualifiedCombinStockMapper.updateWmsQualifiedCombinStock(opeWmsQualifiedCombinStock);
                    }
                    break;
                default:
                    OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(bomId);
                    if (qcResultFlag) {
                        opeWmsPartsStock.setUsedStockQty(opeWmsPartsStock.getUsedStockQty() + 1);
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - 1);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsPartsStock.getId();
                        relationType = WmsTypeEnum.PARTS_WAREHOUSE.getType();

                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                    } else {
                        OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = wmsQualifiedPartsStockMapper.getWmsQualifiedPartsStockByBomId(bomId);
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + 1);

                        // 质检失败同时也需要将待出库的数量-1, 因为此时会入不合格品库
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - 1);
                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);

                        // 设置出入库记录所需参数值, 出库单无需“入库类型”
                        relationId = opeWmsQualifiedPartsStock.getId();
                        relationType = WmsTypeEnum.PARTS_UNQUALIFIED_WAREHOUSE.getType();

                        wmsQualifiedPartsStockMapper.updateWmsQualifiedPartsStock(opeWmsQualifiedPartsStock);
                    }
                    break;
            }
        }

        /**
         * 添加出入库记录
         */
        if (null != relationId) {
            opeWmsStockRecordMapper.insert(buildWmsStockRecord(relationId, relationType, inWhType, recordType, userId));
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

    /**
     * 组装出入库记录
     * @param relationId 关联库存id
     * @param relationType 关联库存类型, 详情见{@link WmsTypeEnum}
     * @param inWhType 入库类型, 详情见{@link InWhTypeEnums}
     * @param recordType 记录类型 1入库 2出库, 详情见{@link InOutWhEnums}
     * @param userId 用户id
     * @return
     */
    private OpeWmsStockRecord buildWmsStockRecord(Long relationId, Integer relationType, Integer inWhType, Integer recordType, Long userId) {
        OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
        opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
        opeWmsStockRecord.setDr(0);
        opeWmsStockRecord.setRelationId(relationId);
        opeWmsStockRecord.setRelationType(relationType);
        opeWmsStockRecord.setInWhQty(1);
        opeWmsStockRecord.setInWhType(inWhType); // 出库操作不需要填入库类型
        opeWmsStockRecord.setRecordType(recordType);
        opeWmsStockRecord.setStockType(1);
        opeWmsStockRecord.setCreatedBy(userId);
        opeWmsStockRecord.setCreatedTime(new Date());
        opeWmsStockRecord.setUpdatedBy(userId);
        opeWmsStockRecord.setUpdatedTime(new Date());

        return opeWmsStockRecord;
    }

}
