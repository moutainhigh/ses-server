package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductTypeEnum {

    // 目前只有E50,E100,E125
    E25("1", "E25"),

    E50("2", "E50"),

    E100("3", "E100"),

    E125("4", "E125");

    private String code;

    private String msg;

    public static String showCode(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            for (ProductTypeEnum o : ProductTypeEnum.values()) {
                if (msg.contains(o.getMsg())) {
                    return o.getCode();
                }
            }
        }
        return null;
    }

}
