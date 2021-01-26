package com.redescooter.ses.admin.dev.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Aleks
 * @Version：1.3
 * @create: 2019/11/01 11:22
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCodeEnums {

    //  10000   系统公用异常

    TOKEN_NOT_EXIST(10001, "token不存在"),

    USER_NOT_EXIST(10002, "用户不存在"),

    PASSWORD_EMPTY(10003, "密码为空"),

    PASSROD_WRONG(10004, "密码错误"),

    INCONSISTENT_PASSWORD(10005, "密码不一致"),

    ACCOUNT_ALREADY_EXIST(10006, "账户已经存在了"),

    SYSTEMID_IS_NOT_MATCH(10007, "systemid 不匹配"),

    APPID_IS_NOT_MATCH(10008, "appid 不匹配"),

    LANGUAGE_CANNOT_EMPTY(10009, "语言为空"),

    CLIENTTYPE_CANNOT_EMPTY(10010, "客户端类型为空"),

    CLIENTIP_CANNOT_EMPTY(10011, "客户端IP 为空"),

    TIMEZONE_CANNOT_EMPTY(10012, "时区为空"),

    VERSION_CANNOT_EMPTY(10013, "版本号为空"),

    COUNTRY_CANNOT_EMPTY(10014, "国家为空"),

    LOGIN_PSD_ERROER_NUM_MANY(10015, "密码错误次数过多，请一分钟之后再登陆"),

    LOGIN_PSD_ERROER_NEED_CODE(10016, "密码错误"),

    EMAIL_IS_NOT_ILLEGAL(10017, "邮箱不合法"),

    EAMIL_CODE_TIME_OUT(10018, "请先获取验证码或验证码已过期"),

    CODE_IS_WRONG(10019, "验证码错误"),

    TOKEN_MESSAGE_IS_FALSE(10020, "token过期"),

    // 业务异常 10031 - 99999
    GROUP_NOT_EXISTS(10031, "分组不存在"),

    COLOR_NOT_EXISTS(10032, "颜色不存在"),

    SCOOTER_TABLET_SN_EXISTS(10033, "车辆平板已存在"),

    DATA_FORMAT_ERROR(10034, "数据格式有误"),

    SCOOTER_NOT_EXISTS(10035, "车辆不存在"),

    SPECIFIC_TYPE_NOT_EXISTS(10036, "规格类型不存在"),

    EMAIL_NOT_NULL(10037, "邮箱不能为空"),

    DATA_EXCEPTION(10038, "数据异常"),

    SCOOTER_NOT_CLOSED(10039, "车辆未关闭"),

    CREATE_SCOOTER_ERROR(10040, "创建车辆信息失败")
    ;

    private int code;

    private String message;

}
