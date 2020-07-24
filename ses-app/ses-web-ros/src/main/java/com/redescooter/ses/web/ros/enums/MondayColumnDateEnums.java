package com.redescooter.ses.web.ros.enums;

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

    DATE("date","日期"),

    TIME("time","时间");

    private String title;

    private String message;
}
