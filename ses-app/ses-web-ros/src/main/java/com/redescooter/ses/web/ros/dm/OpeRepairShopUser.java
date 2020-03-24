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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRepairShopUser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_repair_shop_user")
public class OpeRepairShopUser implements Serializable {
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
     * 维修店ID
     */
    @TableField(value = "repair_shop_id")
    @ApiModelProperty(value = "维修店ID")
    private Long repairShopId;

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
     * 电话号
     */
    @TableField(value = "tel_number")
    @ApiModelProperty(value = "电话号")
    private String telNumber;

    /**
     * 性别：0、 Male-男；1、Female-女
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别：0、 Male-男；1、Female-女")
    private Integer gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 身份证号
     */
    @TableField(value = "identity")
    @ApiModelProperty(value = "身份证号")
    private String identity;

    /**
     * 是否主账户
     */
    @TableField(value = "master")
    @ApiModelProperty(value = "是否主账户")
    private Boolean master;

    /**
     * 登录名
     */
    @TableField(value = "login_name")
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value = "盐")
    private String salt;

    /**
     * 状态 Normal,LEAVE,Lock,Cancel，Expired
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 Normal,LEAVE,Lock,Cancel，Expired")
    private String status;

    /**
     * 员工地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "员工地址")
    private String address;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    /**
     * 最后登录TOKEN
     */
    @TableField(value = "last_login_token")
    @ApiModelProperty(value = "最后登录TOKEN")
    private String lastLoginToken;

    /**
     * 最后登录IP地址
     */
    @TableField(value = "last_login_ip")
    @ApiModelProperty(value = "最后登录IP地址")
    private String lastLoginIp;

    /**
     * 账户激活时间
     */
    @TableField(value = "activation_time")
    @ApiModelProperty(value = "账户激活时间")
    private Date activationTime;

    /**
     * 账户到期时间
     */
    @TableField(value = "expire_date")
    @ApiModelProperty(value = "账户到期时间")
    private Date expireDate;

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

    public static final String COL_REPAIR_SHOP_ID = "repair_shop_id";

    public static final String COL_PICTURE = "picture";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_EMAIL = "email";

    public static final String COL_TEL_NUMBER = "tel_number";

    public static final String COL_GENDER = "gender";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_IDENTITY = "identity";

    public static final String COL_MASTER = "master";

    public static final String COL_LOGIN_NAME = "login_name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SALT = "salt";

    public static final String COL_STATUS = "status";

    public static final String COL_ADDRESS = "address";

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
}