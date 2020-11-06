package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderTypeEnums {

    ALLOCATE("ALLOCATE", "调拨单", 1),
    SHIPPING("SHIPPING", "采购单", 2),
    INVOICE("INVOICE", "发货单", 3),
    OUTBOUND("OUTBOUND", "出库单", 4),
    ORDER("ORDER", "委托单", 5),
    LOGISTICS("LOGISTICS", "物流运输单", 6);

    private String code;

    private String message;

    private Integer value;
}
