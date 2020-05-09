package com.redescooter.ses.api.hub.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @description: SaaSDriverControllerException
 * @author: Darren
 * @create: 2019/02/26 10:35
 */
public class SeSHubException extends BusinessException {

    private static final String ROOT_CODE = "S006";

    public SeSHubException() {
        super();
    }

    public SeSHubException(int code) {
        super(ROOT_CODE + code);
    }

    public SeSHubException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
