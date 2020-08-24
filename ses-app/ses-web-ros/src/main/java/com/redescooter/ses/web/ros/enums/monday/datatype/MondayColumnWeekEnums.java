package com.redescooter.ses.web.ros.enums.monday.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnWeekEnums
 * @description: MondayColumnWeekEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:06
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnWeekEnums {
    /**
     * {
     *   "week": {
     *     "startDate": "2019-06-10",
     *     "endDate": "2019-06-16"
     *   }
     * }
     */
    WEEK("week","周"),
    STARTDATE("startDate","开始日期"),
    ENDDATE("endDate","结束日期"),;

    private String title;

    private String message;

}
