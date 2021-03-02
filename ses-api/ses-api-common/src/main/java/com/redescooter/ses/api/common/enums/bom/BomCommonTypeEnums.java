package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:BomTypeEnums
 * @description: BomCommonTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 14:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BomCommonTypeEnums {

    /********BOM及产品通用使用********/
    PARTS("Parts", "零部件", "1"),
    ACCESSORY("Accessory", "配件", "2"),
    BATTERY("Battery", "电池", "3"),

    /********产品创建时使用********/
    SCOOTER("Scooter", "整车", "4"),
    COMBINATION("Combination", "组合", "5"),
    ECU_METER("Ecu_Meter", "ECU仪表", "6");

    private String code;

    private String message;

    private String value;

    public static String checkCode(String code) {
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

    public static String getValueByCode(String code) {
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static String getCodeByValue(String value) {
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getCode();
            }
        }
        return null;
    }

    public static BomCommonTypeEnums getEnumsByValue(String value) {
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getEnumsByValue("parts").getCode());;
    }

}
