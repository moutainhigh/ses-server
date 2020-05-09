package com.redescooter.ses.api.common.enums.expressDelivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExpressDeliveryDetailStatusEnums {

    ASGN("ASGN","已分配","1"),
    SHIPPING("SHIPPING", " 正在配送 ","2"),
    COMPLETED("COMPLETED","完成","3"),
    REJECTED("REJECTED", "拒绝","4");


    private String code;

    private String message;

    private String value;

}
