package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyProductEnums {
    CREATE("CREATE","创建","1"),
    REPLACE("REPLACE","替换","2"),
    CAN_DELETE("CAN_DELETE","日后可删除","3"),
    MYSELY_CREATE("MYSELY_CREATE","自行创建","4");

    private String code;

    private String message;

    private String value;
}
