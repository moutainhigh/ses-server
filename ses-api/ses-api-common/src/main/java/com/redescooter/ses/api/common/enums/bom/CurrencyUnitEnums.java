package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CurrencyUnitEnums
 * @description: CurrencyUnitEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:43
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CurrencyUnitEnums {

    FR("FR", "€", "1"),
    EN("EN", "£", "2"),
    //用人民币作为标准单位
    CN("CN", "￥", "3"),
    ;


    private String code;

    private String name;

    private String value;

    public static String checkCode(String code) {
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

    public static String checkValue(String value) {
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static CurrencyUnitEnums getEnumByValue(String value) {
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return CurrencyUnitEnums.FR;
    }

}
