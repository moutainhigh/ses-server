package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyDocumentInvoiceStatusEnums {
    draft("draft", "草稿"),
    due("due", "到期"),
    payinprogress("payinprogress", "部分付款"),
    paid("paid", "付款"),
    late("late", "延期"),
    cancelled("cancelled", "取消");

    private String code;

    private String message;
}
