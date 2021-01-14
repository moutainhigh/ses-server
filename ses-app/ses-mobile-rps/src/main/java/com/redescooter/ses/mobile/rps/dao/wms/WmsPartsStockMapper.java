package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;

/**
 * 原料库部件库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:56
 */
public interface WmsPartsStockMapper {

    /**
     * 修改原料库部件库存信息
     * @param opeWmsPartsStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsPartsStock(OpeWmsPartsStock opeWmsPartsStock);

}
