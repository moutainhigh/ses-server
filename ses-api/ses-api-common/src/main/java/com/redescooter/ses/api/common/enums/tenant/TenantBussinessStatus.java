package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TenantBussinessStatus
 * @description: TenantBussinessStatus
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 17:16
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum TenantBussinessStatus {

    OPEN("OPEN","营业","1"),
    CLOSE("CLOSE","打烊","2"),
    ;


    private String code;
    private String message;
    private String value;
}
