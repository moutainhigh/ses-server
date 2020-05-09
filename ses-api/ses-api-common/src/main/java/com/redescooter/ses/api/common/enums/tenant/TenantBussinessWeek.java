package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TenantBussinessWeek
 * @description: TenantBussinessWeek
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 16:13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TenantBussinessWeek {

    MON("MON","周一","1"),
    TUE("TUE","周二","2"),
    WED("WED","周三","3"),
    THU("THU","周四","4"),
    FRI("FRI","周五","5"),
    SAT("SAT","周六","6"),
    SUN("SUN", "周日", "0")
    ;



    private String code;

    private String message;

    private String value;
}
