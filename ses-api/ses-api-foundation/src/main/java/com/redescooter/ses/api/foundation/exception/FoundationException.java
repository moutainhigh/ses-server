package com.redescooter.ses.api.foundation.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: FoundationException
 * @author: Darren
 * @create: 2019/02/20 11:03
 */
public class FoundationException extends BusinessException {

    private static final String ROOT_CODE = "BA001";

    public FoundationException() {
        super();
    }

    public FoundationException(int code) {
        super(ROOT_CODE + code);
    }

    public FoundationException(int code, String message) {
        super(ROOT_CODE + code, message);
    }

}
