package com.redescooter.ses.admin.dev.constant;

/**
 * 规格自定义项名称常量类
 * @author assert
 * @date 2020/12/15 20:00
 */
public interface SpecificDefNameConstant {

    /**
     * 轮径(直径/cm)
     */
    String WHEEL_DIAMETER = "wheelDiameter";

    /**
     * 转速比。
     */
    String SPEED_RATIO = "speedRatio";

    /**
     * 放电电流
     */
    String LIMIT_SPEED_BOS = "limitSpeedBos";

    /**
     * 限流
     */
    String LIMITING = "limiting";

    /**
     * 限速(km/h)
     */
    String SPEED_LIMIT = "speedLimit";

    /**
     * 电量soc红色告警
     */
    String SOC_RED_WARNING = "socRedWarning";

    /**
     * 电量橙色警告
     */
    String ORANGE_WARNING = "orangeWarning";

    /**
     * 停转SOC
     */
    String STALL_SOC = "stallSOC";

    /**
     * 停转欠压置SOC为0%
     */
    String SET_SOC_TO_0_AT_STALL_UNDER_VOLTAGE = "setSOCTo0AtStallUndervoltage";

    /**
     * 停转电压欠压
     */
    String STALL_VOLTAGE_UNDER_VOLTAGE = "stallVoltageUndervoltage";

    /**
     * 电压合法识别V – 最小电压
     */
    String VOLTAGE_LEGAL_RECOGNITION_MIN = "voltageLegalRecognitionMin";

    /**
     * 电压合法识别V – 最大电压
     */
    String VOLTAGE_LEGAL_RECOGNITION_MAX = "voltageLegalRecognitionMax";

    /**
     * 控制器欠压
     */
    String CONTROLLER_UNDER_VOLTAGE = "controllerUndervoltage";

    /**
     * 控制器欠压回升
     */
    String CONTROLLER_UNDER_VOLTAGE_RECOVERY = "controllerUndervoltageRecovery";

}
