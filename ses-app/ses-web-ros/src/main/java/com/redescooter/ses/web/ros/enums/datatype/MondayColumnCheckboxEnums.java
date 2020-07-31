package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnCheckboxEnums
 * @description: MondayColumnCheckboxEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:27
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnCheckboxEnums {
    /**
     * {
     *   "checked": "true"
     * }
     */
    CHECK_TRUE("checked", "确认选择", Boolean.TRUE),
    CHECK_FALSE("checked", "取消选择", Boolean.FALSE);

    private String title;

    private String message;

    private Boolean result;

}
