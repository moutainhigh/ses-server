package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameStaffListResult
 * @Description
 * @Author Aleks
 * @Date2020/9/2 11:34
 * @Version V1.0
 **/
@Data
public class StaffListResult extends GeneralResult {

    @ApiModelProperty("员工id")
    private Long id;

    @ApiModelProperty(value = "员工姓")
    private String firstName;

    @ApiModelProperty(value = "员工名")
    private String lastName;

    @ApiModelProperty(value = "员工全名")
    private String fullName;

    @ApiModelProperty(value = "员工工号")
    private String code;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "所属岗位名称")
    private String positionName;

    @ApiModelProperty(value = "所属角色名称")
    private String roleNames;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "邮箱账号")
    private String email;

    @ApiModelProperty(value = "员工状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty("是否开通过账号，0：未开通，1：已开通")
    private String openAccount = "0";

}
