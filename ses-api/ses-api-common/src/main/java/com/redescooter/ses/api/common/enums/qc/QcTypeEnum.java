package com.redescooter.ses.api.common.enums.qc;

/**
 * 质检类型枚举类
 * @author assert
 * @date 2021/1/25 18:24
 */
public enum QcTypeEnum {

    PURCHASE(1, "采购"),
    RETURN_MATERIAL(2, "退料"),
    PRODUCE(3, "生产"),
    SHIP(4, "发货"),
    REPAIR(5, "返修"),
    OTHER(6, "其它")
    ;

    private Integer type;
    private String desc;

    QcTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
