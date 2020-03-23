package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PurchasingTypeEnums
 * @description: PurchasingTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:33
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PurchasingTypeEnums {

    TODO("TODO", "待办事项", "1"),
    HISTORY("HISTORY", "历史", "2");

    private String code;

    private String message;

    private String value;

    public static String checkCode(String value) {
        for (PurchasingTypeEnums item : PurchasingTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static PurchasingTypeEnums getEnumByValue(String value) {
        for (PurchasingTypeEnums item : PurchasingTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
