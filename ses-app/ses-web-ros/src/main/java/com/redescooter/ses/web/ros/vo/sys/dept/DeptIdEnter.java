package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
}
