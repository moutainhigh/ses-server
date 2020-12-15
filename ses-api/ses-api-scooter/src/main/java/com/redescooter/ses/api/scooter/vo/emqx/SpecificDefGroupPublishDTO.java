package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 规格自定义项分组 -- 用于EMQ X指令下发时使用
 * @author assert
 * @date 2020/12/8 15:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificDefGroupPublishDTO {

    /**
     * 分组名称(电池出厂号/流水号)
     */
    private String batteryBatchNo;

    /**
     * 轮径(直径/cm)
     */
    private String wheelDiameter;

    /**
     * 转速比。
     */
    private String speedRatio;

    /**
     * 放电电流
     */
    private String limitSpeedBos;

    /**
     * 限流
     */
    private String limiting;

    /**
     * 限速(km/h)
     */
    private String speedLimit;

    /**
     * 电量soc红色告警
     */
    private String socRedWarning;

    /**
     * 电量橙色警告
     */
    private String orangeWarning;

    /**
     * 停转SOC
     */
    private String stallSOC;

    /**
     * 停转欠压置SOC为0%
     */
    private String setSOCTo0AtStallUndervoltage;

    /**
     * 停转电压欠压
     */
    private String stallVoltageUndervoltage;

    /**
     * 电压合法识别V – 最小电压
     */
    private String voltageLegalRecognitionMin;

    /**
     * 电压合法识别V – 最大电压
     */
    private String voltageLegalRecognitionMax;

    /**
     * 控制器欠压
     */
    private String controllerUndervoltage;

    /**
     * 控制器欠压回升
     */
    private String controllerUndervoltageRecovery;

}
