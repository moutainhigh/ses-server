package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.qc.QcStatusEnum;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.qc.QcTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
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
import com.redescooter.ses.mobile.rps.dao.qcorder.*;
import com.redescooter.ses.mobile.rps.dao.wms.*;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.qc.QcOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QcProductResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
    private QcOrderMapper qcOrderMapper;
    @Resource
    private QcScooterMapper qcScooterMapper;
    @Resource
    private QcCombinMapper qcCombinMapper;
    @Resource
    private QcPartsMapper qcPartsMapper;
    @Resource
    private QcOrderSerialBindMapper qcOrderSerialBindMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


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
        String serialNum = qcOrderSerialBindMapper.getDefaultSerialNumBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNotBlank(serialNum, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

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
                }
                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                remainingQty = opeQcScooterB.getQty() - (opeQcScooterB.getQualifiedQty() + opeQcScooterB.getUnqualifiedQty());
                qcId = opeQcScooterB.getQcId();

                opeQcScooterB.setUpdatedBy(paramDTO.getUserId());
                opeQcScooterB.setUpdatedTime(new Date());
                qcScooterMapper.updateQcScooter(opeQcScooterB);
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
                }
                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());
                remainingQty = opeQcCombinB.getQty() - (opeQcCombinB.getQualifiedQty() + opeQcCombinB.getUnqualifiedQty());
                qcId = opeQcCombinB.getQcId();

                opeQcCombinB.setUpdatedBy(paramDTO.getUserId());
                opeQcCombinB.setUpdatedTime(new Date());
                qcCombinMapper.updateQcCombin(opeQcCombinB);
                break;
            default:
                OpeQcPartsB opeQcPartsB = qcPartsMapper.getQcPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeQcPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                RpsAssert.isTrue(qcQty > opeQcPartsB.getQty(),ExceptionCodeEnums.QC_QTY_ERROR.getCode(),
                        ExceptionCodeEnums.QC_QTY_ERROR.getMessage());

                // 更新部件质检合格/不合格数量(部件存在无码质检这里要特殊处理)
                int qty = 0;
                if (qcResultFlag) {
                    qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? opeQcPartsB.getQualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setQualifiedQty(qty);
                } else {
                    qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? opeQcPartsB.getUnqualifiedQty() + 1 : qcQty;
                    opeQcPartsB.setUnqualifiedQty(qty);
                }
                name = partsMapper.getPartsCnNameById(paramDTO.getBomId());
                remainingQty = opeQcPartsB.getQty() - (opeQcPartsB.getQualifiedQty() + opeQcPartsB.getUnqualifiedQty());
                qcId = opeQcPartsB.getQcId();

                opeQcPartsB.setUpdatedBy(paramDTO.getUserId());
                opeQcPartsB.setUpdatedTime(new Date());
                qcPartsMapper.updateQcParts(opeQcPartsB);
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

        // 当质检单为【生产采购单】产生时才需要打印二维码
        OpeQcOrder opeQcOrder = qcOrderMapper.getQcOrderById(qcId);
        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeQcOrder.getRelationOrderType())) {
            resultDTO.setPrintFlag(true);
            if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                opeQcOrderSerialBind.setDefaultSerialNum(paramDTO.getSerialNum());
                opeQcOrderSerialBind.setSerialNum("PARTS" + System.currentTimeMillis());
            }
        }
        qcOrderSerialBindMapper.insertQcOrderSerialBind(opeQcOrderSerialBind);

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

    @Override
    public GeneralResult completeQc(IdEnter enter) {
        QcOrderDetailDTO qcOrderDetailDTO = qcOrderMapper.getQcOrderDetailById(enter.getId());
        RpsAssert.isNull(qcOrderDetailDTO, ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.QC_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!QcStatusEnum.QUALITY_INSPECTION.getStatus().equals(qcOrderDetailDTO.getStatus()),
                ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.QC_ORDER_STATUS_ERROR.getMessage());

        OpeQcOrder opeQcOrder = new OpeQcOrder();
        opeQcOrder.setId(qcOrderDetailDTO.getId());
        opeQcOrder.setQcStatus(QcStatusEnum.QUALITY_INSPECTION_COMPLETED.getStatus());
        opeQcOrder.setUpdatedBy(enter.getUserId());
        opeQcOrder.setUpdatedTime(new Date());
        qcOrderMapper.updateQcOrder(opeQcOrder);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 获取产品序列号
     * @param productType 产品类型 1车辆 2组装件 3部件
     * @param serialNum 序列号
     * @return
     */
    private String getProductSerialNum(Integer productType, String serialNum) {
        Calendar cal = Calendar.getInstance();
        // 年、月、日
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH ) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

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
                return StringUtils.isNotBlank(serialNum) ? serialNum : "COMBINATION" + System.currentTimeMillis();
            default:
                return StringUtils.isNotBlank(serialNum) ? serialNum : "PARTS" + System.currentTimeMillis();
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
        // 生产流水号在质检单这边暂时只有 “生产日” + “工单号”组成, 流水号在确认入库时生成
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", RandomUtils.nextInt(1000, 9999));
        sb.append(number);

        return sb.toString();
    }

}
