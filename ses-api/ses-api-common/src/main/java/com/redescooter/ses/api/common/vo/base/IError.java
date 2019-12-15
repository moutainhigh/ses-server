package com.redescooter.ses.api.common.vo.base;

/**
 * 功能描述: 多服务错误返回
 *
 * @auther: jerry
 * @date: 2019-06-26 16:47
 */
public interface IError {

    /**
     * 服务名
     *
     * @return nameSpace
     */
    String getNameSpace();

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    String getErrorCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getErrorMessage();
}
