package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameDeptTypeResult
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:47
 * @Version V1.0
 **/

@ApiModel(value = "部门类型")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class DeptTypeResult extends GeneralResult {
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty(value = "部门id", required = true)
    private long deptId;
}
