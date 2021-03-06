package com.redescooter.ses.api.mobile.b.exception;

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
    // id 为空
    int ID_IS_EMPTY = 10008;

    // 距离为空
    int MILEAGE_IS_EMPTY = 10031;

    //数据非法
    int DATA_IS_ILLEGLE=10032;
    //原因字符过长
    int REASON_CHARACTER_IS_TOO_LONG =10033;
    //原因非法
    int REASON_IS_ILLEGAL=10034;
    // 名字太长了
    int NAME_IS_TOO_LONG=10035;
}
