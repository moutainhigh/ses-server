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
public enum TaskTimeCountEnums {

    TODAY_NUM("TODAY_NUM", "今日统计", "1"),
    HISTORY_NUM("HISTORY_NUM", "历史统计", "2"),;


    private String code;

    private String message;

    private String value;
}
