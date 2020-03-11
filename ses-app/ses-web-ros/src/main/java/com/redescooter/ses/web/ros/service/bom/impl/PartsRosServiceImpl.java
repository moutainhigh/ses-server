package com.redescooter.ses.web.ros.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomSnClassEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.PartsEventEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.MapResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.parts.ESCUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dao.bom.PartsServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpePartsHistoryRecord;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.service.base.OpeExcleImportService;
import com.redescooter.ses.web.ros.service.base.OpePartsHistoryRecordService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpePartsTypeService;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.parts.AddPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.EditSavePartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsDto;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartsTypeResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName PartsRosServiceImpl
 * @Author Jerry
 * @date 2020/02/26 20:22
 * @Description:
 */
@Slf4j
@Service
public class PartsRosServiceImpl implements PartsRosService {

    @Autowired
    private OpePartsService partsService;

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

    @Autowired
    private OpeExcleImportService excleImportService;

    @Autowired
    private OpePartsProductService partsProductService;
    @Autowired
    private OpePartsHistoryRecordService partsHistoryRecordService;

    @Autowired
    private OpePartsTypeService partsTypeService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PartsServiceMapper partsServiceMapper;

    @Reference
    private IdAppService idAppService;

    @Reference
    private GenerateService generateService;

    @Override
    public MapResult commonCountStatus(GeneralEnter enter) {
        QueryWrapper<OpeParts> partsQueryWrapper = new QueryWrapper<>();
        partsQueryWrapper.eq(OpeParts.COL_DR, 0);
        partsQueryWrapper.eq(OpeParts.COL_USER_ID, enter.getUserId());
        int partsCount = partsService.count(partsQueryWrapper);

        QueryWrapper<OpePartsProduct> scooter = new QueryWrapper<>();
        scooter.eq(OpePartsProduct.COL_DR, 0);
        scooter.eq(OpePartsProduct.COL_USER_ID, enter.getUserId());
        scooter.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        int scooterConut = partsProductService.count(scooter);

        QueryWrapper<OpePartsProduct> combination = new QueryWrapper<>();
        combination.eq(OpePartsProduct.COL_DR, 0);
        combination.eq(OpePartsProduct.COL_USER_ID, enter.getUserId());
        combination.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.COMBINATION.getValue());
        int combinationConut = partsProductService.count(combination);

        Map<String, Integer> map = new HashMap<>();
        map.put("partsCount", partsCount);
        map.put("scooterConut", scooterConut);
        map.put("combinationConut", combinationConut);

