package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:OrderTypeEnums
 * @description: OrderTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 11:47
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderTypeEnums {
    
    INVOICE("invoice","发票"),
    ESTIMATE("estimate","报价单"),
    PROFORMA("proforma","形式表"),
    DELIVERY("delivery","货代配送单"),
    ORDER("order","订单"),
    CREDITNOTE("creditnote","资产清单");
    
    
    private String code;
    
    private String message;
}
