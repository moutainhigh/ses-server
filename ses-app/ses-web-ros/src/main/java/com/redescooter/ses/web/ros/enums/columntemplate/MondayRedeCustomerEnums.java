package com.redescooter.ses.web.ros.enums.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName MondayRedeEnum
 * @Description
 * @Author Charles
 * @Date 2021/06/22 13:57
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayRedeCustomerEnums {
    FIRST_NAME("first_name", "First name", "text"),
    LAST_NAME("last_name", "Last name", "text"),
    MOBILE_AREA_CODE("mobile_area_code", "Mobile Area code", "text"),
    PHONE("phone", "Phone", "text"),
    EMAIL("email", "Email", "text");

    private String id;

    private String title;

    private String type;

    public static String getEnumsTypeByTitle(String title) {
        for (MondayRedeCustomerEnums item : MondayRedeCustomerEnums.values()) {
            if (StringUtils.equals(title, item.getTitle())) {
                return item.getType();
            }
        }
        return null;
    }
}
