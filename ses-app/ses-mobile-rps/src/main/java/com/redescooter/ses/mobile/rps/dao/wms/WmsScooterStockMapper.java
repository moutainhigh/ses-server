package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import org.apache.ibatis.annotations.Param;

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

}
