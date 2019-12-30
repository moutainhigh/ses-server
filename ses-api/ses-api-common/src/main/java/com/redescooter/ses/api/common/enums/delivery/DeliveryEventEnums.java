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
public enum DeliveryEventEnums {

    //创建
    CREATE("CREATE", "创建订单", "1"),
    //开始配送
    START("START", "开始订单", "2"),
    //拒单
    REJECT("REJECT", "拒绝订单", "3"),
    //超时
    TIMEOUT("TIMEOUT", "超时", "4"),
    //完成
    COMPLETED("COMPLETED", "完成", "5"),
    //超时完成
    TIMEOUT_COMPLETE("TIMEOUT_COMPLETE", "超时完成", "6"),
    //失败（取消订单）
    CANCEL("CANCEL", "取消", "7");
    private String code;

    private String message;

    private String value;


    public static DeliveryEventEnums getErrorCodeByCode(String code) {
        for (DeliveryEventEnums item : DeliveryEventEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
