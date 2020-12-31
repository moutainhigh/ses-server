package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuControllerInfoDTO;

/**
 * @author assert
 * @date 2020/11/23 17:59
 */
public interface ScooterMcuControllerInfoMapper {

    /**
     * 修改车辆MCU控制器信息
     * @param mcuControllerInfo
     * @return int
     * @author assert
     * @date 2020/11/20
     */
    int updateMcuControllerByMcuId(ScooterMcuControllerInfoDTO mcuControllerInfo);

    /**
     * 新增车辆MCU控制器信息
     * @param mcuControllerInfo
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int insertMcuController(ScooterMcuControllerInfoDTO mcuControllerInfo);

}
