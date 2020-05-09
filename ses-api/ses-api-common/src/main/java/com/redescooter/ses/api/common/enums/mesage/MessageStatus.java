package com.redescooter.ses.api.common.enums.mesage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: LoginType
 * @author: Darren
 * @create: 2019/04/03 16:36
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MessageStatus {

    READ("READ", "READ", "1"),
    UNREAD("UNREAD", "UNREAD", "2"),

    ;

    private String code;

    private String message;

    private String value;
}
