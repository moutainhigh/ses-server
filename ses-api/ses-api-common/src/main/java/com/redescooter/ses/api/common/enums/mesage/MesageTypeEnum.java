package com.redescooter.ses.api.common.enums.mesage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: MesageTypeEnum
 * @author: Alex
 * @create: 2019/06/25 16:41
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MesageTypeEnum {
    //站内消息
    SITE("SITE", "site", "1"),
    // 站外消息
    PUSH("PUSH", "push", "2"),

    NONE("NONE", "none", "3"),
    ;

    private String code;

    private String message;

    private String value;

    public static MesageTypeEnum getErrorCodeByCode(String code) {
        for (MesageTypeEnum item : MesageTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
