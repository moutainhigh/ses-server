package com.redescooter.ses.api.common.enums.productserial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WorldFactoryEnums
 * @description: WorldFactoryEnums 世界工厂 代码
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/06 20:14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WorldFactoryEnums {

    REDE_FACTORY("LEU", "REDE_FACTORY", "1"),

    ;


    private String code;

    private String message;

    private String value;
}
