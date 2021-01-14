package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderOperationTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatGroupMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsPartsStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 成品库实现类
 * @author: Aleks
 * @create: 2020/12/28 15:00
 */
@Service
@Slf4j
public class WmsFinishStockServiceImpl implements WmsFinishStockService {

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private OpeProductionScooterBomMapper opeProductionScooterBomMapper;

    @Autowired
    private OpeProductionPartsRelationMapper opeProductionPartsRelationMapper;

    @Autowired
    private OpeWmsPartsStockMapper opeWmsPartsStockMapper;

    @Autowired
    private OpeColorMapper opeColorMapper;

    @Autowired
    private OpeSpecificatGroupMapper opeSpecificatGroupMapper;

    @Autowired
    private OpeInWhouseOrderService opeInWhouseOrderService;

    @Autowired
    private OpeOutWhouseOrderService opeOutWhouseOrderService;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpeWmsStockRecordService opeWmsStockRecordService;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private ProductionOrderTraceService productionOrderTraceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private ProductionPurchasService productionPurchasService;

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @Autowired
    private OpeWmsQualifiedCombinStockService opeWmsQualifiedCombinStockService;

    @Reference
    private IdAppService idAppService;



    /**
     * 成品库车辆库存列表
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishScooterListResult> finishScooterList(WmsFinishScooterListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishScooterListResult> list = wmsFinishStockMapper.finishScooterList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 成品库车辆库存详情
     * @param enter
     * @return
     */
    @Override
    public WmsfinishScooterDetailResult finishScooterDetail(IdEnter enter) {
        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getById(enter.getId());
        if (scooterStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishScooterDetailResult result = wmsFinishStockMapper.finishScooterDetail(enter.getId());
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    /**
     * 库存统计
     * @param enter
     * @return
     */
    @Override
    public WmsStockCountResult wmsStockCount(WmsStockCountEnter enter) {
        WmsStockCountResult result = new WmsStockCountResult();
        switch (enter.getProductType()){
            case 1:
                //车辆
                result = wmsFinishStockMapper.wmsScooterStockCount(enter.getStockType());
            default:
                break;
            case 2:
                // 组装件
                result = wmsFinishStockMapper.wmsCombinStockCount(enter.getStockType());
                break;
            case 3:
                // 部件
                result = wmsFinishStockMapper.wmsPartsStockCount(enter.getStockType());
                break;
        }
        if (result == null){
            result = new WmsStockCountResult();
        }
        return result;
    }


    /**
     * 成品库tab的数量统计（只有车辆和组装件）
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> finishStockTabCount(WmsStockCountEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 车辆
        QueryWrapper<OpeWmsScooterStock> scooter = new QueryWrapper<>();
        scooter.eq(OpeWmsScooterStock.COL_STOCK_TYPE,enter.getStockType());
        map.put("1",opeWmsScooterStockService.count(scooter));

        // 组装件
        QueryWrapper<OpeWmsCombinStock> combin = new QueryWrapper<>();
        combin.eq(OpeWmsCombinStock.COL_STOCK_TYPE,enter.getStockType());
        map.put("2",opeWmsCombinStockService.count(combin));
        return map;
    }


    /**
     * 剩下的原料（部件）还可生产多少车
     * @param enter
     * @return
     */
    @Override
    public List<AbleProductionScooterResult> ableProductionScooter(GeneralEnter enter) {
        List<AbleProductionScooterResult> result = new ArrayList<>();

        // 查询整车bom表
        LambdaQueryWrapper<OpeProductionScooterBom> bomWrapper = new LambdaQueryWrapper<>();
        bomWrapper.eq(OpeProductionScooterBom::getDr, DelStatusEnum.VALID.getCode());
        bomWrapper.eq(OpeProductionScooterBom::getBomStatus, 1);
        List<OpeProductionScooterBom> bomList = opeProductionScooterBomMapper.selectList(bomWrapper);
        if (CollectionUtils.isEmpty(bomList)) {
            return result;
        }

        for (OpeProductionScooterBom bom : bomList) {
            AbleProductionScooterResult model = new AbleProductionScooterResult();
            Long productionId = bom.getId();
            Long groupId = bom.getGroupId();
            Long colorId = bom.getColorId();

            // 查询部件表
            LambdaQueryWrapper<OpeProductionPartsRelation> relationWrapper = new LambdaQueryWrapper<>();
            relationWrapper.eq(OpeProductionPartsRelation::getDr, DelStatusEnum.VALID.getCode());
            relationWrapper.eq(OpeProductionPartsRelation::getProductionId, productionId);
            List<OpeProductionPartsRelation> relationList = opeProductionPartsRelationMapper.selectList(relationWrapper);
            if (CollectionUtils.isEmpty(relationList)) {
                continue;
            }

            List<Integer> numList = Lists.newArrayList();
            for (int i = 0; i < relationList.size(); i++) {
                OpeProductionPartsRelation relation = relationList.get(i);
                Long partsId = relation.getPartsId();
                Integer partsQty = relation.getPartsQty();

                // 查询库存表中国仓库此部件的可用库存数量
                LambdaQueryWrapper<OpeWmsPartsStock> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsPartsStock::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeWmsPartsStock::getStockType, 1);
                wrapper.eq(OpeWmsPartsStock::getPartsId, partsId);
                wrapper.gt(OpeWmsPartsStock::getAbleStockQty, 0);
                wrapper.orderByDesc(OpeWmsPartsStock::getCreatedTime);
                List<OpeWmsPartsStock> list = opeWmsPartsStockMapper.selectList(wrapper);
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }

                OpeWmsPartsStock stock = list.get(0);
                Integer ableStockQty = stock.getAbleStockQty();
                int count = ableStockQty / partsQty;
                numList.add(count);
            }
            if (CollectionUtils.isNotEmpty(numList)) {
                int num = Collections.min(numList);
                model.setNum(num);
                model.setColorName(getColorNameById(colorId));
                model.setGroupName(getGroupNameById(groupId));
                result.add(model);
            }
        }
        return result;
    }


