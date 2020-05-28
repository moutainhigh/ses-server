package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaUser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pla_user")
public class PlaUser implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "TENANT_ID")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 系统ID
     */
    @TableField(value = "SYSTEM_ID")
    @ApiModelProperty(value = "系统ID")
    private String systemId;

    /**
     * 应用ip，SAAS_WEB:SaaS配送，SAAS_APP:SaaS移动，SAAS_REPAIR_WEB:SaaS维修，SES_ROS:RedE办公系统，SES_DEVL:RedE开发系统
     */
    @TableField(value = "APP_ID")
    @ApiModelProperty(value = "应用ip，SAAS_WEB:SaaS配送，SAAS_APP:SaaS移动，SAAS_REPAIR_WEB:SaaS维修，SES_ROS:RedE办公系统，SES_DEVL:RedE开发系统")
    private String appId;

    /**
     * 登录名
     */
    @TableField(value = "LOGIN_NAME")
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**
     * 登录类型
     */
    @TableField(value = "LOGIN_TYPE")
    @ApiModelProperty(value = "登录类型")
    private Integer loginType;

    /**
     * 状态 Normal,Lock,Cancel
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态 Normal,Lock,Cancel")
    private String status;

    /**
     * 用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端
     */
    @TableField(value = "USER_TYPE")
    @ApiModelProperty(value = "用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端")
    private Integer userType;

    /**
     * 最后登录时间
     */
    @TableField(value = "LAST_LOGIN_TIME")
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    /**
     * 最后登录TOKEN
     */
    @TableField(value = "LAST_LOGIN_TOKEN")
    @ApiModelProperty(value = "最后登录TOKEN")
    private String lastLoginToken;

    /**
     * 生效时间
     */
    @TableField(value = "EFFECTIVE_TIME")
    @ApiModelProperty(value = "生效时间")
    private Date effectiveTime;

    /**
     * 激活时间
     */
    @TableField(value = "ACTIVATION_TIME")
    @ApiModelProperty(value = "激活时间")
    private Date activationTime;

    /**
     * 到期时间
     */
    @TableField(value = "EXPIRE_TIME")
    @ApiModelProperty(value = "到期时间")
    private Date expireTime;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATED_BY")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 最后登录IP地址
     */
    @TableField(value = "LAST_LOGIN_IP")
    @ApiModelProperty(value = "最后登录IP地址")
    private String lastLoginIp;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "ID";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "TENANT_ID";

    public static final String COL_SYSTEM_ID = "SYSTEM_ID";

    public static final String COL_APP_ID = "APP_ID";

    public static final String COL_LOGIN_NAME = "LOGIN_NAME";

    public static final String COL_LOGIN_TYPE = "LOGIN_TYPE";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_USER_TYPE = "USER_TYPE";

    public static final String COL_LAST_LOGIN_TIME = "LAST_LOGIN_TIME";

    public static final String COL_LAST_LOGIN_TOKEN = "LAST_LOGIN_TOKEN";

    public static final String COL_EFFECTIVE_TIME = "EFFECTIVE_TIME";

    public static final String COL_ACTIVATION_TIME = "ACTIVATION_TIME";

    public static final String COL_EXPIRE_TIME = "EXPIRE_TIME";

    public static final String COL_CREATED_BY = "CREATED_BY";

    public static final String COL_CREATED_TIME = "CREATED_TIME";

    public static final String COL_UPDATED_BY = "UPDATED_BY";

    public static final String COL_UPDATED_TIME = "UPDATED_TIME";

    public static final String COL_LAST_LOGIN_IP = "LAST_LOGIN_IP";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static PlaUserBuilder builder() {
        return new PlaUserBuilder();
    }
}
