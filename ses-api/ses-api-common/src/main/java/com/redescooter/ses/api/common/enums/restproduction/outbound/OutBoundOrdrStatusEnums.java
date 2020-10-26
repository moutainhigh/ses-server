package com.redescooter.ses.api.common.enums.restproduction.outbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OutBoundOrdrStatusEnums {


    BE_OUTBOUND("BE_OUTBOUND", "待出库", 0),
    QUALITY_INSPECTION("QUALITY_INSPECTION", "QUALITY_INSPECTION", 10),
    OUT_STOCK("OUT_STOCK", "已出库", 20),
    CANCEL("CANCEL", "取消", 30);

    private String code;

    private String message;

    private Integer value;
}
