package com.redescooter.ses.api.common.enums.entrustorder;

/**
 * 委托单状态枚举类
 * @author assert
 * @date 2020/12/31 10:22
 */
public enum EntrustOrderEnum {

    /**
     * 委托单状态
     */
    TO_BE_DELIVERED(0, "待发货"),
    SIGN_FOR(10, "待签收"),
    HAVE_BEEN_RECEIVED(20, "已签收");

    private Integer status;
    private String desc;

    EntrustOrderEnum(Integer status, String desc) {
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
