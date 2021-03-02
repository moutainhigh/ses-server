package com.redescooter.ses.api.common.enums.wms;

/**
 * 仓库类型枚举类型
 * @author assert
 * @date 2021/1/15 18:55
 */
public enum WmsTypeEnum {

    SCOOTER_WAREHOUSE(1, "车辆成品库"),
    COMBINATION_WAREHOUSE(2, "组装件成品库"),
    PARTS_WAREHOUSE(3, "部件原料库"),
    SCOOTER_UNQUALIFIED_WAREHOUSE(4, "车辆不合格品库"),
    COMBINATION_UNQUALIFIED_WAREHOUSE(5, "组装件不合格品库"),
    PARTS_UNQUALIFIED_WAREHOUSE(6, "部件不合格品库"),
    FR_SCOOTER_WAREHOUSE(7, "法国车辆库"),
    FR_COMBINATION_WAREHOUSE(8, "法国组装件库"),
    FR_PARTS_WAREHOUSE(9, "法国部件库")
    ;

    private Integer type;
    private String desc;

    WmsTypeEnum(Integer type, String desc) {
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
