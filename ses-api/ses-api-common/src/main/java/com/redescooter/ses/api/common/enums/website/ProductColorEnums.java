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

    RED("RED", "红色", "1"),
    BLUE("BLUE", "蓝色", "2"),
    CHAMPAGNE("CHAMPAGNE", "香槟色", "3"),
    CARBON("CARBON", "炭灰色", "4"),
    ;

    private String code;

    private String message;

    private String value;
}
