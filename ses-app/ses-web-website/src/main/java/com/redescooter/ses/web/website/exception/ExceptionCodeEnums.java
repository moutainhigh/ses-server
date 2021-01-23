package com.redescooter.ses.web.website.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author JERRY
 * @version V1.1
 * @Date: 03/01/2021 10:50
 * @ClassName: ExceptionCodeEnums
 * @Function: 异常配置
 * TODO：规则：异常说明_ERROR
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCodeEnums {

    //  10000   系统公用异常

    TOKEN_NOT_EXIST(10001, "token不存在"),

    USER_NOT_EXIST(10002, "用户不存在"),

    PASSWORD_EMPTY(10003, "密码为空"),

    EMAIL_EMPTY(10004, "邮箱为空"),

    ACCOUNT_NOT_ACTIVATED(10005, "账户未激活"),

    PASSROD_WRONG(10006, "密码错误"),

    THE_ACCOUNT_HAS_BEEN_FROZEN(10007, "账户被冻结"),

    ACCOUNT_CANCELLED(10008, "账户被注销了"),

    ACCOUNT_EXPIRED(10009, "账号过期"),

    INCONSISTENT_PASSWORD(10010, "密码不一致"),

    ACCOUNT_ALREADY_EXIST(10011, "账户已经存在了"),

    TOKEN_MESSAGE_IS_FALSE(10012, "非法访问"),

    SYSTEMID_IS_NOT_MATCH(10013, "systemid不匹配"),

    COUNTRY_CANNOT_EMPTY(10014, "appid不匹配"),

    APPID_IS_NOT_MATCH(10015, "国家为空"),

    LANGUAGE_CANNOT_EMPTY(10016, "语言为空"),

    CLIENTTYPE_CANNOT_EMPTY(10017, "客户端类型为空"),

    CLIENTIP_CANNOT_EMPTY(10018, "客户端IP为空"),

    TIMEZONE_CANNOT_EMPTY(10019, "时区为空"),

    VERSION_CANNOT_EMPTY(10020, "版本号为空"),

    PARAM_ERROR(10021, "参数错误"),

    UN_COMPLETE_ORDER_ERROR(10022, "存在未完成的订单"),

    ;


    private int code;

    private String message;

}
