package com.redescooter.ses.mobile.rps.service.material.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.QcTypeEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.material.MaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplate;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplateB;
import com.redescooter.ses.mobile.rps.dm.OpeParts;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQc;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQcItem;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.base.OpePartQcTemplateBService;
import com.redescooter.ses.mobile.rps.service.base.OpePartQcTemplateService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBQcItemService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBQcService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasQcTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasService;
import com.redescooter.ses.mobile.rps.service.base.impl.OpePurchasTraceService;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcDetailEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcTemplateDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.PartQcResultResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.PartTemplateEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.PartTemplateResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.ReturnedCompletedEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:MaterialService
 * @description: MaterialService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:23
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialServiceMapper materialServiceMapper;

    @Autowired
    private OpePurchasService opePurchasService;

    @Autowired
    private OpePurchasBService opePurchasBService;

    @Autowired
    private OpePartQcTemplateService opePartQcTemplateService;

    @Autowired
    private OpePartQcTemplateBService opePartQcTemplateBService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private BussinessNumberService bussinessNumberService;

    @Autowired
    private OpePurchasTraceService opePurchasTraceService;

    @Autowired
    private OpePurchasBQcService opePurchasBQcService;

    @Autowired
    private OpePurchasBQcItemService opePurchasBQcItemService;

    @Autowired
    private OpePurchasQcTraceService opePurchasQcTraceService;

    @Reference
    private IdAppService idAppService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByStatus(GeneralEnter enter) {
        QueryWrapper<OpePurchas> opePurchasQueryWrapper = new QueryWrapper<>();
        opePurchasQueryWrapper.eq(OpePurchas.COL_DR, 0);
        opePurchasQueryWrapper.eq(OpePurchas.COL_STATUS, PurchasingStatusEnums.MATERIALS_QC.getValue());

        Map<String, Integer> map = Maps.newHashMap();
        map.put(QcTypeEnums.WAIT.getValue(), opePurchasService.count(opePurchasQueryWrapper));
        map.put(QcTypeEnums.FAIL.getValue(), materialServiceMapper.qcFailType(enter));
        return map;
    }

    /**
     * 来料质检列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialQcListResult> list(PageEnter enter) {
        QueryWrapper<OpePurchas> opePurchasQueryWrapper = new QueryWrapper<>();
        opePurchasQueryWrapper.eq(OpePurchas.COL_STATUS, PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchasQueryWrapper.eq(OpePurchas.COL_DR, 0);
        int count = opePurchasService.count(opePurchasQueryWrapper);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, materialServiceMapper.list(enter));
    }

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialQcListResult> failList(PageEnter enter) {

        int count = materialServiceMapper.failListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, 1, materialServiceMapper.failList(enter));
    }

    /**
     * 退回并完成
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult returnedCompleted(ReturnedCompletedEnter enter) {
        return null;
    }

    /**
     * 继续质检
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult againQc(IdEnter enter) {
        return null;
    }

    /**
     * 来料质检详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialDetailResult> materialQcDetail(MaterialQcDetailEnter enter) {
        //订单、状态 过滤
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, enter.getId());
        int count = opePurchasBService.count(opePurchasBQueryWrapper);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, materialServiceMapper.detail(enter));
    }

    /**
     * 来料质检失败详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialDetailResult> materialQcFailDetail(MaterialQcDetailEnter enter) {
        //订单、状态 过滤
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        int count = materialServiceMapper.qcFailDetailCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        return PageResult.create(enter, count, materialServiceMapper.qcFailDetail(enter));
    }

    /**
     * 部件质检模板
     *
     * @param enter
     * @return
     */
    @Override
    public MaterialQcTemplateDetailResult MaterialQcTemplate(IdEnter enter) {
        //采购单 校验
        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        OpeParts opeParts = opePartsService.getById(opePurchasB.getPartId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //模板质检项 查询
        QueryWrapper<OpePartQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_PART_ID, opePurchasB.getPartId());
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_DR, 0);
        List<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }

        //质检项 结果集查询
        QueryWrapper<OpePartQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_DR, 0);
        opePartQcTemplateBQueryWrapper.in(OpePartQcTemplateB.COL_PART_QC_TEMPLATE_ID, opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
        List<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }

        //封装质检模板
        List<PartTemplateResult> partTemplateList = Lists.newArrayList();
        opePartQcTemplateList.forEach(item -> {
            List<PartQcResultResult> partQcResultList = Lists.newArrayList();
            opePartQcTemplateBList.forEach(templateB -> {
                if (item.getId().equals(templateB.getPartQcTemplateId())) {
                    partQcResultList.add(
                            PartQcResultResult.builder()
                                    .id(templateB.getId())
                                    .partQcTemplateId(templateB.getPartQcTemplateId())
                                    .qcResult(templateB.getQcResult())
                                    .uploadFlag(templateB.getUploadFlag())
                                    .resultsSequence(templateB.getResultsSequence())
                                    .build()
                    );
                }
            });
            partTemplateList.add(
                    PartTemplateResult.builder()
                            .id(item.getId())
                            .qcItemName(item.getQcItemName())
                            .partQcResultList(partQcResultList)
                            .build()
            );

        });

        return MaterialQcTemplateDetailResult.builder()
                .id(opePurchasB.getId())
                .partCnName(opeParts.getCnName())
                .partN(opeParts.getPartsNumber())
                .partId(opeParts.getId())
                .partTemplateList(partTemplateList)
                .build();
    }

    /**
     * 保存来料质检结果
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SaveMaterialQcResult saveMaterialQc(SaveMaterialQcEnter enter) {
        List<PartTemplateEnter> partQcResultList = Lists.newArrayList();
        //设置 模板、模板结果 之间关系
        Map<PartTemplateEnter, Long> templateMap = Maps.newHashMap();
        //质检通过模板
        Map<OpePartQcTemplate, List<OpePartQcTemplateB>> passTemplateMap = Maps.newHashMap();
        //质检项结果保存
        List<OpePurchasQcTrace> opePurchasQcTraceList = new ArrayList<>();

        //对入参数据做校验
        OpePurchasB opePurchasB = checkSaveMaterialEnter(enter, partQcResultList, templateMap, passTemplateMap);

        OpeParts opeParts = opePartsService.getById(opePurchasB.getPartId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //根据质检类型校验质检数量
        if (opeParts.getIdClass().equals(Boolean.TRUE)) {
            if (enter.getQty() == null || enter.getQty() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getCode(), ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getMessage());
            }
        }

        //质检结果
        Boolean qcResult = Boolean.TRUE;
        for (Map.Entry<PartTemplateEnter, Long> entry : templateMap.entrySet()) {
            PartTemplateEnter partTemplateEnter = entry.getKey();
            Long value = entry.getValue();

            //质检项
            Boolean qcItem=Boolean.FALSE;
            for (OpePartQcTemplate item : passTemplateMap.keySet()) {
                if (item.getId().equals(partTemplateEnter.getId())) {
                    qcItem = Boolean.TRUE;
                }
            }
            if (!qcItem) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
            }
            if (!passTemplateMap.containsValue(value)) {
                qcResult = Boolean.FALSE;
                break;
            }
        }

        //判断采购单下其他部品状态
        List<OpePurchasB> opePurchasBList = opePurchasBService.list(new LambdaQueryWrapper<OpePurchasB>().eq(OpePurchasB::getPurchasId, opePurchasB.getPurchasId())
                .ne(OpePurchasB::getId, opePurchasB.getId())
                .ne(OpePurchasB::getLaveWaitQcQty, 0)
        );
        //根据质检方式减库存
        if (opeParts.getIdClass()) {
            opePurchasB.setLaveWaitQcQty(opePurchasB.getLaveWaitQcQty() - enter.getQty());
        } else {
            opePurchasB.setLaveWaitQcQty(opePurchasB.getLaveWaitQcQty() - 1);
        }

        //判断是否要更新采购单状态
        Boolean updatePuchasStatus = Boolean.TRUE;
        opePurchasBList.add(opePurchasB);
        for (OpePurchasB item : opePurchasBList) {
            if (item.getLaveWaitQcQty() != 0) {
                updatePuchasStatus = Boolean.FALSE;
                break;
            }
        }
        //更新采购单状态以及采购单记录
        if (updatePuchasStatus && qcResult) {
            OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getId());
            opePurchas.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
            opePurchas.setLaveWaitQcTotal(0);
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);

            //更新订单记录
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opePurchas.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
            saveNodeEnter.setEvent(PurchasingStatusEnums.QC_COMPLETED.getValue());
            saveNodeEnter.setMemo(null);
            this.saveNode(saveNodeEnter);
        }

        //保存质检结果
        OpePurchasBQc purchasBQc = buildOpePurchasBQc(enter, opeParts, opePurchasB, qcResult);

        //保存质检条目
        OpePurchasBQcItem purchasBQcItem = BuildOpePurchasBQcItem(enter, opeParts, purchasBQc);

        //保存质检项记录
        templateMap.forEach((key, value) -> {
            opePurchasQcTraceList.add(buildOpePurchasQcTrace(enter, opePurchasB.getId(), purchasBQc.getId(), purchasBQcItem.getId(), key, value));
        });

        opePurchasBQcService.save(purchasBQc);

        opePurchasBQcItemService.save(purchasBQcItem);

        opePurchasQcTraceService.saveBatch(opePurchasQcTraceList);
        return SaveMaterialQcResult.builder()
                .partCnName(opeParts.getCnName())
                .laveWaitQcQty(opePurchasB.getLaveWaitQcQty())
                .batchN(purchasBQc.getBatchNo())
                .partN(opeParts.getPartsNumber())
                .qcResult(qcResult == true ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                .build();
    }

    private OpePurchasBQc buildOpePurchasBQc(SaveMaterialQcEnter enter, OpeParts opeParts, OpePurchasB opePurchasB, Boolean qcResult) {
        IdEnter batchNoEnter = new IdEnter();
        batchNoEnter.setId(opePurchasB.getId());
        return OpePurchasBQc.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC))
                .dr(0)
                .tenantId(0L)
                .userId(0L)
                .purchasBId(opePurchasB.getId())
                .partsId(enter.getId())
                .qualityInspectorId(enter.getUserId())
                .batchNo(bussinessNumberService.materialQcBatchNo(batchNoEnter))
                .status(qcResult == true ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                .totalQualityInspected(opeParts.getIdClass() == true ? 1 : enter.getQty())
                .passCount(qcResult == true ? enter.getQty() : 0)
                .failCount(qcResult == false ? enter.getQty() : 0)
                .qualityInspectionTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpePurchasBQcItem BuildOpePurchasBQcItem(SaveMaterialQcEnter enter, OpeParts opeParts, OpePurchasBQc purchasBQc) {
        return OpePurchasBQcItem.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC_ITEM))
                .dr(0)
                .partId(opeParts.getId())
                .purchasBId(purchasBQc.getId())
                .purchasBQcId(purchasBQc.getId())
                .qcBatchTotal(opeParts.getIdClass() == true ? 1 : enter.getQty())
                .serialNum(opeParts.getIdClass() == true ? "REDE" + RandomUtil.BASE_CHAR_NUMBER : null)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 保存质检记录
     *
     * @param enter
     * @param opePurchasBId
     * @param purchasBQcId
     * @param purchasBQcItemId
     * @param key
     * @param value
     * @return
     */
    private OpePurchasQcTrace buildOpePurchasQcTrace(SaveMaterialQcEnter enter, Long opePurchasBId, Long purchasBQcId, Long purchasBQcItemId, PartTemplateEnter key,
                                                     Long value) {
        return OpePurchasQcTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_QC_TRACE))
                .dr(0)
                .partId(enter.getId())
                .purchasId(opePurchasBId)
                .purchasBQcId(purchasBQcId)
                .purchasBQcItemId(purchasBQcItemId)
                .partQcTemplateId(key.getId())
                .partQcTemplateBId(value)
                .partQcPicture(key.getPictureList())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 保存质检记录 入参校验
     *
     * @param enter
     * @param partQcResultList
     * @param templateMap
     */
    private OpePurchasB checkSaveMaterialEnter(SaveMaterialQcEnter enter, List<PartTemplateEnter> partQcResultList, Map<PartTemplateEnter, Long> templateMap,
                                               Map<OpePartQcTemplate, List<OpePartQcTemplateB>> passTemplateMap) {
        try {
            partQcResultList.addAll(JSON.parseArray(enter.getPartTemplateListJson(), PartTemplateEnter.class));
            if (CollectionUtils.isEmpty(partQcResultList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            partQcResultList.forEach(item -> {
                templateMap.put(item, item.getId());
            });
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //查询质检模板
        QueryWrapper<OpePartQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_PART_ID, opePurchasB.getPartId());
        List<OpePartQcTemplate> partQcTemplateList = opePartQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(partQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }
        if (templateMap.keySet().size() != partQcTemplateList.size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
        }

        //查询模板对应的pass结果集
        QueryWrapper<OpePartQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_DR, 0);
        opePartQcTemplateBQueryWrapper.in(OpePartQcTemplateB.COL_PART_QC_TEMPLATE_ID, partQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_PASS_FLAG, Boolean.TRUE);
        Collection<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }
        if (opePartQcTemplateBList.size() != templateMap.values().size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
        }

        //封装质检pass模板
        partQcTemplateList.forEach(item -> {
            List<OpePartQcTemplateB> partTemplateList = Lists.newArrayList();
            opePartQcTemplateBList.forEach(template -> {
                if (item.getId().equals(template.getPartQcTemplateId())) {
                    partTemplateList.add(template);
                    passTemplateMap.put(item, partTemplateList);
                }
            });
        });
        return opePurchasB;
    }

    /**
     * 保存采购单记录
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .purchasId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }
}
