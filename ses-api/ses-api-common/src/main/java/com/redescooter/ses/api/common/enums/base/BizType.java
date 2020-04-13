package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: UserStatusEnum
 * @author: Darren
 * @create: 2019/01/22 15:10
 */
@Getter
@AllArgsConstructor
public enum BizType {

    DELIVERY("DELIVERY", "订单", "1"),
    EXPRESS_DELIVERY("EXPRESS_DELIVERY", "快递配送大订单", "2"),
    EXPRESS_ORDER("EXPRESS_ORDER", "快递配送小订单", "3"),
    DRIVER("DRIVER", "司机", "4"),
    SCOOTER("SCOOTER", "车辆", "5"),
    REPAIR_ORDER("REPAIR_ORDER", "维修", "6");

    private String code;

    private String message;

    private String value;

    public static BizType getErrorCodeByCode(String code) {
        for (BizType item : BizType.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
