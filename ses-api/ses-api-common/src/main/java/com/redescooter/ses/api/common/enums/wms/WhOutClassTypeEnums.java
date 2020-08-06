package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WhOutTypeEnums
 * @description: WhOutTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/21 14:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WhOutClassTypeEnums {
    TODO("TODO", "待办事项", "1"),
    HISTORY("HISTORY", "历史", "2");

    private String code;

    private String message;

    private String value;

    public static String checkCode(String value) {
        for (WhOutClassTypeEnums item : WhOutClassTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static WhOutClassTypeEnums getEnumByValue(String value) {
        for (WhOutClassTypeEnums item : WhOutClassTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
