package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 批量根据scooterId查询ECU仪表数据
     * @param scooterIds
     * @return java.util.List<com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO>
     * @author assert
     * @date 2020/11/30
    */
    List<ScooterEcuDTO> batchGetScooterEcuByScooterId(@Param("scooterIds") List<Long> scooterIds);

}
