package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameEditPositionEnter
 * @Description
 * @Author Joan
 * @Date2020/9/3 18:44
 * @Version V1.0
 **/
@ApiModel(value = "岗位新增", description = "岗位新增")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EditPositionEnter extends GeneralEnter {
    @ApiModelProperty(value = "岗位Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    private String positionName;

    @ApiModelProperty(value = "所属部门id")
    @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.PLEASE_SELECT_DEPARTMENT, message = "请选择所属部门")
    private Long deptId;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

}
