package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName:ScooterActionResult
 * @description: ScooterActionResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:59
 */
@Getter
@AllArgsConstructor
public enum ScooterActionResult {

    SUCCESS("SUCCESS","成功","1"),
    FAIL("FAIL","失败","2");

    private String code;

    private String message;

    private String value;
}
