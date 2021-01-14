package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;

/**
 * 成品库组装件库存 Mapper接口
 * @author assert
 * @date 2021/1/14 15:57
 */
public interface WmsCombinStockMapper {

    /**
     * 修改成品库组装件库存信息
     * @param opeWmsCombinStock
     * @return int
     * @author assert
     * @date 2021/1/14
    */
    int updateWmsCombinStock(OpeWmsCombinStock opeWmsCombinStock);

}
