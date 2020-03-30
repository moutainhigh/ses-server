package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WhseTypeEnums
 * @description: WhseTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/27 18:31
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WhseTypeEnums {

    PURCHAS("PURCHAS", "采购", "1"),
    ALLOCATE("ALLOCATE", "调拨", "2"),
    ASSEMBLY("ASSEMBLY", "组装", "3");

    private String code;

    private String message;

    private String value;
}

