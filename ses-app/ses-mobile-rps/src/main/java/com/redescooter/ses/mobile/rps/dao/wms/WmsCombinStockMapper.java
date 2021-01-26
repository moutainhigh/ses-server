package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据bomId查询成品库组装件库存信息
     * @param bomId
     * @return com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock
     * @author assert
     * @date 2021/1/15
    */
    OpeWmsCombinStock getWmsCombinStockByBomId(Long bomId);

    /**
     * 批量修改成品库组装件库存信息
     * @param opeWmsCombinStockList
     * @return int
     * @author assert
     * @date 2021/1/22
    */
    int batchUpdateWmsCombinStock(@Param("opeWmsCombinStockList") List<OpeWmsCombinStock> opeWmsCombinStockList);

}
