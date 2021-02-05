package com.redescooter.ses.api.common.enums.qc;

/**
 * 质检模板产品类型枚举类
 * @author assert
 * @date 2021/1/6 15:51
 */
public enum QcTemplateProductTypeEnum {

    /**
     * 质检模板产品类型
     */
    PARTS(1, "部件"),
    COMPONENTS(2, "零件"),
    BATTERY(3, "电池"),
    SCOOTER(4, "整车"),
    COMBINATION(5, "组装件"),
    ECU(6, "ECU仪表");

    private Integer type;
    private String desc;

    QcTemplateProductTypeEnum(Integer type, String desc) {
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
