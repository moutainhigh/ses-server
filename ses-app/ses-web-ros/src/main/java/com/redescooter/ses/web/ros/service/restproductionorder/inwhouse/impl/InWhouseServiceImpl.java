package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.NewInWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproductionorder.AllocateOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderCombinBServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderPartsBServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderScooterBServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionAssemblyOrderServiceMapper;
import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionPurchasServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchaseOrder;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAllocateOrderService;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeInWhousePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPurchaseOrderService;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.service.restproductionorder.number.OrderNumberService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailCombinResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailPartsResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailScooterResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdateCombinBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdatePartsBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdateScooterBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassNameInWhouseServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
@Service
@Slf4j
public class InWhouseServiceImpl implements InWhouseService {

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;

    @Autowired
    private OrderNumberService orderNumberService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private InWhouseOrderScooterBServiceMapper inWhouseOrderScooterBServiceMapper;

    @Autowired
    private InWhouseOrderCombinBServiceMapper inWhouseOrderCombinBServiceMapper;

    @Autowired
    private InWhouseOrderPartsBServiceMapper inWhouseOrderPartsBServiceMapper;

    @Autowired
    private AllocateOrderServiceMapper allocateOrderServiceMapper;

    @Autowired
    private OpeProductionPurchaseOrderService opeProductionPurchaseOrderService;

    @Autowired
    private OpeCombinOrderService opeCombinOrderService;

    @Autowired
    private ProductionPurchasServiceMapper productionPurchasServiceMapper;

    @Autowired
    private ProductionAssemblyOrderServiceMapper productionAssemblyOrderServiceMapper;

    @Autowired
    private ProductionPurchasService productionPurchasService;

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Autowired
    private OpeAllocateOrderService opeAllocateOrderService;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @DubboReference
    private IdAppService idAppService;

