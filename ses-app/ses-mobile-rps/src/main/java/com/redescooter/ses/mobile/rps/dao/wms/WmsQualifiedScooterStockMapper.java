package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedScooterStock;

/**
 * 不合格品库车辆库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:58
 */
public interface WmsQualifiedScooterStockMapper {

    /**
     * 修改不合格品库车辆库存信息
     * @param opeWmsQualifiedScooterStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsQualifiedScooterStock(OpeWmsQualifiedScooterStock opeWmsQualifiedScooterStock);

}
