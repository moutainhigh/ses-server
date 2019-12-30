package com.redescooter.ses.api.common.enums.account;

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

    NORMAL("NORMAL", "正常","1"),
    LOCK("LOCKED", "锁定","2"),
    CANCEL("CANCEL", "取消","3"),
    EXPIRED("EXPIRED","过期","4");

    private String code;

    private String message;

    private String value;

}
