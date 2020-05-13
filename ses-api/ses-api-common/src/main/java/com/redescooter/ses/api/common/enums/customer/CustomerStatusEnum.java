package com.redescooter.ses.api.common.enums.customer;

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

    POTENTIAL_CUSTOMERS("POTENTIAL_CUSTOMERS", "潜在客户", "1"),

    OFFICIAL_CUSTOMER("OFFICIAL_CUSTOMER", "正式客户", "2"),

    TRASH_CUSTOMER("TRASH_CUSTOMER", "垃圾客户", "3"),

    // 当询价单 被接受时 更新为潜在客户
    PREDESTINATE_CUSTOMER("PREDESTINATE_CUSTOMER","预定客户","4"),
    ;


    private String code;

    private String message;

    private String value;


    public static CustomerStatusEnum getCustomerStatusEnumByCode(String value) {
        for (CustomerStatusEnum item : CustomerStatusEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
