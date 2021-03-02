package com.redescooter.ses.api.common.exception;

/**
 * @description: ValidationExceptionCode
 * 方法、参数、异常类型
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionBaseCode {
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
    //密码为空
    int PASSWORD_IS_EMPTY = 10009;
    //密码不合法
    int PASSWORD_IS_ILLEGAL = 10010;
    // 邮箱非法
    int EMAIL_IS_ILLEGAL = 10011;
    //经度违法
    int LON_IS_ILLEGAL = 10014;
    //维度违法
    int LAT_IS_ILLEGAL = 10015;
    //密码不合法
    int PASSWORD_ILLEAGE = 10016;
    //验证码错误
    int VERIFICATION_CODE_ERROR = 10017;
    //名称不能为空
    int NAME_ILLEAGE = 10017;
    //时间不能为空
    int TIME_IS_NOT_NULL = 10018;

    /**
     * 10031-99999 业务异常
     */
    // 发布类型为空
    int RELEASE_TYPE_IS_EMPTY = 10031;
    // 应用类型为空
    int APP_TYPE_IS_EMPTY = 10032;
    //邮件模板编号不能为空
    int MAIL_TEMPLATE_NUMBER_IS_EMPTY = 10034;

}
