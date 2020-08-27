package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

import java.util.List;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyShippPriceResult {
    private String id;

    private String rcid;

    private String name;

    private double amount;

    private String taxid;

    private String taxrate;

    private String priceRule;

    private String pricePercent;

    private String hasTaxesInc;

    private String itemid;

    private int declid;

    private String currencyid;

    private boolean hasVat;

    private String useEcoTax;

    private String ecoTaxType;

    private String ecoTax;

    private String amountTaxesFree;

    private String amountTaxesInc;

    private String formatted_amountTaxesFree;

    private String formatted_amountTaxesInc;

    private String formatted_amount;

    private int promoAmount;

    private String formatted_promoAmount;

    private List<String> promotion;
}
