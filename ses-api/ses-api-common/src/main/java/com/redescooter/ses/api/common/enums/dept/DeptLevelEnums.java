package com.redescooter.ses.api.common.enums.dept;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName DeptLevelEnums
 * @Author Jerry
 * @date 2020/03/11 20:12
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeptLevelEnums {

    COMPANY("COMPANY", "顶级公司顶级公司", "0"),
    DEPARTMENT("DEPARTMENT", "一级部门", "1"),
    ;

    private String code;

    private String message;

    private String value;

    public static DeptLevelEnums getEnumByValue(String value) {
        for (DeptLevelEnums item : DeptLevelEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
