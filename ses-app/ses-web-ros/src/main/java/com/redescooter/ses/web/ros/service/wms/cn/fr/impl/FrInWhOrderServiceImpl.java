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
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
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
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddScooterBEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description 法国仓库入库单ServiceImpl层
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
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterService scooterService;

    /**
     * 新增法国仓库入库单
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addFrInWhOrder(FrInWhOrderAddEnter enter) {
        log.info("新增法国仓库入库单的入参是:[{}]", enter);
        enter = SesStringUtils.objStringTrim(enter);

        // 解析
        List<FrInWhOrderAddScooterBEnter> list;
        try {
            list = JSONArray.parseArray(enter.getSt(), FrInWhOrderAddScooterBEnter.class);
        } catch (Exception ex) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // 新增入库单主表
        OpeInWhouseOrder inWhOrder = new OpeInWhouseOrder();
        inWhOrder.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
        inWhOrder.setDr(Constant.DR_FALSE);
        inWhOrder.setTenantId(enter.getTenantId());
        inWhOrder.setDeptId(enter.getOpeDeptId());
        // 入库的是法国仓库
        inWhOrder.setCountryType(WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
        inWhOrder.setInWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_INBOUND.getValue())));
        inWhOrder.setInWhStatus(NewInWhouseOrderStatusEnum.DRAFT.getValue());
        // 入库的是成品库
        inWhOrder.setWhType(1);
        // 入库的是车辆
        inWhOrder.setOrderType(ProductTypeEnums.SCOOTER.getValue());
        // 入库类型是其他
        inWhOrder.setInWhType(enter.getInWhType());
        inWhOrder.setInWhQty(list.size());
        inWhOrder.setSource(0);
        // 是仓库新增
        inWhOrder.setIfWh(1);
        inWhOrder.setRemark(enter.getRemark());
        inWhOrder.setCreatedBy(enter.getUserId());
        inWhOrder.setCreatedTime(new Date());
        inWhOrder.setUpdatedBy(enter.getUserId());
        inWhOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.save(inWhOrder);

        // 新增入库单车辆子表
        List<OpeInWhouseScooterB> scooterBList = Lists.newArrayList();
        for (FrInWhOrderAddScooterBEnter item : list) {
            OpeInWhouseScooterB scooterB = new OpeInWhouseScooterB();
            scooterB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_SCOOTER_B));
            scooterB.setDr(Constant.DR_FALSE);
            scooterB.setInWhId(inWhOrder.getId());
            scooterB.setGroupId(item.getGroupId());
            scooterB.setColorId(item.getColorId());
            // 因为每一辆车只对应一个sn,所以入库数量只能是1
            scooterB.setInWhQty(1);
            scooterB.setCreatedBy(enter.getUserId());
            scooterB.setCreatedTime(new Date());
            scooterB.setUpdatedBy(enter.getUserId());
            scooterB.setUpdatedTime(new Date());
            scooterB.setDef1(item.getSn());
            scooterB.setDef2(item.getBluetoothMacAddress());
            scooterBList.add(scooterB);
        }
        opeInWhouseScooterBService.saveBatch(scooterBList);

        // 操作记录
        SaveOpTraceEnter trace = new SaveOpTraceEnter(null, inWhOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), inWhOrder.getRemark());
        trace.setUserId(enter.getUserId());
        productionOrderTraceService.save(trace);

        // 状态流转
        OrderStatusFlowEnter flow = new OrderStatusFlowEnter(null, inWhOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhOrder.getId(), inWhOrder.getRemark());
        flow.setUserId(enter.getUserId());
        orderStatusFlowService.save(flow);

        // 操作法国仓库车辆库存,待入库数量增加
        wmsMaterialStockService.waitInStock(inWhOrder.getOrderType(), inWhOrder.getId(), inWhOrder.getCountryType(), enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 确认入库
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult confirmInWh(IdEnter enter) {
        log.info("确认入库的入参是:[{}]", enter);
        OpeInWhouseOrder inWhOrder = opeInWhouseOrderService.getById(enter.getId());
        if (null == inWhOrder) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!NewInWhouseOrderStatusEnum.DRAFT.getValue().equals(inWhOrder.getInWhStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }

        // 修改入库单主表状态为已入库
        inWhOrder.setInWhStatus(NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrderService.updateById(inWhOrder);

        // 修改入库单车辆子表的实际入库数量
        LambdaQueryWrapper<OpeInWhouseScooterB> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeInWhouseScooterB::getInWhId, inWhOrder.getId());
        List<OpeInWhouseScooterB> list = opeInWhouseScooterBService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeInWhouseScooterB scooterB : list) {
                scooterB.setActInWhQty(scooterB.getInWhQty());
            }
            opeInWhouseScooterBService.saveOrUpdateBatch(list);
        }

        // 操作记录
        SaveOpTraceEnter trace = new SaveOpTraceEnter(null, inWhOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(), inWhOrder.getRemark());
        trace.setUserId(enter.getUserId());
        productionOrderTraceService.save(trace);

        // 操作法国仓库车辆库存表,待入库数量减少,可用数量增加,并生成入库记录
        wmsMaterialStockService.inStock(inWhOrder.getOrderType(), inWhOrder.getId(), inWhOrder.getCountryType(), enter.getUserId(), inWhOrder.getInWhType());

        // 生成批次号
        String lot = getProductLot();
        List<SyncScooterDataDTO> syncList = Lists.newArrayList();
        List<OpeInWhouseOrderSerialBind> saveInWhSerialBindList = Lists.newArrayList();
        List<OpeWmsStockSerialNumber> saveStockSerialNumberList = Lists.newArrayList();

        // 循环入库单车辆子表,拿到每辆要入库的车的信息
        for (OpeInWhouseScooterB item : list) {
            // 为每一辆车生成一个RSN
            String rsn = getProductSerialNum();

            // 新增sco_scooter表
            SyncScooterDataDTO syncData = new SyncScooterDataDTO();
            syncData.setScooterNo(rsn);
            syncData.setTabletSn(item.getDef1());
            // 车辆入库默认型号是E50
            syncData.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
            syncData.setUserId(enter.getUserId());
            syncList.add(syncData);

            // 新增ope_in_whouse_order_serial_bind表
            LambdaQueryWrapper<OpeInWhouseOrderSerialBind> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeInWhouseOrderSerialBind::getDr, Constant.DR_FALSE);
            wrapper.eq(OpeInWhouseOrderSerialBind::getSerialNum, rsn);
            wrapper.last("limit 1");
            OpeInWhouseOrderSerialBind inSerialBind = opeInWhouseOrderSerialBindService.getOne(wrapper);
            if (null == inSerialBind) {
                log.info("生成的rsn在ope_in_whouse_order_serial_bind表不存在,正常执行新增");
                // 根据groupId和colorId找到productId
                LambdaQueryWrapper<OpeProductionScooterBom> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeProductionScooterBom::getDr, Constant.DR_FALSE);
                lqw.eq(OpeProductionScooterBom::getBomStatus, 1);
                lqw.eq(OpeProductionScooterBom::getGroupId, item.getGroupId());
                lqw.eq(OpeProductionScooterBom::getColorId, item.getColorId());
                lqw.last("limit 1");
                OpeProductionScooterBom bom = opeProductionScooterBomService.getOne(lqw);
                if (null != bom) {
                    log.info("找到了bom");
                    OpeInWhouseOrderSerialBind bind = new OpeInWhouseOrderSerialBind();
                    bind.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
                    bind.setDr(Constant.DR_FALSE);
                    bind.setOrderBId(item.getId());
                    bind.setOrderType(ProductTypeEnums.SCOOTER.getValue());
                    bind.setSerialNum(rsn);
                    bind.setDefaultSerialNum(item.getDef1());
                    bind.setTabletSn(item.getDef1());
                    bind.setBluetoothMacAddress(item.getDef2());
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

            // 新增ope_wms_stock_serial_number表
            LambdaQueryWrapper<OpeWmsStockSerialNumber> wmsWrapper = new LambdaQueryWrapper<>();
            wmsWrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
            wmsWrapper.eq(OpeWmsStockSerialNumber::getRsn, rsn);
            wmsWrapper.last("limit 1");
            OpeWmsStockSerialNumber serialNumber = opeWmsStockSerialNumberService.getOne(wmsWrapper);
            if (null == serialNumber) {
                log.info("生成的rsn在ope_wms_stock_serial_number表不存在,正常执行新增");
                // 根据groupId和colorId找到relationId
                LambdaQueryWrapper<OpeWmsScooterStock> stockWrapper = new LambdaQueryWrapper<>();
                stockWrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
                stockWrapper.eq(OpeWmsScooterStock::getGroupId, item.getGroupId());
                stockWrapper.eq(OpeWmsScooterStock::getColorId, item.getColorId());
                stockWrapper.eq(OpeWmsScooterStock::getStockType, WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                stockWrapper.last("limit 1");
                OpeWmsScooterStock stock = opeWmsScooterStockService.getOne(stockWrapper);
                if (null != stock) {
                    log.info("找到了stock");
                    OpeWmsStockSerialNumber number = new OpeWmsStockSerialNumber();
                    number.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
                    number.setDr(Constant.DR_FALSE);
                    number.setTenantId(enter.getTenantId());
                    number.setDeptId(enter.getOpeDeptId());
                    number.setRelationType(ProductTypeEnums.SCOOTER.getValue());
                    number.setRelationId(stock.getId());
                    number.setStockType(WmsStockTypeEnum.FRENCH_WAREHOUSE.getType());
                    number.setRsn(rsn);
                    number.setStockStatus(1);
                    number.setLotNum(lot);
                    number.setSn(item.getDef1());
                    number.setBluetoothMacAddress(item.getDef2());
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
            log.info("新增sco_scooter表成功");
        }
        if (CollectionUtils.isNotEmpty(saveInWhSerialBindList)) {
            opeInWhouseOrderSerialBindService.saveOrUpdateBatch(saveInWhSerialBindList);
            log.info("新增ope_in_whouse_order_serial_bind表成功");
        }
        if (CollectionUtils.isNotEmpty(saveStockSerialNumberList)) {
            opeWmsStockSerialNumberService.saveOrUpdateBatch(saveStockSerialNumberList);
            log.info("新增ope_wms_stock_serial_number表成功");
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获得产品序列号
     */
    private String getProductSerialNum() {
        boolean flag = true;
        String serialNum = generateProductSerialNumber();

        // 获取当天入库的车辆编号信息
        List<String> list = scooterService.getToDayScooterNos();
        if (CollectionUtils.isNotEmpty(list)) {
            while (flag) {
                // 如果车辆序列号已经存在则重新生成
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
     * 生成产品序列号
     */
    private String generateProductSerialNumber() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String productRange = "ED";
        String structureType = "D";

        /**
         * 编号规则：
         * 1.目标市场(默认法国)
         * 2.产品范围
         * 3.结构类型
         * 4.生产地点(默认中国南通)
         * 5.年份,只取后两位(2021年就是21)
         * 6.月份
         * 7.生产流水号(生产日 + 工单号 + 流水号-暂时是随机数)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append(productRange);
        sb.append(structureType);
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // 获取当前时间戳,并截取最后6位拼接在编号最后
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * 获取产品批次号
     */
    private String getProductLot() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // 获取当前时间戳,并截取最后6位拼接在编号最后
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);

        /**
         * 批次规则：
         * 1.LOT(固定值)
         * 2.年份
         * 3.月份
         * 4.天数
         * 5.时间戳截取后6位
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
