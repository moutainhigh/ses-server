package com.redescooter.ses.service.scooter.dm.base;

import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2020/11/20 16:36
 */
@Data
public class ScoScooterBbiBatteryWare {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    private Integer dr;

    /**
     * 电池仓位 0：没有接入 1：1号电池仓，以此类推
     */
    private Integer wareNo;

    /**
     * 车辆编号
     */
    private String scooterNo;

    /**
     * 电池回路是否打开 true打开 false未打开
     */
    private Boolean batteryCircuitOpen;

    /**
     * 仓内是否有压差 true有 false无
     */
    private Boolean diffVoltage;

    /**
     * 电池仓电流是否异常 true异常 false正常
     */
    private Boolean electricityBreakdown;

    /**
     * 电池仓是否可用 true是 false否
     */
    private Boolean enable;

    /**
     * 是否放入电池 true是 false否
     */
    private Boolean haveBattery;

    /**
     * Mos是否异常 true是 false否
     */
    private Boolean mosBreakdown;

    /**
     * 电池仓电压是否异常 true异常 false正常
     */
    private Boolean voltageBreakdown;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;
}