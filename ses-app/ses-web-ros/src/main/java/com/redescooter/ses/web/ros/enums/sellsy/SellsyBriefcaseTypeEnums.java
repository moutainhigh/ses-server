package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyBriefcaseTypeEnums {
    people("people", "人"), third("third", "第三方"), opportunity("opportunity", "机会"), item("item", "产品条目"),
    service("service", "服务"), invoice("invoice", "发票"), estimate("estimate", "估计"), proforma("proforma", ""),
    delivery("delivery", "配送单"), order("order", "订单"), creditnote("creditnote", "信用票据"),
    purInvoice("purInvoice", "采购发票"), purDelivery("purDelivery", "采购单"), purOrder("purOrder", "采购订单"),
    purCreditNote("purCreditNote", "采购信用单据"), document("document", "文档类型"), purchase("purchase", "采购"),;

    private String code;

    private String message;
}
