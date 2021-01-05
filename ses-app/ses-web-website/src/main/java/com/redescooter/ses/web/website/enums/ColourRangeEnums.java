package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 12:07 上午
 * @Description 颜色使用范围枚举
 **/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ColourRangeEnums {

    VEHICLE("整车", "VEHICLE", 1),
    PARTS("配件", "PARTS", -1);

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (ColourRangeEnums item : ColourRangeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
