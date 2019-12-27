package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName:ScooterActionType
 * @description: ScooterActionType
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 14:39
 */
@Getter
@AllArgsConstructor
public enum ScooterActionTypeEnums {

    UNLOCK("UNLOCK","开锁","1"),
    LOCK("LOCK","上锁","2"),
    START_NAVIGATION("START_NAVIGATION","开启导航","3"),
    END_NAVIGATION("END_NAVIGATION","结束导航","4"),
    ;


    private String code;

    private String message;

    private String value;
}
