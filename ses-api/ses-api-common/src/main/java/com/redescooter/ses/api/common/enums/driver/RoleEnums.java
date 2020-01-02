package com.redescooter.ses.api.common.enums.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: RoleEnums
 * @desc
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleEnums {
    DRIVER("DRIVER", "consumer", "1"),
    MANAGE("MANAGE", "manage", "2");

    private String code;

    private String message;

    private String value;


    public static RoleEnums getErrorCodeByCode(String code) {
        for (RoleEnums item : RoleEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
