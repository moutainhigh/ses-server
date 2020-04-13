package com.redescooter.ses.api.common.enums.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 10/2/2020 8:25 pm
 * @ClassName: DriverLoginTypeEnum
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DriverLoginTypeEnum {

    EMAIL("EMAIL", "email", "1"),
    NICKNAME("NICKNAME", "nickname", "2");

    private String code;

    private String message;

    private String value;


    public static DriverLoginTypeEnum getErrorCodeByCode(String code) {
        for (DriverLoginTypeEnum item : DriverLoginTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
