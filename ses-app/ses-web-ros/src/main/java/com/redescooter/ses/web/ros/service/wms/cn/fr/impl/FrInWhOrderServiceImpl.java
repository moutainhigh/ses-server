package com.redescooter.ses.web.ros.service.wms.cn.fr.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.NewInWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.wms.WmsStockTypeEnum;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockRecord;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockRecordService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.OpeInWhouseOrderSerialBindService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrInWhOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsInStockRecordEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddScooterBEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderCheckEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description ?????????????????????ServiceImpl???
 * @Author Chris
 * @Date 2021/4/15 10:08
 */
@Service
@Slf4j
public class FrInWhOrderServiceImpl implements FrInWhOrderService {

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Autowired
    private OpeInWhouseOrderSerialBindService opeInWhouseOrderSerialBindService;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeWmsStockRecordService opeWmsStockRecordService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterService scooterService;

    /**
     * ???????????????????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addFrInWhOrder(FrInWhOrderAddEnter enter) {
        log.info("???????????????????????????????????????:[{}]", enter);
        enter = SesStringUtils.objStringTrim(enter);

        // ??????
        List<FrInWhOrderAddScooterBEnter> list;
        try {
            list = JSONArray.parseArray(enter.getSt(), FrInWhOrderAddScooterBEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // ???????????????ope_in_whouse_order_serial_bind???????????????
        /*boolean flag = false;
        for (FrInWhOrderAddScooterBEnter scooterB : list) {
            LambdaQueryWrapper<OpeInWhouseOrderSerialBind> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeInWhouseOrderSerialBind::getDr, Constant.DR_FALSE);
            qw.eq(OpeInWhouseOrderSerialBind::getSerialNum, scooterB.getSn());
            int count = opeInWhouseOrderSerialBindService.count(qw);
            if (count > 0) {
                flag = true;
                break;
            }

            LambdaQueryWrapper<OpeInWhouseOrderSerialBind> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeInWhouseOrderSerialBind::getDr, Constant.DR_FALSE);
            lqw.eq(OpeInWhouseOrderSerialBind::getTabletSn, scooterB.getTabletSn());
            int tabletSnCount = opeInWhouseOrderSerialBindService.count(lqw);
            if (tabletSnCount > 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new SesWebRosException(ExceptionCodeEnums.FR_WH_RSN_IS_EXIST.getCode(), ExceptionCodeEnums.FR_WH_RSN_IS_EXIST.getMessage());
        }*/

