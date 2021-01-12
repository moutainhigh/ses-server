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
    PRODUCT_IS_NOT_EXISTS("ENTRUST_001", "该产品不在出库列表中,请核对!")
    ;

    private String errorCode;
    private String errorMsg;

    EntrustOrderErrorMessageEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
