package com.redescooter.ses.web.ros.enums.monday.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:MondayColumnEnums
 * @description: MondayColumnEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/24 15:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MondayContantUsColumnEnums {

    FIRST_CONTACT("last_contacted", "First contact", "date"),
    LAST_CONTACTED("due_date", "Last contacted", "date"),
    NEXT_CONTACT("date", "Next contact", "date"),
    PRENOM("text06", "Prenom", "text"),
    NOM("pr_nom", "Nom", "text"),
    TEL("phone", "Tel", "phone"),
    EMAIL("text1", "Email", "text"),
    VOTRE_MESSAGE("text", "Votre Message", "text"),
    CODE_POSTAL("text7", "Code Postal", "text"),
    VILLE("text8", "ville", "text");


    private String id;

    private String title;

    private String type;

    public static String getEnumsTypeByTitle(String title) {
        for (MondayContantUsColumnEnums item : MondayContantUsColumnEnums.values()) {
            if (StringUtils.equals(title, item.getTitle())) {
                return item.getType();
            }
        }
        return null;
    }

}
