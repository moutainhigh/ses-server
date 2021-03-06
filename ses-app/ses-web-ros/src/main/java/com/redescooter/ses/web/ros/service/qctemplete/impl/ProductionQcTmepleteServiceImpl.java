package com.redescooter.ses.web.ros.service.qctemplete.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.QcSourceTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.bom.QcItemTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultResult;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.SaveByCopyIdEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:ProductionQcTmepleteServiceiMPL
 * @description: ProductionQcTmepleteServiceiMPL
 * @author: Alex @Version???1.3
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

    @DubboReference
    private IdAppService idAppService;

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveQcTemplateEnter enter) {
        // ??????????????????
        List<QcItemTemplateEnter> list = null;
        Map<QcItemTemplateEnter, List<QcResultEnter>> map = Maps.newHashMap();
        try {
            list = JSON.parseArray(enter.getQcItemTemplateEnter(), QcItemTemplateEnter.class);
            if (CollectionUtils.isEmpty(list)) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            list.forEach(item -> {
                List<QcResultEnter> qcResultEnterList = JSON.parseArray(item.getQcResultEnter(), QcResultEnter.class);
                map.put(item, qcResultEnterList);
            });
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (map.containsKey(null) || map.containsValue(null)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // ??????????????????
        List<OpeProductionQualityTempate> saveList = new ArrayList<>();
        List<OpeProductionQualityTempateB> saveBList = new ArrayList<>();

        // ????????????
        for (Map.Entry<QcItemTemplateEnter, List<QcResultEnter>> entry : map.entrySet()) {
            QcItemTemplateEnter key = entry.getKey();
            List<QcResultEnter> value = entry.getValue();
            // ???????????????
            if (StringUtils.isBlank(key.getQcItemName())) {
                throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getMessage());
            }

            int sequence = 0;
            for (QcResultEnter item : value) {
                if (StringUtils.isBlank(item.getResult())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getMessage());
                }
                if (StringManaConstant.entityIsNull(item.getUploadPictureFalg()) || "".equals(item.getUploadPictureFalg())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getMessage());
                }
                if (0 == item.getResultSequence() || StringManaConstant.entityIsNull(item.getResultSequence())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getMessage());
                }

                // ????????? ????????????
                if (NumberUtil.eqZero(sequence)) {
                    sequence = item.getResultSequence();
                } else {
                    sequence += 1;
                    if (!item.getResultSequence().equals(sequence)) {
                        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                    }
                }
            }
        }

        // ?????? ?????? ??? ????????????
        List<OpeProductionQualityTempate> itemList = deleteOpeProductionQualityTempate(enter);

        // ?????? ???????????????
        buildProductTemplate(enter, saveList, saveBList, map, itemList);
        // ????????????????????????
        if (CollectionUtils.isNotEmpty(saveList)) {
            opeProductionQualityTempateService.saveBatch(saveList);
        }
        // ??????????????????????????????
        if (CollectionUtils.isNotEmpty(saveBList)) {
            opeProductionQualityTempateBService.saveBatch(saveBList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcTemplateDetailResult> detail(QcTempleteDetailEnter enter) {
        // ????????????
        checkOpeProductionProduction(enter.getId(), enter.getProductionProductType());

        List<QcTemplateDetailResult> result = productionQcTmepleteServiceMapper.detail(enter);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.EMPTY_LIST;
        }
        List<QcResultResult> qcResultResultList = productionQcTmepleteServiceMapper.detailQcResultList(result.stream().map(QcTemplateDetailResult::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(qcResultResultList)) {
            return Collections.EMPTY_LIST;
        }

        result.forEach(item -> {
            item.setQcResultResultList(qcResultResultList.stream().filter(qcResult -> item.getId().equals(qcResult.getQualityTempateId())).collect(Collectors.toList()));
        });
        result.stream().sorted(Comparator.comparing(QcTemplateDetailResult::getCreateTime)).collect(Collectors.toList());
        return result;
    }

    /**
     * ?????? ?????????????????? ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveByCopyId(SaveByCopyIdEnter enter) {
        List<OpeProductionQualityTempate> opeProductionQualityTempateList =
                opeProductionQualityTempateService.list(new LambdaQueryWrapper<OpeProductionQualityTempate>()
                        .eq(OpeProductionQualityTempate::getProductionId, enter.getLastProductId())
                        .eq(OpeProductionQualityTempate::getProductionType, enter.getLastProductionProductType()));
        if (CollectionUtils.isEmpty(opeProductionQualityTempateList)) {
            return null;
        }
        List<OpeProductionQualityTempateB> productionQualityTempateBList =
                opeProductionQualityTempateBService.list(new LambdaQueryWrapper<OpeProductionQualityTempateB>()
                        .in(OpeProductionQualityTempateB::getProductQualityTempateId,
                                opeProductionQualityTempateList.stream().map(OpeProductionQualityTempate::getId).collect(Collectors.toList())));

        if (CollectionUtils.isEmpty(productionQualityTempateBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.QC_TEMPLATE_IS_NOT_EXIT.getCode(),
                    ExceptionCodeEnums.QC_TEMPLATE_IS_NOT_EXIT.getMessage());
        }


        for (OpeProductionQualityTempate opeProductionQualityTempate : opeProductionQualityTempateList) {
            opeProductionQualityTempate.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE));
            opeProductionQualityTempate.setProductionId(enter.getId());
            opeProductionQualityTempate.setProductionType(enter.getProductionProductType());
            opeProductionQualityTempate.setCreatedBy(enter.getUserId());
            opeProductionQualityTempate.setCreatedTime(new Date());
            opeProductionQualityTempate.setUpdatedBy(enter.getUserId());
            opeProductionQualityTempate.setUpdatedTime(new Date());
        }
        opeProductionQualityTempateService.saveBatch(opeProductionQualityTempateList);

        for (OpeProductionQualityTempate templete : opeProductionQualityTempateList) {
            productionQualityTempateBList.forEach(item -> {
                item.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE_B));
                item.setProductQualityTempateId(templete.getId());
                item.setCreatedBy(enter.getUserId());
                item.setCreatedTime(new Date());
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());
            });
        }

        opeProductionQualityTempateBService.saveBatch(productionQualityTempateBList);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????????????????
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
        // ????????? ?????????????????????
        for (QcItemTemplateEnter qcItemTemplateEnter : qcResultEnterMap.keySet()) {
            int passResult = (int) qcResultEnterMap.get(qcItemTemplateEnter).stream().filter(QcResultEnter::getPassFlag).count();
            if (1 != passResult) {
                throw new SesWebRosException(ExceptionCodeEnums.QC_PASS_RESULT_ONLY_ONE.getCode(), ExceptionCodeEnums.QC_PASS_RESULT_ONLY_ONE.getMessage());
            }
        }

        qcResultEnterMap.forEach((key, value) -> {
            Long templateId = idAppService.getId(SequenceName.OPE_PRODUCTION_QUALITY_TEMPLETE);
            if (CollectionUtils.isNotEmpty(opeProductionQcTemplateList)) {
                OpeProductionQualityTempate opeProductionQualityTempate = opeProductionQcTemplateList.stream().filter(item -> item.getId().equals(key.getId())).findFirst().orElse(null);
                if (StringManaConstant.entityIsNotNull(opeProductionQualityTempate)) {
                    saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId,
                            key.getQcItemName(), opeProductionQualityTempate.getImportExcelBatchNo(), opeProductionQualityTempate.getSourceType()));
                    value.forEach(item -> {
                        saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                    });
                } else {
                    saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId, key.getQcItemName(),
                            null, Integer.valueOf(QcSourceTypeEnums.MANUAL_ENTRY.getValue()))
                    );
                    value.forEach(item -> {
                        saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                    });
                }
            } else {
                saveProductionQualityTempateList.add(buildProductionQcTemplate(enter, templateId, key.getQcItemName(),
                        null, Integer.valueOf(QcSourceTypeEnums.MANUAL_ENTRY.getValue()))
                );
                value.forEach(item -> {
                    saveProductionQualityTempateBList.add(buildProductQcTemplateB(enter, templateId, item));
                });
            }
        });

        //????????????????????????
        switch (enter.getProductionProductType()) {
            case 4:
                OpeProductionScooterBom productionScooterBom = opeProductionScooterBomService.getById(enter.getId());
//                if (Objects.isNull(productionScooterBom.getQcFlag()) || !productionScooterBom.getQcFlag()) {
                productionScooterBom.setQcFlag(Boolean.TRUE);
                opeProductionScooterBomService.updateById(productionScooterBom);
//                }
                break;
            case 5:
                OpeProductionCombinBom productionCombinBom = opeProductionCombinBomService.getById(enter.getId());
                //????????????????????????
//                if (Objects.isNull(productionCombinBom.getQcFlag() || !productionCombinBom.getQcFlag())) {
                productionCombinBom.setQcFlag(Boolean.TRUE);
                opeProductionCombinBomService.updateById(productionCombinBom);
//                }
                break;
            default:
                OpeProductionParts opeProductionPart = opeProductionPartsService.getById(enter.getId());
                //????????????????????????
//                if (Objects.isNull(opeProductionPart.getQcFlag()) || !opeProductionPart.getQcFlag()) {
                opeProductionPart.setQcFlag(Boolean.TRUE);
                opeProductionPartsService.updateById(opeProductionPart);
//                }
                break;
        }

    }

    /**
     * ??????????????????
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
     * ??????????????????
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
     * ?????? ?????? ??? ????????????
     *
     * @param enter
     * @return
     */
    private List<OpeProductionQualityTempate> deleteOpeProductionQualityTempate(SaveQcTemplateEnter enter) {
        // ????????????
        checkOpeProductionProduction(enter.getId(), enter.getProductionProductType());

        // ??????????????????????????????
        List<OpeProductionQualityTempate> opeProductionQualityTempateList =
                opeProductionQualityTempateService.list(new LambdaQueryWrapper<OpeProductionQualityTempate>()
                        .eq(OpeProductionQualityTempate::getProductionType, enter.getProductionProductType())
                        .eq(OpeProductionQualityTempate::getProductionId, enter.getId()));
        if (CollectionUtils.isNotEmpty(opeProductionQualityTempateList)) {
            // ???????????????
            opeProductionQualityTempateService.removeByIds(opeProductionQualityTempateList.stream()
                    .map(OpeProductionQualityTempate::getId).collect(Collectors.toList()));

            // ??????????????????
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
     * ????????????
     *
     * @param id
     * @param productionProductType
     */
    private void checkOpeProductionProduction(Long id, Integer productionProductType) {
        // ??????????????????
        // ????????????
        if (StringUtils.equalsAny(String.valueOf(productionProductType), BomCommonTypeEnums.PARTS.getValue(), BomCommonTypeEnums.ACCESSORY.getValue(), BomCommonTypeEnums.BATTERY.getValue())) {
            OpeProductionParts opeProductionPart = opeProductionPartsService.getById(id);
            if (StringManaConstant.entityIsNull(opeProductionPart)) {
                throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
        }
        // ????????????
        if (productionProductType.equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBom productionScooterBom = opeProductionScooterBomService.getById(id);
            if (StringManaConstant.entityIsNull(productionScooterBom)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
        }
        // ????????????
        if (productionProductType.equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBom productionCombinBom = opeProductionCombinBomService.getById(id);
            if (StringManaConstant.entityIsNull(productionCombinBom)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
        }
    }
}
