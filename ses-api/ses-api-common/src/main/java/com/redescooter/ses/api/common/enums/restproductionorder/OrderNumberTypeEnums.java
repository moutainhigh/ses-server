package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderNumberTypeEnums {
    ALLOCAT("ALLOCAT", "调拨单", "TO"),
    PURHCAS("PURHCAS", "采购", "PO"),
    INVOICE("INVOICE", "发货单", "SO"),
    STOCK("STOCK", "出库单", "OO"),
    CONSIGN("CONSIGN", "委托单", "DO");

    private String code;

    private String message;

    private String value;

}
