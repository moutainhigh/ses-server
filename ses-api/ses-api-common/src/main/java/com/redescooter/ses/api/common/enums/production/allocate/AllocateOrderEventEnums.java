package com.redescooter.ses.api.common.enums.production.allocate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AllocateOrderEventEnums
 * @description: AllocateOrderEventEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 16:44
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AllocateOrderEventEnums {

    PENDING("PENDING", "创建", "1"),
    INPROGRESS("INPROGRESS", "备料", "2"),
    ALLOCATE("ALLOCATE", "调拨", "3"),
    INPRODUCTIONWH("INPRODUCTIONWH", "入库", "4"),
    CANCELLED("CANCELLED", "取消", "5");

    private String code;

    private String message;

    private String value;
}
