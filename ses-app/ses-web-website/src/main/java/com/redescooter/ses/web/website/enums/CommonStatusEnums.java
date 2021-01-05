package com.redescooter.ses.web.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author jerry
 * @Date  2021/1/4 1:02 下午
 * @Description  通用状态
 **/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CommonStatusEnums {

    NORMAL("正常","NORMAL",1),
    INVALID("失效","INVALID",-1),;

    private String remark;

    private String code;

    private int value;

    public static String getEnumsTypeByValue(int value) {
        for (CommonStatusEnums item : CommonStatusEnums.values()) {
            if (value == item.value) {
                return item.getCode();
            }
        }
        return null;
    }
}
