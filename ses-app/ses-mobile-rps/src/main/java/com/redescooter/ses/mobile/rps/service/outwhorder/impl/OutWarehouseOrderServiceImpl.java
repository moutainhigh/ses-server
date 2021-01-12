package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutWhOrderTypeEnum;
import com.redescooter.ses.api.common.service.RosOutWhOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcItemMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcTraceMapper;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.order.*;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.qc.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.enums.OutWhOrderErrorMessageEnum;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.outwhorder.OutWarehouseOrderService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateDTO;
import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateResultDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/4 16:22
 */
@Slf4j
@Service
public class OutWarehouseOrderServiceImpl implements OutWarehouseOrderService {

    @Reference
    private IdAppService idAppService;
    @Reference
    private RosOutWhOrderService rosOutWhOrderService;
    @Resource
    private OutWarehouseOrderMapper outWarehouseOrderMapper;
    @Resource
    private OutWhScooterBMapper outWhScooterBMapper;
    @Resource
    private OutWhCombinBMapper outWhCombinBMapper;
    @Resource
    private OutWhPartsBMapper outWhPartsBMapper;
    @Resource
    private InvoiceProductSerialNumMapper invoiceProductSerialNumMapper;
    @Resource
    private OpTraceMapper opTraceMapper;
    @Resource
    private OrderStatusFlowMapper orderStatusFlowMapper;
    @Resource
    private ProductionQualityTemplateMapper templateMapper;
    @Resource
    private OrderSerialBindMapper orderSerialBindMapper;
    @Resource
    private OpeOrderQcItemMapper opeOrderQcItemMapper;
    @Resource
    private OpeOrderQcTraceMapper opeOrderQcTraceMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public Map<Integer, Integer> getOutWarehouseOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = outWarehouseOrderMapper.getOutWarehouseOrderTypeCount();
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

