package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 3:19 下午
 * @ClassName: GenderEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum GenderEnums {

    MEN("MEN", "男", "1"),
    WOMEN("WOMEN", "女", "0");

    private String code;

    private String message;

    //编码对应值
    private String value;

    public static GenderEnums getGenderEnums(String key) {
        for (GenderEnums item : GenderEnums.values()) {
            if (item.getValue().equals(key)) {
                return item;
            }
        }
        return null;
    }

}
