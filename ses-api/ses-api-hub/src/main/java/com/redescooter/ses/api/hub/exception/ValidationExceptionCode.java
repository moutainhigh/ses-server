package com.redescooter.ses.api.hub.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {

    int DRIVERID_IS_EMPTY = 1001;

    int RECIPIENT_EMPTY = 1002;

    int RECIPIENTADDRESS_EMPTY = 1003;

    int RECIPIENTTEL_EMPTY = 1004;

    int DELIVERYLONGITUDE_EMPTY = 1005;

    int DELIVERYLATITUDE_EMPTY = 1006;

    int PARCELQUANTITY_EMPTY = 1007;

    int APPOINTMENT_EMPTY = 1008;

    int DELIVERY_ID_EMPTY = 1009;

    int TENANTID_IS_EMPTY = 1010;

    int DATETIMESTR_EMPTY = 1011;

    int DELIVERYID_IS_EMPTY = 1012;

    int DELIVERY_EVENT_EMPTY = 1013;

    int SCOOTERID_EMPTY = 1014;

    int DRIVER_EVENT_EMPTY = 1015;

    int LOGIN_NAME_EMPTY = 1016;

    int FULLNAME_IS_EMPTY = 1017;

    int PICTURE_IS_EMPTY = 1018;

    int GENDER_IS_EMPTY = 1019;

    int PLACEBIRTH_IS_EMPTY = 1020;

    int EMAIL_IS_EMPTY = 1021;

    int TELNUMBER_IS_EMPTY = 1022;

    int BIRTHDAY_IS_EMPTY = 1023;

    int DRIVINGLICENSE_IS_EMPTY = 1024;

    int DRIVINGLICENSEPICTURE_IS_EMPTY = 1025;

    int IDENTITY_IS_EMPTY = 1026;

    int DRIVER_STATUS_EMPTY = 1027;

    int SCOOTER_STATUS_EMPTY = 1028;

    int RFID_EVENT_IS_EMPTY = 1029;

    int RFID_IS_EMPTY = 1030;

    int SN_IS_EMPTY = 1031;

    int USER_ID_EMPTY = 1032;

    int DRIVING_LICENSE_EMPTY = 1033;

    int DRIVINGLICENSE_PICTURE_EMPTY = 1034;

    int ROLE_EMPTY = 1035;

    int JOINDATE_EMPTY = 1036;

    int TIMEZONE_EMPTY = 1037;

    int FIRST_NAME = 1038;

    int LAST_NAME = 1039;

    int EXPIRETIME_IS_NOT_EMPTY = 1040;

    int COUNTRY_IS_NOT_EMPTY = 1041;

    int CITY_IS_NOT_EMPTY = 1042;

    int AREA_IS_NOT_EMPTY = 1043;

    int CUSTOMERNAME_IS_NOT_EMPTY = 1044;

    int CUSTOMERSOURCE_IS_NOT_EMPTY = 1045;

    int CUSTOMERTYPE_IS_NOT_EMPTY = 1046;

    int CUSTOMERINDUSTRY_IS_NOT_EMPTY = 1047;

    int CUSTOMERADDRESS_IS_NOT_EMPTY = 1048;

    int TELPHONE_IS_NOT_EMPTY = 1049;

    int EMAIL_IS_NOT_EMPTY = 1050;

    int PICTURE_IS_NOT_EMPTY = 1051;

    int FULLNAME_IS_NOT_EMPTY = 1052;

    int DAYNUM_IS_EMPTY = 1053;
    //传入租户id不能为空
    int INPUTTENANTID_NOT_IS_EMPTY = 1054;
    /**
     * 邮箱不可用
     */
    int EMAIL_ADDRESS_IS_NOT_AVAILABLE = 1055;
    /**
     * 电话不可用
     */
    int ILLEGAL_TELNUMBER_NOT_AVAILABLE = 1056;

    int INPUT_QUANTITY_IS_ILLEGAL = 1057;
    /**
     * 超时预期值不能为空
     */
    int TIMEOUTEXPECTDE_IS_EMPTY = 1058;
    /**
     * 估计持续时间不能为空
     */
    int ESTIMATEDDURATION_IS_EMPTY = 1059;

    /**
     * 配送范围不能为空
     */
    int DISTRIBUTIONRANGE_IS_EMPTY = 1060;
    /**
     * 收货人 姓名不可用
     */
    int NAME_IS_NOT_AVAILABLE = 1061;
    /**
     * 收货地址不可用
     */
    int ADDRESS_IS_NOT_AVAILABLE = 1062;
    /**
     * 收货地址不可用
     */
    int RECIPIENTTEL_IS_NOT_AVAILABLE = 1063;
    /**
     * 预约时间不可用
     */
    int APPOINTMENT_IS_NOT_AVAILABLE = 1064;
    /**
     * firstName 不可用
     */
    int FIRST_NAME_IS_NOT_AVAILABLE = 1065;
    /**
     * lastName 不可用
     */
    int LASTNAME_IS_NOT_AVAILABLE = 1066;
    /**
     * 行驶证 不可用
     */
    int DRIVINGLICENSE_LENGTH_IS_NOT_AVAILABLE = 1067;
    /**
     * 维修店Id 为空
     */
    int REPAIR_SHOPID_IS_EMPTY = 1068;
    /**
     * 预约时间为空
     */
    int REPAIRAPPOINTMENTTIME_IS_EMPTY = 1069;
    /**
     * 维修类型
     */
    int REPAIRTYPE_IS_EMPTY = 1070;
    /**
     * 服务类型
     */
    int SERVICETYPE_IS_EMPTY = 1071;
    /**
     * 联系地址
     */
    int CONTACTADDRESS_IS_EMPTY = 1072;
    /**
     * 维修的车Id
     */
    int SCOOTERLICENSE_LIST_IS_EMPTY = 1073;
    /**
     * 联系地址经度
     */
    int CONTACT_ADDRESS_LONGITUDE_IS_EMPTY = 1074;
    /**
     * 联系地址维度
     */
    int CONTACT_ADDRESS_LATITUDE_IS_EMPTY = 1075;
    /**
     * 负责人Id
     */
    int PRINCIPALID_IS_EMPTY = 1076;
    /**
     * 负责人电话
     */
    int PRINCIPALTELEPHONE_IS_EMPTY = 1077;
    /**
     * placeID 为空
     */
    int PLACEID_IS_EMPTY = 1078;

    /************************scooter*********************/
    //scooterID 爲空
    int SCOOTERID_IS_EMPTY = 1001;
    //scooter 事件 为空
    int EVENT_OPERATION_IS_EMPTY = 1002;
    // 用户经度为空
    int USERLONGITUDE_IS_EMPTY = 1003;
    //用户维度为空
    int USERLATITUDE_IS_EMPTY = 1004;
    // action 结果为空
    int ACTION_RESULT_IS_EMPTY = 1005;
    // 车锁状态 为空
    int LOCKSTATUS_IS_EMPTY = 1006;
    //后备箱状态 为空
    int TOPBOX_STATUS_IS_EMPTY = 1007;
    //经度为空
    int LONGITULE_IS_EMPTY = 1008;
    //维度 为空
    int LATITUDE_IS_EMPTY = 1009;
    //geohash为空
    int GEOHASH_IS_EMPTY = 1010;
    //电量为空
    int BATTERY_IS_EMPTY = 1011;
    //开始时间为空
    int BEGINTIME_IS_EMPTY = 1012;
    //结束时间为空
    int ENDTIME_IS_EMPTY = 1013;
    //锁的事件为空
    int LOCK_EVENT_IS_EMPTY = 10014;
    //导航事件为空
    int NAVIHATION_EVENT_IS_EMPTY = 1015;
    //目的地为空
    int DESTINATION_IS_NOT_EMPTY = 1016;
    //目的地维度为空
    int DESTINATION_LATITUDE_IS_NOT_EMPTY = 1017;
    //目的地经度为空
    int DESTINATION_LONGITUDE_IS_NOT_EMPTY = 1018;
    //scooter 状态为空
    int SCOOTER_STATUS_IS_EMPTY = 1019;
    //锁后备箱状态为空
    int LOCKBOX_EVENT_IS_EMPTY = 1020;
    //维修类型为空
    int REPAIR_TYPE_IS_EMPTY = 1021;
    //预约时间为空
    int BOOKTIME_IS_EMPTY = 1022;
    //维修sooter的状态为空
    int REPAIT_SCOOTERSTATUS_IS_EMPTY = 1023;
    //车牌号为空
    int SCOOTER_LICENSE_IS_EMPTY = 1024;
    //关闭订单司机主键不能为空
    int DELIVERYID_IS_NOT_NOTZERO = 1025;
    //查询拒绝订单司机主键不能为空
    int QUERYREFUSEORDERINFO_IS_NOT_NOTZERO = 1026;
    // 维修单Id
    int REPAIR_ORDER_ID_IS_EMPTY = 1027;
    // 国家为空
    int COUNTRYID_EMPTY = 1028;
    // 城市为空
    int CITYID_EMPTY = 1029;
    // 区域为空
    int AREAID_EMPTY = 1030;

    /*******************repair***************************/

    int LOGIN_NAME_EMPTY1 = 1001;

    int LOGIN_PASSWORD_EMPTY = 1002;

    int LOGIN_NAME_AND_PASSWORD = 1003;

    int REPAIR_SHOPID_IS_EMPTY1 = 1004;
    //维修单id
    int REPAIR_ORDER_ID_IS_EMPTY1 = 1005;
    //拒绝维修单原因
    int REFUSE_REPIR_ORDER_REASON_IS_EMPTY = 1006;
    // 支付类型
    int PAYTYPE_IS_EMPTY = 1007;
    // 维修耗时
    int REPAIRDURATION_IS_EMPTY = 1008;
    // 消费金额
    int AMOUNT_OF_CONSUMPTION_IS_EMPTY = 1009;
    // 维修工列表为空
    int TRACELISTRESULT_IS_EMPTY = 1010;
    // 配件列表 为空
    int SPAREPARTLIST_IS_EMPTY = 1011;
    // 维修单 状态
    int REPAIR_ORDER_STATUS_IS_EMPTY = 1012;
    //维修单事件
    int REPAIR_ORDER_EVENT_IS_EMPTY = 1013;
    // staff id 为空
    int STAFF_ID_IS_EMPTY = 1014;
    //顾客Id
    int CUSTOMER_ID_IS_EMPTY = 1015;
    // 顾客姓名
    int CUSTOMER_NAME_IS_EMPTY = 1016;
    // 顾客类型
    int CUSTOMER_TYPE_IS_EMPTY = 1017;
    // 预约时间
    int REPAIRAPPOINTMENTTIME_IS_EMPTY1 = 1018;
    // 客户名字
    int CONTACTNUMBER_IS_EMPTY = 1019;
    // 维修类型
    int REPAIRTYPE_IS_EMPTY1 = 1020;
    // 服务类型
    int SERVICETYPE_IS_EMPTY1 = 1021;
    // 客户联系地址
    int CONTACTADDRESS_IS_EMPTY1 = 1022;
    // 拒绝原因
    int CANCEL_REASON_IS_EMPTY = 1023;
    // 车牌号列表
    int SCOOTERLICENSE_LIST_IS_EMPTY1 = 1024;
    // 联系地址经度
    int CONTACT_ADDRESS_LONGITUDE_IS_EMPTY1 = 1025;
    // 联系地址维度
    int CONTACT_ADDRESS_LATITUDE_IS_EMPTY1 = 1026;
    // 邮箱为空
//    int EMAIL_IS_EMPTY = 1027;
    // 旧密码为空
//    int OLD_PASSWORD_IS_EMPTY = 1028;
    // 新密码为空
    int NEWPASSWORD_IS_EMPTY = 1029;
    // 重复新秘密
    int CONFIRMNEW_PASSWORD_IS_EMPTY = 1030;
    // 发送邮件的邮箱为空
    int SENDCODE_MAIL_EMPTY = 1031;
    // 发送邮箱事件为空
    int SEND_CODE_EVENT = 1032;
    // 维修店 从周几营业
    int REPAIR_SHOP_START_WEEK_EMPTY = 1033;
    // 维修单 到周几结束
    int REPAIR_SHOP_END_WEEK_EMPTY = 1034;
    // 维修店 营业时间
    int REPAIR_SHOP_BEGINTIME_EMPTY = 1035;
    // 维修店 打样时间
    int REPAIR_SHOP_ENDTIME_EMPTY = 1036;
    // 维修店 服务范围
    int REPAIR_SHOP_SERVICE_RANGE_EMPTY = 1037;
    // 维修店 服务费用
    int REPAIR_SHOP_SERVICE_COST_EMPTY = 1038;
    // 维修单 店铺图片
    int REPAIR_SHOP_REPAIR_PICTURE_EMPTY = 1039;
    //  维修店 状态
    int REPAIR_SHOP_REPAIR_STATUS_EMPTY = 1040;
    // 负责人id 为空
    int PRINCIPAL_ID_EMPTY = 1041;
    // 国家ID 为空
    int COUNTRYID_EMPTY1 = 1042;
    // 城市为空
    int CITYID_EMPTY1 = 1043;
    // 区域为空
    int AREAID_EMPTY1 = 1044;
    // 维修店 类型为空
    int REPAIR_SHOP_TYPE_EMPTY = 1045;
    // 维修店名字为空
    int REPAIR_SHOP_NAME_EMPTY = 1046;
    //  维修店地址为空
    int REPAIR_SHOP_ADDRESS_EMPTY = 1047;
    // 维修店经度为空
    int REPAIR_SHOP_LONGITUDE_EMPTY = 1048;
    // 维修店维度为空
    int REPAIR_SHOP_LATITUDE_EMPTY = 1049;
    //  维修店负责人名字为空
    int REPAIR_SHOP_PRINCIPALNAME_EMPTY = 1050;
    //  维修店城市ID 为空
    int REPAIR_SHOP_CITYID_EMPTY = 1051;
    // 维修店区域ID 为空
    int REPAIR_SHOP_AREAID_EMPTY = 1052;
    // 维修店联系电话为空
    int REPAIR_SHOP_CONTACTNUMBER_EMPTY = 1053;
    // 维修店邮箱
    int REPAIR_SHOP_EMAIL_EMPTY = 1054;
    // 维修店 营业执照
    int REPAIR_SHOP_BUSINESSLICENSE_EMPTY = 1055;
    // 国家为空
    int REPAIR_SHOP_COUNTRYID_EMPTY = 1056;
    // 账户操作事件为空
    int ACCOUNT_OPERATION_EVENT_EMPTY = 1057;
    // sysuser id is empty
    int SYSUSER_ID_IS_EMPTY = 1058;
    // scooter id 是空的
    int SCOOTERID_IS_EMPTY1 = 1059;
    // 账户开通的有效期
    int DAYS_IS_EMPTY = 1060;
    //placeID 为空
    int PLACEID_IS_EMPTY1 = 1061;
    // 头像为空
    int PICTURE_IS_EMPTY1 = 1062;

    /********************************corporte-api-consumer**********************************/

    int DELIVERY_ID_EMPTY1 = 1001;

    int DELIVERY_EVENT_IS_EMPTY = 1002;

    int DELIVERY_LONGITUDE_EMPTY = 1003;

    int DELIVERY_LATITUDE_EMPTY =1004;

    int DRIVER_LONGITUDE_EMPTY=1005;

    int DRIVER_LATITUDE_EMPTY=1006;

    int SCOOTER_ID_EMPTY =1007;

    int RFIDSN_EMPTY =1008;

    int URL_EMPTY =1009;

    int MILEAGE_EMPTY=1010;
    // 极光注册ID
    int REGISTRATIONID_EMPTY = 1011;

    /********************************consumer-api-consumer**********************************/

    int ADDRESS_EMPTY=1001;
    // 维修店Id
    int REPAIR_SHOPID_IS_EMPTY11=1002;
    // 预约时间
    int REPAIRAPPOINTMENTTIME_IS_EMPTY11=1003;
    // 维修类型
    int REPAIRTYPE_IS_EMPTY11=1004;
    // 服务类型
    int SERVICETYPE_IS_EMPTY11=1005;
    // 客户联系地址
    int CONTACTADDRESS_IS_EMPTY11=1006;
    // 车牌号为空
    int SCOOTER_LICENSEPLATE_IS_EMPTY=1007;
    //国家为空
    int COUNTRYID_EMPTY11 = 1008;
    //城市为空
    int CITYID_EMPTY11 = 1009;
    // 区域为空
    int AREAID_EMPTY11 = 1010;
    // 极光注册Id
    int REGISTRATIONID_EMPTY11 = 1011;

    /********************************consumer*********************************/
    int INPUTTENANTID_IS_EMPTY=1001;

    int PICTURE_IS_EMPTY11=1002;

    int ID_CANNOT_EMPTY = 1011;

    int DAYNUM_CANNOT_EMPTY = 1022;

    /**********************************************************************/
    int LOGIN_INFO_EMPTY = 1001;

    int USERTYPE_EMPTY = 1002;

    int USERNAME_EMPTY = 1003;

    int EMAIL_EMPTY = 1004;

    int ADDRESS_EMPTY1 = 1005;

    int CITYID_EMPTY111 = 1006;

    int AREAID_EMPTY111 = 1007;

    int INDUSTY_EMPTY = 1008;

    int SOURCE_EMPTY = 1009;

    int SCOOTERQUANTITY_EMPTY = 1010;

    int ID_CANNOT_EMPTY1 = 1011;

    int SENDCODE_MAIL_EMPTY1 = 1012;

    int SEND_CODE_EVENT1 = 1013;

    int STATUS_CANNOT_EMPTY = 1014;

    int PHONE_CANNOT_EMPTY = 1015;

    int COUNTRY_CANNOT_EMPTY = 1016;

    int IDCARD_CANNOT_EMPTY = 1017;

    int IDCARDPOSITIVE_CANNOT_EMPTY = 1018;

    int IDCARDNEGATIVE_CANNOT_EMPTY = 1019;

    int BUSINESSLICENSENUM_CANNOT_EMPTY = 1020;

    int BUSINESSLICENSEPICTURE_CANNOT_EMPTY = 1021;

    int DAYNUM_CANNOT_EMPTY1 = 1022;
    //传入租户id不能为空
    int INPUTTENANTID_NOT_IS_EMPTY1 = 1023;

    int CHANAGEPASSWORD_LOGINNAME_EMPTY=1024;

    int CHANAGEPASSOWRD_OLDPASSWORD_EMPTY=1025;

    int CHANAGEPASSWORD_NEWPASSWORD_EMPTY=1026;

    int EMAIL_IS_NOT_AVAILABLE=1027;

    int PHONE_IS_NOT_AVAILABLE=1028;

    int USERNAME_IS_NOT_AVAILABLE=1029;

    int ADDRESS_IS_NOT_AVAILABLE1=1030;

    int SCOOTERQUANTITY_IS_NOT_AVAILABLE=1031;

    int IDCARD_IS_NOT_AVAILABLE=1032;

    int BUSINESSLICENSENUM_IS_NOT_AVAILABLE=1033;

    int SEND_NAME_EMPTY=1034;
    // 维修店 类型为空
    int REPAIR_SHOP_TYPE_EMPTY1 = 1035;
    // 维修店名字为空
    int REPAIR_SHOP_NAME_EMPTY1 = 1036;
    //  维修店地址为空
    int REPAIR_SHOP_ADDRESS_EMPTY1 = 1037;
    // 维修店经度为空
    int REPAIR_SHOP_LONGITUDE_EMPTY1 = 1038;
    // 维修店维度为空
    int REPAIR_SHOP_LATITUDE_EMPTY1 = 1039;
    //  维修店负责人名字为空
    int REPAIR_SHOP_PRINCIPALNAME_EMPTY1 = 1040;
    //  维修店城市ID 为空
    int REPAIR_SHOP_CITYID_EMPTY1 = 1041;
    // 维修店区域ID 为空
    int REPAIR_SHOP_AREAID_EMPTY1 = 1042;
    // 维修店联系电话为空
    int REPAIR_SHOP_CONTACTNUMBER_EMPTY1 = 1043;
    // 维修店邮箱
    int REPAIR_SHOP_EMAIL_EMPTY1 = 1044;
    // 维修店 营业执照
    int REPAIR_SHOP_BUSINESSLICENSE_EMPTY1 = 1045;
    // 国家为空
    int REPAIR_SHOP_COUNTRYID_EMPTY1 = 1046;

    //邮箱地址不能为空
    int EMAILADRESS_EMPTY=1022;


}
