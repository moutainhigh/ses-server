package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WhOutStatusEnums
 * @description: WhOutStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 15:04
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WhOutStatusBEnums {

    UNPREPARED("UNPREPARED","未备料","1"),
    PREPARED("PREPARED","已备料","2");

    private String code;

    private String message;

    private String value;
}
