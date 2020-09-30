package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductionScooterGroupEnums {

    HIG_SPEED("HIG_SPEED", 1, "高速"), LOW_SPEED("LOW_SPEED", 2, "低速");

    private String code;

    private Integer value;

    private String message;

    public static ProductionScooterGroupEnums getEnumByValue(Integer value) {
        for (ProductionScooterGroupEnums item : ProductionScooterGroupEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
