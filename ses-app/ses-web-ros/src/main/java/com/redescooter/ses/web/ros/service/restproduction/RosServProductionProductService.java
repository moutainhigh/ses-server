package com.redescooter.ses.web.ros.service.restproduction;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;

import java.util.List;
import java.util.Map;

public interface RosServProductionProductService {
    /**
     * 状态统计
     * 
     * @param enter
     * @return
     */
    Map<Integer, Integer> countByType(IdEnter enter);

    /**
     * 分组查询
     * 
     * @param generalEnter
     * @return
     */
    List<BaseNameResult> groupList(GeneralEnter generalEnter);

    /**
     * 颜色查询
     * 
     * @param enter
     * @return
     */
    List<BaseNameResult> colorList(GeneralEnter enter);

    /**
     * 车辆列表
     * 
     * @param enter
     * @return
     */
    PageResult<RosProductionScooterListResult> scooterList(RosProductionScooterListEnter enter);

    /**
     * 组合列表
     * 
     * @param enter
     * @return
     */
    PageResult<RosProductionCombinationListResult> combinationList(RosProductionCombinationListEnter enter);

    /**
     * 校验生效时间是否合理
     * 
     * @param enter
     * @return
     */
    BooleanResult checkEffectiveDate(RosProductionTimeParmEnter enter);

    /**
     * excel 导入
     * 
     * @param enter
     * @return
     */
    ImportExcelPartsResult saveScooterImportExcel(StringEnter enter);

    /**
     * 区域列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionSecResult> secList(GeneralEnter enter);

    /**
     * 部件查询列表
     * 
     * @param enter
     * @return
     */
    PageResult<RosProductionProductPartListResult> productionProductPartList(RosProductionProductPartListEnter enter);

    /**
     * 车辆的保存
     * 
     * @param enter
     * @return
     */
    GeneralResult rosSaveProductionProduct(RosSaveProductionProductEnter enter);

    /**
     * 详情
     * 
     * @param enter
     * @return
     */
    RosProductionProductDetailResult detail(RosProuductionTypeEnter enter);

    /**
     * 生效
     * 
     * @param enter
     * @return
     */
    GeneralResult takeEffect(RosProuductionTypeEnter enter);

    /**
     * 产品禁用
     * 
     * @param enter
     * @return
     */
    GeneralResult productionProductDisable(RosProuductionTypeEnter enter);

    /**
     * 发布
     * 
     * @param enter
     * @return
     */
    GeneralResult release(RosProuductionTypeEnter enter);

    /**
     * 删除草稿
     * 
     * @param enter
     * @return
     */
    GeneralResult delete(RosProuductionTypeEnter enter);

}
