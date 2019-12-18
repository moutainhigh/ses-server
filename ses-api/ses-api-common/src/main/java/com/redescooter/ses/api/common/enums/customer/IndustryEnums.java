package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:IndustryEnums
 * @description: IndustryEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/10 17:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum IndustryEnums {
    RESTAURANT("RESTAURANT", "餐厅"),
    EXPRESS_DELIVERY("EXPRESS_DELIVERY", "快递");

    private String code;

    private String message;
}
