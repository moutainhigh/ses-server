package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductContractEnums
 * @description: ProductContractEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/23 19:14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductContractEnums {

    REDE("REDE", "REDE", "1");

    private String code;

    private String message;

    private String value;
}
