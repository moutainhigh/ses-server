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
public enum  BomServiceTypeEnums {

    PART("PART","部品","1"),
    SCOOTER("SCOOTER","整车","2"),
    COMBINATION("COMBINATION","套餐","3");

    private String code;

    private String message;

    private String value;

    public static String checkCode(String code) {
        for (BomServiceTypeEnums item : BomServiceTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

}
