package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PaymentTypeEnuums
 * @description: PaymentTypeEnuums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 11:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentTypeEnums {

    STAGING("STAGING", "分期", "1"),
    MONTHLY_PAY("MONTHLY_PAY", "月结", "2");

    private String code;

    private String message;

    private String value;
}
