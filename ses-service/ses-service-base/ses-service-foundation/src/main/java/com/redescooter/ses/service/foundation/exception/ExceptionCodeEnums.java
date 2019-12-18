package com.redescooter.ses.service.foundation.exception;

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
