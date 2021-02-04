package com.redescooter.ses.api.common.enums.bom;

/**
 * 产品bom状态枚举类
 * @author alex
 * @date 2021/1/4 18:02
 */
public enum ProductionBomStatusEnums {

    /**
     * 产品bom状态
     */
    ACTIVE("ACTIVE", 1, "已激活"),
    TO_BE_ACTIVE("TO_BE_ACTIVE", 2, "待激活"),
    EXPIRED("EXPIRED", 3, "已过期"),
    ABOLISHED("ABOLISHED", 4, "已作废");

    private String code;
    private Integer value;
    private String message;

    ProductionBomStatusEnums(String code, Integer value, String message) {
        this.code = code;
        this.value = value;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
