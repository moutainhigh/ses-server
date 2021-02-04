package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum NewInWhouseOrderStatusEnum {

    DRAFT("DRAFT", "草稿", 1),

    ALREADY_IN_WHOUSE("ALREADY_IN_WHOUSE", "已入库", 10);

    private String code;

    private String message;

    private Integer value;

}
