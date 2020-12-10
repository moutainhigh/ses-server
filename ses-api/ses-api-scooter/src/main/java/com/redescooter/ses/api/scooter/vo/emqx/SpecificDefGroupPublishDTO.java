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
     * 分组名称(电池批次号)
     */
    private String batteryBatchNo;

    private String wheelDiameter;

    private String speedRatio;

    private String limitSpeedBos;

    private String limiting;

    private String speedLimit;

    private String socRedWarning;

    private String orangeWarning;

    private String stallSOC;

    private String setSOCTo0AtStallUndervoltage;

    private String stallVoltageUndervoltage;

    private String voltageLegalRecognitionMin;

    private String voltageLegalRecognitionMax;

    private String controllerUndervoltage;

    private String controllerUndervoltageRecovery;

}
