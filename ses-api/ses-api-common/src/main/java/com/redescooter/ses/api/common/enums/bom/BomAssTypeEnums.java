package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:BomServiceTypeEnums
 * @description: BomServiceTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BomAssTypeEnums {

    SCOOTER("SCOOTER","整车","1"),
    COMBINATION("COMBINATION","组合","2");

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
