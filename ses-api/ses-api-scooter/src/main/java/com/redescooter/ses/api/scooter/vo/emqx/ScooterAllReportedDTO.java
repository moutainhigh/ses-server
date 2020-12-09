package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

/**
 * 车辆上报整车数据 DTO
 * @author assert
 * @date 2020/11/23 14:23
 */
@Data
public class ScooterAllReportedDTO {

    /**
     * ECU仪表数据
     */
    private ScooterEcuDTO ecu;

    /**
     * BBI电池相关数据
     */
    private ScooterBbiReportedDTO bbi;

    /**
     * MCU控制器数据
     */
    private ScooterMcuReportedDTO mcu;

    /**
     * 车辆锁信息
     */
    private ScooterLockReportedDTO lock;

}
