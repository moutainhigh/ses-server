package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaJpushUser")
@Data
@TableName(value = "pla_jpush_user")
public class PlaJpushUser implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 用户主键
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户主键")
    private Long userId;

    /**
     * 设备唯一标识
     */
    @TableField(value = "registration_id")
    @ApiModelProperty(value = "设备唯一标识")
    private String registrationId;

    /**
     * 标签
     */
    @TableField(value = "tag")
    @ApiModelProperty(value = "标签")
    private String tag;

    /**
     * 别名
     */
    @TableField(value = "alias")
    @ApiModelProperty(value = "别名")
    private String alias;

    /**
     * 推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为："android", "ios", "winphone"。
     */
    @TableField(value = "platform_type")
    @ApiModelProperty(value = "推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为：android, ios, winphone。")
    private String platformType;

    /**
     * 推送目标:别名ALIAS、标签TAG、注册唯一属性REGISTRATION_ID、分群SEGMENT、广播
     */
    @TableField(value = "audience_type")
    @ApiModelProperty(value = "推送目标:别名ALIAS、标签TAG、注册唯一属性REGISTRATION_ID、分群SEGMENT、广播")
    private String audienceType;

    /**
     * 推送时间
     */
    @TableField(value = "push_time")
    @ApiModelProperty(value = "推送时间")
    private Date pushTime;

    /**
     * 登录绑定：0，注销解绑：1
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "登录绑定：0，注销解绑：1")
    private Integer status;

    /**
     * 状态码：登录绑定LOGIN，注销解绑LOGOUT
     */
    @TableField(value = "status_code")
    @ApiModelProperty(value = "状态码：登录绑定LOGIN，注销解绑LOGOUT")
    private String statusCode;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Long createBy;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_REGISTRATION_ID = "registration_id";

    public static final String COL_TAG = "tag";

    public static final String COL_ALIAS = "alias";

    public static final String COL_PLATFORM_TYPE = "platform_type";

    public static final String COL_AUDIENCE_TYPE = "audience_type";

    public static final String COL_PUSH_TIME = "push_time";

    public static final String COL_STATUS = "status";

    public static final String COL_STATUS_CODE = "status_code";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}