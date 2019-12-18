package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: CustomerStatus
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerSource {
    //官网
    OFFICIAL_WEBSITE("OFFICIAL_WEBSITE", "OFFICIAL_WEBSITE"),

    // crm 管理员系统添加
    ROS("ROS", "ROS"),

    //email方式 联系添加
    EMAIL("EMAIL", "EMAIL"),

    //电话联系添加
    PHONE("PHONE", "PHONE");

    private String code;

    private String message;


    public static CustomerSource getErrorCodeByCode(String code) {
        for (CustomerSource item : CustomerSource.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
