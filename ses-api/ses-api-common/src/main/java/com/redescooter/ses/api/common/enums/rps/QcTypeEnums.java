package com.redescooter.ses.api.common.enums.rps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QcTypeEnums
 * @description: QcTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 15:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcTypeEnums {

    WAIT("WAIT","待质检","1"),
    FAIL("FAIL","质检失败","2");

    private String code;

    private String message;

    private String value;
}
