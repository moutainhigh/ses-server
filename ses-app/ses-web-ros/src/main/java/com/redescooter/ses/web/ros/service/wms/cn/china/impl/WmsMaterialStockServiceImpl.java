package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.wms.cn.china.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsFinishStockMapper;
import com.redescooter.ses.web.ros.dao.wms.cn.china.WmsMaterialStockMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustCombinB;
import com.redescooter.ses.web.ros.dm.OpeEntrustPartsB;
import com.redescooter.ses.web.ros.dm.OpeEntrustScooterB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.redescooter.ses.web.ros.dm.OpeOutWhCombinB;
import com.redescooter.ses.web.ros.dm.OpeOutWhPartsB;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeWmsCombinStock;
import com.redescooter.ses.web.ros.dm.OpeWmsPartsStock;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockRecord;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeEntrustCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeEntrustPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeEntrustScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhousePartsBService;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhCombinBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhPartsBService;
import com.redescooter.ses.web.ros.service.base.OpeOutWhScooterBService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeWmsCombinStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsPartsStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsScooterStockService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockRecordService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialpartsStockDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsInStockRecordEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockRecordResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: ??????????????????
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
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;

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

    @Autowired
    private OpeEntrustScooterBService opeEntrustScooterBService;

    @Autowired
    private OpeEntrustCombinBService opeEntrustCombinBService;

    @Autowired
    private OpeEntrustPartsBService opeEntrustPartsBService;

    @DubboReference
    private IdAppService idAppService;


    @Override
    public PageResult<MaterialStockPartsListResult> materialStockPartsList(MaterialStockPartsListEnter enter) {
        SesStringUtils.objStringTrim(enter);
        int totalRows = wmsMaterialStockMapper.partsCotalRows(enter);
        if (NumberUtil.eqZero(totalRows)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MaterialStockPartsListResult> list = wmsMaterialStockMapper.materialPartsList(enter);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MaterialStockPartsListResult model : list) {
                Long partsId = model.getPartsId();
                if (StringManaConstant.entityIsNotNull(partsId)) {
                    OpeProductionParts parts = opeProductionPartsService.getById(partsId);
                    if (StringManaConstant.entityIsNotNull(parts)) {
                        Integer idClass = parts.getIdCalss();
                        model.setIdClass(idClass);
                    }
                }
            }
        }
        return PageResult.create(enter, totalRows, list);
    }

    @Override
    public MaterialpartsStockDetailResult materialStockPartsDetail(IdEnter enter) {
        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(partsStock)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        MaterialpartsStockDetailResult result = wmsMaterialStockMapper.materialStockPartsDetail(enter.getId());
        // ????????????
        List<WmsStockRecordResult> record = wmsFinishStockMapper.inStockRecord(enter.getId());
        if (CollectionUtils.isNotEmpty(record)) {
            for (WmsStockRecordResult r : record) {
                LambdaQueryWrapper<OpeWmsStockSerialNumber> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsStockSerialNumber::getDr, DelStatusEnum.VALID.getCode());
                wrapper.eq(OpeWmsStockSerialNumber::getStockType, 1);
                wrapper.eq(OpeWmsStockSerialNumber::getRelationType, 3);
                wrapper.eq(OpeWmsStockSerialNumber::getRelationId, r.getRelationId());
                List<OpeWmsStockSerialNumber> list = opeWmsStockSerialNumberMapper.selectList(wrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    OpeWmsStockSerialNumber number = list.get(0);
                    String lotNum = number.getLotNum();
                    if (StringUtils.isNotBlank(lotNum)) {
                        r.setLotNum(lotNum);
                    }
                }
            }
        }
        result.setRecordList(record);
        return result;
    }


    /**
     * ????????????????????????????????????????????????????????????????????????
     *
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType      1:???????????? 2???????????????
     * @param userId         ?????????ID
     */
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
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ????????????????????????????????????
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
                            scooterStock.setAbleStockQty(0);
                            scooterStock.setUsedStockQty(0);
                            scooterStock.setWaitOutStockQty(0);
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
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() + combinB.getInWhQty());
                            combinStockList.add(dbCombine);
                        } else {
                            OpeWmsCombinStock combinStock = new OpeWmsCombinStock();
                            combinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                            combinStock.setStockType(stockType);
                            combinStock.setCombinNo(combinB.getCombinNo());
                            // ????????????????????????/??????/??????
                            OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(combinB.getProductionCombinBomId());
                            if (StringManaConstant.entityIsNotNull(combinBom)) {
                                combinStock.setEnName(combinBom.getEnName());
                                combinStock.setFrName(combinBom.getFrName());
                                combinStock.setCnName(combinBom.getCnName());
                                combinStock.setProductionCombinBomId(combinBom.getId());
                            }
                            combinStock.setWaitInStockQty(combinB.getInWhQty());
                            combinStock.setAbleStockQty(0);
                            combinStock.setUsedStockQty(0);
                            combinStock.setWaitOutStockQty(0);
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
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() + partsB.getInWhQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        } else {
                            OpeWmsPartsStock partsStock = new OpeWmsPartsStock();
                            partsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                            partsStock.setStockType(stockType);
                            partsStock.setWaitInStockQty(partsB.getInWhQty());
                            partsStock.setAbleStockQty(0);
                            partsStock.setUsedStockQty(0);
                            partsStock.setWaitOutStockQty(0);
                            partsStock.setPartsNo(partsB.getPartsNo());
                            partsStock.setPartsType(partsB.getPartsType());
                            partsStock.setCreatedBy(userId);
                            partsStock.setCreatedTime(new Date());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            // ?????????????????????/??????/????????????
                            OpeProductionParts partsBom = opeProductionPartsService.getById(partsB.getPartsId());
                            if (StringManaConstant.entityIsNotNull(partsBom)) {
                                partsStock.setCnName(partsBom.getCnName());
                                partsStock.setEnName(partsBom.getEnName());
                                partsStock.setFrName(partsBom.getFrName());
                                partsStock.setPartsId(partsBom.getId());
                            }
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    /**
     * ????????????????????? ??????????????????(???????????????????????????)
     *
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType      1:???????????? 2???????????????
     * @param userId         ?????????ID
     * @param inWhType       ???????????????1??????????????????2??????????????????3??????????????????5??????????????????6?????????
     */
    @Override
    @Async
    public void inStock(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        // ????????????
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
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ?????????  ??????????????? ?????????????????????????????????/???????????????????????????????????????????????????????????????????????? ??????????????????????????????
                            dbScooter.setAbleStockQty((dbScooter.getAbleStockQty() == null ? 0 : dbScooter.getAbleStockQty()) + scooterB.getInWhQty());
                            // ???????????????  ????????????????????????????????????
                            dbScooter.setWaitInStockQty(dbScooter.getWaitInStockQty() - scooterB.getInWhQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                        // ????????????????????????
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
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeInWhouseCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setAbleStockQty((dbCombine.getAbleStockQty() == null ? 0 : dbCombine.getAbleStockQty()) + combinB.getInWhQty());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() - combinB.getInWhQty());
                            combinStockList.add(dbCombine);
                        }
                        // ????????????????????????
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
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setAbleStockQty((wmsPartsStock.getAbleStockQty() == null ? 0 : wmsPartsStock.getAbleStockQty()) + partsB.getActInWhQty());
                            // ???????????????????????????????????????
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() - partsB.getActInWhQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        }
                        // ????????????????????????
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
        // ??????????????????
        createInStockRecord(scooterRecordList, userId);
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


