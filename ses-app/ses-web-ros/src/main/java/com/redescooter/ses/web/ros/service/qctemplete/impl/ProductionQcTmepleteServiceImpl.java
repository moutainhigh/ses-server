package com.redescooter.ses.web.ros.service.qctemplete.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.QcSourceTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.qctemplete.ProductionQcTempleteServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempate;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempateB;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeProductionQualityTempateBService;
import com.redescooter.ses.web.ros.service.base.OpeProductionQualityTempateService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.qctemplete.ProductionQcTmepleteService;
import com.redescooter.ses.web.ros.vo.bom.QcItemTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultResult;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.SaveByCopyIdEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionQcTmepleteServiceiMPL
 * @description: ProductionQcTmepleteServiceiMPL
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 13:59
 */
@Service
public class ProductionQcTmepleteServiceImpl implements ProductionQcTmepleteService {

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionQualityTempateService opeProductionQualityTempateService;

    @Autowired
    private OpeProductionQualityTempateBService opeProductionQualityTempateBService;

    @Autowired
    private ProductionQcTempleteServiceMapper productionQcTmepleteServiceMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * 部件质检模板保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveQcTemplateEnter enter) {
        // 数据解析集合
        List<QcItemTemplateEnter> qcItemTemplateEnterList = null;
        Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap = Maps.newHashMap();
        try {
            qcItemTemplateEnterList = JSON.parseArray(enter.getQcItemTemplateEnter(), QcItemTemplateEnter.class);
            if (CollectionUtils.isEmpty(qcItemTemplateEnterList)) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            qcItemTemplateEnterList.forEach(item -> {
                List<QcResultEnter> qcResultEnterList = JSON.parseArray(item.getQcResultEnter(), QcResultEnter.class);
                qcResultEnterMap.put(item, qcResultEnterList);
            });
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (qcResultEnterMap.containsKey(null) || qcResultEnterMap.containsValue(null)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // 数据保存集合
        List<OpeProductionQualityTempate> saveOpeProductionQcTemplateList = new ArrayList<>();

        List<OpeProductionQualityTempateB> saveOpeProductQcTemplateBList = new ArrayList<>();

        // 入参校验
        for (Map.Entry<QcItemTemplateEnter, List<QcResultEnter>> entry : qcResultEnterMap.entrySet()) {
            QcItemTemplateEnter key = entry.getKey();
            List<QcResultEnter> value = entry.getValue();
            // 质检项校验
            if (StringUtils.isBlank(key.getQcItemName())) {
                throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getMessage());
            }

            int sequence = 0;
            for (QcResultEnter item : value) {
                if (StringUtils.isBlank(item.getResult())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getMessage());
                }
                if (item.getUploadPictureFalg() == null || item.getUploadPictureFalg().equals("")) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getMessage());
                }
                if (item.getResultSequence() == 0 || item.getResultSequence() == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getMessage());
                }

                // 结果集 排序校验
                if (sequence == 0) {
                    sequence = item.getResultSequence();
                } else {
                    sequence += 1;
                    if (!item.getResultSequence().equals(sequence)) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                            ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                }
            }
        }

        // 校验 产品 和 质检模板
        List<OpeProductionQualityTempate> opeProductionQualityTempateList = deleteOpeProductionQualityTempate(enter);

        // 构建 数据库对象
        buildProductTemplate(enter, saveOpeProductionQcTemplateList, saveOpeProductQcTemplateBList, qcResultEnterMap,
            opeProductionQualityTempateList);
        // 质检模板数据保存
        if (CollectionUtils.isNotEmpty(saveOpeProductionQcTemplateList)) {
            opeProductionQualityTempateService.saveBatch(saveOpeProductionQcTemplateList);
        }
        // 质检项结果集数据保存
        if (CollectionUtils.isNotEmpty(saveOpeProductQcTemplateBList)) {
            opeProductionQualityTempateBService.saveBatch(saveOpeProductQcTemplateBList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 质检模板详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcTemplateDetailResult> detail(QcTempleteDetailEnter enter) {
        // 产品校验
        checkOpeProductionProduction(enter.getId(), enter.getProductionProductType());

        List<QcTemplateDetailResult> result = productionQcTmepleteServiceMapper.detail(enter);

        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        List<QcResultResult> qcResultResultList = productionQcTmepleteServiceMapper
            .detailQcResultList(result.stream().map(QcTemplateDetailResult::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(qcResultResultList)) {
            return new ArrayList<>();
        }
        result.forEach(item -> {
            item.setQcResultResultList(qcResultResultList.stream()
                .filter(qcResult -> item.getId().equals(qcResult.getQualityTempateId())).collect(Collectors.toList()));
        });
        return result;
    }

    /**
     * 产品 质检模板复制 快速保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveByCopyId(SaveByCopyIdEnter enter) {
        OpeProductionQualityTempate opeProductionQualityTempate =
            opeProductionQualityTempateService.getOne(new LambdaQueryWrapper<OpeProductionQualityTempate>()
                .eq(OpeProductionQualityTempate::getProductionId, enter.getLastProductId())
                .eq(OpeProductionQualityTempate::getProductionType, enter.getLastProductionProductType()));
        if (opeProductionQualityTempate == null) {
            return null;
        }

        List<OpeProductionQualityTempateB> productionQualityTempateBList =
            opeProductionQualityTempateBService.list(new LambdaQueryWrapper<OpeProductionQualityTempateB>()
                .eq(OpeProductionQualityTempateB::getProductQualityTempateId, opeProductionQualityTempate.getId()));

        if (CollectionUtils.isEmpty(productionQualityTempateBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.QC_TEMPLATE_IS_NOT_EXIT.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_IS_NOT_EXIT.getMessage());
        }

        // 新产品 质检模板
        opeProductionQualityTempate.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE));
        opeProductionQualityTempate.setProductionId(enter.getId());
        opeProductionQualityTempate.setProductionType(enter.getProductionProductType());
        opeProductionQualityTempate.setCreatedBy(enter.getUserId());
        opeProductionQualityTempate.setCreatedTime(new Date());
        opeProductionQualityTempate.setUpdatedBy(enter.getUserId());
        opeProductionQualityTempate.setUpdatedTime(new Date());
        opeProductionQualityTempateService.save(opeProductionQualityTempate);

        productionQualityTempateBList.forEach(item -> {
            item.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE_B));
            item.setProductQualityTempateId(opeProductionQualityTempate.getId());
            item.setCreatedBy(enter.getUserId());
            item.setCreatedTime(new Date());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });
        opeProductionQualityTempateBService.saveBatch(productionQualityTempateBList);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 产品质检模板封装
     *
     * @param enter
     * @param saveProductionQualityTempateList
     * @param saveProductionQualityTempateBList
     * @param qcResultEnterMap
     * @param opeProductionQcTemplateList
     */
    private void buildProductTemplate(SaveQcTemplateEnter enter,
        List<OpeProductionQualityTempate> saveProductionQualityTempateList,
        List<OpeProductionQualityTempateB> saveProductionQualityTempateBList,
        Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap,
        List<OpeProductionQualityTempate> opeProductionQcTemplateList) {
        // 质检项 结果集数量校验
        for (QcItemTemplateEnter qcItemTemplateEnter : qcResultEnterMap.keySet()) {
            int passResult =
                (int)qcResultEnterMap.get(qcItemTemplateEnter).stream().filter(QcResultEnter::getPassFlag).count();
            if (passResult != 1) {
                throw new SesWebRosException(ExceptionCodeEnums.QC_PASS_RESULT_ONLY_ONE.getCode(),
                    ExceptionCodeEnums.QC_PASS_RESULT_ONLY_ONE.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(opeProductionQcTemplateList)) {
            qcResultEnterMap.forEach((key, value) -> {
                Long templateId = idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE);
                opeProductionQcTemplateList.forEach(template -> {
                    if (key.getId() == null || key.getId() == 0) {
                        saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId,
                            key.getQcItemName(), null, Integer.valueOf(QcSourceTypeEnums.MANUAL_ENTRY.getValue())));
                        value.forEach(item -> {
                            saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                        });
                    }
                    if (template.getId().equals(key.getId())) {
                        saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId,
                            key.getQcItemName(), template.getImportExcelBatchNo(), template.getSourceType()));
                        value.forEach(item -> {
                            saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                        });
                    }
                });
            });

        } else {
            qcResultEnterMap.forEach((key, value) -> {
                Long templateId = idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE);
                saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId, key.getQcItemName(),
                    null, Integer.valueOf(QcSourceTypeEnums.MANUAL_ENTRY.getValue()))

                );
                value.forEach(item -> {
                    saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                });
            });
        }
    }

    /**
     * 质检模板赋值
     * 
     * @param enter
     * @param templateId
     * @param qcItemName
     * @param importExcelBatchNo
     * @param sourceType
     * @return
     */
    private OpeProductionQualityTempate buildProductionQcTemplate(SaveQcTemplateEnter enter, Long templateId,
        String qcItemName, String importExcelBatchNo, Integer sourceType) {
        OpeProductionQualityTempate opeProductionQualityTempate = new OpeProductionQualityTempate();
        opeProductionQualityTempate.setId(templateId);
        opeProductionQualityTempate.setDr(0);
        opeProductionQualityTempate.setProductionId(enter.getId());
        opeProductionQualityTempate.setProductionType(enter.getProductionProductType());
        opeProductionQualityTempate.setImportExcelBatchNo(importExcelBatchNo);
        opeProductionQualityTempate.setSourceType(sourceType);
        opeProductionQualityTempate.setQcItemName(qcItemName);
        opeProductionQualityTempate.setRevision(0);
        opeProductionQualityTempate.setCreatedBy(enter.getUserId());
        opeProductionQualityTempate.setCreatedTime(new Date());
        opeProductionQualityTempate.setUpdatedBy(enter.getUserId());
        opeProductionQualityTempate.setUpdatedTime(new Date());
        return opeProductionQualityTempate;
    }

    /**
     * 质检结果为空
     * 
     * @param enter
     * @param templateId
     * @param item
     * @return
     */
    private OpeProductionQualityTempateB buildProductQcTemplateB(SaveQcTemplateEnter enter, Long templateId,
        QcResultEnter item) {
        OpeProductionQualityTempateB opeProductionQualityTempateB = new OpeProductionQualityTempateB();
        opeProductionQualityTempateB.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE_B));
        opeProductionQualityTempateB.setDr(0);
        opeProductionQualityTempateB.setProductQualityTempateId(templateId);
        opeProductionQualityTempateB.setQcResult(item.getResult());
        opeProductionQualityTempateB.setPassFlag(item.getPassFlag());
        opeProductionQualityTempateB.setUploadFlag(item.getUploadPictureFalg());
        opeProductionQualityTempateB.setResultsSequence(item.getResultSequence());
        opeProductionQualityTempateB.setRevision(0);
        opeProductionQualityTempateB.setCreatedBy(enter.getUserId());
        opeProductionQualityTempateB.setCreatedTime(new Date());
        opeProductionQualityTempateB.setUpdatedBy(enter.getUserId());
        opeProductionQualityTempateB.setUpdatedTime(new Date());
        return opeProductionQualityTempateB;
    }

    /**
     * 校验 产品 和 质检模板
     * 
     * @param enter
     * @return
     */
    private List<OpeProductionQualityTempate> deleteOpeProductionQualityTempate(SaveQcTemplateEnter enter) {
        // 产品校验
        checkOpeProductionProduction(enter.getId(), enter.getProductionProductType());

        // 查询是否存在质检模板
        List<OpeProductionQualityTempate> opeProductionQualityTempateList =
            opeProductionQualityTempateService.list(new LambdaQueryWrapper<OpeProductionQualityTempate>()
                .eq(OpeProductionQualityTempate::getProductionType, enter.getProductionProductType())
                .eq(OpeProductionQualityTempate::getProductionId, enter.getId()));
        if (CollectionUtils.isNotEmpty(opeProductionQualityTempateList)) {
            // 质检项删除
            opeProductionQualityTempateService.removeByIds(opeProductionQualityTempateList.stream()
                .map(OpeProductionQualityTempate::getId).collect(Collectors.toList()));

            // 质检结果删除
            List<OpeProductionQualityTempateB> opeProductionQualityTempateBList =
                opeProductionQualityTempateBService.list(new LambdaQueryWrapper<OpeProductionQualityTempateB>()
                    .in(OpeProductionQualityTempateB::getProductQualityTempateId, opeProductionQualityTempateList
                        .stream().map(OpeProductionQualityTempate::getId).collect(Collectors.toList())));
            if (CollectionUtils.isNotEmpty(opeProductionQualityTempateBList)) {
                opeProductionQualityTempateBService.removeByIds(opeProductionQualityTempateBList.stream()
                    .map(OpeProductionQualityTempateB::getId).collect(Collectors.toList()));
            }
        }
        return opeProductionQualityTempateList;
    }

    /**
     * 产品校验
     * 
     * @param id
     * @param productionProductType
     */
    private void checkOpeProductionProduction(Long id, Integer productionProductType) {
        // 产品校验验证
        // 部件校验
        if (StringUtils.equalsAny(String.valueOf(productionProductType), BomCommonTypeEnums.PARTS.getValue(),
            BomCommonTypeEnums.ACCESSORY.getValue(), BomCommonTypeEnums.BATTERY.getValue())) {
            OpeProductionParts opeProductionPart = opeProductionPartsService.getById(id);
            if (opeProductionPart == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
        }
        // 车辆校验
        if (productionProductType.equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBom productionScooterBom = opeProductionScooterBomService.getById(id);
            if (productionScooterBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
        }
        // 组合校验
        if (productionProductType.equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBom productionCombinBom = opeProductionCombinBomService.getById(id);
            if (productionCombinBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
        }
    }
}
