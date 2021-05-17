package com.redescooter.ses.api.common.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ScooterTypeEnum {

    // 目前只有R2A
    R2A("R2A", "二轮摩托车");

    // R3A("R3A", "三轮摩托车");

    private String code;

    private String msg;

}
