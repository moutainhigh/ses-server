package com.redescooter.ses.api.common.enums.website;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductColorEnums
 * @description: ProductColorEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:42
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductColorEnums {

    RED("RED", "red", "1"),
    BLUE("BLUE", "blue", "2"),
    CHAMPAGNE("CHAMPAGNE", "champagne", "3"),
    CARBON("CARBON", "carbon", "4"),
    BLACK("BLACK","black","5"),
    ;

    public static ProductColorEnums getProductColorEnumsByValue(String value) {
        for (ProductColorEnums item : ProductColorEnums.values()) {
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
