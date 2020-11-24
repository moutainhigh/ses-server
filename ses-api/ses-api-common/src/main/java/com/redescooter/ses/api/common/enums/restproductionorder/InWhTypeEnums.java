package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  @author: Aleks
 *  @Date: 2020/11/12 15:47
 *  @version：
 *  @Description: 入库类型的枚举
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum InWhTypeEnums {
    PRODUCTIN_IN_WHOUSE("PRODUCTIN_IN_WHOUSE", "生产入库", 1),
    REPAIR_IN_WHOUSE("REPAIR_IN_WHOUSE", "返修入库", 2),
    PURCHASE_IN_WHOUSE("PURCHASE_IN_WHOUSE", "采购入库", 3),
    RETURN_MATERIAL_IN_WHOUSE("RETURN_MATERIAL_IN_WHOUSE", "退料入库", 4),
    OTHER("OTHER", "其他", 5),

    ;


    private String code;

    private String message;

    private Integer value;
}
