package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:EcotaxTypeEnums
 * @description: EcotaxTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 17:08
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyEcotaxTypeEnums {
    
    INC("inc","使用","inc"),
    EXC("exc","不使用","exc");
    
    private String code;
    
    private String message;
    
    private String value;
}
