package com.redescooter.ses.api.common.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyMethod
 * @description: SellsyMethod
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 19:41
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyMethodsEnums {
    
    test("1","1","1");
    
    private String code;
    
    private String message;
    
    private String value;
}
