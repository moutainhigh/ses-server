package com.redescooter.ses.mobile.rps.service.outwhorder.impl;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.NewOutBoundOrderStatusEnums;
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
import com.redescooter.ses.mobile.rps.dao.outwhorder.*;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderMapper;
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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;

    @Resource
    private OutWhouseOrderSerialBindMapper outWhouseOrderSerialBindMapper;

    @Resource
    private SaveWmsStockDataComponent saveWmsStockDataComponent;

    @Resource
    private CombinationOrderService combinationOrderService;

    @Resource
    private ProductionPartsMapper partsMapper;

    @Resource
    private QcOrderMapper qcOrderMapper;

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
        OpeOutWhouseOrder opeOutWhouseOrder = outWarehouseOrderMapper.getOutWarehouseById(enter.getId());
        RpsAssert.isNull(opeOutWhouseOrder, ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getMessage());

        // 如果是组装备料出库对状态判断是 “待出库”,调拨发货创建的出库单则是“新建”
        if (OutWhOrderTypeEnum.COMBINATION_OUT_OF_STOCK.getType().equals(opeOutWhouseOrder.getOutType())) {
            RpsAssert.isFalse(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue().equals(opeOutWhouseOrder.getOutWhStatus()),
                    ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        } else {
            RpsAssert.isFalse(NewOutBoundOrderStatusEnums.DRAFT.getValue().equals(opeOutWhouseOrder.getOutWhStatus()),
                    ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        /**
         * 检查当前出库单是否已经生成质检单,避免质检单重复生成
         */
        int isExistsQcOrder = qcOrderMapper.isExistsQcOrderByRelationIdByType(opeOutWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue());
        RpsAssert.isTrue(isExistsQcOrder > 0, ExceptionCodeEnums.QC_SUBMITTED.getCode(),
                ExceptionCodeEnums.QC_SUBMITTED.getMessage());

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

        /**
         * 查询出库单产品详情 1：车辆 2：组装件 3：部件(出库单的不合格数量和质检数量字段可以去掉By-assert)
         */
        switch (paramDTO.getProductType()) {
            case 1:
                productDetail = outWhScooterBMapper.getScooterProductDetailByProductId(paramDTO.getProductId());
                break;
            case 2:
                productDetail = outWhCombinBMapper.getCombinProductDetailByProductId(paramDTO.getProductId());
                break;
            default:
                productDetail = outWhPartsBMapper.getPartsProductDetailByProductId(paramDTO.getProductId());
                break;
        }
        return productDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        // 无码产品不填写扫码数量时抛出异常
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());

        Integer qty = paramDTO.getIdClass() ? 1 : paramDTO.getQty();
        // 避免产品重复扫码出库
        if (paramDTO.getIdClass()) {
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
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

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
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

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
                RpsAssert.isBlank(paramDTO.getPartsNo(), ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getMessage());
                // 校验部件是否有序列号标识跟入参传递的是否一致
                Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
                RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                boolean flag = 0 == idClass ? false : true;
                RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeOutWhPartsB opeOutWhPartsB = outWhPartsBMapper.getOutWhOrderPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeOutWhPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新出库单部件已出库数量
                if (paramDTO.getIdClass()) {
                    opeOutWhPartsB.setAlreadyOutWhQty(opeOutWhPartsB.getAlreadyOutWhQty() + qty);
                } else {
                    // 限制无码产品重复质检, 并且无码产品输入的入库数量必须和入库数量一致
                    RpsAssert.isTrue(!qty.equals(opeOutWhPartsB.getQty()),ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeOutWhPartsB.getAlreadyOutWhQty() > 0,ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
                    opeOutWhPartsB.setAlreadyOutWhQty(qty);
                }
                opeOutWhPartsB.setUpdatedBy(paramDTO.getUserId());
                opeOutWhPartsB.setUpdatedTime(new Date());
                outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);
                break;
        }

        if (paramDTO.getIdClass()) {
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
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult outWarehouse(IdEnter enter) {
        OpeOutWhouseOrder opeOutWhouseOrder = outWarehouseOrderMapper.getOutWarehouseById(enter.getId());

        /**
         * 数据完整性校验
         */
        RpsAssert.isNull(opeOutWhouseOrder, ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.OUT_WH_ORDER_IS_NOT_EXISTS.getMessage());

        /*if (!OutWhOrderTypeEnum.COMBINATION_OUT_OF_STOCK.getType().equals(opeOutWhouseOrder.getOutType())) {
            RpsAssert.isFalse(NewOutBoundOrderStatusEnums.BE_OUTBOUND.getValue().equals(opeOutWhouseOrder.getOutWhStatus()),
                    ExceptionCodeEnums.NOT_COMPLETED_QC.getCode(), ExceptionCodeEnums.NOT_COMPLETED_QC.getMessage());
        }*/

        /**
         * 出库操作(仓库库存操作) 1车辆 2组装件 3部件
         */
        List<OutWarehouseOrderProductDTO> productList = null;
        List<String> serialNumList = null;
        switch (opeOutWhouseOrder.getOutWhType()) {
            case 1:
                productList = outWhScooterBMapper.getOutWhOrderScooterByOutWhId(enter.getId());
                // 检查是否有已出库数量
                checkHasAlreadyOutWhQty(productList);

                List<Long> outWhScooterIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhScooterIds);

                /**
                 * {bomId, List<OutWarehouseOrderProductDTO>}
                 */
                Map<Long, List<OutWarehouseOrderProductDTO>> scooterMap = productList.stream().collect(
                        Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                );

                saveWmsStockDataComponent.saveWmsScooterStockData(null,null, scooterMap,
                        InOutWhEnums.OUT.getValue(), enter.getUserId());
                break;
            case 2:
                productList = outWhCombinBMapper.getOutWhOrderCombinByOutWhId(enter.getId());
                // 检查是否有已出库数量
                checkHasAlreadyOutWhQty(productList);

                List<Long> outWhCombinIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhCombinIds);

                /**
                 * {bomId, List<OutWarehouseOrderProductDTO>}
                 */
                Map<Long, List<OutWarehouseOrderProductDTO>> combinationMap = productList.stream().collect(
                        Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                );

                saveWmsStockDataComponent.saveWmsCombinationStockData(null, null, combinationMap,
                        InOutWhEnums.OUT.getValue(), enter.getUserId());
                break;
            default:
                productList = outWhPartsBMapper.getOutWhOrderPartsByOutWhId(enter.getId());

                // 检查是否有已出库数量
                checkHasAlreadyOutWhQty(productList);

                List<Long> outWhPartsIds = productList.stream().map(OutWarehouseOrderProductDTO::getId).collect(Collectors.toList());
                serialNumList = outWhouseOrderSerialBindMapper.batchGetSerialNumByOrderBIds(outWhPartsIds);

                /**
                 * {bomId, List<OutWarehouseOrderProductDTO>}
                 */
                Map<Long, List<OutWarehouseOrderProductDTO>> partsMap = productList.stream().collect(
                        Collectors.groupingBy(OutWarehouseOrderProductDTO::getBomId)
                );

                saveWmsStockDataComponent.saveWmsPartsStockData(null, null, partsMap,
                        InOutWhEnums.OUT.getValue(), enter.getUserId());

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
         * 产品出库后将信息从库存产品序列号表中删除(将库存状态改为不可用)
         */
        if (CollectionUtils.isNotEmpty(serialNumList)){
            for (String obj : serialNumList) {
                OpeWmsStockSerialNumber model = new OpeWmsStockSerialNumber();
                model.setStockStatus(WmsStockStatusEnum.UNAVAILABLE.getStatus());
                model.setRsn(obj);
                model.setUpdatedBy(enter.getUserId());
                model.setUpdatedTime(new Date());
                wmsStockSerialNumberMapper.updateWmsStockSerialNumberByRSn(model);
            }
            //wmsStockSerialNumberMapper.batchDeleteWmsStockSerialNumberBySerialNum(serialNumList);
        }

        /**
         * 调用Aleks提交出库后的状态流转方法
         */
        rosOutWhOrderService.outWarehouse(enter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 检查是否有已出库数量
     * @param outWarehouseOrderProductList
     */
    private void checkHasAlreadyOutWhQty(List<OutWarehouseOrderProductDTO> outWarehouseOrderProductList) {
        int qty = 0;
        for (int i = 0; i < outWarehouseOrderProductList.size(); i++) {
            qty += outWarehouseOrderProductList.get(i).getAlreadyOutWhQty();
        }
        RpsAssert.isTrue(qty <= 0, ExceptionCodeEnums.OUT_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.OUT_WH_QTY_ERROR.getMessage());
    }

}
