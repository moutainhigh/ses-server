package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductionBomStatusEnums {

    ACTIVE("ACTIVE", 1, "已激活"), TO_BE_ACTIVE("TO_BE_ACTIVE", 2, "待激活"), EXPIRED("EXPIRED", 3, "已过期"),
    ABOLISHED("ABOLISHED", 4, "已作废");

    private String code;

    private Integer value;

    private String message;
}
