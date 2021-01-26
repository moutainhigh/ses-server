package com.redescooter.ses.mobile.rps.dao.wms;

import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据bomId查询原料库部件库存信息(这里也可以把partsId理解成bomId)
     * @param bomId
     * @return com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock
     * @author assert
     * @date 2021/1/15
    */
    OpeWmsPartsStock getWmsPartsStockByBomId(Long bomId);

    /**
     * 批量修改原料库部件库存信息
     * @param opeWmsPartsStockList
     * @return int
     * @author assert
     * @date 2021/1/22
    */
    int batchUpdateWmsPartsStock(@Param("opeWmsPartsStockList") List<OpeWmsPartsStock> opeWmsPartsStockList);

}
