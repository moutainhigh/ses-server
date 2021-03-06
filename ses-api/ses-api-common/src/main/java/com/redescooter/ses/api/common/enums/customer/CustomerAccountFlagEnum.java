package com.redescooter.ses.api.common.enums.customer;

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
    NORMAL("NORMAL","未操作","0"),
    INACTIVATED("INACTIVATED","未激活","1"),
    ACTIVATION("ACTIVATION","激活成功","3"),
    ;

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
