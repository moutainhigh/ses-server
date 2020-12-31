package com.redescooter.ses.web.ros.dao.wms.cn.china;

import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialpartsStockDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:原料库dao层
 * @author: Aleks
 * @create: 2020/12/28 15:12
 */
public interface WmsMaterialStockMapper {

    /**
     * 原料库部件列表的统计
     * @param enter
     * @return
     */
    int partsCotalRows(@Param("enter") MaterialStockPartsListEnter enter);


    /**
     * 原料库部件库存列表
     * @param enter
     * @return
     */
    List<MaterialStockPartsListResult> materialPartsList(@Param("enter") MaterialStockPartsListEnter enter);


    /**
     * 原料库部件库存详情
     * @param id
     * @return
     */
    MaterialpartsStockDetailResult materialStockPartsDetail(@Param("id") Long id);


    /**
     * 原料库今日入库数据
     * @param date
     * @return
     */
    Integer materialTodayStockCount(@Param("date")String date,@Param("recordType") Integer recordType);


    /**
     * 法国部件今日的入库/出库数量
     * @param date
     * @param recordType
     * @return
     */
    Integer frTodayPartsInOrOutStockCount(@Param("date")String date,@Param("recordType") Integer recordType);

}
