package com.redescooter.ses.api.common.enums.rps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ModerTypeEnums {

    METERIAL("METERIAL", "来料质检", "1"),
    PURCHASE_WH("PURCHASE_WH", "采购入库", "2"),
    PREPARED("PREPARED", "带备料", "3"),
    ASSEMBLY("ASSEMBLY", "整车组装", "4"),
    SCOOTER_QC("SCOOTER_QC", "整车质检", "5"),
    PRODUCTION_WH("PRODUCTION_WH", "生产入库", "6");

    private String code;

    private String message;

    private String value;
}