        // ???????????????ope_wms_stock_serial_number???????????????
        boolean flag = false;
        for (FrInWhOrderAddScooterBEnter scooterB : list) {
            LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            qw.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            qw.eq(OpeWmsStockSerialNumber::getRsn, scooterB.getSn());
            int count = opeWmsStockSerialNumberService.count(qw);
            if (count > 0) {
                flag = true;
                break;
            }

            LambdaQueryWrapper<OpeWmsStockSerialNumber> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            lqw.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            lqw.eq(OpeWmsStockSerialNumber::getSn, scooterB.getTabletSn()).or().eq(OpeWmsStockSerialNumber::getDef3, scooterB.getTabletSn());
            int tabletSnCount = opeWmsStockSerialNumberService.count(lqw);
            if (tabletSnCount > 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new SesWebRosException(ExceptionCodeEnums.FR_WH_RSN_IS_EXIST.getCode(), ExceptionCodeEnums.FR_WH_RSN_IS_EXIST.getMessage());
        }

        // ?????????????????????
        OpeInWhouseOrder inWhOrder = new OpeInWhouseOrder();
        inWhOrder.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
        inWhOrder.setDr(Constant.DR_FALSE);
        inWhOrder.setTenantId(enter.getTenantId());
        inWhOrder.setDeptId(enter.getOpeDeptId());
        // ????????????????????????
        inWhOrder.setCountryType(WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
        inWhOrder.setInWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_INBOUND.getValue())));
        inWhOrder.setInWhStatus(NewInWhouseOrderStatusEnum.DRAFT.getValue());
        // ?????????????????????
        inWhOrder.setWhType(1);
        // ??????????????????
        inWhOrder.setOrderType(ProductTypeEnums.SCOOTER.getValue());
        // ?????????????????????
        inWhOrder.setInWhType(enter.getInWhType());
        inWhOrder.setInWhQty(list.size());
        inWhOrder.setSource(0);
        // ???????????????
        inWhOrder.setIfWh(1);
        inWhOrder.setRemark(enter.getRemark());
        inWhOrder.setCreatedBy(enter.getUserId());
        inWhOrder.setCreatedTime(new Date());
        inWhOrder.setUpdatedBy(enter.getUserId());
        inWhOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.save(inWhOrder);

        // ???????????????????????????
        List<OpeInWhouseScooterB> scooterBList = Lists.newArrayList();
        for (FrInWhOrderAddScooterBEnter item : list) {
            OpeInWhouseScooterB scooterB = new OpeInWhouseScooterB();
            scooterB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_SCOOTER_B));
            scooterB.setDr(Constant.DR_FALSE);
            scooterB.setInWhId(inWhOrder.getId());
            scooterB.setGroupId(item.getGroupId());
            scooterB.setColorId(item.getColorId());
            // ?????????????????????????????????sn,???????????????????????????1
            scooterB.setInWhQty(1);
            scooterB.setCreatedBy(enter.getUserId());
            scooterB.setCreatedTime(new Date());
            scooterB.setUpdatedBy(enter.getUserId());
            scooterB.setUpdatedTime(new Date());
            scooterB.setDef1(item.getSn());
            scooterB.setDef2(item.getBluetoothMacAddress());
            scooterB.setDef3(item.getTabletSn());
            scooterBList.add(scooterB);
        }
        opeInWhouseScooterBService.saveBatch(scooterBList);

        // ????????????
        SaveOpTraceEnter trace = new SaveOpTraceEnter(null, inWhOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), inWhOrder.getRemark());
        trace.setUserId(enter.getUserId());
        productionOrderTraceService.save(trace);

        // ????????????
        OrderStatusFlowEnter flow = new OrderStatusFlowEnter(null, inWhOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhOrder.getId(), inWhOrder.getRemark());
        flow.setUserId(enter.getUserId());
        orderStatusFlowService.save(flow);

        // ??????????????????????????????,?????????????????????
        wmsMaterialStockService.waitInStock(inWhOrder.getOrderType(), inWhOrder.getId(), inWhOrder.getCountryType(), enter.getUserId());
        return new GeneralResult(String.valueOf(inWhOrder.getId()));
    }

    /**
     * ??????rsn?????????????????????????????????????????????
     */
    @Override
    public BooleanResult checkRsn(FrInWhOrderCheckEnter enter) {
        BooleanResult result = new BooleanResult();

        Integer count = 0, tabletSnCount = 0, macCount = 0;
        // ???????????????
        if (StringUtils.isNotBlank(enter.getRsn())) {
            LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            qw.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            qw.eq(OpeWmsStockSerialNumber::getRsn, enter.getRsn());
            count = opeWmsStockSerialNumberService.count(qw);
        }

        // ???????????????
        if (StringUtils.isNotBlank(enter.getTabletSn())) {
            LambdaQueryWrapper<OpeWmsStockSerialNumber> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            lqw.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            lqw.eq(OpeWmsStockSerialNumber::getSn, enter.getTabletSn()).or().eq(OpeWmsStockSerialNumber::getDef3, enter.getTabletSn());
            tabletSnCount = opeWmsStockSerialNumberService.count(lqw);
        }

        // ????????????
        if (StringUtils.isNotBlank(enter.getBluetoothMacAddress())) {
            LambdaQueryWrapper<OpeWmsStockSerialNumber> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            wrapper.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            wrapper.eq(OpeWmsStockSerialNumber::getBluetoothMacAddress, enter.getBluetoothMacAddress());
            macCount = opeWmsStockSerialNumberService.count(wrapper);
        }

        if (count > 0 || tabletSnCount > 0 || macCount > 0) {
            result.setSuccess(Boolean.FALSE);
            result.setRequestId(enter.getRequestId());
            return result;
        }

        result.setSuccess(Boolean.TRUE);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult confirmInWh(IdEnter enter) {
        log.info("????????????????????????:[{}]", enter);
        OpeInWhouseOrder inWhOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!NewInWhouseOrderStatusEnum.DRAFT.getValue().equals(inWhOrder.getInWhStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }

        // ???????????????????????????????????????
        inWhOrder.setInWhStatus(NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrderService.updateById(inWhOrder);

        // ????????????????????????????????????????????????
        LambdaQueryWrapper<OpeInWhouseScooterB> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeInWhouseScooterB::getInWhId, inWhOrder.getId());
        List<OpeInWhouseScooterB> list = opeInWhouseScooterBService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeInWhouseScooterB scooterB : list) {
                scooterB.setActInWhQty(scooterB.getInWhQty());
            }
            opeInWhouseScooterBService.saveOrUpdateBatch(list);
        }

        // ????????????
        SaveOpTraceEnter trace = new SaveOpTraceEnter(null, inWhOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(), inWhOrder.getRemark());
        trace.setUserId(enter.getUserId());
        productionOrderTraceService.save(trace);

        // ?????????????????????????????????,?????????????????????,??????????????????,?????????????????????
        //wmsMaterialStockService.inStock(inWhOrder.getOrderType(), inWhOrder.getId(), inWhOrder.getCountryType(), enter.getUserId(), inWhOrder.getInWhType());
        List<WmsInStockRecordEnter> recordList = Lists.newArrayList();
        LambdaQueryWrapper<OpeInWhouseScooterB> wq = new LambdaQueryWrapper<>();
        wq.eq(OpeInWhouseScooterB::getInWhId, inWhOrder.getId());
        List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(wq);
        if (CollectionUtils.isNotEmpty(scooterBList)) {
            List<OpeWmsScooterStock> stockList = Lists.newArrayList();
            for (OpeInWhouseScooterB item : scooterBList) {
                // ????????????????????????????????? ?????????????????????
                LambdaQueryWrapper<OpeWmsScooterStock> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeWmsScooterStock::getGroupId, item.getGroupId());
                lqw.eq(OpeWmsScooterStock::getColorId, item.getColorId());
                lqw.eq(OpeWmsScooterStock::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                lqw.last("limit 1");
                OpeWmsScooterStock stock = opeWmsScooterStockService.getOne(lqw);
                if (StringManaConstant.entityIsNotNull(stock)) {
                    stock.setAbleStockQty(stock.getAbleStockQty() + item.getInWhQty());
                    stock.setWaitInStockQty(stock.getWaitInStockQty() - item.getInWhQty());
                    stockList.add(stock);

                    // ????????????????????????
                    WmsInStockRecordEnter record = new WmsInStockRecordEnter();
                    record.setRelationId(stock.getId());
                    record.setInWhType(inWhOrder.getInWhType());
                    record.setRelationType(7);
                    record.setInWhQty(item.getInWhQty());
                    record.setRecordType(1);
                    record.setStockType(WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                    recordList.add(record);
                }
            }
            opeWmsScooterStockService.saveOrUpdateBatch(stockList);
        }
        createInStockRecord(recordList, enter.getUserId());

        // ???????????????
        String lot = getProductLot();
        List<SyncScooterDataDTO> syncList = Lists.newArrayList();
        List<OpeInWhouseOrderSerialBind> saveInWhSerialBindList = Lists.newArrayList();
        List<OpeWmsStockSerialNumber> saveStockSerialNumberList = Lists.newArrayList();

        // ???????????????????????????,????????????????????????????????????
        for (OpeInWhouseScooterB item : list) {
            // ???????????????????????????RSN
            //String rsn = getProductSerialNum();
            String rsn = item.getDef1();
            // ??????sco_scooter???
            SyncScooterDataDTO syncData = new SyncScooterDataDTO();
            syncData.setScooterNo(rsn);
            // ??????????????????
            syncData.setBluetoothMacAddress(item.getDef2());
            // ???????????????
            syncData.setTabletSn(item.getDef3());
            // ???????????????????????????E50
            syncData.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
            syncData.setUserId(enter.getUserId());
            syncList.add(syncData);

            // ??????ope_in_whouse_order_serial_bind???
            LambdaQueryWrapper<OpeInWhouseOrderSerialBind> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeInWhouseOrderSerialBind::getDr, Constant.DR_FALSE);
            wrapper.eq(OpeInWhouseOrderSerialBind::getSerialNum, rsn);
            wrapper.last("limit 1");
            OpeInWhouseOrderSerialBind inSerialBind = opeInWhouseOrderSerialBindService.getOne(wrapper);
            if (StringManaConstant.entityIsNull(inSerialBind)) {
                log.info("?????????rsn???ope_in_whouse_order_serial_bind????????????,??????????????????");
                // ??????groupId???colorId??????productId
                LambdaQueryWrapper<OpeProductionScooterBom> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeProductionScooterBom::getDr, Constant.DR_FALSE);
                lqw.eq(OpeProductionScooterBom::getBomStatus, 1);
                lqw.eq(OpeProductionScooterBom::getGroupId, item.getGroupId());
                lqw.eq(OpeProductionScooterBom::getColorId, item.getColorId());
                lqw.last("limit 1");
                OpeProductionScooterBom bom = opeProductionScooterBomService.getOne(lqw);
                if (StringManaConstant.entityIsNotNull(bom)) {
                    log.info("?????????bom");
                    OpeInWhouseOrderSerialBind bind = new OpeInWhouseOrderSerialBind();
                    bind.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
                    bind.setDr(Constant.DR_FALSE);
                    bind.setOrderBId(item.getId());
                    bind.setOrderType(ProductTypeEnums.SCOOTER.getValue());
                    // ???????????????
                    bind.setSerialNum(rsn);
                    bind.setDefaultSerialNum(rsn);
                    // ????????????
                    bind.setBluetoothMacAddress(item.getDef2());
                    // ???????????????
                    bind.setTabletSn(item.getDef3());
                    bind.setLot(lot);
                    bind.setProductId(bom.getId());
                    bind.setProductType(ProductTypeEnums.SCOOTER.getValue());
                    bind.setQty(item.getActInWhQty());
                    bind.setCreatedBy(enter.getUserId());
                    bind.setCreatedTime(new Date());
                    bind.setUpdatedBy(enter.getUserId());
                    bind.setUpdatedTime(new Date());
                    saveInWhSerialBindList.add(bind);
                }
            }

            // ??????ope_wms_stock_serial_number???
            LambdaQueryWrapper<OpeWmsStockSerialNumber> wmsWrapper = new LambdaQueryWrapper<>();
            wmsWrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            wmsWrapper.eq(OpeWmsStockSerialNumber::getRsn, rsn);
            wmsWrapper.eq(OpeWmsStockSerialNumber::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
            wmsWrapper.last("limit 1");
            OpeWmsStockSerialNumber serialNumber = opeWmsStockSerialNumberService.getOne(wmsWrapper);
            if (StringManaConstant.entityIsNull(serialNumber)) {
                log.info("?????????rsn???ope_wms_stock_serial_number????????????,??????????????????");
                // ??????groupId???colorId??????relationId
                LambdaQueryWrapper<OpeWmsScooterStock> stockWrapper = new LambdaQueryWrapper<>();
                stockWrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
                stockWrapper.eq(OpeWmsScooterStock::getGroupId, item.getGroupId());
                stockWrapper.eq(OpeWmsScooterStock::getColorId, item.getColorId());
                stockWrapper.eq(OpeWmsScooterStock::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                stockWrapper.last("limit 1");
                OpeWmsScooterStock stock = opeWmsScooterStockService.getOne(stockWrapper);
                if (StringManaConstant.entityIsNotNull(stock)) {
                    log.info("?????????stock");
                    OpeWmsStockSerialNumber number = new OpeWmsStockSerialNumber();
                    number.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
                    number.setDr(Constant.DR_FALSE);
                    number.setTenantId(enter.getTenantId());
                    number.setDeptId(enter.getOpeDeptId());
                    number.setRelationType(ProductTypeEnums.SCOOTER.getValue());
                    number.setRelationId(stock.getId());
                    number.setStockType(WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                    number.setStockStatus(1);
                    number.setLotNum(lot);
                    // ???????????????
                    number.setRsn(rsn);
                    number.setSn(item.getDef3());
                    // ????????????
                    number.setBluetoothMacAddress(item.getDef2());
                    // ???????????????
                    number.setDef3(item.getDef3());
                    number.setCreatedBy(enter.getUserId());
                    number.setCreatedTime(new Date());
                    number.setUpdatedBy(enter.getUserId());
                    number.setUpdatedTime(new Date());
                    saveStockSerialNumberList.add(number);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(syncList)) {
            scooterService.syncScooterData(syncList);
            log.info("??????sco_scooter?????????");
        }
        if (CollectionUtils.isNotEmpty(saveInWhSerialBindList)) {
            opeInWhouseOrderSerialBindService.saveOrUpdateBatch(saveInWhSerialBindList);
            log.info("??????ope_in_whouse_order_serial_bind?????????");
        }
        if (CollectionUtils.isNotEmpty(saveStockSerialNumberList)) {
            opeWmsStockSerialNumberService.saveOrUpdateBatch(saveStockSerialNumberList);
            log.info("??????ope_wms_stock_serial_number?????????");
        }
        return new GeneralResult(enter.getRequestId());
    }

    public void createInStockRecord(List<WmsInStockRecordEnter> list, Long userId) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<OpeWmsStockRecord> resultList = new ArrayList<>();
            for (WmsInStockRecordEnter item : list) {
                OpeWmsStockRecord record = new OpeWmsStockRecord();
                BeanUtils.copyProperties(item, record);
                record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
                record.setCreatedBy(userId);
                record.setCreatedTime(new Date());
                record.setUpdatedBy(userId);
                record.setUpdatedTime(new Date());
                resultList.add(record);
            }
            opeWmsStockRecordService.saveOrUpdateBatch(resultList);
        }
    }

    /**
     * ?????????????????????
     */
    private String getProductSerialNum() {
        boolean flag = true;
        String serialNum = generateProductSerialNumber();

        // ???????????????????????????????????????
        List<String> list = scooterService.getToDayScooterNos();
        if (CollectionUtils.isNotEmpty(list)) {
            while (flag) {
                // ????????????????????????????????????????????????
                if (list.contains(serialNum)) {
                    serialNum = generateProductSerialNumber();
                } else {
                    flag = false;
                }
            }
        }
        return serialNum;
    }

    /**
     * ?????????????????????
     */
    private String generateProductSerialNumber() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String productRange = "ED";
        String structureType = "D";

        /**
         * ???????????????
         * 1.????????????(????????????)
         * 2.????????????
         * 3.????????????
         * 4.????????????(??????????????????)
         * 5.??????,???????????????(2021?????????21)
         * 6.??????
         * 7.???????????????(????????? + ????????? + ?????????-??????????????????)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append(productRange);
        sb.append(structureType);
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // ?????????????????????,???????????????6????????????????????????
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * ?????????????????????
     */
    private String getProductLot() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // ?????????????????????,???????????????6????????????????????????
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);

        /**
         * ???????????????
         * 1.LOT(?????????)
         * 2.??????
         * 3.??????
         * 4.??????
         * 5.??????????????????6???
         */
        StringBuilder sb = new StringBuilder();
        sb.append("LOT");
        sb.append(year);
        sb.append(month);
        sb.append(day);
        sb.append(sub);
        return sb.toString();
    }

}
