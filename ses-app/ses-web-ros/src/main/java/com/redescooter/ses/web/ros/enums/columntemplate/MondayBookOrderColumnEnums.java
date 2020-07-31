package com.redescooter.ses.web.ros.enums.columntemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    ID_CP("tags5","ID CP"),
    RESP("dup__of_resp","Resp"),
    FIRST_CONTACT("last_contacted","First contact"),
    LAST_CONTACTED("due_date","Last contacted"),
    NEXT_CONTACT("date","Next contact"),
    TYPE_PROSPECT("status1","Type prospect"),
    TYPE("status7","Type"),
    CONVERSION("type","Conversion"),
    PRENOM("text06","Prenom"),
    NOM("pr_nom","Nom"),
    TEL("phone","Tel"),
    EMAIL("text1","Email"),
    NB_SCOOTERS("chiffres","Nb scooters"),
    MODEL("text2","Model"),
    COULEUR("text8","Couleur"),
    VOTRE_MESSAGE("text47","Votre Message"),
    CODE_POSTAL("text4","Code Postal"),
    VILLE("text8","ville");


    private String id;

    private String title;
}
