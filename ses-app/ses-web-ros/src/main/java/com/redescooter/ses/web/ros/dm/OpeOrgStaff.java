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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeOrgStaff")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_org_staff")
public class OpeOrgStaff implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 部门ID
     */
    @TableField(value = "org_department_id")
    @ApiModelProperty(value = "部门ID")
    private Long orgDepartmentId;

    /**
     * 主要岗位Id
     */
    @TableField(value = "org_position_id")
    @ApiModelProperty(value = "主要岗位Id")
    private Long orgPositionId;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 名字
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value = "名字")
    private String firstName;

    /**
     * 姓氏
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "姓氏")
    private String lastName;

    /**
     * 状态：In-service在职，Leave离职
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态：In-service在职，Leave离职")
    private String status;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private String birthday;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 1代表女的，0代表男的
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "1代表女的，0代表男的")
    private Integer gender;

    /**
     * 证件类型ID_CARD 身份证 Passport 护照  Driver_License 驾驶证
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型ID_CARD 身份证 Passport 护照  Driver_License 驾驶证,")
    private String certificateType;

    /**
     * 身份证号
     */
    @TableField(value = "certificate_number")
    @ApiModelProperty(value = "身份证号")
    private String certificateNumber;

    /**
     * 身份证图片 正面 反面
     */
    @TableField(value = "certificate_picture")
    @ApiModelProperty(value = "身份证图片 正面 反面")
    private String certificatePicture;

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

    public static final String COL_ORG_DEPARTMENT_ID = "org_department_id";

    public static final String COL_ORG_POSITION_ID = "org_position_id";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_STATUS = "status";

    public static final String COL_PHONE = "phone";

    public static final String COL_EMAIL = "email";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_ADDRESS = "address";

    public static final String COL_GENDER = "gender";

    public static final String COL_CERTIFICATE_TYPE = "certificate_type";

    public static final String COL_CERTIFICATE_NUMBER = "certificate_number";

    public static final String COL_CERTIFICATE_PICTURE = "certificate_picture";

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