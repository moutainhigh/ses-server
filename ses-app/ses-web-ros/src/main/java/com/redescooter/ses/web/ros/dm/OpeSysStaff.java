package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户员工表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysStaff")
@Data
@TableName(value = "ope_sys_staff")
public class OpeSysStaff implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 员工状态 1：正常，2：禁用
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "员工状态 1：正常，2：禁用")
    private Integer status;

    /**
     * 所属部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    /**
     * 所属岗位id
     */
    @TableField(value = "position_id")
    @ApiModelProperty(value = "所属岗位id")
    private Long positionId;

    /**
     * 所属角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "所属角色id")
    private Long roleId;

    /**
     * 联系电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    /**
     * 邮箱账号
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱账号")
    private String email;

    /**
     * 入职日期
     */
    @TableField(value = "entry_date")
    @ApiModelProperty(value = "入职日期")
    private Date entryDate;

    /**
     * 国籍
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value = "国籍")
    private String countryName;

    /**
     * 地址1
     */
    @TableField(value = "address_1")
    @ApiModelProperty(value = "地址1")
    private String address1;

    /**
     * 地址2
     */
    @TableField(value = "address_2")
    @ApiModelProperty(value = "地址2")
    private String address2;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 证件类型 1：身份证，2：驾驶证，3：护照
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型 1：身份证，2：驾驶证，3：护照")
    private Integer certificateType;

    /**
     * 图片1（身份证正面或者驾驶证或者护照）
     */
    @TableField(value = "certificat_picture_1")
    @ApiModelProperty(value = "图片1（身份证正面或者驾驶证或者护照）")
    private String certificatPicture1;

    /**
     * 图片2（身份证反面）
     */
    @TableField(value = "certificat_picture_2")
    @ApiModelProperty(value = "图片2（身份证反面）")
    private String certificatPicture2;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 员工头像
     */
    @TableField(value = "employee_picture")
    @ApiModelProperty(value = "员工头像")
    private String employeePicture;

    /**
     * 员工姓
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value = "员工姓")
    private String firstName;

    /**
     * 员工名
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "员工名")
    private String lastName;

    /**
     * 员工全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value = "员工全名")
    private String fullName;

    /**
     * 性别 1：男，2：女
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 1：男，2：女")
    private Integer gender;

    /**
     * 用户ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value = "用户ID")
    private Long sysUserId;

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

    public static final String COL_STATUS = "status";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_POSITION_ID = "position_id";

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_EMAIL = "email";

    public static final String COL_ENTRY_DATE = "entry_date";

    public static final String COL_COUNTRY_NAME = "country_name";

    public static final String COL_ADDRESS_1 = "address_1";

    public static final String COL_ADDRESS_2 = "address_2";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_CERTIFICATE_TYPE = "certificate_type";

    public static final String COL_CERTIFICAT_PICTURE_1 = "certificat_picture_1";

    public static final String COL_CERTIFICAT_PICTURE_2 = "certificat_picture_2";

    public static final String COL_REMARK = "remark";

    public static final String COL_EMPLOYEE_PICTURE = "employee_picture";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_GENDER = "gender";

    public static final String COL_SYS_USER_ID = "sys_user_id";

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