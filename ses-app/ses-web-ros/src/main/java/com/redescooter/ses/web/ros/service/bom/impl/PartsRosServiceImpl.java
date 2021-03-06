package com.redescooter.ses.web.ros.service.bom.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomSnClassEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.PartsEventEnums;
import com.redescooter.ses.api.common.enums.product.PartsProductEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdsEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.MapResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.parts.ESCUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dao.bom.PartsServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplate;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.redescooter.ses.web.ros.dm.OpePartsDraftHistoryRecord;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplateB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeExcleImportService;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateBService;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateService;
import com.redescooter.ses.web.ros.service.base.OpePartQcTemplateBService;
import com.redescooter.ses.web.ros.service.base.OpePartQcTemplateService;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftHistoryRecordService;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpePartsTypeService;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateBService;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateService;
import com.redescooter.ses.web.ros.service.bom.PartsRosService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.parts.AddPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.DeletePartBindProductResult;
import com.redescooter.ses.web.ros.vo.bom.parts.DeletePartResult;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.EditSavePartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsDto;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartUnbindEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartsTypeResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private BomRosServiceMapper bomRosServiceMapper;

    @Autowired
    private OpeExcleImportService excleImportService;

    @Autowired
    private OpePartsProductService partsProductService;

    @Autowired
    private OpePartsDraftHistoryRecordService partsDraftHistoryRecordService;

    @Autowired
    private OpePartsTypeService partsTypeService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PartsServiceMapper partsServiceMapper;

    @Autowired
    private OpePartsService partsService;

    @Autowired
    private OpePartsDraftService opePartsDraftService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpePartsDraftService getOpePartsDraftService;

    @Autowired
    private OpePartQcTemplateService opePartQcTemplateService;

    @Autowired
    private OpePartQcTemplateBService opePartQcTemplateBService;

    @Autowired
    private OpeProductQcTemplateService opeProductQcTemplateService;

    @Autowired
    private OpeProductQcTemplateBService opeProductQcTemplateBService;

    @Autowired
    private OpePartDraftQcTemplateService opePartDraftQcTemplateService;

    @Autowired
    private OpePartDraftQcTemplateBService opePartDraftQcTemplateBService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private GenerateService generateService;

    @Override
    public MapResult commonCountStatus(GeneralEnter enter) {
        QueryWrapper<OpePartsDraft> partsQueryWrapper = new QueryWrapper<>();
        partsQueryWrapper.eq(OpePartsDraft.COL_DR, Constant.DR_FALSE);
        partsQueryWrapper.eq(OpePartsDraft.COL_USER_ID, enter.getUserId());
        int partsCount = getOpePartsDraftService.count(partsQueryWrapper);

        QueryWrapper<OpePartsProduct> scooter = new QueryWrapper<>();
        scooter.eq(OpePartsProduct.COL_DR, Constant.DR_FALSE);
        scooter.eq(OpePartsProduct.COL_USER_ID, enter.getUserId());
        scooter.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        int scooterConut = partsProductService.count(scooter);

        QueryWrapper<OpePartsProduct> combination = new QueryWrapper<>();
        combination.eq(OpePartsProduct.COL_DR, Constant.DR_FALSE);
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
        if (0 < list.size()) {
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
        List<OpePartsDraft> saveList = new ArrayList<>();
        List<OpePartsDraftHistoryRecord> instersHistory = new ArrayList<>();

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
            OpePartsDraft parts = createPartsDraft(saveParts, lot, enter.getUserId());
            saveList.add(parts);
            instersHistory.add(createPartsDraftHistory(parts, PartsEventEnums.CREATE.getValue(), enter.getUserId(), parts.getPartsNumber()));
        }

        OpeExcleImport excleImport = new OpeExcleImport();
        excleImport.setId(idAppService.getId(SequenceName.OPE_EXCLE_IMPORT));
        excleImport.setDr(0);
        excleImport.setStatus(String.valueOf(BomStatusEnums.NORMAL.getValue()));
        excleImport.setBatchNo(lot);
        excleImport.setCount(saveList.size());
        excleImport.setAttachment(enter.getUrl());
        excleImport.setServiceType("BOM??????");
        excleImport.setMessage("BOM??????????????????");
        excleImport.setCreatedBy(enter.getUserId());
        excleImport.setCreatedTime(new Date());
        excleImport.setUpdatedBy(enter.getUserId());
        excleImport.setUpdatedTime(new Date());
        excleImport.setDataJson(JSONArray.toJSONString(list));

        excleImportService.save(excleImport);
        opePartsDraftService.batchInsert(saveList);
        partsDraftHistoryRecordService.saveBatch(instersHistory);

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

        List<EditSavePartsEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), EditSavePartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpePartsDraft> updates = new ArrayList<>();
        List<OpePartsDraft> insters = new ArrayList<>();
        List<OpePartsDraftHistoryRecord> instersHistory = new ArrayList<>();

        if (0 < enters.size()) {
            String lot = generateService.getOrderNo();
            enters.forEach(pat -> {
                checkParts(pat);
                if (org.springframework.util.StringUtils.isEmpty(pat.getId())) {
                    OpePartsDraft partsDraft = createPartsDraft(pat, lot, enter.getUserId());
                    insters.add(partsDraft);
                    instersHistory.add(createPartsDraftHistory(partsDraft, PartsEventEnums.CREATE.getValue(), enter.getUserId(), partsDraft.getPartsNumber()));
                } else {
                    OpePartsDraft parts = createPartsDraft(pat, null, enter.getUserId());
                    updates.add(parts);
                    OpePartsDraft opePartsDraft = opePartsDraftService.getById(pat.getId());
                    instersHistory.add(createPartsDraftHistory(opePartsDraft, PartsEventEnums.UPDATE.getValue(), enter.getUserId(), opePartsDraft.getPartsNumber()));
                }
            });
        }

        //????????? ??????????????????
        ArrayList<OpePartsDraft> opePartsDrafts = Lists.newArrayList(insters);
        opePartsDrafts.addAll(updates);

        if (CollectionUtils.isNotEmpty(opePartsDrafts)) {
            opePartsDrafts.forEach(item -> {
                //??????????????????????????????
                if (StringUtils.isAnyEmpty(item.getSec(), item.getCnName(), item.getEnName(), item.getFrName(),
                        item.getSnClass(), item.getProductionCycle(), String.valueOf(item.getSupplierId()))) {
                    throw new SesWebRosException(ExceptionCodeEnums.PARTS_BASE_IS_NOT_COMPLETE_INFORMATION.getCode(), ExceptionCodeEnums.PARTS_BASE_IS_NOT_COMPLETE_INFORMATION.getMessage());
                }
            });
        }

        if (0 < insters.size()) {
            opePartsDraftService.saveBatch(insters);
        }
        if (0 < updates.size()) {
            opePartsDraftService.updateBatchById(updates);
        }
        if (0 < instersHistory.size()) {
            partsDraftHistoryRecordService.saveBatch(instersHistory);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Transient
    @Override
    public List<DetailsPartsResult> iterations(StringEnter enter) {

        List<AddPartsEnter> partsDraft = new ArrayList<>();
        List<OpePartsDraft> updatePartsDrafts = new ArrayList<>();
        List<OpePartsDraftHistoryRecord> savePartsDraftHistory = new ArrayList<>();
        //??????????????????
        DetailsPartsResult detailsPartsResult = new DetailsPartsResult();
        List<DetailsPartsResult> result = new ArrayList<>();
        try {
            partsDraft = JSONArray.parseArray(enter.getKeyword(), AddPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //????????????????????????????????????
        if (0 < partsDraft.size()) {
            partsDraft.forEach(p -> {
                OpePartsDraft originalPartsDraft = opePartsDraftService.getById(p.getId());
                Optional.ofNullable(originalPartsDraft).ifPresent(original -> {
                    //?????????????????????
                    List<String> checkProductNums = bomRosServiceMapper.checkProductNums(enter);
                    if (checkProductNums.contains(p)) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                    //????????????
                    OpePartsDraft newPartsDraft = new OpePartsDraft();
                    newPartsDraft.setId(original.getId());
                    newPartsDraft.setPartsNumber(p.getPartsNumber());
                    newPartsDraft.setUpdatedBy(enter.getUserId());
                    newPartsDraft.setUpdatedTime(new Date());
                    updatePartsDrafts.add(newPartsDraft);
                    savePartsDraftHistory.add(createPartsDraftHistory(original, PartsEventEnums.ADD.getValue(), enter.getUserId(), p.getPartsNumber()));

                    // ???????????????????????????
                    List<OpePartsDraftHistoryRecord> partsHistoryRecordList = partsDraftHistoryRecordService.list(new LambdaQueryWrapper<OpePartsDraftHistoryRecord>()
                            .eq(OpePartsDraftHistoryRecord::getPartsDraftId, newPartsDraft.getId())
                            .eq(OpePartsDraftHistoryRecord::getDr, Constant.DR_FALSE)
                            .eq(OpePartsDraftHistoryRecord::getEvent, PartsEventEnums.ADD.getValue()));
                    if (CollectionUtils.isEmpty(partsHistoryRecordList)) {
                        partsHistoryRecordList = new ArrayList<>();
                    }
                    // ???????????????????????????
                    List<HistoryPartsDto> historyPartsDtoList = new ArrayList<>();
                    partsHistoryRecordList.forEach(item -> {
                        historyPartsDtoList.add(HistoryPartsDto.builder()
                                .id(item.getId())
                                .createdTime(item.getCreatedTime())
                                .partsNumber(item.getPartsNumber())
                                .build());
                    });
                    detailsPartsResult.setId(newPartsDraft.getId());
                    detailsPartsResult.setPartsNumber(newPartsDraft.getPartsNumber());
                    detailsPartsResult.setHistoryHist(historyPartsDtoList);
                    result.add(detailsPartsResult);
                });
            });
        }
        if (0 < updatePartsDrafts.size() && 0 < savePartsDraftHistory.size()) {
            opePartsDraftService.updateBatchById(updatePartsDrafts);
            partsDraftHistoryRecordService.saveBatch(savePartsDraftHistory);
        }

        return result;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deletes(StringEnter enter) {

        List<IdEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), IdEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<Long> deletes = new ArrayList<>();
        List<OpePartsDraftHistoryRecord> insters = new ArrayList<>();

        Set<Long> partDraftIds = new HashSet<>();
        enters.forEach(item -> {
            partDraftIds.add(item.getId());
        });
        Collection<OpePartsDraft> opePartDraftList = opePartsDraftService.listByIds(new ArrayList<>(partDraftIds));
        if (CollectionUtils.isEmpty(opePartDraftList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        if (0 < enters.size()) {
            for (OpePartsDraft item : opePartDraftList) {
                enters.forEach(dl -> {
                    if (!org.springframework.util.StringUtils.isEmpty(dl.getId())) {
                        if (item.getId().equals(dl.getId())) {
                            deletes.add(item.getId());
                            insters.add(createPartsDraftHistory(item, PartsEventEnums.DELETE.getValue(), enter.getUserId(), item.getPartsNumber()));
                        }
                    }
                });
            }
        }

        //?????????????????????????????????
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.in(OpeParts.COL_PARTS_DRAFT_ID, enters.stream().map(IdEnter::getId).collect(Collectors.toList()));
        List<OpeParts> opePartsList = opePartsService.list(opePartsQueryWrapper);

        if (CollectionUtils.isNotEmpty(opePartsList)) {

            //?????????????????????????????????
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
            List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
            if (CollectionUtils.isNotEmpty(partsProductBList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getMessage());
            }

            //??????????????????
            opePartsService.removeByIds(opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
            //todo ??????????????????
//            QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
//            opePartsQueryWrapper.in(OpePriceSheet.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
//            opePriceSheetService.remove(opePriceSheetQueryWrapper);

            //??????????????????
//            QueryWrapper<OpePriceSheetHistory> opePriceSheetHistoryQueryWrapper = new QueryWrapper<>();
//            opePriceSheetHistoryQueryWrapper.in(OpePriceSheetHistory.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
//            opePriceSheetHistoryService.remove(opePriceSheetHistoryQueryWrapper);
        }
        if (0 < deletes.size()) {
            opePartsDraftService.removeByIds(deletes);
            partsDraftHistoryRecordService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeletePartResult> queryPartBindProduct(StringEnter enter) {
        List<IdEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), IdEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        Set<Long> partDraftIds = new HashSet<>();
        enters.forEach(item -> {
            partDraftIds.add(item.getId());
        });
        Collection<OpePartsDraft> opePartDraftList = opePartsDraftService.listByIds(new ArrayList<>(partDraftIds));
        if (CollectionUtils.isEmpty(opePartDraftList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //?????????????????????????????????
        List<DeletePartResult> result = buildDeletePartResult(partDraftIds, opePartDraftList);
        return result;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult partUnbind(PartUnbindEnter enter) {
        OpePartsDraft partsDraft = opePartsDraftService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(partsDraft)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //???????????? ????????????
        List<OpePartsProductB> partsProductBList = bomRosServiceMapper.opePartsProductBListByPartIDraftds(Lists.newArrayList(enter.getId()));
        if (CollectionUtils.isEmpty(partsProductBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_NOT_BIND_PRODUCT.getMessage());
        }

        List<Long> productIds = partsProductBList.stream().map(OpePartsProductB::getPartsProductId).collect(Collectors.toList());

        Collection<OpePartsProduct> opePartsProductList = opePartsProductService.listByIds(productIds);
        if (CollectionUtils.isEmpty(opePartsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        //??????????????????
        partsProductBList.forEach(item -> {
            opePartsProductList.forEach(product -> {
                if (item.getPartsProductId().equals(product.getId())) {
                    product.setSumPartsQty(product.getSumPartsQty() - item.getPartsQty());
                    product.setUpdatedBy(enter.getUserId());
                    product.setUpdatedTime(new Date());
                }
            });
        });

        //?????????????????????
        opePartsProductBService.removeByIds(partsProductBList.stream().map(OpePartsProductB::getId).collect(Collectors.toList()));
        //??????productbom
        opePartsProductService.updateBatchById(opePartsProductList);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public DetailsPartsResult details(IdEnter enter) {

        OpePartsDraft byId = opePartsDraftService.getById(enter.getId());

        DetailsPartsResult result = new DetailsPartsResult();
        BeanUtils.copyProperties(byId, result);

        QueryWrapper<OpePartsDraftHistoryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(OpePartsDraftHistoryRecord.COL_PARTS_DRAFT_ID, enter.getId());
        wrapper.eq(OpePartsDraftHistoryRecord.COL_DR, Constant.DR_FALSE);
        wrapper.eq(OpePartsDraftHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsDraftHistoryRecord> historyLists = partsDraftHistoryRecordService.list(wrapper);
        List<HistoryPartsDto> list = new ArrayList<>();

        if (0 < historyLists.size()) {
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
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        int count = partsServiceMapper.listCount(enter);

        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DetailsPartsResult> list = partsServiceMapper.list(enter);

        //???????????????????????????
        for (DetailsPartsResult item : list) {
            if (CollectionUtils.isNotEmpty(item.getHistoryHist())) {
                item.getHistoryHist().removeIf(hist -> StringUtils.equals(hist.getPartsNumber(), item.getPartsNumber()));
            }
        }

        return PageResult.create(enter, count, list);
    }

    @Override
    public HistoryPartsResult historys(IdEnter enter) {

        QueryWrapper<OpePartsDraftHistoryRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(OpePartsDraftHistoryRecord.COL_PARTS_DRAFT_ID, enter.getId());
        wrapper.eq(OpePartsDraftHistoryRecord.COL_DR, Constant.DR_FALSE);
        wrapper.eq(OpePartsDraftHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsDraftHistoryRecord> historyLists = partsDraftHistoryRecordService.list(wrapper);
        HistoryPartsResult historyPartsResult = new HistoryPartsResult();

        List<HistoryPartsDto> list = new ArrayList<>();

        if (0 < historyLists.size()) {
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

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult deleteHistorys(IdsEnter enter) {
        Collection<OpePartsDraftHistoryRecord> opePartsDraftHistoryRecordList = partsDraftHistoryRecordService.listByIds(enter.getIds());
        if (CollectionUtils.isEmpty(opePartsDraftHistoryRecordList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_HISTROY_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_HISTROY_TRACE_IS_NOT_EXIST.getMessage());
        }
        partsDraftHistoryRecordService.removeByIds(enter.getIds());
        return new GeneralResult(enter.getRequestId());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult synchronizeParts(GeneralEnter enter) {

        //????????????
        List<OpeParts> partsSave = new ArrayList<>();
        //????????????
        List<OpePartsProduct> productSave = new ArrayList<>();

        //??????????????????
        List<OpePartsProductB> savePartsProductBList = new ArrayList<>();
        //?????????????????????
        List<OpePartQcTemplate> savePartQcTemplateList = new ArrayList<>();
        //??????????????????????????????
        List<OpePartQcTemplateB> savePartQcTemplateBList = new ArrayList<>();
        //??????????????????????????? Map
        Map<Long, Long> partDraftMap = Maps.newHashMap();
        //??????????????????
        List<OpeProductQcTemplate> saveProductQcTemplate = new ArrayList<>();
        //??????????????????
        List<OpeProductQcTemplateB> saveProductQcTemplateB = new ArrayList<>();

        //1.?????????????????????????????????
        List<OpePartsDraft> partsDrafts =
                opePartsDraftService.list(new LambdaQueryWrapper<OpePartsDraft>().eq(OpePartsDraft::getPerfectFlag, Boolean.TRUE).eq(OpePartsDraft::getSynchronizeFlag,
                        Boolean.FALSE).eq(OpePartsDraft::getUserId, enter.getUserId()));
        //2.???????????????????????????????????????????????????????????????????????????????????????

        if (CollectionUtil.isNotEmpty(partsDrafts)) {
            partsDrafts.forEach(p -> {
                //?????????????????????
                p.setSynchronizeFlag(Boolean.TRUE);
                OpeParts parts = new OpeParts();
                BeanUtils.copyProperties(p, parts);
                parts.setPartsDraftId(p.getId());
                partsSave.add(parts);
            });

            //?????????????????????
            opePartsDraftService.updateBatch(partsDrafts);
        }
        //??????????????????
        List<OpeParts> partsUpdate = new ArrayList<>();
        //??????????????????
        List<OpeParts> partsInsert = getOpeParts(enter, partsSave, productSave, partDraftMap, partsUpdate);
        //??????????????????
        List<OpePartsProduct> productUpdate = new ArrayList<>();
        //??????????????????
        List<OpePartsProduct> productInsert = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(productSave)) {

            for (OpePartsProduct p : productSave) {
                OpePartsProduct oneProduct = partsProductService.getOne(new LambdaQueryWrapper<OpePartsProduct>().eq(OpePartsProduct::getDr, 0).eq(OpePartsProduct::getDef1, p.getDef1()).last("limit" +
                        " 1"));

                if (StringManaConstant.entityIsNull(oneProduct)) {
                    p.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT));
                    productInsert.add(p);
                } else {
                    p.setId(oneProduct.getId());
                    productUpdate.add(p);
                }
            }
            //????????? ???????????????????????? ??????
            List<OpePartsProduct> opePartsProductList = Lists.newArrayList();
            opePartsProductList.addAll(productUpdate);
            if (CollectionUtils.isNotEmpty(productInsert)) {
                opePartsProductList.addAll(productInsert);
            }

            opePartsProductList.forEach(product -> {

                //????????????????????????
                QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
                opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_PRODUCT_ID, product.getId());
                opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_ID, Long.valueOf(product.getDef1()));
                opePartsProductBQueryWrapper.last("limit 1");
                OpePartsProductB partsProductB = opePartsProductBService.getOne(opePartsProductBQueryWrapper);
                //??????????????????
                if (StringManaConstant.entityIsNull(partsProductB)) {
                    savePartsProductBList.add(buildPartProductB(enter, product.getId(), Long.valueOf(product.getDef1()), idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B)));
                } else {
                    savePartsProductBList.add(buildPartProductB(enter, partsProductB.getPartsProductId(), Long.valueOf(product.getDef1()), partsProductB.getId()));
                }
            });

            //?????????????????????????????????
            partsSave.clear();
            partsSave.addAll(partsInsert);
            partsSave.addAll(partsUpdate);

            //??????????????????
            syncTemplateSingle(partsSave, savePartQcTemplateList, savePartQcTemplateBList, partDraftMap, saveProductQcTemplate, saveProductQcTemplateB, partsDrafts, opePartsProductList);

            //????????????
            if (CollectionUtils.isNotEmpty(savePartQcTemplateList)) {
                opePartQcTemplateService.saveBatch(savePartQcTemplateList);
            }
            if (CollectionUtils.isNotEmpty(savePartQcTemplateBList)) {
                opePartQcTemplateBService.saveBatch(savePartQcTemplateBList);
            }
            if (CollectionUtils.isNotEmpty(saveProductQcTemplate)) {
                opeProductQcTemplateService.saveBatch(saveProductQcTemplate);
            }
            if (CollectionUtils.isNotEmpty(saveProductQcTemplateB)) {
                opeProductQcTemplateBService.saveBatch(saveProductQcTemplateB);
            }


            if (CollectionUtils.isNotEmpty(productInsert)) {
                partsProductService.saveBatch(productInsert);
            }
            if (CollectionUtils.isNotEmpty(productUpdate)) {
                partsProductService.updateBatch(productUpdate);
            }
            if (CollectionUtils.isNotEmpty(savePartsProductBList)) {
                opePartsProductBService.saveOrUpdateBatch(savePartsProductBList);
            }
        }

        return new GeneralResult(enter.getRequestId());
    }

    private List<OpeParts> getOpeParts(GeneralEnter enter, List<OpeParts> partsSave, List<OpePartsProduct> productSave, Map<Long, Long> partDraftMap, List<OpeParts> partsUpdate) {
        List<OpeParts> partsInsert = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(partsSave)) {
            partsSave.forEach(p -> {

                OpeParts oneParts = partsService.getOne(new LambdaQueryWrapper<OpeParts>().eq(OpeParts::getPartsDraftId, p.getPartsDraftId()).last("limit 1"));
                if (StringManaConstant.entityIsNull(oneParts)) {
                    p.setId(idAppService.getId(SequenceName.OPE_PARTS));
                    partsInsert.add(p);
                } else {
                    p.setId(oneParts.getId());
                    partsUpdate.add(p);
                }
                //?????????SCC ??????SC ???????????? ?????????????????????
//                if (p.getSnClass().equals(BomSnClassEnums.SSC.getValue())) {
                getProduct(enter, productSave, p);
//                }
                partDraftMap.put(p.getPartsDraftId(), p.getId());
            });
            if (CollectionUtils.isNotEmpty(partsInsert)) {
                partsService.saveBatch(partsInsert);
            }
            if (CollectionUtils.isNotEmpty(partsUpdate)) {
                partsService.updateBatch(partsUpdate);
            }
        }
        return partsInsert;
    }

    private void getProduct(GeneralEnter enter, List<OpePartsProduct> productSave, OpeParts p) {
        OpePartsProduct product = new OpePartsProduct();
        product.setDr(0);
        product.setTenantId(p.getTenantId());
        product.setUserId(p.getUserId());
        product.setStatus(PartsProductEnums.DOWN.getValue());
        product.setSnClass(p.getSnClass());
        if (StringManaConstant.entityIsNotNull(p.getPartsType())) {
            product.setProductType(Integer.parseInt(BomCommonTypeEnums.getValueByCode(p.getPartsType())));
        }
        product.setProductCode(p.getEnName());
        product.setProductNumber(p.getPartsNumber());
        product.setCnName(p.getCnName());
        product.setFrName(p.getFrName());
        product.setEnName(p.getEnName());
        product.setProductionCycle(p.getProductionCycle());
        product.setSumPartsQty(1);
        product.setModel(p.getPartsNumber());
        product.setPictures(p.getDwg());
        product.setAfterSalesFlag(StringUtils.equals(p.getSnClass(), BomSnClassEnums.SSC.getValue()));
        product.setAddedServicesFlag(false);
        product.setRevision(0);
        product.setCreatedBy(enter.getUserId());
        product.setCreatedTime(new Date());
        product.setUpdatedBy(enter.getUserId());
        product.setUpdatedTime(new Date());
        product.setDef1(String.valueOf(p.getId()));
        productSave.add(product);
    }

    /**
     * ?????????????????????????????????
     *
     * @param partsSave
     * @param savePartQcTemplateList
     * @param savePartQcTemplateBList
     * @param partDraftMap
     * @param saveProductQcTemplate
     * @param saveProductQcTemplateB
     * @param partsDrafts
     * @param opePartsProductList
     */
    private void syncTemplateSingle(List<OpeParts> partsSave, List<OpePartQcTemplate> savePartQcTemplateList, List<OpePartQcTemplateB> savePartQcTemplateBList, Map<Long, Long> partDraftMap,
                                    List<OpeProductQcTemplate> saveProductQcTemplate, List<OpeProductQcTemplateB> saveProductQcTemplateB, List<OpePartsDraft> partsDrafts,
                                    List<OpePartsProduct> opePartsProductList) {
        //??????????????????????????????
        List<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.list(new LambdaQueryWrapper<OpePartQcTemplate>().in(OpePartQcTemplate::getPartId,
                partsSave.stream().map(OpeParts::getId).collect(Collectors.toList())).eq(OpePartQcTemplate::getDr, 0));

        if (CollectionUtils.isNotEmpty(opePartQcTemplateList)) {
            //???????????????
            List<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(new LambdaQueryWrapper<OpePartQcTemplateB>().in(OpePartQcTemplateB::getPartQcTemplateId,
                    opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList())).eq(OpePartQcTemplateB::getDr, 0));

            //?????????????????????????????????
            opePartQcTemplateService.removeByIds(opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(opePartQcTemplateBList)) {
                //????????????????????????
                opePartQcTemplateBService.removeByIds(opePartQcTemplateBList.stream().map(OpePartQcTemplateB::getId).collect(Collectors.toList()));
            }
        }

        //???????????????
        List<OpeProductQcTemplate> opeProductQcTemplateList = opeProductQcTemplateService.list(new LambdaQueryWrapper<OpeProductQcTemplate>().in(OpeProductQcTemplate::getProductId,
                opePartsProductList.stream().map(OpePartsProduct::getId).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(opeProductQcTemplateList)) {
            //???????????????
            opeProductQcTemplateService.remove(new LambdaQueryWrapper<OpeProductQcTemplate>().in(OpeProductQcTemplate::getId,
                    opeProductQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));

            //???????????????
            opeProductQcTemplateBService.remove(new LambdaQueryWrapper<OpeProductQcTemplateB>().in(OpeProductQcTemplateB::getProductQcTemplateId,
                    opeProductQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));
        }

        //???????????????????????????
        List<OpePartDraftQcTemplate> opePartDraftQcTemplateList = opePartDraftQcTemplateService.list(new LambdaQueryWrapper<OpePartDraftQcTemplate>().in(OpePartDraftQcTemplate::getPartDraftId,
                partsDrafts.stream().map(OpePartsDraft::getId).collect(Collectors.toList())));

        if (CollectionUtils.isNotEmpty(opePartDraftQcTemplateList)) {
            //????????????????????????
            List<OpePartDraftQcTemplateB> partDraftQcTemplateBList =
                    opePartDraftQcTemplateBService.list(new LambdaQueryWrapper<OpePartDraftQcTemplateB>().in(OpePartDraftQcTemplateB::getPartDraftQcTemplateId,
                            opePartDraftQcTemplateList.stream().map(OpePartDraftQcTemplate::getId).collect(Collectors.toList())));
            //????????????????????????

            opePartDraftQcTemplateList.forEach(item -> {
                OpePartQcTemplate opePartQcTemplate = new OpePartQcTemplate();
                BeanUtils.copyProperties(item, opePartQcTemplate);
                opePartQcTemplate.setId(idAppService.getId(SequenceName.OPE_PART_QC_TEMPLATE));
                opePartQcTemplate.setPartId(partDraftMap.get(item.getPartDraftId()));
                savePartQcTemplateList.add(opePartQcTemplate);

                //??????????????????????????????
                partDraftQcTemplateBList.forEach(templateB -> {
                    if (item.getId().equals(templateB.getPartDraftQcTemplateId())) {
                        OpePartQcTemplateB opePartQcTemplateB = new OpePartQcTemplateB();
                        BeanUtils.copyProperties(templateB, opePartQcTemplateB);
                        opePartQcTemplateB.setId(idAppService.getId(SequenceName.OPE_PART_QC_TEMPLATE_B));
                        opePartQcTemplateB.setPartQcTemplateId(opePartQcTemplate.getId());
                        savePartQcTemplateBList.add(opePartQcTemplateB);
                    }
                });
            });
        }

        //??????????????????
        if (CollectionUtils.isNotEmpty(savePartQcTemplateList)) {
            opePartsProductList.forEach(item -> {

                savePartQcTemplateList.forEach(template -> {

                    if (Long.valueOf(item.getDef1()).equals(template.getPartId())) {
                        //???????????????
                        OpeProductQcTemplate opeProductQcTemplate = new OpeProductQcTemplate();
                        BeanUtils.copyProperties(template, opeProductQcTemplate);
                        opeProductQcTemplate.setId(idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE));
                        opeProductQcTemplate.setProductId(item.getId());
                        saveProductQcTemplate.add(opeProductQcTemplate);

                        //?????????????????????
                        savePartQcTemplateBList.forEach(templateB -> {
                            if (template.getId().equals(templateB.getPartQcTemplateId())) {
                                OpeProductQcTemplateB opeProductQcTemplateB = new OpeProductQcTemplateB();
                                BeanUtils.copyProperties(template, opeProductQcTemplateB);
                                opeProductQcTemplateB.setId(idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE_B));
                                opeProductQcTemplateB.setProductQcTemplateId(template.getId());
                                opeProductQcTemplateB.setQcResult(templateB.getQcResult());
                                opeProductQcTemplateB.setPassFlag(templateB.getPassFlag());
                                opeProductQcTemplateB.setUploadFlag(templateB.getUploadFlag());
                                opeProductQcTemplateB.setResultsSequence(templateB.getResultsSequence());
                                saveProductQcTemplateB.add(opeProductQcTemplateB);
                            }
                        });
                    }

                });
            });
        }
    }

    /**
     * ??????????????????
     *
     * @param partsSave
     * @param savePartQcTemplateList
     * @param savePartQcTemplateBList
     * @param partDraftMap
     * @param partsDrafts
     */
    private void syncPartTemplateSingle(List<OpeParts> partsSave, List<OpePartQcTemplate> savePartQcTemplateList, List<OpePartQcTemplateB> savePartQcTemplateBList, Map<Long, Long> partDraftMap,
                                        List<OpePartsDraft> partsDrafts) {
    }

    private OpePartsProductB buildPartProductB(GeneralEnter enter, Long productId, Long partId, Long id) {
        return OpePartsProductB.builder()
                .id(id)
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .status(String.valueOf(BomStatusEnums.NORMAL.getValue()))
                .partsProductId(productId)
                .partsId(partId)
                .partsQty(1)
                .note(null)
                .revision(0)
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .build();
    }

    @Override
    public IntResult synchronizeNum(GeneralEnter enter) {
        return partsServiceMapper.synchronizeNum(enter);
    }

    @Override
    public PageResult<QueryPartListResult> partList(QueryPartListEnter enter) {
        int count = partsServiceMapper.partListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, partsServiceMapper.partList(enter));
    }

    /**
     * @param enter
     * @return
     */
    @Override
    public PageResult<DetailsPartsResult> saveProductPartList(QueryPartListEnter enter) {
        int count = partsServiceMapper.saveProductPartListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DetailsPartsResult> list = partsServiceMapper.saveProductPartList(enter);
        return PageResult.create(enter, count, list);
    }

    private OpePartsDraftHistoryRecord createPartsDraftHistory(OpePartsDraft partsDraft, String event, long userId, String partNum) {
        OpePartsDraftHistoryRecord record = new OpePartsDraftHistoryRecord();
        record.setId(idAppService.getId(SequenceName.OPE_PARTS_DRAFT_HISTORY_RECORD));
        record.setDr(0);
        record.setTenantId(0L);
        record.setUserId(userId);
        record.setStatus(String.valueOf(BomStatusEnums.NORMAL.getValue()));
        record.setPartsNumber(partNum);
        record.setImportLot(partsDraft.getImportLot());
        record.setPartsDraftId(partsDraft.getId());
        record.setEvent(event);
        record.setPartsType(partsDraft.getPartsType());
        record.setSec(partsDraft.getSec());
        record.setCnName(partsDraft.getCnName());
        record.setFrName(partsDraft.getFrName());
        record.setEnName(partsDraft.getEnName());
        record.setSnClass(partsDraft.getSnClass());
        record.setPartsQty(0);
        record.setProductionCycle(partsDraft.getProductionCycle());
        record.setSupplierId(partsDraft.getSupplierId());
        record.setDwg(partsDraft.getDwg());
        record.setNote(partsDraft.getNote());
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
            parts.setStatus(String.valueOf(BomStatusEnums.NORMAL.getValue()));
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

    private OpePartsDraft createPartsDraft(EditSavePartsEnter enter, String lot, long userId) {
        OpePartsDraft partsDraft = new OpePartsDraft();

        if (org.springframework.util.StringUtils.isEmpty(enter.getId())) {
            partsDraft.setId(idAppService.getId(SequenceName.OPE_PARTS_DRAFT));
            partsDraft.setDr(0);
            partsDraft.setTenantId(0L);
            partsDraft.setUserId(userId);
            partsDraft.setStatus(String.valueOf(BomStatusEnums.NORMAL.getValue()));
            partsDraft.setCreatedBy(userId);
            partsDraft.setCreatedTime(new Date());
            partsDraft.setImportLot(lot);
            partsDraft.setPartsNumber(enter.getPartsNumber());
        } else {
            partsDraft.setId(enter.getId());
        }
        if (StringManaConstant.entityIsNotNull(enter.getPartsType())) {
            partsDraft.setPartsType(BomCommonTypeEnums.checkCode(enter.getPartsType()));
        }
        partsDraft.setSnClass(enter.getSnClassFlag());
        partsDraft.setSec(ESCUtils.checkESC(enter.getSec()));
        partsDraft.setCnName(enter.getCnName());
        partsDraft.setFrName(enter.getFrName());
        partsDraft.setEnName(enter.getEnName());
        partsDraft.setProductionCycle(enter.getProductionCycle());

        partsDraft.setSupplierId(enter.getSupplierId());
        partsDraft.setDwg(enter.getDwg());
        partsDraft.setNote(enter.getNote());

        if (StringUtils.isNoneBlank(partsDraft.getSec(), partsDraft.getCnName(), partsDraft.getEnName(), partsDraft.getFrName(),
                partsDraft.getSnClass(), partsDraft.getProductionCycle(), String.valueOf(partsDraft.getSupplierId()))) {
            partsDraft.setPerfectFlag(Boolean.TRUE);

        } else {
            partsDraft.setPerfectFlag(Boolean.FALSE);
        }

        if (StringManaConstant.entityIsNull(enter.getIdClass())) {
            partsDraft.setIdClass(Boolean.FALSE);
        } else {
            partsDraft.setIdClass(enter.getIdClass());
        }
        partsDraft.setSynchronizeFlag(Boolean.FALSE);
        partsDraft.setRevision(0);
        partsDraft.setUpdatedBy(userId);
        partsDraft.setUpdatedTime(new Date());
        return partsDraft;

    }

    private void checkParts(EditSavePartsEnter enter) {

        String partsNumber = enter.getPartsNumber();
        String snClassFlag = BomSnClassEnums.checkValue(enter.getSnClassFlag());
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

    /**
     * ???????????????????????????
     *
     * @param partIds
     * @param opePartDraftList
     * @return
     */
    private List<DeletePartResult> buildDeletePartResult(Set<Long> partIds, Collection<OpePartsDraft> opePartDraftList) {

        //????????????????????????
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.in(OpeParts.COL_PARTS_DRAFT_ID, opePartDraftList.stream().map(OpePartsDraft::getId).collect(Collectors.toList()));
        List<OpeParts> opePartsList = opePartsService.list(opePartsQueryWrapper);
        if (!CollectionUtils.isEmpty(opePartsList)) {
//            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
            List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
            if (CollectionUtils.isNotEmpty(partsProductBList)) {
                //??????????????????
                Map<Long, Long> productIdMaps = Maps.newHashMap();
                partsProductBList.forEach(item -> {
                    productIdMaps.put(item.getPartsProductId(), item.getPartsId());
                });

                Collection<OpePartsProduct> opePartsProductList = opePartsProductService.listByIds(productIdMaps.entrySet().stream().map(item -> item.getKey()).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(opePartsProductList)) {
                    List<DeletePartResult> result = new ArrayList<>();
                    opePartsList.forEach(part -> {
                        if (productIdMaps.containsValue(part.getId())) {
                            //??????????????????
                            List<DeletePartBindProductResult> scooterList = new ArrayList<>();

                            List<DeletePartBindProductResult> combinationList = new ArrayList<>();

                            opePartsProductList.forEach(item -> {
                                if (StringUtils.equals(item.getProductType().toString(), BomCommonTypeEnums.SCOOTER.getValue())) {
                                    //??????
                                    scooterList.add(
                                            DeletePartBindProductResult.builder()
                                                    .id(item.getId())
                                                    .cnName(item.getCnName())
                                                    .enName(item.getEnName())
                                                    .productN(item.getProductNumber())
                                                    .build()
                                    );
                                } else {
                                    //??????
                                    combinationList.add(
                                            DeletePartBindProductResult.builder()
                                                    .id(item.getId())
                                                    .cnName(item.getCnName())
                                                    .enName(item.getEnName())
                                                    .productN(item.getProductNumber())
                                                    .build()
                                    );
                                }
                            });
                            result.add(
                                    DeletePartResult.builder()
                                            .id(part.getPartsDraftId())
                                            .cnName(part.getCnName())
                                            .enName(part.getEnName())
                                            .partsN(part.getPartsNumber())
                                            .scooterList(scooterList)
                                            .combinationList(combinationList)
                                            .build()
                            );
                        }
                    });
                    return result;
                }
            }
        }
        return new ArrayList<>();
    }
}
