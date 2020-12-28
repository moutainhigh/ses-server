package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerTypeEnum {

    BUSINESS("1", "企业"),

    PERSONAL("2", "个人");

    private String key;

    private String value;

    public static String showMsg(String key) {
        for (CustomerTypeEnum o : CustomerTypeEnum.values()) {
            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }
        return null;
    }

}
