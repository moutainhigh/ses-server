package com.redescooter.ses.web.ros.dao.wms.cn.china;

import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WmsDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialDetailEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsDetailEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishCombinListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockRecordResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishCombinDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;
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
     * @param id
     * @return
     */
    WmsfinishCombinDetailResult finishCombinDetail(@Param("id") Long id);


    /**
     * 成品库今日入库数量
     * @param date yyyy-mm-dd
     * @return
     */
    Integer finishTodayStackCount(@Param("date")String date,@Param("recordType") Integer recordType);


    /**
     * 法国车辆今日入库/处理数量
     * @param date
     * @param recordType
     * @return
     */
    Integer frTodayScooterInOrOutStockCount(@Param("date")String date,@Param("recordType") Integer recordType);


    /**
     * 法国组装件今日入库/处理数量
     * @param date
     * @param recordType
     * @return
     */
    Integer frTodayCombinInOrOutStockCount(@Param("date")String date,@Param("recordType") Integer recordType);


    //     *******************   下面是法国的仓库    ****************


    /**
     * 成品库车辆list统计
     * @param enter
     * @return
     */
    int frScooterTotalRows(@Param("enter") WmsFinishScooterListEnter enter);

    /**
     * 成品库车辆list
     * @param enter
     * @return
     */
    List<WmsFinishScooterListResult> frScooterList(@Param("enter") WmsFinishScooterListEnter enter);


    /**
     * 成品库组装件list统计
     * @param enter
     * @return
     */
    int frCombinCotalRows(@Param("enter") CombinationListEnter enter);


    /**
     * 成品库组装件list
     * @param enter
     * @return
     */
    List<WmsFinishCombinListResult> frCombinList(@Param("enter") CombinationListEnter enter);

    /**
     * 中国仓库原料库详情
     */
    List<WmsDetailResult> getMaterialPartsDetail(@Param("enter") MaterialDetailEnter enter);

    /**
     * 中国仓库原料库详情count
     */
    int getMaterialPartsDetailCount(@Param("enter") MaterialDetailEnter enter);

    /**
     * 中国仓库成品库车辆和组装件详情
     */
    List<WmsDetailResult> getScooterAndCombinDetail(@Param("enter") WmsDetailEnter enter);

    /**
     * 中国仓库成品库车辆和组装件详情count
     */
    int getScooterAndCombinDetailCount(@Param("enter") WmsDetailEnter enter);

    /**
     * 法国仓库车辆,组装件和部件详情count
     */
    int getDetailCount(@Param("enter") WmsDetailEnter enter);

    /**
     * 法国仓库车辆,组装件和部件详情
     */
    List<WmsDetailResult> getDetail(@Param("enter") WmsDetailEnter enter);

}
