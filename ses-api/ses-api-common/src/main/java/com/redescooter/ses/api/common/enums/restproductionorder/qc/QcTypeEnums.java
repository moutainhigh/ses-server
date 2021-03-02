package com.redescooter.ses.api.common.enums.restproductionorder.qc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 质检类型枚举
 * @Author Chris
 * @Date 2021/1/25 11:30
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcTypeEnums {

    PURCHASE("PURCHASE", "采购", 1),

    RETURN_MATERIAL("RETURN_MATERIAL", "退料", 2),

    PRODUCTION("PRODUCTION", "生产", 3),

    SEND_GOOD("SEND_GOOD", "发货", 4),

    REWORK("REWORK", "返修", 5),

    OTHER("OTHER", "其他", 6);

    private String code;

    private String message;

    private Integer value;

}
