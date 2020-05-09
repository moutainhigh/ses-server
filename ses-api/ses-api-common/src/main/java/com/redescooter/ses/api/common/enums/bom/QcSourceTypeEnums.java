package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QcSourceTypeEnums
 * @description: QcSourceTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 10:50
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcSourceTypeEnums {

    EXCEL("EXCEL", "模板导入", "1"),
    MANUAL_ENTRY("MANUAL_ENTRY", "手动录入", "2");

    private String code;

    private String message;

    private String value;
}
