package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
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
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcItemMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcTraceMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationOrderMapper;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private OpeProductionPurchasePartsBService opeProductionPurchasePartsBService;

    @Autowired
    private OpeCombinOrderScooterBService opeCombinOrderScooterBService;

    @Autowired
    private OpeCombinOrderCombinBService opeCombinOrderCombinBService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;



    @Override
    public Map<Integer, Integer> getQcOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = qcOrderMapper.getQcOrderTypeCount();
        //TODO: {orderType, totalCount}
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
        // ??????status???,?????????????????????????????????????????????
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1 : 0);
        List<CountByStatusResult> countByStatusResultList = qcOrderMapper.getQcTypeCount(paramDTO);
         // {qcType, totalCount}
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
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
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1 : 0);
        int count = qcOrderMapper.countByQcOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }
        return PageResult.create(paramDTO, count, qcOrderMapper.getQcOrderList(paramDTO));
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(enter.getId());
        RpsAssert.isNull(opeQcOrder, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!QcStatusEnum.PENDING_QUALITY_INSPECTION.getStatus().equals(opeQcOrder.getQcStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());

        // ???????????????????????????????????????
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

         // ???????????????????????????????????????????????????
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

         // ??????????????????????????? 1?????? 2????????? 3??????
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

         // (???????????? + ???????????????) < ???????????? ---- ?????????
        List<QcOrderProductDTO> pendingQcProductList = productList.stream().filter(
                p -> (p.getQualifiedQty() + p.getUnqualifiedQty()) < p.getQty()
        ).collect(Collectors.toList());

         // ???????????? > 0 ---- ????????????
        List<QcOrderProductDTO> qcSuccessProductList = productList.stream().filter(
                p -> p.getQualifiedQty() > 0
        ).collect(Collectors.toList());

         // ??????????????? > 0 ---- ????????????
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

         // ??????????????????????????? 1?????? 2????????? 3??????
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
         // ????????????productType????????????????????????????????????
        Integer productType;
        switch (paramDTO.getProductType()) {
            case 1:
                String scooterModel = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                resultDTO.setName(scooterModel);

                productType = QcTemplateProductTypeEnum.SCOOTER.getType();
                break;
            case 2:
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

         // {productQualityTempateId, List<ProductQcTemplateResultDTO>}
        Map<Long, List<ProductQcTemplateResultDTO>> templateBMap = qcResultList.stream().collect(
                Collectors.groupingBy(ProductQcTemplateResultDTO::getTemplateId)
        );

        productQcTemplateList.forEach(template -> {
            template.setQcTemplateResultList(templateBMap.get(template.getId()));
        });

        resultDTO.setProductQcTemplateList(productQcTemplateList);
        return resultDTO;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public SaveScanCodeResultDTO saveQcResult(SaveQcResultParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        resultDTO.setPrintFlag(false);

        // ????????????????????????????????????????????????
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQcQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());
         // ????????????
        Integer qcQty = paramDTO.getIdClass() ? 1 : paramDTO.getQcQty();
        Integer remainingQty = 0;
        String name = null;
        boolean qcResultFlag = true;
        Integer stockRelationType = null;
        Integer inWhType = null;

        // ????????????????????????
        List<OpeOrderQcTrace> opeOrderQcTraceList = new ArrayList<>();

        List<QcProductResultDTO> qcProductResultList = null;
        try {
            qcProductResultList = JSONArray.parseArray(paramDTO.getSt(), QcProductResultDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

         // ????????????????????????, ??????????????????????????????????????????
        QueryQcTemplateParamDTO queryParam = new QueryQcTemplateParamDTO();
        queryParam.setBomId(paramDTO.getBomId());
        queryParam.setProductType(paramDTO.getProductType());

        QueryQcTemplateResultDTO qcTemplateResult = getQcTemplateByIdAndType(queryParam);
        List<ProductQcTemplateDTO> productQcTemplateList = qcTemplateResult.getProductQcTemplateList();

        RpsAssert.isEmpty(productQcTemplateList, ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());

         // {templateId, List<ProductQcTemplateResultDTO>}
        Map<Long, List<ProductQcTemplateResultDTO>> qcResultMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t.getQcTemplateResultList())
        );

         // {templateId, ProductQcTemplateDTO}
        Map<Long, ProductQcTemplateDTO> qcTemplateMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t)
        );

        // ??????????????????, ???????????????????????????????????????????????????,????????????????????????,???????????????????????????
        if (paramDTO.getIdClass() && !ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType())) {
            int count = qcOrderSerialBindMapper.isExistsSerialNum(paramDTO.getSerialNum(), paramDTO.getProductId(), 1);
            RpsAssert.isTrue(count > 0, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        // ????????????
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

         // ??????????????????????????????
        boolean qcResultIdIsExists = false;
        for (QcProductResultDTO qc : qcProductResultList) {
            // ????????????????????????
            OpeOrderQcTrace opeOrderQcTrace = new OpeOrderQcTrace();
            opeOrderQcTrace.setId(idAppService.getId(SequenceName.OPE_ORDER_QC_TRACE));
            opeOrderQcTrace.setDr(0);
            opeOrderQcTrace.setProductQcTemplateBId(qc.getTemplateResultId());
            opeOrderQcTrace.setProductQcTemplateId(qc.getTemplateId());
            ProductQcTemplateDTO productQcTemplateDTO = qcTemplateMap.get(qc.getTemplateId());
            // ????????????????????????????????????npe
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

            // ??????????????????
            for (ProductQcTemplateResultDTO result : qcResultMap.get(qc.getTemplateId())) {
                if (qc.getTemplateResultId().equals(result.getId())) {
                    qcResultIdIsExists = true;
                    opeOrderQcTrace.setProductQcTemplateBName(result.getQcResult());
                    if (!result.getPassFlag()) {
                        qcResultFlag = false;
                    }
                }
            }
            // ???????????????????????????
            RpsAssert.isFalse(qcResultIdIsExists, ExceptionCodeEnums.QC_TEMPLATE_RESULT_IS_NOT_EXISTS.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_RESULT_IS_NOT_EXISTS.getMessage());
            // ???????????????false,?????????????????????????????????
            qcResultIdIsExists = false;

            opeOrderQcTraceList.add(opeOrderQcTrace);
        }

        // ???????????? pass/ng
        opeOrderQcItem.setQcResult(qcResultFlag ? 1 : 2);

         // ?????????????????? 1?????? 2????????? 3??????
        Long qcId = null;
        Long stockId = null;
        OpeQcOrder opeQcOrder = null;
        switch (paramDTO.getProductType()) {
            case 1:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeQcScooterB opeQcScooterB = qcScooterMapper.getQcScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcScooterB.getQcId());
                // ????????????????????????+1,?????????????????????+1
                if (qcResultFlag) {
                    opeQcScooterB.setQualifiedQty(opeQcScooterB.getQualifiedQty() + 1);
                    // ?????????????????? ????????????????????????????????????????????????????????????????????????(??????????????????????????? ????????????????????????????????????)
                    changeCombinScooter(opeQcOrder.getRelationOrderId(),opeQcScooterB.getScooterBomId());
                } else {
                    opeQcScooterB.setUnqualifiedQty(opeQcScooterB.getUnqualifiedQty() + 1);
                    OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                    RpsAssert.isNull(scooterBom, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                     // ?????????????????????????????????????????????,???????????????????????????????????????????????????
                    QueryWrapper<OpeWmsQualifiedScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_DR, "0");
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID, scooterBom.getGroupId());
                    scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_COLOR_ID, scooterBom.getColorId());

                    OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock = opeWmsQualifiedScooterStockService.getOne(scooterStockQueryWrapper);
                    if (null == opeWmsQualifiedScooterStock) {
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
                    // ????????????????????????
                    stockId = opeWmsQualifiedScooterStock.getId();
                    stockRelationType = WmsTypeEnum.SCOOTER_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                     // ????????????????????????????????????,????????????????????????????????????????????????(??????????????????????????????)
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper
                                .getWmsScooterStockByGroupIdAndColorId(opeQcScooterB.getGroupId(), opeQcScooterB.getColorId());
                        // ?????????????????????????????????,???????????????????????????????????????????????????
                        RpsAssert.isNull(opeWmsScooterStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() - qcQty);
                        // ???????????????????????????,?????????????????????????????????????????????????????????????????????
                        opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - qcQty);
                        opeWmsScooterStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsScooterStock.setUpdatedTime(new Date());
                        wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);

                        // ?????????????????????????????????(????????????)(??????????????????????????????)
                        if (paramDTO.getIdClass()) {
                            OpeWmsStockSerialNumber model = new OpeWmsStockSerialNumber();
                            model.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                            model.setRsn(paramDTO.getSerialNum());
                            model.setUpdatedBy(paramDTO.getUserId());
                            model.setUpdatedTime(new Date());
                            wmsStockSerialNumberMapper.updateWmsStockSerialNumberByRSn(model);
                            //wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
                        }
                    }

                }
                // ????????????????????????/???????????????
                opeQcScooterB.setUpdatedBy(paramDTO.getUserId());
                opeQcScooterB.setUpdatedTime(new Date());
                qcScooterMapper.updateQcScooter(opeQcScooterB);

                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                remainingQty = opeQcScooterB.getQty() - (opeQcScooterB.getQualifiedQty() + opeQcScooterB.getUnqualifiedQty());
                qcId = opeQcScooterB.getQcId();
                break;
            case 2:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeQcCombinB opeQcCombinB = qcCombinMapper.getQcCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcCombinB.getQcId());
                // ???????????????????????????/???????????????
                if (qcResultFlag) {
                    opeQcCombinB.setQualifiedQty(opeQcCombinB.getQualifiedQty() + 1);
                    // ?????????????????? ????????????????????????????????????????????????????????????????????????(??????????????????????????? ????????????????????????????????????)
                    changeCombinCombin(opeQcOrder.getRelationOrderId(),opeQcCombinB.getProductionCombinBomId());
                } else {
                    opeQcCombinB.setUnqualifiedQty(opeQcCombinB.getUnqualifiedQty() + 1);

                     // ???????????????????????????????????????????????????,?????????????????????????????????????????????????????????
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
                    // ????????????????????????
                    stockId = opeWmsQualifiedCombinStock.getId();
                    stockRelationType = WmsTypeEnum.COMBINATION_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                     // ????????????????????????????????????,????????????????????????????????????????????????(??????????????????????????????)
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(opeQcCombinB.getProductionCombinBomId());
                        // ?????????????????????????????????,???????????????????????????????????????????????????
                        RpsAssert.isNull(opeWmsCombinStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() - qcQty);
                        // ???????????????????????????,?????????????????????????????????????????????????????????????????????
                        opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - qcQty);
                        opeWmsCombinStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsCombinStock.setUpdatedTime(new Date());
                        wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);

                        // ?????????????????????????????????(????????????)(??????????????????????????????)
                        if (paramDTO.getIdClass()) {
                            OpeWmsStockSerialNumber model = new OpeWmsStockSerialNumber();
                            model.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                            model.setRsn(paramDTO.getSerialNum());
                            model.setUpdatedBy(paramDTO.getUserId());
                            model.setUpdatedTime(new Date());
                            wmsStockSerialNumberMapper.updateWmsStockSerialNumberByRSn(model);
                            //wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
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
                // ??????????????????????????????????????????????????????????????????
                Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
                RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                boolean flag = 0 != idClass;
                RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());
                // ????????????????????????????????????????????????
                RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQcQty(),
                        ExceptionCodeEnums.QC_QTY_ERROR.getCode(), ExceptionCodeEnums.QC_QTY_ERROR.getMessage());

                OpeQcPartsB opeQcPartsB = qcPartsMapper.getQcPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                // ECU???????????????????????????mac??????
                RpsAssert.isTrue(BomCommonTypeEnums.ECU_METER.getValue().equals(String.valueOf(opeQcPartsB.getPartsType())) && StringUtils.isBlank(paramDTO.getBluetoothMacAddress()),
                        ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getCode(), ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getMessage());

                opeQcOrder = qcOrderMapper.getQcOrderById(opeQcPartsB.getQcId());
                // ??????????????????????????????, ?????????????????????????????????????????????????????????
                if (!paramDTO.getIdClass()) {
                    RpsAssert.isTrue(!qcQty.equals(opeQcPartsB.getQty()), ExceptionCodeEnums.QC_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.QC_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeQcPartsB.getUnqualifiedQty() > 0 || opeQcPartsB.getQualifiedQty() > 0,
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(), ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                } else {
                     // ???????????????????????????????????????????????????????????????????????????????????????????????????, ?????????????????????????????????????????????,???????????????????????????,
                     // ????????????,????????????????????????????????????????????????????????????????????????, ??????????????????????????????????????????
                    if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
                        int count = qcOrderSerialBindMapper.isExistsSerialNum(paramDTO.getSerialNum(), paramDTO.getProductId(),
                                OrderTypeEnums.FACTORY_PURCHAS.getValue());
                        RpsAssert.isTrue(count > 0, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                                ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                    }
                }

                // ????????????????????????/???????????????(?????????????????????????????????????????????)
                int qty = 0;
                if (qcResultFlag) {
                    qty = paramDTO.getIdClass() ? opeQcPartsB.getQualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setQualifiedQty(qty);
                    // ?????????????????? ????????????????????????????????????????????????????????????????????????(??????????????????????????? ????????????????????????????????????)
                    changePurchaseParts(opeQcOrder.getRelationOrderId(),opeQcPartsB.getPartsId(),paramDTO.getIdClass()?1:qcQty);
                } else {
                    qty = paramDTO.getIdClass() ? opeQcPartsB.getUnqualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setUnqualifiedQty(qty);

                    // ??????????????????<???????????????+????????????,????????????????????????,????????????
                    Integer totalQty = opeQcPartsB.getQty();
                    Integer unqualifiedQty = opeQcPartsB.getUnqualifiedQty();
                    Integer qualifiedQty = opeQcPartsB.getQualifiedQty();
                    Integer sumQty = unqualifiedQty + qualifiedQty;
                    if (totalQty.compareTo(sumQty) < 0) {
                        throw new SesMobileRpsException(ExceptionCodeEnums.QC_QTY_ERROR.getCode(), ExceptionCodeEnums.QC_QTY_ERROR.getMessage());
                    }

                    // ????????????????????????????????????????????????,??????????????????????????????????????????????????????
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
                    // ????????????????????????
                    stockId = opeWmsQualifiedPartsStock.getId();
                    stockRelationType = WmsTypeEnum.PARTS_UNQUALIFIED_WAREHOUSE.getType();
                    inWhType = InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue();

                     // ????????????????????????????????????,????????????????????????????????????????????????(??????????????????????????????)
                    if (OrderTypeEnums.OUTBOUND.getValue().equals(opeQcOrder.getRelationOrderType())
                            && QcTypeEnum.SHIP.getType().equals(opeQcOrder.getQcType())) {
                        OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(opeQcPartsB.getPartsId());
                        // ?????????????????????????????????,???????????????????????????????????????????????????
                        RpsAssert.isNull(opeWmsPartsStock, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                        opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() - qcQty);
                        // ???????????????????????????,?????????????????????????????????????????????????????????????????????
                        opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - qcQty);
                        opeWmsPartsStock.setUpdatedBy(paramDTO.getUserId());
                        opeWmsPartsStock.setUpdatedTime(new Date());
                        wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);

                        // ?????????????????????????????????(????????????)(??????????????????????????????)
                        if (paramDTO.getIdClass()) {
                            OpeWmsStockSerialNumber model = new OpeWmsStockSerialNumber();
                            model.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                            model.setRsn(paramDTO.getSerialNum());
                            model.setUpdatedBy(paramDTO.getUserId());
                            model.setUpdatedTime(new Date());
                            wmsStockSerialNumberMapper.updateWmsStockSerialNumberByRSn(model);
                            //wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(Arrays.asList(paramDTO.getSerialNum()));
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

         // ?????????????????????????????????
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

         // ?????????????????????????????????(?????????????????????, ????????????????????????????????????)
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

        // ?????????????????????????????????????????????????????????????????????
        String serialNum = "PARTS" + System.currentTimeMillis();
        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
            resultDTO.setPrintFlag(true);
            opeQcOrderSerialBind.setSerialNum(serialNum);
            if (paramDTO.getIdClass()) {
                opeQcOrderSerialBind.setDefaultSerialNum(paramDTO.getSerialNum());
                // ?????????????????????
                opeWmsStockSerialNumber.setSn(paramDTO.getSerialNum());
            }
        }

        qcOrderSerialBindMapper.insertQcOrderSerialBind(opeQcOrderSerialBind);
        // ?????????????????????????????????????????????,?????????????????????????????????
        if (!qcResultFlag) {
            wmsStockSerialNumberMapper.insertWmsStockSerialNumber(opeWmsStockSerialNumber);
            // ?????????????????????????????????
            OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
            opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            opeWmsStockRecord.setDr(0);
            opeWmsStockRecord.setRelationId(stockId);
            opeWmsStockRecord.setRelationType(stockRelationType);
            opeWmsStockRecord.setInWhType(inWhType);
            opeWmsStockRecord.setInWhQty(qcQty);
            opeWmsStockRecord.setRecordType(Integer.valueOf(InOutWhEnums.IN.getValue()));
            opeWmsStockRecord.setStockType(1); // ??????????????????
            opeWmsStockRecord.setCreatedBy(paramDTO.getUserId());
            opeWmsStockRecord.setCreatedTime(new Date());
            opeWmsStockRecord.setUpdatedBy(paramDTO.getUserId());
            opeWmsStockRecord.setUpdatedTime(new Date());
            opeWmsStockRecordMapper.insertOrUpdateSelective(opeWmsStockRecord);

            // rsn???????????????,??????????????????
            LambdaQueryWrapper<OpeCodebaseRsn> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
            lqw.eq(OpeCodebaseRsn::getRsn, paramDTO.getSerialNum());
            lqw.eq(OpeCodebaseRsn::getStatus, 1);
            lqw.last("limit 1");
            OpeCodebaseRsn rsn = opeCodebaseRsnService.getOne(lqw);
            if (null == rsn) {
                log.info("rsn???????????????,?????????????????????????????????rsn");
                OpeCodebaseRsn rsnModel = new OpeCodebaseRsn();
                rsnModel.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RSN));
                rsnModel.setDr(Constant.DR_FALSE);
                rsnModel.setRsn(paramDTO.getSerialNum());
                rsnModel.setStatus(3);
                rsnModel.setCreatedBy(paramDTO.getUserId());
                rsnModel.setCreatedTime(new Date());
                opeCodebaseRsnService.save(rsnModel);
            } else {
                log.info("rsn??????????????????,?????????rsn?????????????????????");
                rsn.setStatus(3);
                rsn.setUpdatedBy(paramDTO.getUserId());
                rsn.setUpdatedTime(new Date());
                opeCodebaseRsnService.updateById(rsn);
            }
        }

         // ????????????????????????
        opeOrderQcItem.setOrderSerialId(serialId);
        opeOrderQcItem.setOrderType(opeQcOrder.getRelationOrderType());
        opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
        opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

        // ????????????????????????
        resultDTO.setQty(remainingQty);
        resultDTO.setName(name);
        resultDTO.setPartsNo(paramDTO.getPartsNo());
        resultDTO.setLot(paramDTO.getLot());
        resultDTO.setSerialNum(ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType()) ? serialNum : paramDTO.getSerialNum());
        resultDTO.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
        resultDTO.setProductionDate(new Date());
        resultDTO.setQcResultFlag(qcResultFlag);
        resultDTO.setTabletSn(paramDTO.getTabletSn());
        return resultDTO;
    }


    // ?????????????????? ??????????????????????????????????????????????????????????????????
    public void changePurchaseParts(Long purchaseId,Long partsId,Integer qty){
        // ??????????????????????????????
        QueryWrapper<OpeProductionPurchasePartsB> qw = new QueryWrapper<>();
        qw.eq(OpeProductionPurchasePartsB.COL_PARTS_ID,partsId);
        qw.eq(OpeProductionPurchasePartsB.COL_PRODUCTION_PURCHASE_ID,purchaseId);
        qw.last("limit 1");
        OpeProductionPurchasePartsB purchasePartsB = opeProductionPurchasePartsBService.getOne(qw);
        if (purchasePartsB != null) {
            purchasePartsB.setQualifiedQty((purchasePartsB.getQualifiedQty()==null?0:purchasePartsB.getQualifiedQty()) + qty);
            opeProductionPurchasePartsBService.saveOrUpdate(purchasePartsB);
        }
    }


    // ?????????????????? ????????????????????????????????????????????????????????????
    public void changeCombinScooter(Long combinId,Long scooterBomId) {
        // ???????????????????????????
        QueryWrapper<OpeCombinOrderScooterB> qw = new QueryWrapper<>();
        qw.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, combinId);
        qw.eq(OpeCombinOrderScooterB.COL_SCOOTER_BOM_ID, scooterBomId);
        qw.last("limit 1");
        OpeCombinOrderScooterB orderScooterB = opeCombinOrderScooterBService.getOne(qw);
        if (orderScooterB != null) {
            orderScooterB.setWaitInWhQty((orderScooterB.getWaitInWhQty()==null?0:orderScooterB.getWaitInWhQty())+1);
            opeCombinOrderScooterBService.saveOrUpdate(orderScooterB);
        }
    }


    // ?????????????????? ????????????????????????????????????????????????????????????
    public void changeCombinCombin(Long combinId,Long productionCombinBomId) {
        // ???????????????????????????
        QueryWrapper<OpeCombinOrderCombinB> qw = new QueryWrapper<>();
        qw.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, combinId);
        qw.eq(OpeCombinOrderCombinB.COL_PRODUCTION_COMBIN_BOM_ID, productionCombinBomId);
        qw.last("limit 1");
        OpeCombinOrderCombinB orderCombinB = opeCombinOrderCombinBService.getOne(qw);
        if (orderCombinB != null) {
            orderCombinB.setWaitInWhQty((orderCombinB.getWaitInWhQty()==null?0:orderCombinB.getWaitInWhQty())+1);
            opeCombinOrderCombinBService.saveOrUpdate(orderCombinB);
        }
    }



    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(enter.getId());

        RpsAssert.isNull(opeQcOrder, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());
        /*RpsAssert.isTrue(!QcStatusEnum.QUALITY_INSPECTION.getStatus().equals(opeQcOrder.getQcStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());*/

         // ???????????????????????????????????????????????? 1?????? 2????????? 3??????
        List<QcOrderProductDTO> productList;
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
        // ??????????????????????????????
        checkQcQty(productList);

        opeQcOrder.setId(opeQcOrder.getId());
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION_COMPLETED.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

         // ????????????????????????????????????
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
     * ??????????????????????????????
     *
     * @param productList
     */
    private void checkQcQty(List<QcOrderProductDTO> productList) {
        productList.forEach(product -> {
            RpsAssert.isTrue((product.getQualifiedQty() + product.getUnqualifiedQty()) < product.getQty(),
                    ExceptionCodeEnums.NOT_COMPLETED_QC.getCode(), ExceptionCodeEnums.NOT_COMPLETED_QC.getMessage());
        });
    }

}
