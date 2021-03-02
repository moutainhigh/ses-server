package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 1:43 上午
 * @Description 产品状态枚举
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductStatusEnums {
    UP("正常","UP",1),
    DOWN("失效","DOWN",-1),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (ProductStatusEnums item : ProductStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (ProductStatusEnums item : ProductStatusEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
