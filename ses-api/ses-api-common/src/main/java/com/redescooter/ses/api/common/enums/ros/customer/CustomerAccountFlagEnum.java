package com.redescooter.ses.api.common.enums.ros.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 7:17 下午
 * @ClassName: CustomerAccountFlagEnum
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerAccountFlagEnum {

    ACTIVATION("ACTIVATION","激活成功","1"),
    INACTIVATED("INACTIVATED","未激活","0"),;

    private String code;

    private String message;

    private String value;

    public static CustomerAccountFlagEnum getCustomerAccountFlagEnumByCode(String value) {
        for (CustomerAccountFlagEnum item : CustomerAccountFlagEnum.values()) {
            if (item.getCode().equals(value)) {
                return item;
            }
        }
        return null;
    }

}
