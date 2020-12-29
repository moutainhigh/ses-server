package com.redescooter.ses.web.ros.exception;

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

    ACCOUNT_IS_ALRADY_ACTIVATION(10028, "账户已经激活不可重复激活"),

    TOKEN_IS_EXPIRED(10029, "数据已过期"),

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

    BATTERIES_DOES_NOT_MEET_THE_STANDARD(10125, "电池数量不符合标准"),

    CUSTOMER_NOT_ALLOWED_TO_CREATED_INQUIRY(10126, "正式客户不允许添加询价单，请与销售人员联系"),

    DISTRUST_IS_NOT_EXIST(10127, "区域不存在，请重新输入"),

    CUSTOMER_ALREADY_EXIST_ORDER_FORM(10128, "客户已存在预订单不允许添加预订单，请与销售人员联系"),

    PARTS_BASE_IS_NOT_COMPLETE_INFORMATION(10129, "部品基础信息不完整，如partsNumber,ImportLot,snClassFlag,partsType,sec等"),

    NEW_AND_OLD_PASSWORDS_ARE_THE_SAME(10130, "新旧密码一致"),

    CHARACTER_IS_TOO_LONG(10131, "字符过长"),

    CHARACTER_IS_TOO_SHORT(10132, "字符过短"),

    CONSTANT_NAME_IS_NOT_ILLEGAL(10133, "联系人名字不合法"),

    COMPANY_NAME_IS_NOT_ILLEGAL(10134, "公司名称不合法"),

    CUSTOMER_NAME_IS_NOT_ILLEGAL(10135, "客户名称不合法"),

    REMARK_IS_NOT_ILLEGAL(10136, "备注不合法"),

    INVOICE_NUM_IS_NOT_ILLEGAL(10137, "发票号不合法"),

    BUSSINESS_NUM_IS_NOT_ILLEGAL(10138, "发票编号不合法"),

    SCOOTER_QTY_IS_NOT_ILLEGAL(10139, "车辆数量不合法"),

    EMPLOYEE_NAME_IS_NOT_ILLEGAL(10140, "员工名字不合法"),

    TELEPHONE_IS_NOT_ILLEGAL(10141, "手机号码不合法"),

    ADDRESS_IS_NOT_ILLEGAL(10142, "地址不合法"),

    JOB_TITLE_IS_ILLEGAL(10143, "职位名称不合法"),

    FACTORY_NAME_IS_NOT_ILLEGAL(10144, "工厂名称不合法"),

    FACTORY_ADDRESS_IS_NOT_ILLEGAL(10145, "工厂地址不合法"),

    EMAIL_IS_NOT_ILLEGAL(10146, "邮箱不合法"),

    FACTORY_TAG_IS_NOT_ILLEGAL(10147, "工厂标签不合法"),

    ILLEGAL_BUSINESS_LICENSE_NUMBER(10148, "营业执照编号不合法"),

    SUPPLIER_NAME_IS_NOT_ILLEGAL(10149, "供应商名称不合法"),

    SUPPLIER_ADDRESS_IS_NOT_ILLEGAL(10150, "供应商地址不合法"),

    SUPPLIER_TAG_IS_NOT_ILLEGAL(10151, "供应商标签不合法"),

    TOP_DEPT_IS_NOT_EXIST(10152, "顶级部门不存在"),

    LOGIN_PSD_ERROER_NEED_CODE(10153, "密码错误"),

    LOGIN_PSD_ERROER_NUM_MANY(10154, "密码错误次数过多，请一分钟之后再登陆"),

    CODE_NOT_EMPTY(10155, "验证码不能为空"),

    EAMIL_NOT_REGISTER(10156, "当前邮箱还未注册，请先注册"),

    EAMIL_CODE_TIME_OUT(10157, "请先获取验证码或验证码已过期"),

    WH_OUT_ORDER_NOT_EXIST(10158, "出库单不存在"),

    PRODUCT_PART_IS_EMPTY(10159, "产品部件不存在"),

    CONSIGN_TYPE_NOT_EXIST(10160, "物流方式不存在"),

    NO_LOAN(10161, "委托方式为空"),

    NO_PERM(10162, "没有该接口的权限"),

    NO_MATERIAL_FOR_OUT_ORDER(10163, "该订单暂无备料"),

    ACTIVATION_CUSTOMER_NOT_DELETE(10164, "激活之后的客户不能删除"),

    ROLE_IS_NOT_EXIST(10165, "角色不存在"),

    ROLE_IS_NOT_DELETE(10166, "角色下面存在员工，不能删除"),

    DEPT_NAME_IS_EMPTY(10167, "部门名称为空"),

    SUPERIOR_DEPT_IS_EMPTY(10168, "上级部门为空"),

    PLEASE_UNTIE_THE_SUBDEPT(10169, "请解绑子部门"),

    ID_IS_NOT_NULL(10170, "id不能为空"),

    DEPT_DISABLE(10171, "当前部门被禁用"),

    ALREADY_OPEN(10172, "当前员工已经开通过账号"),

    TYPE_IS_NULL(10173, "类型不能为空"),

    SAVE_DEPT_POSITION_NAME_NOT_REPEAT(10174, "同部门下面的岗位名称不能重复"),

    ACCOUNT_DISABLED(10175, "账号被禁用"),

    POSITION_DISABLED(10176, "岗位被禁用"),

    SORT_NOT_NEG(10177, "排序不能为负数"),

    DEPT_LEVEL_ERROR(10178, "部门最多只能有4级"),

    ASSEMBLY_PRODUCT_IS_EMPTY(10179, "组装产品为空"),

    PSD_LENGTH_ERROR(10180, "密码长度不对"),

    SAFE_CODE_NOT_OPEN(10181, "未开通安全码"),

    OLD_PSD_ERROR(10182, "旧密码错误，请重新输入"),

    PLEASE_COMPLETE_MSG(10183, "请完善信息"),

    DRAFT_NOT_EXIST(10184, "草稿不存在"),

    BOM_IS_NOT_EXIST(10185, "BOM不存在"),

    BOM_HAS_REACHED_EFFECTIVE_TIME(10186, "Bom未到生效时间"),

    BOM_PART_HAVE_LAST_ONE(10187, "BOM 至少又一个部件"),

    PRODUCTION_PART_IS_NOT_EXIST(10188, "部件不存在"),

    BOM_HAS_DUPLICATE_EFFECTIVE_DATE(10189, "bom有重复的日期"),

    BOM_NUM_REPEAT(10190, "bom编号已重复"),

    BOM_MSG_IS_NOT_COMPLETE(10191, "Bom信息不完整"),

    SAFE_CODE_FAILURE(10192, "安全码失效，请重新给校验"),

    PARTS_NO_REPEAT(10193, "部件号重复"),

    PARTS_IS_DISABLE(10194, "部件已被禁用"),

    GROUP_NOT_EXIST(10195, "分组不存在"),

    GROUP_BE_USED(10196, "分组已经被使用，不能删除"),

    DEF_NOT_NULL(10197, "自定义项不能为空"),

    DEF_NUM_ERROR(10198, "自定义项最多8个"),

    SPECIFICAT_TYPE_NOT_EXIST(10199, "规格类型不存在"),

    COLOR_VALUE_NOT_NULL(10200, "色值不能为空"),

    COLOR_VALUE_LENGTH_ERROR(10201, "色值长度错误"),

    SPECIFICAT_TYPE_NAME_NOT_NULL(10202, "规格类型名称不能为空"),

    SPECIFICAT_TYPE_NAME_EXIST(10203, "规格类型名称已经存在"),

    DEF_NAME_NOT_NULL(10204, "自定义名称不能为空"),

    DEF_NAME_ILLEGAL(10205, "自定义名称非法"),

    DEF_VALUE_ILLEGAL(10206, "自定义值非法"),

    THE_NUMBER_OF_PARTS_LEAST_GREATER_THAN_ONE(10207, "部件数量至少大于1"),

    DELETE_THE_ORDER_FIRST(10208, "请先删除订单"),

    PARTS_MSG_NOT_PERFECT(10209, "部品信息不完善，请完善信息"),

    COLOR_NOT_EXIST(10210, "颜色不存在"),

    SPECIFICAT_TYPE_NAME_ILLEGAL(10211, "规格名称仅支持大写字母、数字和“-”"),

    PRODUCT_CODE_NOT_NULL(10212, "产品编码不能为空"),

    COLOR_IS_USED(10213, "颜色已被使用，不能删除"),

    SPECIFICAT_TYPE_IS_USED(10214, "规格类型已被使用，不能删除"),

    WEIGHT_ILLEGAL(10215, "重量违法"),

    QUANTITY_ILLEGAL(10216, "数量违法"),

    SELECT_COMBIN_NAME(10217, "请先选择组装件名称"),

    PRODUCT_NAME_NOT_NULL(10218, "产品名称不能为空"),

    COLOR_VALUE_EXIST(10219, "色值已存在"),

    ORDER_NOT_EXIST(10220, "单据不存在"),

    ORDER_STATUS_ERROR(10221, "单据状态的不满足"),

    ORDER_TRACE_IS_NOT_EXIST(10222, "单据记录不存在"),

    STOCK_NOT_CANCEL(10223, "正在备货中，无法取消"),

    PRODUCT_DOES_NOT_EXIST(10224, "产品不存在"),

    PRODUCT_DOES_ALRADY_EXIST(10225, "当前产品类型已存在"),

    QTY_IS_ILLEGAL(10226,"数量不合法"),

    DEPT_NAME_EXIST(10227,"当前父级部门下面部门名称重复"),

    ROLE_NAME_EXIST(10228,"角色名称已存在"),

    PARENT_DEPT_ERROR(10229,"不允许将自己设置为父级部门！"),

    PARENT_DEPT_NOT_EXIST(10230,"父级部门不存在！"),

    PARENT_DEPT_IS_DISABLE(10231,"父级部门被禁用！"),

    TOP_DEPT_IS_NOT_DISABLE(10232,"顶级部门不能禁用！"),

    DEPT_IS_ERROR(10233,"只能选择当前部门及其子部门！"),

    DEF_GROUP_IS_NOT_NULL(10234, "自定义项分组不能为空"),

    UPDATE_FAIL(10235, "修改失败"),

    ENABLE_STORE_NOT_DELETE(10236, "启用中的店铺，不可删除。"),

    STORE_EMAIL_MUST_CHAR(10237, "邮件地址必须带有@字符"),

    INSERT_FAIL(10238, "新增失败"),

    DEF_GROUP_NAME_NOT_NULL(10239, "自定义分组名称不能为空"),

    DEF_GROUP_NAME_ILLEGAL(10240, "自定义分组名称非法"),

    SEAT_NOT_EMPTY(10241, "座位数不能为空")



    ;


    private int code;

    private String message;

}
