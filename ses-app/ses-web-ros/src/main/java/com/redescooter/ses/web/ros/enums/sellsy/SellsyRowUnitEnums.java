package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyRowUnitEnums
 * @description: SellsyRowUnitEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 17:48
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyRowUnitEnums {
    
    KG("kg","千克","kg"),
    m3("m3","m3","m3");
    
    private String code;
    
    private String message;
    
    private String value;
}
