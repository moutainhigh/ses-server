package com.redescooter.ses.api.common.enums.website;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AccessoryBatteryEnums
 * @description: AccessoryBatteryEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AccessoryBatteryEnums {

    BATTERY("BATTERY","BATTERY","1000000","100.00");


    private String code;

    private String message;

    private String value;

    private String price;
}
