package com.redescooter.ses.web.ros.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CountryShortNameEnums
 * @description: CountryShortNameEnums 电话的国家代码
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/24 16:27
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayCountryShortNameEnums {

    FRANCE("France","FR");

    private String countryShortName;

    private String value;
}
