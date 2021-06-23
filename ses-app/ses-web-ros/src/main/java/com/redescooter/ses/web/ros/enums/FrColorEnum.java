package com.redescooter.ses.web.ros.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FrColorEnum {

    ONE("1", "#2D2926"),

    TWO("2", "#776E64"),

    THREE("3", "#525354");

    private String key;

    private String value;

    public static String showKey(String value) {
        for (FrColorEnum o : FrColorEnum.values()) {
            if (o.getValue().equals(value)) {
                return o.getKey();
            }
        }
        return null;
    }

}
