package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductionPartsRelationTypeEnums {
    SCOOTER_DRAFT("SCOOTER_DRAFT", 1, "车辆草稿"), SCOOTER_BOM("SCOOTER_BOM", 2, "车辆BOM"),
    COMBINATION_DRAFT("COMBINATION_DRAFT", 3, "组合产品草稿"), COMBINATION_BOM("COMBINATION_BOM", 4, "组合产品");

    private String code;

    private Integer value;

    private String message;
}
