package com.redescooter.ses.service.foundation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Alex @Version：1.3
 * @create: 2019/11/01 11:12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCodeEnums {

    // 10000 系统公用异常
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


    // 10030 之后业务异常
    RENEW_END_DATETIME_IS_NOT_AVAILABLE(10030, "续期结束时间不可用"),

    RENEW_START_DATETIME_IS_NOT_AVAILABLE(10031, "续期开始时间不可用"),

    USERPERMISSION_IS_NOT_EXIST(10032, "没有权限"),

    STATUS_IS_REASONABLE(10033, "状态不合理"),

    TENANT_ALREADY_EXIST(10034, "租户已经存在"),

    USERTOKEN_SERVICE_CODE_EXPIRED(10035, "验证码不存在"),

    USERTOKEN_SERVICE_CODE_WRONG(10036, "验证码错误"),

    DISTRIBUTIONRANGE_IS_EMPTY(10037, "配送范围 为空"),

    ESTIMATEDDURATION_IS_EMPTY(10038, "配送时间 为空"),

    TENANT_CONFIG_IS_NOT_EXIST(10039, "租户配置不存在"),

    MESSAGE_IS_NOT_EXIST(10040, "消息不存在"),

    MESSAGE_ABNORMAL_PARAMETER(10041, "消息参数不完整"),

    NEW_AND_OLD_PASSWORDS_ARE_THE_SAME(10042, "新旧密码不能一致"),

    VERSION_IS_NOT_EXIST(10043, "版本不存在"),

    VERSION_TYPE_IS_EMPTY(10044, "版本类型为空"),

    VERSION_CODE_IS_EMPTY(10045, "版本编号为空"),

    DATA_EXCEPTION(10046, "参数数据异常或格式错误"),

    CODE_IS_EMPTY(10047, "验证码为空"),

    CODE_IS_WRONG(10048, "验证码错误"),

    LOGIN_PSD_ERROER_NUM_MANY(10049, "登录失败次数过多"),

    LOGIN_PSD_ERROER_NEED_CODE(10050, "登陆失败需要验证码"),

    GROUP_IS_NOT_EXIST(10051, "分组不存在"),

    PARAMETER_IS_NOT_EXIST(10052, "参数不存在"),

    ENABLE_NOT_DELETE(10053, "生效的数据不能删除"),

    WORK_ORDER_NOT_EXIST(10054, "工单不存在"),

    VERSION_STATUS_IS_NOT_UNRELEASED(10055, "版本状态不是未发布"),

    VERSION_CODE_EXISTS(10056, "版本编码已存在"),

    VERSION_NUMBER_EXISTS(10057, "版本号已存在"),

    TITLE_LENGTH_ERROR(10058, "标题长度错误"),

    CONTENT_LENGTH_ERROR(10059, "描述内容长度过长"),

    NUMBER_NOT_REPEAT(10060, "编号不能重复"),

    TEMPLATE_IS_NOT_EXIST(10061, "邮件模板不存在"),

    TEMPLATE_PARAM_IS_NOT_EXIST(10062, "邮件模板参数不存在"),

    TEMPLATE_PARAM_NO_IS_NOT_NUMBER(10063, "邮件模板编号只能输入数字"),

    LENGTH_IS_TOO_LONG(10064, "长度过长"),

    GROUP_NOT_ENABLE(10065, "此分组未启用")

    ;

    private int code;
    private String message;

    private static ExceptionCodeEnums getErrorCodeByCode(int code) {
        for (ExceptionCodeEnums item : ExceptionCodeEnums.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
