package com.redescooter.ses.api.common.exception;

/**
 * @description: ValidationExceptionCode
 * 方法、参数、异常类型
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
    // id为空
    int ID_IS_EMPTY = 10008;
    // id为空
    int IN_WAIT_WH_QTY = 10009;
    // 序列号
    int SERIAL_NUM = 10009;
}
