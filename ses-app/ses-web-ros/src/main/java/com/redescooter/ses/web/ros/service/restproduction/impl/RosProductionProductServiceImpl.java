package com.redescooter.ses.web.ros.service.restproduction.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.ClassTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
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
import com.redescooter.ses.web.ros.service.qctemplete.ProductionQcTmepleteService;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.verifyhandler.ProductionProductExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.SaveByCopyIdEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Ros production product service.
 */
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
    private OpeColorService opeColorService;

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @Autowired
    private ProductionQcTmepleteService productionQcTmepleteService;

    @Autowired
    private OpeProductionQualityTempateService opeProductionQualityTempateService;

    @Autowired
    private OpeProductPriceService opeProductPriceService;

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
        return rosProductionProductServiceMapper.groupList(generalEnter);
    }

    /**
     * 颜色查询
     *
     * @param enter
     * @return
     */
    @Override
    public List<BaseNameResult> colorList(GeneralEnter enter) {
        return rosProductionProductServiceMapper.colorList(enter);
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
            count = rosProductionProductServiceMapper.scooterBomListCount(enter,
                    ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            rosProductionScooterListResults = rosProductionProductServiceMapper.scooterBomList(enter,
                    ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
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
            int count = rosProductionProductServiceMapper.combinationListCount(enter,
                    ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, rosProductionProductServiceMapper.combinationList(enter,
                    ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));
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
            // 查询当前产品是否有其他版本
            List<OpeProductionScooterBom> productionCombinBomList =
                    opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                            .eq(OpeProductionScooterBom::getBomNo, enter.getProductN())
                            .in(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue(),
                                    ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));

            if (CollectionUtils.isNotEmpty(productionCombinBomList)) {
                for (OpeProductionScooterBom item : productionCombinBomList) {
                    if (item.getEffectiveDate().equals(enter.getDateTime())) {
                        return new BooleanResult(Boolean.FALSE);
                    }
                }
            }
            if (DateUtil.diffDay(enter.getDateTime(),new Date())>0) {
                return new BooleanResult(Boolean.FALSE);
            }
        }

        // 组合产品校验
        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(), String.valueOf(enter.getProductionType()))) {
            // 查询当前产品是否有其他版本
            List<OpeProductionCombinBom> productionCombinBomList =
                    opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                            .eq(OpeProductionCombinBom::getBomNo, enter.getProductN()).in(OpeProductionCombinBom::getBomStatus,
                                    ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));

            if (CollectionUtils.isNotEmpty(productionCombinBomList)) {
                for (OpeProductionCombinBom item : productionCombinBomList) {
                    if (item.getEffectiveDate().equals(enter.getDateTime())) {
                        return new BooleanResult(Boolean.FALSE);
                    }
                }
            }
            if (enter.getDateTime().before(new Date())) {
                return new BooleanResult(Boolean.FALSE);
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
                importExcelService.setiExcelVerifyHandler(new ProductionProductExcelVerifyHandlerImpl())
                        .importOssExcel(enter.getUrl(), RosParseExcelData.class, new ImportParams());
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
                        .sec(item.getSec()).enName(item.getEnglishName()).cnName(item.getChineseName())
                        .errMsg(item.getErrorMsg()).rowNum(item.getRowNum()).build());
            });
        }
        if (CollectionUtils.isNotEmpty(successList)) {
            successProductPartListResult = checkSuccessRosProductionProductPartListResults(failProductPartListResult, successList);
        }
        // 判断失败的部件，是否存在正式部件，如果存在 部件部件信息不完整异常《 部件不存在异常 （不存在异常会覆盖 信息不完整异常）
        if (CollectionUtils.isNotEmpty(failProductPartListResult)) {
            List<RosProductionProductPartListResult> failPartProductionList =
                    rosProductionProductServiceMapper.rosImportProductionProductPartsList(failProductPartListResult.stream()
                            .map(RosProductionProductPartListResult::getPartsNum).collect(Collectors.toList()));
            if (CollectionUtils.isEmpty(failPartProductionList)) {
                failProductPartListResult.stream().forEach(item -> item.setErrMsg("This part does not exist."));
            }
            if (CollectionUtils.isNotEmpty(failPartProductionList)) {
                failProductPartListResult.stream()
                        .filter(item -> failPartProductionList.stream()
                                .noneMatch(part -> StringUtils.equals(item.getPartsNum(), part.getPartsNum())))
                        .forEach(item -> item.setErrMsg("This part does not exist."));
            }
        }
        importProductionProductResult.setFailProductPartListResult(failProductPartListResult);
        importProductionProductResult.setSuccessProductPartListResult(successProductPartListResult);
        importProductionProductResult
                .setSuccess(CollectionUtils.isEmpty(failProductPartListResult) ? Boolean.TRUE : Boolean.FALSE);
        return importProductionProductResult;
    }

    /**
     * check 解析成功的零部件
     * @param failProductPartListResult
     * @param successList
     * @return
     */
    private List<RosProductionProductPartListResult> checkSuccessRosProductionProductPartListResults(List<RosProductionProductPartListResult> failProductPartListResult,
                                                                                                     List<RosParseExcelData> successList) {
        List<RosProductionProductPartListResult>  successProductPartListResult = rosProductionProductServiceMapper.rosImportProductionProductPartsList(
                successList.stream().map(RosParseExcelData::getPartsNo).collect(Collectors.toList()));
        if (CollectionUtils.isNotEmpty(successProductPartListResult)) {
            for (RosParseExcelData item : successList) {
                RosProductionProductPartListResult rosProductionProductPartListResult = successProductPartListResult
                        .stream().filter(part -> StringUtils.equals(item.getPartsNo(), part.getPartsNum())).findFirst()
                        .orElse(null);
                // 非空 和信息校验
                if (rosProductionProductPartListResult == null
                        || !StringUtils.equals(item.getChineseName(), rosProductionProductPartListResult.getCnName())
                        || !StringUtils.equals(item.getEnglishName(), rosProductionProductPartListResult.getEnName())) {
                    failProductPartListResult.add(RosProductionProductPartListResult.builder()
                            .partsNum(item.getPartsNo()).rowNum(item.getRowNum()).enName(item.getEnglishName())
                            .sec(item.getSec()).cnName(item.getChineseName()).errMsg(item.getErrorMsg()).build());
                    continue;
                }
                if (null != rosProductionProductPartListResult &&
                        (rosProductionProductPartListResult.getPrice() == null ||
                                rosProductionProductPartListResult.getPrice().equals(BigDecimal.ZERO) ||
                                rosProductionProductPartListResult.getTempleteCount() == 0)) {
                    long existCount = failProductPartListResult.stream().filter(part -> StringUtils.equals(item.getPartsNo(), part.getPartsNum())).count();
                    if (existCount == 0) {
                        failProductPartListResult.add(RosProductionProductPartListResult.builder()
                                .partsNum(item.getPartsNo()).rowNum(item.getRowNum()).enName(item.getEnglishName())
                                .sec(item.getSec()).cnName(item.getChineseName()).errMsg("There is no price or quality inspection template for this part.").build());
                    }
                    continue;
                }
                rosProductionProductPartListResult.setQty(Integer.valueOf(item.getQuantity()));
            }
            for (RosParseExcelData success : successList) {
                for (RosProductionProductPartListResult item : failProductPartListResult) {
                    if (StringUtils.equals(item.getPartsNum(), success.getPartsNo())) {
                        successList.remove(success);
                        continue;
                    }
                }
            }
        }
        //部件存在性校验
        if (CollectionUtils.isNotEmpty(successList) && CollectionUtils.isEmpty(successProductPartListResult)) {
            successList.forEach(item -> {
                RosProductionProductPartListResult rosProductionProductPartListResult = failProductPartListResult.stream()
                        .filter(fail -> successProductPartListResult.stream()
                                .noneMatch(part -> StringUtils.equals(fail.getPartsNum(), part.getPartsNum())))
                        .findFirst().orElse(null);
                if(rosProductionProductPartListResult==null){
                    failProductPartListResult.add(RosProductionProductPartListResult.builder()
                            .partsNum(item.getPartsNo()).rowNum(item.getRowNum()).enName(item.getEnglishName())
                            .sec(item.getSec()).cnName(item.getChineseName()).errMsg("This part does not exist.").build());
                }else {
                    failProductPartListResult.stream()
                            .filter(fail -> successProductPartListResult.stream()
                                    .noneMatch(part -> StringUtils.equals(fail.getPartsNum(), part.getPartsNum())))
                            .forEach(part->part.setErrMsg("This part does not exist."));
                }
            });
        }

        return successProductPartListResult;
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
            OpeProductionScooterBomDraft opeProductionScooterBomDraft = buildOpeProductionScooterDraft(enter, partQty);
            if (enter.getId() == null || enter.getId() == 0) {
                // 保存
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
                opeProductionScooterBomDraft.setCreatedBy(queryProductionScooterBomDraft.getCreatedBy());
                opeProductionScooterBomDraft.setCreatedTime(queryProductionScooterBomDraft.getCreatedTime());
                opeProductionScooterBomDraft.setId(enter.getId());
            }
            productionProductId = opeProductionScooterBomDraft.getId();
            productionProducType = ProductionPartsRelationTypeEnums.SCOOTER_DRAFT.getValue();
            opeProductionScooterBomDraftService.insertOrUpdate(opeProductionScooterBomDraft);
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft productionCombinBomDraft = buildProductionCombinBom(enter, partQty);
            if (enter.getId() == null || enter.getId() == 0) {
                // 保存
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
                productionCombinBomDraft.setCreatedBy(queryProductionCombinBomDraft.getCreatedBy());
                productionCombinBomDraft.setCreatedTime(queryProductionCombinBomDraft.getCreatedTime());
                productionCombinBomDraft.setId(enter.getId());
            }
            productionProductId = productionCombinBomDraft.getId();
            productionProducType = ProductionPartsRelationTypeEnums.COMBINATION_DRAFT.getValue();
            opeProductionCombinBomDraftService.insertOrUpdate(productionCombinBomDraft);
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

        if (enter.getClassType() == null || enter.getClassType() == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.TYPE_IS_NULL.getCode(),
                    ExceptionCodeEnums.TYPE_IS_NULL.getMessage());
        }

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
                        .enName(StringUtils.isBlank(opeProductionScooterBomDraft.getEnName()) ? null
                                : opeProductionScooterBomDraft.getEnName())
                        .cnName(StringUtils.isBlank(opeProductionScooterBomDraft.getCnName()) ? null
                                : opeProductionScooterBomDraft.getCnName())
                        .frName(StringUtils.isBlank(opeProductionScooterBomDraft.getFrName()) ? null
                                : opeProductionScooterBomDraft.getFrName())
                        .effectiverDate(opeProductionScooterBomDraft.getEffectiveDate())
                        .createTime(opeProductionScooterBomDraft.getCreatedTime()).qcTempletePromptIcon(Boolean.TRUE)
                        .createById(opeProductionScooterBomDraft.getCreatedBy()).build();
                if (result.getGroupId() != null && result.getGroupId() != 0) {
                    OpeSpecificatGroup opeSpecificatGroup = opeSpecificatGroupService.getById(result.getGroupId());
                    if (opeSpecificatGroup == null) {
                        throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(),
                                ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
                    }
                    result.setGroupName(opeSpecificatGroup.getGroupName());
                }
                if (result.getColorId() != null && result.getColorId() != 0) {
                    OpeColor opeColor = opeColorService.getById(result.getColorId());
                    if (opeColor == null) {
                        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(),
                                ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
                    }
                    result.setColorName(opeColor.getColorName());
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
                        .enName(StringUtils.isBlank(opeProductionScooterBom.getEnName()) ? null
                                : opeProductionScooterBom.getEnName())
                        .cnName(StringUtils.isBlank(opeProductionScooterBom.getCnName()) ? null
                                : opeProductionScooterBom.getCnName())
                        .frName(StringUtils.isBlank(opeProductionScooterBom.getFrName()) ? null
                                : opeProductionScooterBom.getFrName())
                        .version(opeProductionScooterBom.getVersoin()).status(opeProductionScooterBom.getBomStatus())
                        .effectiverDate(opeProductionScooterBom.getEffectiveDate()).qcTempletePromptIcon(Boolean.TRUE)
                        .createById(opeProductionScooterBom.getCreatedBy()).build();
                if (result.getGroupId() != null && result.getGroupId() != 0) {
                    OpeSpecificatGroup opeSpecificatGroup = opeSpecificatGroupService.getById(result.getGroupId());
                    if (opeSpecificatGroup == null) {
                        throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(),
                                ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
                    }
                    result.setGroupName(opeSpecificatGroup.getGroupName());
                }
                if (result.getColorId() != null && result.getColorId() != 0) {
                    OpeColor opeColor = opeColorService.getById(result.getColorId());
                    if (opeColor != null) {
                        result.setColorName(opeColor.getColorName());
                    }
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
                        .enName(StringUtils.isBlank(opeProductionCombinBomDraft.getEnName()) ? null
                                : opeProductionCombinBomDraft.getEnName())
                        .cnName(StringUtils.isBlank(opeProductionCombinBomDraft.getCnName()) ? null
                                : opeProductionCombinBomDraft.getCnName())
                        .frName(StringUtils.isBlank(opeProductionCombinBomDraft.getFrName()) ? null
                                : opeProductionCombinBomDraft.getFrName())
                        .effectiverDate(opeProductionCombinBomDraft.getEffectiveDate()).qcTempletePromptIcon(Boolean.TRUE)
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
                        .enName(StringUtils.isBlank(opeProductionCombinBom.getEnName()) ? null
                                : opeProductionCombinBom.getEnName())
                        .cnName(StringUtils.isBlank(opeProductionCombinBom.getCnName()) ? null
                                : opeProductionCombinBom.getCnName())
                        .frName(StringUtils.isBlank(opeProductionCombinBom.getFrName()) ? null
                                : opeProductionCombinBom.getFrName())
                        .version(opeProductionCombinBom.getVersoin()).status(opeProductionCombinBom.getBomStatus())
                        .effectiverDate(opeProductionCombinBom.getEffectiveDate()).qcTempletePromptIcon(Boolean.TRUE)
                        .createById(opeProductionCombinBom.getCreatedBy()).build();
            }
        }

        // 设置整车详情中 质检模板
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_TWO.getValue())) {
            Integer opeProductionQualityTempateCount =
                    opeProductionQualityTempateService.count(new LambdaQueryWrapper<OpeProductionQualityTempate>()
                            .eq(OpeProductionQualityTempate::getProductionId, enter.getId())
                            .eq(OpeProductionQualityTempate::getProductionType, enter.getProductionProductType()));
            if (opeProductionQualityTempateCount != 0) {
                result.setQcTempletePromptIcon(Boolean.FALSE);
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
                            .eq(OpeProductionScooterBom::getBomNo, opeProductionScooterBom.getBomNo())
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
            if(DateUtil.diffDays(opeProductionCombinBom.getEffectiveDate(),new Date())>0){
                throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                        ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
            }

            OpeProductionCombinBom avticeCombinBom =
                    opeProductionCombinBomService.getOne(new LambdaQueryWrapper<OpeProductionCombinBom>()
                            .eq(OpeProductionCombinBom::getBomNo, opeProductionCombinBom.getBomNo())
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
    public GeneralResult release(RosProductionProductReleaseEnter enter) {
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        String checkResut = jedisService.get(key);
        if (!Boolean.valueOf(checkResut)) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_FAILURE.getCode(),
                    ExceptionCodeEnums.SAFE_CODE_FAILURE.getMessage());
        }
        jedisService.delKey(key);

        if (enter.getDirectRelease()) {
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
            // 校验车辆
            if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
                checkOpeProductionScooter(enter, null, partList);
            }
            if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
                checkOpeProductionOpeProductionCombinBom(enter, null, partList);
            }
            return new GeneralResult(enter.getRequestId());
        }

        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBomDraft opeProductionScooterBomDraft =
                    opeProductionScooterBomDraftService.getById(enter.getId());
            if (opeProductionScooterBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            // 校验车辆
            checkOpeProductionScooter(enter, opeProductionScooterBomDraft, null);
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft opeProductionCombinBomDraft =
                    opeProductionCombinBomDraftService.getById(enter.getId());
            if (opeProductionCombinBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }

            // 信息校验
            checkOpeProductionOpeProductionCombinBom(enter, opeProductionCombinBomDraft, null);
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

    /**
     * 产品编号校验
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult checkProductN(CheckProductNEnter enter) {
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            QueryWrapper queryWrapper = new QueryWrapper();
            if (enter.getId() != null && enter.getId() != 0) {
                queryWrapper.ne(OpeProductionScooterBom.COL_ID, enter.getId());
            }
            queryWrapper.eq(OpeProductionScooterBom.COL_BOM_NO, enter.getProductN());
            queryWrapper.in(OpeProductionScooterBom.COL_BOM_STATUS, ProductionBomStatusEnums.ACTIVE.getValue(),
                    ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
            List<OpeProductionScooterBom> opeProductionScooterBomList =
                    opeProductionScooterBomService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(opeProductionScooterBomList)) {
                return new BooleanResult(Boolean.FALSE);
            }
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            QueryWrapper queryWrapper = new QueryWrapper();
            if (enter.getId() != null && enter.getId() != 0) {
                queryWrapper.ne(OpeProductionCombinBom.COL_ID, enter.getId());
            }
            queryWrapper.eq(OpeProductionCombinBom.COL_BOM_NO, enter.getProductN());
            queryWrapper.in(OpeProductionCombinBom.COL_BOM_STATUS, ProductionBomStatusEnums.ACTIVE.getValue(),
                    ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
            List<OpeProductionCombinBom> opeProductionCombinBomList = opeProductionCombinBomService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(opeProductionCombinBomList)) {
                return new BooleanResult(Boolean.FALSE);
            }
        }
        return new BooleanResult(Boolean.TRUE);
    }

    /**
     * 校验产品信息 是否完整
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult checkProductionInfo(RosProuductionTypeEnter enter) {
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))) {
            OpeProductionScooterBomDraft opeProductionScooterBomDraft =
                    opeProductionScooterBomDraftService.getById(enter.getId());
            if (opeProductionScooterBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            // 校验车辆草稿信息
            checkScooterInfo(opeProductionScooterBomDraft);
        }
        if (enter.getProductionProductType().equals(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))) {
            OpeProductionCombinBomDraft opeProductionCombinBomDraft =
                    opeProductionCombinBomDraftService.getById(enter.getId());
            if (opeProductionCombinBomDraft == null) {
                throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
            }
            checkCombinInfo(opeProductionCombinBomDraft);
        }
        return null;
    }

    private void checkOpeProductionScooter(RosProductionProductReleaseEnter enter,
                                           OpeProductionScooterBomDraft opeProductionScooterBomDraft, List<ProductionProductEnter> partList) {

        Long opeProductionBomId = idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM);

        List<OpeProductionParts> opeProductionPartsList = null;
        int partQty = 0;
        if (enter.getDirectRelease()) {
            partQty = partList.stream().mapToInt(ProductionProductEnter::getQty).sum();

            // 校验部件是否存在
            opeProductionPartsList = opeProductionPartsService.list(new LambdaQueryWrapper<OpeProductionParts>()
                    .eq(OpeProductionParts::getDisable, Boolean.FALSE).in(OpeProductionParts::getId,
                            partList.stream().map(ProductionProductEnter::getId).collect(Collectors.toList())));
            if (CollectionUtils.isEmpty(opeProductionPartsList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getMessage());
            }
            opeProductionScooterBomDraft = OpeProductionScooterBomDraft.builder()
                    .id(idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM_DRAFT)).dr(0).bomNo(enter.getProductN())
                    .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId())
                    .effectiveDate(enter.getEffectiverDate()).colorId(enter.getColorId()).enName(enter.getEnName())
                    .partsQty(partQty).updatedBy(enter.getUserId()).updatedTime(new Date()).createdBy(enter.getUserId())
                    .createdTime(new Date()).build();
        }
        // 校验车辆草稿信息
        checkScooterInfo(opeProductionScooterBomDraft);

        // 查询当前产品是否有其他版本
        List<OpeProductionScooterBom> productionScooterBomList =
                opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                        .eq(OpeProductionScooterBom::getBomNo, enter.getProductN()).in(OpeProductionScooterBom::getBomStatus,
                                ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));
        if (CollectionUtils.isNotEmpty(productionScooterBomList)) {
            productionScooterBomList.forEach(item -> {
                if (item.getEffectiveDate().equals(enter.getEffectiverDate())) {
                    throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getCode(),
                            ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getMessage());
                }
            });
        }
        Integer addHour = Integer.valueOf(StringUtils.equals(StringUtils.trim(enter.getTimeZone()).substring(0, 1), "0") ? StringUtils.trim(enter.getTimeZone()).substring(1, 2) :
                enter.getTimeZone().substring(0, 2));
        if(DateUtil.diffDays(opeProductionScooterBomDraft.getEffectiveDate(),DateUtil.dateAddHour(new Date(),addHour))>0){
            throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                    ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
        }

        // 编号校验
        List<OpeProductionScooterBom> opeProductionScooterBomList =
                opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                        .eq(OpeProductionScooterBom::getBomNo, opeProductionScooterBomDraft.getBomNo()));

        // 子记录 保存
        List<OpeProductionPartsRelation> opeProductionPartsRelationList = new ArrayList<>();
        if (!enter.getDirectRelease()) {
            // 查询关系表
            opeProductionPartsRelationList =
                    opeProductionPartsRelationService.list(new LambdaQueryWrapper<OpeProductionPartsRelation>()
                            .eq(OpeProductionPartsRelation::getProductionId, opeProductionScooterBomDraft.getId()));
            if (CollectionUtils.isEmpty(opeProductionPartsRelationList)) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getCode(),
                        ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getMessage());
            }
            for (OpeProductionPartsRelation item : opeProductionPartsRelationList) {
                item.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_RELATION));
                item.setProductionId(opeProductionBomId);
                item.setProductionType(ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
                item.setCreatedBy(enter.getUserId());
                item.setCreatedTime(new Date());
                item.setUpdatedTime(new Date());
                item.setUpdatedBy(enter.getUserId());
            }
        } else {
            for (OpeProductionParts item : opeProductionPartsList) {
                opeProductionPartsRelationList.add(OpeProductionPartsRelation.builder()
                        .id(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_RELATION)).dr(0)
                        .productionId(opeProductionBomId)
                        .productionType(ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue()).partsId(item.getId())
                        .partsNo(item.getPartsNo()).partsSec(item.getPartsSec())
                        .procurementCycle(item.getProcurementCycle())
                        .partsQty(partList.stream().filter(part -> part.getId().equals(item.getId())).findFirst()
                                .orElse(null).getQty())
                        .cnName(StringUtils.isBlank(item.getCnName()) ? null : item.getCnName())
                        .frName(StringUtils.isBlank(item.getFrName()) ? null : item.getFrName())
                        .enName(StringUtils.isBlank(item.getEnName()) ? null : item.getEnName())
                        .createdBy(enter.getUserId()).createdTime(new Date()).updatedBy(enter.getUserId())
                        .updatedTime(new Date()).build());
            }
        }

        String versionIdentificat = null;
        String versionNum = null;
        if (CollectionUtils.isEmpty(opeProductionScooterBomList)) {
            versionIdentificat = String.valueOf(DateUtil.Start);
            versionNum = getVersionNum(null);
        } else {
            versionIdentificat = opeProductionScooterBomList.get(0).getVersionIdentificat();
            versionNum = getVersionNum(getMaxVersionNum(opeProductionScooterBomList.stream()
                    .map(OpeProductionScooterBom::getVersoin).collect(Collectors.toList())));
        }

        OpeProductionScooterBom productionScooterBom = OpeProductionScooterBom.builder().id(opeProductionBomId).dr(0)
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

        if (!enter.getDirectRelease()) {
            // 删除草稿
            opeProductionScooterBomDraftService.removeById(opeProductionScooterBomDraft);
        }
        if (null != enter.getId() && enter.getId() != 0) {
            OpeProductionScooterBomDraft productionScooterBomDraft =
                    opeProductionScooterBomDraftService.getById(enter.getId());
            if (productionScooterBomDraft != null) {
                opeProductionScooterBomDraftService.removeById(productionScooterBomDraft.getId());
            }
            // 若当前版本为待激活 发布后 删除当前版本
            OpeProductionScooterBom queryOpeProductionScooterBom =
                    opeProductionScooterBomService.getById(enter.getId());
            if (queryOpeProductionScooterBom != null) {
                if (queryOpeProductionScooterBom.getBomStatus()
                        .equals(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())) {
                    queryOpeProductionScooterBom.setBomStatus(ProductionBomStatusEnums.ABOLISHED.getValue());
                    opeProductionScooterBomService.updateById(queryOpeProductionScooterBom);
                }
            }
        }

        opeProductionScooterBomService.save(productionScooterBom);

        if (opeProductionPartsRelationList.stream().filter(item -> item.getPartsQty() < 0).findFirst()
                .orElse(null) != null) {
            throw new SesWebRosException(ExceptionCodeEnums.THE_NUMBER_OF_PARTS_LEAST_GREATER_THAN_ONE.getCode(),
                    ExceptionCodeEnums.THE_NUMBER_OF_PARTS_LEAST_GREATER_THAN_ONE.getMessage());
        }
        // 自己记录保存
        opeProductionPartsRelationService.saveBatch(opeProductionPartsRelationList);

        // 保存 质检模板
        SaveByCopyIdEnter saveByCopyIdEnter = new SaveByCopyIdEnter(productionScooterBom.getId(), Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()),
                enter.getId(), Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()));
        saveByCopyIdEnter.setUserId(enter.getUserId());
        productionQcTmepleteService.saveByCopyId(
                saveByCopyIdEnter);

    }

    private void checkScooterInfo(OpeProductionScooterBomDraft opeProductionScooterBomDraft) {
        // 数据完整性校验
        int count = 7;
        if (StringUtils.isNotBlank(opeProductionScooterBomDraft.getBomNo())) {
            count--;
        }
        if (!Objects.equals(null, opeProductionScooterBomDraft.getGroupId())
                && opeProductionScooterBomDraft.getGroupId() != 0) {
            count--;
        }
        if (!Objects.equals(null, opeProductionScooterBomDraft.getColorId())
                && opeProductionScooterBomDraft.getColorId() != 0) {
            count--;
        }
        if (!Objects.equals(null, opeProductionScooterBomDraft.getProcurementCycle())
                && opeProductionScooterBomDraft.getProcurementCycle() != 0) {
            count--;
        }
        if (!Objects.equals(null, opeProductionScooterBomDraft.getPartsQty())
                && opeProductionScooterBomDraft.getPartsQty() != 0) {
            count--;
        }
        if (null != opeProductionScooterBomDraft.getEffectiveDate()) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionScooterBomDraft.getEnName())) {
            count--;
        }
        if (count != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getCode(),
                    ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getMessage());
        }
    }

    private void checkOpeProductionOpeProductionCombinBom(RosProductionProductReleaseEnter enter,
                                                          OpeProductionCombinBomDraft opeProductionCombinBomDraft, List<ProductionProductEnter> partList) {

        Long combinBomBomId = idAppService.getId(SequenceName.OPE_PRODUCTION_SCOOTER_BOM);

        List<OpeProductionParts> opeProductionPartsList = null;
        int partQty = 0;
        if (enter.getDirectRelease()) {
            partQty = partList.stream().mapToInt(ProductionProductEnter::getQty).sum();

            // 校验部件是否存在
            opeProductionPartsList = opeProductionPartsService.list(new LambdaQueryWrapper<OpeProductionParts>()
                    .eq(OpeProductionParts::getDisable, Boolean.FALSE).in(OpeProductionParts::getId,
                            partList.stream().map(ProductionProductEnter::getId).collect(Collectors.toList())));
            if (CollectionUtils.isEmpty(opeProductionPartsList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getMessage());
            }
            opeProductionCombinBomDraft = OpeProductionCombinBomDraft.builder()
                    .id(idAppService.getId(SequenceName.OPE_PRODUCTION_COMBIN_BOM_DRAFT)).dr(0).bomNo(enter.getProductN())
                    .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId()).colorId(enter.getColorId())
                    .enName(enter.getEnName()).cnName(enter.getCnName()).effectiveDate(enter.getEffectiverDate())
                    .frName(enter.getFrName()).partsQty(partQty).updatedBy(enter.getUserId()).updatedTime(new Date())
                    .createdBy(enter.getUserId()).createdTime(new Date()).build();
        }
        // 校验组合信息
        checkCombinInfo(opeProductionCombinBomDraft);
        // 查询当前产品是否有其他版本
        List<OpeProductionCombinBom> productionCombinBomList =
                opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                        .eq(OpeProductionCombinBom::getBomNo, enter.getProductN()).in(OpeProductionCombinBom::getBomStatus,
                                ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue()));
        if (CollectionUtils.isNotEmpty(productionCombinBomList)) {
            productionCombinBomList.forEach(item -> {
                if (item.getEffectiveDate().equals(enter.getEffectiverDate())) {
                    throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getCode(),
                            ExceptionCodeEnums.BOM_HAS_DUPLICATE_EFFECTIVE_DATE.getMessage());
                }
            });
        }
        Integer addHour = Integer.valueOf(StringUtils.equals(StringUtils.trim(enter.getTimeZone()).substring(0, 1), "0") ? StringUtils.trim(enter.getTimeZone()).substring(1, 2) :
                enter.getTimeZone().substring(0, 2));
        if(DateUtil.diffDays(opeProductionCombinBomDraft.getEffectiveDate(),DateUtil.dateAddHour(new Date(),addHour))>0){
            throw new SesWebRosException(ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getCode(),
                    ExceptionCodeEnums.BOM_HAS_REACHED_EFFECTIVE_TIME.getMessage());
        }

        // 子记录 保存
        List<OpeProductionPartsRelation> opeProductionPartsRelationList = new ArrayList<>();
        if (!enter.getDirectRelease()) {
            // 查询关系表
            opeProductionPartsRelationList =
                    opeProductionPartsRelationService.list(new LambdaQueryWrapper<OpeProductionPartsRelation>()
                            .eq(OpeProductionPartsRelation::getProductionId, opeProductionCombinBomDraft.getId()));
            if (CollectionUtils.isEmpty(opeProductionPartsRelationList)) {
                throw new SesWebRosException(ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getCode(),
                        ExceptionCodeEnums.BOM_PART_HAVE_LAST_ONE.getMessage());
            }
            for (OpeProductionPartsRelation item : opeProductionPartsRelationList) {
                item.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_RELATION));
                item.setProductionId(combinBomBomId);
                item.setProductionType(ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
                item.setCreatedBy(enter.getUserId());
                item.setCreatedTime(new Date());
                item.setUpdatedTime(new Date());
                item.setUpdatedBy(enter.getUserId());
            }
        } else {
            for (OpeProductionParts item : opeProductionPartsList) {
                opeProductionPartsRelationList.add(OpeProductionPartsRelation.builder()
                        .id(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_RELATION)).dr(0)
                        .productionId(combinBomBomId)
                        .productionType(ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue()).partsId(item.getId())
                        .partsNo(item.getPartsNo()).partsSec(item.getPartsSec())
                        .procurementCycle(item.getProcurementCycle())
                        .partsQty(partList.stream().filter(part -> part.getId().equals(item.getId())).findFirst()
                                .orElse(null).getQty())
                        .cnName(StringUtils.isBlank(item.getCnName()) ? null : item.getCnName())
                        .frName(StringUtils.isBlank(item.getFrName()) ? null : item.getFrName())
                        .enName(StringUtils.isBlank(item.getEnName()) ? null : item.getEnName())
                        .createdBy(enter.getUserId()).createdTime(new Date()).updatedBy(enter.getUserId())
                        .updatedTime(new Date()).build());
            }
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
            versionNum = getVersionNum(getMaxVersionNum(opeProductionCombinBomList.stream()
                    .map(OpeProductionCombinBom::getVersoin).collect(Collectors.toList())));
        }

        OpeProductionCombinBom opeProductionCombinBom = OpeProductionCombinBom.builder().id(combinBomBomId).dr(0)
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

        // 自己记录保存
        opeProductionPartsRelationService.saveBatch(opeProductionPartsRelationList);

        SaveByCopyIdEnter saveByCopyIdEnter = new SaveByCopyIdEnter(opeProductionCombinBom.getId(),
                Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()), enter.getId(),
                Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()));
        saveByCopyIdEnter.setUserId(enter.getUserId());
        // 保存 质检模板
        productionQcTmepleteService.saveByCopyId(saveByCopyIdEnter);

        if (!enter.getDirectRelease()) {
            // 删除草稿
            opeProductionCombinBomDraftService.removeById(opeProductionCombinBomDraft);
        }
        if (null != enter.getId() && enter.getId() != 0) {
            OpeProductionCombinBomDraft productionCombinBomDraft =
                    opeProductionCombinBomDraftService.getById(enter.getId());
            if (productionCombinBomDraft != null) {
                opeProductionCombinBomDraftService.removeById(productionCombinBomDraft.getId());
            }
            // 若当前版本为待激活 发布后 删除当前版本
            OpeProductionCombinBom queryOpeProductionCombinBom = opeProductionCombinBomService.getById(enter.getId());
            if (queryOpeProductionCombinBom != null) {
                if (queryOpeProductionCombinBom.getBomStatus()
                        .equals(ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())) {
                    queryOpeProductionCombinBom.setBomStatus(ProductionBomStatusEnums.ABOLISHED.getValue());
                    opeProductionCombinBomService.updateById(queryOpeProductionCombinBom);
                }
            }
        }
    }

    private void checkCombinInfo(OpeProductionCombinBomDraft opeProductionCombinBomDraft) {
        // 数据完整性校验
        int count = 7;
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getBomNo())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getCnName())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getEnName())) {
            count--;
        }
        if (StringUtils.isNotBlank(opeProductionCombinBomDraft.getFrName())) {
            count--;
        }
        if (!Objects.equals(null, opeProductionCombinBomDraft.getProcurementCycle())
                && opeProductionCombinBomDraft.getProcurementCycle() != 0) {
            count--;
        }
        if (!Objects.equals(null, opeProductionCombinBomDraft.getPartsQty())
                && opeProductionCombinBomDraft.getPartsQty() != 0) {
            count--;
        }
        if (!Objects.equals(null, opeProductionCombinBomDraft.getEffectiveDate())) {
            count--;
        }
        if (count != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getCode(),
                    ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getMessage());
        }
    }

    private OpeProductionCombinBomDraft buildProductionCombinBom(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionCombinBomDraft.builder().dr(0).tenantId(0L).deptId(0L).bomNo(enter.getProductN())
                .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId()).colorId(enter.getColorId())
                .enName(enter.getEnName()).cnName(enter.getCnName()).effectiveDate(enter.getEffectiverDate())
                .frName(enter.getFrName()).partsQty(qty).updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    private OpeProductionScooterBomDraft buildOpeProductionScooterDraft(RosSaveProductionProductEnter enter, int qty) {
        return OpeProductionScooterBomDraft.builder().tenantId(0L).deptId(0L).dr(0).bomNo(enter.getProductN())
                .procurementCycle(enter.getProcurementCycle()).groupId(enter.getGroupId())
                .effectiveDate(enter.getEffectiverDate()).colorId(enter.getColorId()).enName(enter.getEnName())
                .partsQty(qty).updatedBy(enter.getUserId()).updatedTime(new Date()).build();
    }

    private String getMaxVersionNum(List<String> stringList) {

        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }

        int versionNum = 0;
        String result = null;
        for (String item : stringList) {
            if (StringUtils.isEmpty(item)) {
                continue;
            }
            // 版本号累加
            char[] versionChar = item.toCharArray();
            String versionString = Character.toString(versionChar[versionChar.length - 2])
                    + Character.toString(versionChar[versionChar.length - 1]);
            Integer versionInteger = Integer.valueOf(versionString);
            if (versionNum == 0 || versionNum < versionInteger) {
                versionNum = versionInteger;
                result = item;
            }
        }
        return result;
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

    @Override
    public GeneralResult bomExport(Long id, Integer productionProductType, HttpServletResponse response) {
        List<RosProductionExport> exportList = new ArrayList<>();
        switch (productionProductType) {
            case 4:
                exportList = scooterExport(id);
            default:
                break;
            case 5:
                exportList = combinationExport(id);
                break;
        }
        try {
            // 设置响应输出的头类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
            // =========easypoi部分
            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName("log");
            // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RosProductionExport.class, exportList);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            System.out.println("+++++++++++++++++++");
        }
        return new GeneralResult();
    }

    /**
     * Scooter export list.
     *
     * @param id the id
     * @return list
     * @Author Aleks
     * @Description 封装需要导出的整车数据
     * @Date 2020 /9/30 13:32
     * @Param
     */
    public List<RosProductionExport> scooterExport(Long id) {
        List<RosProductionExport> resultList = new ArrayList<>();
        // 这个时候id为整车的id
        OpeProductionScooterBom scooterBom = opeProductionScooterBomService.getById(id);
        if (scooterBom == null) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
        }
        // 找到整车的部件
        List<OpeProductionPartsRelation> relationList = getRrelations(id);
        if (CollectionUtils.isNotEmpty(relationList)) {
            List<OpePartsSec> secList = opePartsSecService.list();
            for (OpeProductionPartsRelation relation : relationList) {
                RosProductionExport export = new RosProductionExport();
                export.setName(scooterBom.getEnName());
                export.setProductN(scooterBom.getBomNo());
                export.setVersion(scooterBom.getVersoin());
                export.setSec(getSecName(relation.getPartsSec(), secList));
                export.setPartsNo(relation.getPartsNo());
                export.setEnName(relation.getEnName());
                export.setCnName(relation.getCnName());
                export.setProcurementCycle(relation.getProcurementCycle());
                export.setQty(relation.getPartsQty());
                resultList.add(export);
            }
        }
        return resultList;
    }

    /**
     * Gets sec name.
     *
     * @param secid the secid
     * @param secList the sec list
     * @return the sec name
     */
