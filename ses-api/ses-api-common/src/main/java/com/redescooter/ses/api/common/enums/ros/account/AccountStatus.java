package com.redescooter.ses.api.common.enums.ros.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: AccountStatus
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AccountStatus {
    //正常
    NORMAL("NORMAL", "NORMAL"),

    //加锁（离职）
    LOCK("LOCKED", "LOCKED"),

    //取消
    CANCEL("CANCEL", "POTENTIAL_CUSTOMERS");

    private String code;

    private String message;


    public static AccountStatus getErrorCodeByCode(String code) {
        for (AccountStatus item : AccountStatus.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
