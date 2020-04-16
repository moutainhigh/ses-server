package com.redescooter.ses.mobile.rps.exception;

import com.redescooter.ses.tool.utils.VerificationCodeImgUtil;
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

    //  10000   默认系统公用异常

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

    STATUS_IS_ILLEGAL(10020, "状态非法"),

    ILLEGAL_DATA(10021, "数据非法"),

    INSUFFICIENT_PERMISSIONS(10022, "用户暂无权限"),


    PURCHAS_IS_NOT_EXIST(10030, "采购单不存在"),

    PART_IS_NOT_HAVE_QC_TEMPLATE(10031, "部件暂无质检模板"),

    PART_IS_NOT_EXIST(10032, "部件不存在"),

    PART_QC_QTY_IS_EMPTY(10033, "部件质检数量为空"),

    PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE(10035, "部件质检项不完整"),

    ALLOCATE_ORDER_IS_NOT_EXIST(10036, "调拨单不存在"),

    PREPARE_MATERIAL_QTY_IS_WRONG(10037, "备料数量不匹配"),

    ASSEMNLY_ORDER_IS_EXIST(10038, "组装单不存在"),

    PRODUCT_IS_NOT_HAVE_FORMULA(10039, "商品暂无配方"),

    ;

    private int code;

    private String message;

}
