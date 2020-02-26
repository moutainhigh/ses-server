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
public enum PartsStatusEnums {

    /**
     * 正常
     */
    NORMAL("NORMAL", "NORMAL", "1"),
    ;

    private String code;

    private String message;

    private String value;
}
