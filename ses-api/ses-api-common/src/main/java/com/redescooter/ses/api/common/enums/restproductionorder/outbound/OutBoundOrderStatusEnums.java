package com.redescooter.ses.api.common.enums.restproductionorder.outbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OutBoundOrderStatusEnums {


    DRAFT("DRAFT", "新建", -1),
    BE_OUTBOUND("BE_OUTBOUND", "待出库", 0),
    QUALITY_INSPECTION("QUALITY_INSPECTION", "质检中", 10),
    OUT_STOCK("OUT_STOCK", "已出库", 20),
    CANCEL("CANCEL", "取消", 30);

    private String code;

    private String message;

    private Integer value;
}
