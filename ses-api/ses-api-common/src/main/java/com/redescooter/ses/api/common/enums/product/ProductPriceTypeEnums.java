package com.redescooter.ses.api.common.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductPriceType
 * @description: ProductPriceType
 * @author: Alex @Version：1.3
 * @create: 2020/10/14 17:34
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductPriceTypeEnums {

    SALSE_PRICE("SALSE_PRICE", "销售价", 1), PURCHASE_PRICE("PURCHASE_PRICE", "采购价", 2);

    private String code;

    private String message;

    private Integer value;
}
