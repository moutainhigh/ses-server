package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.ClassTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionScooterGroupEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
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
            result.put(ClassTypeEnums.TYPE_TWO.getValue(),
                opeProductionScooterBomService
                    .count(new LambdaQueryWrapper<OpeProductionScooterBom>().in(OpeProductionScooterBom::getBomStatus,
                        ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())));
        }

        // 组合
        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(), String.valueOf(enter.getId()))) {
            result.put(ClassTypeEnums.TYPE_ONE.getValue(), opeProductionCombinBomDraftService.count());
            result.put(ClassTypeEnums.TYPE_TWO.getValue(),
                opeProductionCombinBomService
                    .count(new LambdaQueryWrapper<OpeProductionCombinBom>().in(OpeProductionCombinBom::getBomStatus,
                        ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())));
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
                if (count == 0) {
                    return PageResult.createZeroRowResult(enter);
                }
                rosProductionScooterListResults = rosProductionProductServiceMapper.scooterBomList(enter);
            }
        }

        for (RosProductionScooterListResult item : rosProductionScooterListResults) {
            item.setGroupName(ProductionScooterGroupEnums.getEnumByValue(item.getGroupId().intValue()).getCode());
            item.setColorName(
                ProductColorEnums.getProductColorEnumsByValue(String.valueOf(item.getColorId())).getCode());
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
            if (CollectionUtils.isEmpty(opeProductionScooterBomList)) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }

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
            if (CollectionUtils.isEmpty(opeProductionCombinBomList)) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
            }

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
        return new BooleanResult(Boolean.TRUE);
    }

    /**
     * excel 导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelPartsResult saveScooterImportExcel(GeneralEnter enter) {
        return null;
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
                opeProductionScooterBomDraft = buildOpeProductionScooterDraft(enter, partQty);
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
                productionCombinBomDraft = buildProductionCombinBom(enter, partQty);
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
                    .productN(opeProductionScooterBomDraft.getBomNo())
                    .groupId(opeProductionScooterBomDraft.getGroupId())
                    .groupName(ProductionScooterGroupEnums
                        .getEnumByValue(opeProductionScooterBomDraft.getGroupId().intValue()).getCode())
                    .colorId(opeProductionScooterBomDraft.getColorId())
                    .colorName(ProductColorEnums
                        .getProductColorEnumsByValue(String.valueOf(opeProductionScooterBomDraft.getColorId()))
                        .getCode())
                    .procurementCycle(opeProductionScooterBomDraft.getProcurementCycle())
                    .enName(opeProductionScooterBomDraft.getEnName())
                    .createTime(opeProductionScooterBomDraft.getCreatedTime())
                    .createById(opeProductionScooterBomDraft.getCreatedBy()).build();
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
                    .productN(opeProductionScooterBom.getBomNo()).groupId(opeProductionScooterBom.getGroupId())
                    .groupName(ProductionScooterGroupEnums
                        .getEnumByValue(opeProductionScooterBom.getGroupId().intValue()).getCode())
                    .colorId(opeProductionScooterBom.getColorId())
                    .colorName(ProductColorEnums
                        .getProductColorEnumsByValue(String.valueOf(opeProductionScooterBom.getColorId())).getCode())
                    .procurementCycle(opeProductionScooterBom.getProcurementCycle())
                    .enName(opeProductionScooterBom.getEnName()).version(opeProductionScooterBom.getVersoin())
                    .status(opeProductionScooterBom.getBomStatus())
                    .effectiverDate(opeProductionScooterBom.getEffectiveDate())
                    .createById(opeProductionScooterBom.getCreatedBy()).build();
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
                    .productN(opeProductionCombinBomDraft.getBomNo())
                    .procurementCycle(opeProductionCombinBomDraft.getProcurementCycle())
                    .enName(opeProductionCombinBomDraft.getEnName())
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
                    rosProductionProductServiceMapper.productionScooterVersionList(opeProductionCombinBom.getBomNo());
                // 部件列表信息
                productionProductId = opeProductionCombinBom.getId();
                productionPartsRelationType = ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue();

                result = RosProductionProductDetailResult.builder().id(opeProductionCombinBom.getId())
                    .productN(opeProductionCombinBom.getBomNo())
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
            if (opeProductionScooterBom.getEffectiveDate().before(new Date())) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                    ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
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
            if (!opeProductionScooterBom.getBomStatus().equals(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
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
        return new GeneralResult(enter.getRequestId());
    }


    private OpeProductionCombinBomDraft buildProductionCombinBom(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionCombinBomDraft.builder().dr(0).bomNo(enter.getProductN())
            .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId()).colorId(enter.getColorId())
            .enName(enter.getEnName()).cnName(enter.getCnName()).frName(enter.getFrName()).partsQty(qty)
            .updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    private OpeProductionScooterBomDraft buildOpeProductionScooterDraft(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionScooterBomDraft.builder().dr(0).bomNo(enter.getProductN())
            .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId()).colorId(enter.getColorId())
            .enName(enter.getEnName()).partsQty(qty).updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }
}
