package com.redescooter.ses.api.common.enums.production.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:OpeAssemblyBStatusEnums
 * @description: OpeAssemblyBStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/02 14:00
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OpeAssemblyBStatusEnums {

    UNDONE("UNDONE", "完成", "1"),
    COMPLETE("COMPLETE", "未完成", "2");

    private String code;

    private String message;

    private String value;
}
