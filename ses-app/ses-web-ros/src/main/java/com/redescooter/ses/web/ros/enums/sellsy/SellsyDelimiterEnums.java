package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyDelimiterEnums {

    Space("Space", "空格", " "), right("right", "右边", "right");

    private String code;

    private String message;

    private String value;
}