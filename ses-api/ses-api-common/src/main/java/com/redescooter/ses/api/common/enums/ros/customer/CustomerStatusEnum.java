package com.redescooter.ses.api.common.enums.ros.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: CustomerStatusEnum
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerStatusEnum {

    POTENTIAL_CUSTOMERS("POTENTIAL_CUSTOMERS", "潜在客户",1),

    OFFICIAL_CUSTOMER("OFFICIAL_CUSTOMER", "正式客户",2);

    private String code;

    private String message;

    private Integer value;


    public static CustomerStatusEnum getCustomerStatusEnumByCode(String code) {
        for (CustomerStatusEnum item : CustomerStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
