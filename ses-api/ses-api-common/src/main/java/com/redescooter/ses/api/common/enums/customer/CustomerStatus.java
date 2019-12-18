package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: CustomerStatus
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerStatus {
    // 垃圾桶 （毁约客户）
    TRASH("TRASH","TRASH"),
    //潜在客户
    POTENTIAL_CUSTOMERS("POTENTIAL_CUSTOMERS", "POTENTIAL_CUSTOMERS"),
    //正式客户
    OFFICIAL_CUSTOMER("OFFICIAL_CUSTOMER", "OFFICIAL_CUSTOMER");

    private String code;

    private String message;


    public static CustomerStatus getErrorCodeByCode(String code) {
        for (CustomerStatus item : CustomerStatus.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
