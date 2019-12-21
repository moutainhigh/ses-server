package com.redescooter.ses.api.scooter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: ScooterStatusEnum
 * @author: Darren
 * @create: 2019/01/22 15:10
 */
@Getter
@AllArgsConstructor
public enum ScooterBoxStatusEnum {

    LOCKED("LOCKED", "锁定中"),
    UNLOCKED("UNLOCKED", "已开锁");

    private String code;

    private String message;

    public static ScooterBoxStatusEnum getErrorCodeByCode(String code) {
        for (ScooterBoxStatusEnum item : ScooterBoxStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
