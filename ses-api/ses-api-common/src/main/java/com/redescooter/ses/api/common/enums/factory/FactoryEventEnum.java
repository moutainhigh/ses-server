package com.redescooter.ses.api.common.enums.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FactoryEventEnum {

    /**
     * 创建
     */
    CREATE("CREATE", "CREATE", "1"),
    /**
     * 修改
     */
    MODIFY("MODIFY", "MODIFY", "2"),
    ;

    private String code;

    private String message;

    private String value;
}
