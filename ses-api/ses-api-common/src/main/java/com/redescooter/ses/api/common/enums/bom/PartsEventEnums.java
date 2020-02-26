package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName PartsStatusEnter
 * @Author Jerry
 * @date 2020/02/26 18:05
 * @Description:
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PartsEventEnums {

    CREATE("CREATE", "CREATE", "1"),
    ADD("ADD", "ADD", "2"),
    UPDATE("UPDATE", "UPDATE", "3"),
    DELETE("DELETE", "DELETE", "4"),
    ;

    private String code;

    private String message;

    private String value;
}
