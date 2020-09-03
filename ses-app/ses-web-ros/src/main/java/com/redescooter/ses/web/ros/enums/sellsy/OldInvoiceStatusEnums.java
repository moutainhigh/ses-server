package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OldInvoiceStatusEnums {

    RECEIVED("Réglée", "收到款"),
    NOT_RECEIVED("Validée", "未付款"),
    DRAFT("Proforma", "草稿"),
    CANCELED("Annulée", "取消");

    private String code;

    private String message;
}
