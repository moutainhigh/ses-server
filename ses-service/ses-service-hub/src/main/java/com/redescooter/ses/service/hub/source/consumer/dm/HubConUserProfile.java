package com.redescooter.ses.service.hub.source.consumer.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com.redescooter.ses.service.hub.source.consumer.dm.HubConUserProfile")
@Data
@TableName(value = "con_user_profile")
public class HubConUserProfile implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
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
    @TableField(value = "TENANT_ID")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 姓
     */
    @TableField(value = "FIRST_NAME")
    @ApiModelProperty(value="姓")
    private String firstName;

    /**
     * 名
     */
    @TableField(value = "LAST_NAME")
    @ApiModelProperty(value="名")
    private String lastName;

    /**
     * 姓名
     */
    @TableField(value = "FULL_NAME")
    @ApiModelProperty(value="姓名")
    private String fullName;

    /**
     * 照片
     */
    @TableField(value = "PICTURE")
    @ApiModelProperty(value="照片")
    private String picture;

    /**
     * 邮箱
     */
    @TableField(value = "EMAIL_1")
    @ApiModelProperty(value="邮箱")
    private String email1;

    /**
     * 邮箱
     */
    @TableField(value = "EMAIL_2")
    @ApiModelProperty(value="邮箱")
    private String email2;

    /**
     * 邮箱
     */
    @TableField(value = "EMAIL_3")
    @ApiModelProperty(value="邮箱")
    private String email3;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code_1")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode1;

    /**
     * 电话号
     */
    @TableField(value = "TEL_NUMBER_1")
    @ApiModelProperty(value="电话号")
    private String telNumber1;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code_2")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode2;

    /**
     * 电话号
     */
    @TableField(value = "TEL_NUMBER_2")
    @ApiModelProperty(value="电话号")
    private String telNumber2;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code_3")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode3;

    /**
     * 电话号
     */
    @TableField(value = "TEL_NUMBER_3")
    @ApiModelProperty(value="电话号")
    private String telNumber3;

    /**
     * 性别
     */
    @TableField(value = "GENDER")
    @ApiModelProperty(value="性别")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "BIRTHDAY")
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
    @TableField(value = "ROLE")
    @ApiModelProperty(value="角色 DRIVER-司机；MANAGE-管理者")
    private String role;

    /**
     * 出生地
     */
    @TableField(value = "PLACE_BIRTH")
    @ApiModelProperty(value="出生地")
    private String placeBirth;

    /**
     * 居住地址
     */
    @TableField(value = "ADDRESS")
    @ApiModelProperty(value="居住地址")
    private String address;

    /**
     * 加入日期
     */
    @TableField(value = "JOIN_DATE")
    @ApiModelProperty(value="加入日期")
    private Date joinDate;

    /**
     * 时区
     */
    @TableField(value = "TIME_ZONE")
    @ApiModelProperty(value="时区")
    private String timeZone;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATED_BY")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME")
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

    public static final String COL_TENANT_ID = "TENANT_ID";

    public static final String COL_USER_ID = "USER_ID";

    public static final String COL_FIRST_NAME = "FIRST_NAME";

    public static final String COL_LAST_NAME = "LAST_NAME";

    public static final String COL_FULL_NAME = "FULL_NAME";

    public static final String COL_PICTURE = "PICTURE";

    public static final String COL_EMAIL_1 = "EMAIL_1";

    public static final String COL_EMAIL_2 = "EMAIL_2";

    public static final String COL_EMAIL_3 = "EMAIL_3";

    public static final String COL_COUNTRY_CODE_1 = "country_code_1";

    public static final String COL_TEL_NUMBER_1 = "TEL_NUMBER_1";

    public static final String COL_COUNTRY_CODE_2 = "country_code_2";

    public static final String COL_TEL_NUMBER_2 = "TEL_NUMBER_2";

    public static final String COL_COUNTRY_CODE_3 = "country_code_3";

    public static final String COL_TEL_NUMBER_3 = "TEL_NUMBER_3";

    public static final String COL_GENDER = "GENDER";

    public static final String COL_BIRTHDAY = "BIRTHDAY";

    public static final String COL_CERTIFICATE_TYPE = "CERTIFICATE_TYPE";

    public static final String COL_CERTIFICATE_NEGATIVE_ANNEX = "CERTIFICATE_NEGATIVE_ANNEX";

    public static final String COL_CERTIFICATE_POSITIVE_ANNEX = "CERTIFICATE_POSITIVE_ANNEX";

    public static final String COL_ROLE = "ROLE";

    public static final String COL_PLACE_BIRTH = "PLACE_BIRTH";

    public static final String COL_ADDRESS = "ADDRESS";

    public static final String COL_JOIN_DATE = "JOIN_DATE";

    public static final String COL_TIME_ZONE = "TIME_ZONE";

    public static final String COL_CREATED_BY = "CREATED_BY";

    public static final String COL_CREATED_TIME = "CREATED_TIME";

    public static final String COL_UPDATED_BY = "UPDATED_BY";

    public static final String COL_UPDATED_TIME = "UPDATED_TIME";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}