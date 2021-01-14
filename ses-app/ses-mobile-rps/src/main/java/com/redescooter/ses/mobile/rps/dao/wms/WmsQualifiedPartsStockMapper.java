package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedPartsStock;

/**
 * 不合格品库部件库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:58
 */
public interface WmsQualifiedPartsStockMapper {

    /**
     * 修改不合格品库部件库存信息
     * @param opeWmsQualifiedPartsStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsQualifiedPartsStock(OpeWmsQualifiedPartsStock opeWmsQualifiedPartsStock);

}
