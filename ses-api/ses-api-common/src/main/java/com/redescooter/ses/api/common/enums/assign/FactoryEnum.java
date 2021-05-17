package com.redescooter.ses.api.common.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FactoryEnum {

    AOGE("1", "中国奥格");

    private String code;

    private String msg;

}
