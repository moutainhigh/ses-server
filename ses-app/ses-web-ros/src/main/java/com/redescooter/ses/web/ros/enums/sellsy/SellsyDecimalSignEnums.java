package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyDecimalSignEnums {
    comma("comma", "逗号", ","), origin("origin", "圆点", ".");

    private String code;

    private String message;

    private String value;
}
