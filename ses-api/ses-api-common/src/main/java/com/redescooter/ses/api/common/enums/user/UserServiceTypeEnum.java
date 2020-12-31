package com.redescooter.ses.api.common.enums.user;

/**
 * 用户业务类型枚举类
 * @author assert
 * @date 2020/11/18 16:32
 */
public enum UserServiceTypeEnum {

    /**
     * 用户业务类型
     */
    B(1, "B端用户"),
    C(2, "C端用户");

    private Integer type;
    private String desc;

    UserServiceTypeEnum(Integer type, String desc) {
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
