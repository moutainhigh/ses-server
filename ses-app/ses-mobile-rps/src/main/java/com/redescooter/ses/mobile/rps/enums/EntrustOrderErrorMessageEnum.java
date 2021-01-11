package com.redescooter.ses.mobile.rps.enums;

/**
 * 委托单异常消息枚举类
 * @author assert
 * @date 2021/1/11 18:17
 */
public enum EntrustOrderErrorMessageEnum {

    /**
     * 委托单异常消息
     */
    ;

    private Integer errorType;
    private String errorMsg;

    EntrustOrderErrorMessageEnum(Integer errorType, String errorMsg) {
        this.errorType = errorType;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
