package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyGeneralSortEnums {
    ASC("ASC", "正序"),
    DESC("DESC", "倒叙");

    private String code;

    private String message;
}
