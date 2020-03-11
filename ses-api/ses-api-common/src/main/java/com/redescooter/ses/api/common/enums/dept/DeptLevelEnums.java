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

    COMPANY("COMPANY", "公司", "0"),
    SUBSIDIARY("SUBSIDIARY", "子公司", "1"),
    DEPARTMENT("DEPARTMENT", "部门", "2"),
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
