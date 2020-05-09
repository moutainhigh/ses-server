package com.redescooter.ses.api.common.enums.job;

import com.redescooter.ses.api.common.vo.base.IError;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 11:13 上午
 * @ClassName: JobDefaultError
 * @Function: TODO
 */
public enum JobDefaultError implements IError {
    /**
     * 未知错误
     */
    RJOB_SYSTEM_INTERNAL_ERROR("0000", "RJOB System Internal Error"),
    /**
     * 无效参数
     */
    INVALID_PARAMETER("0001", "Invalid Parameter"),
    /**
     * 服务不存在
     */
    SERVICE_NOT_FOUND("0002", "Service Not Found"),
    /**
     * 参数不全
     */
    PARAMETER_REQUIRED("0003", "Parameter required"),
    /**
     * JSON格式化出错
     */
    JSON_FORMAT_ERROR("0004", "json format error"),
    /**
     * 数据异常
     */
    RESULT_DATE_NULL("0005", "no data"),
    ;

    String errorCode;
    String errorMessage;
    private static final String NS = "RJOB";

    JobDefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getNameSpace() {
        return NS;
    }

    @Override
    public String getErrorCode() {
        return NS + "." + this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