    @Override
    public Map<Integer, Integer> getOutWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO) {
        List<CountByStatusResult> countByStatusResultList = outWarehouseOrderMapper.getOutWarehouseTypeCount(paramDTO);
        /**
         * {outType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult:: getTotalCount)
        );

        for (OutWhOrderTypeEnum item : OutWhOrderTypeEnum.values()) {
            if (null == map.get(item.getType())) {
                map.put(item.getType(), 0);
            }
        }

        return map;
    }

    @Override
    public PageResult<QueryOutWarehouseOrderResultDTO> getOutWarehouseOrderList(QueryOutWarehouseOrderParamDTO paramDTO) {
        int count = outWarehouseOrderMapper.countByOutWarehouseOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, outWarehouseOrderMapper.getOutWarehouseOrderList(paramDTO));
    }

    @Override
    public GeneralResult startQc(IdEnter enter) {
        OutWhOrderErrorMessageResponse errorMessageResponse = new OutWhOrderErrorMessageResponse();

        OutWarehouseOrderDetailDTO outWarehouseOrderDetail = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        if (null == outWarehouseOrderDetail) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

        if (!OutBoundOrderStatusEnums.BE_OUTBOUND.getValue().equals(outWarehouseOrderDetail.getStatus())){
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        /**
         * 使用编程式事务保证多事务的一致性
         */
        boolean result = transactionTemplate.execute(outWarehouseOrderStatus -> {
            boolean flag = true;
            try {
                OpeOutWhouseOrder outWhouseOrder = OpeOutWhouseOrder.builder()
                        .id(enter.getId())
                        .outWhStatus(OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue())
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .build();
                outWarehouseOrderMapper.updateOutWarehouseOrder(outWhouseOrder);

                /**
                 * 保存出库单操作记录、出库单状态流转信息
                 */
                OpeOpTrace opeOpTrace = buildOpTrace(outWarehouseOrderDetail.getId(), OrderTypeEnums.OUTBOUND.getValue(),
                        OrderOperationTypeEnums.START_QC.getValue(), outWarehouseOrderDetail.getRemark(), enter.getUserId());

                OpeOrderStatusFlow opeOrderStatusFlow = buildOrderStatusFlow(outWarehouseOrderDetail.getId(), OrderTypeEnums.OUTBOUND.getValue(),
                        OutBoundOrderStatusEnums.QUALITY_INSPECTION.getValue(), outWarehouseOrderDetail.getRemark(), enter.getUserId());

                opTraceMapper.insertOpTrace(opeOpTrace);
                orderStatusFlowMapper.insertOrderStatusFlow(opeOrderStatusFlow);
            } catch (Exception e) {
                flag = false;
                log.error("【出库单开始质检失败】----{}", ExceptionUtils.getStackTrace(e));
                outWarehouseOrderStatus.setRollbackOnly();
            }
            return flag;
        });

        /**
         * 出库单开始质检失败时抛出异常
         */
        RpsAssert.isFalse(result, ExceptionCodeEnums.OUT_WH_ORDER_START_QC_ERROR.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_START_QC_ERROR.getMessage());

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public OutWarehouseOrderDetailDTO getOutWarehouseOrderDetailById(IdEnter enter) {
        OutWarehouseOrderDetailDTO outWarehouseOrderDetail = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        RpsAssert.isNull(outWarehouseOrderDetail, ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());

        List<OutWarehouseOrderProductDTO> productList = null;

        /**
         * 查询出库单产品信息 1：车辆 2：组装件 3：部件
         */
        switch (outWarehouseOrderDetail.getOrderType()) {
            case 1:
                productList = outWhScooterBMapper.getOutWhOrderScooterByOutWhId(enter.getId());
                break;
            case 2:
                productList = outWhCombinBMapper.getOutWhOrderCombinByOutWhId(enter.getId());
                break;
            default:
                productList = outWhPartsBMapper.getOutWhOrderPartsByOutWhId(enter.getId());
                break;
        }

        outWarehouseOrderDetail.setProductList(productList);
        return outWarehouseOrderDetail;
    }

    @Override
    public OutWhOrderProductDetailDTO getOutWhOrderProductDetailByProductId(QueryProductDetailParamDTO paramDTO) {
        OutWhOrderProductDetailDTO productDetail = null;
        List<ProductSerialNumberDTO> productSerialNumberList = null;

        /**
         * 查询出库单产品详情 1：车辆 2：组装件 3：部件
         */
        switch (paramDTO.getProductType()) {
            case 1:
                productDetail = outWhScooterBMapper.getScooterProductDetailByProductId(paramDTO.getProductId());
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderScooterByProductId(productDetail.getId());
                break;
            case 2:
                productDetail = outWhCombinBMapper.getCombinProductDetailByProductId(paramDTO.getProductId());
                // 车辆跟组装件序列号查询返回结果一致,直接用同一个接口了
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderScooterByProductId(productDetail.getId());
                break;
            default:
                productDetail = outWhPartsBMapper.getPartsProductDetailByProductId(paramDTO.getProductId());
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderPartsByProductId(productDetail.getId());
                break;
        }

        productDetail.setProductSerialNumberList(productSerialNumberList);
        return productDetail;
    }

    @Override
    public QueryQcTemplateResultDTO getQcTemplateByIdAndType(QueryQcTemplateParamDTO paramDTO) {
        /**
         * 当前接口在用户扫码时进入,此时能拿到的参数只有：产品编号、序列号和批次号
         */
        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        Integer productType;
        String productName;

        /**
         * 根据入参productType调整为质检模板产品类型 && 查询产品名称
         */
        switch (opeOrderSerialBind.getProductType()) {
            case 1:
                productName = outWhScooterBMapper.getScooterModelById(opeOrderSerialBind.getOrderBId());
                productType = QcTemplateProductTypeEnum.SCOOTER.getType();
                break;
            case 2 :
                productName = outWhCombinBMapper.getCombinNameById(opeOrderSerialBind.getOrderBId());
                productType = QcTemplateProductTypeEnum.COMBINATION.getType();
                break;
            default:
                productName = outWhPartsBMapper.getPartsNameById(opeOrderSerialBind.getOrderBId());
                productType = QcTemplateProductTypeEnum.PARTS.getType();
                break;
        }

        // 设置查询条件
        paramDTO.setProductId(opeOrderSerialBind.getProductId());
        paramDTO.setProductType(productType);

        List<ProductQcTemplateDTO> productQcTemplateList = templateMapper.getProductQcTemplateByProductId(paramDTO);
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

        /**
         * 组装返回结果对象
         */
        QueryQcTemplateResultDTO resultDTO = new QueryQcTemplateResultDTO();
        resultDTO.setName(productName);
        resultDTO.setProductId(opeOrderSerialBind.getOrderBId());
        resultDTO.setProductType(opeOrderSerialBind.getProductType());
        resultDTO.setProductQcTemplateList(productQcTemplateList);

        return resultDTO;
    }

    @Override
    public GeneralResult saveQcResult(SaveQcResultParamDTO paramDTO) {
        boolean qcResultFlag = true;
        List<OpeOrderQcTrace> opeOrderQcTraceList = new ArrayList<>();

        List<QcProductResultDTO> qcProductResultList = null;
        try {
            qcProductResultList = JSONArray.parseArray(paramDTO.getSt(), QcProductResultDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        ProductSerialNumberDTO productSerialNumber = invoiceProductSerialNumMapper
                .getInvoiceProductSerialNumBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNotNull(productSerialNumber, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

        /**
         * 查询质检模板信息, 用于检查产品质检结果是否通过
         */
        QueryQcTemplateParamDTO qcTemplateParam = new QueryQcTemplateParamDTO();
        qcTemplateParam.setSerialNum(paramDTO.getSerialNum());

        QueryQcTemplateResultDTO qcTemplateResult = getQcTemplateByIdAndType(qcTemplateParam);
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

        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        BeanUtils.copyProperties(opeOrderSerialBind, opeOrderQcItem);

        Long qcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(qcItemId);
        opeOrderQcItem.setOrderSerialId(opeOrderSerialBind.getId());
        opeOrderQcItem.setOrderType(OrderTypeEnums.OUTBOUND.getValue());
        opeOrderQcItem.setRevision(1);
        opeOrderQcItem.setCreatedTime(new Date());
        opeOrderQcItem.setUpdatedTime(new Date());

        /**
         * 检查质检结果是否通过
         */
        for (QcProductResultDTO qc : qcProductResultList) {
            RpsAssert.isEmpty(qcResultMap.get(qc.getTemplateId()), ExceptionCodeEnums.ILLEGAL_DATA.getCode(),
                    ExceptionCodeEnums.ILLEGAL_DATA.getMessage());

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
         * 保存质检结果
         */
        saveQcResultTransaction(opeOrderQcItem, opeOrderSerialBind, opeOrderQcTraceList, paramDTO.getUserId());

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public OutWhOrderErrorMessageResponse outWarehouse(IdEnter enter) {
        OutWhOrderErrorMessageResponse response = new OutWhOrderErrorMessageResponse();

        OutWarehouseOrderDetailDTO outWarehouseOrder = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        RpsAssert.isNull(outWarehouseOrder, ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getMessage());

        // 统计出库单产品质检数量, 来得到已出库数量
        Integer alreadyOutWhQty;
        switch (outWarehouseOrder.getOrderType()) {
            case 1:
                alreadyOutWhQty = outWhScooterBMapper.countOutWhScooterQcQtyByOutWhId(enter.getId());
                break;
            case 2:
                alreadyOutWhQty = outWhCombinBMapper.countOutWhCombinQcQtyByOutWhId(enter.getId());
                break;
            default:
                alreadyOutWhQty = outWhPartsBMapper.countOutWhPartsQcQtyByOutWhId(enter.getId());
                break;
        }

        if (0 == alreadyOutWhQty) {
            response.setCode(OutWhOrderErrorMessageEnum.QUALITY_INSPECTION_NOT_STARTED.getErrorCode());
            response.setDesc(OutWhOrderErrorMessageEnum.QUALITY_INSPECTION_NOT_STARTED.getErrorDesc());
            return response;
        }

        /**
         * 更新出库单已出库数量
         */
        outWarehouseOrderMapper.updateOutWarehouseOrder(buildOutWhouseOrder(enter.getId(),
                OutBoundOrderStatusEnums.OUT_STOCK.getValue(), null, alreadyOutWhQty, enter.getUserId()));

        /**
         * 调用Aleks提交出库后的状态流转方法
         */
        rosOutWhOrderService.outWarehouse(enter);

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutWhOrderErrorMessageResponse updatePartsQcQty(UpdatePartsQcQtyParamDTO paramDTO) {
        OutWhOrderErrorMessageResponse response = new OutWhOrderErrorMessageResponse();

        OutWhOrderProductDetailDTO outWhOrderProduct = outWhPartsBMapper.getPartsProductDetailByProductId(paramDTO.getProductId());

        /**
         * 1.产品不存在
         * 2.质检数量不能大于应出库数量
         */
        if (null == outWhOrderProduct) {
            response.setCode(OutWhOrderErrorMessageEnum.PRODUCT_IS_NOT_EXISTS.getErrorCode());
            response.setDesc(OutWhOrderErrorMessageEnum.PRODUCT_IS_NOT_EXISTS.getErrorDesc());
            return response;
        }

        if (paramDTO.getQcQty() > outWhOrderProduct.getQty()) {
            response.setCode(OutWhOrderErrorMessageEnum.QC_QTY_GREATER_THAN_QTY.getErrorCode());
            response.setDesc(OutWhOrderErrorMessageEnum.QC_QTY_GREATER_THAN_QTY.getErrorDesc());
            return response;
        }

        /**
         * 更新部件质检数量、质检图片信息
         * -出库单产品表里面应该是需要有三个数量字段：1.出库数量 2.质检数量 3.已出库数量
         * -这里质检完成后就直接修改已出库数量了,因为没有质检数量这个字段,所以这样也没问题,不过正常逻辑应该是：
         * -质检完成后只修改产品的[质检数量]字段, 最终再提交出库的时候才会去更新[已出库数量]字段。委托单那边差不多缺少[已确认数量]字段
         */
        OpeOutWhPartsB opeOutWhPartsB = OpeOutWhPartsB.builder()
                .id(paramDTO.getProductId())
                .alreadyOutWhQty(paramDTO.getQcQty())
                .picture(paramDTO.getImageUrls())
                .updatedBy(paramDTO.getUserId())
                .updatedTime(new Date())
                .build();
        outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);

        /**
         * 更新出库单状态为部分出库和已质检总数
         */
        int qcQty = outWarehouseOrderMapper.getOutWhOrderQcQtyById(outWhOrderProduct.getOutWhId());
        outWarehouseOrderMapper.updateOutWarehouseOrder(buildOutWhouseOrder(outWhOrderProduct.getOutWhId(),
                OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue(), qcQty + paramDTO.getQcQty(),null, paramDTO.getUserId()));

        return response;
    }


    /**
     * 保存质检结果编程式事务方法
     * @param opeOrderQcItem
     * @param opeOrderSerialBind
     * @param opeOrderQcTraceList
     * @param userId
     */
    private void saveQcResultTransaction(OpeOrderQcItem opeOrderQcItem, OpeOrderSerialBind opeOrderSerialBind,
                                         List<OpeOrderQcTrace> opeOrderQcTraceList, Long userId) {
        boolean result = transactionTemplate.execute(qcStatus -> {
            boolean flag = true;
            try {
                Long outWhId = null;
                OutWhOrderProductDetailDTO outWhOrderProduct = null;
                /**
                 * 更新出库单产品质检数量, 只有质检成功时才会更新
                 */
                if (opeOrderQcItem.getQcResult() == 1) {
                    switch (opeOrderSerialBind.getProductType()) {
                        case 1:
                            outWhOrderProduct = outWhScooterBMapper.getScooterProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhScooterB opeOutWhScooterB = new OpeOutWhScooterB();
                            opeOutWhScooterB.setId(outWhOrderProduct.getId());
                            opeOutWhScooterB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhScooterB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhScooterBMapper.updateOutWhScooterB(opeOutWhScooterB);
                            break;
                        case 2:
                            outWhOrderProduct = outWhCombinBMapper.getCombinProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhCombinB opeOutWhCombinB = new OpeOutWhCombinB();
                            opeOutWhCombinB.setId(outWhOrderProduct.getId());
                            opeOutWhCombinB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhCombinB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhCombinBMapper.updateOutWhCombinB(opeOutWhCombinB);
                            break;
                        default:
                            outWhOrderProduct = outWhPartsBMapper.getPartsProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhPartsB opeOutWhPartsB = new OpeOutWhPartsB();
                            opeOutWhPartsB.setId(outWhOrderProduct.getId());
                            opeOutWhPartsB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhPartsB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);
                            break;
                    }

                    /**
                     * 更新出库单状态为 【部分出库】
                     */
                    int qcQty = outWarehouseOrderMapper.getOutWhOrderQcQtyById(outWhId);
                    outWarehouseOrderMapper.updateOutWarehouseOrder(buildOutWhouseOrder(outWhId,
                            OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue(), qcQty + 1,null, userId));
                }

                /**
                 * 保存出库单产品序列号信息
                 */
                OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
                BeanUtils.copyProperties(opeOrderSerialBind, opeInvoiceProductSerialNum);

                opeInvoiceProductSerialNum.setId(idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM));
                opeInvoiceProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
                opeInvoiceProductSerialNum.setRelationType(opeOrderSerialBind.getProductType());
                opeInvoiceProductSerialNum.setCreatedBy(userId);
                opeInvoiceProductSerialNum.setCreatedTime(new Date());
                opeInvoiceProductSerialNum.setUpdatedBy(userId);
                opeInvoiceProductSerialNum.setUpdatedTime(new Date());
                invoiceProductSerialNumMapper.insertInvoiceProductSerialNum(opeInvoiceProductSerialNum);

                /**
                 * 保存质检记录
                 */
                opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
                opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

                /**
                 * 质检失败需要将质检的产品丢入不合格品库中去,通对库存做扣减操作
                 */

            } catch (Exception e) {
                flag = false;
                log.error("【保存质检结果失败】----{}", ExceptionUtils.getStackTrace(e));
                qcStatus.setRollbackOnly();
            }
            return flag;
        });

        // 手动抛出编程式事务异常
        RpsAssert.isFalse(result, ExceptionCodeEnums.QC_ERROR.getCode(), ExceptionCodeEnums.QC_ERROR.getMessage());
    }

    /**
     * 组装单据操作记录信息
     * @param orderId
     * @param orderType
     * @param opType
     * @param remark
     * @param userId
     * @return
     */
    private OpeOpTrace buildOpTrace(Long orderId, Integer orderType, Integer opType, String remark, Long userId) {
        OpeOpTrace opeOpTrace = OpeOpTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_OP_TRACE))
                .dr(0)
                .relationId(orderId)
                .orderType(orderType)
                .opType(opType)
                .remark(remark)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();

        return opeOpTrace;
    }

    /**
     * 组装单据状态流转信息
     * @param orderId
     * @param orderType
     * @param orderStatus
     * @param remark
     * @param userId
     * @return
     */
    private OpeOrderStatusFlow buildOrderStatusFlow(Long orderId, Integer orderType, Integer orderStatus, String remark, Long userId) {
        OpeOrderStatusFlow orderStatusFlow = OpeOrderStatusFlow.builder()
                .id(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW))
                .dr(0)
                .relationId(orderId)
                .orderType(orderType)
                .orderStatus(orderStatus)
                .remark(remark)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();

        return orderStatusFlow;
    }

    /**
     * 组装出库单信息
     * @param id 出库单id
     * @param status 出库单状态,参照枚举类：{@link OutBoundOrderStatusEnums}
     * @param qcQty 已质检数量
     * @param alreadyOutWhQty 已出库数量
     * @param userId 用户id
     * @return
     */
    private OpeOutWhouseOrder buildOutWhouseOrder(Long id, Integer status, Integer qcQty, Integer alreadyOutWhQty, Long userId) {
        OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
        opeOutWhouseOrder.setId(id);
        opeOutWhouseOrder.setOutWhStatus(status);
        opeOutWhouseOrder.setQcQty(qcQty);
        opeOutWhouseOrder.setAlreadyOutWhQty(alreadyOutWhQty);
        opeOutWhouseOrder.setUpdatedBy(userId);
        opeOutWhouseOrder.setUpdatedTime(new Date());
        if (null != alreadyOutWhQty) {
            opeOutWhouseOrder.setOutStockTime(new Date());
        }

        return opeOutWhouseOrder;
    }

}
