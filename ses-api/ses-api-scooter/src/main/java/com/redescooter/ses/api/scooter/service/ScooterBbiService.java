package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterBbiReportedDTO;

/**
 * @author assert
 * @date 2020/11/23 16:32
 */
public interface ScooterBbiService {

    /**
     * 添加车辆BBI电池相关数据--车辆EMQ X上报业务
     * @param scooterReportedBbi
     * @return int
     * @author assert
     * @date 2020/11/20
     */
    int insertScooterBbiByEmqX(ScooterBbiReportedDTO scooterReportedBbi);

}
