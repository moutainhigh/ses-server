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

    RED("RED","红色","1"),
    BLACK("BLACK","黑色","2"),
    YELLOW("YELLOW","黄色","3");

    private String code;

    private String message;

    private String value;
}
