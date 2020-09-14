package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamedeptEnter
 * @Description
 * @Author Joan
 * @Date2020/9/2 10:35
 * @Version V1.0
 **/
@ApiModel(value = "部门创建111")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AddDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(code = ValidationExceptionCode.DEPT_NAME_IS_EMPTY, message = "部门名字为空")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String name;

    @ApiModelProperty(value = "父级部门id", required = true)
    @NotNull(code = ValidationExceptionCode.PID_IS_EMPTY, message = "父级Id为空")
    private Long pid;

    @ApiModelProperty(value = "负责人", required = true)
    private Long principal;

    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    @NotNull(code = ValidationExceptionCode.DEPT_STATUS_IS_EMPTY, message = "部门状态为空")
    private Integer deptStatus;

    @ApiModelProperty(value = "排序")
    @Regexp(value = RegexpConstant.positiveInteger,code = ValidationExceptionCode.SORT_ILLEGAL,message = "排序值必须为正整数，请重新输入")
    private Integer sort=1;

}
