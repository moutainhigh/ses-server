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
public enum ScooterLockStatus {

    LOCK("LOCKED", "锁定"),
    UNLOCK("UNLOCK", "已开锁");

    private String code;

    private String message;

    public static ScooterLockStatus getErrorCodeByCode(String code) {
        for (ScooterLockStatus item : ScooterLockStatus.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
