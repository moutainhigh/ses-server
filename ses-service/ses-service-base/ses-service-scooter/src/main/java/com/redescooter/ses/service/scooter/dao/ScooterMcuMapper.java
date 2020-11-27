package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuReportedDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/23 17:58
 */
public interface ScooterMcuMapper {

    /**
     * 根据scooterNo和batchNo查询车辆MCU控制器数据
     * @param scooterNo,batchNo
     * @return com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuReportedDTO
     * @author assert
     * @date 2020/11/20
     */
    ScooterMcuReportedDTO getScooterMcuByScooterNoAndBatchNo(@Param("scooterNo") String scooterNo, @Param("batchNo") Long batchNo);

    /**
     * 根据id修改车辆MCU控制器信息
     * @param scooterMcu
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int updateScooterMcuById(ScooterMcuReportedDTO scooterMcu);

    /**
     * 新增车辆MCU控制器信息
     * @param scooterMcu
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int insertScooterMcu(ScooterMcuReportedDTO scooterMcu);

}
