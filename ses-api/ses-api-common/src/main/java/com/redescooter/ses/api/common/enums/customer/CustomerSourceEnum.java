package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 8:57 上午
 * @ClassName: CustomerSourceEnum
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerSourceEnum {

    SYSTEM("SYSTEM", "系统", "1"),
    WEBSITE("WEBSITE", "官网", "2"),
    ;

    private String code;

    private String message;

    private String value;


    public static CustomerSourceEnum getCustomerSourceEnumByCode(String code) {
        for (CustomerSourceEnum item : CustomerSourceEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
