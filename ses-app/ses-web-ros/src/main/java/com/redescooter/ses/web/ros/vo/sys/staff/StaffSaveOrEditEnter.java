package com.redescooter.ses.web.ros.vo.sys.staff;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameStaffSaveOrEditEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/1 19:35
 * @Version V1.0
 **/
@Data
public class StaffSaveOrEditEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    private Long id;

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
    private String roleId;

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
    private String entryDate;

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
    private String birthday;

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

    @ApiModelProperty(value = "国家编码如")
    private String countryCode;

    @ApiModelProperty(value = "城市图标名称如中国")
    private String countryCodeName;

    @ApiModelProperty(value = "是否开启安全码（0：否，1：是）")
    private Integer ifSafeCode;

}
