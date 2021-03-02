package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 4:37 上午
 * @Description 支付方式枚举类
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentTypeEnums {

    FULL_AMOUNT("全额支付","FULL_AMOUNT",0),
    BY_STAGES("分期支付","BY_STAGES",1),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (PaymentTypeEnums item : PaymentTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (PaymentTypeEnums item : PaymentTypeEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
