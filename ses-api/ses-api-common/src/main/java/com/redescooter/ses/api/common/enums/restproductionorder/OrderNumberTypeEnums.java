package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderNumberTypeEnums {
    ALLOCAT("ALLOCAT", "调拨单", "RTO"),
    PURHCAS("PURHCAS", "采购单", "RPO"),
    INVOICE("INVOICE", "发货单", "RSO"),
    STOCK("STOCK", "出库单", "ROO"),
    CONSIGN("CONSIGN", "委托单", "RDO"),

    COMBIN("COMBIN", "组装单", "AO"),

    IN_WHOUSE("IN_WHOUSE", "入库单", "EO"),

    PRODUCTION_PURCHASE("PRODUCTION_PURCHASE", "生产采购单", "PO"),

    INQUIRY_ORDER("INQUIRY_ORDER", "询价单", "XO");


    private String code;

    private String message;

    private String value;

}
