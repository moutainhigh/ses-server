package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListResult;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RosProductionProductServiceMapper {
    /**
     * 草稿列表统计
     * 
     * @param enter
     * @return
     */
    int scooterDraftListCount(RosProductionScooterListEnter enter);

    /**
     * 车辆草稿列表
     *
     * @param enter
     * @return
     */
    List<RosProductionScooterListResult> scooterDraftList(RosProductionScooterListEnter enter);

    /**
     * 车辆bom 列表
     * 
     * @param enter
     * @return
     */
    int scooterBomListCount(RosProductionScooterListEnter enter);

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    List<RosProductionScooterListResult> scooterBomList(RosProductionScooterListEnter enter);

    /**
     * 组合草稿列表
     * 
     * @param enter
     * @return
     */
    int combinationDraftListCount(RosProductionCombinationListEnter enter);

    /**
     * 组合草稿列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionCombinationListResult> combinationDraftList(RosProductionCombinationListEnter enter);

    /**
     * 组合列表统计
     * 
     * @param enter
     * @return
     */
    int combinationListCount(RosProductionCombinationListEnter enter);

    /**
     * 组合列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionCombinationListResult> combinationList(RosProductionCombinationListEnter enter);

    /**
     * 部件查询统计
     * 
     * @param enter
     * @return
     */
    int productionProductPartListCount(RosProductionProductPartListEnter enter);

    /**
     * 部件查询列表
     * 
     * @param enter
     * @return
     */
    List<RosProductionProductPartListResult> productionProductPartList(RosProductionProductPartListEnter enter);

    /**
     * 车辆BOM版本列表
     * 
     * @param bomNo
     * @return
     */
    List<RosProductionProdductVersionResult> productionScooterVersionList(String bomNo);

    /**
     * 产品部件列表
     * 
     * @param id
     * @param productTypeId
     * @return
     */
    List<RosProductionProductPartListResult> rosProductionProductPartsList(@Param("id") Long id,
        @Param("productTypeId") Integer productTypeId);
}
