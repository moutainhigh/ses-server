package com.redescooter.ses.api.proxy.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: ProxyException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class ProxyException extends BusinessException {

    private static final String ROOT_CODE = "S007";

    private static final int DEFAULT_ERROR_CODE = 1;

    public ProxyException() {
        this(DEFAULT_ERROR_CODE);
    }

    public ProxyException(int code) {
        this(code, "");
    }

    public ProxyException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
