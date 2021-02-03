package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/23 10:39 上午
 * @Description 官网订单支付状态枚举
 * 支付状态,1待支付，2部分支付，3已支付
 * Payment status, 1 UN_PAID, 2 PARTIALLY_PAID, 3 PAID
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SiteOrderPaymentStatusEnums {

    UN_PAID("待支付", "UN_PAID", 1),
    PARTIALLY_PAID("部分支付", "PARTIALLY_PAID", 2),
    PAID("已支付", "PAID", 3),
    ;

    private String remark;

    private String code;

    private Integer value;

    public static String getEnumsCodeByValue(int value) {
        for (SiteOrderPaymentStatusEnums item : SiteOrderPaymentStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (SiteOrderPaymentStatusEnums item : SiteOrderPaymentStatusEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
