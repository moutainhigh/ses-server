package com.redescooter.ses.web.ros.enums.salePrice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SalePriceEnum {

    E50(1, "E50+1Batterie"),

    E100(2, "E100+2Batterie"),

    E125(3, "E125+4Batterie");

    private Integer code;

    private String msg;

    public static String showMsg(Integer code) {
        for (SalePriceEnum o : SalePriceEnum.values()) {
            if (o.getCode().equals(code)) {
                return o.getMsg();
            }
        }
        return null;
    }

}
