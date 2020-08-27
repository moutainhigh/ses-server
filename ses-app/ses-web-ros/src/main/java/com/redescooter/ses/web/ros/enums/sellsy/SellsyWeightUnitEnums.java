package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyWeightUnitEnums {

    g("g", "克", "g"), kg("kg", "千克", "kg");

    private String code;

    private String message;

    private String value;
}
