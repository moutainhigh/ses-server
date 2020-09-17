package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 系统日志表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysLog")
@Data
@TableName(value = "ope_sys_log")
public class OpeSysLog implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0：正常，1：删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0：正常，1：删除")
    private Integer dr;

    /**
     * 操作模块
     */
    @TableField(value = "op_modul")
    @ApiModelProperty(value = "操作模块")
    private String opModul;

    /**
     * 操作人名称
     */
    @TableField(value = "op_user_name")
    @ApiModelProperty(value = "操作人名称")
    private String opUserName;

    /**
     * 操作人编码
     */
    @TableField(value = "op_user_code")
    @ApiModelProperty(value = "操作人编码")
    private String opUserCode;

    /**
     * 操作人部门
     */
    @TableField(value = "op_user_dept_name")
    @ApiModelProperty(value = "操作人部门")
    private String opUserDeptName;

    /**
     * 登陆的IP
     */
    @TableField(value = "login_ip")
    @ApiModelProperty(value = "登陆的IP")
    private String loginIp;

    /**
     * 请求地址
     */
    @TableField(value = "request_address")
    @ApiModelProperty(value = "请求地址")
    private String requestAddress;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    /**
     * 请求方式
     */
    @TableField(value = "request_type")
    @ApiModelProperty(value = "请求方式")
    private String requestType;

    /**
     * 返回参数
     */
    @TableField(value = "response_param")
    @ApiModelProperty(value = "返回参数")
    private String responseParam;

    /**
     * 异常信息
     */
    @TableField(value = "exp_msg")
    @ApiModelProperty(value = "异常信息")
    private String expMsg;

    /**
     * 日志类型，1:登陆，2：操作
     */
    @TableField(value = "log_type")
    @ApiModelProperty(value = "日志类型，1:登陆，2：操作")
    private Integer logType;

    /**
     * 请求是否成功，1：是，2：否
     */
    @TableField(value = "if_success")
    @ApiModelProperty(value = "请求是否成功，1：是，2：否")
    private Integer ifSuccess;

    /**
     * 请求时长（毫秒）
     */
    @TableField(value = "time_consum")
    @ApiModelProperty(value = "请求时长（毫秒）")
    private Long timeConsum;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_OP_MODUL = "op_modul";

    public static final String COL_OP_USER_NAME = "op_user_name";

    public static final String COL_OP_USER_CODE = "op_user_code";

    public static final String COL_OP_USER_DEPT_NAME = "op_user_dept_name";

    public static final String COL_LOGIN_IP = "login_ip";

    public static final String COL_REQUEST_ADDRESS = "request_address";

    public static final String COL_REQUEST_PARAM = "request_param";

    public static final String COL_REQUEST_TYPE = "request_type";

    public static final String COL_RESPONSE_PARAM = "response_param";

    public static final String COL_EXP_MSG = "exp_msg";

    public static final String COL_LOG_TYPE = "log_type";

    public static final String COL_IF_SUCCESS = "if_success";

    public static final String COL_TIME_CONSUM = "time_consum";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}