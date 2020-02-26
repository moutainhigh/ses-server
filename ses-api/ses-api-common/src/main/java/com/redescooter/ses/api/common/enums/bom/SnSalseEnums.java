package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SnSalseEnums
 * @description: SnSalseEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 14:26
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SnSalseEnums {

    All("All","所有的","1"),
    SC("SC","可采购","2"),
    SCC("SCC","可采购，可外销","3"),;
    private String code;

    private String message;

    private String value;
}
