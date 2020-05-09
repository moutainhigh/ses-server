package com.redescooter.ses.api.common.enums.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FactoryStatusEnum {

    /**
     * 正常
     */
    NORMAL("NORMAL", "NORMAL", "1"),
    /**
     * 过期
     */
    OVERDUE("OVERDUE", "OVERDUE", "2"),
    ;

    private String code;

    private String message;

    private String value;
}
