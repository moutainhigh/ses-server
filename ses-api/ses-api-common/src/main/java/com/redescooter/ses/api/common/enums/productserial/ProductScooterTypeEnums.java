package com.redescooter.ses.api.common.enums.productserial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductScooterTypeEnums
 * @description: ProductScooterTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/06 20:17
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductScooterTypeEnums {

   SCOOTER_125_CC("SCOOTER_125_CC","RS","1"),;

    private String code;

    private String message;

    private String value;
}
