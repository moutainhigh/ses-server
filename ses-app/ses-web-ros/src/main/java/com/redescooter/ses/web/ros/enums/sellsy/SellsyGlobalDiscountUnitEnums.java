package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyGlobalDiscountUnitEnums
 * @description: SellsyGlobalDiscountUnitEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 19:20
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyGlobalDiscountUnitEnums {
    
    PERCENT("percent","百分比","percent"),
    AMOUNT("amount","总金额","amount");
    
    private String code;
    
    private String message;
    
    private String value;
}
