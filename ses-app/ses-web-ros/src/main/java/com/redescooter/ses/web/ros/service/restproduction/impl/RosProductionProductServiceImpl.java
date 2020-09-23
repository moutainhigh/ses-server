package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.ClassTypeEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionSecResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosProuductionTypeEnter;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RosProductionProductServiceImpl implements RosServProductionProductService {

    /**
     * 状态统计
     * 
     * @param enter
     * @return
     */
    @Override
    public Map<Integer, Integer> countByType(StringEnter enter) {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(ClassTypeEnums.TYPE_ONE.getValue(), 1);
        result.put(ClassTypeEnums.TYPE_TWO.getValue(), 2);
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
        result.add(new BaseNameResult(1000000L, "你猜"));
        result.add(new BaseNameResult(100002L, "你猜"));
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
        result.add(new BaseNameResult(1000000L, "你猜"));
        result.add(new BaseNameResult(100002L, "你猜"));
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

        return PageResult.create(enter, 1,
            Lists.newArrayList(RosProductionScooterListResult.builder().id(100000000L).groupId(1000000L).groupName("你猜")
                .productNum("你猜").enName("你擦").cnName("你猜").frName("你次").qty(11).productionCycle(20).build()));
    }

    /**
     * 组合列表
     * 
     * @param enter
     * @return
     */
    @Override
    public PageResult<RosProductionCombinationListResult> combinationList(RosProductionCombinationListEnter enter) {
        return PageResult.create(enter, 1, Lists.newArrayList(RosProductionCombinationListResult.builder()
            .id(100000000L).partsId(1000000L).enName("你猜").partsN("你猜").sec("FOA").enName("你擦").cnName("你猜")
            .frName("你次").qty(11).productionCycle(20)
            .partsList(Lists.newArrayList(RosProductionCombinationPartsResult.builder().id(100000000L).partsId(1000000L)
                .enName("你猜").partsN("你猜").sec("FOA").enName("你擦").cnName("你猜").frName("你次").qty(11).build()))
            .build()));
    }

    /**
     * 校验生效时间是否合理
     * 
     * @param enter
     * @return
     */
    @Override
    public BooleanResult checkEffectiveDate(BaseTimeParmEnter enter) {
        return new BooleanResult(Boolean.TRUE);
    }

    /**
     * excel 导入
     * 
     * @param enter
     * @param file
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

        return Lists.newArrayList(new RosProductionSecResult(10000000L, "F01", "F01", "描述"));
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
