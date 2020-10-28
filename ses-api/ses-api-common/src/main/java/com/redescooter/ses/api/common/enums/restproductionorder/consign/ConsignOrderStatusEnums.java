package com.redescooter.ses.api.common.enums.restproductionorder.consign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ConsignOrderStatusEnums {

    BE_DELIVERED("BE_DELIVERED", "待发货", 0),
    BE_SIGNED("BE_SIGNED", "待签收", 10),
    RECEIVED("RECEIVED", "已签收", 20);

    private String code;

    private String message;

    private Integer value;
}
