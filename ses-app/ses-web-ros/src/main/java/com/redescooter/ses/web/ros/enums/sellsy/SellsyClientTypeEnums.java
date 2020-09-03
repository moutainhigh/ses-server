package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyClientTypeEnums {
    corporation("corporation", "公司"),
    person("person", "个人");

    private String code;

    private String value;
}
