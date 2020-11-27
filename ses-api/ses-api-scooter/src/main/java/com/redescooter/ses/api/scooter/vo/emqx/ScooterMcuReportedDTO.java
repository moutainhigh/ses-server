package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.util.Date;

/**
 * 车辆上报MCU控制器信息 DTO
 * @author assert
 * @date 2020/11/20 17:55
 */
@Data
public class ScooterMcuReportedDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 批次号,唯一
     */
    private Long batchNo;

    /**
     * 生产日期
     */
    private Date date;

    /**
     * 出厂号/流水号
     */
    private Long factoryNo;

    /**
     * 控制器功率，单位W。1:500W 2:1000W 3:1500W 以此类推
     */
    private Integer controlPower;

    /**
     * 电机功率，单位W。1:500W 2:1000W 3:1500W 以此类推
     */
    private Integer motorPower;

    /**
     * 电压，精度0.01V
     */
    private Double voltage;

    /**
     * 电机转速，单位r/min
     */
    private Integer motorSpeed;

    /**
     * 母线电流，精度0.01A
     */
    private Double motherI;

    /**
     * 相线电流，精度0.01A
     */
    private Double phaseI;

    /**
     * 电机低温温度
     */
    private Double motorLowT;

    /**
     * 电机高温温度
     */
    private Double motorHighT;

    /**
     * 控制器低温温度
     */
    private Double controlLowT;

    /**
     * 控制器高温温度
     */
    private Double controlHighT;

    /**
     * 左倾角度,范围0-90度
     */
    private Double inclinationLeftAngle;

    /**
     * 右倾角度,范围0-90度
     */
    private Double inclinationRightAngle;

    /**
     * 上坡角度,范围0-90度
     */
    private Double climbingUpAngle;

    /**
     * 下坡角度,范围0-90度
     */
    private Double climbingDownAngle;

    /**
     * 过压值，精度0.01V
     */
    private Double overVoltage;

    /**
     * 欠压值，精度0.01V
     */
    private Double lackVoltage;

    /**
     * 限流值,精度0.01A
     */
    private Double limitI;

    /**
     * 控制器是否工作 true是 false否
     */
    private Boolean controlWork;

    /**
     * 转把是否故障 true是 false否
     */
    private Boolean shifterBreakdown;

    /**
     * 电机霍尔是否故障 true是 false否
     */
    private Boolean motorHallBreakdown;

    /**
     * 控制器是否故障 true是 false否
     */
    private Boolean controlBreakdown;

    /**
     * 是否刹车 true是 false否
     */
    private Boolean brakeBreakdown;

    /**
     * 电机缺相故障 true是 false否
     */
    private Boolean motorLossPhaseBreakdown;

    /**
     * 陀螺仪是否故障 true是 false否
     */
    private Boolean gyroscopeBreakdown;

    /**
     * 是否欠压保护 true是 false否
     */
    private Boolean lackVProtect;

    /**
     * 是否过压保护 true是 false否
     */
    private Boolean overVProtect;

    /**
     * 是否水平
     */
    private Boolean balance;

    /**
     * 是否限速 true是 false否
     */
    private Boolean controlSpeedLimit;

    /**
     * 是否充电 true是 false否
     */
    private Boolean charge;

    /**
     * 0 扭矩 1 功率
     */
    private Integer energy;

    /**
     * 扭矩/功率百分比
     */
    private Integer energyPercent;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

    /**
     * MCU控制器状态信息
     */
    private ScooterMcuControllerInfoDTO controlInfo;

}
