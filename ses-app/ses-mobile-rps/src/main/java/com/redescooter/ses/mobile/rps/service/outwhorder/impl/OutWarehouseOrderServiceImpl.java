package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.order.*;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOpTrace;
import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
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
         * 使用编程式事务保证多事务的唯一性
         */
        transactionTemplate.execute(outWarehouseOrderStatus -> {
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
                log.error("【出库单开始质检失败】----{}", ExceptionUtils.getStackTrace(e));
                outWarehouseOrderStatus.setRollbackOnly();
            }
            return 1;
        });

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public OutWarehouseOrderDetailDTO getOutWarehouseOrderDetailById(IdEnter enter) {
        OutWarehouseOrderDetailDTO outWarehouseOrderDetail = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        if (null == outWarehouseOrderDetail) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

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
        switch (paramDTO.getOrderType()) {
            case 1:
                productDetail = outWhScooterBMapper.getScooterProductDetailByProductId(paramDTO.getId());
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderScooterByProductId(paramDTO.getId());
                break;
            case 2:
                productDetail = outWhCombinBMapper.getCombinProductDetailByProductId(paramDTO.getId());
                // 车辆跟组装件序列号查询返回结果一致,直接用同一个接口了
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderScooterByProductId(paramDTO.getId());
                break;
            default:
                productDetail = outWhPartsBMapper.getPartsProductDetailByProductId(paramDTO.getId());
                productSerialNumberList = invoiceProductSerialNumMapper.getOutWhOrderPartsByProductId(paramDTO.getId());
                break;
        }

        productDetail.setProductSerialNumberList(productSerialNumberList);
        return productDetail;
    }

    @Override
    public GeneralResult saveQcResult(SaveQcResultParamDTO paramDTO) {

        transactionTemplate.execute(saveQcResultStatus -> {

            return 1;
        });

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
