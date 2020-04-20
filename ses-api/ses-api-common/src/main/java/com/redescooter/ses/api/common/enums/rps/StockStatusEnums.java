package com.redescooter.ses.api.common.enums.rps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassNamesockStuausEnums
 * @Description
 * @Author Joan
 * @Date2020/4/20 15:22
 * @Version V1.0
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StockStatusEnums {
    Available("WAIT","可用","1"),
    Broken("FAIL","破损","2");

    private String code;

    private String message;

    private String value;
}
