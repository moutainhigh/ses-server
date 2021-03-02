package com.redescooter.ses.api.common.enums.base;

/**
 * 应用版本类型枚举类
 * @author assert
 * @date 2020/11/30 16:00
 */
public enum AppVersionTypeEnum {

    /**
     * 应用版本类型
     */
    IOS(1, "IOS", "IOS"),
    ANDROID(2, "ANDROID", "安卓"),
    SCS(3, "SCS", "车载平板"),
    SaaS(4, "SaaS","SaaS"),
    SERVER(5, "SERVER","后台服务"),
    ROS(6, "ROS","ROS内部管理系统")
    ;

    private Integer type;
    private String code;
    private String msg;

    AppVersionTypeEnum(Integer type, String code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

}
