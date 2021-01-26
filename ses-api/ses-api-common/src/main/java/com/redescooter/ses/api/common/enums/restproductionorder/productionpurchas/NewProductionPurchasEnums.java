package com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NewProductionPurchasEnums {

    DRAFT("DRAFT", "草稿", 1),

    PURCHASING("PURCHASING", "采购中", 10),

    FINISHED("FINISHED", "已完成", 20);

    private String code;

    private String message;

    private Integer value;

}
