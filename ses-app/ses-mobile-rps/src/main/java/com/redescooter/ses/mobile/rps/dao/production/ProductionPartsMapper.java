package com.redescooter.ses.mobile.rps.dao.production;

import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;

/**
 * 部件 Mapper接口
 * @author assert
 * @date 2021/1/13 11:25
 */
public interface ProductionPartsMapper {

    /**
     * 根据partsNo查询部件信息
     * @param partsNo
     * @return com.redescooter.ses.mobile.rps.dm.OpeProductionParts
     * @author assert
     * @date 2021/1/13
    */
    OpeProductionParts getProductionPartsByPartsNo(String partsNo);

}
