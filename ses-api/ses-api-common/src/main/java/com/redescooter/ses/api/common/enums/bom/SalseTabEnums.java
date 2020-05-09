package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName SalseTabEnums
 * @Author Jerry
 * @date 2020/03/06 15:57
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SalseTabEnums {

    SALES_PRODUCTS("product", "销售产品", "1"),
    AFTER_SALES_PRODUCTS("afterSale", "售后产品", "2"),
    VALUE_ADDED_PRODUCTS("service", "增值服务", "3"),
    ;

    private String code;

    private String name;

    private String value;


    public static String checkCode(String code) {
        for (SalseTabEnums item : SalseTabEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }
}
