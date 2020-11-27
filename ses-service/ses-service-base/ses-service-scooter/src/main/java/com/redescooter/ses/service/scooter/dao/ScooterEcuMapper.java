package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;

/**
 * @author assert
 * @date 2020/11/23 16:39
 */
public interface ScooterEcuMapper {

    int updateScooterEcu(ScooterEcuDTO scooterEcu);

    int insertScooterEcu(ScooterEcuDTO scooterEcu);

    /**
     * 根据车辆编号查询车辆ECU仪表数据
     * @param serialNumber
     * @return com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO
     * @author assert
     * @date 2020/11/20
     */
    ScooterEcuDTO getScooterEcuBySerialNumber(String serialNumber);

}