    /**
     * 成品库组装件list
     * @param enter
     * @return
     */
    @Override
    public PageResult<WmsFinishCombinListResult> finishCombinList(CombinationListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsFinishStockMapper.combinCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsFinishCombinListResult> list = wmsFinishStockMapper.finishCombinList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    /**
     * 成品库组装件详情
     * @param enter
     * @return
     */
    @Override
    public WmsfinishCombinDetailResult finishCombinDetail(IdEnter enter) {
        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getById(enter.getId());
        if (combinStock == null){
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishCombinDetailResult result = wmsFinishStockMapper.finishCombinDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }

    /**
     * 根据colorId获取colorName
     */
    public String getColorNameById(Long colorId) {
        if (null == colorId) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getMessage());
        }
        OpeColor color = opeColorMapper.selectById(colorId);
        if (null != color) {
            return color.getColorName();
        }
        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
    }

    /**
     * 根据groupId获取groupName
     */
    public String getGroupNameById(Long groupId) {
        if (null == groupId) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        OpeSpecificatGroup group = opeSpecificatGroupMapper.selectById(groupId);
        if (null != group) {
            return group.getGroupName();
        }
        throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
    }


    /**
     * 出库单和入库单的数量统计，按国家区分（从库存点击出入库管理进入的）
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> outOrInOrderConunt(WmsStockTypeEnter enter) {
        Map<String, Integer> map = new HashMap<>();

        // 入库单
        QueryWrapper<OpeInWhouseOrder> in = new QueryWrapper<>();
        in.eq(OpeInWhouseOrder.COL_COUNTRY_TYPE,enter.getStockType());
        if (enter.getSource() == null){
            in.eq(OpeInWhouseOrder.COL_SOURCE,0);
        }else {
            in.eq(OpeInWhouseOrder.COL_SOURCE,enter.getSource());
        }
        if (enter.getOrderType() != null){
            in.eq(OpeInWhouseOrder.COL_ORDER_TYPE,enter.getOrderType());
        }
        map.put("1",opeInWhouseOrderService.count(in));

        // 出库单
        QueryWrapper<OpeOutWhouseOrder> out = new QueryWrapper<>();
        out.eq(OpeOutWhouseOrder.COL_COUNTRY_TYPE,enter.getStockType());
        if (enter.getSource() == null){
            out.eq(OpeOutWhouseOrder.COL_SOURCE,0);
        }else {
            out.eq(OpeOutWhouseOrder.COL_SOURCE,enter.getSource());
        }
        if (enter.getOrderType() != null){
            out.eq(OpeOutWhouseOrder.COL_OUT_WH_TYPE,enter.getOrderType());
        }
        map.put("2", opeOutWhouseOrderService.count(out));
        return map;
    }

    /**
     * 根据组装件id获得成品库组装件库存可用库存数量
     *
     * @param enter
     * @return
     */
    @Override
    public IntResult getAbleStockQtyByProductionCombinBomId(WmsQualifiedCombinEnter enter) {
        IntResult result = new IntResult();
        switch (enter.getSource()){
            case 0:
                LambdaQueryWrapper<OpeWmsCombinStock> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsCombinStock::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeWmsCombinStock::getProductionCombinBomId, enter.getId());
                wrapper.orderByDesc(OpeWmsCombinStock::getCreatedTime);
                List<OpeWmsCombinStock> list = opeWmsCombinStockService.list(wrapper);
                if (CollectionUtils.isEmpty(list)) {
                    result.setValue(0);
                } else {
                    OpeWmsCombinStock stock = list.get(0);
                    if (null != stock) {
                        Integer ableStockQty = stock.getAbleStockQty();
                        result.setValue(ableStockQty);
                    }
                }
            default:
                break;
            case 1:
                // 说明是中国不合格品库车辆库 创建出库单 需要从不合格品库找可出库的数据
                QueryWrapper<OpeWmsQualifiedCombinStock> qw = new QueryWrapper<>();
                qw.eq(OpeWmsQualifiedCombinStock.COL_PRODUCTION_COMBIN_BOM_ID,enter.getId());
                qw.last("limit 1");
                OpeWmsQualifiedCombinStock qualifiedCombinStock = opeWmsQualifiedCombinStockService.getOne(qw);
                if (qualifiedCombinStock != null) {
                    result.setValue(qualifiedCombinStock.getQty());
                }else {
                    result.setValue(0);
                }
                break;
        }

