package com.redescooter.ses.web.ros.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public enum MondayColumnEnums {

    ID_CP("tags5","ID CP"),
    RESP("dup__of_resp","Resp"),
    FIRST_CONTACT("last_contacted","First contact"),
    LAST_CONTACTED("due_date","Last contacted"),
    NEXT_CONTACT("date","Next contact"),
    TYPE("status7","Type"),
    CONVERSION("type","Conversion"),
    PRENOM("text06","Prenom"),
    NOM("pr_nom","Nom"),
    TEL("phone","Tel"),
    EMAIL("text1","Email"),
    VOTRE_MESSAGE("text","Votre Message"),
    CODE_POSTAL("text7","Code Postal"),
    VILLE("text8","ville")
    ;


    private String id;

    private String title;

}
