package com.redescooter.ses.api.common.enums.website;

import com.redescooter.ses.api.common.enums.customer.CustomerCertificateTypeEnum;
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

    SCOOTER_50_CC("SCOOTER_50_CC", "50CC", "1"),
    SCOOTER_100_CC("SCOOTER_100_CC", "100CC", "2"),
    SCOOTER_125_CC("SCOOTER_125_CC", "125CC", "3"),
    ;


    public static ProductModelEnums getProductModelEnumsByValue(String value) {
        for (ProductModelEnums item : ProductModelEnums.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    private String code;

    private String message;

    private String value;

}
