package com.redescooter.ses.mobile.rps.enums;

/**
 * 出库单异常消息枚举类
 * @author assert
 * @date 2021/1/11 18:16
 */
public enum OutWhOrderErrorMessageEnum {

    /**
     * 出库单异常消息
     */
    OUT_WAREHOUSE_ORDER_IS_NOT_EXISTS(1, "出库单不存在"),
    QUALITY_INSPECTION_COMPLETED(2, "当前部件数量已完成,无需再质检"),
    QUALITY_INSPECTION_NOT_STARTED(3, "还未开始质检,无法提交出库"),
    PRODUCT_IS_NOT_EXISTS(4, "该产品不在出库列表中, 请核对!"),
    QUALITY_INSPECTION_FAILED(5, "质检失败")
    ;

    private Integer errorType;
    private String errorDesc;

    OutWhOrderErrorMessageEnum(Integer errorType, String errorDesc) {
        this.errorType = errorType;
        this.errorDesc = errorDesc;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

}
