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

    //自定义业务异常

    PURCHAS_IS_NOT_EXIST(10030, "采购单不存在"),

    PART_IS_NOT_HAVE_QC_TEMPLATE(10031, "部件暂无质检模板"),

    PART_IS_NOT_EXIST(10032, "部件不存在"),

    PART_QC_QTY_IS_EMPTY(10033, "部件质检数量为空"),

    PART_TEMPLATE_IS_NOT_EXIST(10034, "部件模板不存在"),

    PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE(10035, "部件质检项不完整"),

    ALLOCATE_ORDER_IS_NOT_EXIST(10036, "调拨单不存在"),

    PREPARE_MATERIAL_QTY_IS_WRONG(10037, "备料数量不匹配"),

    ASSEMNLY_ORDER_IS_EXIST(10038, "组装单不存在"),

    PRODUCT_IS_NOT_HAVE_FORMULA(10039, "商品暂无配方"),

    PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST(10040, "商品组装记录不存在"),

    PRODUCT_SERIAL_NUMBER_NON_REPEATABLE_PRINTING(10041, "序列号不可重复打印"),

    PRINT_SERIAL_NUMBER_FAILURE(10042, "条码打印失败"),

    PART_INFORMATION_IS_NOT_COMPLETE(10043, "部件信息不完整"),

    PART_QTY_IS_WRONG(10044, "部件数量不正确"),

    PRODUCT_IS_NOT_EXIST(10045, "商品不存在"),

    THE_PRODUCT_HAS_BEEN_ASSEMBLED(10046, "该商品已经组装完成"),

    WAREHOUSE_IS_NOT_EXIST(10047, "仓库不存在"),

    NO_NEED_TO_CHECK_AGAIN(10048, "无需再次质检"),

    NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION(10049, "未进行质检请先进行质检"),

    QC_PASS_WITHOUT_RETURN(10050, "Qc通过无需退货"),

    PAYMENT_INFO_IS_NOT_EXIST(10051, "付款信息不存在"),

    PLEASE_SCAN_THE_CODE_FIRST(10052, "请先进行质检"),

    PART_PASSED_THE_QUALITY_INSPECTION(10053, "部件已经通过质检"),

    OPE_B_ORDER_IS_EMPTY(10054, "组装子单为空"),

    QC_TEMPLATE_IS_EMPTY(10055, "质检模板为空"),

    QC_OPTION_IS_EMPTY(10056, "质检项目为空"),

    QC_TEMPLATE_B_IS_EMPTY(10057, "质检模板详情为空"),

    WAIT_QC_PART_NUM_ERROR(10058, "输入的待质检部件数错误"),

    WAIT_QC_NUM_ERROR(10059, "待质检总数错误"),

    QC_INFO_IS_EMPTY(10060, "质检记录为空"),

    WAIT_IN_WH_NUM_IS_EMPTY(10061, "待质检数量为空"),

    ALLOCATE_B_ORDER_IS_NOT_EXIST(10062, "调拨单子单不存在"),

    WAIT_IN_WH_NUM_ERROR(10063, "待入库总数错误"),

    PART_IS_ALREADY_DAMAGE(10064, "部件已损坏"),

    WH_IS_EMPTY(10065, "仓库不存在"),

    ALLOCATE_TRACE_IS_EMPTY(10066, "调拨单记录为空"),

    PURCHAS_PART_FINISHED_PREPARATION(10067, "采购部品已完成备料"),

    SERIAL_NUMBER_IS_WRONG(10068,"序列号是错误的"),

    ;

    private int code;

    private String message;

}
