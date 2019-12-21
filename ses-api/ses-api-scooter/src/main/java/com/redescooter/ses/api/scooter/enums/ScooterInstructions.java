package com.redescooter.ses.api.scooter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: ScooterAction
 * @author: Darren
 * @create: 2019/02/20 10:21
 */
@Getter
@AllArgsConstructor
public enum ScooterInstructions {

    LOCK("LOCKED", "锁车"),
    UNLOCK("UNLOCK", "开锁"),
    NAVIGATION("NAVIGATION", "导航"),
    OBD("OBD", "OBD检测");


    private String code;

    private String message;

    public static ScooterInstructions getMessage(String code) {
        for (ScooterInstructions item : ScooterInstructions.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
