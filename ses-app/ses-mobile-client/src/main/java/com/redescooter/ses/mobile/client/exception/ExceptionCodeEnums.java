package com.redescooter.ses.mobile.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/11/01 11:22
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCodeEnums {

    //  10000   系统公用异常
    TOKEN_NOT_EXIST(10001, "token不存在"),

    TOKEN_MESSAGE_IS_FALSE(10002, "token过期"),

    USER_NOT_EXIST(10003, "用户不存在"),

    PASSWORD_EMPTY(10004, "密码为空"),

    ACCOUNT_NOT_ACTIVATED(10005, "账户未激活"),

    PASSROD_WRONG(10006, "密码错误"),

    THE_ACCOUNT_HAS_BEEN_FROZEN(10007, "账户被冻结"),

    ACCOUNT_CANCELLED(10008, "账户被注销了"),

    ACCOUNT_EXPIRED(10009, "账号过期"),

    AUTHORIZATION_FAILED(10010, "授权失败"),

    INCONSISTENT_PASSWORD(10011, "密码不一致"),

    ACCOUNT_ALREADY_EXIST(10012, "账户已经存在了"),

    SYSTEMID_IS_NOT_MATCH(10013, "SYSTEMID不匹配"),

    APPID_IS_NOT_MATCH(10014, "APPID不匹配"),

    LANGUAGE_CANNOT_EMPTY(10015, "语言为空"),

    CLIENTTYPE_CANNOT_EMPTY(10016, "客户端类型为空"),

    CLIENTIP_CANNOT_EMPTY(10017, "客户端IP为空"),

    TIMEZONE_CANNOT_EMPTY(10018, "时区为空"),

    VERSION_CANNOT_EMPTY(10019, "版本号为空"),

    COUNTRY_CANNOT_EMPTY(10020, "国家为空"),

    LOGIN_NAME_CANNOT_EMPTY(10021, "用户名不能为空"),

    PRIMARY_KEY_CANNOT_EMPTY(10022, "主键不能为空"),

    STATUS_CANNOT_EMPTY(10023, "状态不能为空"),

    TENANT_NOT_EXIST(10024, "租户不存在"),

    ACCOUNT_IS_NOT_EXIST(10025, "账户不存在"),

    ACCESS_DENIED(10026, "非法登录，授权失败，禁止访问"),

    CODE_IS_WRONG(10027, "验证码错误"),

    EMAIL_IS_EMPTY(10031, "邮件为空"),

    EVENT_ERROR(10032, "事件有误"),

    PHONE_IS_WRONG(10034, "电话号不正确"),

    SCOOTER_INFO_NOT_FIND(10035, "没有查到车辆信息"),

    BLUE_TOOTH_MAC_ADDRESS_IS_EMPTY(10036, "蓝牙地址为空")


    ;

    private int code;

    private String message;

}
