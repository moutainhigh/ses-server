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
public enum ProductTypeEnums {

    VEHICLE("整车","VEHICLE",1),
    REPAIR_PARTS("维修配件","REPAIR_PARTS",2),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
