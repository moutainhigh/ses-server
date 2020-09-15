package com.redescooter.ses.web.ros.enums.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:MondayBookOrderColumnEnums
 * @description: MondayBookOrderColumnEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/24 18:07
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayBookOrderColumnEnums {

    FIRST_CONTACT("first_contact8", "First contact", "date"),
    LAST_CONTACTED("due_date", "Last contacted","date"),
    NEXT_CONTACT("date", "Next contact","date"),
    PRENOM("text06", "Prenom","text"),
    NOM("pr_nom", "Nom","text"),
    TEL("phone", "Tel","phone"),
    EMAIL("text1", "Email","text"),
    NB_SCOOTERS("chiffres", "Nb scooters","text"),
    MODEL("text2", "Model","text"),
    VOTRE_MESSAGE("text47", "Votre Message","text"),
    CODE_POSTAL("text4", "Code Postal","text"),
    VILLE("text", "ville", "text"), COULEUR("text8", "Couleur", "text"),
    QUANTITÉ_DE_BATTERIE("couleur2", "Quantité de batterie", "text"), ADDRESSE("adresse5", "Adresse", "text"),

    ;

    private String id;

    private String title;

    //数据类型
    private String type;

    public static String getEnumsTypeByTitle(String title) {
        for (MondayBookOrderColumnEnums item : MondayBookOrderColumnEnums.values()) {
            if (StringUtils.equals(title, item.getTitle())) {
                return item.getType();
            }
        }
        return null;
    }
}
