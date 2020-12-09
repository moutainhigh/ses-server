package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;

/**
 * @author assert
 * @date 2020/11/23 16:31
 */
public interface ScooterEcuService {

    /**
     * 添加车辆仪表设备数据--车辆EMQ X上报业务
     * @param scooterEcu
     * @return int
     * @author assert
     * @date 2020/11/20
     */
    int insertScooterEcuByEmqX(ScooterEcuDTO scooterEcu);

}
