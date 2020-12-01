package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameDeptIdEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/8 14:51
 * @Version V1.0
 **/
@Data
public class DeptIdEnter extends GeneralEnter {

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("类型，1：查询全部的，2:查询未禁用的")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer type;


}
