package com.redescooter.ses.api.common.enums.wms;

/**
 * 仓库类型枚举类
 * @author assert
 * @date 2021/1/25 11:08
 */
public enum WmsStockTypeEnum {

    CHINA_WAREHOUSE(1, "中国仓库"),
    FRENCH_WAREHOUSE(2, "法国仓库");

    private Integer type;
    private String desc;

    WmsStockTypeEnum(Integer type, String desc) {
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
