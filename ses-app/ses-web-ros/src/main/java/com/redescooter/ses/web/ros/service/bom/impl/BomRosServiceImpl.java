package com.redescooter.ses.web.ros.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomAssTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.PartsEventEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpePartsAssembly;
import com.redescooter.ses.web.ros.dm.OpePartsAssemblyB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.bom.BomRosService;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyBService;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.vo.bom.ProdoctPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationDetailResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.combination.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    private OpePartsAssemblyBService opePartsAssemblyBService;

    @Autowired
    private OpePartsAssemblyService opePartsAssemblyService;

    @Autowired
    private OpePartsService opePartsService;

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
        List<OpePartsAssemblyB> opePartsAssemblyList = new ArrayList<>();
        int partAllQty = 0;

        OpePartsAssembly opePartsAssembly = OpePartsAssembly.builder()
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .status(BomStatusEnums.NORMAL.getValue())
                .assNumber(enter.getProductN())
                .cnName(enter.getProductName())
                .frName(enter.getProductName())
                .enName(enter.getProductName())
                .productionCycle(enter.getProcurementCycle())
                .assType(Integer.valueOf(BomAssTypeEnums.SCOOTER.getValue()))
                .note(null)
                .revision(0)
                .build();

        if (enter.getId() == null || enter.getId() == 0) {
            //保存
            Long assemblyId = idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY);
            // 产品编号过滤
            List<String> productNList = bomRosServiceMapper.UsingProductNumList(enter);
            if (productNList.contains(enter.getProductN())) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
            }
            //子表都保存
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {

                    OpePartsAssemblyB opePartsAssemblyB = buildOpePartsAssemblyBSingle(enter.getUserId(), assemblyId, item);
                    opePartsAssemblyB.setId(idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY_B));
                    opePartsAssemblyB.setPartsId(item.getId());
                    opePartsAssemblyB.setCreatedBy(enter.getUserId());
                    opePartsAssemblyB.setCreatedTime(new Date());

                    opePartsAssemblyList.add(opePartsAssemblyB);
                    partAllQty += item.getQty();
                }
            }
            opePartsAssembly.setId(assemblyId);
            opePartsAssembly.setCreatedBy(enter.getUserId());
            opePartsAssembly.setCreatedTime(new Date());
        } else {
            // 修改
            OpePartsAssembly partsAssembly = opePartsAssemblyService.getById(enter.getId());
            if (partsAssembly == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            //产品编号一致 跳过校验 ，不一致重新校验
            if (!StringUtils.equals(partsAssembly.getAssNumber(), enter.getProductN())) {
                List<String> productNList = bomRosServiceMapper.UsingProductNumList(enter);
                if (productNList.contains(enter.getProductN())) {
                    throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
                }
            }
            // 进行 产品条目数据过滤
            checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.UPDATE.getValue(), partList, null);

            // 子表修改
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsAssemblyB opePartsAssemblyB = buildOpePartsAssemblyBSingle(enter.getUserId(), enter.getId(), item);
                    opePartsAssemblyB.setCreatedBy(enter.getUserId());
                    opePartsAssemblyB.setCreatedTime(new Date());
                    opePartsAssemblyB.setId(item.getId());
                    opePartsAssemblyList.add(opePartsAssemblyB);
                    partAllQty += item.getQty();
                }
            }
            opePartsAssembly.setId(enter.getId());
        }
        opePartsAssembly.setInQty(partAllQty);
        opePartsAssembly.setUpdatedBy(enter.getUserId());
        opePartsAssembly.setUpdatedTime(new Date());

        opePartsAssemblyBService.saveOrUpdateBatch(opePartsAssemblyList);

        opePartsAssemblyService.saveOrUpdate(opePartsAssembly);
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
     * @desc: 详情部件列表查询
     * @param: SaveScooterPartListEnter
     * @retrn: SaveScooterPartListResult
     * @auther: alex
     * @date: 2020/2/25 12:43
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<QueryPartListResult> partList(QueryPartListEnter enter) {
        int count = bomRosServiceMapper.partListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, bomRosServiceMapper.partList(enter));
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
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        List<QueryPartListResult> partList = bomRosServiceMapper.productDeatilPartList(enter.getId());
        ScooterDetailResult scooterDetailResult = ScooterDetailResult.builder()
                .id(scooter.getId())
                .productN(scooter.getAssNumber())
                .productCnName(scooter.getCnName())
                .procurementCycle(StringUtils.isBlank(scooter.getProductionCycle()) == true ? "0" : scooter.getProductionCycle())
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
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!StringUtils.equals(scooter.getAssType().toString(), BomAssTypeEnums.SCOOTER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 进行 产品条目数据过滤
        checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.DELETE.getValue(), null, enter.getIds());

        //数据删除
        opePartsAssemblyBService.removeByIds(enter.getIds());
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
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!StringUtils.equals(scooter.getAssType().toString(), BomAssTypeEnums.SCOOTER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 配件删除
        if (scooter.getInQty() != 0) {
            QueryWrapper<OpePartsAssemblyB> opePartsAssemblyBQueryWrapper = new QueryWrapper<>();
            opePartsAssemblyBQueryWrapper.eq(OpePartsAssemblyB.COL_PARTS_ASSEMBLY_ID, scooter.getId());
            opePartsAssemblyBQueryWrapper.eq(OpePartsAssemblyB.COL_DR, 0);
            opePartsAssemblyBService.remove(opePartsAssemblyBQueryWrapper);
        }

        // 整车删除
        opePartsAssemblyService.removeById(enter.getId());
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
        OpePartsAssembly scooter = opePartsAssemblyService.getById(enter.getId());
        if (scooter == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!StringUtils.equals(scooter.getAssType().toString(), BomAssTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        return bomRosServiceMapper.productDeatilPartList(enter.getId());
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
        OpePartsAssembly combination = opePartsAssemblyService.getById(enter.getId());
        if (combination == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        List<QueryPartListResult> partList = bomRosServiceMapper.productDeatilPartList(enter.getId());
        CombinationDetailResult combinationDetailResult = CombinationDetailResult.builder()
                .id(combination.getId())
                .productN(combination.getAssNumber())
                .productCnName(combination.getCnName())
                .productEnName(combination.getEnName())
                .productFrName(combination.getFrName())
                .qty(combination.getInQty())
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
        OpePartsAssembly combinationPart = opePartsAssemblyService.getById(enter.getId());
        if (combinationPart == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!StringUtils.equals(combinationPart.getAssType().toString(), BomAssTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 进行 产品条目数据过滤
        checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.DELETE.getValue(), null, enter.getIds());

        //数据删除
        opePartsAssemblyBService.removeByIds(enter.getIds());
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
        OpePartsAssembly combination = opePartsAssemblyService.getById(enter.getId());
        if (combination == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 产品类型过滤
        if (!StringUtils.equals(combination.getAssType().toString(), BomAssTypeEnums.COMBINATION.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 配件删除
        if (combination.getInQty() != 0) {
            QueryWrapper<OpePartsAssemblyB> opePartsAssemblyBQueryWrapper = new QueryWrapper<>();
            opePartsAssemblyBQueryWrapper.eq(OpePartsAssemblyB.COL_PARTS_ASSEMBLY_ID, combination.getId());
            opePartsAssemblyBQueryWrapper.eq(OpePartsAssemblyB.COL_DR, 0);
            opePartsAssemblyBService.remove(opePartsAssemblyBQueryWrapper);
        }

        // 整车删除
        opePartsAssemblyService.removeById(enter.getId());
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
        // 配件过滤
        List<Long> partIds = new ArrayList<>();
        partList.forEach(item -> {
            partIds.add(item.getId());
        });
        Collection<OpeParts> opePartList = opePartsService.listByIds(partIds);
        if (CollectionUtils.isEmpty(opePartList) || opePartList.size() != partIds.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        int partAllQty = 0;
        List<OpePartsAssemblyB> opePartsAssemblyList = new ArrayList<>();

        OpePartsAssembly opePartsAssembly = OpePartsAssembly.builder()
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .status(BomStatusEnums.NORMAL.getValue())
                .assNumber(enter.getProductN())
                .cnName(enter.getProductCnName())
                .frName(enter.getProductFrName())
                .enName(enter.getProductEnName())
                .productionCycle(null)
                .assType(Integer.valueOf(BomAssTypeEnums.COMBINATION.getValue()))
                .note(null)
                .revision(0)
                .build();

        if (enter.getId() == null || enter.getId() == 0) {
            // 产品编号过滤
            List<String> productNList = bomRosServiceMapper.UsingProductNumList(enter);
            if (productNList.contains(enter.getProductN())) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
            }
            Long assemblyId = idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY);

            //子表都保存
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsAssemblyB opePartsAssemblyB = buildOpePartsAssemblyBSingle(enter.getUserId(), assemblyId, item);
                    opePartsAssemblyB.setId(idAppService.getId(SequenceName.OPE_PARTS_ASSEMBLY_B));
                    opePartsAssemblyB.setPartsId(item.getId());
                    opePartsAssemblyB.setCreatedBy(enter.getUserId());
                    opePartsAssemblyB.setCreatedTime(new Date());

                    opePartsAssemblyList.add(opePartsAssemblyB);
                    partAllQty += item.getQty();
                }
            }
            opePartsAssembly.setId(assemblyId);
            opePartsAssembly.setCreatedBy(enter.getUserId());
            opePartsAssembly.setCreatedTime(new Date());
        } else {
            // 修改
            // 修改
            OpePartsAssembly partsAssembly = opePartsAssemblyService.getById(enter.getId());
            if (partsAssembly == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
            }
            //产品编号一致 跳过校验 ，不一致重新校验
            if (!StringUtils.equals(partsAssembly.getAssNumber(), enter.getProductN())) {
                List<String> productNList = bomRosServiceMapper.UsingProductNumList(enter);
                if (productNList.contains(enter.getProductN())) {
                    throw new SesWebRosException(ExceptionCodeEnums.PRODUCTN_IS_EXIST.getCode(), ExceptionCodeEnums.PRODUCTN_IS_EXIST.getMessage());
                }
            }

            // 进行 产品条目数据过滤
            checkProductEntry(enter.getId(), enter.getUserId(), PartsEventEnums.UPDATE.getValue(), partList, null);
            // 子表修改
            if (CollectionUtils.isNotEmpty(partList)) {
                for (ProdoctPartListEnter item : partList) {
                    OpePartsAssemblyB opePartsAssemblyB = buildOpePartsAssemblyBSingle(enter.getUserId(), enter.getId(), item);
                    opePartsAssemblyB.setCreatedBy(enter.getUserId());
                    opePartsAssemblyB.setCreatedTime(new Date());
                    opePartsAssemblyB.setId(item.getId());
                    opePartsAssemblyList.add(opePartsAssemblyB);
                    partAllQty += item.getQty();
                }
            }
            opePartsAssembly.setId(enter.getId());
        }
        opePartsAssembly.setInQty(partAllQty);
        opePartsAssembly.setUpdatedBy(enter.getUserId());
        opePartsAssembly.setUpdatedTime(new Date());

        // 子表数据保存
        opePartsAssemblyBService.saveOrUpdateBatch(opePartsAssemblyList);

        // 主表数据保存
        opePartsAssemblyService.saveOrUpdate(opePartsAssembly);
        return new GeneralResult(enter.getRequestId());
    }

    private OpePartsAssemblyB buildOpePartsAssemblyBSingle(Long userId, Long assemblyId, ProdoctPartListEnter item) {
        return OpePartsAssemblyB.builder()
                .dr(0)
                .tenantId(0L)
                .userId(userId)
                .status(BomStatusEnums.NORMAL.getValue())
                .partsAssemblyId(assemblyId)
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
        if (StringUtils.equals(event, PartsEventEnums.DELETE.getValue())) {
            // 查询整车配件
            List<QueryPartListResult> productPartList = bomRosServiceMapper.productDeatilPartList(productId);
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
        if (StringUtils.equals(event, PartsEventEnums.UPDATE.getValue())) {
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
            partList.forEach(item -> {
                if (!opePartsIdList.contains(item.getId())) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }
    }
}
