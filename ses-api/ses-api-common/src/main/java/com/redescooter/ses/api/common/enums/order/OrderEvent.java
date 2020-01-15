package com.redescooter.ses.api.common.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

/**
 * @ClassName:OrderEvent
 * @description: OrderEvent
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 17:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum OrderEvent {

    PENDING("PENDING", "待分配", "1"),
    ASSIGNED("ASSIGNED", "已分配", "2"),
    INPROGRESS("INPROGRESS", "正在配送 ", "3"),
    DECLINED("DECLINED", "拒绝", "4"),
    CANCELLED("CANCELLED", "取消", "5"),
    DELIVERED("DELIVERED", "完成", "6"),
    CHANGED("CHANGED", "重新分配", "7"),
    ;

    private String code;

    private String message;

    private String value;
}
