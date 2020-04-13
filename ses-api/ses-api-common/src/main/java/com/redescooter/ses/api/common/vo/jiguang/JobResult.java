package com.redescooter.ses.api.common.vo.jiguang;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IError;
import io.swagger.annotations.ApiModel;

/**
 * description: JobResult
 * author: jerry.li
 * create: 2019-06-26 15:54
 */

@ApiModel(value = "job调用结果集", description = "job调用结果集")
public class JobResult extends GeneralResult {

    private static final long serialVersionUID = -5359531292427290394L;

    private String errorCode;
    private String errorMessage;
    private String extMessage;
    private Object result;
    private JobResult.Status status;

    public JobResult.Status getStatus() {
        return this.status;
    }

    public void setStatus(JobResult.Status status) {
        this.status = status;
    }

    public JobResult() {
        this.status = JobResult.Status.SUCCEED;
    }

    public JobResult(IError error) {
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
        this.status = JobResult.Status.FAILED;
    }

    public static JobResult success() {
        return new JobResult();
    }

    public static JobResult success(Object result) {
        JobResult JobResult = new JobResult();
        JobResult.setResult(result);
        return JobResult;
    }

    public static JobResult failure(IError error) {
        JobResult JobResult = new JobResult();
        JobResult.errorCode = error.getErrorCode();
        JobResult.errorMessage = error.getErrorMessage();
        JobResult.status = Status.FAILED;
        return JobResult;
    }

    public static JobResult failure(String message) {
        JobResult JobResult = new JobResult();
        JobResult.setErrorMessage(message);
        JobResult.status = Status.FAILED;
        return JobResult;
    }

    public static JobResult warring(Object result) {
        JobResult JobResult = new JobResult();
        JobResult.setResult(result);
        JobResult.status = Status.WARRING;
        return JobResult;
    }

    public static JobResult blunder(Object object){
        JobResult JobResult = new JobResult();
        JobResult.setResult(object);
        JobResult.status = Status.BLUNDER;
        return JobResult;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExtMessage() {
        return this.extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.errorCode != null) {
            sb.append("ErrorCode : ").append(this.errorCode).append("ErrorMessage : ").append(this.errorMessage).append("ExtMessage : " + this.extMessage);
        } else {
            sb.append("Succeed");
        }

        return sb.toString();
    }

    public enum Status {
        /**
         * 状态
         */
        SUCCEED,
        WARRING,
        FAILED,
        BLUNDER;//

        Status() {
        }
    }




}