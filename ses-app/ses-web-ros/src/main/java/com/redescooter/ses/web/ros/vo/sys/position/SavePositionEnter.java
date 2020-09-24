package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameSavePositionEnter
 * @Description
 * @Author Joan
 * @Date2020/9/3 17:20
 * @Version V1.0
 **/
@ApiModel(value = "岗位新增", description = "岗位新增")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SavePositionEnter extends GeneralEnter {

    @ApiModelProperty(value = "岗位名称")
    @NotNull(code = ValidationExceptionCode.ROLE_NAME_IS_EMPTY, message = "部门名字为空")
    private String positionName;

    @ApiModelProperty(value = "所属部门id")
    @NotNull(code = ValidationExceptionCode.PLEASE_SELECT_DEPARTMENT, message = "请选择所属部门")
    private Long deptId;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

}
