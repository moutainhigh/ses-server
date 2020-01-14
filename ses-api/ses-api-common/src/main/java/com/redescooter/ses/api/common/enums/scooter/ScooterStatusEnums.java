package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: ScooterStatus
 * @desc
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ScooterStatusEnums {
    //可用的
    AVAILABLE("AVAILABLE", "AVAILABLE", "1"),
    //充电中
    CHARGING("CHARGING", "CHARGING", "2"),
    //维修中
    REPAIR("REPAIR", "REPAIR", "3"),
    //故障损坏
    FAULT("FAULT", "FAULT", "4"),
    //使用中
    USEING("USEING", "USEING", "5");

    private String code;

    private String message;

    private String value;

    public static ScooterStatusEnums getScooterStatus(String code) {
        for (ScooterStatusEnums item : ScooterStatusEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
