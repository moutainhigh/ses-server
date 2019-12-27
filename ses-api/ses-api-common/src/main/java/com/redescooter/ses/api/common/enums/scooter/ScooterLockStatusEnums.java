package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName:ScooterLockStatus
 * @description: ScooterLockStatus
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 17:44
 */
@AllArgsConstructor
@Getter
public enum ScooterLockStatusEnums {

    LOCK("LOCK", "加锁", "1"),
    UNLOCK("UNLOCK", "开锁", "2");

    private String code;

    private String message;

    private String value;
}
