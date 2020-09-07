package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameStaffResult
 * @Description
 * @Author Aleks
 * @Date2020/9/1 20:04
 * @Version V1.0
 **/
@Data
public class StaffResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "员工状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "所属部门id")
    private String deptId;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "所属岗位id")
    private String positionId;

    @ApiModelProperty(value = "所属岗位名称")
    private String positionName;

    @ApiModelProperty(value = "所属角色名称")
    private String roleName;

    @ApiModelProperty(value = "所属角色id")
    private String roleId;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "邮箱账号")
    private String email;

    @ApiModelProperty(value = "入职日期")
    private Date entryDate;

    @ApiModelProperty(value = "国籍")
    private String countryName;

    @ApiModelProperty(value = "地址1")
    private String address1;

    @ApiModelProperty(value = "地址2")
    private String address2;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "证件类型 1：身份证，2：驾驶证，3：护照")
    private Integer certificateType;

    @ApiModelProperty(value = "图片1（身份证正面或者驾驶证或者护照）")
    private String certificatPicture1;

    @ApiModelProperty(value = "图片2（身份证反面）")
    private String certificatPicture2;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "员工头像")
    private String employeePicture;

    @ApiModelProperty(value = "员工姓")
    private String firstName;

    @ApiModelProperty(value = "员工名")
    private String lastName;

    @ApiModelProperty(value = "员工全名")
    private String fullName;

    @ApiModelProperty(value = "员工工号")
    private String code;

    @ApiModelProperty(value = "性别 1：男，2：女")
    private Integer gender;

    @ApiModelProperty(value = "用户ID")
    private Long sysUserId;

    @ApiModelProperty(value = "创建人")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private String updatedName;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty("是否开通过账号，0：未开通，1：已开通")
    private String openAccount = "0";

}
