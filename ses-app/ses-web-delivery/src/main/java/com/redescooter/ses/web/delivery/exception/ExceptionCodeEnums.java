package com.redescooter.ses.web.delivery.exception;

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

    USER_NOT_EXIST(10002, "用户不存在"),

    PASSWORD_EMPTY(10003, "密码为空"),

    ACCOUNT_NOT_ACTIVATED(10004, "账户未激活"),

    PASSROD_WRONG(10005, "密码错误"),

    THE_ACCOUNT_HAS_BEEN_FROZEN(10006, "账户被冻结"),

    ACCOUNT_CANCELLED(10007, "账户被注销了"),

    TOKEN_MESSAGE_IS_FALSE(10008, "token中的信息不正确"),

    ACCOUNT_EXPIRED(10009, "账号过期"),

    INCONSISTENT_PASSWORD(10010, "密码不一致"),

    ACCOUNT_ALREADY_EXIST(10011, "账户已经存在了"),

    SYSTEMID_IS_NOT_MATCH(10012, "systemid 不匹配"),

    APPID_IS_NOT_MATCH(10013, "appid 不匹配"),

    LANGUAGE_CANNOT_EMPTY(10014, "语言为空"),

    CLIENTTYPE_CANNOT_EMPTY(10015, "客户端类型为空"),

    CLIENTIP_CANNOT_EMPTY(10016, "客户端IP 为空"),

    TIMEZONE_CANNOT_EMPTY(10017, "时区为空"),

    VERSION_CANNOT_EMPTY(10018, "版本号为空"),

    COUNTRY_CANNOT_EMPTY(10019, "国家为空"),

    PRIMARY_KEY_CANNOT_EMPTY(10020, "主键不能为空"),

    NON_REPEATABLE(10021, "不可重复操作"),

    DATA_EXCEPTION(10022, "参数数据异常或格式错误"),

    ACCOUNT_IS_ACTIVATED(10023, "账户已激活"),

    STATUS_IS_UNAVAILABLE(10024, "状态不可用"),

    ID_IS_EMPTY(10025, "Id 为空"),

    OPERATION_ERROR(10026, "错误操作"),


    /******************************************************************************************************/

    DRIVER_IS_NOT_EXIST(10030, "司机不存在"),

    DRIVER_STATUS_IS_DEPARTURE(10031, "司机已经离职"),

    DRIVER_STATUS_IS_WORKING(10032, "司机已分配车辆上班，不可重复分配"),

    DRIVER_HAS_NOT_AVAILABLE_SCOOTER(10033, "司机没有可用的车辆"),

    DRIVER_STATUS_IS_OFFWORK(10034, "司机已下班"),

    DRIVER_CANNOT_EMPTY(10035, "司机不能为空"),

    PACKAGES_CANNOT_BE_EMPTY(10036, "包裹数不能为空"),

    RECIPIENT_INFORMATION_IS_MISSING(10037, "收件方信息缺失"),

    MISSING_LATITUDE_AND_LONGITUDE(10038, "经纬度信息缺失"),

    ORDER_HAS_STARTED_AND_CANNOT_BE_CANCELLED(10039, "订单已开始，不能取消"),

    PLEASE_OPERATE_AFTER_WORK(10040, "请下班后进行操作"),

    YOU_HAVE_AN_ORDER_IN_PROGRESS(10041, "你有正在进行中的订单，不可还车下班"),

    DELIVERY_IS_NOT_EXIST(10042, "订单不存在"),

    DELIVERY_CAN_NOT_ASSIGNED_THE_SAME_DRIVER(10043, "订单不能分给同一人"),

    TENANT_BUSINESS_TIME_FORMAT_IS_WRONG(10044, "营业时间格式不正确"),

    TASK_IS_NOT_EXIST(10045, "任务不存在"),

    EXPRESS_ORDER_IS_NOT_EXIST(10046, "快递订单不存在"),

    ILLEGAL_NICKNAME(10047,"昵称不合法"),

    REJECTED_ORDERS_CANNOT_ASSIGNED_THE_SAME_DRIVER(10048,"拒绝订单不可分给同一个司机"),

    ORDER_IS_NOT_ALLOCATED(10049,"订单尚未完全分配完"),

    ORDER_HAS_BEEN_ASSIGNED(10050, "订单已分配，不可取消"),


    ;


    private int code;

    private String message;

}
