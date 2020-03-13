package com.redescooter.ses.api.common.enums.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName MenuTypeEnums
 * @Author Jerry
 * @date 2020/03/11 17:44
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MenuTypeEnums {

    MENUS("MENUS", "菜单", "0"),
    BUTTONS("BUTTONS", "按钮", "1"),
    OTHER("OTHER", "其他", "2"),
    ;
    private String code;

    private String message;

    private String value;

    public static String checkCode(String value) {
        for (MenuTypeEnums item : MenuTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return OTHER.value;

    }

    public static MenuTypeEnums getErrorCodeByCode(String code) {
        for (MenuTypeEnums item : MenuTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;

    }
}
