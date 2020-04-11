package com.redescooter.ses.web.ros.service.bom.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomSnClassEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.PartsEventEnums;
import com.redescooter.ses.api.common.enums.bom.QcSourceTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplate;
import com.redescooter.ses.web.ros.dm.OpePartDraftQcTemplateB;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplate;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplateB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateBService;
import com.redescooter.ses.web.ros.service.base.OpePartDraftQcTemplateService;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateBService;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateService;
import com.redescooter.ses.web.ros.service.bom.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.ProdoctPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QcItemTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultEnter;
import com.redescooter.ses.web.ros.vo.bom.QcResultResult;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationDetailResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.combination.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName:BomRosServiceImpl
 * @description: BomRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:17
 */
@Slf4j
@Service
public class BomRosServiceImpl implements BomRosService {

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpePartDraftQcTemplateService opePartDraftQcTemplateService;

    @Autowired
    private OpePartDraftQcTemplateBService opePartDraftQcTemplateBService;

    @Autowired
    private OpeProductQcTemplateService opeProductQcTemplateService;

    @Autowired
    private OpeProductQcTemplateBService opeProductQcTemplateBService;

    @Autowired
    private OpePartsDraftService opePartsDraftService;


    /**
     * @param enter
     * @desc: 车辆列表
     * @param: enter
     * @retrn: PageResult
     * @auther: alex
     * @date: 2020/2/25 10:31
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ScooterListResult> scooterList(ScooterListEnter enter) {
        int count = bomRosServiceMapper.scooterListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, bomRosServiceMapper.scooterList(enter));
    }

    /**
     * @param enter
     * @desc: 保存整车
     * @param: enter
     * @eturn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 10:36
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult saveScooter(SaveScooterEnter enter) {
        // json 转换
        List<ProdoctPartListEnter> partList = null;
        try {
            partList = JSONArray.parseArray(enter.getPartList(), ProdoctPartListEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 数据重复性过滤
        HashSet<Long> partIds = new HashSet<>();
        partList.forEach(item -> {
            partIds.add(item.getId());
        });
        if (partIds.size() != partList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        List<OpePartsProductB> opePartsProductList = new ArrayList<>();
        int partAllQty = 0;

        OpePartsProduct opePartsProduct = OpePartsProduct.builder()
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .status(BomStatusEnums.NORMAL.getValue())
                .snClass(BomSnClassEnums.SSC.getValue())
                .productNumber(enter.getProductN())
                .cnName(enter.getProductName())
                .frName(enter.getProductName())
                .enName(enter.getProductName())
                .productionCycle(enter.getProcurementCycle())
                .productType(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()))
                .note(null)
                .afterSalesFlag(Boolean.TRUE)
                .revision(0)
                .build();

        // 校验部品既有价格又有供应商的 部品
        List<Long> partsIdList = partList.stream().map(ProdoctPartListEnter::getId).collect(Collectors.toList());
        if (bomRosServiceMapper.countSupplierWithPriceByPartIds(partsIdList) != partsIdList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_CANNOT_BE_ASSEMBLED_WITHOUT_SUPPLIERS_WITHOUT_PRICES.getCode(),
                    ExceptionCodeEnums.PARTS_CANNOT_BE_ASSEMBLED_WITHOUT_SUPPLIERS_WITHOUT_PRICES.getMessage());
        }
        if (enter.getId() == null || enter.getId() == 0) {
            //保存
            Long productId = idAppService.getId(SequenceName.OPE_PARTS_PRODUCT);
            // 产品编号过滤
            List<String> productNList = bomRosServiceMapper.checkProductNums(enter);
            if (productNList.contains(enter.getProductN())) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
            }
            //子表都保存
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {

                    OpePartsProductB opePartsProductB = buildOpePartsProductBSingle(enter.getUserId(), productId, item);
                    opePartsProductB.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B));
                    opePartsProductB.setPartsId(item.getId());
                    opePartsProductB.setCreatedBy(enter.getUserId());
                    opePartsProductB.setCreatedTime(new Date());

                    opePartsProductList.add(opePartsProductB);
                    partAllQty += item.getQty();
                }
            }
            opePartsProduct.setId(productId);
            opePartsProduct.setCreatedBy(enter.getUserId());
            opePartsProduct.setCreatedTime(new Date());
        } else {
            // 修改
            OpePartsProduct partsProduct = opePartsProductService.getById(enter.getId());
            if (partsProduct == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            //产品编号一致 跳过校验 ，不一致重新校验
            if (!SesStringUtils.equals(partsProduct.getProductNumber(), enter.getProductN())) {
                List<String> productNList = bomRosServiceMapper.checkProductNums(enter);
                if (productNList.contains(enter.getProductN())) {
                    throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
                }
            }
            // 进行 产品条目数据过滤
            checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.UPDATE.getValue(), partList, null);

            //删除产品子表数据
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_PRODUCT_ID, enter.getId());
            opePartsProductBService.remove(opePartsProductBQueryWrapper);
            // 重新添加数据
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsProductB opePartsProductB = buildOpePartsProductBSingle(enter.getUserId(), enter.getId(), item);
                    opePartsProductB.setCreatedBy(enter.getUserId());
                    opePartsProductB.setCreatedTime(new Date());
                    opePartsProductB.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B));
                    opePartsProductList.add(opePartsProductB);
                    partAllQty += item.getQty();
                }
            }
            opePartsProduct.setId(enter.getId());
        }
        opePartsProduct.setSumPartsQty(partAllQty);
        opePartsProduct.setUpdatedBy(enter.getUserId());
        opePartsProduct.setUpdatedTime(new Date());

        //部品没有价格 没有供应商 不可组合车辆

        opePartsProductBService.saveOrUpdateBatch(opePartsProductList);

        opePartsProductService.saveOrUpdate(opePartsProduct);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: sec 区域查询
     * @param: enter
     * @retrn: SecResult
     * @auther: alex
     * @date: 2020/2/25 12:33
     * @Version: Ros 1.2
     */
    @Override
    public List<SecResult> secList(GeneralEnter enter) {
        return bomRosServiceMapper.secList(enter);
    }


