package com.redescooter.ses.api.common.enums.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AddressBureauEnums
 * @description: AddressBureauEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/16 16:00
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AddressBureauEnums {

    PAIRS("1000000", "PARIS", "1"),
    SHANGHAI("1000001", "SHANGHAI", "2");

    public static String checkCode(String code) {
        for (AddressBureauEnums item : AddressBureauEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

    public static String checkValue(String value) {
        for (AddressBureauEnums item : AddressBureauEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static AddressBureauEnums getEnumByValue(String value) {
        for (AddressBureauEnums item : AddressBureauEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static AddressBureauEnums getEnumByCode(String code) {
        for (AddressBureauEnums item : AddressBureauEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }


    private String code;

    private String message;

    private String value;

}
