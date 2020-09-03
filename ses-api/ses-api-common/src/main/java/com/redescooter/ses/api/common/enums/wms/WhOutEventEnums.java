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
public enum WhOutEventEnums {

    PENDING("PENDING","新建","1"),
    START_PREPARE_MATERIAL("START_PREPARE_MATERIAL","开始备料","2"),
    PREPARE_MATERIAL("PREPARE_MATERIAL","备料","3"),
    OUT_WH("OUT_WH","出库","4"),
    IN_WH("IN_WH","入库","5"),
    CANCELLED("CANCELLED","取消","6");

    private String code;

    private String message;

    private String value;
}
