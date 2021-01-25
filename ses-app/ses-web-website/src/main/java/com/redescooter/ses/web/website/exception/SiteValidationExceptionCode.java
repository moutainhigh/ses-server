package com.redescooter.ses.web.website.exception;

/**
 * @description: SiteValidationExceptionCode
 * @author: Jerry
 * @create: 03/01/2021 10:50
 */
public interface SiteValidationExceptionCode {

    //10000  TODO 系统公用异常
    //token不存在
    int TOKEN_NOT_EXIST = 10001;
    //用户不存在
    int USER_NOT_EXIST = 10002;
    //密码为空
    int PASSWORD_EMPTY = 10003;
    //邮箱为空
    int EMAIL_EMPTY = 10004;
    //账户未激活
    int ACCOUNT_NOT_ACTIVATED = 10005;
    //密码错误
    int PASSROD_WRONG = 10006;
    //账户被冻结
    int THE_ACCOUNT_HAS_BEEN_FROZEN = 10007;
    //账户被注销了
    int ACCOUNT_CANCELLED = 10008;
    //账号过期
    int ACCOUNT_EXPIRED = 10009;
    //密码不一致
    int INCONSISTENT_PASSWORD = 10010;
    //账户已经存在了
    int ACCOUNT_ALREADY_EXIST = 10011;
    //非法访问
    int TOKEN_MESSAGE_IS_FALSE = 10012;
    //systemid不匹配
    int SYSTEMID_IS_NOT_MATCH = 10013;
    //appid不匹配
    int COUNTRY_CANNOT_EMPTY = 10014;
    //国家为空
    int APPID_IS_NOT_MATCH = 10015;
    //语言为空
    int LANGUAGE_CANNOT_EMPTY = 10016;
    //客户端类型为空
    int CLIENTTYPE_CANNOT_EMPTY = 10017;
    //客户端IP为空
    int CLIENTIP_CANNOT_EMPTY = 10018;
    //时区为空
    int TIMEZONE_CANNOT_EMPTY = 10019;
    //版本号为空
    int VERSION_CANNOT_EMPTY = 10020;
    //参数错误
    int PARAM_ERROR = 10021;
    //长度过长，长度为2-20字符
    int CHARACTER_IS_TOO_LONG = 10022;
    //长度过短，长度为2-20字符
    int CHARACTER_IS_TOO_SHORT = 10023;
    //国家手机号编码不能为空
    int COUNTRY_CODE_IS_EMPTY = 10024;
    //URL不能为空
    int URL_IS_EMPTY = 10025;
    //名子不能为空
    int FIRST_NAME_IS_EMPTY = 10026;
    //姓氏不能为空
    int LAST_NAME_IS_EMPTY = 10027;
    //主键不能为空
    int ID_IS_EMPTY = 10028;
    //消息不能为空
    int MESSAGE_IS_EMPTY = 10031;
}
