package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.service.order.OpTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.*;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
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
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Resource
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Resource
    private WmsPartsStockMapper wmsPartsStockMapper;
    @Resource
    private ProductionScooterBomMapper scooterBomMapper;
    @Resource
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;
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

        RpsAssert.isTrue(!InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue().equals(inWhOrderDetail.getStatus()),
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
    public InWhOrderProductDetailDTO getProductDetailByProductId(QueryProductDetailParamDTO paramDTO) {
        InWhOrderProductDetailDTO inWhOrderProductDetail = null;

        /**
         * 查询入库单产品详情, 1车辆 2组装件 3部件
         * RPS1.0.0版本不需要调这个接口
         */
        switch (paramDTO.getProductType()) {
            case 1:
                inWhOrderProductDetail = inWhouseScooterBMapper.getInWhOrderScooterById(paramDTO.getProductId());
                break;
            case 2:
                inWhOrderProductDetail = inWhouseCombinBMapper.getInWhOrderCombinById(paramDTO.getProductId());
                break;
            default:
                inWhOrderProductDetail = inWhousePartsBMapper.getInWhOrderPartsById(paramDTO.getProductId());
                break;
        }

        return inWhOrderProductDetail;
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

    @Override
    public GeneralResult completeQc(IdEnter enter) {
        InWhOrderDetailDTO inWhOrderDetail = inWhOrderMapper.getInWhOrderDetailById(enter.getId());
        RpsAssert.isNull(inWhOrderDetail, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        /**
         * 查询入库单产品信息 1车辆 2组装件 3部件
         */
        List<InWhOrderProductDTO> productList = null;
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

        List<InWhOrderProductDTO> pendingQcProductList = productList.stream().filter(
                p -> (p.getQcQty() + p.getUnqualifiedQty()) < p.getQty()
        ).collect(Collectors.toList());

        RpsAssert.isNotEmpty(pendingQcProductList, ExceptionCodeEnums.QC_IS_NOT_COMPLETED.getCode(),
                ExceptionCodeEnums.QC_IS_NOT_COMPLETED.getMessage());

        /**
         * 修改入库单状态为 “待入库”
         */
        OpeInWhouseOrder opeInWhouseOrder = new OpeInWhouseOrder();
        opeInWhouseOrder.setId(inWhOrderDetail.getId());
        opeInWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_IN_WH.getValue());
        opeInWhouseOrder.setQcCompletionTime(new Date());
        opeInWhouseOrder.setUpdatedBy(enter.getUserId());
        opeInWhouseOrder.setUpdatedTime(new Date());
        inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

        /**
         * 保存单据状态流转信息
         */
        orderStatusFlowService.insertOrderStatusFlow(inWhOrderDetail.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(),
                InWhouseOrderStatusEnum.WAIT_IN_WH.getValue(), inWhOrderDetail.getRemark(), enter.getUserId());

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public ConfirmStorageResultDTO confirmStorage(ConfirmStorageParamDTO paramDTO) {
        ConfirmStorageResultDTO resultDTO = new ConfirmStorageResultDTO();
        // 公共参数
        Integer qty = null == paramDTO.getQty() ? 1 : paramDTO.getQty();
        Long relationId;
        Integer relationType;
        Integer inWhType;
        Integer remainingQty = 0;
        String name;

        /**
         * 这里只会是质检成功的产品入库
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhouseScooterB.getActInWhQty() > opeInWhouseScooterB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                remainingQty = opeInWhouseScooterB.getQcQty() - qty;
                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                // 更新入库单车辆实际入库数量
                opeInWhouseScooterB.setActInWhQty(opeInWhouseScooterB.getActInWhQty() + qty);
                opeInWhouseScooterB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseScooterB.setUpdatedTime(new Date());
                inWhouseScooterBMapper.updateInWhouseScooter(opeInWhouseScooterB);

                OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                        scooterBom.getColorId());
                // 设置出入库记录所需参数值
                relationId = opeWmsScooterStock.getId();
                relationType = WmsTypeEnum.SCOOTER_WAREHOUSE.getType();
                inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                // 库存操作
                opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() + qty);
                opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - qty);
                wmsScooterStockMapper.updateWmsScooterStock(opeWmsScooterStock);
                break;
            case 2:
                OpeInWhouseCombinB opeInWhouseCombinB = inWhouseCombinBMapper.getInWhouseCombinById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhouseCombinB.getActInWhQty() > opeInWhouseCombinB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                remainingQty = opeInWhouseCombinB.getQcQty() - qty;
                name = null;
                // 更新入库单组装件实际入库数量
                opeInWhouseCombinB.setActInWhQty(opeInWhouseCombinB.getActInWhQty() + qty);
                opeInWhouseCombinB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseCombinB.setUpdatedTime(new Date());
                inWhouseCombinBMapper.updateInWhouseCombin(opeInWhouseCombinB);

                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(paramDTO.getBomId());

                // 设置出入库记录所需参数值
                relationId = opeWmsCombinStock.getId();
                relationType = WmsTypeEnum.COMBINATION_WAREHOUSE.getType();
                inWhType = InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue();

                // 库存操作
                opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + qty);
                opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - qty);
                wmsCombinStockMapper.updateWmsCombinStock(opeWmsCombinStock);
                break;
            default:
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhousePartsB.getActInWhQty() > opeInWhousePartsB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                remainingQty = opeInWhousePartsB.getQcQty() - qty;
                name = null;
                // 更新入库单部件实际入库数量
                opeInWhousePartsB.setActInWhQty(opeInWhousePartsB.getActInWhQty() + qty);
                opeInWhousePartsB.setUpdatedBy(paramDTO.getUserId());
                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);

                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(paramDTO.getBomId());
                // 设置出入库记录所需参数值
                relationId = opeWmsPartsStock.getId();
                relationType = WmsTypeEnum.PARTS_WAREHOUSE.getType();
                inWhType = InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue();

                // 库存操作
                opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + qty);
                opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - qty);
                wmsPartsStockMapper.updateWmsPartsStock(opeWmsPartsStock);
                break;
        }

        /**
         * 添加出入库记录
         */
        OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
        opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
        opeWmsStockRecord.setDr(0);
        opeWmsStockRecord.setRelationId(relationId);
        opeWmsStockRecord.setRelationType(relationType);
        opeWmsStockRecord.setInWhQty(qty);
        opeWmsStockRecord.setInWhType(inWhType);
        opeWmsStockRecord.setRecordType(Integer.valueOf(InOutWhEnums.IN.getValue()));
        opeWmsStockRecord.setStockType(1); // 仓库类型 1中国仓库 2法国仓库
        opeWmsStockRecord.setCreatedBy(paramDTO.getUserId());
        opeWmsStockRecord.setCreatedTime(new Date());
        opeWmsStockRecord.setUpdatedBy(paramDTO.getUserId());
        opeWmsStockRecord.setUpdatedTime(new Date());

        opeWmsStockRecordMapper.insert(opeWmsStockRecord);

        /**
         * 设置确认入库返回结果信息
         */
        resultDTO.setQty(remainingQty);
        resultDTO.setName(name);
        resultDTO.setPartsNo(paramDTO.getPartsNo());
        resultDTO.setLot(paramDTO.getLot());
        resultDTO.setSerialNum(paramDTO.getSerialNum());

        return resultDTO;
    }

}
