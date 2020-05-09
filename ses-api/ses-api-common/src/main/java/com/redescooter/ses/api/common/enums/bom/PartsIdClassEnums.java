package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName PartsIdClassEnums
 * @Author Jerry
 * @date 2020/04/08 22:17
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PartsIdClassEnums {

    Y("Y", "有唯一编码", "1"),
    N("N", "没有唯一编码", "2");

    private String code;

    private String name;

    private String value;

}
