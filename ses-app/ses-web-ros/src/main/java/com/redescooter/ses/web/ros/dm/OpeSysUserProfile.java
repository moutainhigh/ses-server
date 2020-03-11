package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysUserProfile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_user_profile")
public class OpeSysUserProfile implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 所属租户
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "所属租户")
    private Long tenantId;

    /**
     * 维修店ID
     */
    @TableField(value = "repair_shop_Id")
    @ApiModelProperty(value = "维修店ID")
    private Long repairShopId;

    /**
     * 用户ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value = "用户ID")
    private Long sysUserId;

    /**
     * 照片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "照片")
    private String picture;

    /**
     * 名
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value = "全名")
    private String fullName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 电话号
     */
    @TableField(value = "tel_number")
    @ApiModelProperty(value = "电话号")
    private String telNumber;

    /**
     * 性别 Male 男 Female 女
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 Male 男 Female 女")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 出生地
     */
    @TableField(value = "place_birth")
    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    /**
     * 地址国家编码，如手机号 中国 +86
     */
    @TableField(value = "address_country_code")
    @ApiModelProperty(value = "地址国家编码，如手机号 中国 +86")
    private String addressCountryCode;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件编号
     */
    @TableField(value = "certificate_number")
    @ApiModelProperty(value = "证件编号")
    private String certificateNumber;

    /**
     * 证件反面图片
     */
    @TableField(value = "certificate_negative_annex")
    @ApiModelProperty(value = "证件反面图片")
    private String certificateNegativeAnnex;

    /**
     * 证件正面图片
     */
    @TableField(value = "certificate_positive_annex")
    @ApiModelProperty(value = "证件正面图片")
    private String certificatePositiveAnnex;

    /**
     * 加入日期
     */
    @TableField(value = "join_date")
    @ApiModelProperty(value = "加入日期")
    private Date joinDate;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_REPAIR_SHOP_ID = "repair_shop_Id";

    public static final String COL_SYS_USER_ID = "sys_user_id";

    public static final String COL_PICTURE = "picture";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_EMAIL = "email";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_TEL_NUMBER = "tel_number";

    public static final String COL_GENDER = "gender";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_PLACE_BIRTH = "place_birth";

    public static final String COL_ADDRESS_COUNTRY_CODE = "address_country_code";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CERTIFICATE_TYPE = "certificate_type";

    public static final String COL_CERTIFICATE_NUMBER = "certificate_number";

    public static final String COL_CERTIFICATE_NEGATIVE_ANNEX = "certificate_negative_annex";

    public static final String COL_CERTIFICATE_POSITIVE_ANNEX = "certificate_positive_annex";

    public static final String COL_JOIN_DATE = "join_date";

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