package com.redescooter.ses.mobile.wh.fr.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesMobileFrWhException extends BusinessException {

    private static final String ROOT_CODE = "WF000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesMobileFrWhException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesMobileFrWhException(int code) {
        this(code, "");
    }

    public SesMobileFrWhException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
