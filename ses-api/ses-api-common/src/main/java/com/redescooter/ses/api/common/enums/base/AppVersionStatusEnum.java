package com.redescooter.ses.api.common.enums.base;

/**
 * 应用版本状态枚举类
 * @author assert
 * @date 2020/12/1 18:23
 */
public enum AppVersionStatusEnum {

    /**
     * 应用版本状态
     */
    UNRELEASED(0, "未发布"),
    RELEASED(1, "已发布"),
    ACTIVE(2, "使用中/生效中")
    ;

    private Integer status;
    private String desc;

    AppVersionStatusEnum(Integer status, String desc) {
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