    @Override
    public PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = inWhouseOrderServiceMapper.totalNum(enter);
        if (NumberUtil.eqZero(totalNum)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InWhouseListResult> list = inWhouseOrderServiceMapper.inWhList(enter);
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeInWhouseOrder inWhouseOrder = new OpeInWhouseOrder();
        BeanUtils.copyProperties(enter, inWhouseOrder);
        inWhouseOrder.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER));
        inWhouseOrder.setCreatedBy(enter.getUserId());
        inWhouseOrder.setCreatedTime(new Date());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        inWhouseOrder.setInWhStatus(NewInWhouseOrderStatusEnum.DRAFT.getValue());
        // 单据号
        inWhouseOrder.setInWhNo(orderNumberService.generateOrderNo(new OrderNumberEnter(OrderTypeEnums.FACTORY_INBOUND.getValue())));
        // 统计入库数量
        countQty(inWhouseOrder, enter.getSt());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 处理子表
        createInWhouseB(inWhouseOrder, enter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CREATE.getValue(), inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(), inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        try {
            // 如果是从仓库新增的入库单 新增就是待入库状态
            /*if (1 == enter.getIfWh()) {
                wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
            } else {
                // 如果是整车入库单或者是组装件入库单 生成的时候就是待入库状态 这个时候 要处理库存
                if (inWhouseOrder.getOrderType() == 1 || inWhouseOrder.getOrderType() == 2) {
                    wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
                }
            }*/
            wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
        } catch (Exception e) {

        }
        return new GeneralResult(String.valueOf(inWhouseOrder.getId()));
    }


    public void createInWhouseB(OpeInWhouseOrder inWhouseOrder, InWhouseSaveOrUpdateEnter enter) {
        switch (inWhouseOrder.getOrderType()) {
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseScooterB> scooterBList = new ArrayList<>();
                for (SaveOrUpdateScooterBEnter scooterEnter : scooterEnters) {
                    OpeInWhouseScooterB scooterB = new OpeInWhouseScooterB();
                    BeanUtils.copyProperties(scooterEnter, scooterB);
                    scooterB.setInWhId(inWhouseOrder.getId());
                    scooterB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_SCOOTER_B));
                    scooterB.setCreatedBy(enter.getUserId());
                    scooterB.setCreatedTime(new Date());
                    scooterB.setUpdatedBy(enter.getUserId());
                    scooterB.setUpdatedTime(new Date());
                    scooterB.setDef1(scooterEnter.getSn());
                    scooterB.setDef2(scooterEnter.getBluetoothMacAddress());
                    scooterB.setDef3(scooterEnter.getTabletSn());
                    scooterBList.add(scooterB);
                }
                opeInWhouseScooterBService.saveOrUpdateBatch(scooterBList);
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhouseCombinB> combinBList = new ArrayList<>();
                for (SaveOrUpdateCombinBEnter combinBEnter : combinBEnters) {
                    OpeInWhouseCombinB combinB = new OpeInWhouseCombinB();
                    BeanUtils.copyProperties(combinBEnter, combinB);
                    combinB.setInWhId(inWhouseOrder.getId());
                    combinB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_COMBIN_B));
                    combinB.setCreatedBy(enter.getUserId());
                    combinB.setCreatedTime(new Date());
                    combinB.setUpdatedBy(enter.getUserId());
                    combinB.setUpdatedTime(new Date());
                    combinBList.add(combinB);
                }
                opeInWhouseCombinBService.saveOrUpdateBatch(combinBList);
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(enter.getSt(), SaveOrUpdatePartsBEnter.class);
                    log.info("本次传过来的数据条数：：：：：：" + partsBEnters.size());
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                List<OpeInWhousePartsB> partsBList = new ArrayList<>();
                for (SaveOrUpdatePartsBEnter partsBEnter : partsBEnters) {
                    OpeInWhousePartsB partsB = new OpeInWhousePartsB();
                    BeanUtils.copyProperties(partsBEnter, partsB);
                    partsB.setInWhId(inWhouseOrder.getId());
                    partsB.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_PARTS_B));
                    partsB.setCreatedBy(enter.getUserId());
                    partsB.setCreatedTime(new Date());
                    partsB.setUpdatedBy(enter.getUserId());
                    partsB.setUpdatedTime(new Date());
                    partsBList.add(partsB);
                }
                log.info("本次保存的数据条数：：：：：：" + partsBList.size());
                opeInWhousePartsBService.saveOrUpdateBatch(partsBList);
                break;
        }
    }


    public void countQty(OpeInWhouseOrder inWhouseOrder, String st) {
        boolean flag = false;
        switch (inWhouseOrder.getOrderType()) {
            case 1:
                // scooter
                List<SaveOrUpdateScooterBEnter> scooterEnters = new ArrayList<>();
                try {
                    scooterEnters = JSONArray.parseArray(st, SaveOrUpdateScooterBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (scooterEnters.stream().anyMatch(o -> null == o.getInWhQty())) {
                    flag = true;
                }
                if (flag) {
                    throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                }
                inWhouseOrder.setInWhQty(scooterEnters.stream().mapToInt(SaveOrUpdateScooterBEnter::getInWhQty).sum());
//                if (null != inWhouseOrder.getRelationOrderId()){
//                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
//                }
            default:
                break;
            case 2:
                // combin
                List<SaveOrUpdateCombinBEnter> combinBEnters = new ArrayList<>();
                try {
                    combinBEnters = JSONArray.parseArray(st, SaveOrUpdateCombinBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (combinBEnters.stream().anyMatch(o -> null == o.getInWhQty())) {
                    flag = true;
                }
                if (flag) {
                    throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                }
                inWhouseOrder.setInWhQty(combinBEnters.stream().mapToInt(SaveOrUpdateCombinBEnter::getInWhQty).sum());
//                if (null != inWhouseOrder.getRelationOrderId()){
//                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
//                }
                break;
            case 3:
                // parts
                List<SaveOrUpdatePartsBEnter> partsBEnters = new ArrayList<>();
                try {
                    partsBEnters = JSONArray.parseArray(st, SaveOrUpdatePartsBEnter.class);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                if (partsBEnters.stream().anyMatch(o -> null == o.getInWhQty())) {
                    flag = true;
                }
                if (flag) {
                    throw new SesWebRosException(ExceptionCodeEnums.NUMBER_NOT_EMPTY.getCode(), ExceptionCodeEnums.NUMBER_NOT_EMPTY.getMessage());
                }
                inWhouseOrder.setInWhQty(partsBEnters.stream().mapToInt(SaveOrUpdatePartsBEnter::getInWhQty).sum());
//                if (null != inWhouseOrder.getRelationOrderId()){
//                    inWhouseOrder.setRelationOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
//                }
                break;
        }
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(NewInWhouseOrderStatusEnum.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        BeanUtils.copyProperties(enter, inWhouseOrder);
        inWhouseOrder.setUpdatedTime(new Date());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        countQty(inWhouseOrder, enter.getSt());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 编辑的时候 先把下面的产品删除  再重新插入
        deleteOrderB(inWhouseOrder);
        // 处理子表
        createInWhouseB(inWhouseOrder, enter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.EDIT.getValue(), inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);

        try {
            // 如果是从仓库新增的入库单 新增就是待入库状态
            /*if (1 == enter.getIfWh()) {
                wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
            } else {
                // 如果是整车入库单或者是组装件入库单 生成的时候就是待入库状态 这个时候 要处理库存
                if (inWhouseOrder.getOrderType() == 1 || inWhouseOrder.getOrderType() == 2) {
                    wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
                }
            }*/
            wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), inWhouseOrder.getCountryType(), enter.getUserId());
        } catch (Exception e) {

        }
        return new GeneralResult(String.valueOf(inWhouseOrder.getId()));
    }


    // 删除入库单下面的子表数据
    public void deleteOrderB(OpeInWhouseOrder inWhouseOrder) {
        switch (inWhouseOrder.getOrderType()) {
            case 1:
                // scooter
                QueryWrapper<OpeInWhouseScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    opeInWhouseScooterBService.removeByIds(scooterBList.stream().map(OpeInWhouseScooterB::getId).collect(Collectors.toList()));
                    // 修改库存信息
                    List<OpeWmsScooterStock> scooterStocks = new ArrayList<>();
                    for (OpeInWhouseScooterB scooterB : scooterBList) {
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE, inWhouseOrder.getCountryType());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        scooterStock.setWaitInStockQty(scooterStock.getWaitInStockQty() - scooterB.getInWhQty());
                        scooterStocks.add(scooterStock);
                    }
                    if (CollectionUtils.isNotEmpty(scooterStocks)) {
                        opeWmsScooterStockService.saveOrUpdateBatch(scooterStocks);
                    }
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeInWhouseCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseCombinB> combinBList = opeInWhouseCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBList)) {
                    opeInWhouseCombinBService.removeByIds(combinBList.stream().map(OpeInWhouseCombinB::getId).collect(Collectors.toList()));
                    // 修改库存信息
                    List<OpeWmsCombinStock> combinStocks = new ArrayList<>();
                    for (OpeInWhouseCombinB combinB : combinBList) {
                        QueryWrapper<OpeWmsCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                        combinStockQueryWrapper.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        combinStockQueryWrapper.eq(OpeWmsCombinStock.COL_STOCK_TYPE, inWhouseOrder.getCountryType());
                        combinStockQueryWrapper.last("limit 1");
                        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getOne(combinStockQueryWrapper);
                        combinStock.setWaitInStockQty(combinStock.getWaitInStockQty() - combinB.getInWhQty());
                        combinStocks.add(combinStock);
                    }
                    if (CollectionUtils.isNotEmpty(combinStocks)) {
                        opeWmsCombinStockService.saveOrUpdateBatch(combinStocks);
                    }
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInWhousePartsB> parts = new QueryWrapper<>();
                parts.eq(OpeInWhousePartsB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhousePartsB> partsBList = opeInWhousePartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBList)) {
                    opeInWhousePartsBService.removeByIds(partsBList.stream().map(OpeInWhousePartsB::getId).collect(Collectors.toList()));
                    // 修改库存信息
                    List<OpeWmsPartsStock> partsStocks = new ArrayList<>();
                    for (OpeInWhousePartsB partsB : partsBList) {
                        QueryWrapper<OpeWmsPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                        partsStockQueryWrapper.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        partsStockQueryWrapper.eq(OpeWmsPartsStock.COL_STOCK_TYPE, inWhouseOrder.getCountryType());
                        partsStockQueryWrapper.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(partsStockQueryWrapper);
                        partsStock.setWaitInStockQty(partsStock.getWaitInStockQty() - partsB.getInWhQty());
                        partsStocks.add(partsStock);
                    }
                    if (CollectionUtils.isNotEmpty(partsStocks)) {
                        opeWmsPartsStockService.saveOrUpdateBatch(partsStocks);
                    }
                }
                break;
        }
    }


    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        QueryWrapper<OpeInWhouseOrder> scooter = new QueryWrapper<>();
        scooter.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.SCOOTER.getValue());
        scooter.eq(OpeInWhouseOrder.COL_COUNTRY_TYPE, 1);
        scooter.eq(OpeInWhouseOrder.COL_SOURCE, 0);
        map.put("1", opeInWhouseOrderService.count(scooter));

        QueryWrapper<OpeInWhouseOrder> combin = new QueryWrapper<>();
        combin.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.COMBINATION.getValue());
        combin.eq(OpeInWhouseOrder.COL_COUNTRY_TYPE, 1);
        combin.eq(OpeInWhouseOrder.COL_SOURCE, 0);
        map.put("2", opeInWhouseOrderService.count(combin));

        QueryWrapper<OpeInWhouseOrder> parts = new QueryWrapper<>();
        parts.eq(OpeInWhouseOrder.COL_ORDER_TYPE, ProductTypeEnums.PARTS.getValue());
        parts.eq(OpeInWhouseOrder.COL_COUNTRY_TYPE, 1);
        parts.eq(OpeInWhouseOrder.COL_SOURCE, 0);
        map.put("3", opeInWhouseOrderService.count(parts));
        return map;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inWhouseDelete(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(NewInWhouseOrderStatusEnum.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        opeInWhouseOrderService.removeById(enter.getId());
        // 追加 入库单（车辆/组装件）新增的时候 会把库存的待入库数量增加  删除的时候要相应的减少
        if (1 == inWhouseOrder.getOrderType() || 2 == inWhouseOrder.getOrderType()) {
            changeStock(inWhouseOrder);
        }
        return new GeneralResult(enter.getRequestId());
    }


    // 入库单（车辆/组装件）新增的时候 会把库存的待入库数量增加  删除的时候要相应的减少
    public void changeStock(OpeInWhouseOrder inWhouseOrder) {
        switch (inWhouseOrder.getOrderType()) {
            case 1:
                // 车辆
                QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseScooterB> scooterBS = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                    for (OpeInWhouseScooterB scooterB : scooterBS) {
                        QueryWrapper<OpeWmsScooterStock> stockQueryWrapper = new QueryWrapper<>();
                        stockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        stockQueryWrapper.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        stockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE, inWhouseOrder.getCountryType());
                        stockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getOne(stockQueryWrapper);
                        if (StringManaConstant.entityIsNotNull(scooterStock)) {
                            scooterStock.setWaitInStockQty(scooterStock.getWaitInStockQty() - scooterB.getInWhQty());
                            scooterStockList.add(scooterStock);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }

            default:
                break;
            case 2:
                // 组装件
                QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseCombinB> combinBS = opeInWhouseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        QueryWrapper<OpeWmsCombinStock> combinQueryWrapper = new QueryWrapper<>();
                        combinQueryWrapper.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        combinQueryWrapper.eq(OpeWmsCombinStock.COL_STOCK_TYPE, inWhouseOrder.getCountryType());
                        combinQueryWrapper.last("limit 1");
                        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getOne(combinQueryWrapper);
                        if (StringManaConstant.entityIsNotNull(combinStock)) {
                            combinStock.setWaitInStockQty(combinStock.getWaitInStockQty() - combinB.getInWhQty());
                            combinStockList.add(combinStock);
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
        }
    }


    @Override
    public InWhouseDetailResult inWhouseDetail(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        InWhouseDetailResult result = new InWhouseDetailResult();
        result.setId(inWhouseOrder.getId());
        result.setInWhType(inWhouseOrder.getInWhType());
        result.setOrderType(inWhouseOrder.getOrderType());
        result.setInWhNo(inWhouseOrder.getInWhNo());
        result.setInWhStatus(inWhouseOrder.getInWhStatus());
        result.setRelationOrderId(inWhouseOrder.getRelationOrderId());
        result.setRelationOrderNo(inWhouseOrder.getRelationOrderNo());
        result.setRelationOrderType(inWhouseOrder.getRelationOrderType());
        result.setWhType(inWhouseOrder.getWhType());
        result.setRemark(inWhouseOrder.getRemark());
        // 入库单下面的产品明细
        switch (inWhouseOrder.getOrderType()) {
            // 为了找到剩余的可入库数量 增加下面的操作
            case 1:
                // scooter
                List<InWhouseDetailScooterResult> scooterResults = inWhouseOrderScooterBServiceMapper.scooterBs(inWhouseOrder.getId());
                if (CollectionUtils.isNotEmpty(scooterResults)) {
                    // 计算可入库数量 整车只会关联调拨单或者组装单
                    if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.ALLOCATE.getValue())) {
                        // 关联的是调拨单
                        inWhOrderRelationScooterAllocate(inWhouseOrder, scooterResults);
                    } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
                        // 关联的组装单
                        inWhOrderRelationScooterCombin(inWhouseOrder, scooterResults);
                    }
                }
                result.setScooters(scooterResults);
            default:
                break;
            case 2:
                // combin
                List<InWhouseDetailCombinResult> combinResults = inWhouseOrderCombinBServiceMapper.combinBs(inWhouseOrder.getId());
                if (CollectionUtils.isNotEmpty(combinResults)) {
                    // 计算可入库的数量
                    if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.ALLOCATE.getValue())) {
                        // 关联的是调拨单
                        inWhOrderRelationCombinAllocate(inWhouseOrder, combinResults);
                    } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
                        // 关联的组装单
                        inWhOrderRelationCombinCombin(inWhouseOrder, combinResults);
                    }
                }
                result.setCombins(combinResults);
                break;
            case 3:
                // parts
                List<InWhouseDetailPartsResult> partsResults = inWhouseOrderPartsBServiceMapper.partsBs(inWhouseOrder.getId());
                if (CollectionUtils.isNotEmpty(partsResults)) {
                    // 计算可入库数量
                    if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.ALLOCATE.getValue())) {
                        // 关联的是调拨单
                        inWhOrderRelationPartsAllocate(inWhouseOrder, partsResults);
                    } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
                        // 关联的组装单
                        inWhOrderRelationPartsCombin(inWhouseOrder, partsResults);
                    } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())) {
                        // 关联的生产采购单
                        inWhOrderRelationPartsPurchase(inWhouseOrder, partsResults);
                    }
                }
                result.setParts(partsResults);
                break;
        }
        // 操作动态
        List<OpTraceResult> traces = allocateOrderServiceMapper.allocateTrace(enter.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue());
        result.setOpTraces(traces);
        // 关联的单据（可能关联生产采购单或者组装单）
        // 先判断关联的是哪种单据
        List<PurchaseRelationOrderResult> relationOrderResults = new ArrayList<>();
        if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue()) && InWhTypeEnums.PURCHASE_IN_WHOUSE.getValue().equals(inWhouseOrder.getInWhType())) {
            // 生产采购单
            QueryWrapper<OpeProductionPurchaseOrder> purchaseQueryWrapper = new QueryWrapper<>();
            purchaseQueryWrapper.eq(OpeProductionPurchaseOrder.COL_ID, inWhouseOrder.getRelationOrderId());
            purchaseQueryWrapper.last("limit 1");
            OpeProductionPurchaseOrder purchaseOrder = opeProductionPurchaseOrderService.getOne(purchaseQueryWrapper);
            if (StringManaConstant.entityIsNotNull(purchaseOrder)) {
                PurchaseRelationOrderResult relationOrderResult = new PurchaseRelationOrderResult();
                relationOrderResult.setId(purchaseOrder.getId());
                relationOrderResult.setOrderNo(purchaseOrder.getPurchaseNo());
                relationOrderResult.setOrderType(OrderTypeEnums.FACTORY_PURCHAS.getValue());
                relationOrderResult.setCreatedTime(purchaseOrder.getCreatedTime());
                relationOrderResults.add(relationOrderResult);
            }
        } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue()) && InWhTypeEnums.PRODUCTIN_IN_WHOUSE.getValue().equals(inWhouseOrder.getInWhType())) {
            // 组装单
            QueryWrapper<OpeCombinOrder> combinOrderQueryWrapper = new QueryWrapper<>();
            combinOrderQueryWrapper.eq(OpeCombinOrder.COL_ID, inWhouseOrder.getRelationOrderId());
            combinOrderQueryWrapper.last("limit 1");
            OpeCombinOrder combinOrder = opeCombinOrderService.getOne(combinOrderQueryWrapper);
            if (StringManaConstant.entityIsNotNull(combinOrder)) {
                PurchaseRelationOrderResult relationOrderResult = new PurchaseRelationOrderResult();
                relationOrderResult.setId(combinOrder.getId());
                relationOrderResult.setOrderNo(combinOrder.getCombinNo());
                relationOrderResult.setOrderType(OrderTypeEnums.COMBIN_ORDER.getValue());
                relationOrderResult.setCreatedTime(combinOrder.getCreatedTime());
                relationOrderResults.add(relationOrderResult);
            }
        } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.ALLOCATE.getValue())) {
            // 调拨单
            QueryWrapper<OpeAllocateOrder> allocateQueryWrapper = new QueryWrapper<>();
            allocateQueryWrapper.eq(OpeAllocateOrder.COL_ID, inWhouseOrder.getRelationOrderId());
            allocateQueryWrapper.last("limit 1");
            OpeAllocateOrder allocateOrder = opeAllocateOrderService.getOne(allocateQueryWrapper);
            if (StringManaConstant.entityIsNotNull(allocateOrder)) {
                PurchaseRelationOrderResult relationOrderResult = new PurchaseRelationOrderResult();
                relationOrderResult.setId(allocateOrder.getId());
                relationOrderResult.setOrderNo(allocateOrder.getAllocateNo());
                relationOrderResult.setOrderType(OrderTypeEnums.ALLOCATE.getValue());
                relationOrderResult.setCreatedTime(allocateOrder.getCreatedTime());
                relationOrderResults.add(relationOrderResult);
            }
        }
        result.setRelationOrders(relationOrderResults);
        return result;
    }

    // 入库单关联车辆组装单
    private void inWhOrderRelationScooterCombin(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailScooterResult> scooterResults) {
        // 关联的是组装单 组装单关联的只有入库单
        // 已经入库的车辆入库单集合
        Map<String, List<OpeInWhouseScooterB>> inWhScooterMap = new HashMap<>();
        // 还没有入库的车辆入库单集合
        Map<String, List<OpeInWhouseScooterB>> unInWhScooterMap = new HashMap<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(combinOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, combinOrder.getId());
            combinInWhouseOrderQw.ne(OpeInWhouseOrder.COL_ID, inWhouseOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                    scooterBQueryWrapper.in(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> inWhScooters = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhScooters)) {
                        inWhScooterMap = inWhScooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhouseScooterB> scooterBWrapper = new QueryWrapper<>();
                    scooterBWrapper.in(OpeInWhouseScooterB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> unInWhScooters = opeInWhouseScooterBService.list(scooterBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhScooters)) {
                        unInWhScooterMap = unInWhScooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                for (InWhouseDetailScooterResult scooterResult : scooterResults) {
                    // 定义已经入库的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhScooterMap) && inWhScooterMap.size() > 0) {
                        for (String key : inWhScooterMap.keySet()) {
                            if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                                alreadyNum = alreadyNum + (inWhScooterMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhScooterMap) && unInWhScooterMap.size() > 0) {
                        for (String key : unInWhScooterMap.keySet()) {
                            if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                                alreadyNum = alreadyNum + (unInWhScooterMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getInWhQty).sum());
                            }
                        }
                    }
                    scooterResult.setAbleInWhQty(scooterResult.getCombinQty() - alreadyNum);
                }
            }
        }
    }


    // 入库单关联组装件组装单
    private void inWhOrderRelationCombinCombin(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailCombinResult> combinResults) {
        // 关联的是组装单 组装单关联的只有入库单
        // 已经入库的组装件入库单集合
        Map<Long, List<OpeInWhouseCombinB>> inWhCombinMap = new HashMap<>();
        // 还没有入库的组装件入库单集合
        Map<Long, List<OpeInWhouseCombinB>> unInWhCombinMap = new HashMap<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(combinOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, combinOrder.getId());
            combinInWhouseOrderQw.ne(OpeInWhouseOrder.COL_ID, inWhouseOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                    combinBQueryWrapper.in(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> inWhCombins = opeInWhouseCombinBService.list(combinBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhCombins)) {
                        inWhCombinMap = inWhCombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhouseCombinB> combinBWrapper = new QueryWrapper<>();
                    combinBWrapper.in(OpeInWhouseCombinB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> unInWhCombins = opeInWhouseCombinBService.list(combinBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhCombins)) {
                        unInWhCombinMap = unInWhCombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                for (InWhouseDetailCombinResult combinResult : combinResults) {
                    // 定义已经入库的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhCombinMap) && 0 < inWhCombinMap.size()) {
                        for (Long key : inWhCombinMap.keySet()) {
                            if (key.equals(combinResult.getProductionCombinBomId())) {
                                alreadyNum = alreadyNum + (inWhCombinMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhCombinMap) && 0 < unInWhCombinMap.size()) {
                        for (Long key : unInWhCombinMap.keySet()) {
                            if (key.equals(combinResult.getProductionCombinBomId())) {
                                alreadyNum = alreadyNum + (unInWhCombinMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getInWhQty).sum());
                            }
                        }
                    }
                    combinResult.setAbleInWhQty(combinResult.getCombinQty() - alreadyNum);
                }
            }
        }
    }


    // 入库单关联部件组装单
    private void inWhOrderRelationPartsCombin(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailPartsResult> partsResults) {
        // 关联的是组装单 组装单关联的只有入库单
        // 已经入库的车辆入库单集合
        Map<Long, List<OpeInWhousePartsB>> inWhCombinMap = new HashMap<>();
        // 还没有入库的车辆入库单集合
        Map<Long, List<OpeInWhousePartsB>> unInWhCombinMap = new HashMap<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(combinOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, combinOrder.getId());
            combinInWhouseOrderQw.ne(OpeInWhouseOrder.COL_ID, inWhouseOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhousePartsB> partsBQueryWrapper = new QueryWrapper<>();
                    partsBQueryWrapper.in(OpeInWhousePartsB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> inWhpartss = opeInWhousePartsBService.list(partsBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhpartss)) {
                        inWhCombinMap = inWhpartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhousePartsB> partsBWrapper = new QueryWrapper<>();
                    partsBWrapper.in(OpeInWhousePartsB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> unInWhPartss = opeInWhousePartsBService.list(partsBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhPartss)) {
                        unInWhCombinMap = unInWhPartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                for (InWhouseDetailPartsResult partsResult : partsResults) {
                    // 定义已经入库的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhCombinMap) && 0 < inWhCombinMap.size()) {
                        for (Long key : inWhCombinMap.keySet()) {
                            if (key.equals(partsResult.getPartsId())) {
                                alreadyNum = alreadyNum + (inWhCombinMap.get(key).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhCombinMap) && 0 < unInWhCombinMap.size()) {
                        for (Long key : unInWhCombinMap.keySet()) {
                            if (key.equals(partsResult.getPartsId())) {
                                alreadyNum = alreadyNum + (unInWhCombinMap.get(key).stream().mapToInt(OpeInWhousePartsB::getInWhQty).sum());
                            }
                        }
                    }
                    partsResult.setAbleInWhQty(partsResult.getPurchaseQty() - alreadyNum);
                }
            }
        }
    }


    // 入库单关联部件生产采购单
    private void inWhOrderRelationPartsPurchase(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailPartsResult> partsResults) {
        // 关联的是组装单 组装单关联的只有入库单
        // 已经入库的车辆入库单集合
        Map<Long, List<OpeInWhousePartsB>> inWhCombinMap = new HashMap<>();
        // 还没有入库的车辆入库单集合
        Map<Long, List<OpeInWhousePartsB>> unInWhCombinMap = new HashMap<>();
        OpeProductionPurchaseOrder purchaseOrder = opeProductionPurchaseOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(purchaseOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, purchaseOrder.getId());
            combinInWhouseOrderQw.ne(OpeInWhouseOrder.COL_ID, inWhouseOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhousePartsB> partsBQueryWrapper = new QueryWrapper<>();
                    partsBQueryWrapper.in(OpeInWhousePartsB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> inWhpartss = opeInWhousePartsBService.list(partsBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhpartss)) {
                        inWhCombinMap = inWhpartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhousePartsB> partsBWrapper = new QueryWrapper<>();
                    partsBWrapper.in(OpeInWhousePartsB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> unInWhPartss = opeInWhousePartsBService.list(partsBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhPartss)) {
                        unInWhCombinMap = unInWhPartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                for (InWhouseDetailPartsResult partsResult : partsResults) {
                    // 定义已经入库的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhCombinMap) && 0 < inWhCombinMap.size()) {
                        for (Long key : inWhCombinMap.keySet()) {
                            if (key.equals(partsResult.getPartsId())) {
                                alreadyNum = alreadyNum + (inWhCombinMap.get(key).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhCombinMap) && 0 < unInWhCombinMap.size()) {
                        for (Long key : unInWhCombinMap.keySet()) {
                            if (key.equals(partsResult.getPartsId())) {
                                alreadyNum = alreadyNum + (unInWhCombinMap.get(key).stream().mapToInt(OpeInWhousePartsB::getInWhQty).sum());
                            }
                        }
                    }
                    partsResult.setAbleInWhQty(partsResult.getPurchaseQty() - alreadyNum);
                }
            }
        }
    }


    // 入库单关联车辆调拨单 计算可用的数量
    private void inWhOrderRelationScooterAllocate(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailScooterResult> scooterResults) {
        // 存放中国仓库  已经出库的车辆信息
        Map<String, List<OpeOutWhScooterB>> outMap = new HashMap<>();
        // 存放中国仓库  还没有出库的车辆信息
        Map<String, List<OpeOutWhScooterB>> unOutMap = new HashMap<>();

        // 存放法国仓库  已经入库的车辆信息
        Map<String, List<OpeInWhouseScooterB>> frInMap = new HashMap<>();
        // 存放法国仓库  还没有入库的车辆信息
        Map<String, List<OpeInWhouseScooterB>> frUnInMap = new HashMap<>();
        // 关联的是调拨单（法国仓库）  先找到调拨单
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // 找到调拨单产生的中国的出库单（就是法国要入库的）
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // 已经出库的出库单集合
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhScooterB> outscooterQw = new QueryWrapper<>();
                    outscooterQw.in(OpeOutWhScooterB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhScooterB> outscooters = opeOutWhScooterBService.list(outscooterQw);
                    if (CollectionUtils.isNotEmpty(outscooters)) {
                        outMap = outscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                    }
                }
                // 还没有出库的出库单集合
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhScooterB> unOutscooterQw = new QueryWrapper<>();
                    unOutscooterQw.in(OpeOutWhScooterB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhScooterB> unOutscooters = opeOutWhScooterBService.list(unOutscooterQw);
                    if (CollectionUtils.isNotEmpty(unOutscooters)) {
                        unOutMap = unOutscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey(o)));
                    }
                }
            }
            // 找到调拨单产生的法国仓库的入库单
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 法国仓库已经入库的入库单
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhouseScooterB> inscooterQw = new QueryWrapper<>();
                    inscooterQw.in(OpeInWhouseScooterB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> inscooters = opeInWhouseScooterBService.list(inscooterQw);
                    if (CollectionUtils.isNotEmpty(inscooters)) {
                        frInMap = inscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                // 法国仓库还没有入库的入库单
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10 && !o.getId().equals(inWhouseOrder.getId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhouseScooterB> inscooterQw = new QueryWrapper<>();
                    inscooterQw.in(OpeInWhouseScooterB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> uninscooters = opeInWhouseScooterBService.list(inscooterQw);
                    if (CollectionUtils.isNotEmpty(uninscooters)) {
                        frUnInMap = uninscooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
            }
        }
        for (InWhouseDetailScooterResult scooterResult : scooterResults) {
            // 定义已经入库的数量
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // 中国仓库 已经出库的出库单
                for (String key : outMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhScooterB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // 中国仓库 还没有出库的出库单
                for (String key : unOutMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhScooterB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (String key : frInMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (String key : frUnInMap.keySet()) {
                    if ((scooterResult.getGroupId() + "" + scooterResult.getColorId()).equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getInWhQty).sum());
                    }
                }
            }
            scooterResult.setAbleInWhQty(scooterResult.getCombinQty() - alreadyNum);
        }
    }


    // 入库单关联组装件调拨单 计算可用的数量
    private void inWhOrderRelationCombinAllocate(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailCombinResult> combinResults) {
        // 存放中国仓库  已经出库的车辆信息
        Map<Long, List<OpeOutWhCombinB>> outMap = new HashMap<>();
        // 存放中国仓库  还没有出库的车辆信息
        Map<Long, List<OpeOutWhCombinB>> unOutMap = new HashMap<>();

        // 存放法国仓库  已经入库的车辆信息
        Map<Long, List<OpeInWhouseCombinB>> frInMap = new HashMap<>();
        // 存放法国仓库  还没有入库的车辆信息
        Map<Long, List<OpeInWhouseCombinB>> frUnInMap = new HashMap<>();
        // 关联的是调拨单（法国仓库）  先找到调拨单
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // 找到调拨单产生的中国的出库单（就是法国要入库的）
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // 已经出库的出库单集合
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhCombinB> outcombinQw = new QueryWrapper<>();
                    outcombinQw.in(OpeOutWhCombinB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhCombinB> outcombins = opeOutWhCombinBService.list(outcombinQw);
                    if (CollectionUtils.isNotEmpty(outcombins)) {
                        outMap = outcombins.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                    }
                }
                // 还没有出库的出库单集合
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhCombinB> unOutcombinQw = new QueryWrapper<>();
                    unOutcombinQw.in(OpeOutWhCombinB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhCombinB> unOutcombins = opeOutWhCombinBService.list(unOutcombinQw);
                    if (CollectionUtils.isNotEmpty(unOutcombins)) {
                        unOutMap = unOutcombins.stream().collect(Collectors.groupingBy(OpeOutWhCombinB::getProductionCombinBomId));
                    }
                }
            }
            // 找到调拨单产生的法国仓库的入库单
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 法国仓库已经入库的入库单
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhouseCombinB> incombinQw = new QueryWrapper<>();
                    incombinQw.in(OpeInWhouseCombinB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> incombins = opeInWhouseCombinBService.list(incombinQw);
                    if (CollectionUtils.isNotEmpty(incombins)) {
                        frInMap = incombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                // 法国仓库还没有入库的入库单
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10 && !o.getId().equals(inWhouseOrder.getId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhouseCombinB> incombinQw = new QueryWrapper<>();
                    incombinQw.in(OpeInWhouseCombinB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> unincombins = opeInWhouseCombinBService.list(incombinQw);
                    if (CollectionUtils.isNotEmpty(unincombins)) {
                        frUnInMap = unincombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
            }
        }
        for (InWhouseDetailCombinResult combinResult : combinResults) {
            // 定义已经入库的数量
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // 中国仓库 已经出库的出库单
                for (Long key : outMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhCombinB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // 中国仓库 还没有出库的出库单
                for (Long key : unOutMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhCombinB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (Long key : frInMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (Long key : frUnInMap.keySet()) {
                    if (combinResult.getProductionCombinBomId().equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getInWhQty).sum());
                    }
                }
            }
            combinResult.setAbleInWhQty(combinResult.getCombinQty() - alreadyNum);
        }
    }


    // 入库单关联部件的调拨单 计算可用的数量
    private void inWhOrderRelationPartsAllocate(OpeInWhouseOrder inWhouseOrder, List<InWhouseDetailPartsResult> partsResults) {
        // 存放中国仓库  已经出库的车辆信息
        Map<Long, List<OpeOutWhPartsB>> outMap = new HashMap<>();
        // 存放中国仓库  还没有出库的车辆信息
        Map<Long, List<OpeOutWhPartsB>> unOutMap = new HashMap<>();

        // 存放法国仓库  已经入库的车辆信息
        Map<Long, List<OpeInWhousePartsB>> frInMap = new HashMap<>();
        // 存放法国仓库  还没有入库的车辆信息
        Map<Long, List<OpeInWhousePartsB>> frUnInMap = new HashMap<>();
        // 关联的是调拨单（法国仓库）  先找到调拨单
        OpeAllocateOrder allocateOrder = opeAllocateOrderService.getById(inWhouseOrder.getRelationOrderId());
        if (StringManaConstant.entityIsNotNull(allocateOrder)) {
            // 找到调拨单产生的中国的出库单（就是法国要入库的）
            List<OpeOutWhouseOrder> outWhouseOrders = inWhouseOrderServiceMapper.outOrderByAllocateId(allocateOrder.getId());
            if (CollectionUtils.isNotEmpty(outWhouseOrders)) {
                // 已经出库的出库单集合
                List<OpeOutWhouseOrder> outs = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 20).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(outs)) {
                    QueryWrapper<OpeOutWhPartsB> outpartsQw = new QueryWrapper<>();
                    outpartsQw.in(OpeOutWhPartsB.COL_OUT_WH_ID, outs.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhPartsB> outpartss = opeOutWhPartsBService.list(outpartsQw);
                    if (CollectionUtils.isNotEmpty(outpartss)) {
                        outMap = outpartss.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                    }
                }
                // 还没有出库的出库单集合
                List<OpeOutWhouseOrder> unOuts = outWhouseOrders.stream().filter(o -> o.getOutWhStatus() == 0 || o.getOutWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unOuts)) {
                    QueryWrapper<OpeOutWhPartsB> unOutpartsQw = new QueryWrapper<>();
                    unOutpartsQw.in(OpeOutWhPartsB.COL_OUT_WH_ID, unOuts.stream().map(OpeOutWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeOutWhPartsB> unOutpartss = opeOutWhPartsBService.list(unOutpartsQw);
                    if (CollectionUtils.isNotEmpty(unOutpartss)) {
                        unOutMap = unOutpartss.stream().collect(Collectors.groupingBy(OpeOutWhPartsB::getPartsId));
                    }
                }
            }
            // 找到调拨单产生的法国仓库的入库单
            QueryWrapper<OpeInWhouseOrder> inWhouseOrderQw = new QueryWrapper<>();
            inWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, allocateOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(inWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 法国仓库已经入库的入库单
                List<OpeInWhouseOrder> frInWhouseOrders = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frInWhouseOrders)) {
                    QueryWrapper<OpeInWhousePartsB> partsQw = new QueryWrapper<>();
                    partsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, frInWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> inpartss = opeInWhousePartsBService.list(partsQw);
                    if (CollectionUtils.isNotEmpty(inpartss)) {
                        frInMap = inpartss.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
                // 法国仓库还没有入库的入库单
                List<OpeInWhouseOrder> frUnWhouseOrders =
                        inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10 && !o.getId().equals(inWhouseOrder.getId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(frUnWhouseOrders)) {
                    QueryWrapper<OpeInWhousePartsB> inpartsQw = new QueryWrapper<>();
                    inpartsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, frUnWhouseOrders.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhousePartsB> unincombins = opeInWhousePartsBService.list(inpartsQw);
                    if (CollectionUtils.isNotEmpty(unincombins)) {
                        frUnInMap = unincombins.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                    }
                }
            }
        }
        for (InWhouseDetailPartsResult partsResult : partsResults) {
            // 定义已经入库的数量
            Integer alreadyNum = 0;
            if (StringManaConstant.entityIsNotNull(outMap) && 0 < outMap.size()) {
                // 中国仓库 已经出库的出库单
                for (Long key : outMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (outMap.get(key).stream().mapToInt(OpeOutWhPartsB::getAlreadyOutWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(unOutMap) && 0 < unOutMap.size()) {
                // 中国仓库 还没有出库的出库单
                for (Long key : unOutMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (unOutMap.get(key).stream().mapToInt(OpeOutWhPartsB::getQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frInMap) && 0 < frInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (Long key : frInMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (frInMap.get(key).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum());
                    }
                }
            }
            if (StringManaConstant.entityIsNotNull(frUnInMap) && 0 < frUnInMap.size()) {
                // 法国仓库  已经入库的车辆信息
                for (Long key : frUnInMap.keySet()) {
                    if (partsResult.getPartsId().equals(key)) {
                        alreadyNum = alreadyNum + (frUnInMap.get(key).stream().mapToInt(OpeInWhousePartsB::getInWhQty).sum());
                    }
                }
            }
            partsResult.setAbleInWhQty(partsResult.getPurchaseQty() - alreadyNum);
        }
    }


    // 按照颜色和车型  进行嵌套分组 (车辆)
    private static String fetchGroupKey(OpeOutWhScooterB scooterB) {
        // 按照分组和颜色进行分组
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }

    // 按照颜色和车型  进行嵌套分组（车辆）
    private static String fetchGroupKey1(OpeInWhouseScooterB scooterB) {
        // 按照分组和颜色进行分组
        return scooterB.getGroupId() + "" + scooterB.getColorId();
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inWhConfirm(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if ((inWhouseOrder.getOrderType().equals(ProductTypeEnums.SCOOTER.getValue()) || inWhouseOrder.getOrderType().equals(ProductTypeEnums.COMBINATION.getValue())) && !inWhouseOrder.getInWhStatus().equals(NewInWhouseOrderStatusEnum.DRAFT.getValue())) {
            // 整车和组装件 是草稿状态的时候才能点击
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        if (inWhouseOrder.getOrderType().equals(ProductTypeEnums.PARTS.getValue()) && !inWhouseOrder.getInWhStatus().equals(NewInWhouseOrderStatusEnum.DRAFT.getValue())) {
            // 部件 是待入库状态的时候才能点击
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);

        // 入库单确认入库 处理字表的实际入库数量
        comfirmInHandleB(inWhouseOrder);

        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
//        if (inWhouseOrder.getOrderType().equals(ProductTypeEnums.PARTS.getValue())){
        if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())) {
            // 如果是部件入库单  点击确认入库  需要改变部件采购单的状态
            productionPurchasService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(), inWhouseOrder.getId(), enter.getUserId());
        } else if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())) {
            // 如果是关联的组装单  点击确认入库  需要改变组装单的状态
            productionAssemblyOrderService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(), inWhouseOrder.getId(), enter.getUserId());
        }
        // 入库单确认入库的是时候  库存待入库数量减少  可用数量增加
        try {
            wmsMaterialStockService.inStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), 1, enter.getUserId(), inWhouseOrder.getInWhType());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }

    private void comfirmInHandleB(OpeInWhouseOrder inWhouseOrder) {
        switch (inWhouseOrder.getOrderType()) {
            case 1:
                QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseScooterB> scooterBS = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)) {
                    for (OpeInWhouseScooterB scooterB : scooterBS) {
                        scooterB.setActInWhQty(scooterB.getInWhQty());
                    }
                    opeInWhouseScooterBService.saveOrUpdateBatch(scooterBS);
                }
            default:
                break;
            case 2:
                QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseCombinB> combinBS = opeInWhouseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        combinB.setActInWhQty(combinB.getInWhQty());
                    }
                    opeInWhouseCombinBService.saveOrUpdateBatch(combinBS);
                }
                break;
            case 3:
                QueryWrapper<OpeInWhousePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeInWhousePartsB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhousePartsB> partsBS = opeInWhousePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeInWhousePartsB partsB : partsBS) {
                        partsB.setActInWhQty(partsB.getInWhQty());
                    }
                    opeInWhousePartsBService.saveOrUpdateBatch(partsBS);
                }
        }
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inWhReadyQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(NewInWhouseOrderStatusEnum.DRAFT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        //inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.READY_QC.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(),
                inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        // todo 生成质检单
        if (StringManaConstant.entityIsNotNull(inWhouseOrder.getRelationOrderType()) && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())) {
            // 如果是部件 将对应的部件采购单的状态变为待入库
            productionPurchasService.statusToBeStored(inWhouseOrder.getRelationOrderId(), enter.getUserId());
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<InWhRelationOrderResult> relationPurchaseOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = productionPurchasServiceMapper.relationOrderData(enter);
        return list;
    }

    @Override
    public List<SaveOrUpdatePartsBEnter> relationPurchaseOrderPartsData(IdEnter enter) {
        List<SaveOrUpdatePartsBEnter> list = productionPurchasServiceMapper.relationPurchaseOrderPartsData(enter);
        if (CollectionUtils.isNotEmpty(list)) {
            QueryWrapper<OpeInWhouseOrder> qw = new QueryWrapper<>();
            qw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, enter.getId());
            qw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_TYPE, OrderTypeEnums.FACTORY_PURCHAS.getValue());
            List<OpeInWhouseOrder> inWhouseOrderList = opeInWhouseOrderService.list(qw);
            if (CollectionUtils.isNotEmpty(inWhouseOrderList)) {
                // 已入库的入库单的集合
                List<OpeInWhouseOrder> inList = inWhouseOrderList.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                // 已入库入库单的部件的集合
                List<OpeInWhousePartsB> inPartsBList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(inList)) {
                    // 找到所有已经入库的入库单的所有部件明细
                    QueryWrapper<OpeInWhousePartsB> partsQw = new QueryWrapper<>();
                    partsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, inList.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    inPartsBList = opeInWhousePartsBService.list(partsQw);
                }
                // 还未入库的入库单的集合
                List<OpeInWhouseOrder> unInList = inWhouseOrderList.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                // 还未入库的入库单的部件的集合
                List<OpeInWhousePartsB> unPartsBList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(unInList)) {
                    // 找到所有已经入库的入库单的所有部件明细
                    QueryWrapper<OpeInWhousePartsB> unPartsQw = new QueryWrapper<>();
                    unPartsQw.in(OpeInWhousePartsB.COL_IN_WH_ID, unInList.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    unPartsBList = opeInWhousePartsBService.list(unPartsQw);
                }
                for (SaveOrUpdatePartsBEnter bEnter : list) {
                    Integer alreadyNum = 0;
                    if (CollectionUtils.isNotEmpty(inPartsBList)) {
                        Map<Long, List<OpeInWhousePartsB>> inPartsMap = inPartsBList.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                        for (Long partsId : inPartsMap.keySet()) {
                            if (bEnter.getPartsId().equals(partsId)) {
                                alreadyNum = alreadyNum + inPartsMap.get(partsId).stream().mapToInt(OpeInWhousePartsB::getActInWhQty).sum();
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(unPartsBList)) {
                        Map<Long, List<OpeInWhousePartsB>> unPartsMap = unPartsBList.stream().collect(Collectors.groupingBy(OpeInWhousePartsB::getPartsId));
                        for (Long partsId : unPartsMap.keySet()) {
                            if (bEnter.getPartsId().equals(partsId)) {
                                alreadyNum = alreadyNum + unPartsMap.get(partsId).stream().mapToInt(OpeInWhousePartsB::getInWhQty).sum();
                            }
                        }
                    }
                    bEnter.setAbleInWhQty(bEnter.getPurchaseQty() - alreadyNum);
                }
            } else {
                list.forEach(o -> o.setAbleInWhQty(o.getPurchaseQty()));
            }
        }
        // 追加 把可入库的数量为0的数据过滤掉 不反回给前端
        List<SaveOrUpdatePartsBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SaveOrUpdatePartsBEnter bEnter : list) {
                if (bEnter.getAbleInWhQty() > 0) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }

    @Override
    public List<InWhRelationOrderResult> relationCombinOrderData(KeywordEnter enter) {
        List<InWhRelationOrderResult> list = productionAssemblyOrderServiceMapper.relationOrderData(enter);
        return list;
    }

    @Override
    public List<SaveOrUpdateCombinBEnter> relationCombinOrderCombinData(IdEnter enter) {
        List<SaveOrUpdateCombinBEnter> list = productionAssemblyOrderServiceMapper.relationCombinOrderCombinData(enter);
        // 追加 计算每个部件的可入库数量
        if (CollectionUtils.isNotEmpty(list)) {
            combinCombinAbleNum(enter, list);
        }
        // 追加 把可入库的数量为0的过滤掉
        List<SaveOrUpdateCombinBEnter> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SaveOrUpdateCombinBEnter bEnter : list) {
                if (bEnter.getAbleInWhQty() > 0) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }


    // 计算组装件的可入库数量
    private void combinCombinAbleNum(IdEnter enter, List<SaveOrUpdateCombinBEnter> list) {
        // 已经入库的组装件入库单集合
        Map<Long, List<OpeInWhouseCombinB>> inWhCombinMap = new HashMap<>();
        // 还没有入库的组装件入库单集合
        Map<Long, List<OpeInWhouseCombinB>> unInWhCombinMap = new HashMap<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNotNull(combinOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, combinOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                    combinBQueryWrapper.in(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> inWhCombins = opeInWhouseCombinBService.list(combinBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhCombins)) {
                        inWhCombinMap = inWhCombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhouseCombinB> combinBWrapper = new QueryWrapper<>();
                    combinBWrapper.in(OpeInWhouseCombinB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseCombinB> unInWhCombins = opeInWhouseCombinBService.list(combinBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhCombins)) {
                        unInWhCombinMap = unInWhCombins.stream().collect(Collectors.groupingBy(OpeInWhouseCombinB::getProductionCombinBomId));
                    }
                }
                for (SaveOrUpdateCombinBEnter combinResult : list) {
                    // 定义需要减去的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhCombinMap) && 0 < inWhCombinMap.size()) {
                        for (Long key : inWhCombinMap.keySet()) {
                            if (key.equals(combinResult.getProductionCombinBomId())) {
                                alreadyNum = alreadyNum + (inWhCombinMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhCombinMap) && 0 < unInWhCombinMap.size()) {
                        for (Long key : unInWhCombinMap.keySet()) {
                            if (key.equals(combinResult.getProductionCombinBomId())) {
                                alreadyNum = alreadyNum + (unInWhCombinMap.get(key).stream().mapToInt(OpeInWhouseCombinB::getInWhQty).sum());
                            }
                        }
                    }
                    combinResult.setAbleInWhQty(combinResult.getCombinQty() - alreadyNum);
                }
            } else {
                list.forEach(o -> o.setAbleInWhQty(o.getCombinQty()));
            }
        }
    }


    @Override
    public List<SaveOrUpdateScooterBEnter> relationCombinOrderScooterData(IdEnter enter) {
        List<SaveOrUpdateScooterBEnter> list = productionAssemblyOrderServiceMapper.relationCombinOrderScooterData(enter);
        List<SaveOrUpdateScooterBEnter> result = new ArrayList<>();
        // 追加
        if (CollectionUtils.isNotEmpty(list)) {
            // 计算可入库的车辆的数量
            scooterCombinAbleNum(enter, list);
            // 追加 把可入库数量为0的数据过滤掉
            for (SaveOrUpdateScooterBEnter bEnter : list) {
                if (0 < bEnter.getAbleInWhQty()) {
                    result.add(bEnter);
                }
            }
        }
        return result;
    }


    private void scooterCombinAbleNum(IdEnter enter, List<SaveOrUpdateScooterBEnter> list) {
        // 已经入库的车辆入库单集合
        Map<String, List<OpeInWhouseScooterB>> inWhScooterMap = new HashMap<>();
        // 还没有入库的车辆入库单集合
        Map<String, List<OpeInWhouseScooterB>> unInWhScooterMap = new HashMap<>();
        OpeCombinOrder combinOrder = opeCombinOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNotNull(combinOrder)) {
            // 找到组装单所对应的入库单
            QueryWrapper<OpeInWhouseOrder> combinInWhouseOrderQw = new QueryWrapper<>();
            combinInWhouseOrderQw.eq(OpeInWhouseOrder.COL_RELATION_ORDER_ID, combinOrder.getId());
            List<OpeInWhouseOrder> inWhouseOrders = opeInWhouseOrderService.list(combinInWhouseOrderQw);
            if (CollectionUtils.isNotEmpty(inWhouseOrders)) {
                // 已经入库的入库单
                List<OpeInWhouseOrder> inWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() == 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(inWhouses)) {
                    QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                    scooterBQueryWrapper.in(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> inWhScooters = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                    if (CollectionUtils.isNotEmpty(inWhScooters)) {
                        inWhScooterMap = inWhScooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                // 还没有入库的入库单
                List<OpeInWhouseOrder> unInWhouses = inWhouseOrders.stream().filter(o -> o.getInWhStatus() != 10).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(unInWhouses)) {
                    QueryWrapper<OpeInWhouseScooterB> scooterBWrapper = new QueryWrapper<>();
                    scooterBWrapper.in(OpeInWhouseScooterB.COL_IN_WH_ID, unInWhouses.stream().map(OpeInWhouseOrder::getId).collect(Collectors.toList()));
                    List<OpeInWhouseScooterB> unInWhScooters = opeInWhouseScooterBService.list(scooterBWrapper);
                    if (CollectionUtils.isNotEmpty(unInWhScooters)) {
                        unInWhScooterMap = unInWhScooters.stream().collect(Collectors.groupingBy(o -> fetchGroupKey1(o)));
                    }
                }
                for (SaveOrUpdateScooterBEnter bEnter : list) {
                    // 定义已经入库的数量
                    Integer alreadyNum = 0;
                    if (StringManaConstant.entityIsNotNull(inWhScooterMap) && 0 < inWhScooterMap.size()) {
                        for (String key : inWhScooterMap.keySet()) {
                            if (key.equals(bEnter.getGroupId() + "" + bEnter.getColorId())) {
                                alreadyNum = alreadyNum + (inWhScooterMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getActInWhQty).sum());
                            }
                        }
                    }
                    if (StringManaConstant.entityIsNotNull(unInWhScooterMap) && 0 < unInWhScooterMap.size()) {
                        for (String key : unInWhScooterMap.keySet()) {
                            if (key.equals(bEnter.getGroupId() + "" + bEnter.getColorId())) {
                                alreadyNum = alreadyNum + (unInWhScooterMap.get(key).stream().mapToInt(OpeInWhouseScooterB::getInWhQty).sum());
                            }
                        }
                    }
                    bEnter.setAbleInWhQty(bEnter.getCombinQty() - alreadyNum);
                }
            } else {
                list.forEach(o -> o.setAbleInWhQty(o.getCombinQty()));
            }
        }
    }


    // 模拟rps的操作 开始质检
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult startQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.WAIT_INSPECTED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.INSPECTING.getValue());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(), inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }


    // 模拟rps的操作 完成质检
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult finishQc(IdEnter enter) {
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inWhouseOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        if (!inWhouseOrder.getInWhStatus().equals(InWhouseOrderStatusEnum.INSPECTING.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ORDER_STATUS_ERROR.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.WAIT_IN_WH.getValue());
        inWhouseOrder.setUpdatedBy(enter.getUserId());
        inWhouseOrder.setUpdatedTime(new Date());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);
//        // todo 为了流程能走下去  这里先把部件的明细实际入库数量设置为可入库数量
//        QueryWrapper<OpeInWhousePartsB> qw = new QueryWrapper<>();
//        qw.eq(OpeInWhousePartsB.COL_IN_WH_ID,inWhouseOrder.getId());
//        List<OpeInWhousePartsB> whousePartsBList = opeInWhousePartsBService.list(qw);
//        if(CollectionUtils.isNotEmpty(whousePartsBList)){
//            for (OpeInWhousePartsB partsB : whousePartsBList) {
//                partsB.setActInWhQty(partsB.getInWhQty());
//            }
//            opeInWhousePartsBService.saveOrUpdateBatch(whousePartsBList);
//        }
        // 入库单变为待入库的时候，需要在库存中插入数据，待入库数量（这里应该是库存的第一步）
        try {
            wmsMaterialStockService.waitInStock(inWhouseOrder.getOrderType(), inWhouseOrder.getId(), 1, enter.getUserId());
        } catch (Exception e) {

        }
        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, inWhouseOrder.getInWhStatus(), OrderTypeEnums.FACTORY_INBOUND.getValue(), inWhouseOrder.getId(), inWhouseOrder.getRemark());
        orderStatusFlowEnter.setUserId(enter.getUserId());
        orderStatusFlowService.save(orderStatusFlowEnter);
        return new GeneralResult(enter.getRequestId());
    }
}
