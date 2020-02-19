package com.redescooter.ses.api.common.enums.driver;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * 司机驾照等级
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DriverLicenseLevelEnum {

    //无
    NONE("NONE", "NONE", "0"),
    //低
    LOW("LOW", "LOW", "1"),
    //中
    CENTRE("CENTRE", "CENTRE", "2"),
    //高
    HIGH("HIGH", "HIGH", "3"),
    ;

    private String code;

    private String message;

    private String value;

    public static DriverLicenseLevelEnum getEnumByValue(String value) {
        for (DriverLicenseLevelEnum item : DriverLicenseLevelEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return DriverLicenseLevelEnum.NONE;
    }
}
