package com.redescooter.ses.api.common.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaleProductType
 * @description: SaleProductType
 * @author: Alex @Version：1.3
 * @create: 2020/10/14 17:12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SaleProductPriceTypeEnums {

    PURCHASE_PRICE("PURCHASE_PRICE", "采购价格", 1), SCOOTER_SALSE_PRICE("SCOOTER_SALSE_PRICE", "车辆销售价", 2),
    COMBINATION_SALSE_PRICE("COMBINATION_SALSE_PRICE", "组合销售价", 3), PART_SALSE_PRICE("PART_SALSE_PRICE", "部件销售价", 4);

    private String code;

    private String message;

    private Integer value;
}
