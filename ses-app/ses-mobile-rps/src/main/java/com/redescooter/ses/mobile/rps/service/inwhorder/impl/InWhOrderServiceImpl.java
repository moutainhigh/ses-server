package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.CombinOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.config.component.SaveWmsStockDataComponent;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.*;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.purchaseorder.PurchaseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.service.order.OpTraceService;
import com.redescooter.ses.mobile.rps.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.*;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    @Reference
    private ScooterService scooterService;
    @Resource
    private InWhOrderMapper inWhOrderMapper;
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
    private ProductionCombinBomMapper combinBomMapper;
    @Resource
    private ProductionPartsMapper partsMapper;
    @Resource
    private CombinOrderMapper combinOrderMapper;
    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private InWhouseOrderSerialBindMapper inWhouseOrderSerialBindMapper;
    @Resource
    private QcOrderSerialBindMapper qcOrderSerialBindMapper;
    @Resource
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private SaveWmsStockDataComponent saveWmsStockDataComponent;


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

        inWhOrderDetail.setProductList(productList);
        return inWhOrderDetail;
    }

    @Override
    public InWhOrderProductDetailDTO getProductDetailByProductId(QueryProductDetailParamDTO paramDTO) {
        InWhOrderProductDetailDTO inWhOrderProductDetail = null;

        /**
         * 查询入库单产品详情, 1车辆 2组装件 3部件
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
    public SaveScanCodeResultDTO saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        // 公共参数
        Integer qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : paramDTO.getQty();
        Integer remainingQty = 0;
        String name;
        String defaultSerialNum = null;
        Long stockId;

        // 避免重复扫码
        OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBind = inWhouseOrderSerialBindMapper
                .getInWhouseOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNotNull(opeInWhouseOrderSerialBind, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());

        /**
         * 这里只会是质检成功的产品入库 1车辆 2组装件 3部件
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeInWhouseScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新入库单车辆实际入库数量
                opeInWhouseScooterB.setActInWhQty(opeInWhouseScooterB.getActInWhQty() + 1);
                opeInWhouseScooterB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseScooterB.setUpdatedTime(new Date());
                inWhouseScooterBMapper.updateInWhouseScooter(opeInWhouseScooterB);

                remainingQty = opeInWhouseScooterB.getQcQty() - opeInWhouseScooterB.getActInWhQty();
                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());

                // 成品库车辆id
                OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(paramDTO.getBomId());
                OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                        scooterBom.getColorId());
                stockId = opeWmsScooterStock.getId();
                break;
            case 2:
                OpeInWhouseCombinB opeInWhouseCombinB = inWhouseCombinBMapper.getInWhouseCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeInWhouseCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 更新入库单组装件实际入库数量
                opeInWhouseCombinB.setActInWhQty(opeInWhouseCombinB.getActInWhQty() + 1);
                opeInWhouseCombinB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseCombinB.setUpdatedTime(new Date());
                inWhouseCombinBMapper.updateInWhouseCombin(opeInWhouseCombinB);

                remainingQty = opeInWhouseCombinB.getQcQty() - opeInWhouseCombinB.getActInWhQty();
                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());

                // 成品库组装件id
                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(paramDTO.getBomId());
                stockId = opeWmsCombinStock.getId();
                break;
            default:
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());
                RpsAssert.isTrue(qty > opeInWhousePartsB.getQcQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                // 更新入库单部件实际入库数量
                if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                    opeInWhousePartsB.setActInWhQty(opeInWhousePartsB.getActInWhQty() + qty);
                } else {
                    opeInWhousePartsB.setActInWhQty(qty);
                }
                opeInWhousePartsB.setUpdatedBy(paramDTO.getUserId());
                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);

                remainingQty = opeInWhousePartsB.getQcQty() - opeInWhousePartsB.getActInWhQty();
                name = partsMapper.getPartsCnNameById(paramDTO.getBomId());
                defaultSerialNum = qcOrderSerialBindMapper.getDefaultSerialNumBySerialNum(paramDTO.getSerialNum());

                // 原料库部件id
                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(paramDTO.getBomId());
                stockId = opeWmsPartsStock.getId();
                break;
        }

        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            /**
             * 保存入库单产品序列号绑定信息
             */
            OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBindNew = new OpeInWhouseOrderSerialBind();
            opeInWhouseOrderSerialBindNew.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER_SERIAL_BIND));
            opeInWhouseOrderSerialBindNew.setOrderBId(paramDTO.getProductId());
            opeInWhouseOrderSerialBindNew.setOrderType(paramDTO.getProductType());
            opeInWhouseOrderSerialBindNew.setSerialNum(paramDTO.getSerialNum());
            opeInWhouseOrderSerialBindNew.setDefaultSerialNum(defaultSerialNum);
            opeInWhouseOrderSerialBindNew.setTabletSn(paramDTO.getTabletSn());
            opeInWhouseOrderSerialBindNew.setLot(paramDTO.getLot());
            opeInWhouseOrderSerialBindNew.setProductId(paramDTO.getBomId());
            opeInWhouseOrderSerialBindNew.setProductType(paramDTO.getProductType());
            opeInWhouseOrderSerialBindNew.setQty(qty);
            opeInWhouseOrderSerialBindNew.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
            opeInWhouseOrderSerialBindNew.setCreatedBy(paramDTO.getUserId());
            opeInWhouseOrderSerialBindNew.setCreatedTime(new Date());
            opeInWhouseOrderSerialBindNew.setUpdatedBy(paramDTO.getUserId());
            opeInWhouseOrderSerialBindNew.setUpdatedTime(new Date());
            inWhouseOrderSerialBindMapper.insertInWhouseOrderSerialBind(opeInWhouseOrderSerialBindNew);

            /**
             * 保存库存产品序列号信息
             */
            OpeWmsStockSerialNumber opeWmsStockSerialNumber = new OpeWmsStockSerialNumber();
            opeWmsStockSerialNumber.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
            opeWmsStockSerialNumber.setRelationId(stockId);
            opeWmsStockSerialNumber.setRelationType(paramDTO.getProductType());
            opeWmsStockSerialNumber.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());
            opeWmsStockSerialNumber.setRsn(paramDTO.getSerialNum());
            opeWmsStockSerialNumber.setStockStatus(WmsStockStatusEnum.DRAFT.getStatus());
            opeWmsStockSerialNumber.setLotNum(paramDTO.getLot());
            opeWmsStockSerialNumber.setSn(defaultSerialNum);
            opeWmsStockSerialNumber.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
            opeWmsStockSerialNumber.setCreatedBy(paramDTO.getUserId());
            opeWmsStockSerialNumber.setCreatedTime(new Date());
            opeWmsStockSerialNumber.setUpdatedBy(paramDTO.getUserId());
            opeWmsStockSerialNumber.setUpdatedTime(new Date());
        }

        /**
         * 扫码入库返回结果信息
         */
        resultDTO.setQty(remainingQty);
        resultDTO.setName(name);
        resultDTO.setPartsNo(paramDTO.getPartsNo());
        resultDTO.setLot(paramDTO.getLot());
        resultDTO.setSerialNum(paramDTO.getSerialNum());
        resultDTO.setProductionDate(new Date());

        return resultDTO;
    }

    @Override
    public GeneralResult confirmStorage(IdEnter enter) {
        OpeInWhouseOrder opeInWhouseOrder = inWhOrderMapper.getInWhOrderById(enter.getId());
        RpsAssert.isNull(opeInWhouseOrder, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        // 编程式事务
        transactionTemplate.execute(confirmStorageStatus -> {
            boolean flag = true;
            try {
                /**
                 * 入库操作(仓库库存操作) 1车辆 2组装件 3部件
                 */
                List<InWhOrderProductDTO> inWhOrderProductList = null;
                List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds = null;
                switch (opeInWhouseOrder.getOrderType()) {
                    case 1:
                        inWhOrderProductList = inWhouseScooterBMapper.getInWhOrderScooterByInWhId(enter.getId());

                        List<Long> inWhScooterIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                        inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhScooterIds);

                        /**
                         * {bomId, List<InWhOrderProductDTO>}
                         */
                        Map<Long, List<InWhOrderProductDTO>> scooterMap = inWhOrderProductList.stream().collect(
                                Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsScooterStockData(scooterMap, inWhouseOrderSerialBinds,null,
                                InOutWhEnums.IN.getValue(), enter.getUserId());
                        break;
                    case 2:
                        inWhOrderProductList = inWhouseCombinBMapper.getInWhOrderCombinByInWhId(enter.getId());

                        List<Long> inWhCombinationIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                        inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhCombinationIds);

                        /**
                         * {bomId, List<InWhOrderProductDTO>}
                         */
                        Map<Long, List<InWhOrderProductDTO>> combinationMap = inWhOrderProductList.stream().collect(
                                Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsCombinationStockData(combinationMap, null, InOutWhEnums.IN.getValue(),enter.getUserId());
                        break;
                    default:
                        inWhOrderProductList = inWhousePartsBMapper.getInWhOrderPartsByInWhId(enter.getId());

                        List<Long> inWhPartsIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                        inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhPartsIds);

                        /**
                         * {bomId, List<InWhOrderProductDTO>}
                         */
                        Map<Long, List<InWhOrderProductDTO>> partsMap = inWhOrderProductList.stream().collect(
                                Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                        );

                        saveWmsStockDataComponent.saveWmsPartsStockData(partsMap, null, InOutWhEnums.IN.getValue(), enter.getUserId());
                        break;
                }

                /**
                 * 将入库产品状态从库存产品序列号表中修改为【可用】
                 */
                List<String> serialNumList = inWhouseOrderSerialBinds.stream().map(OpeInWhouseOrderSerialBind::getSerialNum).collect(Collectors.toList());
                wmsStockSerialNumberMapper.batchUpdateStockStatusByRsnList(serialNumList, enter.getUserId(), new Date());

                if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeInWhouseOrder.getRelationOrderType())) {
                    // 更新采购单状态为 【已入库】
                    OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
                    opeProductionPurchaseOrder.setId(opeInWhouseOrder.getRelationOrderId());
                    opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.HAS_BEEN_STORED.getValue());
                    opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
                    opeProductionPurchaseOrder.setUpdatedTime(new Date());
                    purchaseOrderMapper.updatePurchaseOrder(opeProductionPurchaseOrder);
                } else if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeInWhouseOrder.getRelationOrderType())) {
                    // 更新组装单为 【已入库】
                    OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
                    opeCombinOrder.setId(opeInWhouseOrder.getRelationOrderId());
                    opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.ALREADY_IN_WHOUSE.getValue());
                    opeCombinOrder.setUpdatedBy(enter.getUserId());
                    opeCombinOrder.setUpdatedTime(new Date());
                    combinOrderMapper.updateCombinOrder(opeCombinOrder);
                }

                // 修改入库单状态为 【已入库】
                opeInWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
                opeInWhouseOrder.setUpdatedBy(enter.getUserId());
                opeInWhouseOrder.setUpdatedTime(new Date());
                inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

            } catch (Exception e) {
                flag = false;
                log.error("【确认入库失败】----{}", ExceptionUtils.getStackTrace(e));
                confirmStorageStatus.setRollbackOnly();
            }
            return flag;
        });

        // 手动抛出异常
        RpsAssert.isFalse(false, ExceptionCodeEnums.WAREHOUSING_FAILED.getCode(),
                ExceptionCodeEnums.WAREHOUSING_FAILED.getMessage());

        return new GeneralResult(enter.getRequestId());
    }


}
