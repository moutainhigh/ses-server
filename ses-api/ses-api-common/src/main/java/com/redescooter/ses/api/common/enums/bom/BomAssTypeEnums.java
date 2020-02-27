package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName BomAssTypeEnums
 * @Author Jerry
 * @date 2020/02/27 10:50
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BomAssTypeEnums {

    SCOOTER("SCOOTER", "SCOOTER", "1"),
    COMBINATION("COMBINATION", "COMBINATION", "2"),
    ;

    private String code;

    private String message;

    private String value;

    public static String checkCode(String code) {
        for (BomAssTypeEnums item : BomAssTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }
}
