package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameSelectDeptResult
 * @Description
 * @Author Joan
 * @Date2020/9/2 12:03
 * @Version V1.0
 **/
@ApiModel(value = "查询编辑部门", description = "查询编辑部门")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SelectDeptResult extends GeneralResult {
    @ApiModelProperty(value = "当前节点id")
    protected long id;
    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(code = ValidationExceptionCode.DEPT_NAME_IS_EMPTY, message = "部门名字为空")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String name;

    @ApiModelProperty(value = "父级部门id", required = true)
    @NotNull(code = ValidationExceptionCode.PID_IS_EMPTY, message = "父级Id为空")
    private Long pId;

    @ApiModelProperty(value = "负责人", required = true)
    private Long principal;

    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    @NotNull(code = ValidationExceptionCode.DEPT_STATUS_IS_EMPTY, message = "部门状态为空")
    private Integer deptStatus;

    @ApiModelProperty(value = "排序")
    @Regexp(value = RegexpConstant.positiveInteger,code = ValidationExceptionCode.SORT_ILLEGAL,message = "排序值必须为正整数，请重新输入")
    private Integer sort=1;
}
