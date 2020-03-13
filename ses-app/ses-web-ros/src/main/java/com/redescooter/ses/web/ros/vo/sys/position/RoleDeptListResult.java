package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:PositionResult
 * @description: PositionResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 16:43
 */
@ApiModel(value = "职位部门列表", description = "职位部门列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoleDeptListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "职位列表")
    private List<RoleResult> positionListResult;
}
