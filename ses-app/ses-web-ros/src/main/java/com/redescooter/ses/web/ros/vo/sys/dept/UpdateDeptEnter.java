package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameUpdateDeptEnter
 * @Description
 * @Author Joan
 * @Date2020/9/2 10:38
 * @Version V1.0
 **/
@ApiModel(value = "查询编辑部门", description = "查询编辑部门")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class UpdateDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "部门主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "部门名称", required = true)
     private String name;

    @ApiModelProperty(value = "父级部门id", required = true)
    private Long pid;

    @ApiModelProperty(value = "负责人", required = true)
    private Long principal;

    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    private Integer deptStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort=1;
}
