package com.redescooter.ses.api.common.enums.production.wh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:StockItemStatusEnums
 * @description: StockItemStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 18:01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StockItemStatusEnums {

    DAMAGED("损坏","DAMAGED","1"),
    AVAILABLE("可用","AVAILABLE","2"),
    OUT_OF_STOCK("出库","OUT_OF_STOCK","3");

    private String code;

    private String message;

    private String value;
}
