package com.redescooter.ses.api.common.enums.mesage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: MesageBizType
 * @author: Alex
 * @create: 2019/06/25 16:41
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MesageBizTypeEnum {
    //订单类型的消息
    DELIVERY("DELIVERY", "delivery", "1"),
    // 维修单 类型的消息
    MAINTENANCE("MAINTENANCE", "maintenance", "2"),
    // 表示系统通知
    SYSTEM("SYSTEM", "system", "3");

    private String code;

    private String message;

    private String value;

    public static MesageBizTypeEnum getErrorCodeByCode(String code) {
        for (MesageBizTypeEnum item : MesageBizTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
