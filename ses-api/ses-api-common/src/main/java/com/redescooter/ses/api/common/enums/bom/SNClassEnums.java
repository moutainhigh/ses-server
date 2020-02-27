package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName SNClassEnums
 * @Author Jerry
 * @date 2020/02/26 18:38
 * @Description:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SNClassEnums {

    SC("SC", "仅可采购", "1"),
    SSC("SSC", "可销售可采购", "2"),
    ;


    private String code;

    private String message;

    private String value;


    public static String getValueByCode(String code) {
        for (SNClassEnums item : SNClassEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getValue();
            }
        }
        return SNClassEnums.SC.getValue();
    }

    public static String checkCode(String code) {
        for (SNClassEnums item : SNClassEnums.values()) {
            if (item.getCode().equals(code)) {
                return item.getCode();
            }
        }
        return null;
    }

}
