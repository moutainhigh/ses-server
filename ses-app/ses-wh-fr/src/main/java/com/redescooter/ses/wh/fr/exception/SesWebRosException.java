package com.redescooter.ses.wh.fr.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSCRMControllerException
 * @author: Darren
 * @create: 2019/01/16 11:36
 */
public class SesWebRosException extends BusinessException {

    private static final String ROOT_CODE = "R000";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesWebRosException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesWebRosException(int code) {
        this(code, "");
    }

    public SesWebRosException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
