package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName SaveDeptEnter
 * @Author Jerry
 * @date 2020/03/11 20:09
 * @Description:
 */
@ApiModel(value = "部门创建")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "父级部门id", required = true)
    @NotNull(code = ValidationExceptionCode.PID_IS_EMPTY, message = "父级Id为空")
    private Long pId;

    @ApiModelProperty(value = "负责人", required = true)
    @NotNull(code = ValidationExceptionCode.PRINCIPAL_ID_IS_EXIST, message = "负责人为空")
    private Long principal;

    @ApiModelProperty(value = "级别0公司，1部门", required = true)
    @NotNull(code = ValidationExceptionCode.DEPT_LEVEL_IS_EMPTY, message = "部门层级为空")
    private Integer level;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(code = ValidationExceptionCode.DEPT_NAME_IS_EMPTY, message = "部门名字为空")
    //@Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
