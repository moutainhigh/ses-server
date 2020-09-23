package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.ClassTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionProductServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsSec;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBomDraft;
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
    private OpePartsSecService opePartsSecService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<Integer, Integer> countByType(StringEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        // 整车
        if (StringUtils.equals(BomCommonTypeEnums.SCOOTER.getValue(), enter.getSt())) {
            result.put(ClassTypeEnums.TYPE_ONE.getValue(), opeProductionScooterBomDraftService.count());
            result.put(ClassTypeEnums.TYPE_TWO.getValue(),
                opeProductionScooterBomService
                    .count(new LambdaQueryWrapper<OpeProductionScooterBom>().in(OpeProductionScooterBom::getBomStatus,
                        ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())));
        }

        if (StringUtils.equals(BomCommonTypeEnums.COMBINATION.getValue(), enter.getSt())) {
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
    // todo 产品下个版本更新分组数据源后 可更新
    @Override
    public List<BaseNameResult> groupList(GeneralEnter generalEnter) {
        List<BaseNameResult> result = new ArrayList<>();
        result.add(new BaseNameResult(1000000L, "分组筛选"));
        result.add(new BaseNameResult(100002L, "分组筛选"));
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
            BaseNameResult baseNameResult =
                BaseNameResult.builder().id(Long.valueOf(item.getValue())).name(item.getCode()).build();
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

        if (enter.getClassType().equals(ClassTypeEnums.TYPE_ONE.getValue())) {
            QueryWrapper<OpeProductionScooterBomDraft> opeProductionScooterBomDraftQueryWrapper = new QueryWrapper<>();
            opeProductionScooterBomDraftQueryWrapper.eq(OpeProductionScooterBomDraft.COL_COLOR_ID, enter.getColorId());
            opeProductionScooterBomDraftQueryWrapper.eq(OpeProductionScooterBomDraft.COL_GROUP_ID, enter.getGroupId());
            opeProductionScooterBomDraftQueryWrapper.like(OpeProductionScooterBomDraft.COL_BOM_NO, enter.getKeyword());
            opeProductionScooterBomDraftQueryWrapper.like(OpeProductionScooterBomDraft.COL_EN_NAME, enter.getKeyword());
            int count = opeProductionScooterBomDraftService.count(opeProductionScooterBomDraftQueryWrapper);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, rosProductionProductServiceMapper.scooterDraftList(enter));
        }
        if (enter.getClassType().equals(ClassTypeEnums.TYPE_TWO.getValue())) {
            QueryWrapper<OpeProductionScooterBom> opeProductionScooterBomQueryWrapper = new QueryWrapper<>();
            opeProductionScooterBomQueryWrapper.eq(OpeProductionScooterBom.COL_COLOR_ID, enter.getColorId());
            opeProductionScooterBomQueryWrapper.eq(OpeProductionScooterBom.COL_GROUP_ID, enter.getGroupId());
            opeProductionScooterBomQueryWrapper.in(OpeProductionCombinBom.COL_BOM_STATUS,
                ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue());
            opeProductionScooterBomQueryWrapper.like(OpeProductionScooterBom.COL_BOM_NO, enter.getKeyword());
            opeProductionScooterBomQueryWrapper.like(OpeProductionScooterBom.COL_EN_NAME, enter.getKeyword());
            int count = opeProductionScooterBomService.count(opeProductionScooterBomQueryWrapper);
            if (count == 0) {
                if (count == 0) {
                    return PageResult.createZeroRowResult(enter);
                }
                return PageResult.create(enter, count, rosProductionProductServiceMapper.scooterBomList(enter));
            }
        }
        return null;
    }

    /**
     * 组合列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<RosProductionCombinationListResult> combinationList(RosProductionCombinationListEnter enter) {
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

        return PageResult.create(enter, 1,
            Lists.newArrayList(RosProductionProductPartListResult.builder().id(10000L).partsId(100000L)
                .partsNumber("1000000cmsda").sec("FOW").enName("你猜").cnName("你次啊").frName("你次").productionCycle(10)
                .qty(10).dwgUrl("www.baidu.com").build()));
    }

    /**
     * 车辆的保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult rosSaveProductionProduct(RosSaveProductonProductEnter enter) {

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public RosProductionProductDetailResult detail(RosProuductionTypeEnter enter) {
        return RosProductionProductDetailResult.builder().id(10000L).productN("asdadada").groupId(1000000L)
            .groupName("1000000cmsda").colorId(10000000L).colorName("FOW").enName("你次").procurementCycle(10)
            .status("不知道").effectiverDate(new Date())
            .versionList(Lists.newArrayList(new RosProductionProdductVersionResult("FAO", "生效"))).createById(100000L)
            .createByFirstName("amy").createByLastName("amy").build();
    }

    /**
     * 生效
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult takeEffect(IdEnter enter) {
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
    public GeneralResult productionProductDisable(IdEnter enter) {
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
}
