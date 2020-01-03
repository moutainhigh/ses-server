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

    //10030 之后业务异常

    // 名为空
    int FIRST_NAME_IS_EMPTY = 10031;
    // 名不可用
    int FIRST_NAME_IS_UNAVAILABLE = 10032;


    int LAST_NAME_IS_EMPTY = 10032;

    int LAST_NAME_IS_UNAVAILABLE = 10033;
}
