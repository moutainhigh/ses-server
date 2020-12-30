package com.redescooter.ses.web.ros.dao.wms.cn.china;

import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:44
 */
public interface WmsQualifiedMapper {

    /**
     * 不合格品库车辆库存列表统计
     * @param enter
     * @return
     */
    int totalRows(@Param("enter") WmsFinishScooterListEnter enter);


    /**
     * 不合格品库车辆库存列表
     * @param enter
     * @return
     */
    List<WmsQualifiedScooterListResult> scooterList(@Param("enter") WmsFinishScooterListEnter enter);


    /**
     * 不合格品库车辆库存详情
     * @param id
     * @return
     */
    WmsfinishScooterDetailResult scooterDetail(@Param("id") Long id);


    /**
     * 不合格品库组装件库存列表统计
     * @param enter
     * @return
     */
    int combinCotalRows(@Param("enter") CombinationListEnter enter);


    /**
     * 不合格品库组装件库存列表
     * @param enter
     * @return
     */
    List<WmsQualifiedCombinListResult> combinList(@Param("enter") CombinationListEnter enter);


    /**
     * 不合格品库组装件库存详情
     * @param id
     * @return
     */
    WmsfinishCombinDetailResult combinDetail(@Param("id")Long id);


    /**
     * 不合格品库部件列表统计
     * @param enter
     * @return
     */
    int partsCotalRows(@Param("enter") MaterialStockPartsListEnter enter);


    /**
     * 不合格品库部件列表
     * @param enter
     * @return
     */
    List<WmsQualifiedPartsListResult> partsList(@Param("enter") MaterialStockPartsListEnter enter);


    /**
     * 不合格品库部件详情
     * @param id
     * @return
     */
    MaterialpartsStockDetailResult partsDetail(@Param("id")Long id);
}
