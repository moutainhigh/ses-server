package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: DeliveryEvent
 * @desc filed: 可用的AVAILABLE，充电中CHARGING，维修中REPAIR，故障损坏FAULT，使用中USEING
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TenantScooterStatusEnums {
    //可用的
    AVAILABLE("AVAILABLE", "AVAILABLE", "1"),
    //充电中
    CHARGING("CHARGING", "CHARGING", "2"),
    //维修中
    REPAIR("REPAIR", "REPAIR", "3"),
    //故障损坏
    FAULT("FAULT", "FAULT", "4"),
    //使用中
    USEING("USEING", "USEING", "5");

    private String code;

    private String message;

    private String value;

    public static TenantScooterStatusEnums getTenantScooterStatus(String code) {
        for (TenantScooterStatusEnums item : TenantScooterStatusEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
