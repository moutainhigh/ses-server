package com.redescooter.ses.api.common.enums.restproductionorder.outbound;

/**
 * 出库单类型枚举类
 * @author assert
 * @date 2021/1/11 15:54
 */
public enum OutWhOrderTypeEnum {

    TRANSFER_OUT_OF_STOCK(1, "调拨出库"),
    COMBINATION_OUT_OF_STOCK(2, "组装备料出库"),
    RETURN_AND_EXCHANGE(3, "退换出库"),
    OTHER(4, "其它"),
    REPAIR_OUT_OF_STOCK(5, "返修出库"),
    RETURN_OUT_OF_STOCK(6, "退货出库"),
    SALES_OUT_OF_STOCK(7, "销售出库")
    ;

    private Integer type;
    private String desc;

    OutWhOrderTypeEnum(Integer type, String desc) {
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
