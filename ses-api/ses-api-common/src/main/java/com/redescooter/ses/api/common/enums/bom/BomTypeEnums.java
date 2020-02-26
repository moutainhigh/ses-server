package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:BomTypeEnums
 * @description: BomTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 14:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  BomTypeEnums {

    All("All","所有的","1"),
    PARTS("PARTS","零部件","2"),
    ACCESSORY("ACCESSORY","配件","3"),
    BATTERY("BATTERY","电池","4");

    private String code;

    private String message;

    private String value;
}
