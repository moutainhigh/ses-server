package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentTimeEnums {
    ADVANCE("ADVANCE","ADVANCE",1),
    POSTPONE("POSTPONE","POSTPONE",2),
    PUNCTUALITY("PUNCTUALITY","PUNCTUALITY",3);
    private String remark;

    private String code;

    private int value;

    public static String getEnumsCodeByValue(int value) {
        for (PaymentTimeEnums item : PaymentTimeEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
