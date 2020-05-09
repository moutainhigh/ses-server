package com.redescooter.ses.api.common.enums.productserial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductVersionEnums
 * @description: ProductVersionEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/06 20:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductVersionEnums {

    INIT_VERSION("RE0", "初始版本", "1"),
    ;

    private String code;

    private String message;

    private String value;
}