// 通过secId匹配到name
    public String getSecName(Long secid, List<OpePartsSec> secList) {
        if (org.apache.dubbo.common.utils.CollectionUtils.isEmpty(secList)) {
            return null;
        }
        String secName = "";
        for (OpePartsSec sec : secList) {
            if (sec.getId().equals(secid)) {
                secName = sec.getName();
                break;
            }
        }
        return secName;
    }

    private List<OpeProductionPartsRelation> getRrelations(Long id) {
        QueryWrapper<OpeProductionPartsRelation> qw = new QueryWrapper<>();
        qw.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, id);
        List<OpeProductionPartsRelation> relationList = opeProductionPartsRelationService.list(qw);
        return relationList;
    }

    /**
     * Combination export list.
     *
     * @param id the id
     * @return list
     * @Author Aleks
     * @Description 封装需要导出的组装数据
     * @Date 2020 /9/30 13:32
     * @Param
     */
    public List<RosProductionExport> combinationExport(Long id) {
        List<RosProductionExport> resultList = new ArrayList<>();
        // 这个时候传的是组装的id
        OpeProductionCombinBom combinBom = opeProductionCombinBomService.getById(id);
        if (combinBom == null) {
            throw new SesWebRosException(ExceptionCodeEnums.BOM_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.BOM_IS_NOT_EXIST.getMessage());
        }
        // 找到组装的部件
        List<OpeProductionPartsRelation> relationList = getRrelations(id);
        if (CollectionUtils.isNotEmpty(relationList)) {
            List<OpePartsSec> secList = opePartsSecService.list();
            for (OpeProductionPartsRelation relation : relationList) {
                RosProductionExport export = new RosProductionExport();
                export.setName(combinBom.getEnName());
                export.setProductN(combinBom.getBomNo());
                export.setVersion(combinBom.getVersoin());
                export.setSec(getSecName(relation.getPartsSec(), secList));
                export.setPartsNo(relation.getPartsNo());
                export.setEnName(relation.getEnName());
                export.setCnName(relation.getCnName());
                export.setProcurementCycle(relation.getProcurementCycle());
                export.setQty(relation.getPartsQty());
                resultList.add(export);
            }
        }
        return resultList;
    }

    /**
     * 校验导入的list 数据
     *
     * @param successProductPartListResult
     * @param failProductPartListResult
     * @param successList
     * @return
     */
    private List<RosProductionProductPartListResult> checkRosProductionProductPartListResults(
            List<RosProductionProductPartListResult> successProductPartListResult,
            List<RosProductionProductPartListResult> failProductPartListResult, List<RosParseExcelData> successList) {
        if (CollectionUtils.isNotEmpty(successList)) {
            successProductPartListResult = rosProductionProductServiceMapper.rosImportProductionProductPartsList(
                    successList.stream().map(RosParseExcelData::getPartsNo).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(successProductPartListResult)) {
                for (RosParseExcelData item : successList) {
                    RosProductionProductPartListResult rosProductionProductPartListResult = successProductPartListResult
                            .stream().filter(part -> StringUtils.equals(item.getPartsNo(), part.getPartsNum())).findFirst()
                            .orElse(null);
                    // 非空 和信息校验
                    if (rosProductionProductPartListResult == null
                            || !StringUtils.equals(item.getChineseName(), rosProductionProductPartListResult.getCnName())
                            || !StringUtils.equals(item.getEnglishName(), rosProductionProductPartListResult.getEnName())) {
                        failProductPartListResult.add(RosProductionProductPartListResult.builder()
                                .partsNum(item.getPartsNo()).rowNum(item.getRowNum()).enName(item.getEnglishName())
                                .sec(item.getSec()).cnName(item.getChineseName()).errMsg(item.getErrorMsg()).build());
                        successProductPartListResult
                                .removeIf(part -> StringUtils.equals(item.getPartsNo(), part.getPartsNum()));
                        continue;
                    }
                    rosProductionProductPartListResult.setQty(Integer.valueOf(item.getQuantity()));
                }
            }
            if (CollectionUtils.isNotEmpty(successList)) {
                if (CollectionUtils.isEmpty(successProductPartListResult)) {
                    successList.forEach(item -> {
                        failProductPartListResult
                                .add(RosProductionProductPartListResult.builder().partsNum(item.getPartsNo())
                                        .rowNum(item.getRowNum()).enName(item.getEnglishName()).sec(item.getSec())
                                        .cnName(item.getChineseName()).errMsg("This part does not exist.").build());
                    });
                } else {
                    // 采购价过滤
                    successProductPartListResult.stream().filter(price -> (Objects.equals(null, price.getPrice())
                            || Objects.equals(BigDecimal.ZERO, price.getPrice()))).forEach(item -> {
                        failProductPartListResult.add(RosProductionProductPartListResult.builder()
                                .partsNum(item.getPartsNum()).rowNum(item.getRowNum()).enName(item.getEnName())
                                .sec(item.getSec()).cnName(item.getCnName())
                                .errMsg("There is no purchase price set for this part.").build());
                    });
                }
            }
        }
        // 判断失败的部件，是否存在正式部件，如果存在 部件部件信息不完整异常《 部件不存在异常 （不存在异常会覆盖 信息不完整异常）
        if (CollectionUtils.isNotEmpty(failProductPartListResult)) {
            List<RosProductionProductPartListResult> failPartProductionList =
                    rosProductionProductServiceMapper.rosImportProductionProductPartsList(failProductPartListResult.stream()
                            .map(RosProductionProductPartListResult::getPartsNum).collect(Collectors.toList()));
            if (CollectionUtils.isEmpty(failPartProductionList)) {
                failPartProductionList.stream().forEach(item -> item.setErrMsg("This part does not exist."));
            }
            if (CollectionUtils.isNotEmpty(failPartProductionList)) {
                failProductPartListResult.stream()
                        .filter(item -> failPartProductionList.stream()
                                .noneMatch(part -> StringUtils.equals(item.getPartsNum(), part.getPartsNum())))
                        .forEach(item -> item.setErrMsg("This part does not exist."));
            }
        }
        return successProductPartListResult;
    }

}
