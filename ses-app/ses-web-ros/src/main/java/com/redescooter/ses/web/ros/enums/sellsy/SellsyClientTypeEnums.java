package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyClientTypeEnums {
    corporation("corporation", "公司","corporation"),
    person("person", "个人","person");

    private String code;

    private String message;

    private String value;
}
