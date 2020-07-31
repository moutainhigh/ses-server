package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnEmailEnums
 * @description: MondayColumnEmailEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:51
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnEmailEnums {
    /**
     * {
     *   "email": "itsmyemail@mailserver.com",
     *   "text": "my email"
     * }
     */
    EMAIL("email", "邮箱标题"),
    TEXT("text", "文本"),
    ;

    private String title;

    private String message;

}
