package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterAllReportedDTO;

/**
 * 车辆整车数据上报Service
 * @author assert
 * @date 2020/11/23 14:54
 */
public interface ScooterAllReportedService {

    /**
     * 车辆整车数据上报
     * @param scooterAll
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int insertScooterAllInfo(ScooterAllReportedDTO scooterAll);

}
