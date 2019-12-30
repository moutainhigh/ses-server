package com.redescooter.ses.api.common.enums.account;

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
public enum SysUserStatusEnum {
    //正常
    NORMAL("NORMAL", "NORMAL","1"),

    //加锁（离职）
    LOCK("LOCKED", "LOCKED","2"),

    //取消
    CANCEL("CANCEL", "POTENTIAL_CUSTOMERS", "3"),

    // 取消
    EXPIRED("EXPIRED", "过期", "4");

    private String code;

    private String message;

    private String value;

}
