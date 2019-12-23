package com.redescooter.ses.service.hub.exception;

/**
 * @description: ExceptionCode
 * @author: Darren
 * @create: 2019/01/17 15:02
 */
public interface ExceptionCode {

    /**
     * 10000-10020 登陆相关的异常
     */
    //token 不存在
    int TOKEN_NOT_EXIST = 10001;
    // 用户名不存在
    int LOGIN_USER_NOT_EXIST = 10002;
    // 密码为空
    int LOGIN_PASSWORD_EMPTY = 10003;
    // 账户未激活
    int ACCOUNT_NOT_ACTIVATED = 10004;
    // 密码错误
    int PASSROD_WRONG = 10005;
    // 账户被冻结
    int THE_ACCOUNT_HAS_BEEN_FROZEN = 10006;
    // 账户被注销了
    int ACCOUNT_CANCELLED = 10007;
    // 用户信息不正确
    int USERTOKEN_SERVICE_OPERATION_ABNORMAL = 10008;
    // 账户过期
    int ACCOUNT_EXPIRED = 10009;
    // 密码不一致
    int INCONSISTENT_PASSWORD = 10010;
    // 账户已经存在
    int ACCOUNT_ALREADY_EXIST = 10011;
    // 创建租户失败
    int CREATE_TENANT_FAILURE = 10021;
    // 租户不存在
    int TENANT_IS_NOT_EXIST = 10022;
    // 更新店铺配置失败
    int PERFECT_TENANT_CONFIG_FAILURE = 10023;
    // 完善个人信息失败
    int IMPROVE_PERSONAL_INFORMATION_FAILURE = 10024;
    // 租户被冻结
    int TENANT_IS_FROZEN = 10025;
    //车不存在
    int SCOOTER_IS_NOT_EXIST = 10026;
    // 客户不存在
    int CUSTOMER_IS_NOT_EXIST = 10027;
    // 更新头像失败
    int UPDATE_CUSTOMER_AVATAR_FAILURE = 10028;
    // 维修店 不存在
    int REPAIR_ORDER_SHOP_NOT_EXIST = 10029;
    // 营业时间为空
    int REPAIR_SHOPBUSINESS_HOURS_IS_EMPTY = 10030;
    // 维修店已经关闭
    int REPAIR_SHOP_IS_CLOSE = 10031;
    // 不在预约时间内
    int APPOINTMENT_TIME_IS_NOT_WITHIN_THE_BUSINESS_SCOPE = 10032;
    // 维修单正在被维修
    int ORDERS_BEING_REPAIRED = 10033;
    // 用户不存在
    int SYSUSER_IS_NOT_EXIST = 10034;
    // 部门不存在
    int ORGDEPARTMENT_NOT_EXIST = 10035;
    // 员工不存在
    int STAFF_NOT_EXIST = 10036;
    // 车子正在被维修
    int SCOOTER_IS_REPAIRING = 10037;
    // 维修店负责人为空
    int REPAIR_SHOP_PRINCIPLE_NOT_EMPTY = 10038;
    // 维修单 已经取消
    int REPAIR_ORDER_IS_ALREADY_CANCELED = 10039;
    // 个人信息不存在
    int SYSUSER_PROFILE_NOT_EXIST = 10040;
    // 续期时间格式不正确
    int RENEWAL_DAYS_FORMAT_IS_INCORRECT = 10041;
    // 维修店 联系电话为空
    int REPAIR_SHOP_CONTACT_ATTACHMENT_EMPTY = 10042;
    // 维修单不存在
    int REPAIR_ORDER_NOT_EXIST = 10043;
    // 维修单 已经开始=
    int REPAIR_ORDER_IS_ALREADY_REPAIRING = 10044;
    // 维修单已经完成
    int REPAIR_ORDER_IS_ALREADY_COMPLETE = 10045;
    // 维修单已经拒绝
    int REPAIR_ORDER_IS_ALREADY_REFUSE = 10046;
    // 维修单没有接受
    int REPAIR_ORDER_IS_NOT_ACCEPT = 10047;
    // 维修店 零不件不存在
    int REPAIRSHOP_SPAREPART_NOT_EXIST = 10048;
    // 维修店 零部件库存不足
    int REPAIRSHOP_SPAREPART_INVENTORY_SHORTAGE = 10049;
    // 车辆没有维修单
    int THE_VEHICLE_DOES_NOT_HAVE_ALL_THE_REPAIR_ORDERS = 10050;

    int USER_IS_NOT_EXIST = 10051;
    // 邮箱已经存在
    int EMAIL_IS_ALREADY_EXIST = 10052;
}
