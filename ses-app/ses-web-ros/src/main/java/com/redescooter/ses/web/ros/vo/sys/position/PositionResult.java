package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamePositionResult
 * @Description
 * @Author Joan
 * @Date2020/9/3 13:17
 * @Version V1.0
 **/
@ApiModel(value = "岗位列表", description = "岗位列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionResult extends GeneralResult {
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty(value = "部门id", required = true)
    private long id;

}
