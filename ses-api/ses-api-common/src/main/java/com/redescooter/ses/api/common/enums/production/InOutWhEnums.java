package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:InOutWhEnter
 * @description: InOutWhEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/28 10:22
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InOutWhEnums {

    IN("IN", "入库", "1"),
    OUT("OUT", "出库", "2");

    private String code;

    private String message;

    private String value;
}
