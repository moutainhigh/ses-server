package com.redescooter.ses.api.common.enums.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:EmployeeDeptEnums
 * @description: 员工部门列
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 11:17
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EmployeeDeptTypeEnums {

    COMPANY("COMPANY", "公司", "1"),
    DEPT("DEPT", "部门", "2"),
    POSITION("POSITION", "职位", "3"),
    OFFICEAREA("OFFICEAREA", "办公区域", "4"),
    ;

    private String code;

    private String message;

    private String value;

    public static String checkCode(String code) {
        for (EmployeeDeptTypeEnums item : EmployeeDeptTypeEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

    public static String checkValue(String value) {
        for (EmployeeDeptTypeEnums item : EmployeeDeptTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static EmployeeDeptTypeEnums getEnumByValue(String value) {
        for (EmployeeDeptTypeEnums item : EmployeeDeptTypeEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
