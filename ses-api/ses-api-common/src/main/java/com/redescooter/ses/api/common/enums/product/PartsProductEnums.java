package com.redescooter.ses.api.common.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName PartsProductEnums
 * @Author Jerry
 * @date 2020/04/08 14:31
 * @Description:
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PartsProductEnums {

    UP("UP", "上架", "1"),
    DOWN("DOWN", "下架", "2");

    private String code;

    private String message;

    private String value;
}
