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
public enum MessagePriorityEnums {

    NONE_REMIND("NONE_REMIND", "不需要提醒", "1"),
    COMMON_REMIND("COMMON_REMIND", "普通提醒", "2"),
    FORCED_REMIND("FORCED_REMIND", "强制提醒", "3");

    private String code;

    private String message;

    private String value;
}
