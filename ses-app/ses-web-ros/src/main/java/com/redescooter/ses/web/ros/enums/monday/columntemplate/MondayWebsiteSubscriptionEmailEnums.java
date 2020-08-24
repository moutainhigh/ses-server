package com.redescooter.ses.web.ros.enums.monday.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:MondayWebsiteSubscriptionEmailEnums
 * @description: MondayWebsiteSubscriptionEmailEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/04 13:23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayWebsiteSubscriptionEmailEnums {

    COURRIER("text","Courrier","text"),
    ;

    private String id;

    private String title;

    private String type;

    public static String getEnumsTypeByTitle(String title) {
        for (MondayWebsiteSubscriptionEmailEnums item : MondayWebsiteSubscriptionEmailEnums.values()) {
            if (StringUtils.equals(title, item.getTitle())) {
                return item.getType();
            }
        }
        return null;
    }
}
