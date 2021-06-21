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
    TOKEN_NOT_EXIST(10001, "token不存在"),

    TOKEN_MESSAGE_IS_FALSE(10002, "token过期"),

    USER_NOT_EXIST(10003, "用户不存在"),

    PASSWORD_EMPTY(10004, "密码为空"),

    ACCOUNT_NOT_ACTIVATED(10005, "账户未激活"),

    PASSROD_WRONG(10006, "密码错误"),

    THE_ACCOUNT_HAS_BEEN_FROZEN(10007, "账户被冻结"),

    ACCOUNT_CANCELLED(10008, "账户被注销了"),

    ACCOUNT_EXPIRED(10009, "账号过期"),

    INSUFFICIENT_PERMISSIONS(10010, "权限不足,不允许操作"),

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

    ACCOUNT_IS_NOT_EXIST(10025,"账户不存在"),

    // 10030 后 业务异常
    SCOOTER_IS_NOT_EXIST(10031,"车辆不存在"),

    SCOOTER_IS_ALREADY_EXIST(10032,"车辆已存在"),

    EVENT_IS_EMPTY(10033,"事件为空"),

    POSITIONING_DATA_IS_EMPTY(10034,"定位数据为空，指经纬度"),

    MILEAGE_IS_EMPTY(10035,"骑行距离为空"),

    DURATION_IS_EMPTY(10036,"耗时为空"),

    ORDER_HAS_DISTRIBUTED(10037, "此询价单已分配给其他账户"),

    COLOR_NOT_EXISTS(10038, "颜色不存在"),

    PARTS_HAS_INPUT(10039, "部件已录入"),

    CODEBASE_NOT_EXIST(10040, "码库中不存在"),

    SN_ALREADY_EXISTS(10041, "SN已存在"),

    SCOOTER_NOT_CLOSED(10042, "车辆未关闭"),

    DATA_EXCEPTION(10043, "数据异常"),

    VIN_NOT_MATCH(10044, "vin不匹配")









    ;


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
