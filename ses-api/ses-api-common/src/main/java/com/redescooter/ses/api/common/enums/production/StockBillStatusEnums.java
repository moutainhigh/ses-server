package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:StockBillStatusEnums
 * @description: StockBillStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/28 10:33
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StockBillStatusEnums {


    NORMAL("NORMAL", "正常", "1"),
    ABNORMAL("ABNORMAL", "异常", "2");

    private String code;

    private String message;

    private String value;
}