        result.setRequestId(enter.getRequestId());
        return result;
    }



    @Override
    public GeneralResult inWhConfirm(OutOrInWhConfirmEnter enter) {
        // 不管怎么说 先找到入库单
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        inWhouseOrder.setInWhStatus(InWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
        opeInWhouseOrderService.saveOrUpdate(inWhouseOrder);

        switch (inWhouseOrder.getOrderType()){
            case 1:
                QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseScooterB> scooterBS = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBS)){
                    for (OpeInWhouseScooterB scooterB : scooterBS) {
                        scooterB.setActInWhQty(scooterB.getInWhQty());
                    }
                    opeInWhouseScooterBService.saveOrUpdateBatch(scooterBS);
                }
            default :
                break;
            case 2:
                QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInWhouseCombinB.COL_IN_WH_ID, inWhouseOrder.getId());
                List<OpeInWhouseCombinB> combinBS = opeInWhouseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBS)){
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
                if (CollectionUtils.isNotEmpty(partsBS)){
                    for (OpeInWhousePartsB partsB : partsBS) {
                        partsB.setActInWhQty(partsB.getInWhQty());
                    }
                    opeInWhousePartsBService.saveOrUpdateBatch(partsBS);
                }
        }

        if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.FACTORY_PURCHAS.getValue())){
            // 如果是部件入库单  点击确认入库  需要改变部件采购单的状态
            productionPurchasService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(),inWhouseOrder.getId(),enter.getUserId());
        }else if (null != inWhouseOrder.getRelationOrderType() && inWhouseOrder.getRelationOrderType().equals(OrderTypeEnums.COMBIN_ORDER.getValue())){
            // 如果是关联的组装单  点击确认入库  需要改变组装单的状态
            productionAssemblyOrderService.statusToPartWhOrAllInWh(inWhouseOrder.getRelationOrderId(),inWhouseOrder.getId(),enter.getUserId());
        }

        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, inWhouseOrder.getId(), OrderTypeEnums.FACTORY_INBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(),
                inWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);

        List<WmsInStockRecordEnter> records = new ArrayList<>();
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)){
                    List<OpeWmsScooterStock> scooterStocks = new ArrayList<>();
                    for (OpeInWhouseScooterB scooterB : scooterBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID,scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE,enter.getStockType());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        if (scooterStock != null) {
                            scooterStock.setAbleStockQty(scooterStock.getAbleStockQty() + scooterB.getInWhQty());
                            scooterStocks.add(scooterStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(scooterStock.getId());
                            scooterRecord.setRelationType(1);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(scooterB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStocks);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhouseCombinB> combinBList = opeInWhouseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)){
                    List<OpeWmsCombinStock> combinStocks = new ArrayList<>();
                    for (OpeInWhouseCombinB combinB : combinBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                        combinStockQueryWrapper.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID,combinB.getProductionCombinBomId());
                        combinStockQueryWrapper.last("limit 1");
                        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getOne(combinStockQueryWrapper);
                        if (combinStock != null) {
                            combinStock.setAbleStockQty(combinStock.getAbleStockQty() + combinB.getInWhQty());
                            combinStocks.add(combinStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(combinStock.getId());
                            scooterRecord.setRelationType(2);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(combinB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStocks);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInWhousePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhousePartsB> partsBList = opeInWhousePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)){
                    List<OpeWmsPartsStock> partsStocks = new ArrayList<>();
                    for (OpeInWhousePartsB partsB : partsBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                        partsStockQueryWrapper.eq(OpeWmsPartsStock.COL_PARTS_ID,partsB.getPartsId());
                        partsStockQueryWrapper.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(partsStockQueryWrapper);
                        if (partsStock != null) {
                            partsStock.setAbleStockQty(partsStock.getAbleStockQty() + partsB.getInWhQty());
                            partsStocks.add(partsStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(partsStock.getId());
                            scooterRecord.setRelationType(3);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(partsB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsStocks);
                }
                break;
        }
        createInStockRecord(records,enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    public void createInStockRecord(List<WmsInStockRecordEnter> scooterRecordList, Long userId) {
        if (CollectionUtils.isNotEmpty(scooterRecordList)) {
            List<OpeWmsStockRecord> list = new ArrayList<>();
            for (WmsInStockRecordEnter recordEnter : scooterRecordList) {
                OpeWmsStockRecord record = new OpeWmsStockRecord();
                BeanUtils.copyProperties(recordEnter, record);
                record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
                record.setCreatedBy(userId);
                record.setCreatedTime(new Date());
                record.setUpdatedBy(userId);
                record.setUpdatedTime(new Date());
                list.add(record);
            }
            opeWmsStockRecordService.saveOrUpdateBatch(list);
        }
    }


    @Override
    public GeneralResult outWhConfirm(OutOrInWhConfirmEnter enter) {
        // 不管怎么说 先找到出库单
        OpeOutWhouseOrder outWhouseOrder = opeOutWhouseOrderService.getById(enter.getId());
        if (outWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        outWhouseOrder.setOutWhStatus(OutBoundOrderStatusEnums.OUT_STOCK.getValue());
        opeOutWhouseOrderService.saveOrUpdate(outWhouseOrder);

        // 处理字表的数据
        switch (outWhouseOrder.getOutWhType()){
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhScooterB> scooterBS = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBS)){
                    for (OpeOutWhScooterB scooterB : scooterBS) {
                        scooterB.setAlreadyOutWhQty(scooterB.getQty());
                    }
                    opeOutWhScooterBService.saveOrUpdateBatch(scooterBS);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhCombinB> combinBs = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBs)){
                    for (OpeOutWhCombinB combinB : combinBs) {
                        combinB.setAlreadyOutWhQty(combinB.getQty());
                    }
                    opeOutWhCombinBService.saveOrUpdateBatch(combinBs);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, outWhouseOrder.getId());
                List<OpeOutWhPartsB> partsBs = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBs)){
                    for (OpeOutWhPartsB partsB : partsBs) {
                        partsB.setAlreadyOutWhQty(partsB.getQty());
                    }
                    opeOutWhPartsBService.saveOrUpdateBatch(partsBs);
                }
                break;
        }


        // 状态流转
        OrderStatusFlowEnter orderStatusFlowEnter = new OrderStatusFlowEnter(null, outWhouseOrder.getOutWhStatus(), OrderTypeEnums.OUTBOUND.getValue(), outWhouseOrder.getId(), "");
        orderStatusFlowService.save(orderStatusFlowEnter);
        // 操作记录
        SaveOpTraceEnter opTraceEnter = new SaveOpTraceEnter(null, outWhouseOrder.getId(), OrderTypeEnums.OUTBOUND.getValue(), OrderOperationTypeEnums.CONFIRM_IN_WH.getValue(),
                outWhouseOrder.getRemark());
        opTraceEnter.setUserId(enter.getUserId());
        productionOrderTraceService.save(opTraceEnter);
        List<WmsInStockRecordEnter> records = new ArrayList<>();
        switch (outWhouseOrder.getOutWhType()){
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeOutWhScooterB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhScooterB> scooterBList = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)){
                    List<OpeWmsScooterStock> scooterStocks = new ArrayList<>();
                    for (OpeOutWhScooterB scooterB : scooterBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID,scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.eq(OpeWmsScooterStock.COL_STOCK_TYPE,enter.getStockType());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsScooterStock scooterStock = opeWmsScooterStockService.getOne(scooterStockQueryWrapper);
                        if (scooterStock != null) {
                            scooterStock.setAbleStockQty(scooterStock.getAbleStockQty() - scooterB.getQty());
                            scooterStock.setUsedStockQty(scooterStock.getUsedStockQty() + scooterB.getQty());
                            scooterStocks.add(scooterStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(scooterStock.getId());
                            scooterRecord.setRelationType(1);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(scooterB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStocks);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeOutWhCombinB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhCombinB> combinBList = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)){
                    List<OpeWmsCombinStock> combinStocks = new ArrayList<>();
                    for (OpeOutWhCombinB combinB : combinBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                        combinStockQueryWrapper.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID,combinB.getProductionCombinBomId());
                        combinStockQueryWrapper.last("limit 1");
                        OpeWmsCombinStock combinStock = opeWmsCombinStockService.getOne(combinStockQueryWrapper);
                        if (combinStock != null) {
                            combinStock.setAbleStockQty(combinStock.getAbleStockQty() - combinB.getQty());
                            combinStock.setUsedStockQty(combinStock.getUsedStockQty() + combinB.getQty());
                            combinStocks.add(combinStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(combinStock.getId());
                            scooterRecord.setRelationType(2);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(combinB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStocks);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeOutWhPartsB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhPartsB> partsBList = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)){
                    List<OpeWmsPartsStock> partsStocks = new ArrayList<>();
                    for (OpeOutWhPartsB partsB : partsBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                        partsStockQueryWrapper.eq(OpeWmsPartsStock.COL_PARTS_ID,partsB.getPartsId());
                        partsStockQueryWrapper.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(partsStockQueryWrapper);
                        if (partsStock != null) {
                            partsStock.setAbleStockQty(partsStock.getAbleStockQty() - partsB.getQty());
                            partsStock.setUsedStockQty(partsStock.getUsedStockQty() + partsB.getQty());
                            partsStocks.add(partsStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(partsStock.getId());
                            scooterRecord.setRelationType(3);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(partsB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsStocks);
                }
                break;
        }
        createInStockRecord(records,enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

}