    /**
     * @param enter
     * @desc: 整车详情
     * @param: enter
     * @retrn: ScooterDetailResult
     * @auther: alex
     * @date: 2020/2/25 13:19
     * @Version: Ros 1.2
     */
    @Override
    public ScooterDetailResult scooterDetail(IdEnter enter) {
        OpePartsProduct scooter = opePartsProductService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        List<QueryPartListResult> partList = bomRosServiceMapper.productDeatilPartList(enter);
        ScooterDetailResult scooterDetailResult = ScooterDetailResult.builder()
                .id(scooter.getId())
                .productN(scooter.getProductNumber())
                .productCnName(scooter.getCnName())
                .procurementCycle(SesStringUtils.isBlank(scooter.getProductionCycle()) == true ? "0" : scooter.getProductionCycle())
                .build();
        if (CollectionUtils.isNotEmpty(partList)) {
            double sum = partList.stream().mapToDouble(QueryPartListResult::getQty).sum();
            scooterDetailResult.setQtySum(sum);
            scooterDetailResult.setPartList(partList);

        }
        return scooterDetailResult;
    }

    /**
     * @param enter
     * @desc: 删除整车的配件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 13:20
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult deleteScooterPart(DeletePartEnter enter) {

        if (CollectionUtils.isEmpty(enter.getIds())) {
            return new GeneralResult(enter.getRequestId());
        }
        // 整车查询
        OpePartsProduct scooter = opePartsProductService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!SesStringUtils.equals(scooter.getProductType().toString(), BomCommonTypeEnums.SCOOTER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 进行 产品条目数据过滤
        checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.DELETE.getValue(), null, enter.getIds());

        //数据删除
        opePartsProductBService.removeByIds(enter.getIds());
        scooter.setSumPartsQty(scooter.getSumPartsQty() - enter.getIds().size());
        scooter.setUpdatedBy(enter.getUserId());
        scooter.setUpdatedTime(new Date());
        opePartsProductService.updateById(scooter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 删除整车
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:37
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult deleteScooter(IdEnter enter) {
        // 整车查询
        OpePartsProduct scooter = opePartsProductService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!SesStringUtils.equals(scooter.getProductType().toString(), BomCommonTypeEnums.SCOOTER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 配件删除
        if (scooter.getSumPartsQty() != 0) {
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_PRODUCT_ID, scooter.getId());
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
            opePartsProductBService.remove(opePartsProductBQueryWrapper);
        }

        // 整车删除
        opePartsProductService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 组合列表
     * @paam: enter
     * @retrn: CombinationListResult
     * @auther: alex
     * @date: 2020/2/25 14:03
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<CombinationListResult> combinationList(CombinationListEnter enter) {
        int count = bomRosServiceMapper.combinationListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, bomRosServiceMapper.combinationList(enter));
    }

    /**
     * @param enter
     * @desc: 列表部件列表
     * @param: enter
     * @retrn: List<QueryPartListResult>
     * @auther: alex
     * @date: 2020/2/27 11:44
     * @Version: Ros 1.2
     */
    @Override
    public List<QueryPartListResult> combinationListPartList(IdEnter enter) {
        // 整车查询
        OpePartsProduct scooter = opePartsProductService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!SesStringUtils.equals(scooter.getProductType().toString(), BomCommonTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        return bomRosServiceMapper.productDeatilPartList(enter);
    }

    /**
     * @param enter
     * @desc: 组合详情
     * @param: id
     * @retrn: CombinationResult
     * @auther: alex
     * @date: 2020/2/25 14:05
     * @Version: Ros 1.2
     */
    @Override
    public CombinationDetailResult combinationDetail(IdEnter enter) {
        OpePartsProduct combination = opePartsProductService.getById(enter.getId());
        if (combination == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        List<QueryPartListResult> partList = bomRosServiceMapper.productDeatilPartList(enter);
        CombinationDetailResult combinationDetailResult = CombinationDetailResult.builder()
                .id(combination.getId())
                .productN(combination.getProductNumber())
                .productCnName(combination.getCnName())
                .productEnName(combination.getEnName())
                .productFrName(combination.getFrName())
                .qty(combination.getSumPartsQty())
                .build();
        if (CollectionUtils.isNotEmpty(partList)) {
            combinationDetailResult.setPartList(partList);
        }
        return combinationDetailResult;
    }

    /**
     * @param enter
     * @desc: 删除组合里的部件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:07
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteCombinationPart(DeletePartEnter enter) {
        if (CollectionUtils.isEmpty(enter.getIds())) {
            return new GeneralResult(enter.getRequestId());
        }
        // 整车查询
        OpePartsProduct combinationPart = opePartsProductService.getById(enter.getId());
        if (combinationPart == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!SesStringUtils.equals(combinationPart.getProductType().toString(), BomCommonTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 进行 产品条目数据过滤
        checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.DELETE.getValue(), null, enter.getIds());

        //数据删除
        opePartsProductBService.removeByIds(enter.getIds());
        combinationPart.setSumPartsQty(combinationPart.getSumPartsQty() - enter.getIds().size());
        combinationPart.setUpdatedBy(enter.getUserId());
        combinationPart.setUpdatedTime(new Date());
        opePartsProductService.updateById(combinationPart);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 删除组合
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:08
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult deleteCombination(IdEnter enter) {
        OpePartsProduct combination = opePartsProductService.getById(enter.getId());
        if (combination == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!SesStringUtils.equals(combination.getProductType().toString(), BomCommonTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 配件删除
        if (combination.getSumPartsQty() != 0) {
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_ID, combination.getId());
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
            opePartsProductBService.remove(opePartsProductBQueryWrapper);
        }

        // 整车删除
        opePartsProductService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 保存组合
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:29
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult saveCombination(SaveCombinationEnter enter) {

        // json 转换
        List<ProdoctPartListEnter> partList = null;
        try {
            partList = JSONArray.parseArray(enter.getPartList(), ProdoctPartListEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 数据重复性过滤
        HashSet<Long> partIds = new HashSet<>();
        partList.forEach(item -> {
            partIds.add(item.getId());
        });
        if (partIds.size() != partList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        //todo  校验部品既有价格又有供应商的 部品
//        List<Long> partsIdList = partList.stream().map(ProdoctPartListEnter::getId).collect(Collectors.toList());
//        if (bomRosServiceMapper.countSupplierWithPriceByPartIds(partsIdList) != partsIdList.size()) {
//            throw new SesWebRosException(ExceptionCodeEnums.PARTS_CANNOT_BE_ASSEMBLED_WITHOUT_SUPPLIERS_WITHOUT_PRICES.getCode(),
//                    ExceptionCodeEnums.PARTS_CANNOT_BE_ASSEMBLED_WITHOUT_SUPPLIERS_WITHOUT_PRICES.getMessage());
//        }

        // 配件过滤
        List<Long> partIdList = new ArrayList<>();
        partList.forEach(item -> {
            partIdList.add(item.getId());
        });
        Collection<OpeParts> opePartList = opePartsService.listByIds(partIdList);

        if (CollectionUtils.isEmpty(opePartList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        int partAllQty = 0;
        List<OpePartsProductB> opePartsProductList = new ArrayList<>();

        OpePartsProduct opePartsProduct = OpePartsProduct.builder()
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .snClass(BomSnClassEnums.SSC.getValue())
                .status(BomStatusEnums.NORMAL.getValue())
                .productNumber(enter.getProductN())
                .cnName(enter.getProductCnName())
                .frName(enter.getProductFrName())
                .enName(enter.getProductEnName())
                .productionCycle(null)
                .productType(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()))
                .note(null)
                .afterSalesFlag(Boolean.TRUE)
                .revision(0)
                .build();

        if (enter.getId() == null || enter.getId() == 0) {
            // 产品编号过滤
            List<String> productNList = bomRosServiceMapper.checkProductNums(enter);
            if (productNList.contains(enter.getProductN())) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
            }
            Long ProductId = idAppService.getId(SequenceName.OPE_PARTS_PRODUCT);

            //子表都保存
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsProductB opePartsProductB = buildOpePartsProductBSingle(enter.getUserId(), ProductId, item);
                    opePartsProductB.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B));
                    opePartsProductB.setPartsId(item.getId());
                    opePartsProductB.setCreatedBy(enter.getUserId());
                    opePartsProductB.setCreatedTime(new Date());

                    opePartsProductList.add(opePartsProductB);
                    partAllQty += item.getQty();
                }
            }
            opePartsProduct.setId(ProductId);
            opePartsProduct.setCreatedBy(enter.getUserId());
            opePartsProduct.setCreatedTime(new Date());
        } else {
            // 修改
            OpePartsProduct partsProduct = opePartsProductService.getById(enter.getId());
            if (partsProduct == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            //产品编号一致 跳过校验 ，不一致重新校验
            if (!SesStringUtils.equals(partsProduct.getProductNumber(), enter.getProductN())) {
                List<String> productNList = bomRosServiceMapper.checkProductNums(enter);
                if (productNList.contains(enter.getProductN())) {
                    throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
                }
            }

            // 进行 产品条目数据过滤
            checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.UPDATE.getValue(), partList, null);

            //删除产品子表数据
            QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
            opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_PARTS_PRODUCT_ID, enter.getId());
            opePartsProductBService.remove(opePartsProductBQueryWrapper);

            // 子表修改
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsProductB opePartsProductB = buildOpePartsProductBSingle(enter.getUserId(), enter.getId(), item);
                    opePartsProductB.setCreatedBy(enter.getUserId());
                    opePartsProductB.setCreatedTime(new Date());
                    opePartsProductB.setId(idAppService.getId(SequenceName.OPE_PARTS_PRODUCT_B));
                    opePartsProductList.add(opePartsProductB);
                    partAllQty += item.getQty();
                }
            }
            opePartsProduct.setId(enter.getId());
        }
        opePartsProduct.setSumPartsQty(partAllQty);
        opePartsProduct.setUpdatedBy(enter.getUserId());
        opePartsProduct.setUpdatedTime(new Date());

        // 子表数据保存
        opePartsProductBService.saveOrUpdateBatch(opePartsProductList);

        // 主表数据保存
        opePartsProductService.saveOrUpdate(opePartsProduct);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 质检模板保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult savePartsDraftQcTemplate(SaveQcTemplateEnter enter) {
        //数据保存集合
        List<OpePartDraftQcTemplate> saveOpePartDraftQcTemplateList = new ArrayList<>();

        List<OpePartDraftQcTemplateB> saveOpePartDraftQcTemplateBList = new ArrayList<>();

        //数据解析集合
        List<QcItemTemplateEnter> qcItemTemplateEnterList = null;
        Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap = Maps.newHashMap();
        try {
            qcItemTemplateEnterList = JSON.parseArray(enter.getQcItemTemplateEnter(), QcItemTemplateEnter.class);
            if (CollectionUtils.isEmpty(qcItemTemplateEnterList)) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            qcItemTemplateEnterList.forEach(item -> {
                List<QcResultEnter> qcResultEnterList = JSON.parseArray(item.getQcResultEnter(), QcResultEnter.class);
                qcResultEnterMap.put(item, qcResultEnterList);
            });
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        checkParameterEnter(enter, qcResultEnterMap);

        //查询是否存在质检项 若存在删除所有质检项
        List<OpePartDraftQcTemplate> partQcTemplateList = deleteOpePartQcTemplates(enter);
        //形成质检项
        buildPartQcTemplate(enter, saveOpePartDraftQcTemplateList, saveOpePartDraftQcTemplateBList, qcResultEnterMap, partQcTemplateList);

        //质检模板数据保存
        if (CollectionUtils.isNotEmpty(saveOpePartDraftQcTemplateList)) {
            opePartDraftQcTemplateService.saveOrUpdateBatch(saveOpePartDraftQcTemplateList);

        }
        //质检项结果集数据保存
        opePartDraftQcTemplateBService.saveOrUpdateBatch(saveOpePartDraftQcTemplateBList);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 质检模板详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcTemplateDetailResult> partsQcTemplateDetail(IdEnter enter) {

        List<QcTemplateDetailResult> result = Lists.newArrayList();
        //部品验证

        OpePartsDraft opePartsDraft = opePartsDraftService.getById(enter.getId());
        if (opePartsDraft == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //查询质检项
        QueryWrapper<OpePartDraftQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartDraftQcTemplate.COL_PART_DRAFT_ID, enter.getId());
        List<OpePartDraftQcTemplate> partQcTemplateList = opePartDraftQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(partQcTemplateList)) {
            return result;
        }
        List<Long> templateIds = Lists.newArrayList();
        partQcTemplateList.forEach(item -> {
            templateIds.add(item.getId());
        });

        //查询质检项结果
        QueryWrapper<OpePartDraftQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateBQueryWrapper.in(OpePartDraftQcTemplateB.COL_PART_DRAFT_QC_TEMPLATE_ID, templateIds);
        List<OpePartDraftQcTemplateB> templateBList = opePartDraftQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
        if (CollectionUtils.isEmpty(templateBList)) {
            partQcTemplateList.forEach(item -> {
                result.add(
                        QcTemplateDetailResult.builder()
                                .id(item.getId())
                                .qcItemName(item.getQcItemName())
                                .qcResultResultList(new ArrayList<>())
                                .build()
                );
            });
        } else {
            partQcTemplateList.forEach(item -> {
                List<QcResultResult> resultTemplateBList = Lists.newArrayList();
                templateBList.forEach(templateb -> {
                    if (item.getId().equals(templateb.getPartDraftQcTemplateId())) {
                        resultTemplateBList.add(
                                QcResultResult.builder()
                                        .result(templateb.getQcResult())
                                        .resultSequence(templateb.getResultsSequence())
                                        .uploadPictureFalg(templateb.getUploadFlag())
                                        .build()
                        );
                    }
                });
                result.add(
                        QcTemplateDetailResult.builder()
                                .id(item.getId())
                                .qcItemName(item.getQcItemName())
                                .qcResultResultList(resultTemplateBList)
                                .build()
                );
            });
        }
        return result;
    }

    /**
     * 产品质检模板保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveProductQcTemplate(SaveQcTemplateEnter enter) {
        //数据保存集合
        List<OpeProductQcTemplate> saveOpeProductQcTemplateList = new ArrayList<>();

        List<OpeProductQcTemplateB> saveOpeProductQcTemplateBList = new ArrayList<>();

        //数据解析集合
        List<QcItemTemplateEnter> qcItemTemplateEnterList = null;
        Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap = Maps.newHashMap();
        try {
            qcItemTemplateEnterList = JSON.parseArray(enter.getQcItemTemplateEnter(), QcItemTemplateEnter.class);
            if (CollectionUtils.isEmpty(qcItemTemplateEnterList)) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            qcItemTemplateEnterList.forEach(item -> {
                List<QcResultEnter> qcResultEnterList = JSON.parseArray(item.getQcResultEnter(), QcResultEnter.class);
                qcResultEnterMap.put(item, qcResultEnterList);
            });
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (qcResultEnterMap.containsKey(null) || qcResultEnterMap.containsValue(null)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //入参校验
        qcResultEnterMap.forEach((key, value) -> {
            //质检项校验
            if (StringUtils.isBlank(key.getQcItemName())) {
                throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getMessage());
            }

            value.forEach(item -> {
                if (StringUtils.isBlank(item.getResult())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getMessage());
                }
                if (item.getUploadPictureFalg() == null || item.getUploadPictureFalg().equals("")) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getMessage());
                }
                if (item.getResultSequence() == 0 || item.getResultSequence() == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getMessage());
                }
            });
        });

        //商品验证
        OpePartsProduct opePartsProduct = opePartsProductService.getById(enter.getId());
        if (opePartsProduct == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        List<OpeProductQcTemplate> opeProductQcTemplateList = deleteOpeProductQcTemplates(enter);
        //构建质检模板
        buildProductTemplate(enter, saveOpeProductQcTemplateList, saveOpeProductQcTemplateBList, qcResultEnterMap, opeProductQcTemplateList);
        //质检模板数据保存
        if (CollectionUtils.isNotEmpty(saveOpeProductQcTemplateList)) {
            opeProductQcTemplateService.saveOrUpdateBatch(saveOpeProductQcTemplateList);

        }
        //质检项结果集数据保存
        opeProductQcTemplateBService.saveOrUpdateBatch(saveOpeProductQcTemplateBList);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @param enter
     * @return
     */
    @Override
    public PageResult<DetailsPartsResult> saveProductPartList(QueryPartListEnter enter) {
        int count = bomRosServiceMapper.saveProductPartListCount(enter);

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<DetailsPartsResult> list = bomRosServiceMapper.saveProductPartList(enter);

        return PageResult.create(enter, count, list);
    }

    /**
     * 产品质检模板详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcTemplateDetailResult> productQcTemplateDetail(IdEnter enter) {
        List<QcTemplateDetailResult> result = Lists.newArrayList();
        //部品验证
        OpePartsProduct opePartsProduct = opePartsProductService.getById(enter.getId());
        if (opePartsProduct == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //查询质检项
        QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
        opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, enter.getId());
        List<OpeProductQcTemplate> productQcTemplateList = opeProductQcTemplateService.list(opeProductQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(productQcTemplateList)) {
            return result;
        }
        List<Long> templateIds = Lists.newArrayList();
        productQcTemplateList.forEach(item -> {
            templateIds.add(item.getId());
        });

        //查询质检项结果
        QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
        opeProductQcTemplateBQueryWrapper.in(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, templateIds);
        List<OpeProductQcTemplateB> productQcTemplateBList = opeProductQcTemplateBService.list(opeProductQcTemplateBQueryWrapper);

        if (CollectionUtils.isEmpty(productQcTemplateBList)) {
            productQcTemplateList.forEach(item -> {
                result.add(
                        QcTemplateDetailResult.builder()
                                .id(item.getId())
                                .qcItemName(item.getQcItemName())
                                .qcResultResultList(new ArrayList<>())
                                .build()
                );
            });
        } else {
            productQcTemplateList.forEach(item -> {
                List<QcResultResult> resultTemplateBList = Lists.newArrayList();
                productQcTemplateBList.forEach(templateb -> {
                    if (item.getId().equals(templateb.getProductQcTemplateId())) {
                        resultTemplateBList.add(
                                QcResultResult.builder()
                                        .result(templateb.getQcResult())
                                        .resultSequence(templateb.getResultsSequence())
                                        .uploadPictureFalg(templateb.getUploadFlag())
                                        .build()
                        );
                    }
                });
                result.add(
                        QcTemplateDetailResult.builder()
                                .id(item.getId())
                                .qcItemName(item.getQcItemName())
                                .qcResultResultList(resultTemplateBList)
                                .build()
                );
            });
        }
        return result;
    }

    /**
     * 产品质检模板封装
     *
     * @param enter
     * @param saveOpeProductQcTemplateList
     * @param saveOpeProductQcTemplateBList
     * @param qcResultEnterMap
     * @param opeProductQcTemplateList
     */
    private void buildProductTemplate(SaveQcTemplateEnter enter, List<OpeProductQcTemplate> saveOpeProductQcTemplateList, List<OpeProductQcTemplateB> saveOpeProductQcTemplateBList,
                                      Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap, List<OpeProductQcTemplate> opeProductQcTemplateList) {
        if (CollectionUtils.isNotEmpty(opeProductQcTemplateList)) {
            qcResultEnterMap.forEach((key, value) -> {
                Long templateId = idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE);
                opeProductQcTemplateList.forEach(template -> {
                    if (key.getId() == null || key.getId() == 0) {
                        saveOpeProductQcTemplateList.add(
                                buildProductQcTemplate(enter, templateId, key.getQcItemName(), null, QcSourceTypeEnums.MANUAL_ENTRY.getValue())
                        );
                        value.forEach(item -> {
                            saveOpeProductQcTemplateBList.add(
                                    buildProductQcTemplateB(enter, templateId, item)
                            );
                        });
                    }
                    if (template.getId().equals(key.getId())) {
                        saveOpeProductQcTemplateList.add(
                                buildProductQcTemplate(enter, templateId, key.getQcItemName(), template.getImportExcelBatchNo(), template.getSourceType())
                        );
                        value.forEach(item -> {
                            saveOpeProductQcTemplateBList.add(
                                    buildProductQcTemplateB(enter, templateId, item)
                            );
                        });
                    }
                });
            });

        } else {
            qcResultEnterMap.forEach((key, value) -> {
                Long templateId = idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE);
                saveOpeProductQcTemplateList.add(
                        buildProductQcTemplate(enter, templateId, key.getQcItemName(), null, QcSourceTypeEnums.MANUAL_ENTRY.getValue())

                );
                value.forEach(item -> {
                    saveOpeProductQcTemplateBList.add(
                            buildProductQcTemplateB(enter, templateId, item)
                    );
                });
            });
        }
    }