//    /**
//     * ??????????????????  ?????????????????????
//     * @param productionType
//     * @param id
//     * @param stockType
//     * @param userId
//     * @param inWhType
//     */
//    @Override
//    @GlobalTransactional(rollbackFor = Exception.class)
//    @Async
//    public void ableLowWaitOutUp(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
//        switch (productionType) {
//            case 1:
//                // scooter
//                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
//                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID,id);
//                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
//                if (CollectionUtils.isNotEmpty(outScooterBs)) {
//                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
//                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
//                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
//                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
//                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
//                        dbScooterStock.last("limit 1");
//                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
//                        if (dbScooter != null){
//                            // ?????????  ?????????????????????
//                            dbScooter.setAbleStockQty(dbScooter.getAbleStockQty() - outScooterB.getQty());
//                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() + outScooterB.getQty());
//                            dbScooter.setUpdatedBy(userId);
//                            dbScooter.setUpdatedTime(new Date());
//                            scooterStockList.add(dbScooter);
//                        }
//                    }
//                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
//                }
//            default:
//                break;
//            case 2:
//                // combin
//                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
//                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID,id);
//                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
//                if (CollectionUtils.isNotEmpty(outWhCombinBS)){
//                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
//                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
//                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
//                        if (combinBom != null){
//                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
//                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO,combinBom.getBomNo());
//                            dbCombinStock.last("limit 1");
//                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
//                            if (dbCombin != null) {
//                                dbCombin.setAbleStockQty(dbCombin.getAbleStockQty() - outWhCombinB.getQty());
//                                dbCombin.setWaitOutStockQty(dbCombin.getWaitOutStockQty() + outWhCombinB.getQty());
//                                dbCombin.setUpdatedBy(userId);
//                                dbCombin.setUpdatedTime(new Date());
//                                combinStockList.add(dbCombin);
//                            }
//                        }
//                    }
//                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
//                }
//                break;
//            case 3:
//                // parts
//                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
//                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID,id);
//                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
//                if (CollectionUtils.isNotEmpty(outWhPartsList)){
//                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
//                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
//                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
//                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO,outWhPartsB.getPartsNo());
//                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE,outWhPartsB.getPartsType());
//                        dbPartsStock.last("limit 1");
//                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
//                        if (partsStock != null) {
//                            partsStock.setAbleStockQty(partsStock.getAbleStockQty() - outWhPartsB.getQty());
//                            partsStock.setWaitOutStockQty(partsStock.getWaitOutStockQty() + outWhPartsB.getQty());
//                            partsStock.setUpdatedBy(userId);
//                            partsStock.setUpdatedTime(new Date());
//                            partsList.add(partsStock);
//                        }
//                    }
//                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
//                }
//                break;
//        }
//    }


    @Override
    @Async
    public void waitOutLow(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        switch (productionType) {
            case 1:
                // scooter
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, id);
                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(outScooterBs)) {
                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ?????????  ?????????????????????  ???????????? ??????????????????????????????
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
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, id);
                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(outWhCombinBS)) {
                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
                        if (StringManaConstant.entityIsNotNull(combinBom)) {
                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO, combinBom.getBomNo());
                            dbCombinStock.last("limit 1");
                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
                            if (StringManaConstant.entityIsNotNull(dbCombin)) {
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
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, id);
                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(outWhPartsList)) {
                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO, outWhPartsB.getPartsNo());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE, outWhPartsB.getPartsType());
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(partsStock)) {
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


//    @GlobalTransactional(rollbackFor = Exception.class)
//    @Async
//    @Override
//    public void usedlUpWaitOutLow(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
//        switch (productionType) {
//            case 1:
//                // scooter
//                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
//                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, id);
//                List<OpeOutWhScooterB> outScooterBs = opeOutWhScooterBService.list(scooter);
//                if (CollectionUtils.isNotEmpty(outScooterBs)) {
//                    List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
//                    for (OpeOutWhScooterB outScooterB : outScooterBs) {
//                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
//                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, outScooterB.getGroupId());
//                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, outScooterB.getColorId());
//                        dbScooterStock.last("limit 1");
//                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
//                        if (dbScooter != null) {
//                            // ?????????  ?????????????????????  ???????????? ??????????????????????????????
//                            dbScooter.setUsedStockQty(dbScooter.getUsedStockQty() + outScooterB.getQty());
//                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() - outScooterB.getQty());
//                            dbScooter.setUpdatedBy(userId);
//                            dbScooter.setUpdatedTime(new Date());
//                            scooterStockList.add(dbScooter);
//                        }
//                    }
//                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
//                }
//            default:
//                break;
//            case 2:
//                // combin
//                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
//                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, id);
//                List<OpeOutWhCombinB> outWhCombinBS = opeOutWhCombinBService.list(combin);
//                if (CollectionUtils.isNotEmpty(outWhCombinBS)) {
//                    List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
//                    for (OpeOutWhCombinB outWhCombinB : outWhCombinBS) {
//                        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(outWhCombinB.getProductionCombinBomId());
//                        if (combinBom != null) {
//                            QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
//                            dbCombinStock.eq(OpeWmsCombinStock.COL_COMBIN_NO, combinBom.getBomNo());
//                            dbCombinStock.last("limit 1");
//                            OpeWmsCombinStock dbCombin = opeWmsCombinStockService.getOne(dbCombinStock);
//                            if (dbCombin != null) {
//                                dbCombin.setUsedStockQty(dbCombin.getUsedStockQty() + outWhCombinB.getQty());
//                                dbCombin.setWaitOutStockQty(dbCombin.getWaitOutStockQty() - outWhCombinB.getQty());
//                                dbCombin.setUpdatedBy(userId);
//                                dbCombin.setUpdatedTime(new Date());
//                                combinStockList.add(dbCombin);
//                            }
//                        }
//                    }
//                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
//                }
//                break;
//            case 3:
//                // parts
//                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
//                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, id);
//                List<OpeOutWhPartsB> outWhPartsList = opeOutWhPartsBService.list(parts);
//                if (CollectionUtils.isNotEmpty(outWhPartsList)) {
//                    List<OpeWmsPartsStock> partsList = new ArrayList<>();
//                    for (OpeOutWhPartsB outWhPartsB : outWhPartsList) {
//                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
//                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_NO, outWhPartsB.getPartsNo());
//                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_TYPE, outWhPartsB.getPartsType());
//                        dbPartsStock.last("limit 1");
//                        OpeWmsPartsStock partsStock = opeWmsPartsStockService.getOne(dbPartsStock);
//                        if (partsStock != null) {
//                            partsStock.setUsedStockQty(partsStock.getUsedStockQty() + outWhPartsB.getQty());
//                            partsStock.setWaitOutStockQty(partsStock.getWaitOutStockQty() - outWhPartsB.getQty());
//                            partsStock.setUpdatedBy(userId);
//                            partsStock.setUpdatedTime(new Date());
//                            partsList.add(partsStock);
//                        }
//                    }
//                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
//                }
//                break;
//        }
//    }


    /**
     * ???????????????????????? ????????????????????????????????????
     *
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType       ???????????????1??????????????????2??????????????????3??????????????????5??????????????????6?????????
     */
    @Async
    @Override
    public void waitOutUp(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        log.info("????????????????????????");
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, id);
                List<OpeOutWhScooterB> scooterBList = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeOutWhScooterB scooterB : scooterBList) {
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ????????????????????????????????????
                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() + scooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        } else {
                            OpeWmsScooterStock scooterStock = new OpeWmsScooterStock();
                            scooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
                            scooterStock.setStockType(stockType);
                            scooterStock.setGroupId(scooterB.getGroupId());
                            scooterStock.setColorId(scooterB.getColorId());
                            scooterStock.setWaitInStockQty(0);
                            scooterStock.setAbleStockQty(0);
                            scooterStock.setUsedStockQty(0);
                            scooterStock.setWaitOutStockQty(scooterB.getQty());
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
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, id);
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeOutWhCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setWaitOutStockQty(dbCombine.getWaitOutStockQty() + combinB.getQty());
                            combinStockList.add(dbCombine);
                        } else {
                            OpeWmsCombinStock combinStock = new OpeWmsCombinStock();
                            combinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                            combinStock.setStockType(stockType);
                            // ????????????????????????/??????/??????
                            OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(combinB.getProductionCombinBomId());
                            if (StringManaConstant.entityIsNotNull(combinBom)) {
                                combinStock.setEnName(combinBom.getEnName());
                                combinStock.setFrName(combinBom.getFrName());
                                combinStock.setCnName(combinBom.getCnName());
                                combinStock.setCombinNo(combinBom.getBomNo());
                                combinStock.setProductionCombinBomId(combinB.getProductionCombinBomId());
                            }
                            combinStock.setWaitInStockQty(0);
                            combinStock.setAbleStockQty(0);
                            combinStock.setUsedStockQty(0);
                            combinStock.setWaitOutStockQty(combinB.getQty());
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
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, id);
                List<OpeOutWhPartsB> partsBS = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeOutWhPartsB partsB : partsBS) {
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setWaitOutStockQty(wmsPartsStock.getWaitOutStockQty() + partsB.getQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        } else {
                            OpeWmsPartsStock partsStock = new OpeWmsPartsStock();
                            partsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                            partsStock.setStockType(stockType);
                            partsStock.setWaitInStockQty(0);
                            partsStock.setAbleStockQty(0);
                            partsStock.setUsedStockQty(0);
                            partsStock.setWaitOutStockQty(partsB.getQty());
                            partsStock.setPartsNo(partsB.getPartsNo());
                            partsStock.setPartsType(partsB.getPartsType());
                            partsStock.setCreatedBy(userId);
                            partsStock.setCreatedTime(new Date());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            // ?????????????????????/??????/????????????
                            OpeProductionParts partsBom = opeProductionPartsService.getById(partsB.getPartsId());
                            if (StringManaConstant.entityIsNotNull(partsBom)) {
                                partsStock.setCnName(partsBom.getCnName());
                                partsStock.setEnName(partsBom.getEnName());
                                partsStock.setFrName(partsBom.getFrName());
                                partsStock.setPartsId(partsBom.getId());
                            }
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    /**
     * ????????????????????? ??????????????????  ?????????????????? ???????????????????????????
     *
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType       ???????????????1??????????????????2??????????????????3??????????????????5??????????????????6?????????
     */
    @Override
    @Async
    public void waitOutLowAbleLowUsedUp(Integer productionType, Long id, Integer stockType, Long userId, Integer inWhType) {
        List<WmsInStockRecordEnter> scooterRecordList = new ArrayList<>();
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeOutWhScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeOutWhScooterB.COL_OUT_WH_ID, id);
                List<OpeOutWhScooterB> scooterBList = opeOutWhScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeOutWhScooterB scooterB : scooterBList) {
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ????????????????????????????????????
                            dbScooter.setWaitOutStockQty(dbScooter.getWaitOutStockQty() - scooterB.getQty());
                            Integer ableNum = dbScooter.getAbleStockQty() - scooterB.getQty();
                            if (0 > ableNum) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }
                            dbScooter.setAbleStockQty(ableNum);
                            dbScooter.setUsedStockQty(dbScooter.getUsedStockQty() + scooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);

                            // ????????????????????????
                            WmsInStockRecordEnter partsRecord = new WmsInStockRecordEnter();
                            partsRecord.setRelationId(dbScooter.getId());
                            partsRecord.setRelationType(1);
                            partsRecord.setInWhType(inWhType);
                            partsRecord.setInWhQty(scooterB.getQty());
                            partsRecord.setRecordType(2);
                            partsRecord.setStockType(stockType);
                            scooterRecordList.add(partsRecord);
                        }
                    }
                    opeWmsScooterStockService.saveOrUpdateBatch(scooterStockList);
                }
            default:
                break;
            case 2:
                // combin
                List<OpeWmsCombinStock> combinStockList = new ArrayList<>();
                QueryWrapper<OpeOutWhCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeOutWhCombinB.COL_OUT_WH_ID, id);
                List<OpeOutWhCombinB> combinBS = opeOutWhCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeOutWhCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            Integer ableNum = dbCombine.getAbleStockQty() - combinB.getQty();
                            if (0 > ableNum) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }
                            dbCombine.setWaitOutStockQty(ableNum);
                            dbCombine.setAbleStockQty(dbCombine.getAbleStockQty() - combinB.getQty());
                            dbCombine.setUsedStockQty(dbCombine.getUsedStockQty() + combinB.getQty());
                            combinStockList.add(dbCombine);

                            // ????????????????????????
                            WmsInStockRecordEnter partsRecord = new WmsInStockRecordEnter();
                            partsRecord.setRelationId(dbCombine.getId());
                            partsRecord.setRelationType(2);
                            partsRecord.setInWhType(inWhType);
                            partsRecord.setInWhQty(combinB.getQty());
                            partsRecord.setRecordType(2);
                            partsRecord.setStockType(stockType);
                            scooterRecordList.add(partsRecord);
                        }
                    }
                    opeWmsCombinStockService.saveOrUpdateBatch(combinStockList);
                }
                break;
            case 3:
                // parts
                List<OpeWmsPartsStock> partsList = new ArrayList<>();
                QueryWrapper<OpeOutWhPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeOutWhPartsB.COL_OUT_WH_ID, id);
                List<OpeOutWhPartsB> partsBS = opeOutWhPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeOutWhPartsB partsB : partsBS) {
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setWaitOutStockQty(wmsPartsStock.getWaitOutStockQty() - partsB.getQty());
                            Integer ableNum = wmsPartsStock.getAbleStockQty() - partsB.getQty();
                            if (0 > ableNum) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }
                            wmsPartsStock.setAbleStockQty(ableNum);
                            wmsPartsStock.setUsedStockQty(wmsPartsStock.getUsedStockQty() + partsB.getQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);

                            // ????????????????????????
                            WmsInStockRecordEnter partsRecord = new WmsInStockRecordEnter();
                            partsRecord.setRelationId(wmsPartsStock.getId());
                            partsRecord.setRelationType(3);
                            partsRecord.setInWhType(inWhType);
                            partsRecord.setInWhQty(partsB.getQty());
                            partsRecord.setRecordType(2);
                            partsRecord.setStockType(stockType);
                            scooterRecordList.add(partsRecord);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
        // ??????????????????
        createInStockRecord(scooterRecordList, userId);
    }


    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param productionType 1:scooter 2:combin 3:parts
     * @param entrustId
     * @param stockType      1:???????????? 2???????????????
     * @param userId         ?????????ID
     */
    @Override
    @Async
    public void FrWaitInStock(Integer productionType, Long entrustId, Integer stockType, Long userId) {
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeEntrustScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeEntrustScooterB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustScooterB> scooterBList = opeEntrustScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeEntrustScooterB scooterB : scooterBList) {
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ????????????????????????????????????
                            dbScooter.setWaitInStockQty(dbScooter.getWaitInStockQty() + scooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        } else {
                            OpeWmsScooterStock scooterStock = new OpeWmsScooterStock();
                            scooterStock.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
                            scooterStock.setStockType(stockType);
                            scooterStock.setGroupId(scooterB.getGroupId());
                            scooterStock.setColorId(scooterB.getColorId());
                            scooterStock.setWaitInStockQty(scooterB.getQty());
                            scooterStock.setAbleStockQty(0);
                            scooterStock.setUsedStockQty(0);
                            scooterStock.setWaitOutStockQty(0);
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
                QueryWrapper<OpeEntrustCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeEntrustCombinB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustCombinB> combinBS = opeEntrustCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeEntrustCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() + combinB.getQty());
                            combinStockList.add(dbCombine);
                        } else {
                            OpeWmsCombinStock combinStock = new OpeWmsCombinStock();
                            combinStock.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                            combinStock.setStockType(stockType);
                            // ????????????????????????/??????/??????
                            OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(combinB.getProductionCombinBomId());
                            if (StringManaConstant.entityIsNotNull(combinBom)) {
                                combinStock.setEnName(combinBom.getEnName());
                                combinStock.setFrName(combinBom.getFrName());
                                combinStock.setCnName(combinBom.getCnName());
                                combinStock.setProductionCombinBomId(combinBom.getId());
                                combinStock.setCombinNo(combinBom.getBomNo());
                            }
                            combinStock.setWaitInStockQty(combinB.getQty());
                            combinStock.setAbleStockQty(0);
                            combinStock.setUsedStockQty(0);
                            combinStock.setWaitOutStockQty(0);
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
                QueryWrapper<OpeEntrustPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeEntrustPartsB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustPartsB> partsBS = opeEntrustPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeEntrustPartsB partsB : partsBS) {
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() + partsB.getQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        } else {
                            OpeWmsPartsStock partsStock = new OpeWmsPartsStock();
                            partsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                            partsStock.setStockType(stockType);
                            partsStock.setWaitInStockQty(partsB.getQty());
                            partsStock.setAbleStockQty(0);
                            partsStock.setUsedStockQty(0);
                            partsStock.setWaitOutStockQty(0);
                            partsStock.setPartsNo(partsB.getPartsNo());
                            partsStock.setPartsType(partsB.getPartsType());
                            partsStock.setCreatedBy(userId);
                            partsStock.setCreatedTime(new Date());
                            partsStock.setUpdatedBy(userId);
                            partsStock.setUpdatedTime(new Date());
                            // ?????????????????????/??????/????????????
                            OpeProductionParts partsBom = opeProductionPartsService.getById(partsB.getPartsId());
                            if (StringManaConstant.entityIsNotNull(partsBom)) {
                                partsStock.setCnName(partsBom.getCnName());
                                partsStock.setEnName(partsBom.getEnName());
                                partsStock.setFrName(partsBom.getFrName());
                                partsStock.setPartsId(partsBom.getId());
                            }
                            partsList.add(partsStock);
                        }
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
    }


    /**
     * ????????????????????????????????? ????????????????????? (??????????????????????????????)
     *
     * @param productionType 1:scooter 2:combin 3:parts
     * @param entrustId
     * @param stockType      1:???????????? 2???????????????
     * @param userId         ?????????ID
     * @param inWhType       ???????????????1??????????????????2??????????????????3??????????????????5??????????????????6?????????
     */
    @Override
    @Async
    public void frInStock(Integer productionType, Long entrustId, Integer stockType, Long userId, Integer inWhType) {
        // ????????????
        List<WmsInStockRecordEnter> scooterRecordList = new ArrayList<>();
        switch (productionType) {
            case 1:
                // scooter
                List<OpeWmsScooterStock> scooterStockList = new ArrayList<>();
                QueryWrapper<OpeEntrustScooterB> scooter = new QueryWrapper<>();
                scooter.eq(OpeEntrustScooterB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustScooterB> scooterBList = opeEntrustScooterBService.list(scooter);
                if (CollectionUtils.isNotEmpty(scooterBList)) {
                    for (OpeEntrustScooterB scooterB : scooterBList) {
                        // ????????????????????????????????????/??????????????? ????????? ?????? ????????????
                        QueryWrapper<OpeWmsScooterStock> dbScooterStock = new QueryWrapper<>();
                        dbScooterStock.eq(OpeWmsScooterStock.COL_GROUP_ID, scooterB.getGroupId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_COLOR_ID, scooterB.getColorId());
                        dbScooterStock.eq(OpeWmsScooterStock.COL_STOCK_TYPE, stockType);
                        dbScooterStock.last("limit 1");
                        OpeWmsScooterStock dbScooter = opeWmsScooterStockService.getOne(dbScooterStock);
                        if (StringManaConstant.entityIsNotNull(dbScooter)) {
                            // ?????????  ??????????????? ?????????????????????????????????/???????????????????????????????????????????????????????????????????????? ??????????????????????????????
                            dbScooter.setAbleStockQty((dbScooter.getAbleStockQty() == null ? 0 : dbScooter.getAbleStockQty()) + scooterB.getQty());
                            // ???????????????  ????????????????????????????????????
                            dbScooter.setWaitInStockQty(dbScooter.getWaitInStockQty() - scooterB.getQty());
                            dbScooter.setUpdatedBy(userId);
                            dbScooter.setUpdatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        } else {
                            // ????????????
                            dbScooter.setId(idAppService.getId(SequenceName.OPE_WMS_SCOOTER_STOCK));
                            dbScooter.setDr(DelStatusEnum.VALID.getCode());
                            dbScooter.setStockType(stockType);
                            dbScooter.setGroupId(scooterB.getGroupId());
                            dbScooter.setColorId(scooterB.getColorId());
                            dbScooter.setAbleStockQty(scooterB.getQty());
                            dbScooter.setUsedStockQty(0);
                            dbScooter.setWaitInStockQty(scooterB.getQty());
                            dbScooter.setWaitOutStockQty(0);
                            dbScooter.setCreatedBy(userId);
                            dbScooter.setCreatedTime(new Date());
                            scooterStockList.add(dbScooter);
                        }
                        // ????????????????????????
                        WmsInStockRecordEnter scooterRecord = new WmsInStockRecordEnter();
                        scooterRecord.setRelationId(dbScooter.getId());
                        scooterRecord.setRelationType(7);
                        scooterRecord.setInWhType(7);
                        scooterRecord.setInWhQty(scooterB.getQty());
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
                QueryWrapper<OpeEntrustCombinB> combin = new QueryWrapper<>();
                combin.eq(OpeEntrustCombinB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustCombinB> combinBS = opeEntrustCombinBService.list(combin);
                if (CollectionUtils.isNotEmpty(combinBS)) {
                    for (OpeEntrustCombinB combinB : combinBS) {
                        // ?????????????????????????????????????????? ??????????????? ????????????
                        QueryWrapper<OpeWmsCombinStock> dbCombinStock = new QueryWrapper<>();
                        dbCombinStock.eq(OpeWmsCombinStock.COL_PRODUCTION_COMBIN_BOM_ID, combinB.getProductionCombinBomId());
                        dbCombinStock.eq(OpeWmsCombinStock.COL_STOCK_TYPE, stockType);
                        dbCombinStock.last("limit 1");
                        OpeWmsCombinStock dbCombine = opeWmsCombinStockService.getOne(dbCombinStock);
                        if (StringManaConstant.entityIsNotNull(dbCombine)) {
                            // ???????????????????????????????????????  ??????
                            dbCombine.setUpdatedBy(userId);
                            dbCombine.setUpdatedTime(new Date());
                            dbCombine.setAbleStockQty((dbCombine.getAbleStockQty() == null ? 0 : dbCombine.getAbleStockQty()) + combinB.getQty());
                            dbCombine.setWaitInStockQty(dbCombine.getWaitInStockQty() - combinB.getQty());
                            combinStockList.add(dbCombine);
                        } else {
                            // ????????????
                            OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(combinB.getProductionCombinBomId());
                            if (StringManaConstant.entityIsNotNull(combinBom)) {
                                // ????????????
                                dbCombine.setId(idAppService.getId(SequenceName.OPE_WMS_COMBIN_STOCK));
                                dbCombine.setDr(DelStatusEnum.VALID.getCode());
                                dbCombine.setStockType(stockType);
                                dbCombine.setProductionCombinBomId(combinB.getProductionCombinBomId());
                                dbCombine.setCombinNo(combinBom.getBomNo());
                                dbCombine.setCnName(combinBom.getCnName());
                                dbCombine.setEnName(combinBom.getEnName());
                                dbCombine.setFrName(combinBom.getFrName());
                                dbCombine.setAbleStockQty(combinB.getQty());
                                dbCombine.setUsedStockQty(0);
                                dbCombine.setWaitInStockQty(combinB.getQty());
                                dbCombine.setWaitOutStockQty(0);
                                dbCombine.setCreatedBy(userId);
                                dbCombine.setCreatedTime(new Date());
                                combinStockList.add(dbCombine);
                            }
                        }
                        // ????????????????????????
                        WmsInStockRecordEnter combinRecord = new WmsInStockRecordEnter();
                        combinRecord.setRelationId(dbCombine.getId());
                        combinRecord.setRelationType(8);
                        combinRecord.setInWhType(7);
                        combinRecord.setInWhQty(combinB.getQty());
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
                QueryWrapper<OpeEntrustPartsB> parts = new QueryWrapper<>();
                parts.eq(OpeEntrustPartsB.COL_ENTRUST_ID, entrustId);
                List<OpeEntrustPartsB> partsBS = opeEntrustPartsBService.list(parts);
                if (CollectionUtils.isNotEmpty(partsBS)) {
                    for (OpeEntrustPartsB partsB : partsBS) {
                        // ???????????????????????????????????????  ????????? ??????  ????????????
                        QueryWrapper<OpeWmsPartsStock> dbPartsStock = new QueryWrapper<>();
                        dbPartsStock.eq(OpeWmsPartsStock.COL_PARTS_ID, partsB.getPartsId());
                        dbPartsStock.eq(OpeWmsPartsStock.COL_STOCK_TYPE, stockType);
                        dbPartsStock.last("limit 1");
                        OpeWmsPartsStock wmsPartsStock = opeWmsPartsStockService.getOne(dbPartsStock);
                        if (StringManaConstant.entityIsNotNull(wmsPartsStock)) {
                            // ??????????????????  ????????????
                            wmsPartsStock.setAbleStockQty((wmsPartsStock.getAbleStockQty() == null ? 0 : wmsPartsStock.getAbleStockQty()) + partsB.getQty());
                            // ???????????????????????????????????????
                            wmsPartsStock.setWaitInStockQty(wmsPartsStock.getWaitInStockQty() - partsB.getQty());
                            wmsPartsStock.setUpdatedBy(userId);
                            wmsPartsStock.setUpdatedTime(new Date());
                            partsList.add(wmsPartsStock);
                        } else {
                            // ????????????
                            OpeProductionParts part = opeProductionPartsService.getById(partsB.getPartsId());
                            if (StringManaConstant.entityIsNotNull(part)) {
                                wmsPartsStock.setId(idAppService.getId(SequenceName.OPE_WMS_PARTS_STOCK));
                                wmsPartsStock.setDr(DelStatusEnum.VALID.getCode());
                                wmsPartsStock.setStockType(stockType);
                                wmsPartsStock.setPartsId(partsB.getPartsId());
                                wmsPartsStock.setPartsNo(partsB.getPartsNo());
                                wmsPartsStock.setPartsType(partsB.getPartsType());
                                wmsPartsStock.setCnName(part.getCnName());
                                wmsPartsStock.setEnName(part.getEnName());
                                wmsPartsStock.setFrName(part.getFrName());
                                wmsPartsStock.setAbleStockQty(partsB.getQty());
                                wmsPartsStock.setUsedStockQty(0);
                                wmsPartsStock.setWaitInStockQty(partsB.getQty());
                                wmsPartsStock.setWaitOutStockQty(0);
                                wmsPartsStock.setCreatedBy(userId);
                                wmsPartsStock.setCreatedTime(new Date());
                                partsList.add(wmsPartsStock);
                            }
                        }
                        // ????????????????????????
                        WmsInStockRecordEnter partsRecord = new WmsInStockRecordEnter();
                        partsRecord.setRelationId(wmsPartsStock.getId());
                        partsRecord.setRelationType(9);
                        partsRecord.setInWhType(7);
                        partsRecord.setInWhQty(partsB.getQty());
                        partsRecord.setRecordType(1);
                        partsRecord.setStockType(stockType);
                        scooterRecordList.add(partsRecord);
                    }
                    opeWmsPartsStockService.saveOrUpdateBatch(partsList);
                }
                break;
        }
        // ??????????????????
        createInStockRecord(scooterRecordList, userId);
    }

}
