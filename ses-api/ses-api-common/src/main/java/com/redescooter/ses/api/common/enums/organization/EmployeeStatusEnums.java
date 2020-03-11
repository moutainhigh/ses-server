package com.redescooter.ses.api.common.enums.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:EmployeeStatusEnums
 * @description: EmployeeStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/11 11:47
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EmployeeStatusEnums {

    IN_SERVICE("IN_SERVICE", "在职", "1"),
    LEAVE("LEAVE", "离职", "2");


    private String code;

    private String message;

    private String value;

}
