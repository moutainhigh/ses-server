package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.scooter.SyncScooterEcuDataDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;

import java.util.List;

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

    /**
     * 同步车辆仪表ECU数据
     * @param syncScooterEcuData
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int syncScooterEcuData(SyncScooterEcuDataDTO syncScooterEcuData);

    /**
     * 根据tabletSnList查询车辆仪表ECU数据
     * @param tabletSnList
     * @return java.util.List<com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO>
     * @author assert
     * @date 2020/12/27
    */
    List<ScooterEcuDTO> getScooterEcuByTabletSnList(List<String> tabletSnList);

}
