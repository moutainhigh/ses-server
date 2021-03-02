package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 5:14 上午
 * @Description 分期付款月份
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LnstallmentMonthEnums {

    DOWN_PAYMENTS("首付","DOWN_PAYMENTS",0),
    STAGE24("24期","STAGE24",1),
    STAGE36("36期","STAGE36",2),
    STAGE48("48期","STAGE48",3),
    STAGE60("60期","STAGE60",4),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (LnstallmentMonthEnums item : LnstallmentMonthEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (LnstallmentMonthEnums item : LnstallmentMonthEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
