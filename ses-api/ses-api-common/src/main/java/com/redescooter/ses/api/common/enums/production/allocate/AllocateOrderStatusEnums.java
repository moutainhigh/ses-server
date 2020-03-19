package com.redescooter.ses.api.common.enums.production.allocate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AllocateOrderStatusEnums
 * @description: AllocateOrderStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 16:37
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AllocateOrderStatusEnums {

    PENDING("PENDING", "待处理", "1"),
    INPROGRESS("INPROGRESS", "进行中", "2"),
    INPRODUCTIONWH("INPRODUCTIONWH", "入库", "3"),
    CANCELLED("CANCELLED", "取消", "4");

    private String code;

    private String message;

    private String value;
}
