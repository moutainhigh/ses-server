package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerFormEnum {

    BUSINESS("1", "Company"),

    PERSONAL("2", "Personal");

    private String key;

    private String value;

    public static String showMsg(String key) {
        for (CustomerFormEnum o : CustomerFormEnum.values()) {
            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }
        return null;
    }

}