        return new MapResult(map);
    }

    @Override
    public List<PartsTypeResult> typeCount(GeneralEnter enter) {
        List<PartsTypeResult> resultList = new ArrayList<>();

        List<OpePartsType> list = partsTypeService.list();
        if (list.size() > 0) {
            list.forEach(type -> {
                PartsTypeResult result = new PartsTypeResult();
                result.setId(type.getId());
                result.setName(type.getName());
                result.setCode(type.getCode());
                result.setValue(type.getValue());
                resultList.add(result);
            });
        }

        return resultList;
    }

    @Override
    public ImportExcelPartsResult importParts(ImportPartsEnter enter) {
        return excelService.readExcelDataByParts(enter);
    }

    @Transient
    @Override
    public ImportExcelPartsResult savePartsList(List<ExpressPartsExcleData> list, ImportPartsEnter enter) {
        ImportExcelPartsResult result = new ImportExcelPartsResult();
        List<OpeParts> saveList = new ArrayList<>();
        List<OpePartsHistoryRecord> instersHistory = new ArrayList<>();

        String lot = generateService.getOrderNo();
        for (ExpressPartsExcleData excleData : list) {
            EditSavePartsEnter saveParts = new EditSavePartsEnter();
            saveParts.setPartsNumber(excleData.getPartsN());
            saveParts.setSec(ESCUtils.checkESC(excleData.getEsc()));
            saveParts.setPartsType(BomCommonTypeEnums.checkCode(excleData.getType()));
            saveParts.setSnClassFlag(BomSnClassEnums.getValueByCode(excleData.getSnClass()));
            saveParts.setCnName(excleData.getCnName());
            saveParts.setFrName(excleData.getFrName());
            saveParts.setEnName(excleData.getEnName());
            OpeParts parts = createParts(saveParts, lot, enter.getUserId());
            saveList.add(parts);
            instersHistory.add(createPartsHistory(parts, PartsEventEnums.CREATE.getValue(), enter.getUserId(), parts.getPartsNumber()));
        }

        OpeExcleImport excleImport = new OpeExcleImport();
        excleImport.setId(idAppService.getId(SequenceName.OPE_EXCLE_IMPORT));
        excleImport.setDr(0);
        excleImport.setStatus(BomStatusEnums.NORMAL.getValue());
        excleImport.setBatchNo(lot);
        excleImport.setCount(saveList.size());
        excleImport.setAttachment(enter.getUrl());
        excleImport.setServiceType("BOM导入");
        excleImport.setMessage("BOM物料部件导入");
        excleImport.setCreatedBy(enter.getUserId());
        excleImport.setCreatedTime(new Date());
        excleImport.setUpdatedBy(enter.getUserId());
        excleImport.setUpdatedTime(new Date());
        excleImport.setDataJson(JSONArray.toJSONString(list));

        excleImportService.save(excleImport);
        partsService.batchInsert(saveList);
        partsHistoryRecordService.saveBatch(instersHistory);

        result.setSuccess(Boolean.TRUE);
        result.setSuccessNum(list.size());
        result.setFailNum(0);
        result.setBatchNo(lot);
        result.setRequestId(enter.getRequestId());

        return result;
    }

    @Transient
    @Override
    public GeneralResult edits(StringEnter enter) {

        List<EditSavePartsEnter> enters = null;
        try {
            enters = JSONArray.parseArray(enter.getSt(), EditSavePartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpeParts> updates = new ArrayList<>();
        List<OpeParts> insters = new ArrayList<>();
        List<OpePartsHistoryRecord> instersHistory = new ArrayList<>();

        if (enters.size() > 0) {
            String lot = generateService.getOrderNo();
            enters.forEach(pat -> {
                checkParts(pat);
                if (org.springframework.util.StringUtils.isEmpty(pat.getId())) {
                    OpeParts parts = createParts(pat, lot, enter.getUserId());
                    insters.add(parts);
                    instersHistory.add(createPartsHistory(parts, PartsEventEnums.CREATE.getValue(), enter.getUserId(), parts.getPartsNumber()));
                } else {
                    OpeParts parts = createParts(pat, null, enter.getUserId());
                    updates.add(parts);
                    OpeParts opeParts = partsService.getById(pat.getId());
                    instersHistory.add(createPartsHistory(opeParts, PartsEventEnums.UPDATE.getValue(), enter.getUserId(), opeParts.getPartsNumber()));
                }
            });
        }

        if (insters.size() > 0) {
            partsService.saveBatch(insters);
        }
        if (updates.size() > 0) {
            partsService.updateBatchById(updates);
        }
        if (instersHistory.size() > 0) {
            partsHistoryRecordService.saveBatch(instersHistory);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Transient
    @Override
    public List<DetailsPartsResult> iterations(StringEnter enter) {

        List<AddPartsEnter> enters = null;
        try {
            enters = JSONArray.parseArray(enter.getSt(), AddPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpeParts> update = new ArrayList<>();
        List<OpePartsHistoryRecord> instersHistory = new ArrayList<>();


        DetailsPartsResult detailsPartsResult = new DetailsPartsResult();
        List<DetailsPartsResult> result = new ArrayList<>();

        //判断是否进行部品号的迭代
        if (enters.size() > 0) {
            enters.forEach(ne -> {
                OpeParts olod = partsService.getById(ne.getId());
                Optional.ofNullable(olod).ifPresent(oloPid -> {
                    //验证是否已存在
                    List<String> usingProductNumList = bomRosServiceMapper.UsingProductNumList(enter);
                    if (usingProductNumList.contains(ne)) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                    //保存记录
                    OpeParts news = new OpeParts();
                    news.setId(oloPid.getId());
                    news.setPartsNumber(ne.getPartsNumber());
                    news.setUpdatedBy(enter.getUserId());
                    news.setUpdatedTime(new Date());
                    update.add(news);
                    instersHistory.add(createPartsHistory(oloPid, PartsEventEnums.ADD.getValue(), enter.getUserId(), ne.getPartsNumber()));


                    // 查询历史的订单编号
                    QueryWrapper<OpePartsHistoryRecord> opePartsHistoryRecordQueryWrapper = new QueryWrapper<>();
                    opePartsHistoryRecordQueryWrapper.eq(OpePartsHistoryRecord.COL_PARTS_ID, news.getId());
                    opePartsHistoryRecordQueryWrapper.eq(OpePartsHistoryRecord.COL_DR, 0);

                    List<OpePartsHistoryRecord> opePartsHistoryRecordList = partsHistoryRecordService.list(opePartsHistoryRecordQueryWrapper);
                    if (CollectionUtils.isEmpty(opePartsHistoryRecordList)) {
                        opePartsHistoryRecordList = new ArrayList<>();
                    }
                    // 返回修改的信息记录
                    List<HistoryPartsDto> historyPartsDtoList = new ArrayList<>();
                    opePartsHistoryRecordList.forEach(item -> {
                        historyPartsDtoList.add(HistoryPartsDto.builder()
                                .id(item.getId())
                                .createdTime(item.getCreatedTime())
                                .partsNumber(item.getPartsNumber())
                                .build());
                    });
                    detailsPartsResult.setId(news.getId());
                    detailsPartsResult.setPartsNumber(news.getPartsNumber());
                    detailsPartsResult.setHistoryHist(historyPartsDtoList);
                    result.add(detailsPartsResult);
                });
            });
        }
        if (update.size() > 0 && instersHistory.size() > 0) {
            partsService.updateBatchById(update);
            partsHistoryRecordService.saveBatch(instersHistory);
        }

        return result;
    }

    @Transient
    @Override
    public GeneralResult deletes(StringEnter enter) {

        List<IdEnter> enters = null;
        try {
            enters = JSONArray.parseArray(enter.getSt(), IdEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<Long> deletes = new ArrayList<>();
        List<OpePartsHistoryRecord> insters = new ArrayList<>();
        if (enters.size() > 0) {
            enters.forEach(dl -> {
                if (!org.springframework.util.StringUtils.isEmpty(dl.getId())) {
                    OpeParts byId = partsService.getById(dl.getId());
                    if (byId != null) {
                        deletes.add(byId.getId());
                        insters.add(createPartsHistory(byId, PartsEventEnums.DELETE.getValue(), enter.getUserId(), byId.getPartsNumber()));
                    }
                }
            });
        }
        if (deletes.size() > 0) {
            partsService.removeByIds(deletes);
            partsHistoryRecordService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public DetailsPartsResult details(IdEnter enter) {

        OpeParts byId = partsService.getById(enter.getId());

        DetailsPartsResult result = new DetailsPartsResult();
        BeanUtils.copyProperties(byId, result);

        QueryWrapper<OpePartsHistoryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(OpePartsHistoryRecord.COL_PARTS_ID, enter.getId());
        wrapper.eq(OpePartsHistoryRecord.COL_DR, 0);
        wrapper.eq(OpePartsHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsHistoryRecord> historyLists = partsHistoryRecordService.list(wrapper);
        List<HistoryPartsDto> list = new ArrayList<>();

        if (historyLists.size() > 0) {
            historyLists.forEach(ht -> {
                HistoryPartsDto dto = new HistoryPartsDto();
                dto.setId(ht.getId());
                dto.setPartsNumber(ht.getPartsNumber());
                dto.setCreatedTime(ht.getCreatedTime());
                list.add(dto);
            });
        }

        result.setHistoryHist(list);
        return result;
    }

    @Override
    public PageResult<DetailsPartsResult> list(PartListEnter enter) {

        int count = partsServiceMapper.listCount(enter);

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DetailsPartsResult> list = partsServiceMapper.list(enter);

        return PageResult.create(enter, count, list);
    }

    @Override
    public HistoryPartsResult historys(IdEnter enter) {

        QueryWrapper<OpePartsHistoryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(OpePartsHistoryRecord.COL_PARTS_ID, enter.getId());
        wrapper.eq(OpePartsHistoryRecord.COL_DR, 0);
        wrapper.eq(OpePartsHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsHistoryRecord> historyLists = partsHistoryRecordService.list(wrapper);
        HistoryPartsResult historyPartsResult = new HistoryPartsResult();

        List<HistoryPartsDto> list = new ArrayList<>();

        if (historyLists.size() > 0) {
            historyLists.forEach(ht -> {
                HistoryPartsDto dto = new HistoryPartsDto();
                dto.setId(ht.getId());
                dto.setPartsNumber(ht.getPartsNumber());
                dto.setCreatedTime(ht.getCreatedTime());
                list.add(dto);
            });
        }

        historyPartsResult.setList(list);

        return historyPartsResult;
    }

    @Override
    public GeneralResult partsSynchronize(GeneralEnter enter) {
        return null;
    }

    @Override
    public PageResult<QueryPartListResult> partList(QueryPartListEnter enter) {
        int count = partsServiceMapper.partListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, partsServiceMapper.partList(enter));
    }

    private OpePartsHistoryRecord createPartsHistory(OpeParts parts, String event, long userId, String partNum) {
        OpePartsHistoryRecord record = new OpePartsHistoryRecord();
        record.setId(idAppService.getId(SequenceName.OPE_PARTS_HISTORY_RECORD));
        record.setDr(0);
        record.setTenantId(0L);
        record.setUserId(userId);
        record.setStatus(BomStatusEnums.NORMAL.getValue());
        record.setPartsNumber(partNum);
        record.setImportLot(parts.getImportLot());
        record.setPartsId(parts.getId());
        record.setEvent(event);
        record.setPartsType(parts.getPartsType());
        record.setSec(parts.getSec());
        record.setCnName(parts.getCnName());
        record.setFrName(parts.getFrName());
        record.setEnName(parts.getEnName());
        record.setSnClass(parts.getSnClass());
        record.setPartsQty(0);
        record.setProductionCycle(parts.getProductionCycle());
        record.setSupplierId(parts.getSupplierId());
        record.setDwg(parts.getDwg());
        record.setNote(parts.getNote());
        record.setRevision(0);
        record.setCreatedBy(userId);
        record.setCreatedTime(new Date());
        record.setUpdatedBy(userId);
        record.setUpdatedTime(new Date());
        return record;
    }

    private OpeParts createParts(EditSavePartsEnter enter, String lot, long userId) {
        OpeParts parts = new OpeParts();

        if (org.springframework.util.StringUtils.isEmpty(enter.getId())) {
            parts.setId(idAppService.getId(SequenceName.OPE_PARTS));
            parts.setDr(0);
            parts.setTenantId(0L);
            parts.setUserId(userId);
            parts.setStatus(BomStatusEnums.NORMAL.getValue());
            parts.setCreatedBy(userId);
            parts.setCreatedTime(new Date());
            parts.setImportLot(lot);
            parts.setPartsNumber(enter.getPartsNumber());
        } else {
            parts.setId(enter.getId());
        }
        parts.setPartsType(BomCommonTypeEnums.checkCode(enter.getPartsType()));
        parts.setSnClass(BomSnClassEnums.getValueByCode(enter.getSnClassFlag()));
        parts.setSec(ESCUtils.checkESC(enter.getSec()));
        parts.setCnName(enter.getCnName());
        parts.setFrName(enter.getFrName());
        parts.setEnName(enter.getEnName());
        parts.setProductionCycle(enter.getProductionCycle());
        parts.setSupplierId(enter.getSupplierId());
        parts.setDwg(enter.getDwg());
        parts.setNote(enter.getNote());
        parts.setSynchronizeFlag(Boolean.FALSE);
        parts.setRevision(0);
        parts.setUpdatedBy(userId);
        parts.setUpdatedTime(new Date());
        return parts;

    }

    private void checkParts(EditSavePartsEnter enter) {

        String partsNumber = enter.getPartsNumber();
        String snClassFlag = BomSnClassEnums.checkCode(enter.getSnClassFlag());
        String partsType = BomCommonTypeEnums.checkCode(enter.getPartsType());
        String sec = ESCUtils.checkESC(enter.getSec());
        if (StringUtils.isAnyBlank(partsNumber, snClassFlag, partsType, sec)) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_BASE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.PARTS_BASE_IS_ILLEGAL.getMessage());
        }
        if (!org.springframework.util.StringUtils.isEmpty(enter.getId())) {
            if (StringUtils.isAnyBlank(enter.getImportLot())) {
                throw new SesWebRosException(ExceptionCodeEnums.PARTS_BASE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.PARTS_BASE_IS_ILLEGAL.getMessage());
            }
        }
        QueryWrapper<OpeParts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeParts.COL_DR, 0);
        queryWrapper.eq(OpeParts.COL_PARTS_NUMBER, partsNumber);
        if (org.springframework.util.StringUtils.isEmpty(enter.getId())) {
            int count = partsService.count(queryWrapper);
            if (count > 0) {
                throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_EXIST.getCode(), ExceptionCodeEnums.PARTS_NUMBER_EXIST.getMessage());
            }
        }

    }
}
