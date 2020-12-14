package com.redescooter.ses.api.foundation.exception;

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
    // id 为空
    int ID_IS_EMPTY = 10008;
    // 注册Id 为空
    int REGISTRATIONID_EMPTY = 10009;
    // I18 配置为空
    int I18NCONFIG_ID_NOTZERO = 10010;

    //客户端类型为空
    int CLIENT_TYPE_IS_EMPTY = 10011;

    //客户端代码为空
    int CLIENT_CODE_IS_EMPTY = 10012;

    //名称为空
    int NAME_IS_EMPTY = 10013;

    //分组id 为空
    int GROUP_ID_IS_EMPTY = 10014;

    //key值为空
    int KEY_IS_EMPTY = 10015;

    //value 为空
    int VALUE_IS_EMPTY = 10016;

    //启用按钮为空
    int ENABLE_IS_EMPTY = 10017;

    // 版本类型不能为空
    int VERSION_TYPE_IS_NOT_EMPTY = 10018;
    // 版本更新内容不能为空
    int VERSION_UPDATE_CONTENT_IS_NOT_EMPTY = 10019;
    // 版本更新包地址不能为空
    int VERSION_UPDATE_LINK_IS_NOT_EMPTY = 10020;
    // 版本号不能为空
    int VERSION_NUMBER_IS_NOT_EMPTY = 10021;
    // 版本编码不能为空
    int VERSION_CODE_IS_NOT_EMPTY = 10022;
    // 版本发布环境不能为空
    int VERSION_RELEASE_ENVIRONMENT_IS_NOT_EMPTY = 10023;

}
