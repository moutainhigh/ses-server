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
public enum DeliveryLableEnums {

    // 超时预警 （和订单业务无关 主要是为了推送消息定义的订单状态）
    TIMEOUT_WARNING("TIMEOUT_WARNING", "timeout warning", "1");


    private String code;

    private String message;

    private String value;


    public static DeliveryLableEnums get(String value) {
        for (DeliveryLableEnums item : DeliveryLableEnums.values()) {
            if (item.getCode().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
