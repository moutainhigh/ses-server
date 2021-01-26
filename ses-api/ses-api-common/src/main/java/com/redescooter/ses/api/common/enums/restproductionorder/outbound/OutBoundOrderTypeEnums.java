package com.redescooter.ses.api.common.enums.restproductionorder.outbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OutBoundOrderTypeEnums {
    SALES("SALES", "销售", 1),
    RECEIVING("RECEIVING","领用",2),
    ASSEMBLY("ASSEMBLY","组装",3),
    OTHER("OTHER","其他",4);

    private String code;

    private String message;

    private Integer value;
}
