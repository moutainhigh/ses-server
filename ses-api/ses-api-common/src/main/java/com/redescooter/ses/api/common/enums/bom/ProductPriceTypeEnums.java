package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PriceServiceTypeEnums
 * @description: PriceServiceTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/02 10:29
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductPriceTypeEnums {

    PART("Part", "零部件", "1"),
    BATTERY("Battery", "电池", "2"),
    ACCESSORY("Accessory", "配件", "3"),
    SCOOTER("Scooter", "整车", "4");


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
}
