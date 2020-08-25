package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyDoubleVatEnums
 * @description: SellsyDoubleVatEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 19:23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyDoubleVatEnums {
    
    DOUBLE("double","","double "),
    VAT("VAT","","VAT"),
    NOT("not","","not");
    private String code;
    
    private String message;
    
    private String value;
}
