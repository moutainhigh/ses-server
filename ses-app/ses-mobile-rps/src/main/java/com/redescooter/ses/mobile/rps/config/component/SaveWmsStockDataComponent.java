package com.redescooter.ses.mobile.rps.config.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.assign.FactoryEnum;
import com.redescooter.ses.api.common.enums.assign.ProductTypeEnum;
import com.redescooter.ses.api.common.enums.assign.ScooterTypeEnum;
import com.redescooter.ses.api.common.enums.assign.YearEnum;
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
import com.redescooter.ses.mobile.rps.dao.base.OpeCarDistributeMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCarDistribute;
import com.redescooter.ses.mobile.rps.dm.OpeCodebaseRelation;
import com.redescooter.ses.mobile.rps.dm.OpeCodebaseVin;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom;
import com.redescooter.ses.mobile.rps.dm.OpeSpecificatType;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.mobile.rps.service.base.OpeCodebaseVinService;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderSerialBindService;
import com.redescooter.ses.mobile.rps.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderProductDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存库存信息公共组件
 *
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

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseRelationService opeCodebaseRelationService;

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;


    /**
     * 保存车辆成品库信息
     *
     * @param inWhOrderProductMap      入库单产品map
     * @param outWhOrderProductMap     出库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号信息
     * @param type                     出入库类型 1入库 2出库
     * @param userId                   用户id
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
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(), userId));
                // put
                stockMap.put(map.getKey(), opeWmsScooterStock.getId());
            }

            /**
             * todo 保存车辆信息至scooter表, ecu表数据会通过车辆上报保存至数据库
             */
            //int count = scooterService.countByScooter();
            List<SyncScooterDataDTO> scooterDataDTOList = new ArrayList<>();
            List<OpeInWhouseOrderSerialBind> serialBindList = new ArrayList<>();
            for (OpeInWhouseOrderSerialBind inWhSn : inWhouseOrderSerialBinds) {
                //count += 1;
                SyncScooterDataDTO syncScooterData = new SyncScooterDataDTO();
                // 补充车辆生产流水号
                //syncScooterData.setScooterNo(inWhSn.getSerialNum() + count);
                syncScooterData.setScooterNo(inWhSn.getSerialNum());
                syncScooterData.setTabletSn(inWhSn.getTabletSn());
                syncScooterData.setBluetoothMacAddress(inWhSn.getBluetoothMacAddress());
                // 车辆入库默认型号是E50
                syncScooterData.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
                syncScooterData.setUserId(userId);
                scooterDataDTOList.add(syncScooterData);

                // 入库之后 将ope_in_whouse_order_serial_bind 的序列号和平板序列号 同步  2021 3 31
                QueryWrapper<OpeInWhouseOrderSerialBind> qw = new QueryWrapper<>();
                qw.eq(OpeInWhouseOrderSerialBind.COL_SERIAL_NUM, inWhSn.getSerialNum());
                qw.last("limit 1");
                OpeInWhouseOrderSerialBind orderSerialBind = opeInWhouseOrderSerialBindService.getOne(qw);
                if (orderSerialBind != null) {
                    //orderSerialBind.setSerialNum(inWhSn.getSerialNum() + count);
                    orderSerialBind.setSerialNum(inWhSn.getSerialNum());
                    orderSerialBind.setDefaultSerialNum(inWhSn.getTabletSn());
                    serialBindList.add(orderSerialBind);
                }
                //inWhSn.setSerialNum(inWhSn.getSerialNum() + count);
                inWhSn.setSerialNum(inWhSn.getSerialNum());
                inWhSn.setLot(inWhSn.getLot());
                inWhSn.setBluetoothMacAddress(inWhSn.getBluetoothMacAddress());
            }
            scooterService.syncScooterData(scooterDataDTOList);
            opeInWhouseOrderSerialBindService.updateBatchById(serialBindList);

            /**
             * 保存库存产品序列号信息
             */
            wmsStockSerialNumberMapper.batchInsertWmsStockSerialNumber(buildOpeWmsStockSerialNumber(inWhouseOrderSerialBinds, stockMap, userId));

            // 产生4条vin,保存到码库 E50一座 E50两座 E100一座 E100两座
            List<OpeCodebaseVin> vinList = Lists.newArrayList();

            LambdaQueryWrapper<OpeSpecificatType> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeSpecificatType::getDr, Constant.DR_FALSE);
            qw.eq(OpeSpecificatType::getSpecificatName, ProductTypeEnum.E50.getMsg());
            qw.last("limit 1");
            OpeSpecificatType typeE50 = opeSpecificatTypeService.getOne(qw);
            String vin1 = generateVINCode(typeE50.getId(), typeE50.getSpecificatName(), 1);
            String vin2 = generateVINCode(typeE50.getId(), typeE50.getSpecificatName(), 2);

            LambdaQueryWrapper<OpeSpecificatType> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeSpecificatType::getDr, Constant.DR_FALSE);
            lqw.eq(OpeSpecificatType::getSpecificatName, ProductTypeEnum.E100.getMsg());
            lqw.last("limit 1");
            OpeSpecificatType typeE100 = opeSpecificatTypeService.getOne(lqw);
            String vin3 = generateVINCode(typeE100.getId(), typeE100.getSpecificatName(), 1);
            String vin4 = generateVINCode(typeE100.getId(), typeE100.getSpecificatName(), 2);

            OpeCodebaseVin vin1Model = new OpeCodebaseVin();
            vin1Model.setId(idAppService.getId(SequenceName.OPE_CODEBASE_VIN));
            vin1Model.setDr(Constant.DR_FALSE);
            vin1Model.setVin(vin1);
            vin1Model.setStatus(1);
            vin1Model.setCreatedBy(userId);
            vin1Model.setCreatedTime(new Date());

            OpeCodebaseVin vin2Model = new OpeCodebaseVin();
            vin2Model.setId(idAppService.getId(SequenceName.OPE_CODEBASE_VIN));
            vin2Model.setDr(Constant.DR_FALSE);
            vin2Model.setVin(vin2);
            vin2Model.setStatus(1);
            vin2Model.setCreatedBy(userId);
            vin2Model.setCreatedTime(new Date());

            OpeCodebaseVin vin3Model = new OpeCodebaseVin();
            vin3Model.setId(idAppService.getId(SequenceName.OPE_CODEBASE_VIN));
            vin3Model.setDr(Constant.DR_FALSE);
            vin3Model.setVin(vin3);
            vin3Model.setStatus(1);
            vin3Model.setCreatedBy(userId);
            vin3Model.setCreatedTime(new Date());

            OpeCodebaseVin vin4Model = new OpeCodebaseVin();
            vin4Model.setId(idAppService.getId(SequenceName.OPE_CODEBASE_VIN));
            vin4Model.setDr(Constant.DR_FALSE);
            vin4Model.setVin(vin4);
            vin4Model.setStatus(1);
            vin4Model.setCreatedBy(userId);
            vin4Model.setCreatedTime(new Date());

            vinList.add(vin1Model);
            vinList.add(vin2Model);
            vinList.add(vin3Model);
            vinList.add(vin4Model);
            opeCodebaseVinService.saveBatch(vinList);

            // 保存到码库关系表
            List<OpeCodebaseRelation> relationList = Lists.newArrayList();
            String rsn = scooterDataDTOList.get(0).getScooterNo();

            OpeCodebaseRelation relation1 = new OpeCodebaseRelation();
            relation1.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RELATION));
            relation1.setDr(Constant.DR_FALSE);
            relation1.setRsn(rsn);
            relation1.setVin(vin1);
            relation1.setStatus(1);
            relation1.setCreatedBy(userId);
            relation1.setCreatedTime(new Date());

            OpeCodebaseRelation relation2 = new OpeCodebaseRelation();
            relation2.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RELATION));
            relation2.setDr(Constant.DR_FALSE);
            relation2.setRsn(rsn);
            relation2.setVin(vin2);
            relation2.setStatus(1);
            relation2.setCreatedBy(userId);
            relation2.setCreatedTime(new Date());

            OpeCodebaseRelation relation3 = new OpeCodebaseRelation();
            relation3.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RELATION));
            relation3.setDr(Constant.DR_FALSE);
            relation3.setRsn(rsn);
            relation3.setVin(vin3);
            relation3.setStatus(1);
            relation3.setCreatedBy(userId);
            relation3.setCreatedTime(new Date());

            OpeCodebaseRelation relation4 = new OpeCodebaseRelation();
            relation4.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RELATION));
            relation4.setDr(Constant.DR_FALSE);
            relation4.setRsn(rsn);
            relation4.setVin(vin4);
            relation4.setStatus(1);
            relation4.setCreatedBy(userId);
            relation4.setCreatedTime(new Date());

            relationList.add(relation1);
            relationList.add(relation2);
            relationList.add(relation3);
            relationList.add(relation4);
            opeCodebaseRelationService.saveBatch(relationList);
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
     *
     * @param inWhOrderProductMap      入库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号
     * @param outWhOrderProductMap     出库单产品map
     * @param type                     出入库类型 1入库 2出库
     * @param userId                   用户id
     */
    public void saveWmsCombinationStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                            Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap, String type, Long userId) {
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
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(), userId));
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
                        InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue(), qty, InOutWhEnums.OUT.getValue(), userId));
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
     *
     * @param inWhOrderProductMap      入库单产品map
     * @param inWhouseOrderSerialBinds 入库单产品序列号
     * @param outWhOrderProductMap     出库单产品map
     * @param type                     出入库类型 1入库 2出库
     * @param userId                   用户id
     */
    public void saveWmsPartsStockData(Map<Long, List<InWhOrderProductDTO>> inWhOrderProductMap, List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds,
                                      Map<Long, List<OutWarehouseOrderProductDTO>> outWhOrderProductMap, String type, Long userId) {
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
                        InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue(), qty, InOutWhEnums.IN.getValue(), userId));

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
     *
     * @param relationId   关联仓库id
     * @param relationType 关联类型, 可参考：{@link WmsTypeEnum}
     * @param inWhType     入库类型, 可参考：{@link InWhTypeEnums}
     * @param qty          入库数量
     * @param inOutType    出入库类型 1入库 2出库
     * @param userId       用户id
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
     *
     * @param groupId 车辆分组id
     * @param colorId 车辆颜色id
     * @param qty     出入库数量
     * @param userId  用户id
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
     *
     * @param bomId  组装件bomId
     * @param qty    出入库数量
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
     *
     * @param bomId  部件bomId
     * @param qty    出入库数量
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
     *
     * @param opeInWhouseOrderSerialBinds 入库产品序列号集合
     * @param stockMap                    仓库id map集合
     * @param userId                      用户id
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
     *
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

    /**
     * 从指定数组中生成随机数
     */
    public String generateRangeRandom() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    /**
     * 生成VIN Code
     * @param specificatId 车辆型号id
     * @param specificatName 车辆型号名称
     * @param seatNumber 座位数
     * @return
     */
    public String generateVINCode(Long specificatId, String specificatName, Integer seatNumber) {
        String msg = "VXS";
        StringBuffer result = new StringBuffer();

        // 世界工厂代码和车辆类型
        result.append(msg);
        result.append(ScooterTypeEnum.R2A.getCode());

        // 车型编号和座位数量
        String productType = ProductTypeEnum.showCode(specificatName);
        result.append(productType);
        result.append(seatNumber);

        // 指定随机数
        String random = generateRangeRandom();
        result.append(random);

        // 年份字母和工厂编号
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String value = YearEnum.showValue(year);
        result.append(value);
        result.append(FactoryEnum.AOGE.getCode());

        // 6位数递增序列号
        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getSpecificatTypeId, specificatId);
        wrapper.eq(OpeCarDistribute::getSeatNumber, seatNumber);
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 得到自增编号,从倒数第6位开始截取
            for (OpeCarDistribute o : list) {
                String vinCode = o.getVinCode();
                if (StringUtils.isNotBlank(vinCode)) {
                    String sub = vinCode.substring(vinCode.length() - 6);
                    codeList.add(Integer.valueOf(sub));
                }
            }
            if (CollectionUtils.isNotEmpty(codeList)) {
                // 倒序排列
                codeList.sort(Comparator.reverseOrder());
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                nf.setMaximumIntegerDigits(6);
                nf.setMinimumIntegerDigits(6);
                String code = nf.format(new Double(codeList.get(0) + 1));
                result.append(code);
            } else {
                result.append("000001");
            }
        } else {
            result.append("000001");
        }
        // 根据生成的vin得到正确的第九位数字
        String nineCode = checkVIN(result.toString());
        // 用正确的第九位数字替换之前随机生成的第九位数字
        result.replace(8,9, nineCode);
        return result.toString();
    }

    /**
     * 替换Vin的第九位数字（校验vin）
     *
     * @param vin
     * @return
     */
    public static String checkVIN(String vin) {
        Map vinMapWeighting = null;

        Map vinMapValue = null;

        vinMapWeighting = new HashMap();

        vinMapValue = new HashMap();

        vinMapWeighting.put(1, 8);

        vinMapWeighting.put(2, 7);

        vinMapWeighting.put(3, 6);

        vinMapWeighting.put(4, 5);

        vinMapWeighting.put(5, 4);

        vinMapWeighting.put(6, 3);

        vinMapWeighting.put(7, 2);

        vinMapWeighting.put(8, 10);

        vinMapWeighting.put(9, 0);

        vinMapWeighting.put(10, 9);

        vinMapWeighting.put(11, 8);

        vinMapWeighting.put(12, 7);

        vinMapWeighting.put(13, 6);

        vinMapWeighting.put(14, 5);

        vinMapWeighting.put(15, 4);

        vinMapWeighting.put(16, 3);

        vinMapWeighting.put(17, 2);

        vinMapValue.put('0', 0);

        vinMapValue.put('1', 1);

        vinMapValue.put('2', 2);

        vinMapValue.put('3', 3);

        vinMapValue.put('4', 4);

        vinMapValue.put('5', 5);

        vinMapValue.put('6', 6);

        vinMapValue.put('7', 7);

        vinMapValue.put('8', 8);

        vinMapValue.put('9', 9);

        vinMapValue.put('A', 1);

        vinMapValue.put('B', 2);

        vinMapValue.put('C', 3);

        vinMapValue.put('D', 4);

        vinMapValue.put('E', 5);

        vinMapValue.put('F', 6);

        vinMapValue.put('G', 7);

        vinMapValue.put('H', 8);

        vinMapValue.put('J', 1);

        vinMapValue.put('K', 2);

        vinMapValue.put('M', 4);

        vinMapValue.put('L', 3);

        vinMapValue.put('N', 5);

        vinMapValue.put('P', 7);

        vinMapValue.put('R', 9);

        vinMapValue.put('S', 2);

        vinMapValue.put('T', 3);

        vinMapValue.put('U', 4);

        vinMapValue.put('V', 5);

        vinMapValue.put('W', 6);

        vinMapValue.put('X', 7);

        vinMapValue.put('Y', 8);

        vinMapValue.put('Z', 9);

        boolean reultFlag = false;

        String uppervin = vin.toUpperCase();

//排除字母O、I

        if (vin == null || uppervin.indexOf("O") >= 0 || uppervin.indexOf("I") >= 0) {
            reultFlag = false;

        } else {
//1:长度为17

            if (vin.length() == 17) {
                int amount = 0;
                char[] vinArr = uppervin.toCharArray();
                for (int i = 0; i < vinArr.length; i++) {

//VIN码从从第一位开始，码数字的对应值×该位的加权值，计算全部17位的乘积值相加
                    Object o = vinMapValue.get(vinArr[i]);
                    Object o2 = vinMapWeighting.get(i + 1);
                    amount += Integer.parseInt(o == null ? "" : o.toString()) * Integer.parseInt(o2 == null ? "" : o2.toString());

                }
                if (amount % 11 == 10) {
                    return "X";
                } else {
                    int result = amount % 11;
                    return String.valueOf(result);
                }
            }
        }
        return null;
    }

}
