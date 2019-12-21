package com.redescooter.ses.web.delivery.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesWebDeliveryException extends BusinessException {

    private static final String ROOT_CODE = "A000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesWebDeliveryException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesWebDeliveryException(int code) {
        this(code, "");
    }

    public SesWebDeliveryException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
