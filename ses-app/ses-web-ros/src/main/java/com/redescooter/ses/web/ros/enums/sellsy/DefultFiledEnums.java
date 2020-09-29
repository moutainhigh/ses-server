package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DefultFiledEnums {
    defaultTaxId("defaultTaxId", "默认税率"),
    defaultTax2Id("defaultTax2Id", "默认税率2"),
    defaultUnitId("defaultUnitId", "默认货币单位"),
    defaultShippingId("defaultShippingId", "默认配送供应商"),
    defaultCurrency("defaultCurrency", "默认货币单位"),
    dueAmount("dueAmount", "实例单位"),
    formatted_dueAmount("formatted_dueAmount", "格式化金额单位"),
    ;

    private String value;

    private String message;

}
