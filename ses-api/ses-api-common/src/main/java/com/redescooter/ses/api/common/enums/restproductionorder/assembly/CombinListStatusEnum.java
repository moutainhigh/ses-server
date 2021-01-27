package com.redescooter.ses.api.common.enums.restproductionorder.assembly;

/**
 * 组装清单状态枚举类
 * @author assert
 * @date 2021/1/27 19:47
 */
public enum CombinListStatusEnum {

    UN_ASSEMBLED(0, "待组装"),
    ASSEMBLED(1, "已组装");

    private Integer status;
    private String desc;

    CombinListStatusEnum(Integer status, String desc) {
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
