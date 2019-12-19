package com.redescooter.ses.api.common.enums.ros.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: AccountStatusEnum
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AccountStatusEnum {
    //正常
    NORMAL("NORMAL", "NORMAL"),

    //加锁（离职）
    LOCK("LOCKED", "LOCKED"),

    //取消
    CANCEL("CANCEL", "POTENTIAL_CUSTOMERS");

    private String code;

    private String message;


    public static AccountStatusEnum getErrorCodeByCode(String code) {
        for (AccountStatusEnum item : AccountStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
