package com.redescooter.ses.api.common.enums.website;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:productTypeEnums
 * @description: productTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:32
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductModelEnums {

    SCOOTER_125_CC("SCOOTER_125_CC","125CC","1");


    private String code;

    private String message;

    private String value;

}
