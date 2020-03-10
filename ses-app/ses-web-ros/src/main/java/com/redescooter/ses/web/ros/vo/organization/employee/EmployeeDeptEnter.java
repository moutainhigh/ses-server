package com.redescooter.ses.web.ros.vo.organization.employee;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:EmployeeDeptEnter
 * @description: EmployeeDeptEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 11:13
 */
@ApiModel(value = "员工部门列表", description = "员工部门列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmployeeDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "类型，根据类型 查 部门、职位、办公区域、职位列表、公司列表")
    private String type;

    @ApiModelProperty(value = "业务Id，需要进行联动的时候传上一级Id")
    private Long bizId;
}
