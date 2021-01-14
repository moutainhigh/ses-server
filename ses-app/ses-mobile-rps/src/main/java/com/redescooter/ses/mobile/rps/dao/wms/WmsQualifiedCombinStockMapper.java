package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedCombinStock;

/**
 * 不合格品库组装件库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:58
 */
public interface WmsQualifiedCombinStockMapper {

    /**
     * 修改不合格品库组装件库存信息
     * @param opeWmsQualifiedCombinStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsQualifiedCombinStock(OpeWmsQualifiedCombinStock opeWmsQualifiedCombinStock);

}
