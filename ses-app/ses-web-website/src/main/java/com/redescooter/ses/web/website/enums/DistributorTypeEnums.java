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
public enum DistributorTypeEnums {

    SALES_STORES("销售", "sales_stores", 1),
    REPAIR_STORE("维修", "repair_store", 2),
    ALL("销售维修", "sales_repair", 3);

    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (DistributorTypeEnums item : DistributorTypeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (DistributorTypeEnums item : DistributorTypeEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
