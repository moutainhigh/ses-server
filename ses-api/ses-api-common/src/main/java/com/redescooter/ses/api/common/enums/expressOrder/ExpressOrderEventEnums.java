package com.redescooter.ses.api.common.enums.expressOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExpressOrderEvent
 * @description: ExpressOrderEventEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/16 09:59
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExpressOrderEventEnums {

    UNASGN("UNASGN","待分配","1"),
    ASGN("ASGN","已分配","2"),
    SHIPPING("SHIPPING", " 正在配送 ","3"),
    COMPLETED("COMPLETED","完成","4"),
    REJECTED("REJECTED", "拒绝","5"),
    // 只是事件 不是订单状态
    CHANAGED("CHANAGED", "改变状态（拒绝-》待分配）","6")
    ;


    private String code;

    private String message;

    private String value;

}
