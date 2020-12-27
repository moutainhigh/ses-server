package com.redescooter.ses.web.ros.enums.distributor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {

    ENABLE(1, "启用"),

    DISABLE(2, "禁用");

    private Integer code;

    private String msg;

    public static String showMsg(Integer code) {
        for (StatusEnum o : StatusEnum.values()) {
            if (o.getCode().equals(code)) {
                return o.getMsg();
            }
        }
        return null;
    }

}
