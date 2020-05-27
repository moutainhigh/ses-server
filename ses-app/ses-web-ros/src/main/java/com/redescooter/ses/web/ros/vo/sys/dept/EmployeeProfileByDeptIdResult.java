package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

import io.swagger.annotations.*;

/**
 * @ClassName:EmployeeProfileResult
 * @description: EmployeeProfileResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/27 13:35
 */
@ApiModel(value = "员工信息", description = "员工信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class EmployeeProfileByDeptIdResult extends GeneralResult {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "员工姓名")
    private String employeeFirstName;

    @ApiModelProperty(value = "员工姓名")
    private String employeeLastName;

    @ApiModelProperty(value = "员工头像")
    private String employeePicture;

    @ApiModelProperty(value = "职位Id")
    private String employeePositionId;

    @ApiModelProperty(value = "职位名称")
    private String employeePositionName;
}
