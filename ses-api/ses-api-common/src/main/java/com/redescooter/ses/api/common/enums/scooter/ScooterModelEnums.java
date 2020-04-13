package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  @author: alex
 *  @Date: 2020/2/13 10:39
 *  @version：V 1.2
 *  @Description: 车辆型号
 */
@AllArgsConstructor
@Getter
public enum ScooterModelEnums {

    SCOOTER_25_CC("25","25CC","1"),
    SCOOTER_50_CC("50","50CC","2"),
    SCOOTER_125_CC("125","125CC","3"),
    ;

    private String code;

    private String message;

    private String value;
}
