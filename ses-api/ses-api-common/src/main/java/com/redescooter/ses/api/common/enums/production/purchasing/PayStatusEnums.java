package com.redescooter.ses.api.common.enums.production.purchasing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PayStatusEnums
 * @description: PayStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 13:24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PayStatusEnums {
    PAID("PAID", "已支付", "1"),
    UNPAID("UNPAID", "未支付", "2");

    private String code;

    private String message;

    private String value;
}
