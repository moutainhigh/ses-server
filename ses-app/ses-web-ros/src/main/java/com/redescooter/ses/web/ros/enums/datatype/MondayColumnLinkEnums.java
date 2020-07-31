package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnLinkEnums
 * @description: MondayColumnLinkEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnLinkEnums {
    /**
     * {
     *   "url": "http://monday.com",
     *   "text": "go to monday!"
     * }
     */
    URL("url","链接"),
    TEXT("text","文本"),
    ;

    private String title;

    private String message;
}
