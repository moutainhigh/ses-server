package com.redescooter.ses.service.hub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ExceptionCodeEnums
 * @description: ExceptionCodeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/11/01 10:15
 * <p>
 * <p>
 * 10000-10040 为系统
 * <p>
 * 10040- 无穷大  为业务异常
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


    // 20000 业务公用异常
    STATUS_ILLEGAL(20001,"状态不可用"),

    EMAIL_ALREADY_EXIST(20002,"邮箱已存在"),

    EMAIL_NOT_EXIST(20003,"邮箱不存在"),

    TENANT_NOT_EXIST(20004,"租户不存在"),

    TENANT_IS_FROZEN(20005,"租户被冻结"),

    TENANT_IS_EXPIRED(20006,"租户已经到期"),

    MESSAGE_IS_NOT_EXIST(20007,"消息不存在"),

    ACCOUNT_TYPE_IS_EMPTY(20008,"账户类型为空"),




    // 20050----》 无穷大  业务异常

    SCOOTER_IS_NOT_EXIST(20051,"车辆不存在"),

    THE_VEHICLE_DOES_NOT_HAVE_ALL_THE_REPAIR_ORDERS(20052,"车辆没有相匹配的维修单"),

    CUSTOMER_IS_NOT_EXIST(20053,"客户不存在"),

    REPAIR_ORDER_SHOP_NOT_EXIST(20054,"维修店不存在"),

    REPAIR_SHOPBUSINESS_HOURS_IS_EMPTY(20055,"维修店营业时间为空"),

    REPAIR_SHOP_IS_CLOSE(20056,"维修店已打烊"),

    APPOINTMENT_TIME_IS_NOT_WITHIN_THE_BUSINESS_SCOPE(20057,"预约时间不再营业时间内"),

    ORDERS_BEING_REPAIRED(20058,"有维修单已经在进行中"),

    ORGDEPARTMENT_NOT_EXIST(20059,"部门不存在"),

    STAFF_NOT_EXIST(20060,"员工不存在"),

    SCOOTER_IS_REPAIRING(20061,"车子正在维修"),

    REPAIR_SHOP_PRINCIPLE_NOT_EMPTY(20062,"维修店负责人为空"),

    SYSUSER_PROFILE_NOT_EXIST(20063,"用户信息为空"),

    RENEWAL_DAYS_FORMAT_IS_INCORRECT(20064,"续期时间格式不正确"),

    REPAIR_SHOP_CONTACT_ATTACHMENT_EMPTY(20065, "维修店联系电话为空"),

    REPAIR_ORDER_NOT_EXIST(20066,"维修单不存在"),

    REPAIRSHOP_SPAREPART_NOT_EXIST(20067,"维修店零部件不存在"),

    REPAIRSHOP_SPAREPART_INVENTORY_SHORTAGE(20068,"维修店零部件库存不足"),

    ACCOUNT_OPENING_TIME_IS_EMPTY(20069,"账户开通时间为空"),
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
