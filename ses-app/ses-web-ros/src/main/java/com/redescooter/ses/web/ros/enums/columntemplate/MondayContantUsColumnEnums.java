package com.redescooter.ses.web.ros.enums.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

//    ID_CP("tags5", "ID CP", "tag"),
//    RESP("dup__of_resp", "Resp", "multiple-person"),
    FIRST_CONTACT("last_contacted", "First contact", "date"),
    LAST_CONTACTED("due_date", "Last contacted", "date"),
    NEXT_CONTACT("date", "Next contact", "date"),
//    TYPE("status7", "Type", "color"),
//    CONVERSION("type", "Conversion", "color"),
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

}
