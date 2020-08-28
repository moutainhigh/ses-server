package com.redescooter.ses.web.ros.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ThirdExceptionCodeEnums {

    SELLSY_CLIENT_IS_NOT_EXIST(11000, "Sellsy客户不存在"),

    SELLSY_DOCUMENT_IS_ALREADY_EXIST(11001, "发票已经存在"),

    SELLSY_DOCUMNT_RATECATEGORY_IS_EMPTY(11002, "税率不存在"),

    SELLSY_DOCUMENT_CURRENCY_IS_NOT_EXIST(11003, "货币单位不存在"),

    SELLSY_DOCUMENT_USESERVICEDATES_IS_EMPTY(11004, "服务时间为空"),

    ;

    private int code;

    private String message;
}
