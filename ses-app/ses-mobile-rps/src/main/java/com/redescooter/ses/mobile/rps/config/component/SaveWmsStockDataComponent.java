package com.redescooter.ses.mobile.rps.config.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockStatusEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnum;
import com.redescooter.ses.api.common.enums.wms.WmsTypeEnum;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderSerialBindService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存库存信息公共组件
 * @author assert
 * @date 2021/1/25 13:22
 */
@Component
public class SaveWmsStockDataComponent {

    @DubboReference
    private IdAppService idAppService;
    @DubboReference
    private ScooterService scooterService;
    @Resource
    private ProductionScooterBomMapper scooterBomMapper;
    @Resource
    private ProductionCombinBomMapper combinBomMapper;
    @Resource
    private ProductionPartsMapper partsMapper;
    @Resource
    private WmsScooterStockMapper wmsScooterStockMapper;
    @Resource
    private WmsCombinStockMapper wmsCombinStockMapper;
    @Resource
    private WmsPartsStockMapper wmsPartsStockMapper;
    @Resource
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;
    @Resource
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;

    @Autowired
    private OpeInWhouseOrderSerialBindService opeInWhouseOrderSerialBindService;


    /**
     * 保存车辆成品库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param outWhOrderProductMap 出库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号信息
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public void saveWmsScooterStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                        Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap, String type, Long userId) {
        // 公共参数：新增车辆成品库集合、修改车辆成品库集合、出入库记录集合
        List<OpeWmsScooterStock> insertWmsScooterStockList = new ArrayList<>();
        List<OpeWmsScooterStock> updateWmsScooterStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 车辆成品库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
            /**
             * {bomId, stockId}
             */
            Map<Long, Long> stockMap = new HashMap<>();

