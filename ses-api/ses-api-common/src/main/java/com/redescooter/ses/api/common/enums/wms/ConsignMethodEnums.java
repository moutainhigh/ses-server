package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ConsignMethodEnums
 * @description: ConsignMethodEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 14:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ConsignMethodEnums {

    EXPRESS("EXPRESS","快递","1"),
    FREIGHT_FORWARDING("FREIGHT_FORWARDING","货代","2");

    private String code;

    private String message;

    private String value;
}
