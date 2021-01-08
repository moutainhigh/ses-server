package com.redescooter.ses.web.website.exception;


import com.redescooter.ses.api.common.exception.BusinessException;

/**
 * @author JERRY
 * @version V1.1
 * @Date: 03/01/2021 10:50
 * @ClassName: ExceptionCodeEnums
 * @Function: 异常配置
 */
public class SesWebsiteException extends BusinessException {

    private static final String ROOT_CODE = "W001";

    private static final int DEFAULT_ERROR_CODE = 1;

    public SesWebsiteException() {
        this(DEFAULT_ERROR_CODE);
    }

    public SesWebsiteException(int code) {
        this(code, "");
    }

    public SesWebsiteException(int code, String message) {
        super(ROOT_CODE + code, message);
    }
}
