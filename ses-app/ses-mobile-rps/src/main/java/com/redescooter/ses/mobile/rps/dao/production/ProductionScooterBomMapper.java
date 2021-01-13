package com.redescooter.ses.mobile.rps.dao.production;

import com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom;

/**
 * 车辆bom Mapper接口
 * @author assert
 * @date 2021/1/13 11:26
 */
public interface ProductionScooterBomMapper {

    /**
     * 根据id查询车辆bom信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeProductionScooterBom
     * @author assert
     * @date 2021/1/13
    */
    OpeProductionScooterBom getScooterBomById(Long id);

}
