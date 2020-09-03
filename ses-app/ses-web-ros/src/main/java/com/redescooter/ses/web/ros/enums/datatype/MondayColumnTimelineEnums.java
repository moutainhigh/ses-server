package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnTimelineEnums
 * @description: MondayColumnTimelineEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 14:00
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnTimelineEnums {
    /**
     * {
     *   "from": "2019-06-03",
     *   "to": "2019-06-07"
     * }
     */
    FROM("from","从什时间开始"),
    TO("to","到什么时间结束");


    private String code;

    private String message;
}
