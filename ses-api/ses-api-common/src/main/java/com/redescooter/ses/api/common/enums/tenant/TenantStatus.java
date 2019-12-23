package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: TenantStatus
 * @desc filed: 运营中、已过期、已冻结
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TenantStatus {

    //运行中
    INOPERATION("INOPERATION", "运行中","1"),

    //已过期
    EXPIRED("EXPIRED", "已过期","2"),

    //已冻结
    FROZEN("FROZEN", "已冻结","3");

    private String code;

    private String message;

    private String value;


    public static TenantStatus getErrorCodeByCode(String code) {
        for (TenantStatus item : TenantStatus.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
