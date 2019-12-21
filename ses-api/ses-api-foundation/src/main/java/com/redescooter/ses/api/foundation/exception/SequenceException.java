package com.redescooter.ses.api.foundation.exception;


/**
 * @description: SequenceException
 * @author: Darren
 * @create: 2019/01/09 11:14
 */
public class SequenceException extends Exception {

    private static final long serialVersionUID = 1L;

    public SequenceException() {
    }

    public SequenceException(String message) {
        super(message);
    }

    public SequenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceException(Throwable cause) {
        super(cause);
    }
}
