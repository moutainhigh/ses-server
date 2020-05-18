package com.redescooter.ses.web.ros.exception;

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

    INSUFFICIENT_PERMISSIONS(10010, "权限不足,请联系管理员"),

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

    STATUS_ILLEGAL(10023, "状态非法"),

    TENANT_NOT_EXIST(10024, "租户不存在"),

    ACCOUNT_IS_NOT_EXIST(10025, "账户不存在"),

    EMAIL_ALREADY_EXISTS(10026, "邮箱已存在"),

    DATA_EXCEPTION(10027, "参数数据异常或格式错误"),

    ACCOUNT_IS_ALRADY_ACTIVATION(10028,"账户已经激活不可重复激活"),

    //30之后是业务错误码
    FIRST_NAME_CANNOT_EMPTY(10030, "名字不能为空"),

    LAST_NAME_CANNOT_EMPTY(10031, "姓氏不能为空"),

    COMPANY_NAME_CANNOT_EMPTY(10032, "企业名不能为空"),

    MAIL_NAME_CANNOT_EMPTY(10033, "邮箱不能为空"),

    CERTIFICATE_TYPE_CANNOT_EMPTY(10034, "证件类型不能为空"),

    BUSINESS_LICENSE_CANNOT_EMPTY(10035, "营业执照不能为空"),

    INVOICE_CANNOT_EMPTY(10036, "发票不能为空"),

    CITY_CANNOT_EMPTY(10037, "城市不能为空"),

    DISTRUST_CANNOT_EMPTY(10038, "区域不能为空"),

    CUSTOMER_TYPE_CANNOT_EMPTY(10039, "客户类型不能为空"),

    INDUSTRY_TYPE_CANNOT_EMPTY(10040, "行业类型不能为空"),

    ADDRESS_TYPE_CANNOT_EMPTY(10041, "地址不能为空"),

    LATITUDE_AND_LONGITUDE_CANNOT_EMPTY(10042, "经纬度不能为空"),

    TELEPHONE_CANNOT_EMPTY(10043, "手机号不能为空"),

    CERTIFICATETYPE_CANNOT_EMPTY(10044, "证件类型不能为空"),

    ID_CARD_CANNOT_EMPTY(10045, "身份证附件不能为空"),

    CERTIFICATE_CANNOT_EMPTY(10046, "证件附件不能为空"),

    INVOICE_NUM_CANNOT_EMPTY(10047, "发票编号不能为空"),

    INVOICE_ANNEX_CANNOT_EMPTY(10048, "发票附件不能为空"),

    CONTRACT_ANNEX_CANNOT_EMPTY(10049, "合同附件不能为空"),

    TRASH_CAN_NOT_BE_EDITED(10050, "垃圾箱资源不能编辑"),

    CUSTOMER_NOT_EXIST(10051, "客户不存在"),

    CODE_IS_WRONG(10052, "验证码错误"),

    CUSTOMER_TYPE_IS_NOT_EDIT(10053, "正式客户客户类型不可编辑"),

    CUSTOMER_INDUSTRYTYPE_IS_NOT_EDIT(10054, "正式客户客户行业不可编辑"),

    FILE_TEMPLATE_IS_INVALID(10055, "文件模板不合法，请重新下载文件模板"),

    PRODUCTN_IS_EXIST(10056, "产品编号已存在，请重新输入"),

    PRODUCT_IS_NOT_EXIST(10057, "产品不存在"),

    PRODUCT_HAS_NO_PARTS(10058, "产品暂未添加部品,不可删除"),

    PARTS_BASE_IS_ILLEGAL(10059, "部品基础信息不合法，如partsNumber,ImportLot,snClassFlag,partsType,sec等"),

    PARTS_NUMBER_EXIST(10060, "部品号已存在"),

    PRODUCT_PRICE_IS_NOT_EXIST(10061, "产品报价不存在"),

    CURRENCY_UNIT_IS_EMPTY(10062, "产品单位为空"),

    PRODUCT_PRICE_IS_EMPTY(10063, "产品报价为空"),

    PRODUCT_UNIT_IS_EMPTY(10064, "货币单位为空"),

    PART_IS_NOT_EXIST(10065, "部品不存在"),

    PARTS_NUMBER_REPEAT(10066, "导入数据的部品号重复，请更改"),

    INQUIRY_IS_NOT_EXIST(10067, "询价单不存在"),

    EMPLOYEE_IS_NOT_EXIST(10068, "员工不存在"),

    EMPLOYEE_IS_NOT_BING_POSITION(10069, "员工没有绑定职位"),

    DEPT_IS_NOT_EXIST(10070, "部门不存在"),

    POSITION_IS_NOT_EXIST(10071, "职位不存在"),

    REMOVE_ITSELF_CHILD_DEPT(10072, "请删除它的子部门"),

    REMOVE_DEPT_UNDER_POSITION(10073, "请移除下面的职位"),

    NOT_DELETE_ROOT_LEVEL(10074, "不可删除根级部门"),

    NON_REPEATABLE_CREATION_ROOT_LEVEL_DEPT(10075, "不可重复创建根级部门"),

    NON_CREATION_TWO_LEVEL_DEPT(10076, "不可创建二级部门"),

    MENU_IS_NOT_EXIST(10077, "菜单不存在"),

    THE_ROOT_NODE_MENU_CANNOT_BE_EDIT(10078, "根节点菜单不可编辑"),

    UNBUNDLING_OF_EMPLOYEES(10079, "请解绑该职位下的员工"),

    PURCHAS_IS_NOT_EXIST(10080, "采购单不存在"),

    FACTORY_IS_NOT_EXIST(10081, "代工厂不存在"),

    PART_TOTAL_QTY_IS_NOT_MATCH(10082, "配件数量不匹配"),

    PAYMENT_INFO_IS_WRONG(10083, "付款信息是错误的"),

    PART_PRODUCT_IS_NOT_EXIST(10084, "采购商品不存在"),

    FACTORY_ANNEX_IS_PERFECT(10085, "工厂附件不完善"),

    OPEPURCHAS_PAYMENT_IS_NOT_EXIST(10086, "支付周期不存在"),

    PAY_IN_INSTALLMENTS(10087, "请按照分期顺序支付"),

    PAY_AMOUNT_IS_FALSE(10088, "支付金额是错误的"),

    PARTS_ARE_NOT_QC_PASS(10089, "部件存在Qc未质检通过"),

    PARTS_CANNOT_BE_ASSEMBLED_WITHOUT_SUPPLIERS_WITHOUT_PRICES(10090, "部品没有供应商没有价格不可进行组装"),

    SUPPLIER_IS_NOT_PART_OF_THE_CURRENT_DOCUMENT(10091, "供应商不属于当前单据"),

    WAREHOUSE_IS_NOT_EXIST(10092, "仓库不存在"),

    NODE_IS_NOT_EXIST(10093, "节点不存在"),

    ALLOCATE_ORDER_IS_NOT_EXIST(10094, "调拨单不存在"),

    STOCK_IS_NOT_EXIST(10095, "暂无库存"),

    STOCK_IS_SHORTAGE(10096, "库存不足"),

    STOCK_BILL_NOT_IS_EXIST(10097, "库存单据不存在"),

    NO_NEED_TO_CHECK_AGAIN(10098, "无需再次质检"),

    QC_PASS_WITHOUT_RETURN(10099, "Qc通过无需退货"),

    QC_PASSED_WITHOUT_REPEATING_QUALITY_INSPECTION(10100, "无需重复质检"),

    PAYMENT_INFO_IS_NOT_EXIST(10101, "付款信息不存在"),

    ASSEMBLY_IS_NOT_EXIST(10102, "组装单不存在"),

    NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION(10103, "未进行质检请先进行质检"),

    DO_NOT_SET_THE_PRICE_REPEATEDLY(10104, "请不要重复设置价格"),

    PLEASE_SCAN_THE_CODE_FIRST(10105, "请先进行质检"),

    TEMPLATE_QC_RESULT_IS_EMPTY(10106, "模板质检结果为空"),

    TEMPLATE_QC_UPLOAD_PICTURE_FLAG_IS_EMPTY(10107, "模板上传图片标识为空"),

    TEMPLATE_QC_ITEMNAME_IS_EMPTY(10108, "质检项名称为空"),

    TEMPLATE_QC_RESULTSEQUENCE_IS_EMPTY(10109, "质检结果集排序为空"),

    PART_IS_NOT_BIND_PRODUCT(10110, "部件未绑定产品"),

    PART_IS_BIND_PRODUCT(10111, "部件绑定产品,请先解绑"),

    QC_TEMPLATE_IS_NOT_EXIT(10112, "质检模板不存在"),

    QC_PASS_RESULT_ONLY_ONE(10113, "质检项通过结果只能有一个"),

    CONVERT_TO_FORMAL_CUSTOMER_FIRST(10114, "请先转换为正式客户"),

    CUSTOMER_TRANSFERSCOOTER_QTY_IS_WRONG(10115, "客户分配车辆数量是错误的"),

    ASSEMBLY_B_QC_RESULT_IS_NOT_EXIST(10116, "质检结果集不存在"),

    ASSEMBLY_QC_ITEM_IS_NOT_EXIST(10117, "质检条目不存在"),

    ASSEMBLY_QC_RESULT_IS_NOT_EXIST(10118, "直接结果不存在"),

    SCOOTER_PLATES_NOT_REPEATABLE(10119, "车牌号不可重复"),

    SCOOTER_IS_ALREADY_EXIST(10120, "车牌号已经存在"),

    CUSTOMER_IS_NOT_NEED_ALLOCATION_SCOOTER(10121, "客户无需分配车辆"),

    PART_HISTROY_TRACE_IS_NOT_EXIST(10122, "部件记录不存在"),

    TOP_CASE_IS_NOT_EXIST(10123, "后备箱不存在"),

    BATTERY_IS_NOT_EXIST(10124, "电池不存在"),
    ;


    private int code;

    private String message;

}
