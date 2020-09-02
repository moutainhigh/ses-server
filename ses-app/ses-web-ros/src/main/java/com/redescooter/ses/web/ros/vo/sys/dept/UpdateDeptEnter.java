package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassNameUpdateDeptEnter
 * @Description
 * @Author Joan
 * @Date2020/9/2 10:38
 * @Version V1.0
 **/
public class UpdateDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "部门主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.DEPT_NAME_IS_EMPTY, message = "部门名字为空")
    @Regexp(value = RegexpConstant.name,code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String name;

    @ApiModelProperty(value = "父级部门id", required = true)
    @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.PID_IS_EMPTY, message = "父级Id为空")
    private Long pId;

    @ApiModelProperty(value = "负责人", required = true)
    private Long principal;

    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.DEPT_STATUS_IS_EMPTY, message = "部门状态为空")
    private Integer deptStatus;

    @ApiModelProperty(value = "排序")
    @Regexp(value = RegexpConstant.positiveInteger,code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.SORT_ILLEGAL,message = "排序值必须为正整数，请重新输入")
    private Integer sort=1;
}
