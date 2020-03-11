package com.redescooter.ses.web.ros.vo.organization.employee;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:EmployeeDeptResult
 * @description: EmployeeDeptResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 16:11
 */
@ApiModel(value = "保存员工部门列表", description = "保存员工部门列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmployeeDeptResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;
}
