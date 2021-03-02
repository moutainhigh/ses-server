package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date 2021/1/23 10:39 上午
 * @Description 官网订单状态枚举
 * 状态,√√√
 * Status: 1 NEWS, 2 TO_BE_PAID, 3 in_progress, 4 CANCELLED, 5 COMPLETED, 6 CLOSED
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SiteOrderStatusEnums {

    NEWS("新建", "news", 1),
    TO_BE_PAID("待支付", "TO_BE_PAID", 2),
    IN_PROGRESS("进行中", "IN_PROGRESS", 3),
    CANCELLED("取消", "CANCELLED", 4),
    COMPLETED("已完成", "COMPLETED", 5),
    CLOSED("关闭", "CLOSED", 5),
    ;

    private String remark;

    private String code;

    private Integer value;

    public static String getEnumsCodeByValue(int value) {
        for (SiteOrderStatusEnums item : SiteOrderStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }

    public static Integer checkValue(int value) {
        for (SiteOrderStatusEnums item : SiteOrderStatusEnums.values()) {
            if (value == item.value) {
                return item.getValue();
            }
        }
        return null;
    }
}
