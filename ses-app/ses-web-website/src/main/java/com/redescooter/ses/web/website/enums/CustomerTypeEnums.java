package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 10:59 下午
 * @Description 客户类型
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerTypeEnums {

    ENTERPRISE("企业","ENTERPRISE",1),
    PERSONAL("个人","PERSONAL",2),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (CustomerTypeEnums item : CustomerTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
