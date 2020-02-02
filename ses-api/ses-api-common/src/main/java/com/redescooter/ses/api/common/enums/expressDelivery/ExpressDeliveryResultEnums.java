package com.redescooter.ses.api.common.enums.expressDelivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExpressDeliveryResultEnums
 * @description: ExpressDeliveryResultEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/16 10:44
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExpressDeliveryResultEnums {

    WAITING("WAITING", "待配送"),
    SHIPPING("SHIPPING", " 正在配送 "),
    COMPLETED("COMPLETED", "已配送"),
    REFUSED("REFUSED", "失败");


    private String code;

    private String message;

}
