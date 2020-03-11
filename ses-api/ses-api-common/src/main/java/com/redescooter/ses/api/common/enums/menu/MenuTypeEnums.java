package com.redescooter.ses.api.common.enums.menu;

import com.redescooter.ses.api.common.enums.mesage.MesageBizTypeEnum;
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

    CATALOGS("CATALOGS", "目录", "-1"),
    MENUS("MENUS", "菜单", "0"),
    BUTTONS("BUTTONS", "按钮", "1"),
    ;
    private String code;

    private String message;

    private String value;

    public static MenuTypeEnums getErrorCodeByCode(String code) {
        for (MenuTypeEnums item : MenuTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;

    }
}
