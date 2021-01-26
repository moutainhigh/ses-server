package com.redescooter.ses.api.common.enums.wms;

/**
 * 库存状态枚举类
 * @author assert
 * @date 2021/1/25 11:10
 */
public enum WmsStockStatusEnum {

    DRAFT(0, "草稿,待入库"),
    AVAILABLE(1, "可用"),
    UNAVAILABLE(2, "不可用");

    private Integer status;
    private String desc;

    WmsStockStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
