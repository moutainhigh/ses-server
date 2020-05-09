package com.redescooter.ses.api.common.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TaskEvent
 * @description: TaskEvent
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 17:55
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TaskEvent {

    PENDING("PENDING", "待配送", "1"),
    INPROGRESS("INPROGRESS", "配送中", "2"),
    DELIVERED("DELIVERED", "已配送", "3");

    private String code;

    private String message;

    private String value;
}
