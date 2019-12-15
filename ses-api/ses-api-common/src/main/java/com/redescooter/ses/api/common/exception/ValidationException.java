package com.redescooter.ses.api.common.exception;

public class ValidationException extends BaseException {

    public static final String DEFAULE_ERRORCODE = "-1";

    public static final String DEFAULT_ERRORMSG = "unknown error";

    private static final long serialVersionUID = 345573995122478L;
    /**
     * 可替换变量,相当于占位符
     */
    private String[] variables = null;

    public ValidationException() {
        super(DEFAULE_ERRORCODE, DEFAULT_ERRORMSG);
    }

    /**
     * 使用错误码定义BusinessException
     *
     * @param errorCode 错误码
     */
    public ValidationException(int errorCode) {
        super(String.valueOf(errorCode), null);
    }

    /**
     * 使用错误码、提示信息的变量定义BusinessException
     *
     * @param errorCode 错误码
     * @param variables 用于在提示信息中展示的参数
     */
    public ValidationException(int errorCode, String[] variables) {
        super(String.valueOf(errorCode), null);
        this.variables = (variables == null ? null : variables.clone());
    }

    /**
     * 使用错误码、默认提示定义BusinessException
     *
     * @param errorCode    错误码
     * @param errorMessage 默认错误信息
     */
    public ValidationException(int errorCode, String errorMessage) {
        super(String.valueOf(errorCode), errorMessage);
    }

    /**
     * 使用错误码、默认提示、提示信息的变量定义BusinessException
     *
     * @param errorCode    错误码
     * @param errorMessage 默认错误信息
     * @param variables    用于在提示信息中展示的参数
     */
    public ValidationException(int errorCode, String errorMessage, String[] variables) {
        super(String.valueOf(errorCode), errorMessage);
        this.variables = (variables == null ? null : variables.clone());
    }

    /**
     * 将异常封装为BusinessException，并指定错误码
     *
     * @param errorCode 错误码
     * @param t         源异常
     */
    public ValidationException(int errorCode, Throwable t) {
        super(String.valueOf(errorCode), null, t);
    }

    /**
     * 将异常封装为BusinessException，并指定错误码和默认提示
     *
     * @param errorCode    错误码
     * @param errorMessage 错误描述
     * @param t            源异常
     */
    public ValidationException(int errorCode, String errorMessage, Throwable t) {
        super(String.valueOf((errorCode)), errorMessage, t);
    }

    /**
     * 将异常封装为BusinessException，并指定错误码和提示信息的变量
     *
     * @param errorCode 错误码
     * @param variables 参数
     * @param t         源异常
     */
    public ValidationException(int errorCode, String[] variables, Throwable t) {
        super(String.valueOf(errorCode), null, t);
        this.variables = (variables == null ? null : variables.clone());
    }

    /**
     * 将异常封装为BusinessException，并指定错误码、默认提示、提示信息的变量
     *
     * @param errorCode    错误码
     * @param errorMessage 默认错误信息
     * @param variables    用于在提示信息中展示的参数
     * @param t            源异常
     */
    public ValidationException(int errorCode, String errorMessage, String[] variables, Throwable t) {
        super(String.valueOf(errorCode), errorMessage, t);
        this.variables = (variables == null ? null : variables.clone());
    }


    public ValidationException(Throwable cause) {
        super(DEFAULE_ERRORCODE, DEFAULT_ERRORMSG, cause);
    }

    /**
     * 异常堆栈增加错误代码和绑定变量
     *
     * @return 错误信息
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("errorCode[").append(getErrorCode()).append("]\n");
        if (variables != null && variables.length != 0) {
            sb.append("variables[");
            for (int i = 0; i < variables.length; i++) {
                sb.append(variables[i]);
            }
            sb.append("]\n");
        }
        sb.append(super.toString());
        return sb.toString();
    }

}
