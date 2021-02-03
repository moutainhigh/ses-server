package com.redescooter.ses.api.common.enums.rps;

/**
 * RPS项目订单类型枚举类
 * @author assert
 * @date 2021/2/3 9:48
 */
public enum RpsOrderTypeEnum {

    QC_ORDER(1, "质检单"),
    COMBINATION_ORDER(2, "组装单"),
    IN_WH_ORDER(3, "入库单"),
    OUT_WH_ORDER(4, "出库单"),
    ENTRUST_ORDER(5, "委托单");

    private Integer type;
    private String desc;

    RpsOrderTypeEnum(Integer type, String desc) {
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
