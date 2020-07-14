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
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.parts.ESCUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dao.bom.PartsServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.bom.PartsRosService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.parts.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Reference
    private IdAppService idAppService;

    @Reference
    private GenerateService generateService;

    @Override
    public MapResult commonCountStatus(GeneralEnter enter) {
        QueryWrapper<OpePartsDraft> partsQueryWrapper = new QueryWrapper<>();
        partsQueryWrapper.eq(OpePartsDraft.COL_DR, 0);
        partsQueryWrapper.eq(OpePartsDraft.COL_USER_ID, enter.getUserId());
        int partsCount = getOpePartsDraftService.count(partsQueryWrapper);

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
            enters = JSONArray.parseArray(enter.getSt(), EditSavePartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<OpePartsDraft> updates = new ArrayList<>();
        List<OpePartsDraft> insters = new ArrayList<>();
        List<OpePartsDraftHistoryRecord> instersHistory = new ArrayList<>();

        if (enters.size() > 0) {
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

        //不管是 插入还是编辑
        ArrayList<OpePartsDraft> opePartsDrafts = Lists.newArrayList(insters);
        opePartsDrafts.addAll(updates);

        if (CollectionUtils.isNotEmpty(opePartsDrafts)) {
            opePartsDrafts.forEach(item -> {
                //校验部件信息是否完整
                if (StringUtils.isAnyEmpty(item.getSec(), item.getCnName(), item.getEnName(), item.getFrName(),
                        item.getSnClass(), item.getProductionCycle(), String.valueOf(item.getSupplierId()))) {
                    throw new SesWebRosException(ExceptionCodeEnums.PARTS_BASE_IS_NOT_COMPLETE_INFORMATION.getCode(), ExceptionCodeEnums.PARTS_BASE_IS_NOT_COMPLETE_INFORMATION.getMessage());
                }
            });
        }

        if (insters.size() > 0) {
            opePartsDraftService.saveBatch(insters);
        }
        if (updates.size() > 0) {
            opePartsDraftService.updateBatchById(updates);
        }
        if (instersHistory.size() > 0) {
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
        //返回历史记录
        DetailsPartsResult detailsPartsResult = new DetailsPartsResult();
        List<DetailsPartsResult> result = new ArrayList<>();
        try {
            partsDraft = JSONArray.parseArray(enter.getSt(), AddPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //判断是否进行部品号的迭代
        if (partsDraft.size() > 0) {
            partsDraft.forEach(p -> {
                OpePartsDraft originalPartsDraft = opePartsDraftService.getById(p.getId());
                Optional.ofNullable(originalPartsDraft).ifPresent(original -> {
                    //验证是否已存在
                    List<String> checkProductNums = bomRosServiceMapper.checkProductNums(enter);
                    if (checkProductNums.contains(p)) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                    //保存记录
                    OpePartsDraft newPartsDraft = new OpePartsDraft();
                    newPartsDraft.setId(original.getId());
                    newPartsDraft.setPartsNumber(p.getPartsNumber());
                    newPartsDraft.setUpdatedBy(enter.getUserId());
                    newPartsDraft.setUpdatedTime(new Date());
                    updatePartsDrafts.add(newPartsDraft);
                    savePartsDraftHistory.add(createPartsDraftHistory(original, PartsEventEnums.ADD.getValue(), enter.getUserId(), p.getPartsNumber()));

                    // 查询历史的订单编号
                    List<OpePartsDraftHistoryRecord> partsHistoryRecordList = partsDraftHistoryRecordService.list(new LambdaQueryWrapper<OpePartsDraftHistoryRecord>()
                            .eq(OpePartsDraftHistoryRecord::getPartsDraftId, newPartsDraft.getId())
                            .eq(OpePartsDraftHistoryRecord::getDr, Constant.DR_FALSE)
                            .eq(OpePartsDraftHistoryRecord::getEvent, PartsEventEnums.ADD.getValue()));
                    if (CollectionUtils.isEmpty(partsHistoryRecordList)) {
                        partsHistoryRecordList = new ArrayList<>();
                    }
                    // 返回修改的信息记录
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
        if (updatePartsDrafts.size() > 0 && savePartsDraftHistory.size() > 0) {
            opePartsDraftService.updateBatchById(updatePartsDrafts);
            partsDraftHistoryRecordService.saveBatch(savePartsDraftHistory);
        }

        return result;
    }

    @Transactional
    @Override
    public GeneralResult deletes(StringEnter enter) {

        List<IdEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), IdEnter.class);
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

        if (enters.size() > 0) {
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

        //查询是否已经将部件定稿
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.in(OpeParts.COL_PARTS_DRAFT_ID, enters.stream().map(IdEnter::getId).collect(Collectors.toList()));
        List<OpeParts> opePartsList = opePartsService.list(opePartsQueryWrapper);

        if (CollectionUtils.isNotEmpty(opePartsList)) {

            //是否绑定有产品进行校验
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
            List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
            if (CollectionUtils.isNotEmpty(partsProductBList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getMessage());
            }

            //删除定稿部品
            opePartsService.removeByIds(opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
            //todo 产品报价删除
//            QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
//            opePartsQueryWrapper.in(OpePriceSheet.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
//            opePriceSheetService.remove(opePriceSheetQueryWrapper);

            //报价记录删除
//            QueryWrapper<OpePriceSheetHistory> opePriceSheetHistoryQueryWrapper = new QueryWrapper<>();
//            opePriceSheetHistoryQueryWrapper.in(OpePriceSheetHistory.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
//            opePriceSheetHistoryService.remove(opePriceSheetHistoryQueryWrapper);
        }
        if (deletes.size() > 0) {
            opePartsDraftService.removeByIds(deletes);
            partsDraftHistoryRecordService.saveBatch(insters);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 查询部件绑定的商品
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeletePartResult> queryPartBindProduct(StringEnter enter) {
        List<IdEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), IdEnter.class);
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

        //校验部件是否有商品绑定
        List<DeletePartResult> result = buildDeletePartResult(partDraftIds, opePartDraftList);
        return result;
    }

    /**
     * 部品解绑商品
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult partUnbind(PartUnbindEnter enter) {
        OpePartsDraft partsDraft = opePartsDraftService.getById(enter.getId());
        if (partsDraft == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //查询商品 是否存在
        List<OpePartsProductB> partsProductBList = bomRosServiceMapper.opePartsProductBListByPartIDraftds(Lists.newArrayList(enter.getId()));
        if (CollectionUtils.isEmpty(partsProductBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_NOT_BIND_PRODUCT.getMessage());
        }

        List<Long> productIds = partsProductBList.stream().map(OpePartsProductB::getPartsProductId).collect(Collectors.toList());

        Collection<OpePartsProduct> opePartsProductList = opePartsProductService.listByIds(productIds);
        if (CollectionUtils.isEmpty(opePartsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        //查询商品部件
        partsProductBList.forEach(item -> {
            opePartsProductList.forEach(product -> {
                if (item.getPartsProductId().equals(product.getId())) {
                    product.setSumPartsQty(product.getSumPartsQty() - item.getPartsQty());
                    product.setUpdatedBy(enter.getUserId());
                    product.setUpdatedTime(new Date());
                }
            });
        });

        //移除绑定的部件
        opePartsProductBService.removeByIds(partsProductBList.stream().map(OpePartsProductB::getId).collect(Collectors.toList()));
        //更新productbom
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
        wrapper.eq(OpePartsDraftHistoryRecord.COL_DR, 0);
        wrapper.eq(OpePartsDraftHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsDraftHistoryRecord> historyLists = partsDraftHistoryRecordService.list(wrapper);
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
      if (enter.getKeyword()!=null && enter.getKeyword().length()>50){
        return PageResult.createZeroRowResult(enter);
      }
        int count = partsServiceMapper.listCount(enter);

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DetailsPartsResult> list = partsServiceMapper.list(enter);

        //去除历史重复部品号
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
        wrapper.eq(OpePartsDraftHistoryRecord.COL_DR, 0);
        wrapper.eq(OpePartsDraftHistoryRecord.COL_EVENT, PartsEventEnums.ADD.getValue());
        List<OpePartsDraftHistoryRecord> historyLists = partsDraftHistoryRecordService.list(wrapper);
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

    /**
     * 删除部品号历史
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

    @Transactional
    @Override
    public GeneralResult synchronizeParts(GeneralEnter enter) {

        //部件容器
        List<OpeParts> partsSave = new ArrayList<>();
        //产品容器
        List<OpePartsProduct> productSave = new ArrayList<>();

        //产品容器子表
        List<OpePartsProductB> savePartsProductBList = new ArrayList<>();
        //部件质检项集合
        List<OpePartQcTemplate> savePartQcTemplateList = new ArrayList<>();
        //部件质检项结果集集合
        List<OpePartQcTemplateB> savePartQcTemplateBList = new ArrayList<>();
        //草稿、定稿部件关系 Map
        Map<Long, Long> partDraftMap = Maps.newHashMap();
        //保存产品模板
        List<OpeProductQcTemplate> saveProductQcTemplate = new ArrayList<>();
        //保存产品模板
        List<OpeProductQcTemplateB> saveProductQcTemplateB = new ArrayList<>();

        //1.进行查询需要同步的部件
        List<OpePartsDraft> partsDrafts =
                opePartsDraftService.list(new LambdaQueryWrapper<OpePartsDraft>().eq(OpePartsDraft::getPerfectFlag, Boolean.TRUE).eq(OpePartsDraft::getSynchronizeFlag,
                        Boolean.FALSE).eq(OpePartsDraft::getUserId, enter.getUserId()));
        //2.将待同步的部件进行安装是否可进行销售，区分为生产部件及产品

        if (CollectionUtil.isNotEmpty(partsDrafts)) {
            partsDrafts.forEach(p -> {
                //设置已同步标识
                p.setSynchronizeFlag(Boolean.TRUE);
                OpeParts parts = new OpeParts();
                BeanUtils.copyProperties(p, parts);
                parts.setPartsDraftId(p.getId());
                partsSave.add(parts);
            });

            //更新已同步标识
            opePartsDraftService.updateBatch(partsDrafts);
        }
        //部件更新集合
        List<OpeParts> partsUpdate = new ArrayList<>();
        //部件插入集合
        List<OpeParts> partsInsert = getOpeParts(enter, partsSave, productSave, partDraftMap, partsUpdate);
        //部件更新集合
        List<OpePartsProduct> productUpdate = new ArrayList<>();
        //部件插入集合
        List<OpePartsProduct> productInsert = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(productSave)) {

            for (OpePartsProduct p : productSave) {
                OpePartsProduct oneProduct = partsProductService.getOne(new LambdaQueryWrapper<OpePartsProduct>().eq(OpePartsProduct::getDr, 0).eq(OpePartsProduct::getDef1, p.getDef1()).last("limit 1"));

                if (oneProduct == null) {
                    p.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT));
                    productInsert.add(p);
                } else {
                    p.setId(oneProduct.getId());
                    productUpdate.add(p);
                }
            }
            //将更新 保存集合放在一起 产品
            List<OpePartsProduct> opePartsProductList = Lists.newArrayList();
            opePartsProductList.addAll(productUpdate);
            if (CollectionUtils.isNotEmpty(productInsert)) {
                opePartsProductList.addAll(productInsert);
            }

            opePartsProductList.forEach(product -> {

                //查询是否子表数据
                QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
                opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_PRODUCT_ID, product.getId());
                opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_ID, Long.valueOf(product.getDef1()));
                opePartsProductBQueryWrapper.last("limit 1");
                OpePartsProductB partsProductB = opePartsProductBService.getOne(opePartsProductBQueryWrapper);
                //保存子表数据
                if (partsProductB == null) {
                    savePartsProductBList.add(buildPartProductB(enter, product.getId(), Long.valueOf(product.getDef1()), idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B)));
                } else {
                    savePartsProductBList.add(buildPartProductB(enter, partsProductB.getPartsProductId(), Long.valueOf(product.getDef1()), partsProductB.getId()));
                }
            });

            //将部件数据集合放在一起
            partsSave.clear();
            partsSave.addAll(partsInsert);
            partsSave.addAll(partsUpdate);

            //同步部件模板
            syncTemplateSingle(partsSave, savePartQcTemplateList, savePartQcTemplateBList, partDraftMap, saveProductQcTemplate, saveProductQcTemplateB, partsDrafts, opePartsProductList);

            //数据保存
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
                if (oneParts == null) {
                    p.setId(idAppService.getId(SequenceName.OPE_PARTS));
                    partsInsert.add(p);
                } else {
                    p.setId(oneParts.getId());
                    partsUpdate.add(p);
                }
                //无论是SCC 还是SC 都是产品 都需要进行部件
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
        if(p.getPartsType() != null){
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
        product.setAfterSalesFlag(false);
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
     * 同步商品、部件质检模板
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
        //查询部件质检模板记录
        List<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.list(new LambdaQueryWrapper<OpePartQcTemplate>().in(OpePartQcTemplate::getPartId,
                partsSave.stream().map(OpeParts::getId).collect(Collectors.toList())).eq(OpePartQcTemplate::getDr, 0));

        if (CollectionUtils.isNotEmpty(opePartQcTemplateList)) {
            //查询结果集
            List<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(new LambdaQueryWrapper<OpePartQcTemplateB>().in(OpePartQcTemplateB::getPartQcTemplateId,
                    opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList())).eq(OpePartQcTemplateB::getDr, 0));

            //删除部件的质检模板列表
            opePartQcTemplateService.removeByIds(opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(opePartQcTemplateBList)) {
                //删除质检项结果集
                opePartQcTemplateBService.removeByIds(opePartQcTemplateBList.stream().map(OpePartQcTemplateB::getId).collect(Collectors.toList()));
            }
        }

        //产品质检项
        List<OpeProductQcTemplate> opeProductQcTemplateList = opeProductQcTemplateService.list(new LambdaQueryWrapper<OpeProductQcTemplate>().in(OpeProductQcTemplate::getProductId,
                opePartsProductList.stream().map(OpePartsProduct::getId).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(opeProductQcTemplateList)) {
            //删除质检项
            opeProductQcTemplateService.remove(new LambdaQueryWrapper<OpeProductQcTemplate>().in(OpeProductQcTemplate::getId,
                    opeProductQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));

            //删除结果集
            opeProductQcTemplateBService.remove(new LambdaQueryWrapper<OpeProductQcTemplateB>().in(OpeProductQcTemplateB::getProductQcTemplateId,
                    opeProductQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));
        }

        //查询草稿部件质检项
        List<OpePartDraftQcTemplate> opePartDraftQcTemplateList = opePartDraftQcTemplateService.list(new LambdaQueryWrapper<OpePartDraftQcTemplate>().in(OpePartDraftQcTemplate::getPartDraftId,
                partsDrafts.stream().map(OpePartsDraft::getId).collect(Collectors.toList())));

        if (CollectionUtils.isNotEmpty(opePartDraftQcTemplateList)) {
            //查询质检项结果集
            List<OpePartDraftQcTemplateB> partDraftQcTemplateBList =
                    opePartDraftQcTemplateBService.list(new LambdaQueryWrapper<OpePartDraftQcTemplateB>().in(OpePartDraftQcTemplateB::getPartDraftQcTemplateId,
                            opePartDraftQcTemplateList.stream().map(OpePartDraftQcTemplate::getId).collect(Collectors.toList())));
            //同步部件模板数据

            opePartDraftQcTemplateList.forEach(item -> {
                OpePartQcTemplate opePartQcTemplate = new OpePartQcTemplate();
                BeanUtils.copyProperties(item, opePartQcTemplate);
                opePartQcTemplate.setId(idAppService.getId(SequenceName.OPE_PART_QC_TEMPLATE));
                opePartQcTemplate.setPartId(partDraftMap.get(item.getPartDraftId()));
                savePartQcTemplateList.add(opePartQcTemplate);

                //同步部件质检项结果集
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

        //同步产品模板
        if (CollectionUtils.isNotEmpty(savePartQcTemplateList)) {
            opePartsProductList.forEach(item -> {

                savePartQcTemplateList.forEach(template -> {

                    if (Long.valueOf(item.getDef1()).equals(template.getPartId())) {
                        //保存质检项
                        OpeProductQcTemplate opeProductQcTemplate = new OpeProductQcTemplate();
                        BeanUtils.copyProperties(template, opeProductQcTemplate);
                        opeProductQcTemplate.setId(idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE));
                        opeProductQcTemplate.setProductId(item.getId());
                        saveProductQcTemplate.add(opeProductQcTemplate);

                        //保存质检项结果
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
     * 同步部件模板
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
                .status(BomStatusEnums.NORMAL.getValue())
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
        if (count == 0) {
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
        if (count == 0) {
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
        record.setStatus(BomStatusEnums.NORMAL.getValue());
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

    private OpePartsDraft createPartsDraft(EditSavePartsEnter enter, String lot, long userId) {
        OpePartsDraft partsDraft = new OpePartsDraft();

        if (org.springframework.util.StringUtils.isEmpty(enter.getId())) {
            partsDraft.setId(idAppService.getId(SequenceName.OPE_PARTS_DRAFT));
            partsDraft.setDr(0);
            partsDraft.setTenantId(0L);
            partsDraft.setUserId(userId);
            partsDraft.setStatus(BomStatusEnums.NORMAL.getValue());
            partsDraft.setCreatedBy(userId);
            partsDraft.setCreatedTime(new Date());
            partsDraft.setImportLot(lot);
            partsDraft.setPartsNumber(enter.getPartsNumber());
        } else {
            partsDraft.setId(enter.getId());
        }
        if(enter.getPartsType() != null){
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

        if (enter.getIdClass() == null) {
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
     * 删除部件封装返回值
     *
     * @param partIds
     * @param opePartDraftList
     * @return
     */
    private List<DeletePartResult> buildDeletePartResult(Set<Long> partIds, Collection<OpePartsDraft> opePartDraftList) {

        //查询定稿部件数量
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.in(OpeParts.COL_PARTS_DRAFT_ID, opePartDraftList.stream().map(OpePartsDraft::getId).collect(Collectors.toList()));
        List<OpeParts> opePartsList = opePartsService.list(opePartsQueryWrapper);
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_ID, opePartsList.stream().map(OpeParts::getId).collect(Collectors.toList()));
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
        if (CollectionUtils.isNotEmpty(partsProductBList)) {
            //查询相应产品
            Map<Long, Long> productIdMaps = Maps.newHashMap();
            partsProductBList.forEach(item -> {
                productIdMaps.put(item.getPartsProductId(), item.getPartsId());
            });

            Collection<OpePartsProduct> opePartsProductList = opePartsProductService.listByIds(productIdMaps.entrySet().stream().map(item -> item.getKey()).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(opePartsProductList)) {
                List<DeletePartResult> result = new ArrayList<>();
                opePartsList.forEach(part -> {
                    if (productIdMaps.containsValue(part.getId())) {
                        //封装数据返回
                        List<DeletePartBindProductResult> scooterList = new ArrayList<>();

                        List<DeletePartBindProductResult> combinationList = new ArrayList<>();

                        opePartsProductList.forEach(item -> {
                            if (StringUtils.equals(item.getProductType().toString(), BomCommonTypeEnums.SCOOTER.getValue())) {
                                //整车
                                scooterList.add(
                                        DeletePartBindProductResult.builder()
                                                .id(item.getId())
                                                .cnName(item.getCnName())
                                                .enName(item.getEnName())
                                                .productN(item.getProductNumber())
                                                .build()
                                );
                            } else {
                                //组合
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
        return null;
    }
}
