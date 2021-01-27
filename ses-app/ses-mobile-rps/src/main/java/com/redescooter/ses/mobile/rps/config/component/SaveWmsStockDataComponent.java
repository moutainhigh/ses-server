package com.redescooter.ses.mobile.rps.config.component;

import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保存库存信息公共组件
 * @author assert
 * @date 2021/1/25 13:22
 */
@Component
public class SaveWmsStockDataComponent {

    @Reference
    private IdAppService idAppService;
    @Reference
    private ScooterService scooterService;
    @Resource
    private ProductionScooterBomMapper scooterBomMapper;
    @Resource
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Resource
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Resource
    private WmsPartsStockMapper wmsPartsStockMapper;
    @Resource
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;


    /**
     * 保存车辆成品库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param outWhOrderProductMap 出库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号信息
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     * @return
     */
    public void saveWmsScooterStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                        Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap, String type, Long userId) {
        // 公共参数：车辆成品库、出入库记录
        List<OpeWmsScooterStock> opeWmsScooterStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 车辆成品库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
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
            }

            /**
             * 保存车辆信息至scooter表, ecu表数据会通过车辆上报保存至数据库
             */
            int count = scooterService.countByScooter();
            List<SyncScooterDataDTO> scooterDataDTOList = new ArrayList<>();
            for (OpeInWhouseOrderSerialBind inWhSn : inWhouseOrderSerialBinds) {
                count += 1;
                SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
                // 补充车辆生产流水号
                syncScooterData.setScooterNo(inWhSn.getSerialNum() + count);
                syncScooterData.setTabletSn(inWhSn.getTabletSn());
                // 车辆入库默认型号是E50
                syncScooterData.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
                syncScooterData.setUserId(userId);
                scooterDataDTOList.add(syncScooterData);
            }
            scooterService.syncScooterData(scooterDataDTOList);

        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                // 车辆成品库信息
                OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(map.getKey());
                OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                        scooterBom.getColorId());

                qty = map.getValue().get(0).getAlreadyOutWhQty();
                opeWmsScooterStock.setUsedStockQty(opeWmsScooterStock.getUsedStockQty() + qty);
                opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - qty);
                opeWmsScooterStock.setUpdatedBy(userId);
                opeWmsScooterStock.setUpdatedTime(new Date());
                opeWmsScooterStockList.add(opeWmsScooterStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsScooterStock.getId(), WmsTypeEnum.SCOOTER_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, userId));
            }
        }

        /**
         * 修改车辆成品库库存信息、添加入库单记录
         */
        wmsScooterStockMapper.batchUpdateWmsScooterStock(opeWmsScooterStockList);
        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存组装件成品库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param outWhOrderProductMap 出库单产品map
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     */
    public void saveWmsCombinationStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap,
                                            Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap,String type, Long userId) {
        // 公共参数：组装件成品库集合、出入库记录集合
        List<OpeWmsCombinStock> opeWmsCombinStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 组装件成品库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
            for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getActInWhQty();
                // 组装件成品库信息
                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(map.getKey());
                opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + qty);
                opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - qty);
                opeWmsCombinStock.setUpdatedBy(userId);
                opeWmsCombinStock.setUpdatedTime(new Date());
                opeWmsCombinStockList.add(opeWmsCombinStock);

                // 入库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsCombinStock.getId(), WmsTypeEnum.COMBINATION_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, userId));
            }
        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getAlreadyOutWhQty();
                // 组装件成品库信息
                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(map.getKey());
                opeWmsCombinStock.setUsedStockQty(opeWmsCombinStock.getUsedStockQty() + qty);
                opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - qty);
                opeWmsCombinStock.setUpdatedBy(userId);
                opeWmsCombinStock.setUpdatedTime(new Date());
                opeWmsCombinStockList.add(opeWmsCombinStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsCombinStock.getId(), WmsTypeEnum.COMBINATION_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, userId));
            }
        }

        /**
         * 修改组装件成品库库存信息、添加入库单记录
         */
        wmsCombinStockMapper.batchUpdateWmsCombinStock(opeWmsCombinStockList);
        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存部件原料库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param outWhOrderProductMap 出库单产品map
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     */
    public void saveWmsPartsStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap,
                                      Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap,String type, Long userId) {
        // 公共参数：部件成品库集合、出入库记录集合
        List<OpeWmsPartsStock> opeWmsPartsStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 部件原料库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
            for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getActInWhQty();
                // 部件原料库信息
                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(map.getKey());
                opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + qty);
                opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - qty);
                opeWmsPartsStock.setUpdatedBy(userId);
                opeWmsPartsStock.setUpdatedTime(new Date());
                opeWmsPartsStockList.add(opeWmsPartsStock);

                // 入库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsPartsStock.getId(), WmsTypeEnum.PARTS_WAREHOUSE.getType(),
                        InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, userId));
            }
        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getAlreadyOutWhQty();
                // 部件原料库信息
                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(map.getKey());
                opeWmsPartsStock.setUsedStockQty(opeWmsPartsStock.getUsedStockQty() + qty);
                opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - qty);
                opeWmsPartsStock.setUpdatedBy(userId);
                opeWmsPartsStock.setUpdatedTime(new Date());
                opeWmsPartsStockList.add(opeWmsPartsStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsPartsStock.getId(), WmsTypeEnum.PARTS_WAREHOUSE.getType(),
                        InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, userId));
            }
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
    public OpeWmsStockRecord buildWmsStockRecord(Long relationId, Integer relationType, Integer inWhType,
                                                  Integer qty, Long userId) {
        OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
        opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
        opeWmsStockRecord.setDr(0);
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

}
