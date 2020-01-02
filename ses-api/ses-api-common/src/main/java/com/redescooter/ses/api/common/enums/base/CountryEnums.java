package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: Country
 * @author: Darren
 * @create: 2019/03/15 10:31
 */
@Getter
@AllArgsConstructor
public enum CountryEnums {

    SAAS_DRIVER("US", "en", "1"),
    SES_DRIVER("FR", "fr", "2");

    private String country;

    private String language;

    //编码对应值
    private String value;

    public static CountryEnums findCountryByLanguage(String key) {
        for (CountryEnums item : CountryEnums.values()) {
            if (item.getLanguage().equals(key)) {
                return item;
            }
        }
        return null;
    }

}
