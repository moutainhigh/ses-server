package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成品库车辆库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:57
 */
public interface WmsScooterStockMapper {

    /**
     * 修改成品库车辆库存信息
     * @param opeWmsScooterStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsScooterStock(OpeWmsScooterStock opeWmsScooterStock);

    /**
     * 根据groupId、colorId查询成品库车辆库存信息
     * @param groupId
     * @param colorId
     * @return com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock
     * @author assert
     * @date 2021/1/14
    */
    OpeWmsScooterStock getWmsScooterStockByGroupIdAndColorId(@Param("groupId") Long groupId,
                                                             @Param("colorId") Long colorId);

    /**
     * 批量修改成品库车辆库存信息
     * @param opeWmsScooterStockList
     * @return int
     * @author assert
     * @date 2021/1/22
    */
    int batchUpdateWmsScooterStock(@Param("opeWmsScooterStockList") List<OpeWmsScooterStock> opeWmsScooterStockList);

    /**
     * 批量新增成品库车辆库存信息
     * @param opeWmsScooterStockList
     * @return int
     * @author assert
     * @date 2021/1/29
    */
    int batchInsertWmsScooterStock(@Param("opeWmsScooterStockList") List<OpeWmsScooterStock> opeWmsScooterStockList);

    /**
     * 根据bomId查询成品库车辆id
     * @param bomId
     * @return java.lang.Long
     * @author assert
     * @date 2021/1/29
    */
    Long getWmsScooterStockIdByBomId(Long bomId);

}