    private OpeProductQcTemplateB buildProductQcTemplateB(SaveQcTemplateEnter enter, Long templateId, QcResultEnter item) {
        return OpeProductQcTemplateB.builder()
                .id(idAppService.getId(SequenceName.OPE_PRODUCT_QC_TEMPLATE_B))
                .dr(0)
                .productQcTemplateId(templateId)
                .qcResult(item.getResult())
                .uploadFlag(item.getUploadPictureFalg())
                .resultsSequence(item.getResultSequence())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpeProductQcTemplate buildProductQcTemplate(SaveQcTemplateEnter enter, Long templateId, String qcItemName, String importExcelBatchNo, String sourceType) {
        return OpeProductQcTemplate.builder()
                .id(templateId)
                .dr(0)
                .productId(enter.getId())
                .importExcelBatchNo(importExcelBatchNo)
                .sourceType(sourceType)
                .qcItemName(qcItemName)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 删除产品质检项
     *
     * @param enter
     * @return
     */
    private List<OpeProductQcTemplate> deleteOpeProductQcTemplates(SaveQcTemplateEnter enter) {
        //查询质检项 存在的话删除掉
        QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
        opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, enter.getId());
        List<OpeProductQcTemplate> opeProductQcTemplateList = opeProductQcTemplateService.list(opeProductQcTemplateQueryWrapper);
        if (CollectionUtils.isNotEmpty(opeProductQcTemplateList)) {
            List<Long> productTemplateIds = Lists.newArrayList();
            opeProductQcTemplateList.forEach(item -> {
                productTemplateIds.add(item.getId());
            });
            //查询质检结果
            QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
            opeProductQcTemplateBQueryWrapper.in(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, productTemplateIds);
            List<OpeProductQcTemplateB> productQcTemplateBList = opeProductQcTemplateBService.list(opeProductQcTemplateBQueryWrapper);
            if (CollectionUtils.isNotEmpty(productQcTemplateBList)) {
                List<Long> templateBIds = Lists.newArrayList();
                productQcTemplateBList.forEach(item -> {
                    templateBIds.add(item.getId());
                });
                opeProductQcTemplateBService.removeByIds(templateBIds);
            }
            opeProductQcTemplateService.removeByIds(productTemplateIds);
        }
        return opeProductQcTemplateList;
    }


    private OpePartsProductB buildOpePartsProductBSingle(Long userId, Long ProductId, ProdoctPartListEnter item) {
        return OpePartsProductB.builder()
                .dr(0)
                .tenantId(0L)
                .userId(userId)
                .status(BomStatusEnums.NORMAL.getValue())
                .partsProductId(ProductId)
                .partsId(item.getId())
                .partsQty(item.getQty())
                .note(null)
                .revision(0)
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();
    }

    /**
     * @desc: 产品条目验证
     * @param: productId event partList ids
     * @auther: alex
     * @date: 2020/2/27 18:19
     * @Version: Ros 1.2
     */
    private void checkProductEntry(Long productId, Long userId, String event, List<ProdoctPartListEnter> partList, List<Long> ids) {

        // 删除数据时验证
        if (SesStringUtils.equals(event, PartsEventEnums.DELETE.getValue())) {
            // 查询整车配件
            IdEnter idEnter = new IdEnter();
            idEnter.setId(productId);
            idEnter.setUserId(userId);
            List<QueryPartListResult> productPartList = bomRosServiceMapper.productDeatilPartList(idEnter);
            if (CollectionUtils.isEmpty(productPartList)) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getCode(), ExceptionCodeEnums.PRODUCT_HAS_NO_PARTS.getMessage());
            }
            List<Long> partIdList = new ArrayList<>();
            productPartList.forEach(item -> {
                partIdList.add(item.getId());
            });
            // 过滤 传入的部品id 是否合法
            ids.forEach(item -> {
                if (!partIdList.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }
        // 修改数据时验证
        if (SesStringUtils.equals(event, PartsEventEnums.UPDATE.getValue())) {
            //查询所有配件
            QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
            opePartsQueryWrapper.eq(OpeParts.COL_DR, 0);
            opePartsQueryWrapper.eq(OpeParts.COL_USER_ID, userId);
            List<OpeParts> opePartsList = opePartsService.list(opePartsQueryWrapper);

            List<Long> opePartsIdList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(opePartsList)) {
                opePartsList.forEach(item -> {
                    opePartsIdList.add(item.getId());
                });
            }
            // 过滤 传入的部品id 是否合法
            List<Long> partsIdList = Lists.newArrayList();
            partList.forEach(item -> {
                if (!opePartsIdList.contains(item.getId())) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                partsIdList.add(item.getId());
            });
        }
    }

    /**
     * 部件质检项删除
     *
     * @param enter
     * @return
     */
    private List<OpePartDraftQcTemplate> deleteOpePartQcTemplates(SaveQcTemplateEnter enter) {
        QueryWrapper<OpePartDraftQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartDraftQcTemplate.COL_PART_DRAFT_ID, enter.getId());
        List<OpePartDraftQcTemplate> partQcTemplateList = opePartDraftQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isNotEmpty(partQcTemplateList)) {
            Set<Long> partQcTemplateIds = Sets.newHashSet();
            partQcTemplateList.forEach(item -> {
                partQcTemplateIds.add(item.getId());
            });

            //查询质检结果项
//            QueryWrapper<OpePartDraftQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
//            opePartQcTemplateBQueryWrapper.in(OpePartDraftQcTemplateB.COL_PART_DRAFT_QC_TEMPLATE_ID, new ArrayList<>(partQcTemplateIds));
//            List<OpePartDraftQcTemplateB> templateBList = opePartDraftQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
//            if (CollectionUtils.isNotEmpty(templateBList)) {
//                opePartDraftQcTemplateBService.removeByIds(templateBList.stream().map(OpePartDraftQcTemplateB::getId).collect(Collectors.toList()));
//            }
            QueryWrapper<OpePartDraftQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
            opePartQcTemplateBQueryWrapper.in(OpePartDraftQcTemplateB.COL_PART_DRAFT_QC_TEMPLATE_ID, new ArrayList<>(partQcTemplateIds));
            opePartDraftQcTemplateBService.remove(opePartQcTemplateBQueryWrapper);

            opePartDraftQcTemplateService.removeByIds(partQcTemplateIds);
        }
        return partQcTemplateList;
    }

    /**
     * 封装QC质检项模板
     *
     * @param enter
     * @param saveOpePartQcTemplateList
     * @param saveOpePartQcTemplateBList
     * @param qcResultEnterMap
     * @param partQcTemplateList
     */
    private void buildPartQcTemplate(SaveQcTemplateEnter enter, List<OpePartDraftQcTemplate> saveOpePartQcTemplateList, List<OpePartDraftQcTemplateB> saveOpePartQcTemplateBList,
                                     Map<QcItemTemplateEnter,
                                             List<QcResultEnter>> qcResultEnterMap, List<OpePartDraftQcTemplate> partQcTemplateList) {
        if (CollectionUtils.isNotEmpty(partQcTemplateList)) {
            for (Map.Entry<QcItemTemplateEnter, List<QcResultEnter>> entry : qcResultEnterMap.entrySet()) {
                QcItemTemplateEnter key = entry.getKey();
                List<QcResultEnter> value = entry.getValue();
                Long templateId = idAppService.getId(SequenceName.OPE_PART_DRAFT_QC_TEMPLATE);
                if (key.getId() == null || key.getId() == 0) {
                    saveOpePartQcTemplateList.add(
                            buildOpePartTemplate(enter, key.getQcItemName(), templateId, null, QcSourceTypeEnums.MANUAL_ENTRY.getValue())
                    );
                    value.forEach(item -> {
                        saveOpePartQcTemplateBList.add(
                                buildPartTemplateB(enter, templateId, item)
                        );
                    });
                    break;
                }
                partQcTemplateList.forEach(template -> {
                    if (template.getId().equals(key.getId())) {
                        saveOpePartQcTemplateList.add(
                                buildOpePartTemplate(enter, key.getQcItemName(), templateId, template.getImportExcelBatchNo(), template.getSourceType())
                        );

                        value.forEach(item -> {
                            saveOpePartQcTemplateBList.add(buildPartTemplateB(enter, templateId, item));
                        });
                    }
                });
            }

        } else {
            qcResultEnterMap.forEach((key, value) -> {
                Long templateId = idAppService.getId(SequenceName.OPE_PART_DRAFT_QC_TEMPLATE);
                saveOpePartQcTemplateList.add(
                        buildOpePartTemplate(enter, key.getQcItemName(), templateId, null, QcSourceTypeEnums.MANUAL_ENTRY.getValue())

                );
                value.forEach(item -> {
                    saveOpePartQcTemplateBList.add(
                            buildPartTemplateB(enter, templateId, item)
                    );
                });
            });
        }
    }

    private OpePartDraftQcTemplateB buildPartTemplateB(SaveQcTemplateEnter enter, Long templateId, QcResultEnter item) {
        return OpePartDraftQcTemplateB.builder()
                .id(idAppService.getId(SequenceName.OPE_PART_DRAFT_QC_TEMPLATE_B))
                .dr(0)
                .partDraftQcTemplateId(templateId)
                .qcResult(item.getResult())
                .uploadFlag(item.getUploadPictureFalg())
                .resultsSequence(item.getResultSequence())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpePartDraftQcTemplate buildOpePartTemplate(SaveQcTemplateEnter enter, String qcItemName, Long templateId, String importExcelBatchNo, String sourceType) {
        return OpePartDraftQcTemplate.builder()
                .id(templateId)
                .dr(0)
                .partDraftId(enter.getId())
                .importExcelBatchNo(importExcelBatchNo)
                .sourceType(sourceType)
                .qcItemName(qcItemName)
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * QC 质检模板入参校验
     *
     * @param enter
     * @param qcResultEnterMap
     */
    private void checkParameterEnter(SaveQcTemplateEnter enter, Map<QcItemTemplateEnter, List<QcResultEnter>> qcResultEnterMap) {
        if (qcResultEnterMap.containsKey(null) || qcResultEnterMap.containsValue(null)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //入参校验
        qcResultEnterMap.forEach((key, value) -> {
            //质检项校验
            if (StringUtils.isBlank(key.getQcItemName())) {
                throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_ITEMNAME_IS_EMPTY.getMessage());
            }

            value.forEach(item -> {
                if (StringUtils.isBlank(item.getResult())) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getCode(), ExceptionCodeEnums.TEMPLATE_QC_RESULT_IS_EMPTY.getMessage());
                }
                if (item.getUploadPictureFalg() == null || item.getUploadPictureFalg().equals("")) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY.getMessage());
                }
                if (item.getResultSequence() == 0 || item.getResultSequence() == null) {
                    throw new SesWebRosException(ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getCode(),
                            ExceptionCodeEnums.TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY.getMessage());
                }
            });
        });

        //部品验证

        OpePartsDraft opePartsDraft = opePartsDraftService.getById(enter.getId());
        if (opePartsDraft == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
    }
}
