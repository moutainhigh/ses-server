package com.redescooter.ses.web.ros.vo.employee;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:EmployeeListEnter
 * @description: EmployeeListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 10:13
 */
@ApiModel(value = "员工列表入参", description = "员工列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmployeeListEnter extends PageEnter {

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "职位Id")
    private Long positionId;

    @ApiModelProperty(value = "办公区域Id")
    private Long addressBureauId;

    @ApiModelProperty(value = "加入开始时间")
    private Date entryStartDate;

    @ApiModelProperty(value = "加入结束时间")
    private Date entryEndDate;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
