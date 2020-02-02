package com.redescooter.ses.api.common.enums.expressOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 17/1/2020 5:41 下午
 * @ClassName: ExpressOrderStatusEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExpressOrderStatusEnums {
    UNASGN("UNASGN","待分配","1"),
    ASGN("ASGN","已分配","2"),
    SHIPPING("SHIPPING", " 正在配送 ","3"),
    COMPLETED("COMPLETED","完成","4"),
    REJECTED("REJECTED", "拒绝","5"),
    ;

    private String code;

    private String message;

    private String value;

}
