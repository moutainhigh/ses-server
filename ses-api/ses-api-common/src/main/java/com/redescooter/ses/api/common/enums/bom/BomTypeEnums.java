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
public enum BomTypeEnums {

    PARTS("PARTS", "零部件", "1"),
    ACCESSORY("ACCESSORY", "配件", "2"),
    BATTERY("BATTERY", "电池", "3");

    private String code;

    private String message;

    private String value;
}
