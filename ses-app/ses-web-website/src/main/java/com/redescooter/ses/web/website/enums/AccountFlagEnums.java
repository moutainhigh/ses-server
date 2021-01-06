package com.redescooter.ses.web.website.enums;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 11:14 下午
 * @Description 账号使用标识枚举
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AccountFlagEnums {

    INACTIVATED("未激活","INACTIVATED",0),
    ACTIVATION("激活","ACTIVATION",1),;

    private String remark;

    private String code;

    private Integer value;

    public static String getEnumsCodeByValue(int value) {
        for (AccountFlagEnums item : AccountFlagEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