            for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
                // 这里直接get(0)是因为这里list里面只会有一条数据,ros那边创建入库单的时候就有限制,入库单相同产品不能有重复的
                qty = map.getValue().get(0).getActInWhQty();
                // 车辆成品库信息
                OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(map.getKey());
                OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                        scooterBom.getColorId());
                /**
                 * 第一次入库仓库中是没有这辆车的,需要把这辆车添加到车辆成品库里面去
                 */
                if (null == opeWmsScooterStock) {
                    // 不存在,insert
                    opeWmsScooterStock = buildWmsScooterStock(scooterBom.getGroupId(), scooterBom.getColorId(), qty, userId);
                    insertWmsScooterStockList.add(opeWmsScooterStock);
                } else {
                    opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() + qty);
                    opeWmsScooterStock.setWaitInStockQty(opeWmsScooterStock.getWaitInStockQty() - qty);
                    opeWmsScooterStock.setUpdatedBy(userId);
                    opeWmsScooterStock.setUpdatedTime(new Date());
                    // 存在,update
                    updateWmsScooterStockList.add(opeWmsScooterStock);
                }
                // 入库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsScooterStock.getId(), WmsTypeEnum.SCOOTER_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(),userId));
                // put
                stockMap.put(map.getKey(), opeWmsScooterStock.getId());
            }

            /**
             * 保存车辆信息至scooter表, ecu表数据会通过车辆上报保存至数据库
             */
            int count = scooterService.countByScooter();
            List<SyncScooterDataDTO> scooterDataDTOList = new ArrayList<>();
            List<OpeInWhouseOrderSerialBind> serialBindList = new ArrayList<>();
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

                // 入库之后 将ope_in_whouse_order_serial_bind 的序列号和平板序列号 同步  2021 3 31
                QueryWrapper<OpeInWhouseOrderSerialBind> qw = new QueryWrapper<>();
                qw.eq(OpeInWhouseOrderSerialBind.COL_SERIAL_NUM,inWhSn.getSerialNum());
                qw.last("limit 1");
                OpeInWhouseOrderSerialBind orderSerialBind = opeInWhouseOrderSerialBindService.getOne(qw);
                if (orderSerialBind != null) {
                    orderSerialBind.setSerialNum(inWhSn.getSerialNum() + count);
                    orderSerialBind.setDefaultSerialNum(inWhSn.getTabletSn());
                    serialBindList.add(orderSerialBind);
                }
                inWhSn.setSerialNum(inWhSn.getSerialNum() + count);
            }
            scooterService.syncScooterData(scooterDataDTOList);
            opeInWhouseOrderSerialBindService.updateBatch(serialBindList);
            /**
             * 保存库存产品序列号信息
             */
            wmsStockSerialNumberMapper.batchInsertWmsStockSerialNumber(buildOpeWmsStockSerialNumber(inWhouseOrderSerialBinds, stockMap, userId));

        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                // 车辆成品库信息
                OpeProductionScooterBom scooterBom = scooterBomMapper.getScooterBomById(map.getKey());
                // 正常流程下来产品出库操作的时候肯定会存在仓库中,如果没有说明前面流程走的有问题,这里报错也方便后面找问题
                OpeWmsScooterStock opeWmsScooterStock = wmsScooterStockMapper.getWmsScooterStockByGroupIdAndColorId(scooterBom.getGroupId(),
                        scooterBom.getColorId());

                qty = map.getValue().get(0).getAlreadyOutWhQty();
                // 库存校验,检查库存是否足够
                RpsAssert.isTrue(qty > opeWmsScooterStock.getAbleStockQty(), ExceptionCodeEnums.INVENTORY_SHORTAGE.getCode(),
                        ExceptionCodeEnums.INVENTORY_SHORTAGE.getMessage());

                opeWmsScooterStock.setAbleStockQty(opeWmsScooterStock.getAbleStockQty() - qty);
                opeWmsScooterStock.setUsedStockQty(opeWmsScooterStock.getUsedStockQty() + qty);
                opeWmsScooterStock.setWaitOutStockQty(opeWmsScooterStock.getWaitOutStockQty() - qty);
                opeWmsScooterStock.setUpdatedBy(userId);
                opeWmsScooterStock.setUpdatedTime(new Date());
                updateWmsScooterStockList.add(opeWmsScooterStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsScooterStock.getId(), WmsTypeEnum.SCOOTER_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.OUT.getValue(), userId));
            }
        }

        /**
         * 新增/修改车辆成品库库存信息、添加入库单记录
         */
        if (CollectionUtils.isNotEmpty(insertWmsScooterStockList)) {
            wmsScooterStockMapper.batchInsertWmsScooterStock(insertWmsScooterStockList);
        }
        if (CollectionUtils.isNotEmpty(updateWmsScooterStockList)) {
            wmsScooterStockMapper.batchUpdateWmsScooterStock(updateWmsScooterStockList);
        }

        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存组装件成品库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号
     * @param outWhOrderProductMap 出库单产品map
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     */
    public void saveWmsCombinationStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap,List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                            Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap,String type, Long userId) {
        // 公共参数：新增组装件成品库集合、修改组装件成品库集合、出入库记录集合
        List<OpeWmsCombinStock> insertWmsCombinStockList = new ArrayList<>();
        List<OpeWmsCombinStock> updateWmsCombinStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 组装件成品库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
            /**
             * {bomId, stockId}
             */
            Map<Long, Long> stockMap = new HashMap<>();

            for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getActInWhQty();
                // 组装件成品库信息
                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(map.getKey());

                /**
                 * 第一次入库仓库中是没有这个组装件的,需要把这个组装件添加到组装件成品库里面去
                 */
                if (null == opeWmsCombinStock) {
                    opeWmsCombinStock = buildWmsCombinationStock(map.getKey(), qty, userId);
                    insertWmsCombinStockList.add(opeWmsCombinStock);
                } else {
                    opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() + qty);
                    opeWmsCombinStock.setWaitInStockQty(opeWmsCombinStock.getWaitInStockQty() - qty);
                    opeWmsCombinStock.setUpdatedBy(userId);
                    opeWmsCombinStock.setUpdatedTime(new Date());
                    updateWmsCombinStockList.add(opeWmsCombinStock);
                }
                // 入库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsCombinStock.getId(), WmsTypeEnum.COMBINATION_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(),userId));
                // put
                stockMap.put(map.getKey(), opeWmsCombinStock.getId());
            }

            /**
             * 保存库存产品序列号信息
             */
            wmsStockSerialNumberMapper.batchInsertWmsStockSerialNumber(buildOpeWmsStockSerialNumber(inWhouseOrderSerialBinds, stockMap, userId));
        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getAlreadyOutWhQty();
                // 组装件成品库信息
                OpeWmsCombinStock opeWmsCombinStock = wmsCombinStockMapper.getWmsCombinStockByBomId(map.getKey());
                // 库存校验,检查库存是否足够
                RpsAssert.isTrue(qty > opeWmsCombinStock.getAbleStockQty(), ExceptionCodeEnums.INVENTORY_SHORTAGE.getCode(),
                        ExceptionCodeEnums.INVENTORY_SHORTAGE.getMessage());

                opeWmsCombinStock.setAbleStockQty(opeWmsCombinStock.getAbleStockQty() - qty);
                opeWmsCombinStock.setUsedStockQty(opeWmsCombinStock.getUsedStockQty() + qty);
                opeWmsCombinStock.setWaitOutStockQty(opeWmsCombinStock.getWaitOutStockQty() - qty);
                opeWmsCombinStock.setUpdatedBy(userId);
                opeWmsCombinStock.setUpdatedTime(new Date());
                updateWmsCombinStockList.add(opeWmsCombinStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsCombinStock.getId(), WmsTypeEnum.COMBINATION_WAREHOUSE.getType(),
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.OUT.getValue(),userId));
            }
        }

        /**
         * 新增/修改组装件成品库库存信息、添加入库单记录
         */
        if (CollectionUtils.isNotEmpty(insertWmsCombinStockList)) {
            wmsCombinStockMapper.batchInsertWmsCombinStock(insertWmsCombinStockList);
        }
        if (CollectionUtils.isNotEmpty(updateWmsCombinStockList)) {
            wmsCombinStockMapper.batchUpdateWmsCombinStock(updateWmsCombinStockList);
        }

        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 保存部件原料库信息
     * @param inWhOrderProductMap 入库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号
     * @param outWhOrderProductMap 出库单产品map
     * @param type 出入库类型 1入库 2出库
     * @param userId 用户id
     */
    public void saveWmsPartsStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap,List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                      Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap,String type, Long userId) {
        // 公共参数：新增部件原料库集合、修改部件原料库集合、出入库记录集合
        List<OpeWmsPartsStock> insertWmsPartsStockList = new ArrayList<>();
        List<OpeWmsPartsStock> updateWmsPartsStockList = new ArrayList<>();
        List<OpeWmsStockRecord> opeWmsStockRecordList = new ArrayList<>();
        int qty = 0;

        /**
         * 部件原料库操作 1入库 2出库
         */
        if (InOutWhEnums.IN.getValue().equals(type)) {
            /**
             * {bomId, stockId}
             */
            Map<Long, Long> stockMap = new HashMap<>();

            for (Map.Entry<Long, List<InWhOrderProductDTO>> map : inWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getActInWhQty();
                // 部件原料库信息
                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(map.getKey());

                /**
                 * 第一次入库仓库中是没有这个部件的,需要把这个部件添加到部件原料库里面去
                 */
                if (null == opeWmsPartsStock) {
                    opeWmsPartsStock = buildWmsPartsStock(map.getKey(), qty, userId);
                    insertWmsPartsStockList.add(opeWmsPartsStock);
                } else {
                    opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() + qty);
                    opeWmsPartsStock.setWaitInStockQty(opeWmsPartsStock.getWaitInStockQty() - qty);
                    opeWmsPartsStock.setUpdatedBy(userId);
                    opeWmsPartsStock.setUpdatedTime(new Date());
                    updateWmsPartsStockList.add(opeWmsPartsStock);
                }
                // 入库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsPartsStock.getId(), WmsTypeEnum.PARTS_WAREHOUSE.getType(),
                        InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(),userId));

                // put
                stockMap.put(map.getKey(), opeWmsPartsStock.getId());
            }

            /**
             * 保存库存产品序列号信息
             */
            wmsStockSerialNumberMapper.batchInsertWmsStockSerialNumber(buildOpeWmsStockSerialNumber(inWhouseOrderSerialBinds, stockMap, userId));
        } else {
            for (Map.Entry<Long, List<OutWarehouseOrderProductDTO>> map : outWhOrderProductMap.entrySet()) {
                qty = map.getValue().get(0).getAlreadyOutWhQty();
                // 部件原料库信息
                OpeWmsPartsStock opeWmsPartsStock = wmsPartsStockMapper.getWmsPartsStockByBomId(map.getKey());
                // 库存校验,检查库存是否足够
                RpsAssert.isTrue(qty > opeWmsPartsStock.getAbleStockQty(), ExceptionCodeEnums.INVENTORY_SHORTAGE.getCode(),
                        ExceptionCodeEnums.INVENTORY_SHORTAGE.getMessage());

                opeWmsPartsStock.setAbleStockQty(opeWmsPartsStock.getAbleStockQty() - qty);
                opeWmsPartsStock.setUsedStockQty(opeWmsPartsStock.getUsedStockQty() + qty);
                opeWmsPartsStock.setWaitOutStockQty(opeWmsPartsStock.getWaitOutStockQty() - qty);
                opeWmsPartsStock.setUpdatedBy(userId);
                opeWmsPartsStock.setUpdatedTime(new Date());
                updateWmsPartsStockList.add(opeWmsPartsStock);

                // 出库记录
                opeWmsStockRecordList.add(buildWmsStockRecord(opeWmsPartsStock.getId(), WmsTypeEnum.PARTS_WAREHOUSE.getType(),
                        InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, InOutWhEnums.OUT.getValue(), userId));
            }
        }

        /**
         * 新增/修改部件原料库库存信息、添加入库单记录
         */
        if (CollectionUtils.isNotEmpty(insertWmsPartsStockList)) {
            wmsPartsStockMapper.batchInsertWmsPartsStock(insertWmsPartsStockList);
        }
        if (CollectionUtils.isNotEmpty(updateWmsPartsStockList)) {
            wmsPartsStockMapper.batchUpdateWmsPartsStock(updateWmsPartsStockList);
        }

        opeWmsStockRecordMapper.batchInsert(opeWmsStockRecordList);
    }

    /**
     * 组装出入库记录信息
     * @param relationId 关联仓库id
     * @param relationType 关联类型, 可参考：{@link WmsTypeEnum}
     * @param inWhType 入库类型, 可参考：{@link InWhTypeEnums}
     * @param qty 入库数量
     * @param inOutType 出入库类型 1入库 2出库
     * @param userId 用户id
     * @return
     */
    public OpeWmsStockRecord buildWmsStockRecord(Long relationId, Integer relationType, Integer inWhType,
                                                  Integer qty, String inOutType, Long userId) {
        OpeWmsStockRecord opeWmsStockRecord = new OpeWmsStockRecord();
        opeWmsStockRecord.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
        opeWmsStockRecord.setDr(0);
        opeWmsStockRecord.setRelationId(relationId);
        opeWmsStockRecord.setRelationType(relationType);
        opeWmsStockRecord.setInWhType(inWhType);
        opeWmsStockRecord.setInWhQty(qty);
        opeWmsStockRecord.setRecordType(Integer.valueOf(inOutType));
        opeWmsStockRecord.setStockType(1); // 默认中国仓库
        opeWmsStockRecord.setCreatedBy(userId);
        opeWmsStockRecord.setCreatedTime(new Date());
        opeWmsStockRecord.setUpdatedBy(userId);
        opeWmsStockRecord.setUpdatedTime(new Date());

        return opeWmsStockRecord;
    }

    /**
     * 组装成品库车辆库存信息
     * @param groupId 车辆分组id
     * @param colorId 车辆颜色id
     * @param qty 出入库数量
     * @param userId 用户id
     * @return
     */
    private OpeWmsScooterStock buildWmsScooterStock(Long groupId, Long colorId, Integer qty, Long userId) {
        OpeWmsScooterStock opeWmsScooterStock = new OpeWmsScooterStock();
        opeWmsScooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
        opeWmsScooterStock.setDr(0);
        opeWmsScooterStock.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());
        opeWmsScooterStock.setGroupId(groupId);
        opeWmsScooterStock.setColorId(colorId);
        opeWmsScooterStock.setAbleStockQty(qty);
        opeWmsScooterStock.setUsedStockQty(0);
        opeWmsScooterStock.setWaitInStockQty(0);
        opeWmsScooterStock.setWaitOutStockQty(0);
        opeWmsScooterStock.setCreatedBy(userId);
        opeWmsScooterStock.setCreatedTime(new Date());
        opeWmsScooterStock.setUpdatedBy(userId);
        opeWmsScooterStock.setUpdatedTime(new Date());

        return opeWmsScooterStock;
    }

    /**
     * 组装成品库组装件库存信息
     * @param bomId 组装件bomId
     * @param qty 出入库数量
     * @param userId 用户id
     * @return
     */
    private OpeWmsCombinStock buildWmsCombinationStock(Long bomId, Integer qty, Long userId) {
        OpeWmsCombinStock opeWmsCombinStock = new OpeWmsCombinStock();
        opeWmsCombinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
        opeWmsCombinStock.setDr(0);
        opeWmsCombinStock.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());

        OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(bomId);
        RpsAssert.isNull(opeProductionCombinBom, ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getMessage());
        opeWmsCombinStock.setProductionCombinBomId(bomId);
        opeWmsCombinStock.setCombinNo(opeProductionCombinBom.getBomNo());
        opeWmsCombinStock.setCnName(opeProductionCombinBom.getCnName());
        opeWmsCombinStock.setEnName(opeProductionCombinBom.getEnName());
        opeWmsCombinStock.setFrName(opeProductionCombinBom.getFrName());
        opeWmsCombinStock.setAbleStockQty(qty);
        opeWmsCombinStock.setUsedStockQty(0);
        opeWmsCombinStock.setWaitInStockQty(0);
        opeWmsCombinStock.setWaitOutStockQty(0);
        opeWmsCombinStock.setCreatedBy(userId);
        opeWmsCombinStock.setCreatedTime(new Date());
        opeWmsCombinStock.setUpdatedBy(userId);
        opeWmsCombinStock.setUpdatedTime(new Date());

        return opeWmsCombinStock;
    }

    /**
     * 组装成品库部件库存信息
     * @param bomId 部件bomId
     * @param qty 出入库数量
     * @param userId 用户id
     * @return
     */
    private OpeWmsPartsStock buildWmsPartsStock(Long bomId, Integer qty, Long userId) {
        OpeWmsPartsStock opeWmsPartsStock = new OpeWmsPartsStock();
        opeWmsPartsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
        opeWmsPartsStock.setDr(0);
        opeWmsPartsStock.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());

        OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByBomId(bomId);
        RpsAssert.isNull(opeProductionParts, ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getMessage());
        opeWmsPartsStock.setPartsId(bomId);
        opeWmsPartsStock.setPartsNo(opeProductionParts.getPartsNo());
        opeWmsPartsStock.setPartsType(opeProductionParts.getPartsType());
        opeWmsPartsStock.setCnName(opeProductionParts.getCnName());
        opeWmsPartsStock.setEnName(opeProductionParts.getEnName());
        opeWmsPartsStock.setFrName(opeProductionParts.getFrName());
        opeWmsPartsStock.setAbleStockQty(qty);
        opeWmsPartsStock.setUsedStockQty(0);
        opeWmsPartsStock.setWaitInStockQty(0);
        opeWmsPartsStock.setWaitOutStockQty(0);
        opeWmsPartsStock.setCreatedBy(userId);
        opeWmsPartsStock.setCreatedTime(new Date());
        opeWmsPartsStock.setUpdatedBy(userId);
        opeWmsPartsStock.setUpdatedTime(new Date());

        return opeWmsPartsStock;
    }

    /**
     * 组装库存产品序列号信息
     * @param opeInWhouseOrderSerialBinds 入库产品序列号集合
     * @param stockMap 仓库id map集合
     * @param userId 用户id
     * @return
     */
    private List<OpeWmsStockSerialNumber> buildOpeWmsStockSerialNumber(List<OpeInWhouseOrderSerialBind> opeInWhouseOrderSerialBinds,
                                                                       Map<Long, Long> stockMap,
                                                                       Long userId) {
        List<OpeWmsStockSerialNumber> opeWmsStockSerialNumberList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(opeInWhouseOrderSerialBinds)) {
            opeInWhouseOrderSerialBinds.forEach(serial -> {
                OpeWmsStockSerialNumber opeWmsStockSerialNumber = new OpeWmsStockSerialNumber();
                opeWmsStockSerialNumber.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
                opeWmsStockSerialNumber.setDr(0);
                opeWmsStockSerialNumber.setRelationId(stockMap.get(serial.getProductId()));
                opeWmsStockSerialNumber.setRelationType(serial.getOrderType());
                opeWmsStockSerialNumber.setStockType(WmsStockTypeEnum.CHINA_WAREHOUSE.getType());
                opeWmsStockSerialNumber.setRsn(serial.getSerialNum());
                opeWmsStockSerialNumber.setStockStatus(WmsStockStatusEnum.AVAILABLE.getStatus());
                opeWmsStockSerialNumber.setLotNum(serial.getLot());
                opeWmsStockSerialNumber.setSn(serial.getDefaultSerialNum());
                opeWmsStockSerialNumber.setBluetoothMacAddress(serial.getBluetoothMacAddress());
                opeWmsStockSerialNumber.setCreatedBy(userId);
                opeWmsStockSerialNumber.setCreatedTime(new Date());
                opeWmsStockSerialNumber.setUpdatedBy(userId);
                opeWmsStockSerialNumber.setUpdatedTime(new Date());

                opeWmsStockSerialNumberList.add(opeWmsStockSerialNumber);
            });
        }

        return opeWmsStockSerialNumberList;
    }

    /**
     * 获取仓库id
     * @param productType
     * @param bomId
     * @return
     */
    private Long getWmsStockId(Integer productType, Long bomId) {
        Long stockId = null;

        /**
         * 仓库类型 1车辆成品库 2组装件成品库 3部件原料库
         */
        switch (productType) {
            case 1:
                stockId = wmsScooterStockMapper.getWmsScooterStockIdByBomId(bomId);
                RpsAssert.isNull(stockId, ExceptionCodeEnums.SCOOTER_BOM_IS_NOT_EXISTS.getCode(),
                        ExceptionCodeEnums.SCOOTER_BOM_IS_NOT_EXISTS.getMessage());
                break;
            case 2:
                stockId = wmsCombinStockMapper.getWmsCombinStockIdByBomId(bomId);
                RpsAssert.isNull(stockId, ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getCode(),
                        ExceptionCodeEnums.COMBINATION_BOM_IS_NOT_EXISTS.getMessage());
                break;
            default:
                stockId = wmsPartsStockMapper.getWmsPartsStockIdByBomId(bomId);
                RpsAssert.isNull(stockId, ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getCode(),
                        ExceptionCodeEnums.PARTS_BOM_IS_NOT_EXISTS.getMessage());
                break;
        }

        return stockId;
    }

}
