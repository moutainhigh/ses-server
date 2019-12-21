package com.redescooter.ses.api.common.exception;


public class BaseException extends RuntimeException {

    public static final String DEFAULE_ERRORCODE = "1";

    public static final String DEFAULT_ERRORMSG = "unknown error";

    private String errorCode = DEFAULE_ERRORCODE;

    private String errorMessage;

    private boolean i18nTranslated = false;

    public BaseException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseException(String errorCode, String errorMessage, Throwable t) {
        super(errorMessage, t);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isI18nTranslated() {
        return i18nTranslated;
    }

    public void setI18nTranslated(boolean i18nTranslated) {
        this.i18nTranslated = i18nTranslated;
    }


}
