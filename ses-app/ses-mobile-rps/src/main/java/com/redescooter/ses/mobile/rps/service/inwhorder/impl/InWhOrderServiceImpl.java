package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.*;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.CombinOrderStatusEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.purchaseorder.PurchaseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private ProductionCombinBomMapper combinBomMapper;
    @Resource
    private ProductionPartsMapper partsMapper;
    @Resource
    private CombinOrderMapper combinOrderMapper;
    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
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

    @Override
    public SaveScanCodeResultDTO saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        // 公共参数
        Integer qty = null == paramDTO.getQty() ? 1 : paramDTO.getQty();
        Integer remainingQty = 0;
        String name;

        /**
         * 这里只会是质检成功的产品入库 1车辆 2组装件 3部件
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhouseScooterB.getActInWhQty() > opeInWhouseScooterB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                // 更新入库单车辆实际入库数量
                opeInWhouseScooterB.setActInWhQty(opeInWhouseScooterB.getActInWhQty() + 1);
                opeInWhouseScooterB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseScooterB.setUpdatedTime(new Date());
                inWhouseScooterBMapper.updateInWhouseScooter(opeInWhouseScooterB);

                remainingQty = opeInWhouseScooterB.getQcQty() - opeInWhouseScooterB.getActInWhQty();
                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                break;
            case 2:
                OpeInWhouseCombinB opeInWhouseCombinB = inWhouseCombinBMapper.getInWhouseCombinById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhouseCombinB.getActInWhQty() > opeInWhouseCombinB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                // 更新入库单组装件实际入库数量
                opeInWhouseCombinB.setActInWhQty(opeInWhouseCombinB.getActInWhQty() + 1);
                opeInWhouseCombinB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseCombinB.setUpdatedTime(new Date());
                inWhouseCombinBMapper.updateInWhouseCombin(opeInWhouseCombinB);

                remainingQty = opeInWhouseCombinB.getQcQty() - opeInWhouseCombinB.getActInWhQty();
                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());
                break;
            default:
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());
                RpsAssert.isTrue(opeInWhousePartsB.getActInWhQty() > opeInWhousePartsB.getActInWhQty(),
                        ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());

                // 更新入库单部件实际入库数量,部件这边因为会存在有码跟无码的质检,所以质检数量需要根据入参来调整
                opeInWhousePartsB.setActInWhQty(opeInWhousePartsB.getActInWhQty() + qty);
                opeInWhousePartsB.setUpdatedBy(paramDTO.getUserId());
                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);

                remainingQty = opeInWhousePartsB.getQcQty() - opeInWhousePartsB.getActInWhQty();
                name = partsMapper.getPartsCnNameById(paramDTO.getBomId());
                break;
        }

        /**
         * 设置确认入库返回结果信息
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

        /**
         * 入库操作(仓库库存操作) 1车辆 2组装件 3部件
         */
        List<InWhOrderProductDTO> inWhOrderProductList = null;
        switch (opeInWhouseOrder.getOrderType()) {
            case 1:
                inWhOrderProductList = inWhouseScooterBMapper.getInWhOrderScooterByInWhId(enter.getId());

                /**
                 * {bomId, List<InWhOrderProductDTO>}
                 */
                Map<Long, List<InWhOrderProductDTO>> scooterMap = inWhOrderProductList.stream().collect(
                        Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                );

                saveWmsScooterStockData(scooterMap, enter.getUserId());
                break;
            case 2:
                inWhOrderProductList = inWhouseCombinBMapper.getInWhOrderCombinByInWhId(enter.getId());
                /**
                 * {bomId, List<InWhOrderProductDTO>}
                 */
                Map<Long, List<InWhOrderProductDTO>> combinationMap = inWhOrderProductList.stream().collect(
                        Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                );

                saveWmsCombinationStockData(combinationMap, enter.getUserId());
                break;
            default:
                inWhOrderProductList = inWhousePartsBMapper.getInWhOrderPartsByInWhId(enter.getId());
                /**
                 * {bomId, List<InWhOrderProductDTO>}
                 */
                Map<Long, List<InWhOrderProductDTO>> partsMap = inWhOrderProductList.stream().collect(
                        Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                );

                saveWmsPartsStockData(partsMap, enter.getUserId());
                break;
        }

        if (OrderTypeEnums.FACTORY_PURCHAS.getValue().equals(opeInWhouseOrder.getRelationOrderType())) {
            /**
             * 更新采购单状态为 “已入库”
             */
            OpeProductionPurchaseOrder opeProductionPurchaseOrder = new OpeProductionPurchaseOrder();
            opeProductionPurchaseOrder.setId(opeInWhouseOrder.getRelationOrderId());
            opeProductionPurchaseOrder.setPurchaseStatus(ProductionPurchasEnums.HAS_BEEN_STORED.getValue());
            opeProductionPurchaseOrder.setUpdatedBy(enter.getUserId());
            opeProductionPurchaseOrder.setUpdatedTime(new Date());
            purchaseOrderMapper.updatePurchaseOrder(opeProductionPurchaseOrder);
        } else if (OrderTypeEnums.COMBIN_ORDER.getValue().equals(opeInWhouseOrder.getRelationOrderType())) {
            /**
             * 更新组装单为 “已入库”
             */
            OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
            opeCombinOrder.setId(opeInWhouseOrder.getRelationOrderId());
            opeCombinOrder.setCombinStatus(CombinOrderStatusEnums.ALREADY_IN_WHOUSE.getValue());
            opeCombinOrder.setUpdatedBy(enter.getUserId());
            opeCombinOrder.setUpdatedTime(new Date());
            combinOrderMapper.updateCombinOrder(opeCombinOrder);
        }

        /**
         * 修改入库单状态为 “已入库”
         */
        opeInWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrder.setUpdatedBy(enter.getUserId());
        opeInWhouseOrder.setUpdatedTime(new Date());
        inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

        // 编程式事务
        transactionTemplate.execute(confirmStorageStatus -> {
            boolean flag = true;
            try {

            } catch (Exception e) {
                log.error("【确认入库失败】----{}", ExceptionUtils.getStackTrace(e));
            }
            return flag;
        });

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 组装车辆信息
     * @param scooterNo 车辆编号,也就是车辆扫码得到的序列号
     * @param tabletSn 车载平板序列号, 来源于车辆组装部件上
     * @param userId 用户id
     * @return
     */
    private SyncScooterDataDTO buildScooter(String scooterNo, String tabletSn, Long userId) {
        SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
        syncScooterData.setScooterNo(scooterNo);
        syncScooterData.setTabletSn(tabletSn);
        // 车辆入库默认型号是E50
        syncScooterData.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
        syncScooterData.setUserId(userId);

        return syncScooterData;
    }

    /**
     * 保存车辆成品库信息
     * @param inWhOrderProductMap
     * @param userId
     * @return
     */
    private void saveWmsScooterStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, Long userId) {
        // 公共参数：车辆成品库、出入库记录
        List<OpeWmsScooterStock> opeWmsScooterStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 组装车辆入成品库数据
         */
        for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
            // 车辆成品库信息
            OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(map.getKey());
            OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                    scooterBom.getColorId());

            // 这里直接get(0)是因为这里list里面只会有一条数据,ros那边创建入库单的时候就有限制,入库单相同产品不能有重复的
            qty = map.getValue().get(0).getActInWhQty();
            opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() + qty);
            opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - qty);
            opeWmsScooterStock.setUpdatedBy(userId);
            opeWmsScooterStock.setUpdatedTime(new Date());
            opeWmsScooterStockList.add(opeWmsScooterStock);

            // 入库记录
            opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsScooterStock.getId(), WmsTypeEnum.SCOOTER_WAREHOUSE.getType(),
                    InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, userId));

            // 需要找到入库单关联组装单

        }

        /**
         * 保存车辆信息至scooter表, ecu表数据会通过车辆上报保存至数据库
         */
        scooterService.syncScooterData(buildScooter(null, null, userId));

        /**
         * 修改车辆成品库库存信息、添加入库单记录
         */
        wmsScooterStockMapper.batchUpdateWmsScooterStock(opeWmsScooterStockList);
        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存组装件成品库信息
     * @param inWhOrderProductMap
     * @param userId
     */
    private void saveWmsCombinationStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, Long userId) {
        // 公共参数：组装件成品库集合、出入库记录集合
        List<OpeWmsCombinStock> opeWmsCombinStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 组装件入成品库数据
         */
        for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
            qty = map.getValue().get(0).getActInWhQty();
            // 组装件成品库信息
            OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(map.getKey());
            opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + qty);
            opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - qty);
            opeWmsCombinStockList.add(opeWmsCombinStock);

            // 入库记录
            opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsCombinStock.getId(), WmsTypeEnum.COMBINATION_WAREHOUSE.getType(),
                    InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, userId));
        }

        /**
         * 修改组装件成品库库存信息、添加入库单记录
         */
        wmsCombinStockMapper.batchUpdateWmsCombinStock(opeWmsCombinStockList);
        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存部件原料库信息
     * @param inWhOrderProductMap
     * @param userId
     */
    private void saveWmsPartsStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, Long userId) {
        // 公共参数：部件成品库集合、出入库记录集合
        List<OpeWmsPartsStock> opeWmsPartsStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 部件入原料库数据
         */
        for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
            qty = map.getValue().get(0).getActInWhQty();
            // 组装件成品库信息
            OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(map.getKey());
            opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + qty);
            opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - qty);
            opeWmsPartsStockList.add(opeWmsPartsStock);

            // 入库记录
            opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsPartsStock.getId(), WmsTypeEnum.PARTS_WAREHOUSE.getType(),
                    InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, userId));
        }

        /**
         * 修改部件原料库库存信息、添加入库单记录
         */
        wmsPartsStockMapper.batchUpdateWmsPartsStock(opeWmsPartsStockList);
        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 组装出入库记录信息
     * @param relationId 关联仓库id
     * @param relationType 关联类型, 可参考：{@link WmsTypeEnum}
     * @param inWhType 入库类型, 可参考：{@link InWhTypeEnums}
     * @param qty 入库数量
     * @param userId 用户id
     * @return
     */
    private OpeWmsStockRecord buildWmsStockRecord(Long relationId, Integer relationType, Integer inWhType,
                                                  Integer qty, Long userId) {
        OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
        opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
        opeWmsStockRecord.setRelationId(relationId);
        opeWmsStockRecord.setRelationType(relationType);
        opeWmsStockRecord.setInWhType(inWhType);
        opeWmsStockRecord.setInWhQty(qty);
        opeWmsStockRecord.setRecordType(Integer.valueOf(InOutWhEnums.IN.getValue()));
        opeWmsStockRecord.setStockType(1); // 默认中国仓库
        opeWmsStockRecord.setCreatedBy(userId);
        opeWmsStockRecord.setCreatedTime(new Date());
        opeWmsStockRecord.setUpdatedBy(userId);
        opeWmsStockRecord.setUpdatedTime(new Date());

        return opeWmsStockRecord;
    }

    private OpeInWhouseOrderSerialBind buildInWhOrderSerialBind(Long orderBId, Integer orderType, String serialNum, String lot,
                                                                Long bomId, Integer qty, String bluetoothMacAddress) {
        OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBind = new OpeInWhouseOrderSerialBind();
        opeInWhouseOrderSerialBind.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER_SERIAL_BIND));
        opeInWhouseOrderSerialBind.setOrderBId(orderBId);
        opeInWhouseOrderSerialBind.setOrderType(orderType);
        opeInWhouseOrderSerialBind.setSerialNum(serialNum);
        opeInWhouseOrderSerialBind.setLot(lot);
        opeInWhouseOrderSerialBind.setProductId(bomId);
        opeInWhouseOrderSerialBind.setProductType(orderType);
        opeInWhouseOrderSerialBind.setQty(qty);
        opeInWhouseOrderSerialBind.setBluetoothMacAddress(bluetoothMacAddress);
        opeInWhouseOrderSerialBind.setCreatedBy(null);
        opeInWhouseOrderSerialBind.setCreatedTime(new Date());
        opeInWhouseOrderSerialBind.setUpdatedBy(null);
        opeInWhouseOrderSerialBind.setUpdatedTime(new Date());

        return opeInWhouseOrderSerialBind;
    }

}
