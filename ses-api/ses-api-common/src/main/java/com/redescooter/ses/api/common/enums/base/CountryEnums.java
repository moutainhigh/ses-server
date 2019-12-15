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

    SAAS_DRIVER("US", "en"),
    SES_DRIVER("FR", "fr");

    private String country;

    private String language;

    public static CountryEnums findCountryByLanguage(String key) {
        for (CountryEnums item : CountryEnums.values()) {
            if (item.getLanguage().equals(key)) {
                return item;
            }
        }
        return null;
    }

}
