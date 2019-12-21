package com.redescooter.ses.api.common.vo.base;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -3249679162904643876L;
    /**
     * 如果为true,则可以调用getResult,如果为false,则调用errorCode来获取出错信息
     */
    private boolean success;
    private T result;
    private String errorCode = "";
    private String errorMsg = "";

    public Response() {
        this.success = true;
    }

    public Response(T result) {
        this.success = true;
        this.result = result;
    }

    public static Response<Object> ok() {
        Response<Object> r = new Response<Object>();
        r.success = true;
        r.result = "success";
        return r;
    }

    public Response(boolean flag, T result) {
        this.success = flag;
        this.result = result;
    }

    public Response(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public Response(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setErrorCode(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.success = false;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}