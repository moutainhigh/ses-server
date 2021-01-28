package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.qc.QcStatusEnum;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.qc.QcTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.NewCombinOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.NewOutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.NewProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.*;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.*;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.qc.QcOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QcProductResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @DubboReference
    private IdAppService idAppService;
    @Autowired
    private OpeOrderQcItemMapper opeOrderQcItemMapper;
    @Autowired
    private OpeOrderQcTraceMapper opeOrderQcTraceMapper;
    @Autowired
    private ProductionQualityTemplateMapper templateMapper;
    @Autowired
    private ProductionScooterBomMapper scooterBomMapper;
    @Autowired
    private ProductionCombinBomMapper combinBomMapper;
    @Autowired
    private ProductionPartsMapper partsMapper;
    @Autowired
    private QcOrderMapper qcOrderMapper;
    @Autowired
    private QcScooterMapper qcScooterMapper;
    @Autowired
    private QcCombinMapper qcCombinMapper;
    @Autowired
    private QcPartsMapper qcPartsMapper;
    @Autowired
    private QcOrderSerialBindMapper qcOrderSerialBindMapper;
    @Autowired
    private OpeProductionPurchaseOrderMapper opeProductionPurchaseOrderMapper;
    @Autowired
    private OpeOutWhouseOrderMapper opeOutWhouseOrderMapper;
    @Autowired
    private OpeCombinOrderMapper opeCombinOrderMapper;
    @Autowired
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;
    @Autowired
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Autowired
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Autowired
    private WmsPartsStockMapper wmsPartsStockMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Override
    public Map<Integer, Integer> getQcOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = qcOrderMapper.getQcOrderTypeCount();
        /**
         * {orderType, totalCount}
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

    @Override
    public Map<Integer, Integer> getQcTypeCount(CountByOrderTypeParamDTO paramDTO) {
        // 调整status值,避免恶意传参导致查询数据有问题
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
        List<CountByStatusResult> countByStatusResultList = qcOrderMapper.getQcTypeCount(paramDTO);
        /**
         * {qcType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult:: getTotalCount)
        );

        for (QcTypeEnum item : QcTypeEnum.values()) {
            if (null == map.get(item.getType())) {
                map.put(item.getType(), 0);
            }
        }
        return map;
    }

    @Override
    public PageResult<QueryQcOrderResultDTO> getQcOrderList(QueryQcOrderParamDTO paramDTO) {
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
        int count = qcOrderMapper.countByQcOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, qcOrderMapper.getQcOrderList(paramDTO));
    }

    @Override
    public GeneralResult startQc(IdEnter enter) {
        QcOrderDetailDTO qcOrderDetailDTO = qcOrderMapper.getQcOrderDetailById(enter.getId());
        RpsAssert.isNull(qcOrderDetailDTO, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!QcStatusEnum.PENDING_QUALITY_INSPECTION.getStatus().equals(qcOrderDetailDTO.getStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());

        // 更新质检单状态为【质检中】
        OpeQcOrder opeQcOrder = new OpeQcOrder();
        opeQcOrder.setId(qcOrderDetailDTO.getId());
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public QcOrderDetailDTO getQcOrderDetailById(IdEnter enter) {
        QcOrderDetailDTO qcOrderDetailDTO = qcOrderMapper.getQcOrderDetailById(enter.getId());
        RpsAssert.isNull(qcOrderDetailDTO, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        /**
         * 查询质检单产品信息 1车辆 2组装件 3部件
         */
        List<QcOrderProductDTO> productList = null;
        switch (qcOrderDetailDTO.getOrderType()) {
            case 1:
                productList = qcScooterMapper.getQcScooterByQcId(enter.getId());
                break;
            case 2:
                productList = qcCombinMapper.getQcCombinByQcId(enter.getId());
                break;
            default:
                productList = qcPartsMapper.getQcPartsByQcId(enter.getId());
                break;
        }

        /**
         * (合格数量 + 不合格数量) < 质检数量 ---- 待质检
         */
        List<QcOrderProductDTO> pendingQcProductList = productList.stream().filter(
                p -> (p.getQualifiedQty() + p.getUnqualifiedQty()) < p.getQty()
        ).collect(Collectors.toList());

        /**
         * 合格数量 > 0 ---- 质检通过
         */
        List<QcOrderProductDTO> qcSuccessProductList = productList.stream().filter(
                p -> p.getQualifiedQty() > 0
        ).collect(Collectors.toList());

        /**
         * 不合格数量 > 0 ---- 质检失败
         */
        List<QcOrderProductDTO> qcFailedProductList = productList.stream().filter(
                p -> p.getUnqualifiedQty() > 0
        ).collect(Collectors.toList());

        qcOrderDetailDTO.setPendingQcProductList(pendingQcProductList);
        qcOrderDetailDTO.setQcSuccessProductList(qcSuccessProductList);
        qcOrderDetailDTO.setQcFailedProductList(qcFailedProductList);
        return qcOrderDetailDTO;
    }

    @Override
    public QcOrderProductDetailDTO getProductDetailByProductId(QueryProductDetailParamDTO paramDTO) {
        QcOrderProductDetailDTO qcOrderProductDetail = null;

        /**
         * 查询质检单产品详情 1车辆 2组装件 3部件
         */
        switch (paramDTO.getProductType()) {
            case 1:
                qcOrderProductDetail = qcScooterMapper.getQcScooterDetailById(paramDTO.getProductId());
                break;
            case 2:
                qcOrderProductDetail = qcCombinMapper.getQcCombinDetailById(paramDTO.getProductId());
                break;
            default:
                qcOrderProductDetail = qcPartsMapper.getQcPartsDetailById(paramDTO.getProductId());
                break;
        }

        return qcOrderProductDetail;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaveScanCodeResultDTO saveQcResult(SaveQcResultParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        resultDTO.setPrintFlag(false);
        /**
         * 公共参数
         */
        Integer remainingQty = 0;
        String name = null;
        Integer qcQty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : paramDTO.getQcQty();
        boolean qcResultFlag = true;

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

        // 避免重复质检
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            String serialNum = qcOrderSerialBindMapper.getDefaultSerialNumBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotBlank(serialNum, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        // 质检条目
        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        Long qcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(qcItemId);
        opeOrderQcItem.setDr(0);
        opeOrderQcItem.setSerialNum(paramDTO.getSerialNum());
        opeOrderQcItem.setLot(paramDTO.getLot());
        opeOrderQcItem.setOrderBId(paramDTO.getProductId());
        opeOrderQcItem.setProductId(paramDTO.getBomId());
        opeOrderQcItem.setProductType(paramDTO.getProductType());
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
            opeOrderQcTrace.setDr(0);
            opeOrderQcTrace.setProductQcTemplateBId(qc.getTemplateResultId());
            opeOrderQcTrace.setProductQcTemplateId(qc.getTemplateId());
            opeOrderQcTrace.setProductQcTemplateName(qcTemplateMap.get(qc.getTemplateId()).getQcItemName());
            opeOrderQcTrace.setQcItemId(qcItemId);
            opeOrderQcTrace.setPicture(qc.getImageUrls());
            opeOrderQcTrace.setRevision(1);
            opeOrderQcTrace.setRemark(qc.getRemark());
            opeOrderQcTrace.setCreatedBy(paramDTO.getUserId());
            opeOrderQcTrace.setCreatedTime(new Date());
            opeOrderQcTrace.setUpdatedBy(paramDTO.getUserId());
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
         * 产品扫码质检 1车辆 2组装件 3部件
         */
        Long qcId = null;
        Long stockId = null;
        switch (paramDTO.getProductType()) {
            case 1:
                OpeQcScooterB opeQcScooterB = qcScooterMapper.getQcScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                // 更新车辆质检合格/不合格数量
                if (qcResultFlag) {
                    opeQcScooterB.setQualifiedQty(opeQcScooterB.getQualifiedQty() + 1);
                } else {
                    opeQcScooterB.setUnqualifiedQty(opeQcScooterB.getUnqualifiedQty() + 1);
                    // 成品库车辆id
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                    OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                            scooterBom.getColorId());
                    if(opeWmsScooterStock == null){
                        opeWmsScooterStock = new OpeWmsScooterStock();
                        opeWmsScooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
                        opeWmsScooterStock.setGroupId(scooterBom.getGroupId());
                        opeWmsScooterStock.setColorId(scooterBom.getColorId());
                        opeWmsScooterStock.setAbleStockQty(0);
                        opeWmsScooterStock.setUsedStockQty(0);
                        opeWmsScooterStock.setWaitOutStockQty(0);
                        opeWmsScooterStock.setWaitInStockQty(0);
                        opeWmsScooterStock.setStockType(1);
                        opeWmsScooterStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsScooterStock.setCreatedTime(new Date());
                        opeWmsScooterStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsScooterStock.setUpdatedTime(new Date());
                        opeWmsScooterStockService.saveOrUpdate(opeWmsScooterStock);
                    }
                    stockId = opeWmsScooterStock.getId();
                }
                opeQcScooterB.setUpdatedBy(paramDTO.getUserId());
                opeQcScooterB.setUpdatedTime(new Date());
                qcScooterMapper.updateQcScooter(opeQcScooterB);

                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                remainingQty = opeQcScooterB.getQty() - (opeQcScooterB.getQualifiedQty() + opeQcScooterB.getUnqualifiedQty());
                qcId = opeQcScooterB.getQcId();
                break;
            case 2:
                OpeQcCombinB opeQcCombinB = qcCombinMapper.getQcCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新组装件质检合格/不合格数量
                if (qcResultFlag) {
                    opeQcCombinB.setQualifiedQty(opeQcCombinB.getQualifiedQty() + 1);
                } else {
                    opeQcCombinB.setUnqualifiedQty(opeQcCombinB.getUnqualifiedQty() + 1);
                    // 成品库组装件id
                    OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(paramDTO.getBomId());
                    if (opeWmsCombinStock == null){
                        opeWmsCombinStock = new OpeWmsCombinStock();
                        opeWmsCombinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                        opeWmsCombinStock.setStockType(1);
                        opeWmsCombinStock.setProductionCombinBomId(paramDTO.getBomId());
                        // 获取组装件的别的信息
                        OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomService.getById(paramDTO.getBomId());
                        if (opeProductionCombinBom != null) {
                            opeWmsCombinStock.setCombinNo(opeProductionCombinBom.getBomNo());
                            opeWmsCombinStock.setCnName(opeProductionCombinBom.getCnName());
                            opeWmsCombinStock.setEnName(opeProductionCombinBom.getEnName());
                            opeWmsCombinStock.setFrName(opeProductionCombinBom.getFrName());
                        }
                        opeWmsCombinStock.setUsedStockQty(0);
                        opeWmsCombinStock.setAbleStockQty(0);
                        opeWmsCombinStock.setWaitInStockQty(0);
                        opeWmsCombinStock.setWaitOutStockQty(0);
                        opeWmsCombinStock.setCreatedTime(new Date());
                        opeWmsCombinStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsCombinStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsCombinStock.setUpdatedTime(new Date());
                        opeWmsCombinStockService.saveOrUpdate(opeWmsCombinStock);
                    }
                    stockId = opeWmsCombinStock.getId();
                }
                opeQcCombinB.setUpdatedBy(paramDTO.getUserId());
                opeQcCombinB.setUpdatedTime(new Date());
                qcCombinMapper.updateQcCombin(opeQcCombinB);

                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());
                remainingQty = opeQcCombinB.getQty() - (opeQcCombinB.getQualifiedQty() + opeQcCombinB.getUnqualifiedQty());
                qcId = opeQcCombinB.getQcId();
                break;
            default:
                OpeQcPartsB opeQcPartsB = qcPartsMapper.getQcPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                // 限制无码产品重复质检, 并且无码产品质检数量必须和质检数量一致
                if (StringUtils.isBlank(paramDTO.getSerialNum())) {
                    RpsAssert.isTrue(!qcQty.equals(opeQcPartsB.getQty()),ExceptionCodeEnums.QC_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.QC_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeQcPartsB.getUnqualifiedQty() > 0 || opeQcPartsB.getQualifiedQty() > 0,
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(), ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                }

                // 更新部件质检合格/不合格数量(部件存在无码质检这里要特殊处理)
                int qty = 0;
                if (qcResultFlag) {
                    qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? opeQcPartsB.getQualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setQualifiedQty(qty);
                } else {
                    qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? opeQcPartsB.getUnqualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setUnqualifiedQty(qty);
                    // 原料库部件id
                    OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(paramDTO.getBomId());
                    if (opeWmsPartsStock == null) {
                        opeWmsPartsStock = new OpeWmsPartsStock();
                        opeWmsPartsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                        opeWmsPartsStock.setStockType(1);
                        opeWmsPartsStock.setPartsId(paramDTO.getBomId());
                        // 从部件表去数据
                        OpeProductionParts opeProductionParts = opeProductionPartsService.getById(paramDTO.getBomId());
                        if (opeProductionParts != null) {
                            opeWmsPartsStock.setPartsType(opeProductionParts.getPartsType());
                            opeWmsPartsStock.setPartsNo(opeProductionParts.getPartsNo());
                            opeWmsPartsStock.setCnName(opeProductionParts.getCnName());
                            opeWmsPartsStock.setEnName(opeProductionParts.getEnName());
                            opeWmsPartsStock.setFrName(opeProductionParts.getFrName());
                        }
                        opeWmsPartsStock.setWaitInStockQty(0);
                        opeWmsPartsStock.setAbleStockQty(0);
                        opeWmsPartsStock.setUsedStockQty(0);
                        opeWmsPartsStock.setWaitOutStockQty(0);
                        opeWmsPartsStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsPartsStock.setCreatedTime(new Date());
                        opeWmsPartsStock.setUpdatedTime(new Date());
                        opeWmsPartsStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsPartsStockService.saveOrUpdate(opeWmsPartsStock);
                    }
                    stockId = opeWmsPartsStock.getId();
                }
                opeQcPartsB.setUpdatedBy(paramDTO.getUserId());
                opeQcPartsB.setUpdatedTime(new Date());
                qcPartsMapper.updateQcParts(opeQcPartsB);

                name = partsMapper.getPartsCnNameById(paramDTO.getBomId());
                remainingQty = opeQcPartsB.getQty() - (opeQcPartsB.getQualifiedQty() + opeQcPartsB.getUnqualifiedQty());
                qcId = opeQcPartsB.getQcId();
                break;
        }

        /**
         * 保存质检产品序列号信息
         */
        OpeQcOrderSerialBind opeQcOrderSerialBind = new OpeQcOrderSerialBind();
        Long serialId = idAppService.getId(SequenceName.OPE_QC_ORDER_SERIAL_BIND);
        opeQcOrderSerialBind.setId(serialId);
        opeQcOrderSerialBind.setOrderBId(paramDTO.getProductId());
        opeQcOrderSerialBind.setOrderType(paramDTO.getProductType());
        opeQcOrderSerialBind.setSerialNum(paramDTO.getSerialNum());
        opeQcOrderSerialBind.setTabletSn(paramDTO.getTabletSn());
        opeQcOrderSerialBind.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
        opeQcOrderSerialBind.setLot(paramDTO.getLot());
        opeQcOrderSerialBind.setProductId(paramDTO.getBomId());
        opeQcOrderSerialBind.setProductType(paramDTO.getProductType());
        opeQcOrderSerialBind.setQty(qcQty);
        opeQcOrderSerialBind.setCreatedBy(paramDTO.getUserId());
        opeQcOrderSerialBind.setCreatedTime(new Date());
        opeQcOrderSerialBind.setUpdatedBy(paramDTO.getUserId());
        opeQcOrderSerialBind.setUpdatedTime(new Date());

        /**
         * 保存库存产品序列号信息(质检失败时保存, 质检成功在入库单那边保存)
         */
        OpeWmsStockSerialNumber opeWmsStockSerialNumber = new OpeWmsStockSerialNumber();
        opeWmsStockSerialNumber.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
        opeWmsStockSerialNumber.setRelationId(stockId);
        opeWmsStockSerialNumber.setRelationType(paramDTO.getProductType());
        opeWmsStockSerialNumber.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());
        opeWmsStockSerialNumber.setRsn(paramDTO.getSerialNum());
        opeWmsStockSerialNumber.setStockStatus(WmsStockStatusEnum.AVAILABLE.getStatus());
        opeWmsStockSerialNumber.setLotNum(paramDTO.getLot());
        opeWmsStockSerialNumber.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
        opeWmsStockSerialNumber.setCreatedBy(paramDTO.getUserId());
        opeWmsStockSerialNumber.setCreatedTime(new Date());
        opeWmsStockSerialNumber.setUpdatedBy(paramDTO.getUserId());
        opeWmsStockSerialNumber.setUpdatedTime(new Date());

        // 当质检单为【生产采购单】产生时才需要打印二维码
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(qcId);
        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
            resultDTO.setPrintFlag(true);
            if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                opeQcOrderSerialBind.setDefaultSerialNum(paramDTO.getSerialNum());
                opeQcOrderSerialBind.setSerialNum("PARTS" + System.currentTimeMillis());
                // 部件自身序列号
                opeWmsStockSerialNumber.setSn(paramDTO.getSerialNum());
            }
        }
        qcOrderSerialBindMapper.insertQcOrderSerialBind(opeQcOrderSerialBind);
        if (!qcResultFlag) {
            wmsStockSerialNumberMapper.insertWmsStockSerialNumber(opeWmsStockSerialNumber);
        }

        /**
         * 保存产品质检记录
         */
        opeOrderQcItem.setOrderSerialId(serialId);
        opeOrderQcItem.setOrderType(opeQcOrder.getRelationOrderType());
        opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
        opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

        // 设置质检返回结果
        resultDTO.setQty(remainingQty);
        resultDTO.setName(name);
        resultDTO.setPartsNo(paramDTO.getPartsNo());
        resultDTO.setLot(paramDTO.getLot());
        resultDTO.setSerialNum(paramDTO.getSerialNum());
        resultDTO.setProductionDate(new Date());

        return resultDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(enter.getId());

        RpsAssert.isNull(opeQcOrder, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!QcStatusEnum.QUALITY_INSPECTION.getStatus().equals(opeQcOrder.getQcStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());

        opeQcOrder.setId(opeQcOrder.getId());
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION_COMPLETED.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

        /**
         * 完成质检修改关联单据状态
         */
        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
            opeProductionPurchaseOrder.setId(opeQcOrder.getRelationOrderId());
            opeProductionPurchaseOrder.setPurchaseStatus(NewProductionPurchasEnums.FINISHED.getValue());
            opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
            opeProductionPurchaseOrder.setUpdatedTime(new Date());
            opeProductionPurchaseOrderMapper.updateByPrimaryKeySelective(opeProductionPurchaseOrder);
        } else if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
            opeOutWhouseOrder.setId(opeQcOrder.getRelationOrderId());
            opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
            opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
            opeOutWhouseOrder.setUpdatedTime(new Date());
            opeOutWhouseOrderMapper.insertSelective(opeOutWhouseOrder);
        } else if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
            opeCombinOrder.setId(opeQcOrder.getRelationOrderId());
            opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.QC_FINISH.getValue());
            opeCombinOrder.setUpdatedBy(enter.getUserId());
            opeCombinOrder.setUpdatedTime(new Date());
            opeCombinOrderMapper.updateByPrimaryKeySelective(opeCombinOrder);
        }

        return new GeneralResult(enter.getRequestId());
    }

}
