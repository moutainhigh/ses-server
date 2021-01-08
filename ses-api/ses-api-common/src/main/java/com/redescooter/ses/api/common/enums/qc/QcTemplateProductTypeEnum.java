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
    PARTS_DRAFT(0, "部件草稿"),
    PARTS(1, "部件"),
    SCOOTER_DRAFT(2, "整车草稿"),
    SCOOTER(3, "整车"),
    COMBINATION_DRAFT(4, "组装件草稿"),
    COMBINATION(5, "组装件");

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
