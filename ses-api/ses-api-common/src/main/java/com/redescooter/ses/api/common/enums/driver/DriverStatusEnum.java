package com.redescooter.ses.api.common.enums.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: DeliveryEvent
 * @desc filed: WORKING 上班 OFFWORK 下班 DEPARTURE 离职
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DriverStatusEnum {

    WORKING("WORKING", "Working", "1"),
    OFFWORK("OFFWORK", "Offwork", "2"),
    DEPARTURE("DEPARTURE", "Departure", "3");

    private String code;

    private String message;

    private String value;


    public static DriverStatusEnum getDriverStatus(String code) {
        for (DriverStatusEnum item : DriverStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
