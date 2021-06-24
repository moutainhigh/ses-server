package com.redescooter.ses.web.ros.enums.columntemplate;

import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationColumnEnter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MondayRedeEnum
 * @Description
 * @Author Charles
 * @Date 2021/06/22 13:57
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayRedeEnums {
    PULSE_MONDAY_TYPE("pulse_monday_type", "PULSE MONDAY TYPE", "text"),
    N_DE_SERIE_DU_SCOOTER("n_de_s_rie_du_scooter", "N de série du scooter", "text"),
    COC_EN_PJ("coc_en_pj", "COC en PJ", "text"),
    NOM_DE_LA_CONCESSION("nom_de_la_concession", "Nom de la concession", "text"),
    ADDRESS_DE_LA_CONCESSION("address_de_la_concession", "Address de la concession", "text"),
    E50_OU_E100("e50_ou_e100", "E50 ou E100", "text"),
    NOMBRE_DE_BATTERIE("nombre_de_batterie", "Nombre de batterie", "text"),
    N_DE_SERIE_DE_LA_BATTERIE("n_de_s_rie_de_la_batterie", "N de série de la batterie", "text"),
    ID_DASHBOARD("id_dashboard", "ID Dashboard", "text"),
    IMMAT("immat", "Immat", "text"),
    PRENOM("pr_nom", "prénom", "text"),
    NOM("nom", "Nom", "text"),
    ADDRESS_CLIENT("address_client", "Address client", "text"),
    CP("cp", "CP", "text"),
    VILLE("ville", "Ville", "text"),
    PAYS("pays", "Pays", "text"),
    TELEPHONE("t_l_phone", "téléphone", "text"),
    EMAIL("email", "Email", "text"),
    MOT_DE_PASSE("mot_de_passe", "Mot de passe", "text");

    private String id;

    private String title;

    private String type;

    public static String getEnumsTypeByTitle(String title) {
        for (MondayRedeEnums item : MondayRedeEnums.values()) {
            if (StringUtils.equals(title, item.getTitle())) {
                return item.getType();
            }
        }
        return null;
    }

    public static Map<String, String> getMondayRedeTitle() {
        Map<String, String> redeCustomerMap = new HashMap<>();
        for (MondayRedeEnums item : MondayRedeEnums.values()) {
            redeCustomerMap.put(item.getTitle(), item.getId());
        }
        return redeCustomerMap;
    }

    public static List<MondayMutationColumnEnter> init(int boardId) {
        List<MondayMutationColumnEnter> columnEnterList = new ArrayList<>();
        for (MondayRedeEnums enums : MondayRedeEnums.values()) {
            MondayMutationColumnEnter columnEnter = new MondayMutationColumnEnter();
            columnEnter.setBoardId(boardId);
            columnEnter.setColumnType(enums.getType());
            columnEnter.setTitle(enums.getTitle());
            columnEnterList.add(columnEnter);
        }
        return columnEnterList;
    }
}
