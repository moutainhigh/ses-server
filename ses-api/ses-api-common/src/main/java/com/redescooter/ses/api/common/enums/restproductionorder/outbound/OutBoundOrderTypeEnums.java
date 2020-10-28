package com.redescooter.ses.api.common.enums.restproductionorder.outbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OutBoundOrderTypeEnums {
    SALES("SALES", "销售", 1);

    private String code;

    private String message;

    private Integer value;
}
