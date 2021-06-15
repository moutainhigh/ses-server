package com.redescooter.ses.mobile.wh.ch.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesMobileChWhException extends BusinessException {

    private static final String ROOT_CODE = "WC000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesMobileChWhException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesMobileChWhException(int code) {
        this(code, "");
    }

    public SesMobileChWhException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
