package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SaveDeptEnter
 * @Author Jerry
 * @date 2020/03/11 20:09
 * @Description:
 */
@ApiModel(value = "部门创建")
@Data
public class EditDeptEnter extends SaveDeptEnter {

    @ApiModelProperty(value = "部门主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "父级部门id", required = true)
    private Long pId;

    @ApiModelProperty(value = "负责人", required = true)
    private Integer principal;

    @ApiModelProperty(value = "级别0公司，1部门", required = true)
    private Integer level;

    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
