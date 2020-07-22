package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SourceTypeEnter
 * @description: SourceTypeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/28 10:27
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SourceTypeEnums {

    PURCHAS("PURCHAS", "采购", "1"),
    ALLOCATE("ALLOCATE", "调拨", "2"),
    ASSEMBLY("ASSEMBLY", "组装", "3"),
    SCOOTER_ALLOCATE("SCOOTER_ALLOCATE","车辆分配","4"),
    WH_OUT("WH_OUT","生成出库","5"),
    ;

    private String code;

    private String message;

    private String value;

}
