package com.redescooter.ses.mobile.client.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesMobileClientException extends BusinessException {

    private static final String ROOT_CODE = "MC00";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesMobileClientException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesMobileClientException(int code) {
        this(code, "");
    }

    public SesMobileClientException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
