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
public enum WebSiteCustomerSourceEnums {

    OFFICIAL("官方","OFFICIAL",1),
    INTERNAL("内部","INTERNAL",2),
    OFFLINE("线下","OFFLINE",3),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (WebSiteCustomerSourceEnums item : WebSiteCustomerSourceEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
