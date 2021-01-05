package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsMaterialStockMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 原料库实现类
 * @author: Aleks
 * @create: 2020/12/28 15:06
 */
@Service
@Slf4j
public class WmsMaterialStockServiceImpl implements WmsMaterialStockService {

    @Autowired
    private OpeWmsPartsStockService opeWmsPartsStockService;

    @Autowired
    private WmsMaterialStockMapper wmsMaterialStockMapper;

    @Autowired
    private WmsFinishStockMapper wmsFinishStockMapper;

    @Autowired
    private OpeInWhouseScooterBService opeInWhouseScooterBService;

    @Autowired
    private OpeInWhouseCombinBService opeInWhouseCombinBService;

    @Autowired
    private OpeInWhousePartsBService opeInWhousePartsBService;

    @Autowired
    private OpeWmsScooterStockService opeWmsScooterStockService;

    @Autowired
    private OpeWmsCombinStockService opeWmsCombinStockService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomservice;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeWmsStockRecordService opeWmsStockRecordService;

    @Autowired
    private OpeOutWhScooterBService opeOutWhScooterBService;

    @Autowired
    private OpeOutWhCombinBService opeOutWhCombinBService;

    @Autowired
    private OpeOutWhPartsBService opeOutWhPartsBService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Reference
    private IdAppService idAppService;


    @Override
    public PageResult<MaterialStockPartsListResult> materialStockPartsList(MaterialStockPartsListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsMaterialStockMapper.partsCotalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MaterialStockPartsListResult> list = wmsMaterialStockMapper.materialPartsList(enter);
        return PageResult.create(enter, totalRows, list);
    }

    @Override
    public MaterialpartsStockDetailResult materialStockPartsDetail(IdEnter enter) {
        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getById(enter.getId());
        if (partsStock == null) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        MaterialpartsStockDetailResult result = wmsMaterialStockMapper.materialStockPartsDetail(enter.getId());
        // 入库记录
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        result.setRecordList(record);
        return result;
    }


