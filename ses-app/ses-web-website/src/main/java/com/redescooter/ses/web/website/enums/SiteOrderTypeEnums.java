package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/17 1:10 下午
 * @Description 订单类型
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SiteOrderTypeEnums {

    SALE("销售", "SALE", 1),
    LEASE("租赁", "LEASE", 2),
    ;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (SiteOrderTypeEnums item : SiteOrderTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static String getValueByInt(int value) {
        for (SiteOrderTypeEnums item : SiteOrderTypeEnums.values()) {
            if (value == item.value) {
                return String.valueOf(item.value);
            }
        }
        return null;
    }
}
