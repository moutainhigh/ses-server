package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: CommonEvent
 * @author: alex
 * @create: 2019/01/22 15:10
 */
@Getter
@AllArgsConstructor
public enum DriverScooterStatusEnums {

    USED("USED", "使用中", "1"),
    FINSH("END", "归还", "2");

    private String code;

    private String message;

    private String value;

    public static DriverScooterStatusEnums getErrorCodeByCode(String code) {
        for (DriverScooterStatusEnums item : DriverScooterStatusEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
