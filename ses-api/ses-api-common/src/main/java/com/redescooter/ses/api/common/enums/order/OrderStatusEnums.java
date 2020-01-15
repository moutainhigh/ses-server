package com.redescooter.ses.api.common.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:OrderStatusEnums
 * @description: OrderStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 17:48
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatusEnums {

    PENDING("PENDING", "待分配", "1"),
    ASSIGNED("ASSIGNED", "已分配", "2"),
    INPROGRESS("INPROGRESS", "正在配送 ", "3"),
    DECLINED("DECLINED", "拒绝", "4"),
    CANCELLED("CANCELLED", "取消", "5"),
    DELIVERED("DELIVERED", "完成", "6");

    private String code;

    private String message;

    private String value;
}
