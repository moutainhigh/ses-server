package com.redescooter.ses.web.ros.vo.sys.employee;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @ClassName:EmployeeListResult
 * @description: EmployeeListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 11:56
 */
@ApiModel(value = "员工列表", description = "员工列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeptEmployeeListResult extends GeneralResult {
    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "统计")
    private Integer totalCount;

    @ApiModelProperty(value = "员工")
    private List<EmployeeResult> employeeResult;
}
