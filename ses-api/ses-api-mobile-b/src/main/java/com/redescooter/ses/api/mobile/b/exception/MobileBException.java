package com.redescooter.ses.api.mobile.b.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: MobileBException
 * @author: Darren
 * @create: 2019/02/26 10:35
 */
public class MobileBException extends BusinessException {

    private static final String ROOT_CODE = "S005";

    public MobileBException() {
        super();
    }

    public MobileBException(int code) {
        super(ROOT_CODE + code);
    }

    public MobileBException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
