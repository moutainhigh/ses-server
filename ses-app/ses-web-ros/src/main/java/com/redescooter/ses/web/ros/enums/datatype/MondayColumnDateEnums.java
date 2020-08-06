package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnDateEnums
 * @description: MondayColumnDateEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/24 16:45
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnDateEnums {
    /**
     * {
     *   "date": "2019-06-03",
     *   "time": "13:25:00"
     * }
     */
    DATE("date","日期"),

    TIME("time","时间");

    private String title;

    private String message;
}