    @Transactional
    @Override
    @Async
    public void waitInStock(Integer productionType, Long id, Integer stockType, Long userId) {
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeInWhouseScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeInWhouseScooterB.COL_IN_WH_ID, id);
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeInWhouseScooterB scooterB : scooterBList) {
                        // 先看看库存中有没有同车型/颜色的数据 有的话 编辑 否则新增
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (dbScooter != null) {
                            // 说明已经存在这样的数据了
                            dbScooter.setWaitInStockQty(dbScooter.getWaitInStockQty() + scooterB.getInWhQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        } else {
                            OpeWmsScooterStock scooterStock = new OpeWmsScooterStock();
                            scooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
                            scooterStock.setStockType(stockType);
                            scooterStock.setGroupId(scooterB.getGroupId());
                            scooterStock.setColorId(scooterB.getColorId());
                            scooterStock.setWaitInStockQty(scooterB.getInWhQty());
                            scooterStock.setCreatedBy(userId);
                            scooterStock.setCreatedTime(new Date());
                            scooterStock.setUpdatedTime(new Date());
                            scooterStock.setUpdatedBy(userId);
                            scooterStockList.add(scooterStock);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                QueryWrapper<OpeInWhouseCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeInWhouseCombinB.COL_IN_WH_ID, id);
                List<OpeInWhouseCombinB> combinBS = opeInWhouseCombinBService.list(combin);
                if (CollectionUtils.isEmpty(combinBS)) {
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        // 先判断该组装件在库存中有没有 有的话编辑 否则新增
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO, combinB.getCombinNo());
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (dbCombine != null) {
                            // 说明已经存在该组装件的库存  编辑
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() + combinB.getInWhQty());
                            combinStockList.add(dbCombine);
                        } else {
                            OpeWmsCombinStock combinStock = new OpeWmsCombinStock();
                            combinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                            combinStock.setStockType(stockType);
                            combinStock.setCombinNo(combinB.getCombinNo());
                            // 找到组装件的中文/英文/法文
                            OpeProductionCombinBom combinBom = opeProductionCombinBomservice.getById(combinB.getProductionCombinBomId());
                            if (combinBom != null) {
                                combinStock.setEnName(combinBom.getEnName());
                                combinStock.setFrName(combinBom.getFrName());
                                combinStock.setCnName(combinBom.getCnName());
                            }
                            combinStock.setWaitInStockQty(combinB.getInWhQty());
                            combinStock.setCreatedTime(new Date());
                            combinStock.setCreatedBy(userId);
                            combinStock.setUpdatedTime(new Date());
                            combinStock.setUpdatedBy(userId);
                            combinStockList.add(combinStock);
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                List<OpeWmsPartsStock> partsList = new ArrayList<>();
                QueryWrapper<OpeInWhousePartsB> parts = new QueryWrapper<>();
                parts.eq(OpeInWhousePartsB.COL_IN_WH_ID, id);
                List<OpeInWhousePartsB> partsBS = opeInWhousePartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeInWhousePartsB partsB : partsBS) {
                        // 先判断该部件在库存中有没有  有的话 编辑  否则新增
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO, partsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE, partsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (wmsPartsStock != null) {
                            // 说明已经有了  编辑就行
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() + partsB.getActInWhQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        }
                        OpeWmsPartsStock partsStock = new OpeWmsPartsStock();
                        partsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                        partsStock.setStockType(stockType);
                        partsStock.setWaitInStockQty(partsB.getActInWhQty());
                        partsStock.setPartsNo(partsB.getPartsNo());
                        partsStock.setPartsType(partsB.getPartsType());
                        partsStock.setCreatedBy(userId);
                        partsStock.setCreatedTime(new Date());
                        partsStock.setUpdatedBy(userId);
                        partsStock.setUpdatedTime(new Date());
                        // 找到部件的中文/英文/法文名称
                        OpeProductionParts partsBom = opeProductionPartsService.getById(partsB.getPartsId());
                        if (partsBom != null) {
                            partsStock.setCnName(partsBom.getCnName());
                            partsStock.setEnName(partsBom.getEnName());
                            partsStock.setFrName(partsBom.getFrName());
                        }
                        partsList.add(partsStock);
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    /**
     * 入库时
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    @Override
    @Transactional
    @Async
    public void inStock(Integer productionType, Long id, Integer stockType, Long userId,Integer inWhType) {
        // 入库记录
        List<WmsInStockRecordEnter> scooterRecordList = new ArrayList<>();
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeInWhouseScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeInWhouseScooterB.COL_IN_WH_ID, id);
                List<OpeInWhouseScooterB> scooterBList = opeInWhouseScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeInWhouseScooterB scooterB : scooterBList) {
                        // 先看看库存中有没有同车型/颜色的数据 有的话 编辑 否则新增
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (dbScooter != null) {
                            // 讲道理  到这里之后 库存里面必定会有同车型/颜色的数据（因为待入库时就插入数据了，如果没找到 就说明时前面有问题）
                            dbScooter.setAbleStockQty((dbScooter.getAbleStockQty()==null?0:dbScooter.getAbleStockQty()) + scooterB.getInWhQty());
                            // 入库的时候  待入库的数量要相应的减少
                            dbScooter.setWaitInStockQty(dbScooter.getWaitInStockQty() - scooterB.getInWhQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                        // 构建入库记录对象
                        WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                        scooterRecord.setRelationId(dbScooter.getId());
                        scooterRecord.setRelationType(1);
                        scooterRecord.setInWhType(inWhType);
                        scooterRecord.setInWhQty(scooterB.getInWhQty());
                        scooterRecord.setRecordType(1);
                        scooterRecord.setStockType(stockType);
                        scooterRecordList.add(scooterRecord);
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                QueryWrapper<OpeInWhouseCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeInWhouseCombinB.COL_IN_WH_ID, id);
                List<OpeInWhouseCombinB> combinBS = opeInWhouseCombinBService.list(combin);
                if (CollectionUtils.isEmpty(combinBS)) {
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        // 先判断该组装件在库存中有没有 有的话编辑 否则新增
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO, combinB.getCombinNo());
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (dbCombine != null) {
                            // 说明已经存在该组装件的库存  编辑
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setAbleStockQty((dbCombine.getAbleStockQty()==null?0:dbCombine.getAbleStockQty()) + combinB.getInWhQty());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() - combinB.getInWhQty());
                            combinStockList.add(dbCombine);
                        }
                        // 构建入库记录对象
                        WmsInStockRecordEnter combinRecord = new WmsInStockRecordEnter();
                        combinRecord.setRelationId(dbCombine.getId());
                        combinRecord.setRelationType(2);
                        combinRecord.setInWhType(inWhType);
                        combinRecord.setInWhQty(combinB.getInWhQty());
                        combinRecord.setRecordType(1);
                        combinRecord.setStockType(stockType);
                        scooterRecordList.add(combinRecord);
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                List<OpeWmsPartsStock> partsList = new ArrayList<>();
                QueryWrapper<OpeInWhousePartsB> parts = new QueryWrapper<>();
                parts.eq(OpeInWhousePartsB.COL_IN_WH_ID, id);
                List<OpeInWhousePartsB> partsBS = opeInWhousePartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeInWhousePartsB partsB : partsBS) {
                        // 先判断该部件在库存中有没有  有的话 编辑  否则新增
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO, partsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE, partsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (wmsPartsStock != null) {
                            // 说明已经有了  编辑就行
                            wmsPartsStock.setAbleStockQty((wmsPartsStock.getAbleStockQty()==null?0:wmsPartsStock.getAbleStockQty()) + partsB.getActInWhQty());
                            // 待入库的数量也要相应的减少
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() - partsB.getActInWhQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        }
                        // 构建入库记录对象
                        WmsInStockRecordEnter partsRecord = new WmsInStockRecordEnter();
                        partsRecord.setRelationId(wmsPartsStock.getId());
                        partsRecord.setRelationType(3);
                        partsRecord.setInWhType(inWhType);
                        partsRecord.setInWhQty(partsB.getInWhQty());
                        partsRecord.setRecordType(1);
                        partsRecord.setStockType(stockType);
                        scooterRecordList.add(partsRecord);
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
        // 保存入库记录
        createInStockRecord(scooterRecordList,userId);
    }



    public void createInStockRecord(List<WmsInStockRecordEnter> scooterRecordList,Long userId){
       if (CollectionUtils.isNotEmpty(scooterRecordList)){
           List<OpeWmsStockRecord> list = new ArrayList<>();
           for (WmsInStockRecordEnter recordEnter : scooterRecordList) {
               OpeWmsStockRecord record = new OpeWmsStockRecord();
               BeanUtils.copyProperties(recordEnter,record);
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


    /**
     * 可用库存减少  待出库库存增加
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType
     */
    @Override
    @Transactional
    @Async
    public void ableLowWaitOutUp(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        switch (productionType) {
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID,id);
                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(outScooterBs)) {
                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (dbScooter != null){
                            // 讲道理  这里不可能为空  如果为空 就说明之前的地方有误
                            dbScooter.setAbleStockQty(dbScooter.getAbleStockQty() - outScooterB.getQty());
                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() + outScooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID,id);
                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(outWhCombinBS)){
                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
                        if (combinBom != null){
                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO,combinBom.getBomNo());
                            dbCombinStock.last("limit 1");
                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
                            if (dbCombin != null) {
                                dbCombin.setAbleStockQty(dbCombin.getAbleStockQty() - outWhCombinB.getQty());
                                dbCombin.setWaitOutStockQty(dbCombin.getWaitOutStockQty() + outWhCombinB.getQty());
                                dbCombin.setUpdatedBy(userId);
                                dbCombin.setUpdatedTime(new Date());
                                combinStockList.add(dbCombin);
                            }
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID,id);
                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(outWhPartsList)){
                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO,outWhPartsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE,outWhPartsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (partsStock != null) {
                            partsStock.setAbleStockQty(partsStock.getAbleStockQty() - outWhPartsB.getQty());
                            partsStock.setWaitOutStockQty(partsStock.getWaitOutStockQty() + outWhPartsB.getQty());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    @Transactional
    @Override
    @Async
    public void ableUpWaitOutLow(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        switch (productionType) {
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID,id);
                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(outScooterBs)) {
                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (dbScooter != null){
                            // 讲道理  这里不可能为空  如果为空 就说明之前的地方有误
                            dbScooter.setAbleStockQty(dbScooter.getAbleStockQty() + outScooterB.getQty());
                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() - outScooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID,id);
                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(outWhCombinBS)){
                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
                        if (combinBom != null){
                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO,combinBom.getBomNo());
                            dbCombinStock.last("limit 1");
                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
                            if (dbCombin != null) {
                                dbCombin.setAbleStockQty(dbCombin.getAbleStockQty() + outWhCombinB.getQty());
                                dbCombin.setWaitOutStockQty(dbCombin.getWaitOutStockQty() - outWhCombinB.getQty());
                                dbCombin.setUpdatedBy(userId);
                                dbCombin.setUpdatedTime(new Date());
                                combinStockList.add(dbCombin);
                            }
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID,id);
                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(outWhPartsList)){
                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO,outWhPartsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE,outWhPartsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (partsStock != null) {
                            partsStock.setAbleStockQty(partsStock.getAbleStockQty() + outWhPartsB.getQty());
                            partsStock.setWaitOutStockQty(partsStock.getWaitOutStockQty() - outWhPartsB.getQty());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    @Transactional
    @Async
    @Override
    public void usedlUpWaitOutLow(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        switch (productionType) {
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID,id);
                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(outScooterBs)) {
                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (dbScooter != null){
                            // 讲道理  这里不可能为空  如果为空 就说明之前的地方有误
                            dbScooter.setUsedStockQty(dbScooter.getUsedStockQty() + outScooterB.getQty());
                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() - outScooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID,id);
                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(outWhCombinBS)){
                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
                        if (combinBom != null){
                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO,combinBom.getBomNo());
                            dbCombinStock.last("limit 1");
                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
                            if (dbCombin != null) {
                                dbCombin.setUsedStockQty(dbCombin.getUsedStockQty() + outWhCombinB.getQty());
                                dbCombin.setWaitOutStockQty(dbCombin.getWaitOutStockQty() - outWhCombinB.getQty());
                                dbCombin.setUpdatedBy(userId);
                                dbCombin.setUpdatedTime(new Date());
                                combinStockList.add(dbCombin);
                            }
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID,id);
                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(outWhPartsList)){
                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO,outWhPartsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE,outWhPartsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (partsStock != null) {
                            partsStock.setUsedStockQty(partsStock.getUsedStockQty() + outWhPartsB.getQty());
                            partsStock.setWaitOutStockQty(partsStock.getWaitOutStockQty() - outWhPartsB.getQty());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }

}
