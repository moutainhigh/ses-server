package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 4:37 上午
 * @Description 支付状态枚举类
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentStatusEnums {

    UNPAID_PAID("待支付", "UNPAID_PAID", 1),
    DEPOSIT_PAID("定金已支付", "DEPOSIT_PAID", 2),
    BALANCE_PAID("尾款已支付", "BALANCE_PAID", 3),
    REFUNDED("已退款", "REFUNDED", 0),
    ;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (PaymentStatusEnums item : PaymentStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (PaymentStatusEnums item : PaymentStatusEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
