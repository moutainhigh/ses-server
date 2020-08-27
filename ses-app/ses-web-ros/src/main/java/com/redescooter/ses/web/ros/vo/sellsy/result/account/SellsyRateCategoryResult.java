package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyRateCategoryResult {

    private String id;

    private String corpid;

    private String name;

    private String hasTaxesInc;

    private String currencyid;

    private String layoutid;

    private String taxid;

    private String accountingSellCode;

    private String priceRule;

    private String pricePercent;

    private String isDefault;

    private String status;

    private String defaultLayoutEstimate;

    private String defaultLayoutInvoice;

    private String defaultLayoutCreditnote;

    private String defaultLayoutDelivery;

    private String defaultLayoutOrder;

    private String defaultLayoutProforma;

    private String defaultLayoutModel;

    private String discountCode;

    private String taxrate;

    private String currencySymbol;
}
