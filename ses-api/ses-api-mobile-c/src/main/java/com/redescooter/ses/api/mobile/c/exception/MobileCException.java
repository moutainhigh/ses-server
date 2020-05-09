package com.redescooter.ses.api.mobile.c.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: MobileBException
 * @author: Darren
 * @create: 2019/02/26 10:35
 */
public class MobileCException extends BusinessException {

    private static final String ROOT_CODE = "S006";

    public MobileCException() {
        super();
    }

    public MobileCException(int code) {
        super(ROOT_CODE + code);
    }

    public MobileCException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
