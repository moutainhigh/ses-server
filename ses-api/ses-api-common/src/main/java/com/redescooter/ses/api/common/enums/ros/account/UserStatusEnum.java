package com.redescooter.ses.api.common.enums.ros.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: UserStatusEnum
 * @author: Darren
 * @create: 2019/01/22 15:10
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    NORMAL("NORMAL", "正常"),
    LOCK("LOCKED", "锁定"),
    CANCEL("CANCEL", "取消"),
    EXPIRED("EXPIRED","过期");

    private String code;

    private String message;

    public static UserStatusEnum getErrorCodeByCode(String code) {
        for (UserStatusEnum item : UserStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
