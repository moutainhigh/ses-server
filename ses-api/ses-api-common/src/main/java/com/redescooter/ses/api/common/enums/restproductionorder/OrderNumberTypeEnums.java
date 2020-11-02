package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderNumberTypeEnums {
    ALLOCAT("ALLOCAT", "调拨单", "RTO"),
    PURHCAS("PURHCAS", "采购", "RPO"),
    INVOICE("INVOICE", "发货单", "RSO"),
    STOCK("STOCK", "出库单", "ROO"),
    CONSIGN("CONSIGN", "委托单", "RDO");

    private String code;

    private String message;

    private String value;

}
