package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuReportedDTO;

/**
 * @author assert
 * @date 2020/11/23 16:34
 */
public interface ScooterMcuService {

    /**
     * 添加车辆MCU(控制器)相关数据--车辆EMQ X上报业务
     * @param scooterReportedMcu
     * @return int
     * @author assert
     * @date 2020/11/20
     */
    int insertScooterMcuByEmqX(ScooterMcuReportedDTO scooterReportedMcu);

}
