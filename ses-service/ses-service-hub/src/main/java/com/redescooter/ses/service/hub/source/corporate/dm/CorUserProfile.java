package com.redescooter.ses.service.hub.source.corporate.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.redescooter.ses.service.hub.source.corporate.dm.CorUserProfile")
@Data
@TableName(value = "cor_user_profile")
public class CorUserProfile implements Serializable {
    /**
     * ID
     */
     @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 是否主账号:1-主账号,0-子账号
     */
    @TableField(value = "is_master")
    @ApiModelProperty(value="是否主账号:1-主账号,0-子账号")
    private Integer isMaster;

    /**
     * 照片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value="照片")
    private String picture;

    /**
     * 名
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value="名")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value="姓")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value="全名")
    private String fullName;

    /**
     * 邮箱
     */
    @TableField(value = "email_1")
    @ApiModelProperty(value="邮箱")
    private String email1;

    /**
     * 邮箱
     */
    @TableField(value = "email_2")
    @ApiModelProperty(value="邮箱")
    private String email2;

    /**
     * 邮箱
     */
    @TableField(value = "email_3")
    @ApiModelProperty(value="邮箱")
    private String email3;

    /**
     * 电话号
     */
    @TableField(value = "tel_number_1")
    @ApiModelProperty(value="电话号")
    private String telNumber1;

    /**
     * 电话号
     */
    @TableField(value = "tel_number_2")
    @ApiModelProperty(value="电话号")
    private String telNumber2;

    /**
     * 电话号
     */
    @TableField(value = "tel_number_3")
    @ApiModelProperty(value="电话号")
    private String telNumber3;

    /**
     * 性别
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @TableField(value = "CERTIFICATE_TYPE")
    @ApiModelProperty(value="证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件反面图片
     */
    @TableField(value = "CERTIFICATE_NEGATIVE_ANNEX")
    @ApiModelProperty(value="证件反面图片")
    private String certificateNegativeAnnex;

    /**
     * 证件正面图片
     */
    @TableField(value = "CERTIFICATE_POSITIVE_ANNEX")
    @ApiModelProperty(value="证件正面图片")
    private String certificatePositiveAnnex;

    /**
     * 角色 DRIVER-司机；MANAGE-管理者
     */
    @TableField(value = "role")
    @ApiModelProperty(value="角色 DRIVER-司机；MANAGE-管理者")
    private String role;

    /**
     * 页面引导提示开关，默认true 打开
     */
    @TableField(value = "page_boot_tips")
    @ApiModelProperty(value="页面引导提示开关，默认true 打开")
    private Boolean pageBootTips;

    /**
     * 出生地
     */
    @TableField(value = "place_birth")
    @ApiModelProperty(value="出生地")
    private String placeBirth;

    /**
     * 加入日期
     */
    @TableField(value = "join_date")
    @ApiModelProperty(value="加入日期")
    private Date joinDate;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    @ApiModelProperty(value="时区")
    private String timeZone;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_IS_MASTER = "is_master";

    public static final String COL_PICTURE = "picture";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_EMAIL_1 = "email_1";

    public static final String COL_EMAIL_2 = "email_2";

    public static final String COL_EMAIL_3 = "email_3";

    public static final String COL_TEL_NUMBER_1 = "tel_number_1";

    public static final String COL_TEL_NUMBER_2 = "tel_number_2";

    public static final String COL_TEL_NUMBER_3 = "tel_number_3";

    public static final String COL_GENDER = "gender";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_CERTIFICATE_TYPE = "CERTIFICATE_TYPE";

    public static final String COL_CERTIFICATE_NEGATIVE_ANNEX = "CERTIFICATE_NEGATIVE_ANNEX";

    public static final String COL_CERTIFICATE_POSITIVE_ANNEX = "CERTIFICATE_POSITIVE_ANNEX";

    public static final String COL_ROLE = "role";

    public static final String COL_PAGE_BOOT_TIPS = "page_boot_tips";

    public static final String COL_PLACE_BIRTH = "place_birth";

    public static final String COL_JOIN_DATE = "join_date";

    public static final String COL_TIME_ZONE = "time_zone";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}