package com.redescooter.ses.web.ros.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomAssTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.PartsEventEnums;
import com.redescooter.ses.api.common.enums.bom.SNClassEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.PartsServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.ExcelService;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyService;
import com.redescooter.ses.web.ros.service.base.OpePartsHistoryRecordService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.vo.bom.parts.AddPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.EditPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;

import com.redescooter.ses.web.ros.service.base.OpePartsTypeService;
import lombok.extern.slf4j.Slf4j;

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
    private OpePartsAssemblyService partsAssemblyService;

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
        int partsCount = partsService.count(partsQueryWrapper);

        QueryWrapper<OpePartsAssembly> scooter = new QueryWrapper<>();
        scooter.eq(OpePartsAssembly.COL_DR, 0);
        scooter.eq(OpePartsAssembly.COL_ASS_TYPE, BomAssTypeEnums.SCOOTER.getValue());
        int scooterConut = partsAssemblyService.count(scooter);

        QueryWrapper<OpePartsAssembly> combination = new QueryWrapper<>();
        combination.eq(OpePartsAssembly.COL_DR, 0);
        combination.eq(OpePartsAssembly.COL_ASS_TYPE, BomAssTypeEnums.COMBINATION.getValue());
        int combinationConut = partsAssemblyService.count(combination);

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
        OpeParts save = null;
        List<OpeParts> saveList = new ArrayList<>();
        String lot = generateService.getOrderNo();
        for (ExpressPartsExcleData excleData : list) {
            save = new OpeParts();
            save.setId(idAppService.getId(SequenceName.OPE_PARTS));
            save.setDr(0);
            save.setTenantId(0L);
            save.setUserId(enter.getUserId());
            save.setImportLot(lot);
            save.setStatus(BomStatusEnums.NORMAL.getValue());
            save.setPartsType(excleData.getType());
            save.setSec(excleData.getEsc());
            save.setPartsNumber(excleData.getPartsN());
            save.setCnName(excleData.getCnName());
            save.setFrName(excleData.getFrName());
            save.setEnName(excleData.getEnName());
            save.setSnClassFlag(SNClassEnums.getValueByCode(excleData.getSnClass()));
            save.setPartsQty(0);
            save.setSupplierId(0L);
            save.setRevision(0);
            save.setCreatedBy(enter.getUserId());
            save.setCreatedTime(new Date());
            save.setUpdatedBy(enter.getUserId());
            save.setUpdatedTime(new Date());
            saveList.add(save);
        }
        partsService.batchInsert(saveList);

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

        List<EditPartsEnter> enters = null;
        try {
            enters = JSONArray.parseArray(enter.getSt(), EditPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpeParts> updates = new ArrayList<>();
        List<OpePartsHistoryRecord> insters = new ArrayList<>();
        if (enters.size() > 0) {
            enters.forEach(pat -> {
                if (pat.getId() != null || pat.getId() != 0) {
                    OpeParts byId = partsService.getById(pat.getId());
                    //todo 更新验证
                    OpeParts update = new OpeParts();
                    update.setId(byId.getId());
                    update.setPartsType(pat.getPartsType());
                    update.setSec(pat.getSec());
                    update.setCnName(pat.getCnName());
                    update.setFrName(pat.getFrName());
                    update.setEnName(pat.getEnName());
                    update.setSnClassFlag(pat.getSnClassFlag());
                    update.setProductionCycle(pat.getProductionCycle());
                    update.setSupplierId(pat.getSupplierId() == 0 ? null : pat.getSupplierId());
                    update.setDwg(pat.getDwg());
                    update.setUpdatedBy(enter.getUserId());
                    update.setUpdatedTime(new Date());
                    //保存记录
                    if (StringUtils.isNotBlank(pat.getPartsNumber()) && pat.getPartsNumber().equals(byId.getPartsNumber())) {
                        update.setPartsNumber(pat.getPartsNumber());
                        insters.add(getPartsHistoryRecord(byId, PartsEventEnums.ADD.getValue(), enter.getUserId()));
                    }
                    updates.add(update);
                }

            });
        }

        if (updates.size() > 0) {
            partsService.updateBatch(updates);
        }
        if (insters.size() > 0) {
            partsHistoryRecordService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Transient
    @Override
    public GeneralResult adds(StringEnter enter) {

        List<AddPartsEnter> enters = null;
        try {
            enters = JSONArray.parseArray(enter.getSt(), AddPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpeParts> insters = new ArrayList<>();

        if (enters.size() > 0) {
            String lot = generateService.getOrderNo();
            enters.forEach(pat -> {
                OpeParts parts = new OpeParts();
                BeanUtils.copyProperties(pat, parts);
                parts.setId(idAppService.getId(SequenceName.OPE_PARTS));
                parts.setDr(0);
                parts.setTenantId(enter.getTenantId());
                parts.setUserId(enter.getUserId());
                parts.setImportLot(lot);
                parts.setStatus(BomStatusEnums.NORMAL.getValue());
                parts.setSupplierId(pat.getSupplierId());
                parts.setRevision(0);
                parts.setCreatedBy(enter.getUserId());
                parts.setCreatedTime(new Date());
                parts.setUpdatedBy(enter.getUserId());
                parts.setUpdatedTime(new Date());
                insters.add(parts);
            });
        }
        if (insters.size() > 0) {
            partsService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
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

        List<Long> update = new ArrayList<>();
        List<OpePartsHistoryRecord> insters = new ArrayList<>();
        if (enters.size() > 0) {
            enters.forEach(dl -> {
                if (dl.getId() != null || dl.getId() != 0) {
                    OpeParts byId = partsService.getById(dl.getId());
                    if (byId != null) {
                        insters.add(getPartsHistoryRecord(byId, PartsEventEnums.DELETE.getValue(), enter.getUserId()));
                        update.add(byId.getId());
                    }
                }
            });
        }
        if (update.size() > 0) {
            partsService.removeByIds(update);
            partsHistoryRecordService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public DetailsPartsResult details(IdEnter enter) {

        OpeParts byId = partsService.getById(enter.getId());

        DetailsPartsResult result = new DetailsPartsResult();
        BeanUtils.copyProperties(byId, result);

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
    public HistoryPartsResult history(IdEnter enter) {

        QueryWrapper<OpePartsHistoryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(OpePartsHistoryRecord.COL_PARTS_ID, enter.getId());
        wrapper.eq(OpePartsHistoryRecord.COL_DR, 0);

        List<OpePartsHistoryRecord> historyLists = partsHistoryRecordService.list(wrapper);
        HistoryPartsResult historyPartsResult = new HistoryPartsResult();

        List<HistoryPartsDto> list = new ArrayList<>();

        if (historyLists.size() > 0) {
            historyLists.forEach(ht -> {
                HistoryPartsDto dto = new HistoryPartsDto();
                dto.setPartsNumber(ht.getPartsNumber());
                dto.setCreatedTime(ht.getCreatedTime());
                list.add(dto);
            });
        }

        historyPartsResult.setList(list);

        return historyPartsResult;
    }


    private OpePartsHistoryRecord getPartsHistoryRecord(OpeParts parts, String event, long userId) {
        OpePartsHistoryRecord record = new OpePartsHistoryRecord();
        BeanUtils.copyProperties(parts, record);
        record.setId(idAppService.getId(SequenceName.OPE_PARTS_HISTORY_RECORD));
        record.setPartsId(parts.getId());
        record.setEvent(event);
        record.setRevision(0);
        record.setCreatedBy(userId);
        record.setCreatedTime(new Date());
        record.setUpdatedBy(userId);
        record.setUpdatedTime(new Date());
        return record;
    }
}
