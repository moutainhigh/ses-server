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
public enum SellsyDocmentTypeEnums {

    invoice("invoice", "发票"), estimate("estimate", "报价单"), proforma("proforma", "形式表"), delivery("delivery", "货代配送单"),
    order("order", "订单"), creditnote("creditnote", "资产清单");
    
    
    private String code;
    
    private String message;
}
