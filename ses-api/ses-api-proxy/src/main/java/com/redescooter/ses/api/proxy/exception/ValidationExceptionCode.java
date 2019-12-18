package com.redescooter.ses.api.proxy.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {
    //主题 为空
    int SUBJECT_IS_EMPTY = 1001;
    // 收件人为空
    int RECIPIENT_IS_EMPTY = 1002;
    //邮件内容为空
    int CONTENT_IS_EMPTY = 1003;



}
