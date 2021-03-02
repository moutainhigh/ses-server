package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: SysUserStatusEnum
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SiteUserStatusEnum {
    //正常
    NORMAL("NORMAL", "NORMAL",1),

    //加锁（离职）
    LOCK("LOCKED", "LOCKED",2),

    //取消
    CANCEL("CANCEL", "POTENTIAL_CUSTOMERS", 3),

    //过期
    EXPIRED("EXPIRED", "过期", 4);

    private String code;

    private String message;

    private Integer value;

    public static String getEnumsCodeByValue(int value) {
        for (SiteUserStatusEnum item : SiteUserStatusEnum.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (SiteUserStatusEnum item : SiteUserStatusEnum.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
