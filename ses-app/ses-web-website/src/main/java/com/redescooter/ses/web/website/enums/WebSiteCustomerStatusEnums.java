package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 10:42 下午
 * @Description 客户状态枚举
 **/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WebSiteCustomerStatusEnums {

    INTENTION("意向客户", "INTENTION", -1),
    POSITIVE("积极客户", "POSITIVE", 1),
    FORMAL("正式客户", "FORMAL", 01),
    ;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (WebSiteCustomerStatusEnums item : WebSiteCustomerStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }


}
