package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyBooleanEnums
 * @description: SellsyBooleanEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 12:02
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyBooleanEnums {

    YES("YES","Y"),
    NO("NO","N");
    
    
    private String code;
    
    private String value;
}
