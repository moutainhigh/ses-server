package com.redescooter.ses.service.scooter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/10/31 16:17
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionCodeEnums {

    //  10000   系统公用异常
    TOKEN_NOT_EXIST(10001, "token不存在"),;


    private int code;

    private String message;

    public static ExceptionCodeEnums getErrorCodeByCode(int code) {
        for (ExceptionCodeEnums item : ExceptionCodeEnums.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
