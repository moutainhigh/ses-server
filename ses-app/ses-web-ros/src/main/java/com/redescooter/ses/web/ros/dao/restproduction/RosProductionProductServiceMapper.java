package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.api.common.vo.base.BaseNameResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * @param activeStatus
     * @param toBeActive
     * @return
     */
    int scooterBomListCount(@Param("enter") RosProductionScooterListEnter enter,
        @Param("activeStatus") Integer activeStatus, @Param("toBeActive") Integer toBeActive);

    /**
     * 车辆列表
     *
     * @param enter
     * @param active
     * @param toBeActiveValue
     * @return
     */
    List<RosProductionScooterListResult> scooterBomList(@Param("enter") RosProductionScooterListEnter enter,
        @Param("activeStatus") Integer active, @Param("toBeActive") Integer toBeActiveValue);

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
     * @param active
     * @param toBeActiveValue
     * @return
     */
    int combinationListCount(@Param("enter") RosProductionCombinationListEnter enter, @Param("active") Integer active,
        @Param("toBeActiveValue") Integer toBeActiveValue);

    /**
     * 组合列表
     *
     * @param enter
     * @param active
     * @param toBeActiveValue
     * @return
     */
    List<RosProductionCombinationListResult> combinationList(@Param("enter") RosProductionCombinationListEnter enter,
        @Param("active") Integer active, @Param("toBeActiveValue") Integer toBeActiveValue);

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
     * 查询组合版本号
     *
     * @param bomNo
     * @return
     */
    List<RosProductionProdductVersionResult> productionCombinBomVersionList(String bomNo);

    /**
     * 产品部件列表
     *
     * @param id
     * @param productTypeId
     * @return
     */
    List<RosProductionProductPartListResult> rosProductionProductPartsList(@Param("id") Long id,
        @Param("productTypeId") Integer productTypeId);

    /**
     * 导入成功后查询部件信息
     *
     * @param collect
     * @return
     */
    List<RosProductionProductPartListResult> rosImportProductionProductPartsList(List<String> collect);

    /**
     * 分组列表
     *
     * @param generalEnter
     * @return
     */
    List<BaseNameResult> groupList(GeneralEnter generalEnter);

    /**
     * 颜色列表
     * 
     * @param enter
     * @return
     */
    List<BaseNameResult> colorList(GeneralEnter enter);

    /**
     * @Author Aleks
     * @Description  查找所有已发布的【生效中】状态的Combination的英文名称
     * @Date  2020/10/20 13:35
     * @Param []
     * @return
     **/
    List<CombinNameData> combinNameData(@Param("enter")CombinNameEnter enter);

    /**
     * @Author Aleks
     * @Description 与组装件名称相关联的BOM编号
     * @Date  2020/10/20 13:41
     * @Param [enter]
     * @return
     **/
    List<BomNameData> bomNoData(@Param("enter")BomNoEnter enter);


    /**
     * @Author Aleks
     * @Description  通过分组和颜色找整车
     * @Date  2020/11/4 15:03
     * @Param [listMap]
     * @return
     **/
    List<OpeProductionScooterBom> getByGroupAndColorIds(List<Map<String,Object>> listMap);


    /**
     * @Author Aleks
     * @Description  查找所Combination的中文名称
     * @Date  2020/10/20 13:35
     * @Param []
     * @return
     **/
    List<CombinNameData> combinCnNameData(@Param("enter")CombinNameEnter enter);


    /**
     * @Author Aleks
     * @Description 根据中文名称查bom数据
     * @Date  2020/10/20 13:41
     * @Param [enter]
     * @return
     **/
    List<BomNameData> cnBomNoData(@Param("enter")BomNoEnter enter);
}
