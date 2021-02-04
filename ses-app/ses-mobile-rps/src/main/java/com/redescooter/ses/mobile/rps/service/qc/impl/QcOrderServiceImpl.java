package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.qc.QcStatusEnum;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.qc.QcTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.NewCombinOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.NewOutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.NewProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnum;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.*;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationOrderMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.*;
import com.redescooter.ses.mobile.rps.dao.wms.*;
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
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;
    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;
    @Autowired
    private CombinationOrderMapper combinationOrderMapper;
    @Autowired
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;
    @Autowired
    private OpeWmsQualifiedScooterStockService opeWmsQualifiedScooterStockService;
    @Autowired
    private OpeWmsQualifiedCombinStockService opeWmsQualifiedCombinStockService;
    @Autowired
    private OpeWmsQualifiedPartsStockService opeWmsQualifiedPartsStockService;
    @Autowired
    private OpeCombinOrderService opeCombinOrderService;
    @Autowired
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;
    @Autowired
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Autowired
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Autowired
    private WmsPartsStockMapper wmsPartsStockMapper;


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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(enter.getId());
        RpsAssert.isNull(opeQcOrder, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!QcStatusEnum.PENDING_QUALITY_INSPECTION.getStatus().equals(opeQcOrder.getQcStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());

        // 更新质检单状态为【质检中】
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

        /**
         * 开始质检修改组装单状态为“质检中”
         */
        if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeCombinOrder opeCombinOrder = opeCombinOrderService.getById(opeQcOrder.getRelationOrderId());
            RpsAssert.isNull(opeCombinOrder, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

            opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.INSPECTING.getValue());
            opeCombinOrder.setUpdatedBy(enter.getUserId());
            opeCombinOrder.setUpdatedTime(new Date());
            opeCombinOrderService.updateById(opeCombinOrder);
        }

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
                RpsAssert.isNull(opeProductionCombinBom, ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getCode(),
                        ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getMessage());

                resultDTO.setName(opeProductionCombinBom.getCnName());

                productType = QcTemplateProductTypeEnum.COMBINATION.getType();
                break;
            default:
                OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByBomId(paramDTO.getBomId());
                RpsAssert.isNull(opeProductionParts, ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getCode(),
                        ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getMessage());

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

        // 无码产品不填写扫码数量时抛出异常
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQcQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());
        /**
         * 公共参数
         */
        Integer qcQty = paramDTO.getIdClass() ? 1 : paramDTO.getQcQty();
        Integer remainingQty = 0;
        String name = null;
        boolean qcResultFlag = true;
        Integer stockRelationType = null;
        Integer inWhType = null;

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
        RpsAssert.isEmpty(productQcTemplateList, ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());

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

        // 避免重复质检, 这里只对车辆、组装件做重复扫码校验,部件的在下面处理,因为部件会比较特殊
        if (paramDTO.getIdClass() && !ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType())) {
            int count = qcOrderSerialBindMapper.isExistsSerialNum(paramDTO.getSerialNum(), paramDTO.getProductId(), 1);
            RpsAssert.isTrue(count > 0, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
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
        boolean qcResultIdIsExists = false;
        for (QcProductResultDTO qc : qcProductResultList) {
            // 组装质检记录数据
            OpeOrderQcTrace opeOrderQcTrace = new OpeOrderQcTrace();
            opeOrderQcTrace.setId(idAppService.getId(SequenceName.OPE_ORDER_QC_TRACE));
            opeOrderQcTrace.setDr(0);
            opeOrderQcTrace.setProductQcTemplateBId(qc.getTemplateResultId());
            opeOrderQcTrace.setProductQcTemplateId(qc.getTemplateId());
            ProductQcTemplateDTO productQcTemplateDTO = qcTemplateMap.get(qc.getTemplateId());
            // 防止接口参数传递有误导致npe
            RpsAssert.isNull(productQcTemplateDTO, ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
            opeOrderQcTrace.setProductQcTemplateName(productQcTemplateDTO.getQcItemName());
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
                    qcResultIdIsExists = true;
                    opeOrderQcTrace.setProductQcTemplateBName(result.getQcResult());
                    if (!result.getPassFlag()) {
                        qcResultFlag = false;
                    }
                }
            }
            // 质检模板结果不存在
            RpsAssert.isFalse(qcResultIdIsExists, ExceptionCodeEnums.QC_TEMPLATE_RESULT_IS_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_RESULT_IS_NOT_EXISTS.getMessage());
            // 重新调整为false,保证下一次遍历没有问题
            qcResultIdIsExists = false;

            opeOrderQcTraceList.add(opeOrderQcTrace);
        }

        // 质检结果 pass/ng
        opeOrderQcItem.setQcResult(qcResultFlag ? 1 : 2);

        /**
         * 产品扫码质检 1车辆 2组装件 3部件
         */
        Long qcId = null;
        Long stockId = null;
        OpeQcOrder opeQcOrder = null;
        switch (paramDTO.getProductType()) {
            case 1:
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeQcScooterB opeQcScooterB = qcScooterMapper.getQcScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcScooterB.getQcId());
                // 质检成功合格数量+1,失败不合格数量+1
                if (qcResultFlag) {
                    opeQcScooterB.setQualifiedQty(opeQcScooterB.getQualifiedQty() + 1);
                } else {
                    opeQcScooterB.setUnqualifiedQty(opeQcScooterB.getUnqualifiedQty() + 1);
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                    RpsAssert.isNull(scooterBom, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                    /**
                     * 第一次入库仓库中是没有这辆车的,需要把这辆车添加到不合格品库里面去
                     */
                    QueryWrapper<OpeWmsQualifiedScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_DR, "0");
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID, scooterBom.getGroupId());
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_COLOR_ID, scooterBom.getColorId());

                    OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = opeWmsQualifiedScooterStockService.getOne(scooterStockQueryWrapper);
                    if(null == opeWmsQualifiedScooterStock){
                        opeWmsQualifiedScooterStock = new OpeWmsQualifiedScooterStock();
                        opeWmsQualifiedScooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_QUALIFIED_SCOOTER_STOCK));
                        opeWmsQualifiedScooterStock.setDr(0);
                        opeWmsQualifiedScooterStock.setGroupId(scooterBom.getGroupId());
                        opeWmsQualifiedScooterStock.setColorId(scooterBom.getColorId());
                        opeWmsQualifiedScooterStock.setQty(qcQty);
                        opeWmsQualifiedScooterStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsQualifiedScooterStock.setCreatedTime(new Date());
                    } else {
                        opeWmsQualifiedScooterStock.setQty(opeWmsQualifiedScooterStock.getQty() + qcQty);
                    }
                    opeWmsQualifiedScooterStock.setUpdatedBy(paramDTO.getUserId());
                    opeWmsQualifiedScooterStock.setUpdatedTime(new Date());

                    opeWmsQualifiedScooterStockService.saveOrUpdate(opeWmsQualifiedScooterStock);
                    // 仓库相关参数设置
                    stockId = opeWmsQualifiedScooterStock.getId();
                    stockRelationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                    /**
                     * 质检失败产品到不合格品库,同时成品库可用数量同时也需要扣减(只针对于调拨发货流程)
                     */
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper
                                .getWmsScooterStockByGroupIdAndColorId(opeQcScooterB.getGroupId(), opeQcScooterB.getColorId());
                        // 这种情况应该是不会有的,出库质检说明库存中肯定有该产品信息
                        RpsAssert.isNull(opeWmsScooterStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() - qcQty);
                        // 待出库同时也要扣减,因为此时相当于是从成品库出到不合格品库里面去了
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - qcQty);
                        opeWmsScooterStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsScooterStock.setUpdatedTime(new Date());
                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);

                        // 把产品从仓库详情中删除(有码产品)
                        if (paramDTO.getIdClass()) {
                            wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
                        }
                    }

                }
                // 更新车辆质检合格/不合格数量
                opeQcScooterB.setUpdatedBy(paramDTO.getUserId());
                opeQcScooterB.setUpdatedTime(new Date());
                qcScooterMapper.updateQcScooter(opeQcScooterB);

                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                remainingQty = opeQcScooterB.getQty() - (opeQcScooterB.getQualifiedQty() + opeQcScooterB.getUnqualifiedQty());
                qcId = opeQcScooterB.getQcId();
                break;
            case 2:
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeQcCombinB opeQcCombinB = qcCombinMapper.getQcCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcCombinB.getQcId());
                // 更新组装件质检合格/不合格数量
                if (qcResultFlag) {
                    opeQcCombinB.setQualifiedQty(opeQcCombinB.getQualifiedQty() + 1);
                } else {
                    opeQcCombinB.setUnqualifiedQty(opeQcCombinB.getUnqualifiedQty() + 1);

                    /**
                     * 第一次入库仓库中是没有这个组装件的,需要把这个组装件添加到不合格品库里面去
                     */
                    QueryWrapper<OpeWmsQualifiedCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                    combinStockQueryWrapper.eq(OpeWmsQualifiedCombinStock.COL_DR, "0");
                    combinStockQueryWrapper.eq(OpeWmsQualifiedCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, paramDTO.getBomId());

                    OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock = opeWmsQualifiedCombinStockService.getOne(combinStockQueryWrapper);
                    if (null == opeWmsQualifiedCombinStock) {
                        opeWmsQualifiedCombinStock = new OpeWmsQualifiedCombinStock();
                        opeWmsQualifiedCombinStock.setId(idAppService.getId(SequenceName.OPE_WMS_QUALIFIED_COMBIN_STOCK));
                        opeWmsQualifiedCombinStock.setDr(0);

                        OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(paramDTO.getBomId());
                        RpsAssert.isNull(opeProductionCombinBom, ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getCode(),
                                ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getMessage());
                        opeWmsQualifiedCombinStock.setProductionCombinBomId(paramDTO.getBomId());
                        opeWmsQualifiedCombinStock.setCombinNo(opeProductionCombinBom.getBomNo());
                        opeWmsQualifiedCombinStock.setCnName(opeProductionCombinBom.getCnName());
                        opeWmsQualifiedCombinStock.setEnName(opeProductionCombinBom.getEnName());
                        opeWmsQualifiedCombinStock.setFrName(opeProductionCombinBom.getFrName());
                        opeWmsQualifiedCombinStock.setQty(qcQty);
                        opeWmsQualifiedCombinStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsQualifiedCombinStock.setCreatedTime(new Date());
                    } else {
                        opeWmsQualifiedCombinStock.setQty(opeWmsQualifiedCombinStock.getQty() + qcQty);
                    }
                    opeWmsQualifiedCombinStock.setUpdatedBy(paramDTO.getUserId());
                    opeWmsQualifiedCombinStock.setUpdatedTime(new Date());

                    opeWmsQualifiedCombinStockService.saveOrUpdate(opeWmsQualifiedCombinStock);
                    // 仓库相关参数设置
                    stockId = opeWmsQualifiedCombinStock.getId();
                    stockRelationType = WmsTypeEnum.COMBINATION_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                    /**
                     * 质检失败产品到不合格品库,同时成品库可用数量同时也需要扣减(只针对于调拨发货流程)
                     */
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(opeQcCombinB.getProductionCombinBomId());
                        // 这种情况应该是不会有的,出库质检说明库存中肯定有该产品信息
                        RpsAssert.isNull(opeWmsCombinStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() - qcQty);
                        // 待出库同时也要扣减,因为此时相当于是从成品库出到不合格品库里面去了
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - qcQty);
                        opeWmsCombinStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsCombinStock.setUpdatedTime(new Date());
                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);

                        // 把产品从仓库详情中删除(有码产品)
                        if (paramDTO.getIdClass()) {
                            wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
                        }
                    }
                }
                opeQcCombinB.setUpdatedBy(paramDTO.getUserId());
                opeQcCombinB.setUpdatedTime(new Date());
                qcCombinMapper.updateQcCombin(opeQcCombinB);

                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());
                remainingQty = opeQcCombinB.getQty() - (opeQcCombinB.getQualifiedQty() + opeQcCombinB.getUnqualifiedQty());
                qcId = opeQcCombinB.getQcId();
                break;
            default:
                RpsAssert.isBlank(paramDTO.getPartsNo(), ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getMessage());
                // 校验部件是否有序列号标识跟入参传递的是否一致
                Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
                RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                boolean flag = 0 == idClass ? false : true;
                RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());
                // 无码产品不填写质检数量时抛出异常
                RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQcQty(),
                        ExceptionCodeEnums.QC_QTY_ERROR.getCode(), ExceptionCodeEnums.QC_QTY_ERROR.getMessage());

                OpeQcPartsB opeQcPartsB = qcPartsMapper.getQcPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                // ECU仪表必须要传递蓝牙mac地址
                RpsAssert.isTrue(BomCommonTypeEnums.ECU_METER.getValue().equals(opeQcPartsB.getPartsType()) && StringUtils.isBlank(paramDTO.getBluetoothMacAddress()),
                        ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getCode(), ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcPartsB.getQcId());
                // 限制无码产品重复质检, 并且无码产品质检数量必须和质检数量一致
                if (!paramDTO.getIdClass()) {
                    RpsAssert.isTrue(!qcQty.equals(opeQcPartsB.getQty()),ExceptionCodeEnums.QC_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.QC_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeQcPartsB.getUnqualifiedQty() > 0 || opeQcPartsB.getQualifiedQty() > 0,
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(), ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                } else {
                    /**
                     * 如果是质检单是由“工厂采购单”所生成对于重复扫码校验的逻辑就要修改, 因为工厂采购生成的质检单扫码时,扫的码是部件本身的,
                     * 其它例如,组装、出库生成的质检单扫码时，扫的是后台生成的码, 这里比较特殊所以需要这样处理
                     */
                    if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
                        int count = qcOrderSerialBindMapper.isExistsSerialNum(paramDTO.getSerialNum(), paramDTO.getProductId(),
                                OrderTypeEnums.FACTORY_PURCHAS.getValue());
                        RpsAssert.isTrue(count > 0, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                                ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                    }
                }

                // 更新部件质检合格/不合格数量(部件存在无码质检这里要特殊处理)
                int qty = 0;
                if (qcResultFlag) {
                    qty = paramDTO.getIdClass() ? opeQcPartsB.getQualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setQualifiedQty(qty);
                } else {
                    qty = paramDTO.getIdClass() ? opeQcPartsB.getUnqualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setUnqualifiedQty(qty);

                    /**
                     * 第一次入库仓库中是没有这个部件的,需要把这个部件添加到不合格品库里面去
                     */
                    QueryWrapper<OpeWmsQualifiedPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                    partsStockQueryWrapper.eq(OpeWmsQualifiedPartsStock.COL_DR, "0");
                    partsStockQueryWrapper.eq(OpeWmsQualifiedPartsStock.COL_PARTS_ID, paramDTO.getBomId());
                    OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock = opeWmsQualifiedPartsStockService.getOne(partsStockQueryWrapper);
                    if (null == opeWmsQualifiedPartsStock) {
                        opeWmsQualifiedPartsStock = new OpeWmsQualifiedPartsStock();
                        opeWmsQualifiedPartsStock.setId(idAppService.getId(SequenceName.OPE_WMS_QUALIFIED_PARTS_STOCK));
                        opeWmsQualifiedPartsStock.setDr(0);
                        opeWmsQualifiedPartsStock.setPartsId(paramDTO.getBomId());

                        OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByBomId(paramDTO.getBomId());
                        RpsAssert.isNull(opeProductionParts, ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getCode(),
                                ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getMessage());
                        opeWmsQualifiedPartsStock.setPartsNo(opeProductionParts.getPartsNo());
                        opeWmsQualifiedPartsStock.setPartsType(opeProductionParts.getPartsType());
                        opeWmsQualifiedPartsStock.setCnName(opeProductionParts.getCnName());
                        opeWmsQualifiedPartsStock.setEnName(opeProductionParts.getEnName());
                        opeWmsQualifiedPartsStock.setFrName(opeProductionParts.getFrName());
                        opeWmsQualifiedPartsStock.setQty(qcQty);
                        opeWmsQualifiedPartsStock.setCreatedBy(paramDTO.getUserId());
                        opeWmsQualifiedPartsStock.setCreatedTime(new Date());
                    } else {
                        opeWmsQualifiedPartsStock.setQty(opeWmsQualifiedPartsStock.getQty() + qcQty);
                    }
                    opeWmsQualifiedPartsStock.setUpdatedBy(paramDTO.getUserId());
                    opeWmsQualifiedPartsStock.setUpdatedTime(new Date());

                    opeWmsQualifiedPartsStockService.saveOrUpdate(opeWmsQualifiedPartsStock);
                    // 仓库相关参数设置
                    stockId = opeWmsQualifiedPartsStock.getId();
                    stockRelationType = WmsTypeEnum.PARTS_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue();

                    /**
                     * 质检失败产品到不合格品库,同时成品库可用数量同时也需要扣减(只针对于调拨发货流程)
                     */
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(opeQcPartsB.getPartsId());
                        // 这种情况应该是不会有的,出库质检说明库存中肯定有该产品信息
                        RpsAssert.isNull(opeWmsPartsStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() - qcQty);
                        // 待出库同时也要扣减,因为此时相当于是从成品库出到不合格品库里面去了
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - qcQty);
                        opeWmsPartsStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsPartsStock.setUpdatedTime(new Date());
                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);

                        // 把产品从仓库详情中删除(有码产品)
                        if (paramDTO.getIdClass()) {
                            wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
                        }
                    }
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
        String serialNum = "PARTS" + System.currentTimeMillis();
        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
            resultDTO.setPrintFlag(true);
            opeQcOrderSerialBind.setSerialNum(serialNum);
            if (paramDTO.getIdClass()) {
                opeQcOrderSerialBind.setDefaultSerialNum(paramDTO.getSerialNum());
                // 部件自身序列号
                opeWmsStockSerialNumber.setSn(paramDTO.getSerialNum());
            }
        }

        qcOrderSerialBindMapper.insertQcOrderSerialBind(opeQcOrderSerialBind);
        // 质检失败保存库存产品序列号信息,成功在确认入库那边保存
        if (!qcResultFlag) {
            wmsStockSerialNumberMapper.insertWmsStockSerialNumber(opeWmsStockSerialNumber);
            // 保存不合格品库入库记录
            OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
            opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            opeWmsStockRecord.setDr(0);
            opeWmsStockRecord.setRelationId(stockId);
            opeWmsStockRecord.setRelationType(stockRelationType);
            opeWmsStockRecord.setInWhType(inWhType);
            opeWmsStockRecord.setInWhQty(qcQty);
            opeWmsStockRecord.setRecordType(Integer.valueOf(InOutWhEnums.IN.getValue()));
            opeWmsStockRecord.setStockType(1); // 默认中国仓库
            opeWmsStockRecord.setCreatedBy(paramDTO.getUserId());
            opeWmsStockRecord.setCreatedTime(new Date());
            opeWmsStockRecord.setUpdatedBy(paramDTO.getUserId());
            opeWmsStockRecord.setUpdatedTime(new Date());
            opeWmsStockRecordMapper.insertOrUpdateSelective(opeWmsStockRecord);
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
        resultDTO.setSerialNum(ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType()) ? serialNum : paramDTO.getSerialNum());
        resultDTO.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
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

        /**
         * 完成质检时检查产品是否全部质检完 1车辆 2组装件 3部件
         */
        List<QcOrderProductDTO> productList = null;
        switch (opeQcOrder.getOrderType()) {
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
        // 检查产品是否质检完成
        checkQcQty(productList);

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
            opeProductionPurchaseOrderService.updateById(opeProductionPurchaseOrder);
        } else if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
            opeOutWhouseOrder.setId(opeQcOrder.getRelationOrderId());
            opeOutWhouseOrder.setOutWhStatus(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue());
            opeOutWhouseOrder.setUpdatedBy(enter.getUserId());
            opeOutWhouseOrder.setUpdatedTime(new Date());
            opeOutWhouseOrderService.updateById(opeOutWhouseOrder);
        } else if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeQcOrder.getRelationOrderType())) {
            OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
            opeCombinOrder.setId(opeQcOrder.getRelationOrderId());
            opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.QC_FINISH.getValue());
            opeCombinOrder.setUpdatedBy(enter.getUserId());
            opeCombinOrder.setUpdatedTime(new Date());
            combinationOrderMapper.updateCombinationOrder(opeCombinOrder);
        }

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 检查产品是否质检完成
     * @param productList
     */
    private void checkQcQty(List<QcOrderProductDTO> productList) {
        productList.forEach(product -> {
            RpsAssert.isTrue((product.getQualifiedQty() + product.getUnqualifiedQty()) < product.getQty(),
                    ExceptionCodeEnums.NOT_COMPLETED_QC.getCode(), ExceptionCodeEnums.NOT_COMPLETED_QC.getMessage());
        });
    }

}
