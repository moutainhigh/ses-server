package com.redescooter.ses.api.scooter.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: ScooterException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class ScooterException extends BusinessException {

    private static final String ROOT_CODE = "S002";

    private static final int DEFAULT_ERROR_CODE = 1;

    public ScooterException() {
        this(DEFAULT_ERROR_CODE);
    }

    public ScooterException(int code) {
        this(code, "");
    }

    public ScooterException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
