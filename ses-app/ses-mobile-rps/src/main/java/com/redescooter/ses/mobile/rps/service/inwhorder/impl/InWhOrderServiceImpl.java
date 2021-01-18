package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.service.order.OpTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.UpdatePartsQcQtyParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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
 * @date 2021/1/15 18:09
 */
@Slf4j
@Service
public class InWhOrderServiceImpl implements InWhOrderService {

    @Reference
    private IdAppService idAppService;
    @Resource
    private InWhOrderMapper inWhOrderMapper;
    @Resource
    private OpTraceService opTraceService;
    @Resource
    private OrderStatusFlowService orderStatusFlowService;
    @Resource
    private InWhouseScooterBMapper inWhouseScooterBMapper;
    @Resource
    private InWhouseCombinBMapper inWhouseCombinBMapper;
    @Resource
    private InWhousePartsBMapper inWhousePartsBMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public Map<Integer, Integer> getInWarehouseOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseOrderTypeCount();
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
    public Map<Integer, Integer> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseTypeCount(paramDTO);
        /**
         * {inWhType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult:: getTotalCount)
        );

        for (InWhTypeEnums item : InWhTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }

        return map;
    }

    @Override
    public PageResult<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO) {
        int count = inWhOrderMapper.countByInWhOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, inWhOrderMapper.getInWarehouseOrderList(paramDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startQc(IdEnter enter) {
        InWhOrderDetailDTO inWhOrderDetail = inWhOrderMapper.getInWhOrderDetailById(enter.getId());
        RpsAssert.isNull(inWhOrderDetail, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue().equals(inWhOrderDetail.getStatus()),
                ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());

        /**
         * 更新入库单状态为 “质检中”, 保存订单操作记录
         */
        OpeInWhouseOrder opeInWhouseOrder = new OpeInWhouseOrder();
        opeInWhouseOrder.setId(enter.getId());
        opeInWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.INSPECTING.getValue());
        opeInWhouseOrder.setUpdatedBy(enter.getUserId());
        opeInWhouseOrder.setUpdatedTime(new Date());
        inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

        opTraceService.insertOpTrace(inWhOrderDetail.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(),
                OrderOperationTypeEnums.START_QC.getValue(), inWhOrderDetail.getRemark(), enter.getUserId());

        orderStatusFlowService.insertOrderStatusFlow(inWhOrderDetail.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(),
                InWhouseOrderStatusEnum.INSPECTING.getValue(), inWhOrderDetail.getRemark(), enter.getUserId());

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public InWhOrderDetailDTO getInWarehouseOrderDetailById(IdEnter enter) {
        InWhOrderDetailDTO inWhOrderDetail = inWhOrderMapper.getInWhOrderDetailById(enter.getId());
        RpsAssert.isNull(inWhOrderDetail, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        List<InWhOrderProductDTO> productList = null;
        /**
         * 查询入库单产品信息 1车辆 2组装件 3部件
         */
        switch (inWhOrderDetail.getOrderType()) {
            case 1:
                productList = inWhouseScooterBMapper.getInWhOrderScooterByInWhId(inWhOrderDetail.getId());
                break;
            case 2:
                productList = inWhouseCombinBMapper.getInWhOrderCombinByInWhId(inWhOrderDetail.getId());
                break;
            default:
                productList = inWhousePartsBMapper.getInWhOrderPartsByInWhId(inWhOrderDetail.getId());
                break;
        }

        /**
         * 已质检数量+质检失败数量 < 应入库数量 ---- 待质检
         */
        List<InWhOrderProductDTO> pendingQcProductList = productList.stream().filter(
                p -> (p.getQcQty() + p.getUnqualifiedQty()) < p.getQty()
        ).collect(Collectors.toList());

        /**
         * 已质检数量 == 应入库数量 ---- 质检成功
         */
        List<InWhOrderProductDTO> qcSuccessProductList = productList.stream().filter(
                p -> p.getQcQty().equals(p.getQty())
        ).collect(Collectors.toList());

        /**
         * 质检失败数量>0 && 应入库数量 == 已质检数量+质检失败数量 ---- 质检失败
         */
        List<InWhOrderProductDTO> qcFailedProductList = productList.stream().filter(
                p -> p.getUnqualifiedQty() > 0 && p.getQty().equals(p.getQcQty() + p.getUnqualifiedQty())
        ).collect(Collectors.toList());


        inWhOrderDetail.setPendingQcProductList(pendingQcProductList);
        inWhOrderDetail.setQcSuccessProductList(qcSuccessProductList);
        inWhOrderDetail.setQcFailedProductList(qcFailedProductList);

        return inWhOrderDetail;
    }

    @Override
    public GeneralResult completeQc(IdEnter enter) {
        InWhOrderDetailDTO inWhOrderDetail = inWhOrderMapper.getInWhOrderDetailById(enter.getId());
        RpsAssert.isNull(inWhOrderDetail, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        /**
         * 修改入库单状态为 “待入库”
         */
        OpeInWhouseOrder opeInWhouseOrder = new OpeInWhouseOrder();
        opeInWhouseOrder.setId(inWhOrderDetail.getId());
        opeInWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_IN_WH.getValue());
        opeInWhouseOrder.setUpdatedBy(enter.getUserId());
        opeInWhouseOrder.setUpdatedTime(new Date());
        inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

        return new GeneralResult(enter.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updatePartsQcQty(UpdatePartsQcQtyParamDTO paramDTO) {
        OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());
        RpsAssert.isNull(opeInWhousePartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        RpsAssert.isTrue(paramDTO.getQcQty() > opeInWhousePartsB.getInWhQty(),
                ExceptionCodeEnums.QC_QTY_GREATER_THAN_INBOUND_QTY.getCode(), ExceptionCodeEnums.QC_QTY_GREATER_THAN_INBOUND_QTY.getMessage());

        /**
         * 修改入库单部件信息
         */
        opeInWhousePartsB.setQcQty(paramDTO.getQcQty());
        opeInWhousePartsB.setUnqualifiedQty(opeInWhousePartsB.getInWhQty() - paramDTO.getQcQty());
        opeInWhousePartsB.setUpdatedBy(paramDTO.getUserId());
        opeInWhousePartsB.setUpdatedTime(new Date());

        inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);

        return new GeneralResult(paramDTO.getRequestId());
    }

}
