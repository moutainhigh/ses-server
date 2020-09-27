package com.redescooter.ses.web.ros.service.restproduction.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.ClassTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionScooterGroupEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.verifyhandler.RosExcelParse;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spark.utils.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RosProductionProductServiceImpl implements RosServProductionProductService {

    @Autowired
    private RosProductionProductServiceMapper rosProductionProductServiceMapper;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeProductionCombinBomDraftService opeProductionCombinBomDraftService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionScooterBomDraftService opeProductionScooterBomDraftService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpePartsSecService opePartsSecService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private IdAppService idAppService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<Integer, Integer> countByType(IdEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        // 整车
        if (StringUtils.equals(BomCommonTypeEnums.SCOOTER.getValue(), String.valueOf(enter.getId()))) {
            result.put(ClassTypeEnums.TYPE_ONE.getValue(), opeProductionScooterBomDraftService.count());
            result.put(ClassTypeEnums.TYPE_TWO.getValue(), opeProductionScooterBomService.count());
        }

        // 组合
        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(), String.valueOf(enter.getId()))) {
            result.put(ClassTypeEnums.TYPE_ONE.getValue(), opeProductionCombinBomDraftService.count());
            result.put(ClassTypeEnums.TYPE_TWO.getValue(), opeProductionCombinBomService.count());
        }
        return result;
    }

    /**
     * 分组查询
     *
     * @param generalEnter
     * @return
     */
    @Override
    public List<BaseNameResult> groupList(GeneralEnter generalEnter) {
        List<BaseNameResult> result = new ArrayList<>();
        for (ProductionScooterGroupEnums item : ProductionScooterGroupEnums.values()) {
            result.add(new BaseNameResult(Long.valueOf(item.getValue()), item.getMessage()));
        }
        return result;
    }

    /**
     * 颜色查询
     *
     * @param enter
     * @return
     */
    @Override
    public List<BaseNameResult> colorList(GeneralEnter enter) {
        List<BaseNameResult> result = new ArrayList<>();
        for (ProductColorEnums item : ProductColorEnums.values()) {
            result.add(BaseNameResult.builder().id(Long.valueOf(item.getValue())).name(item.getCode()).build());
        }
        return result;
    }

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<RosProductionScooterListResult> scooterList(RosProductionScooterListEnter enter) {

        List<RosProductionScooterListResult> rosProductionScooterListResults = new ArrayList<>();
        int count = 0;
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_ONE.getValue())) {
            count = rosProductionProductServiceMapper.scooterDraftListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            rosProductionScooterListResults = rosProductionProductServiceMapper.scooterDraftList(enter);
        }
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_TWO.getValue())) {
            count = rosProductionProductServiceMapper.scooterBomListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            rosProductionScooterListResults = rosProductionProductServiceMapper.scooterBomList(enter);
        }

        for (RosProductionScooterListResult item : rosProductionScooterListResults) {
            if (item.getGroupId() != null && item.getGroupId() != 0) {
                ProductionScooterGroupEnums groupName =
                    ProductionScooterGroupEnums.getEnumByValue(item.getGroupId().intValue());
                item.setGroupName(groupName == null ? null : groupName.getCode());
            }
            if (item.getColorId() != null && item.getColorId() != 0) {
                ProductColorEnums colorEnum =
                    ProductColorEnums.getProductColorEnumsByValue(String.valueOf(item.getColorId()));
                item.setColorName(colorEnum == null ? null : colorEnum.getCode());
            }
        }
        return PageResult.create(enter, count, rosProductionScooterListResults);
    }

    /**
     * 组合列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<RosProductionCombinationListResult> combinationList(RosProductionCombinationListEnter enter) {
        // 草稿
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_ONE.getValue())) {

            int count = rosProductionProductServiceMapper.combinationDraftListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, rosProductionProductServiceMapper.combinationDraftList(enter));

        }
        // 产品
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_TWO.getValue())) {
            int count = rosProductionProductServiceMapper.combinationListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, rosProductionProductServiceMapper.combinationList(enter));
        }
        return null;
    }

    /**
     * 校验生效时间是否合理
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult checkEffectiveDate(RosProductionTimeParmEnter enter) {
        // 整车时间校验
        if (StringUtils.equals(BomCommonTypeEnums.SCOOTER.getValue(), String.valueOf(enter.getProductionType()))) {
            OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getById(enter.getId());
            if (opeProductionScooterBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }
            List<OpeProductionScooterBom> opeProductionScooterBomList =
                    opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                    .eq(OpeProductionScooterBom::getBomNo, opeProductionScooterBom.getBomNo())
                    .eq(OpeProductionScooterBom::getVersionIdentificat, opeProductionScooterBom.getVersionIdentificat())
                    .in(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue(),
                        ProductionBomStatusEnums.ACTIVE.getValue()));

            if (CollectionUtils.isNotEmpty(opeProductionScooterBomList)) {
                if (opeProductionScooterBomList.size() == 1) {
                    return new BooleanResult(Boolean.TRUE);
                }
                // 移除当前对象
                opeProductionScooterBomList.remove(opeProductionScooterBom);
                if (opeProductionScooterBomList.size() > 1) {
                    if (DateUtil.diffDay(enter.getDateTime(), opeProductionScooterBom.getEffectiveDate()) == 0) {
                        return new BooleanResult(Boolean.FALSE);
                    }
                    for (OpeProductionScooterBom item : opeProductionScooterBomList) {
                        if (DateUtil.diffDay(enter.getDateTime(), item.getEffectiveDate()) == 0) {
                            return new BooleanResult(Boolean.FALSE);
                        }
                    }

                }
            }
        }

        // 组合产品校验
        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(), String.valueOf(enter.getProductionType()))) {
            OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomService.getById(enter.getId());
            if (opeProductionCombinBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            List<OpeProductionCombinBom> opeProductionCombinBomList =
                    opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                    .eq(OpeProductionCombinBom::getBomNo, opeProductionCombinBom.getBomNo())
                    .eq(OpeProductionCombinBom::getVersionIdentificat, opeProductionCombinBom.getVersionIdentificat())
                    .in(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.TO_BE_ACTIVE.getValue(),
                        ProductionBomStatusEnums.ACTIVE.getValue()));

            if (CollectionUtils.isNotEmpty(opeProductionCombinBomList)) {
                if (opeProductionCombinBomList.size() == 1) {
                    return new BooleanResult(Boolean.TRUE);
                }
                // 移除当前对象
                opeProductionCombinBomList.remove(opeProductionCombinBom);
                if (opeProductionCombinBomList.size() > 1) {
                    if (DateUtil.diffDay(enter.getDateTime(), opeProductionCombinBom.getEffectiveDate()) == 0) {
                        return new BooleanResult(Boolean.FALSE);
                    }
                    for (OpeProductionCombinBom item : opeProductionCombinBomList) {
                        if (DateUtil.diffDay(enter.getDateTime(), item.getEffectiveDate()) == 0) {
                            return new BooleanResult(Boolean.FALSE);
                        }
                    }
                }
            }
        }
        return new BooleanResult(Boolean.TRUE);
    }

    @Transactional
    @Override
    public ImportProductionProductResult importProductionProduct(ImportPartsEnter enter) {
        ImportProductionProductResult importProductionProductResult = new ImportProductionProductResult();
        List<RosProductionProductPartListResult> successProductPartListResult = new ArrayList<>();
        List<RosProductionProductPartListResult> failProductPartListResult = new ArrayList<>();
        // 逻辑需要调整
        ExcelImportResult<RosParseExcelData> excelImportResult =
            importExcelService.setiExcelVerifyHandler(new RosExcelParse()).importOssExcel(enter.getUrl(),
                RosParseExcelData.class, new ImportParams());
        if (excelImportResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(),
                ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        // 拿到需要导入的数据
        List<RosParseExcelData> successList = excelImportResult.getList();
        if (CollectionUtils.isEmpty(successList)) {
            // 如果没有成功的数据 直接抛异常
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(),
                ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        List<RosParseExcelData> failList = excelImportResult.getFailList();
        if (CollectionUtils.isNotEmpty(failList)) {
            failList.forEach(item -> {
                failProductPartListResult.add(RosProductionProductPartListResult.builder().partsNum(item.getPartsNo())
                    .enName(item.getEnglishName()).cnName(item.getChineseName()).build());
            });
        }
        if (CollectionUtils.isNotEmpty(successList)) {
            successProductPartListResult = rosProductionProductServiceMapper.rosImportProductionProductPartsList(
                successList.stream().map(RosParseExcelData::getPartsNo).collect(Collectors.toList()));
            if (CollectionUtils.isEmpty(successProductPartListResult)) {
                successList.forEach(item -> {
                    failProductPartListResult
                        .add(RosProductionProductPartListResult.builder().partsNum(item.getPartsNo())
                            .enName(item.getEnglishName()).cnName(item.getChineseName()).build());
                });
            }
        }
        importProductionProductResult.setFailProductPartListResult(failProductPartListResult);
        importProductionProductResult.setSuccessProductPartListResult(successProductPartListResult);
        return importProductionProductResult;
    }

    /**
     * 区域列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<RosProductionSecResult> secList(GeneralEnter enter) {
        List<RosProductionSecResult> resultList = new ArrayList<>();
        List<OpePartsSec> opePartsSecList = opePartsSecService.list();
        if (CollectionUtils.isNotEmpty(opePartsSecList)) {
            opePartsSecList.forEach(item -> {
                resultList
                    .add(new RosProductionSecResult(item.getId(), item.getName(), item.getCode(), item.getNote()));
            });
        }
        return resultList;
    }

    /**
     * 部件查询列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<RosProductionProductPartListResult>
        productionProductPartList(RosProductionProductPartListEnter enter) {
        int count = rosProductionProductServiceMapper.productionProductPartListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, rosProductionProductServiceMapper.productionProductPartList(enter));
    }

    /**
     * 产品保存的保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult rosSaveProductionProduct(RosSaveProductionProductEnter enter) {
        List<ProductionProductEnter> partList = null;
        try {
            partList = JSON.parseArray(enter.getPartList(), ProductionProductEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(partList)) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getCode(),
                ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getMessage());
        }

        // 校验部件是否存在
        List<OpeProductionParts> opeProductionPartsList =
            opeProductionPartsService.list(new LambdaQueryWrapper<OpeProductionParts>()
                .eq(OpeProductionParts::getDisable, Boolean.FALSE).in(OpeProductionParts::getId,
                    partList.stream().map(ProductionProductEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeProductionPartsList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getMessage());
        }

        Long productionProductId = null;
        Integer productionProducType = null;

        // 部件总数
        int partQty = partList.stream().mapToInt(ProductionProductEnter::getQty).sum();

        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBomDraft opeProductionScooterBomDraft = null;
            if (enter.getId() == null || enter.getId() == 0) {
                // 保存
                opeProductionScooterBomDraft = buildOpeProductionScooterDraft(enter, partQty);
                opeProductionScooterBomDraft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM_DRAFT));
                opeProductionScooterBomDraft.setCreatedBy(enter.getUserId());
                opeProductionScooterBomDraft.setCreatedTime(new Date());
            } else {
                // 编辑
                OpeProductionScooterBomDraft queryProductionScooterBomDraft =
                    opeProductionScooterBomDraftService.getById(enter.getId());
                if (queryProductionScooterBomDraft == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
                }
                opeProductionScooterBomDraft = buildOpeProductionScooterDraft(enter, partQty);
                opeProductionScooterBomDraft.setCreatedBy(queryProductionScooterBomDraft.getCreatedBy());
                opeProductionScooterBomDraft.setCreatedTime(queryProductionScooterBomDraft.getCreatedTime());
                opeProductionScooterBomDraft.setId(enter.getId());
            }
            productionProductId = opeProductionScooterBomDraft.getId();
            productionProducType = ProductionPartsRelationTypeEnums.SCOOTER_DRAFT.getValue();
            opeProductionScooterBomDraftService.saveOrUpdate(opeProductionScooterBomDraft);
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft productionCombinBomDraft = null;
            if (enter.getId() == null || enter.getId() == 0) {
                // 保存
                productionCombinBomDraft = buildProductionCombinBom(enter, partQty);
                productionCombinBomDraft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_COMBIN_BOM_DRAFT));
                productionCombinBomDraft.setCreatedBy(enter.getUserId());
                productionCombinBomDraft.setCreatedTime(new Date());
            } else {
                // 编辑
                OpeProductionCombinBomDraft queryProductionCombinBomDraft =
                    opeProductionCombinBomDraftService.getById(enter.getId());
                if (queryProductionCombinBomDraft == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
                }
                productionCombinBomDraft = buildProductionCombinBom(enter, partQty);
                productionCombinBomDraft.setCreatedBy(queryProductionCombinBomDraft.getCreatedBy());
                productionCombinBomDraft.setCreatedTime(queryProductionCombinBomDraft.getCreatedTime());
                productionCombinBomDraft.setId(enter.getId());
            }
            productionProductId = productionCombinBomDraft.getId();
            productionProducType = ProductionPartsRelationTypeEnums.COMBINATION_DRAFT.getValue();
            opeProductionCombinBomDraftService.saveOrUpdate(productionCombinBomDraft);
        }

        // 删除部件建立的绑定关系
        opeProductionPartsRelationService.remove(new LambdaQueryWrapper<OpeProductionPartsRelation>()
            .eq(OpeProductionPartsRelation::getProductionId, productionProductId)
            .eq(OpeProductionPartsRelation::getProductionType, productionProducType));

        List<OpeProductionPartsRelation> opeProductionPartsRelationList = new ArrayList<>();
        // 保存部件重新建立绑定关系
        for (OpeProductionParts item : opeProductionPartsList) {
            opeProductionPartsRelationList
                .add(buildProductionRelation(enter, partList, productionProductId, productionProducType, item));
        }
        if (CollectionUtils.isNotEmpty(opeProductionPartsRelationList)) {
            opeProductionPartsRelationService.saveBatch(opeProductionPartsRelationList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    private OpeProductionPartsRelation buildProductionRelation(RosSaveProductionProductEnter enter,
        List<ProductionProductEnter> partList, Long productionProductId, Integer productionProducType,
        OpeProductionParts item) {
        return OpeProductionPartsRelation.builder().id(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_RELATION))
            .dr(0).productionId(productionProductId).productionType(productionProducType).partsId(item.getId())
            .partsNo(item.getPartsNo()).partsSec(item.getPartsSec()).procurementCycle(item.getProcurementCycle())
            .partsQty(
                partList.stream().filter(part -> item.getId().equals(part.getId())).findFirst().orElse(null).getQty())
            .cnName(item.getCnName()).frName(item.getFrName()).enName(item.getEnName()).createdBy(enter.getUserId())
            .createdTime(new Date()).updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public RosProductionProductDetailResult detail(RosProuductionTypeEnter enter) {
        RosProductionProductDetailResult result = null;
        List<RosProductionProdductVersionResult> versionList = null;
        Long productionProductId = null;
        Integer productionPartsRelationType = null;

        if (StringUtils.equals(BomCommonTypeEnums.SCOOTER.getValue(),
            String.valueOf(enter.getProductionProductType()))) {
            if (ClassTypeEnums.TYPE_ONE.getValue().equals(enter.getClassType())) {
                OpeProductionScooterBomDraft opeProductionScooterBomDraft =
                    opeProductionScooterBomDraftService.getById(enter.getId());
                if (opeProductionScooterBomDraft == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
                }
                // 部件列表信息
                productionProductId = opeProductionScooterBomDraft.getId();
                productionPartsRelationType = ProductionPartsRelationTypeEnums.SCOOTER_DRAFT.getValue();

                result = RosProductionProductDetailResult.builder().id(opeProductionScooterBomDraft.getId())
                    .qty(opeProductionScooterBomDraft.getPartsQty()).productN(opeProductionScooterBomDraft.getBomNo())
                    .groupId(opeProductionScooterBomDraft.getGroupId())
                    .colorId(opeProductionScooterBomDraft.getColorId()).qty(opeProductionScooterBomDraft.getPartsQty())
                    .procurementCycle(opeProductionScooterBomDraft.getProcurementCycle())
                    .enName(opeProductionScooterBomDraft.getEnName())
                    .effectiverDate(opeProductionScooterBomDraft.getEffectiveDate())
                    .createTime(opeProductionScooterBomDraft.getCreatedTime())
                    .createById(opeProductionScooterBomDraft.getCreatedBy()).build();
                if (result.getGroupId() != null && result.getGroupId() != 0) {
                    ProductionScooterGroupEnums groupName =
                        ProductionScooterGroupEnums.getEnumByValue(result.getGroupId().intValue());
                    result.setGroupName(groupName == null ? null : groupName.getCode());
                }
                if (result.getColorId() != null && result.getColorId() != 0) {
                    ProductColorEnums colorEnum =
                        ProductColorEnums.getProductColorEnumsByValue(String.valueOf(result.getColorId()));
                    result.setColorName(colorEnum == null ? null : colorEnum.getCode());
                }
            }
            if (ClassTypeEnums.TYPE_TWO.getValue().equals(enter.getClassType())) {
                OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getById(enter.getId());
                if (opeProductionScooterBom == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
                }
                // 其他版本列表
                versionList =
                    rosProductionProductServiceMapper.productionScooterVersionList(opeProductionScooterBom.getBomNo());
                // 部件列表信息
                productionProductId = opeProductionScooterBom.getId();
                productionPartsRelationType = ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue();

                result = RosProductionProductDetailResult.builder().id(opeProductionScooterBom.getId())
                    .qty(opeProductionScooterBom.getPartsQty()).productN(opeProductionScooterBom.getBomNo())
                    .groupId(opeProductionScooterBom.getGroupId()).colorId(opeProductionScooterBom.getColorId())
                    .procurementCycle(opeProductionScooterBom.getProcurementCycle())
                    .enName(opeProductionScooterBom.getEnName()).version(opeProductionScooterBom.getVersoin())
                    .status(opeProductionScooterBom.getBomStatus())
                    .effectiverDate(opeProductionScooterBom.getEffectiveDate())
                    .createById(opeProductionScooterBom.getCreatedBy()).build();
                if (result.getGroupId() != null && result.getGroupId() != 0) {
                    ProductionScooterGroupEnums groupName =
                        ProductionScooterGroupEnums.getEnumByValue(result.getGroupId().intValue());
                    result.setGroupName(groupName == null ? null : groupName.getCode());
                }
                if (result.getColorId() != null && result.getColorId() != 0) {
                    ProductColorEnums colorEnum =
                        ProductColorEnums.getProductColorEnumsByValue(String.valueOf(result.getColorId()));
                    result.setColorName(colorEnum == null ? null : colorEnum.getCode());
                }
            }
        }
        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(),
            String.valueOf(enter.getProductionProductType()))) {
            if (ClassTypeEnums.TYPE_ONE.getValue().equals(enter.getClassType())) {
                OpeProductionCombinBomDraft opeProductionCombinBomDraft =
                    opeProductionCombinBomDraftService.getById(enter.getId());
                if (opeProductionCombinBomDraft == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
                }
                // 部件列表信息
                productionProductId = opeProductionCombinBomDraft.getId();
                productionPartsRelationType = ProductionPartsRelationTypeEnums.COMBINATION_DRAFT.getValue();

                result = RosProductionProductDetailResult.builder().id(opeProductionCombinBomDraft.getId())
                    .qty(opeProductionCombinBomDraft.getPartsQty()).productN(opeProductionCombinBomDraft.getBomNo())
                    .procurementCycle(opeProductionCombinBomDraft.getProcurementCycle())
                    .enName(opeProductionCombinBomDraft.getEnName())
                    .effectiverDate(opeProductionCombinBomDraft.getEffectiveDate())
                    .createById(opeProductionCombinBomDraft.getCreatedBy()).build();
            }
            if (ClassTypeEnums.TYPE_TWO.getValue().equals(enter.getClassType())) {
                OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomService.getById(enter.getId());
                if (opeProductionCombinBom == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
                }
                // 其他版本列表
                versionList =
                    rosProductionProductServiceMapper.productionCombinBomVersionList(opeProductionCombinBom.getBomNo());
                // 部件列表信息
                productionProductId = opeProductionCombinBom.getId();
                productionPartsRelationType = ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue();

                result = RosProductionProductDetailResult.builder().id(opeProductionCombinBom.getId())
                    .productN(opeProductionCombinBom.getBomNo()).qty(opeProductionCombinBom.getPartsQty())
                    .procurementCycle(opeProductionCombinBom.getProcurementCycle())
                    .enName(opeProductionCombinBom.getEnName()).version(opeProductionCombinBom.getVersoin())
                    .status(opeProductionCombinBom.getBomStatus())
                    .effectiverDate(opeProductionCombinBom.getEffectiveDate())
                    .createById(opeProductionCombinBom.getCreatedBy()).build();
            }
        }

        // 创建人
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(
            new LambdaQueryWrapper<OpeSysUserProfile>().eq(OpeSysUserProfile::getSysUserId, result.getCreateById()));
        // 查询对应的产品部件
        List<RosProductionProductPartListResult> rosProductionProductPartListResultList =
            rosProductionProductServiceMapper.rosProductionProductPartsList(productionProductId,
                productionPartsRelationType);

        result.setCreateByFirstName(opeSysUserProfile == null ? null : opeSysUserProfile.getFirstName());
        result.setCreateByLastName(opeSysUserProfile == null ? null : opeSysUserProfile.getLastName());
        result.setVersionList(versionList);
        result.setRosProductPartListResult(rosProductionProductPartListResultList);
        return result;
    }

    /**
     * 生效
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult takeEffect(RosProuductionTypeEnter enter) {
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(),
                ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);

        if (StringUtils.equals(String.valueOf(enter.getProductionProductType()),
            BomCommonTypeEnums.SCOOTER.getValue())) {
            OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getById(enter.getId());
            if (opeProductionScooterBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }
            if (!opeProductionScooterBom.getBomStatus().equals(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
            if (!opeProductionScooterBom.getEffectiveDate().before(new Date())) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                    ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
            }

            // 查询当前是否有生效中的Bomn 有的话 更新状态
            OpeProductionScooterBom avticeScooterBom =
                opeProductionScooterBomService.getOne(new LambdaQueryWrapper<OpeProductionScooterBom>()
                    .eq(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue()));
            if (avticeScooterBom != null) {
                avticeScooterBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                avticeScooterBom.setUpdatedBy(enter.getId());
                avticeScooterBom.setUpdatedTime(new Date());
                opeProductionScooterBomService.updateById(avticeScooterBom);
            }
            opeProductionScooterBom.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            opeProductionScooterBom.setUpdatedBy(enter.getId());
            opeProductionScooterBom.setUpdatedTime(new Date());
            opeProductionScooterBomService.updateById(opeProductionScooterBom);
        }
        if (StringUtils.equals(String.valueOf(enter.getProductionProductType()),
            BomCommonTypeEnums.COMBINATION.getValue())) {
            OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomService.getById(enter.getId());
            if (opeProductionCombinBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }
            if (!opeProductionCombinBom.getBomStatus().equals(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
            if (opeProductionCombinBom.getEffectiveDate().before(new Date())) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                    ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
            }

            OpeProductionCombinBom avticeCombinBom =
                opeProductionCombinBomService.getOne(new LambdaQueryWrapper<OpeProductionCombinBom>()
                    .eq(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue()));
            if (avticeCombinBom != null) {
                avticeCombinBom.setBomStatus(ProductionBomStatusEnums.EXPIRED.getValue());
                avticeCombinBom.setUpdatedBy(enter.getId());
                avticeCombinBom.setUpdatedTime(new Date());
                opeProductionCombinBomService.updateById(avticeCombinBom);
            }
            opeProductionCombinBom.setBomStatus(ProductionBomStatusEnums.ACTIVE.getValue());
            opeProductionCombinBom.setUpdatedBy(enter.getId());
            opeProductionCombinBom.setUpdatedTime(new Date());
            opeProductionCombinBomService.updateById(opeProductionCombinBom);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 产品禁用
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult productionProductDisable(RosProuductionTypeEnter enter) {
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(),
                ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);

        if (StringUtils.equals(String.valueOf(enter.getProductionProductType()),
            BomCommonTypeEnums.COMBINATION.getValue())) {
            OpeProductionCombinBom opeProductionCombinBom = opeProductionCombinBomService.getById(enter.getId());
            if (opeProductionCombinBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }
            opeProductionCombinBom.setBomStatus(ProductionBomStatusEnums.ABOLISHED.getValue());
            opeProductionCombinBom.setUpdatedBy(enter.getId());
            opeProductionCombinBom.setUpdatedTime(new Date());
            opeProductionCombinBomService.updateById(opeProductionCombinBom);
        }
        if (StringUtils.equals(String.valueOf(enter.getProductionProductType()),
            BomCommonTypeEnums.SCOOTER.getValue())) {
            OpeProductionScooterBom opeProductionScooterBom = opeProductionScooterBomService.getById(enter.getId());
            if (opeProductionScooterBom == null) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }
            opeProductionScooterBom.setBomStatus(ProductionBomStatusEnums.ABOLISHED.getValue());
            opeProductionScooterBom.setUpdatedBy(enter.getId());
            opeProductionScooterBom.setUpdatedTime(new Date());
            opeProductionScooterBomService.updateById(opeProductionScooterBom);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 发布
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult release(RosProuductionTypeEnter enter) {
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(),
                ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);

        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBomDraft opeProductionScooterBomDraft =
                opeProductionScooterBomDraftService.getById(enter.getId());
            if (opeProductionScooterBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            // 校验车辆
            checkOpeProductionScooter(enter, opeProductionScooterBomDraft);
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft opeProductionCombinBomDraft =
                opeProductionCombinBomDraftService.getById(enter.getId());
            if (opeProductionCombinBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }

            // 信息校验
            checkOpeProductionOpeProductionCombinBom(enter, opeProductionCombinBomDraft);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除草稿
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult delete(RosProuductionTypeEnter enter) {
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBomDraft opeProductionScooterBomDraft =
                opeProductionScooterBomDraftService.getById(enter.getId());
            if (opeProductionScooterBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            // 删除部件
            opeProductionScooterBomDraftService.removeById(opeProductionScooterBomDraft.getId());
            if (opeProductionScooterBomDraft.getPartsQty() > 0) {
                opeProductionPartsRelationService.remove(new LambdaQueryWrapper<OpeProductionPartsRelation>()
                    .eq(OpeProductionPartsRelation::getProductionId, opeProductionScooterBomDraft.getId())
                    .eq(OpeProductionPartsRelation::getProductionType,
                        ProductionPartsRelationTypeEnums.SCOOTER_DRAFT.getValue()));
            }
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft opeProductionCombinBomDraft =
                opeProductionCombinBomDraftService.getById(enter.getId());
            if (opeProductionCombinBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            opeProductionCombinBomDraftService.removeById(opeProductionCombinBomDraft.getId());
            if (opeProductionCombinBomDraft.getPartsQty() > 0) {
                opeProductionPartsRelationService.remove(new LambdaQueryWrapper<OpeProductionPartsRelation>()
                    .eq(OpeProductionPartsRelation::getProductionId, opeProductionCombinBomDraft.getId())
                    .eq(OpeProductionPartsRelation::getProductionType,
                        ProductionPartsRelationTypeEnums.COMBINATION_DRAFT.getValue()));
            }

        }
        return new GeneralResult(enter.getRequestId());
    }

    private void checkOpeProductionScooter(RosProuductionTypeEnter enter,
        OpeProductionScooterBomDraft opeProductionScooterBomDraft) {
        // 数据完整性校验
        int count = 7;
        if (StringUtils.isNotBlank(opeProductionScooterBomDraft.getBomNo())) {
            count--;
        }
        if (opeProductionScooterBomDraft.getGroupId() != null || opeProductionScooterBomDraft.getGroupId() != 0) {
            count--;
        }
        if (opeProductionScooterBomDraft.getColorId() != null || opeProductionScooterBomDraft.getColorId() != 0) {
            count--;
        }
        if (opeProductionScooterBomDraft.getProcurementCycle() != null
            || opeProductionScooterBomDraft.getProcurementCycle() != 0) {
            count--;
        }
        if (opeProductionScooterBomDraft.getPartsQty() != null || opeProductionScooterBomDraft.getPartsQty() != 0) {
            count--;
        }
        if (opeProductionScooterBomDraft.getEffectiveDate() != null) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionScooterBomDraft.getEnName())) {
            count--;
        }
        if (count != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getCode(),
                ExceptionCodeEnums.BOM_NUM_REPEAT.getMessage());
        }

        if (!new Date().before(opeProductionScooterBomDraft.getEffectiveDate())) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getCode(),
                ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getMessage());
        }

        // 编号校验
        List<OpeProductionScooterBom> opeProductionScooterBomList =
            opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                .eq(OpeProductionScooterBom::getBomNo, opeProductionScooterBomDraft.getBomNo()));
        String versionIdentificat = null;
        String versionNum = null;
        if (CollectionUtils.isEmpty(opeProductionScooterBomList)) {
            versionIdentificat = String.valueOf(DateUtil.Start);
            versionNum = getVersionNum(null);
        } else {
            versionIdentificat = opeProductionScooterBomList.get(0).getVersionIdentificat();
            versionNum = getVersionNum(opeProductionScooterBomList.get(0).getVersoin());
        }

        OpeProductionScooterBom productionScooterBom = OpeProductionScooterBom.builder()
            .id(idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM)).dr(0)
            .versionIdentificat(versionIdentificat).versoin(versionNum).bomNo(opeProductionScooterBomDraft.getBomNo())
            .procurementCycle(opeProductionScooterBomDraft.getProcurementCycle())
            .effectiveDate(opeProductionScooterBomDraft.getEffectiveDate())
            .groupId(opeProductionScooterBomDraft.getGroupId()).colorId(opeProductionScooterBomDraft.getColorId())
            .bomStatus(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()).announUserId(enter.getUserId())
            .opAnnounUserId(enter.getUserId()).partsQty(opeProductionScooterBomDraft.getPartsQty())
            .cnName(StringUtils.isBlank(opeProductionScooterBomDraft.getCnName()) ? null
                : opeProductionScooterBomDraft.getCnName())
            .enName(StringUtils.isBlank(opeProductionScooterBomDraft.getEnName()) ? null
                : opeProductionScooterBomDraft.getEnName())
            .frName(StringUtils.isBlank(opeProductionScooterBomDraft.getFrName()) ? null
                : opeProductionScooterBomDraft.getFrName())
            .createdBy(enter.getUserId()).updatedBy(enter.getUserId()).createdTime(new Date()).updatedTime(new Date())
            .build();

        opeProductionScooterBomService.save(productionScooterBom);
        // 删除草稿
        opeProductionScooterBomDraftService.removeById(opeProductionScooterBomDraft);
    }

    private void checkOpeProductionOpeProductionCombinBom(RosProuductionTypeEnter enter,
        OpeProductionCombinBomDraft opeProductionCombinBomDraft) {
        // 数据完整性校验
        int count = 7;
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getBomNo())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getCnName())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getFrName())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getEnName())) {
            count--;
        }
        if (opeProductionCombinBomDraft.getProcurementCycle() != null
            || opeProductionCombinBomDraft.getProcurementCycle() != 0) {
            count--;
        }
        if (opeProductionCombinBomDraft.getPartsQty() != null || opeProductionCombinBomDraft.getPartsQty() != 0) {
            count--;
        }
        if (opeProductionCombinBomDraft.getEffectiveDate() != null) {
            count--;
        }
        if (count != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getCode(),
                ExceptionCodeEnums.BOM_NUM_REPEAT.getMessage());
        }

        if (!new Date().before(opeProductionCombinBomDraft.getEffectiveDate())) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getCode(),
                ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getMessage());
        }
        // 编号校验
        List<OpeProductionCombinBom> opeProductionCombinBomList =
            opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                .eq(OpeProductionCombinBom::getBomNo, opeProductionCombinBomDraft.getBomNo()));
        String versionIdentificat = null;
        String versionNum = null;
        if (CollectionUtils.isEmpty(opeProductionCombinBomList)) {
            versionIdentificat = String.valueOf(DateUtil.Start);
            versionNum = getVersionNum(null);
        } else {
            versionIdentificat = opeProductionCombinBomList.get(0).getVersionIdentificat();
            versionNum = getVersionNum(opeProductionCombinBomList.get(0).getVersoin());
        }

        OpeProductionCombinBom opeProductionCombinBom = OpeProductionCombinBom.builder()
            .id(idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM)).dr(0)
            .versionIdentificat(versionIdentificat).versoin(versionNum).bomNo(opeProductionCombinBomDraft.getBomNo())
            .procurementCycle(opeProductionCombinBomDraft.getProcurementCycle())
            .effectiveDate(opeProductionCombinBomDraft.getEffectiveDate())
            .groupId(opeProductionCombinBomDraft.getGroupId()).colorId(opeProductionCombinBomDraft.getColorId())
            .bomStatus(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()).announUserId(enter.getUserId())
            .opAnnounUserId(enter.getUserId()).partsQty(opeProductionCombinBomDraft.getPartsQty())
            .cnName(StringUtils.isBlank(opeProductionCombinBomDraft.getCnName()) ? null
                : opeProductionCombinBomDraft.getCnName())
            .enName(StringUtils.isBlank(opeProductionCombinBomDraft.getEnName()) ? null
                : opeProductionCombinBomDraft.getEnName())
            .frName(StringUtils.isBlank(opeProductionCombinBomDraft.getFrName()) ? null
                : opeProductionCombinBomDraft.getFrName())
            .createdBy(enter.getUserId()).updatedBy(enter.getUserId()).createdTime(new Date()).updatedTime(new Date())
            .build();

        opeProductionCombinBomService.save(opeProductionCombinBom);
        // 删除草稿
        opeProductionCombinBomDraftService.removeById(opeProductionCombinBomDraft);
    }


    private OpeProductionCombinBomDraft buildProductionCombinBom(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionCombinBomDraft.builder().dr(0).bomNo(enter.getProductN())
            .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId()).colorId(enter.getColorId())
            .enName(enter.getEnName()).cnName(enter.getCnName()).effectiveDate(enter.getEffectiveDate())
            .frName(enter.getFrName()).partsQty(qty).updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    private OpeProductionScooterBomDraft buildOpeProductionScooterDraft(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionScooterBomDraft.builder().dr(0).bomNo(enter.getProductN())
            .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId())
            .effectiveDate(enter.getEffectiveDate()).colorId(enter.getColorId()).enName(enter.getEnName()).partsQty(qty)
            .updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    private String getVersionNum(String version) {
        String versionNum = null;
        if (StringUtils.isBlank(version)) {
            // 新版本
            versionNum = String.valueOf((char)(int)((Math.random() * 26) + 65)) + "01";
        } else {
            // 版本号累加
            char[] versionChar = version.toCharArray();
            String versionString = Character.toString(versionChar[versionChar.length - 2])
                + Character.toString(versionChar[versionChar.length - 1]);
            Integer versionInteger = Integer.valueOf(versionString) + 1;
            if (versionInteger < 9) {
                versionNum = Character.toString(versionChar[0]) + String.valueOf(versionInteger);
            }
            versionNum = Character.toString(versionChar[0]) + "0" + String.valueOf(versionInteger);
        }
        return versionNum;
    }
}
