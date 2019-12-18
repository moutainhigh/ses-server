package com.redescooter.ses.service.proxy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/11/01 11:12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCodeEnums {

    TOKEN_NOT_EXIST(10001, "token不存在"),
    // 业务异常
    SEND_CODE_FAILURE(20051, "发送邮件失败."),
    ;

    private int code;
    private String message;

    private static ExceptionCodeEnums getErrorCodeByCode(int code){
        for (ExceptionCodeEnums item : ExceptionCodeEnums.values()) {
            if(item.getCode()==code){
                return item;
            }
        }
        return null;
    }
}
