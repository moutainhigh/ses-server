package com.redescooter.ses.web.ros.enums.monday.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnHourEnums
 * @description: MondayColumnHourEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:53
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnHourEnums {
    /**
     * {
     *   "hour": 16,
     *   "minute": 42
     * }
     */
    HOUR("hour","小时"),
    MINUTE("minute","分钟"),
    ;

    private String title;

    private String message;

}
