package com.redescooter.ses.web.ros.enums.distributor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DistributorTypeEnum {

    SALE("1", "Sales"),

    REPAIR("2", "Repair");

    private String code;

    private String msg;

    public static String showMsg(String code) {
        for (DistributorTypeEnum o : DistributorTypeEnum.values()) {
            if (o.getCode().equals(code)) {
                return o.getMsg();
            }
        }
        return null;
    }

}
