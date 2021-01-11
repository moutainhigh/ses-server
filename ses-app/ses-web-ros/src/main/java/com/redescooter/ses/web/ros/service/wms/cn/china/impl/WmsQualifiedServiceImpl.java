package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsQualifiedMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsQualifiedService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:35
 */
@Service
public class WmsQualifiedServiceImpl implements WmsQualifiedService {

    @Autowired
    private WmsQualifiedMapper wmsQualifiedMapper;

    @Autowired
    private OpeWmsQualifiedScooterStockService opeWmsQualifiedScooterStockService;

    @Autowired
    private OpeWmsQualifiedCombinStockService opeWmsQualifiedCombinStockService;

    @Autowired
    private OpeWmsQualifiedPartsStockService opeWmsQualifiedPartsStockService;

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

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

    @Reference
    private IdAppService idAppService;



    @Override
    public PageResult<WmsQualifiedScooterListResult> scooterList(WmsFinishScooterListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedScooterListResult> list = wmsQualifiedMapper.scooterList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public WmsfinishScooterDetailResult scooterDetail(IdEnter enter) {
        OpeWmsQualifiedScooterStock scooterStock = opeWmsQualifiedScooterStockService.getById(enter.getId());
        if (scooterStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishScooterDetailResult result = wmsQualifiedMapper.scooterDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    @Override
    public PageResult<WmsQualifiedCombinListResult> combineList(CombinationListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.combinCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedCombinListResult> list = wmsQualifiedMapper.combinList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public WmsfinishCombinDetailResult combinDetail(IdEnter enter) {
        OpeWmsQualifiedCombinStock combinStock = opeWmsQualifiedCombinStockService.getById(enter.getId());
        if (combinStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        WmsfinishCombinDetailResult result = wmsQualifiedMapper.combinDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    @Override
    public PageResult<WmsQualifiedPartsListResult> partsList(MaterialStockPartsListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsQualifiedMapper.partsCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WmsQualifiedPartsListResult> list = wmsQualifiedMapper.partsList(enter);
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public MaterialpartsStockDetailResult partsDetail(IdEnter enter) {
        OpeWmsQualifiedPartsStock partsStock = opeWmsQualifiedPartsStockService.getById(enter.getId());
        if (partsStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        MaterialpartsStockDetailResult result = wmsQualifiedMapper.partsDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }

    @Override
    public WmsQualifiedQtyCountResult quailifiedQtyCount(WmsQualifiedQtyCountEnter enter) {
        WmsQualifiedQtyCountResult result = new WmsQualifiedQtyCountResult();
        Integer qty = 0;
        switch (enter.getClassType()) {
            case 1:
                // cscooter
                QueryWrapper<OpeWmsQualifiedScooterStock> scooter = new QueryWrapper<>();
                scooter.select("IFNULL(sum(qty),0) AS qty");
                OpeWmsQualifiedScooterStock scooterStock = opeWmsQualifiedScooterStockService.getOne(scooter);
                qty = scooterStock.getQty();
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeWmsQualifiedCombinStock> combin = new QueryWrapper<>();
                combin.select("IFNULL(sum(qty),0) AS qty");
                OpeWmsQualifiedCombinStock combinStock = opeWmsQualifiedCombinStockService.getOne(combin);
                qty = combinStock.getQty();
                break;
            case 3:
                // parts
                QueryWrapper<OpeWmsQualifiedPartsStock> parts = new QueryWrapper<>();
                parts.select("IFNULL(sum(qty),0) AS qty");
                OpeWmsQualifiedPartsStock partsStock = opeWmsQualifiedPartsStockService.getOne(parts);
                qty = partsStock.getQty();
                break;
        }
        result.setQty(qty);
        return result;
    }

    @Override
    public Map<String, Integer> unailifiedStockTabCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        Integer scooterNum = opeWmsQualifiedScooterStockService.count();
        Integer combinNum = opeWmsQualifiedCombinStockService.count();
        Integer partsNum = opeWmsQualifiedPartsStockService.count();

        map.put("1",scooterNum);
        map.put("2",combinNum);
        map.put("3",partsNum);
        return map;
    }


    @Override
    public GeneralResult inWhConfirm(OutOrInWhConfirmEnter enter) {
        // 不管怎么样 先找到入库单
        OpeInWhouseOrder inWhouseOrder = opeInWhouseOrderService.getById(enter.getId());
        if (inWhouseOrder == null){
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        List<WmsInStockRecordEnter> records = new ArrayList<>();
        switch (inWhouseOrder.getOrderType()){
            case 1:
                // scooter
                QueryWrapper<OpeInWhouseScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)){
                    List<OpeWmsQualifiedScooterStock> scooterStocks = new ArrayList<>();
                    for (OpeInWhouseScooterB scooterB : scooterBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID,scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedScooterStock scooterStock = opeWmsQualifiedScooterStockService.getOne(scooterStockQueryWrapper);
                        if (scooterStock != null) {
                            scooterStock.setQty(scooterStock.getQty() + scooterB.getInWhQty());
                            scooterStocks.add(scooterStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(scooterStock.getId());
                            scooterRecord.setRelationType(4);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(scooterB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedScooterStockService.saveOrUpdateBatch(scooterStocks);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeInWhouseCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhouseCombinB> combinBList = opeInWhouseCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)){
                    List<OpeWmsQualifiedCombinStock> combinStocks = new ArrayList<>();
                    for (OpeInWhouseCombinB combinB : combinBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                        combinStockQueryWrapper.eq(OpeWmsQualifiedCombinStock.COL_PRODUCTION_COMBIN_BOM_ID,combinB.getProductionCombinBomId());
                        combinStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedCombinStock combinStock = opeWmsQualifiedCombinStockService.getOne(combinStockQueryWrapper);
                        if (combinStock != null) {
                            combinStock.setQty(combinStock.getQty() + combinB.getInWhQty());
                            combinStocks.add(combinStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(combinStock.getId());
                            scooterRecord.setRelationType(5);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(combinB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedCombinStockService.saveOrUpdateBatch(combinStocks);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeInWhousePartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeInWhouseScooterB.COL_IN_WH_ID,enter.getId());
                List<OpeInWhousePartsB> partsBList = opeInWhousePartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)){
                    List<OpeWmsQualifiedPartsStock> partsStocks = new ArrayList<>();
                    for (OpeInWhousePartsB partsB : partsBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                        partsStockQueryWrapper.eq(OpeWmsQualifiedPartsStock.COL_PARTS_ID,partsB.getPartsId());
                        partsStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedPartsStock partsStock = opeWmsQualifiedPartsStockService.getOne(partsStockQueryWrapper);
                        if (partsStock != null) {
                            partsStock.setQty(partsStock.getQty() + partsB.getInWhQty());
                            partsStocks.add(partsStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(partsStock.getId());
                            scooterRecord.setRelationType(6);
                            scooterRecord.setInWhType(inWhouseOrder.getInWhType());
                            scooterRecord.setInWhQty(partsB.getInWhQty());
                            scooterRecord.setRecordType(1);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedPartsStockService.saveOrUpdateBatch(partsStocks);
                }
                break;
        }
        createInStockRecord(records,enter.getId());
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
        List<WmsInStockRecordEnter> records = new ArrayList<>();
        switch (outWhouseOrder.getOutWhType()){
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooterBQueryWrapper = new QueryWrapper<>();
                scooterBQueryWrapper.eq(OpeOutWhScooterB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhScooterB> scooterBList = opeOutWhScooterBService.list(scooterBQueryWrapper);
                if (CollectionUtils.isNotEmpty(scooterBList)){
                    List<OpeWmsQualifiedScooterStock> scooterStocks = new ArrayList<>();
                    for (OpeOutWhScooterB scooterB : scooterBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedScooterStock> scooterStockQueryWrapper = new QueryWrapper<>();
                        scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID,scooterB.getGroupId());
                        scooterStockQueryWrapper.eq(OpeWmsQualifiedScooterStock.COL_GROUP_ID, scooterB.getColorId());
                        scooterStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedScooterStock scooterStock = opeWmsQualifiedScooterStockService.getOne(scooterStockQueryWrapper);
                        if (scooterStock != null) {
                            scooterStock.setQty(scooterStock.getQty() - scooterB.getQty());
                            scooterStocks.add(scooterStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(scooterStock.getId());
                            scooterRecord.setRelationType(4);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(scooterB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedScooterStockService.saveOrUpdateBatch(scooterStocks);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combinBQueryWrapper = new QueryWrapper<>();
                combinBQueryWrapper.eq(OpeOutWhCombinB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhCombinB> combinBList = opeOutWhCombinBService.list(combinBQueryWrapper);
                if (CollectionUtils.isNotEmpty(combinBList)){
                    List<OpeWmsQualifiedCombinStock> combinStocks = new ArrayList<>();
                    for (OpeOutWhCombinB combinB : combinBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedCombinStock> combinStockQueryWrapper = new QueryWrapper<>();
                        combinStockQueryWrapper.eq(OpeWmsQualifiedCombinStock.COL_PRODUCTION_COMBIN_BOM_ID,combinB.getProductionCombinBomId());
                        combinStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedCombinStock combinStock = opeWmsQualifiedCombinStockService.getOne(combinStockQueryWrapper);
                        if (combinStock != null) {
                            combinStock.setQty(combinStock.getQty() - combinB.getQty());
                            combinStocks.add(combinStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(combinStock.getId());
                            scooterRecord.setRelationType(5);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(combinB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedCombinStockService.saveOrUpdateBatch(combinStocks);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> partsBQueryWrapper = new QueryWrapper<>();
                partsBQueryWrapper.eq(OpeOutWhPartsB.COL_OUT_WH_ID,enter.getId());
                List<OpeOutWhPartsB> partsBList = opeOutWhPartsBService.list(partsBQueryWrapper);
                if (CollectionUtils.isNotEmpty(partsBList)){
                    List<OpeWmsQualifiedPartsStock> partsStocks = new ArrayList<>();
                    for (OpeOutWhPartsB partsB : partsBList) {
                        // 在库存里面增加可用数量
                        QueryWrapper<OpeWmsQualifiedPartsStock> partsStockQueryWrapper = new QueryWrapper<>();
                        partsStockQueryWrapper.eq(OpeWmsQualifiedPartsStock.COL_PARTS_ID,partsB.getPartsId());
                        partsStockQueryWrapper.last("limit 1");
                        OpeWmsQualifiedPartsStock partsStock = opeWmsQualifiedPartsStockService.getOne(partsStockQueryWrapper);
                        if (partsStock != null) {
                            partsStock.setQty(partsStock.getQty() - partsB.getQty());
                            partsStocks.add(partsStock);

                            // 构建入库记录对象
                            WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                            scooterRecord.setRelationId(partsStock.getId());
                            scooterRecord.setRelationType(6);
                            scooterRecord.setInWhType(outWhouseOrder.getOutType());
                            scooterRecord.setInWhQty(partsB.getQty());
                            scooterRecord.setRecordType(2);
                            scooterRecord.setStockType(enter.getStockType());
                            records.add(scooterRecord);
                        }
                    }
                    opeWmsQualifiedPartsStockService.saveOrUpdateBatch(partsStocks);
                }
                break;
        }
        createInStockRecord(records,enter.getId());
        return new GeneralResult(enter.getRequestId());
    }
}
