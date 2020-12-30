package com.redescooter.ses.web.ros.dao.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 成品库dao层
 * @author: Aleks
 * @create: 2020/12/28 15:12
 */
public interface WmsFinishStockMapper {

    /**
     * 成品库车辆list统计
     * @param enter
     * @return
     */
    int totalRows(@Param("enter") WmsFinishScooterListEnter enter);

    /**
     * 成品库车辆list
     * @param enter
     * @return
     */
    List<WmsFinishScooterListResult> finishScooterList(@Param("enter") WmsFinishScooterListEnter enter);


    /**
     * 成品库车辆详情
     * @param id
     * @return
     */
    WmsfinishScooterDetailResult finishScooterDetail(@Param("id")Long id);


    /**
     * 入库记录
     * @param id
     * @return
     */
    List<WmsStockRecordResult> inStockRecord(@Param("id")Long id);


    /**
     * 车辆库存的总统计数量
     * @param stockType
     * @return
     */
    WmsStockCountResult wmsScooterStockCount(@Param("stockType") Integer stockType);


    /**
     * 组装件库存的总统计数量
     * @param stockType
     * @return
     */
    WmsStockCountResult wmsCombinStockCount(@Param("stockType") Integer stockType);


    /**
     * 部件库存的总统计数量
     * @param stockType
     * @return
     */
    WmsStockCountResult wmsPartsStockCount(@Param("stockType") Integer stockType);


    /**
     * 成品库组装件list统计
     * @param enter
     * @return
     */
    int combinCotalRows(@Param("enter") CombinationListEnter enter);


    /**
     * 成品库组装件list
     * @param enter
     * @return
     */
    List<WmsFinishCombinListResult> finishCombinList(@Param("enter") CombinationListEnter enter);


    /**
     * 成品库组装件详情
     * @param enter
     * @return
     */
    WmsfinishCombinDetailResult finishCombinDetail(@Param("id") Long id);

}
