package com.redescooter.ses.api.common.enums.qc;

/**
 * 质检状态枚举类
 * @author assert
 * @date 2021/1/26 10:05
 */
public enum QcStatusEnum {

    PENDING_QUALITY_INSPECTION(1, "待质检"),
    QUALITY_INSPECTION(10, "质检中"),
    QUALITY_INSPECTION_COMPLETED(20, "质检完成");

    private Integer status;
    private String desc;

    QcStatusEnum(Integer status, String desc) {
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
