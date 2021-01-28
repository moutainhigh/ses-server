package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutWhOrderTypeEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.service.RosOutWhOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.config.component.SaveWmsStockDataComponent;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.*;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.combinorder.CombinationOrderService;
import com.redescooter.ses.mobile.rps.service.outwhorder.OutWarehouseOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @DubboReference
    private IdAppService idAppService;
    @DubboReference
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
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;
    @Resource
    private OutWhouseOrderSerialBindMapper outWhouseOrderSerialBindMapper;
    @Resource
    private SaveWmsStockDataComponent saveWmsStockDataComponent;
    @Resource
    private CombinationOrderService combinationOrderService;
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
        // 调整status值,避免恶意传参导致查询数据有问题
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
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
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
        int count = outWarehouseOrderMapper.countByOutWarehouseOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, outWarehouseOrderMapper.getOutWarehouseOrderList(paramDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult submitQc(IdEnter enter) {
        OutWarehouseOrderDetailDTO outWarehouseOrderDetail = outWarehouseOrderMapper.getOutWarehouseOrderDetailById(enter.getId());
        RpsAssert.isNull(outWarehouseOrderDetail, ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());

        RpsAssert.isTrue(!OutBoundOrderStatusEnums.BE_OUTBOUND.getValue().equals(outWarehouseOrderDetail.getStatus()),
                ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        /**
         * 调用Chris生成出库质检单接口
         */
        rosOutWhOrderService.generatorQcOrderByOutBound(enter);

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        Integer qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : paramDTO.getQty();

        // 避免产品重复扫码出库
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            OpeOutWhouseOrderSerialBind opeOutWhouseOrderSerialBind = outWhouseOrderSerialBindMapper
                    .getOutWhouseOrderSerialBindBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeOutWhouseOrderSerialBind, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        /**
         * 产品扫码出库 1车辆 2组装件 3部件
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeOutWhScooterB opeOutWhScooterB = outWhScooterBMapper.getOutWhOrderScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeOutWhScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新出库单车辆已出库数量
                opeOutWhScooterB.setAlreadyOutWhQty(opeOutWhScooterB.getAlreadyOutWhQty() + 1);
                opeOutWhScooterB.setUpdatedBy(paramDTO.getUserId());
                opeOutWhScooterB.setUpdatedTime(new Date());
                outWhScooterBMapper.updateOutWhScooterB(opeOutWhScooterB);

                break;
            case 2:
                OpeOutWhCombinB opeOutWhCombinB = outWhCombinBMapper.getOutWhOrderCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeOutWhCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新出库单组装件已出库数量
                opeOutWhCombinB.setAlreadyOutWhQty(opeOutWhCombinB.getAlreadyOutWhQty() + 1);
                opeOutWhCombinB.setUpdatedBy(paramDTO.getUserId());
                opeOutWhCombinB.setUpdatedTime(new Date());
                outWhCombinBMapper.updateOutWhCombinB(opeOutWhCombinB);

                break;
            default:
                OpeOutWhPartsB opeOutWhPartsB = outWhPartsBMapper.getOutWhOrderPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeOutWhPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                RpsAssert.isTrue(qty > opeOutWhPartsB.getQcQty(), ExceptionCodeEnums.OUT_WH_QTY_ERROR.getCode(),
                        ExceptionCodeEnums.OUT_WH_QTY_ERROR.getMessage());

                // 更新出库单部件已出库数量
                if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                    opeOutWhPartsB.setAlreadyOutWhQty(opeOutWhPartsB.getAlreadyOutWhQty() + qty);
                } else {
                    opeOutWhPartsB.setAlreadyOutWhQty(qty);
                }
                opeOutWhPartsB.setUpdatedBy(paramDTO.getUserId());
                opeOutWhPartsB.setUpdatedTime(new Date());
                outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);

                break;
        }

        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            /**
             * 修改库存产品序列号绑定信息
             */
            OpeWmsStockSerialNumber opeWmsStockSerialNumber = new OpeWmsStockSerialNumber();
            opeWmsStockSerialNumber.setRsn(paramDTO.getSerialNum());
            opeWmsStockSerialNumber.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
            opeWmsStockSerialNumber.setUpdatedBy(paramDTO.getUserId());
            opeWmsStockSerialNumber.setUpdatedTime(new Date());
            wmsStockSerialNumberMapper.updateWmsStockSerialNumberByRSn(opeWmsStockSerialNumber);

            /**
             * 保存出库单产品序列号绑定信息(无码产品无需保存)
             */
            OpeOutWhouseOrderSerialBind opeOutWhouseOrderSerialBind = new OpeOutWhouseOrderSerialBind();
            opeOutWhouseOrderSerialBind.setId(idAppService.getId(SequenceName.OPE_OUT_WHOUSE_ORDER_SERIAL_BIND));
            opeOutWhouseOrderSerialBind.setOrderBId(paramDTO.getProductId());
            opeOutWhouseOrderSerialBind.setOrderType(paramDTO.getProductType());
            opeOutWhouseOrderSerialBind.setSerialNum(paramDTO.getSerialNum());
            opeOutWhouseOrderSerialBind.setLot(paramDTO.getLot());
            opeOutWhouseOrderSerialBind.setProductId(paramDTO.getBomId());
            opeOutWhouseOrderSerialBind.setProductType(paramDTO.getProductType());
            opeOutWhouseOrderSerialBind.setQty(qty);
            opeOutWhouseOrderSerialBind.setCreatedBy(paramDTO.getUserId());
            opeOutWhouseOrderSerialBind.setCreatedTime(new Date());
            opeOutWhouseOrderSerialBind.setUpdatedBy(paramDTO.getUserId());
            opeOutWhouseOrderSerialBind.setUpdatedTime(new Date());
            outWhouseOrderSerialBindMapper.insertOutWhouseOrderSerialBind(opeOutWhouseOrderSerialBind);
        }

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    @Transactional
    public GeneralResult outWarehouse(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = outWarehouseOrderMapper.getOutWarehouseById(enter.getId());
        RpsAssert.isNull(opeOutWhouseOrder, ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getMessage());
        if (opeOutWhouseOrder.getRelationType() != null && opeOutWhouseOrder.getRelationType().equals(OrderTypeEnums.INVOICE.getValue())){
            RpsAssert.isTrue(opeOutWhouseOrder.getAlreadyOutWhQty() == 0, ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getCode(),
                    ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getMessage());
        }

        boolean result = transactionTemplate.execute(outWarehouseStatus -> {
            boolean flag = true;
            try {
                /**
                 * 出库操作(仓库库存操作) 1车辆 2组装件 3部件
                 */
                List<OutWarehouseOrderProductDTO> productList = null;
                List<String> serialNumList = null;
                switch (opeOutWhouseOrder.getOutWhType()) {
                    case 1:
                        productList = outWhScooterBMapper.getOutWhOrderScooterByOutWhId(enter.getId());

                        List<Long> outWhScooterIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                        serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhScooterIds);

                        /**
                         * {bomId, List<OutWarehouseOrderProductDTO>}
                         */
                        Map<Long, List<OutWarehouseOrderProductDTO>> scooterMap = productList.stream().collect(
                                Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsScooterStockData(null,null, scooterMap,
                                InOutWhEnums.IN.getValue(), enter.getUserId());
                        break;
                    case 2:
                        productList = outWhCombinBMapper.getOutWhOrderCombinByOutWhId(enter.getId());

                        List<Long> outWhCombinIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                        serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhCombinIds);

                        /**
                         * {bomId, List<OutWarehouseOrderProductDTO>}
                         */
                        Map<Long, List<OutWarehouseOrderProductDTO>> combinationMap = productList.stream().collect(
                                Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsCombinationStockData(null, combinationMap, InOutWhEnums.OUT.getValue(), enter.getUserId());
                        break;
                    default:
                        productList = outWhPartsBMapper.getOutWhOrderPartsByOutWhId(enter.getId());

                        List<Long> outWhPartsIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                        serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhPartsIds);

                        /**
                         * {bomId, List<OutWarehouseOrderProductDTO>}
                         */
                        Map<Long, List<OutWarehouseOrderProductDTO>> partsMap = productList.stream().collect(
                                Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsPartsStockData(null, partsMap, InOutWhEnums.OUT.getValue(), enter.getUserId());

                        /**
                         * 如果是组装单生成的出库单则生成组装单清单信息
                         */
                        if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeOutWhouseOrder.getRelationType())) {
                            IdEnter idEnter = new IdEnter();
                            idEnter.setId(opeOutWhouseOrder.getRelationId());
                            idEnter.setUserId(enter.getUserId());
                            combinationOrderService.generateCombinationOrderList(idEnter);
                        }

                        break;
                }

                /**
                 * 产品出库后将信息从库存产品序列号表中删除
                 */
                if (CollectionUtils.isNotEmpty(serialNumList)){
                    wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(serialNumList);
                }

                /**
                 * 调用Aleks提交出库后的状态流转方法
                 */
                rosOutWhOrderService.outWarehouse(enter);
            } catch (Exception e) {
                flag = false;
                log.error("【确认出库失败】----{}", ExceptionUtils.getStackTrace(e));
                outWarehouseStatus.setRollbackOnly();
            }
            return flag;
        });

        // 手动抛出异常
        RpsAssert.isFalse(result, ExceptionCodeEnums.DELIVERY_FAILURE.getCode(), ExceptionCodeEnums.DELIVERY_FAILURE.getMessage());

        return new GeneralResult(enter.getRequestId());
    }

}
