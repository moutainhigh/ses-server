package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyCurrencySymbolLocationEnms {

    left("left", "左边", "left"), right("right", "右边", "right"), both("both", "都存在", "both"), none("none", "没有", "none");

    private String code;

    private String message;

    private String value;
}
