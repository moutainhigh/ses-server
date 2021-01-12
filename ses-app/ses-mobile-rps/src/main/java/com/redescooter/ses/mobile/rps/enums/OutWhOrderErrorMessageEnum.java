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
    QUALITY_INSPECTION_COMPLETED("OUT_WH_001", "已完成质检,无需再质检"),
    QUALITY_INSPECTION_NOT_STARTED("OUT_WH_002", "还未开始质检,无法提交出库"),
    PRODUCT_IS_NOT_EXISTS("OUT_WH_003", "该产品不在出库列表中, 请核对!"),
    OUT_OF_STOCK("OUT_WH_004", "出库单已出库"),
    QC_QTY_GREATER_THAN_QTY("OUT_WH_005", "质检数量不能大于应出库数量")
    ;

    private String errorCode;
    private String errorDesc;

    OutWhOrderErrorMessageEnum(String errorType, String errorDesc) {
        this.errorCode = errorType;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

}
