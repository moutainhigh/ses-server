package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:DirectDebitPaymentGatewayEnums
 * @description: DirectDebitPaymentGatewayEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 13:37
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DirectDebitPaymentGatewayEnums {
    
    N("N","不启动"),
    stripe("stripe","条纹"),
    gocardless("gocardless","无声的");
    
    private String code;
    
    private String value;
}
