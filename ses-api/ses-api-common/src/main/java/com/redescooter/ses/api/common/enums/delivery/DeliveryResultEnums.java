package com.redescooter.ses.api.common.enums.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: DeliveryEvent
 * @desc filed: ONTIME:准时，CANCEl取消，DELAY延迟
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeliveryResultEnums {

    ONTIME("ONTIME", "ONTIME", "1"),
    CANCEL("CANCEL", "CANCEL", "2"),
    DELAY("DELAY", "DELAY", "3");

    private String code;

    private String message;

    private String value;


    public static DeliveryResultEnums getErrorCodeByCode(String code) {
        for (DeliveryResultEnums item : DeliveryResultEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
