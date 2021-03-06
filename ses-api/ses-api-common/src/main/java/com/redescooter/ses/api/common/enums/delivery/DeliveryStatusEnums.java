package com.redescooter.ses.api.common.enums.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: DeliveryEvent
 * @desc filed: WAITING 待配送 SHIPPING 正在配送 COMPLETED 已配送 REFUSED 失败
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeliveryStatusEnums {

    //待配送
    PENDING("PENDING", "pending", "1"),

    //配送中
    DELIVERING("DELIVERING", "delivering", "2"),

    //拒单
    REJECTED("REJECTED", "rejected", "3"),

    //超时完成
    TIMEOUT_COMPLETE("TIMEOUT_COMPLETE", "timeoutComplete", "5"),

    //已送达
    COMPLETED("COMPLETED", "completed", "6"),

    //失败（取消订单）
    CANCEL("CANCEL", "cancel", "7");

    private String code;

    private String message;

    private String value;


    public static DeliveryStatusEnums getErrorCodeByCode(String code) {
        for (DeliveryStatusEnums item : DeliveryStatusEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
