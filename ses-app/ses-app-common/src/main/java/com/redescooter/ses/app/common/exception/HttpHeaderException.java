package com.redescooter.ses.app.common.exception;


/**
 * @description: HttpHeaderException
 * @author: Darren
 * @create: 2019/01/22 18:22
 */
public class HttpHeaderException extends RuntimeException {

    public HttpHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpHeaderException(String message) {
        super(message);
    }
}
