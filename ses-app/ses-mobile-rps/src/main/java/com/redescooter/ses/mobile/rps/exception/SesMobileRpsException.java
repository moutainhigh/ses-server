package com.redescooter.ses.mobile.rps.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesMobileRpsException extends BusinessException {

    private static final String ROOT_CODE = "RP000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesMobileRpsException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesMobileRpsException(int code) {
        this(code, "");
    }

    public SesMobileRpsException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
