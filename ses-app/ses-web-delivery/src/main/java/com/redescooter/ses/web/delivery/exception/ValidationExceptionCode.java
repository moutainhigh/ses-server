package com.redescooter.ses.web.delivery.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {

    // 10000-10030 公共异常

    // 经度为空
    int LAT_IS_EMPTY = 10001;
    // 纬度为空
    int LNG_IS_EMPTY = 10002;
    // 原因为空
    int REASON_IS_EMPTY = 10003;
    // 事件为空
    int EVENT_IS_EMPTY = 10004;
    // 业务对象为空
    int BUSSINESS_OBJ_IS_EMPTY = 10005;
    // 图片为空
    int PICTURE_IS_EMPTY = 10006;
    // 邮箱为空
    int EMAIL_IS_EMPTY = 10007;

    //10030 之后业务异常

    // 名为空
    int FIRST_NAME_IS_EMPTY = 10031;
    // 名不可用
    int FIRST_NAME_IS_UNAVAILABLE = 10032;
    // 姓为空
    int LAST_NAME_IS_EMPTY = 10032;
    // 姓不可用
    int LAST_NAME_IS_UNAVAILABLE = 10033;
    // 性别为空
    int GENDER_IS_EMPTY = 10034;
    // 手机区号为空
    int COUNTRY_CODE_IS_EMPTY = 10035;
    // 手机号为空
    int PHONE_IS_EMPTY = 10036;
    // 手机号不可用
    int PHONE_IS_UNAVAILABLE = 10037;
    // 邮箱不可用
    int EMAIL_IS_UNAVAILABLE = 10038;
    // 地址为空
    int ADDRESS_IS_EMPTY = 10039;
    // 地址不可用
    int ADDRESS_IS_UNAVAILABLE = 10040;
    // 生日为空
    int BIRTHDAY_IS_EMPTY = 10041;
    // 驾驶证为空
    int DRIVER_LICENSE_IS_EMPTY = 10042;
    // 驾驶证 正面 为空
    int DRIVER_LICENSE_UPANNEX_IS_EMPTY = 10043;
    // 驾驶证反面为空
    int DRIVER_LICENSE_DOWNANNEX_IS_EMPTY = 10044;
}
