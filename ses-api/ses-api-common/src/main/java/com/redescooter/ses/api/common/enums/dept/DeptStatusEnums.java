package com.redescooter.ses.api.common.enums.dept;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @ClassNameSelectDeptResult
 * @Description
 * @Author Joan
 * @Date2020/9/2 12:03
 * @Version V1.0
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeptStatusEnums {
    COMPANY("NORMAL", "正常", "1"),
    DEPARTMENT("DISABLE", "禁用", "2"),
    ;

    private String code;

    private String message;

    private String value;

}
