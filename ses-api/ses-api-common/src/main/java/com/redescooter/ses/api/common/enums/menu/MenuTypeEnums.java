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

    NONE("NONE", "暂无", "-1"),
    MENUS("MENUS", "菜单", "0"),
    BUTTONS("BUTTONS", "按钮", "1"),
    API("API", "开放API", "2"),
    ;
    private String code;

    private String message;

    private String value;

    /**
     * 验证菜单类型，不合法时使用默认没有表示
     *
     * @param value
     * @return
     */
    public static String checkCode(String value) {
        for (MenuTypeEnums item : MenuTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return NONE.value;

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
