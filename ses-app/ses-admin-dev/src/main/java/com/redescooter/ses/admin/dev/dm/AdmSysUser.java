package com.redescooter.ses.admin.dev.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
    * 用户表
    */
@ApiModel(value="com-redescooter-ses-admin-dev-dm-AdmSysUser")
@Data
@TableName(value = "adm_sys_user")
public class AdmSysUser {
    /**
     * ID
     */
    @TableId(value = "id")
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 组织部门ID
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value="组织部门ID")
    private Long deptId;

    /**
     * 员工主键
     */
    @TableField(value = "org_staff_id")
    @ApiModelProperty(value="员工主键")
    private Long orgStaffId;

    /**
     * 应用ID
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value="应用ID")
    private String appId;

    /**
     * 系统Id
     */
    @TableField(value = "system_id")
    @ApiModelProperty(value="系统Id")
    private String systemId;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value="盐")
    private String salt;

    /**
     * 状态 Normal,Lock,Cancel，Expired
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 Normal,Lock,Cancel，Expired")
    private String status;

    /**
     * 登录名
     */
    @TableField(value = "login_name")
    @ApiModelProperty(value="登录名")
    private String loginName;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="最后登录时间")
    private Date lastLoginTime;

    /**
     * 最后登录TOKEN
     */
    @TableField(value = "last_login_token")
    @ApiModelProperty(value="最后登录TOKEN")
    private String lastLoginToken;

    /**
     * 最后登录IP地址
     */
    @TableField(value = "last_login_ip")
    @ApiModelProperty(value="最后登录IP地址")
    private String lastLoginIp;

    /**
     * 账户激活时间
     */
    @TableField(value = "activation_time")
    @ApiModelProperty(value="账户激活时间")
    private Date activationTime;

    /**
     * 账户到期时间
     */
    @TableField(value = "expire_date")
    @ApiModelProperty(value="账户到期时间")
    private Date expireDate;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    /**
     * 冗余字段
     */
    @TableField(value = "name")
    @ApiModelProperty(value="使用者")
    private String name;

    /**
     * 冗余字段
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value="部门名称")
    private Double deptName;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_ORG_STAFF_ID = "org_staff_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SALT = "salt";

    public static final String COL_STATUS = "status";

    public static final String COL_LOGIN_NAME = "login_name";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_LAST_LOGIN_TOKEN = "last_login_token";

    public static final String COL_LAST_LOGIN_IP = "last_login_ip";

    public static final String COL_ACTIVATION_TIME = "activation_time";

    public static final String COL_EXPIRE_DATE = "expire_date";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static final String COL_NAME = "name";

    public static final String COL_DEPT_NAME = "dept_name";
}