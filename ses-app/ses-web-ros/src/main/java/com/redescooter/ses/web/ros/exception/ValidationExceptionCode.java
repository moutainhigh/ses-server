package com.redescooter.ses.web.ros.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {

    // ###############################10001~10030公共入参异常#######################################
    // 经度为空
    int LAT_IS_EMPTY = 10001;
    // 纬度为空
    int LNG_IS_EMPTY = 10002;
    // 图片为空
    int PICTURE_IS_EMPTY = 10003;
    //长度过长，长度为2-20字符
    int CHARACTER_IS_TOO_LONG = 10004;
    //长度过短，长度为2-20字符
    int CHARACTER_IS_TOO_SHORT = 10005;
    // 邮箱为空
    int EMAIL_IS_EMPTY = 10006;
    // id 为空
    int ID_IS_EMPTY = 10007;
    //国家手机号编码不能为空
    int COUNTRY_CODE_IS_EMPTY = 10008;
    //URL不能为空
    int URL_IS_EMPTY = 10009;

    //###############################10030~100?? 业务入参异常#######################################
    // 地址信息为空
    int ADDRESS_INFO_IS_EMPTY = 10030;
    //客户信息不能为空
    int CUSTOMER_INFO_IS_EMPTY = 10031;
    //地址国家不能为空
    int ADDRESS_COUNTRY_IS_EMPTY = 10032;
    //代工厂标签不能为空
    int FACTORY_TAG_IS_EMPTY = 10033;
    //代工厂名称不能为空
    int FACTORY_NAME_IS_EMPTY = 10034;
    //供应商标签不能为空
    int SUPPLIER_TAG_IS_EMPTY = 10035;
    //供应商名称不能为空
    int SUPPLIER_NAME_IS_EMPTY = 10036;
    //联系人全名不能为空
    int CONTACT_FULLNAME_IS_EMPTY = 10037;
    //联系人邮箱不能为空
    int CONTACT_EMAIL_IS_EMPTY = 10038;
    //联系人手机号不能为空
    int CONTACT_PHONE_IS_EMPTY = 10039;
    //付款周期不能为空
    int PAYMENT_CYCLE_IS_EMPTY = 10040;
    //合同开始时间不能为空
    int COOPERATION_TIME_START_IS_EMPTY = 10041;
    //营业执照编号不能为空
    int BUSINESS_NUMBER_IS_EMPTY = 10042;
    //营业执照附件不能为空
    int BUSINESS_LICENSE_ANNEX_IS_EMPTY = 10043;
    //合同编号不能为空
    int CONTRACT_NUMBER_IS_EMPTY = 10044;
    //合同附件不能为空
    int CONTRACT_ANNEX_IS_EMPTY = 10045;
    // 产品编号为空
    int PRODUCT_NUM_IS_EMPTY = 10046;
    // 产品英文名字为空
    int PRODUCT_EN_NAME_IS_EMPTY = 10047;
    //产品周期为空
    int PRODUCT_CYCLE_IS_EMPTY = 10048;
    // 产品法文名字为空
    int PRODUCT_FR_NAME_IS_EMPTY = 10049;
    //产品中文名字为空
    int PRODUCT_CN_NAME_IS_EMPTY = 10050;
    // 产品法国报价为空
    int PRODUCT_FR_PRICE_IS_EMPTY = 10051;
    // 产品英国报价为空
    int PRODUCT_EN_PRICE_IS_EMPTY = 10052;
    // 数量为空
    int QTY_IS_EMPTY = 10053;
    //部品号为空
    int PARTS_NUM_IS_EMPTY = 10054;
    //部品类型为空
    int PARTS_TYPE_IS_EMPTY = 10055;
    //部品项目区域为空
    int PARTS_SEC_IS_EMPTY = 10056;
    //是否可销售为空
    int PARTS_SN_CLASS_IS_EMPTY = 10057;
    // 销售价格类型为空
    int SALES_PRICE_TYPE_IS_EMPTY = 10058;
    // 国家城市代码为空
    int COUNTRY_CITY_MSG_IS_EMPTY = 10059;
    // 客户类型为空
    int CUSTOMER_TYPE_IS_EMPTY = 10060;
    // 行业类型
    int INDUSTY_TYPE_IS_EMPTY = 10061;
    // 办公区域为空
    int ADDRESS_BUREAU_IS_EMPTY = 10062;
    // 部门为空
    int DEPT_IS_EMPTY = 10063;
    // 职位为空
    int POSITION_IS_EMPTY = 10064;
    // 名 为空
    int FIRST_NAME_IS_EMPTY = 10065;
    // 姓为空
    int LAST_NAME_IS_EMPTY = 10066;
    // 生日为空
    int BIRTHDAY_IS_EMPTY = 10067;
    // 证件类型为空
    int CERTIFICATE_TYPE_IS_EMPTY = 10068;
    // 证件正面图片为空
    int CERTIFICATE_POSITIVE_PICTURE_IS_EMPTY = 10069;
    //地址字符不合法
    int ADDRESS_CHAR_IS_NOT_ILLEGAL = 10070;
    // 邮箱长度不合法
    int EMAIL_CHAR_IS_NOT_ILLEGAL = 10071;
    // 公司为空
    int COMPANY_IS_EMPTY = 10072;
    // 父级Id为空
    int PID_IS_EMPTY = 10073;
    //部门名字为空
    int DEPT_NAME_IS_EMPTY = 10074;
    // 菜单的名字为空
    int MENU_NAME_IS_EMPTY = 10075;
    // 菜单路径为空
    int MENU_PATH_IS_EMPTY = 10076;
    //菜单类型为空
    int MENU_TYPE_IS_EMPTY = 10077;
    //部门层级为空
    int DEPT_LEVEL_IS_EMPTY = 10078;
    // 职位名称为空
    int ROLE_NAME_IS_EMPTY = 10079;
    // 收货人为空
    int CONSIGNEE_ID__IS_EMPTY = 10080;
    // 工厂Id 为空
    int FACTORY_ID_EMPTY = 10081;
    // 部品为空
    int PARTS_IS_EMPTY = 10082;
    // 工厂为空
    int PAYMENT_TYPE_IS_EMPTY = 10083;
}
