package com.redescooter.ses.api.common.enums.expressDelivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExpressDeliveryStatusEnums
 * @description: ExpressDeliveryStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/13 11:03
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExpressDeliveryStatusEnums {

    WAITING("WAITING", "待配送","1"),
    SHIPPING("SHIPPING", " 正在配送 ","2"),
    COMPLETED("COMPLETED", "已配送","3"),
    REFUSE("REFUSED", "失败","4");

    private String code;

    private String message;

    private String value;
}
