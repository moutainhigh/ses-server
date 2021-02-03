package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 7:48 下午
 * @Description 取货方式
 **/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeliveryMethodEnums {

    SELF_LIFT("自提", "SELF_LIFT", 1, "199"),
    DELIVER_HOME("送货到家", "DELIVER_HOME", -1, "199"),
    ;

    private String remark;

    private String code;

    private int value;

    /**
     * 费用
     **/
    private String cost;


    public static String getEnumsCodeByValue(int value) {
        for (DeliveryMethodEnums item : DeliveryMethodEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (DeliveryMethodEnums item : DeliveryMethodEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
