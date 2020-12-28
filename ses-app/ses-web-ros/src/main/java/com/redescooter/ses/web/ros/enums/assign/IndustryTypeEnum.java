package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum IndustryTypeEnum {

    RESTAURANT("1", "餐厅"),

    EXPRESS("2", "快递");

    private String key;

    private String value;

    public static String showMsg(String key) {
        for (IndustryTypeEnum o : IndustryTypeEnum.values()) {
            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }
        return null;
    }

}
