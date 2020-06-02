package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import lombok.Value;

/**
 * @ClassName:EmployeeListByDeptIdEnter
 * @description: EmployeeListByDeptIdEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/28 11:51
 */
@ApiModel(value = "员工列表", description = "员工列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class EmployeeListByDeptIdEnter extends GeneralEnter {

    @ApiModelProperty(value = "部门Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
