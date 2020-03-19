package com.redescooter.ses.api.common.enums.production.purchasing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QcStatusEnums
 * @description: QcStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 13:36
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcStatusEnums {
    PASS("PASS", "通过", "1"),
    FAIL("FAIL", "失败", "2");

    private String code;

    private String message;

    private String value;
}
