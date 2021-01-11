package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.service.RosOutWhOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.order.*;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.qc.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dm.*;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

        for (OutBoundOrderTypeEnums item : OutBoundOrderTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
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
        RpsAssert.isTrue(result, ExceptionCodeEnums.OUT_WH_ORDER_START_QC_ERROR.getCode(),
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
        if (CollectionUtils.isEmpty(productQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
        }

        List<Long> templateIdList = productQcTemplateList.stream().map(ProductQcTemplateDTO::getId).collect(Collectors.toList());

        List<ProductQcTemplateResultDTO> qcResultList = templateMapper.getProductQcTemplateResultByTemplateIds(templateIdList);
        if (CollectionUtils.isEmpty(qcResultList)){
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
        }

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
        List<QcProductResultDTO> qcProductResultList = null;
        try {
            qcProductResultList = JSONArray.parseArray(paramDTO.getSt(), QcProductResultDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        /**
         * 检查质检的产品是否存在
         */
        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        List<Long> templateResultIds = qcProductResultList.stream().map(QcProductResultDTO::getTemplateResultId).collect(Collectors.toList());
        List<ProductQcTemplateResultDTO> qcResultList = templateMapper.getPassProductQcTemplateResultByIds(templateResultIds);

        /**
         * 如果提交的质检结果数量跟查询质检通过的质检数量不一致则表示质检失败
         */
        RpsAssert.isTrue(qcProductResultList.size() != qcResultList.size(), ExceptionCodeEnums.QC_ERROR.getCode(),
                ExceptionCodeEnums.QC_ERROR.getMessage());

        /**
         * 已质检的产品无需再次质检
         */
        ProductSerialNumberDTO productSerialNumber = invoiceProductSerialNumMapper
                .getInvoiceProductSerialNumBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNotNull(productSerialNumber, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());


        transactionTemplate.execute(qcStatus -> {
            try {

                /**
                 * 保存出库单产品序列号信息
                 */
                OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
                BeanUtils.copyProperties(opeOrderSerialBind, opeInvoiceProductSerialNum);

                opeInvoiceProductSerialNum.setId(idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM));
                opeInvoiceProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
                opeInvoiceProductSerialNum.setRelationType(opeOrderSerialBind.getProductType());
                opeInvoiceProductSerialNum.setCreatedBy(paramDTO.getUserId());
                opeInvoiceProductSerialNum.setCreatedTime(new Date());
                opeInvoiceProductSerialNum.setUpdatedBy(paramDTO.getUserId());
                opeInvoiceProductSerialNum.setUpdatedTime(new Date());
                invoiceProductSerialNumMapper.insertInvoiceProductSerialNum(opeInvoiceProductSerialNum);

                /**
                 * 保存质检记录 >T﹏T>
                 */


                /**
                 * 更新出库单产品质检数量
                 */


            } catch (Exception e) {
                log.error("【保存质检结果失败】----{}", ExceptionUtils.getStackTrace(e));
                qcStatus.setRollbackOnly();
            }
            return 1;
        });


        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public GeneralResult outWarehouse(IdEnter enter) {


        /**
         * 更新出库单已出库数量
         */


        /**
         * 调用Aleks提交出库后的状态流转方法
         */
        rosOutWhOrderService.outWarehouse(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updatePartsQcQty(UpdatePartsQcQtyParamDTO paramDTO) {
        OutWhOrderProductDetailDTO outWhOrderProduct = outWhPartsBMapper.getPartsProductDetailByProductId(paramDTO.getProductId());

        RpsAssert.isNull(outWhOrderProduct, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
        RpsAssert.isTrue(!paramDTO.getQcQty().equals(outWhOrderProduct.getQty()), ExceptionCodeEnums.QC_QTY_ERROR.getCode(),
                ExceptionCodeEnums.QC_QTY_ERROR.getMessage());

        /**
         * 更新部件质检数量
         */
        OpeOutWhPartsB opeOutWhPartsB = OpeOutWhPartsB.builder()
                .id(paramDTO.getProductId())
                .alreadyOutWhQty(paramDTO.getQcQty())
                // 图片附件
                .updatedBy(paramDTO.getUserId())
                .updatedTime(new Date())
                .build();
        outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);

        return new GeneralResult(paramDTO.getRequestId());
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

}
