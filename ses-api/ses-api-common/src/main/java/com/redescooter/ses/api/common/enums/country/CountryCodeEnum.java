package com.redescooter.ses.api.common.enums.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CountryCodeEnum {

    CN("CN", "86"),

    FR("FR", "33");

    private String key;

    private String value;

    public static String getValue(String key) {
        for (CountryCodeEnum o : CountryCodeEnum.values()) {
            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }
        return "33";
    }

}
