package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/6 4:11 下午
 * @Description 配件采购来源枚举
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PartsProcurementSourceEnums {

    FR("法国","FR",1),
    CN("中国","CN",2),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (PartsProcurementSourceEnums item : PartsProcurementSourceEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (PartsProcurementSourceEnums item : PartsProcurementSourceEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
