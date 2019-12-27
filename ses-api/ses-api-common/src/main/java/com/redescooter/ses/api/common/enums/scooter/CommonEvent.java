package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: CommonEvent
 * @author: alex
 * @create: 2019/01/22 15:10
 */
@Getter
@AllArgsConstructor
public  enum CommonEvent {

    START("START", "开始", "1"),
    END("END", "结束", "2");

    private String code;

    private String message;

    private String value;

    public static CommonEvent getErrorCodeByCode(String code) {
        for (CommonEvent item : CommonEvent.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
