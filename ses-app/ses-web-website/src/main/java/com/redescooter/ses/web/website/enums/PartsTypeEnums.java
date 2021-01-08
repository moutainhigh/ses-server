package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 4:07 下午
 * @Description 配件类型枚举类
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PartsTypeEnums {
    BATTERY("电池","BATTERY",1),
    ACCESSORY("配件","ACCESSORY",2),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (PartsTypeEnums item : PartsTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
