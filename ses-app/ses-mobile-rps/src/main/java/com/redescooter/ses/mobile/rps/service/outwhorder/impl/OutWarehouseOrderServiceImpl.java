package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

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
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.order.*;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.outwhorder.OutWarehouseOrderService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
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
    private WmsStockMapper wmsStockMapper;
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
    public GeneralResult outWarehouse(IdEnter enter) {
        OutWarehouseOrderDetailDTO outWarehouseOrder = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        RpsAssert.isNull(outWarehouseOrder, ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(outWarehouseOrder.getAlreadyOutWhQty() == 0, ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getCode(),
                ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getMessage());

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

        /**
         * 数据完整性校验 1.产品不存在 2.产品已质检,无需再次质检 3.质检数量有误
         */
        RpsAssert.isNull(outWhOrderProduct, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        RpsAssert.isTrue(outWhOrderProduct.getAlreadyOutWhQty() > 0, ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());

        RpsAssert.isTrue(paramDTO.getQcQty() > outWhOrderProduct.getQty(), ExceptionCodeEnums.QC_QTY_GREATER_THAN_QTY.getCode(),
                ExceptionCodeEnums.QC_QTY_GREATER_THAN_QTY.getMessage());

        /**
         * 更新部件质检数量、质检图片信息
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
        int alreadyOutWhQty = outWarehouseOrderMapper.getOutWhOrderAlreadyOutWhQtyById(outWhOrderProduct.getOutWhId());

        OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
        opeOutWhouseOrder.setId(outWhOrderProduct.getOutWhId());
        opeOutWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue());
        opeOutWhouseOrder.setAlreadyOutWhQty(alreadyOutWhQty + paramDTO.getQcQty());
        opeOutWhouseOrder.setUpdatedBy(paramDTO.getUserId());
        opeOutWhouseOrder.setUpdatedTime(new Date());
        outWarehouseOrderMapper.updateOutWarehouseOrder(opeOutWhouseOrder);

        /**
         * 质检完成库存操作扣减
         */


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
