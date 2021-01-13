package com.redescooter.ses.mobile.rps.dao.production;

import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;

/**
 * 组装bom Mapper接口
 * @author assert
 * @date 2021/1/13 11:26
 */
public interface ProductionCombinBomMapper {

    /**
     * 根据id查询组装bom信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom
     * @author assert
     * @date 2021/1/13
    */
    OpeProductionCombinBom getCombinBomById(Long id);

}
