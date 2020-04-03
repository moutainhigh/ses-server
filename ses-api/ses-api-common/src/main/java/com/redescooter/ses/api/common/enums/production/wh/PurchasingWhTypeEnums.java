package com.redescooter.ses.api.common.enums.production.wh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PurchasingWhTypeEnums
 * @description: PurchasingWhTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:17
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PurchasingWhTypeEnums {

    AVAILABLE("AVAILABLE", "可用", "1"),
    PURCHASING("PURCHASING", "采购中", "2 "),
    TO_BE_STORED("TO_BE_STORED", "待入库", "3"),
    OUT_WH("OUT_WH", "已出库", "4"),
    WASTE("WASTE", "废料库", "5");

    private String code;

    private String message;

    private String value;
}
