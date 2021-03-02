package com.redescooter.ses.web.website.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesWebRepairException extends BusinessException {

    private static final String ROOT_CODE = "A000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesWebRepairException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesWebRepairException(int code) {
        this(code, "");
    }

    public SesWebRepairException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
