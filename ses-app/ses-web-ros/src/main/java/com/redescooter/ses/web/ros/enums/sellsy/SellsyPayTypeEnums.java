package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyPayTypeEnums
 * @description: 支付方式
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 13:18
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyPayTypeEnums {
    
    paypal("paypal", "1"),
    bluepaid("bluepaid", "2"),
    stripe("stripe", "3"),
    atos("atos", "4"),
    adyen("adyen","5");
    
    private String code;
    
    private String value;
}
