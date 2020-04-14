package com.redescooter.ses.mobile.rps.service.material.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.rps.QcTypeEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.dao.material.MaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.materialqc.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        return PageResult.create(enter, 1, materialServiceMapper.list(enter));
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
        return PageResult.create(enter, 1, materialServiceMapper.detail(enter));
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
                .id(opeParts.getId())
                .partCnName(opeParts.getCnName())
                .partN(opeParts.getPartsNumber())
                .purchasBId(opePurchasB.getId())
                .partTemplateList(partTemplateList)
                .build();
    }

    /**
     * 保存来料质检结果
     *
     * @param enter
     * @return
     */
    @Override
    public SaveMaterialQcResult saveMaterialQc(SaveMaterialQcEnter enter) {
        List<PartTemplateEnter> partQcResultList = Lists.newArrayList();
        //设置 模板、模板结果 之间关系
        Map<Long, Long> templateMap = Maps.newHashMap();
        //解析质检模板数据
        try {
            partQcResultList.addAll(JSON.parseArray(enter.getPartTemplateListJson(), PartTemplateEnter.class));
            if (CollectionUtils.isEmpty(partQcResultList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            partQcResultList.forEach(item -> {
                templateMap.put(item.getId(), JSON.parseObject(item.getPartQcResultIdListJson(), Long.class));
            });
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        //对入参数据做校验
        OpeParts opeParts = opePartsService.getById(enter.getId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getPurchasBId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        if (!opePurchasB.getPartId().equals(opeParts.getId())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        //根据质检类型校验质检数量
        if (opeParts.getIdClass().equals(Boolean.TRUE)) {
            if (enter.getQty() == null || enter.getQty() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getCode(), ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getMessage());
            }
        }
        //查询质检模板
        Collection<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.listByIds(templateMap.keySet().stream().collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(opePartQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getMessage());
        }
        if (templateMap.keySet().size() != opePartQcTemplateList.size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getMessage());
        }
        //查询模板对应的结果集
        Collection<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.listByIds(templateMap.values());
        if (CollectionUtils.isEmpty(opePartQcTemplateBList)){
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getMessage());
        }
        if (opePartQcTemplateBList.size()!=templateMap.values().size()){
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_TEMPLATE_IS_NOT_EXIST.getMessage());
        }


        return null;
    }
}
