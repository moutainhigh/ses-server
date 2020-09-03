package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamePositionTypeResult
 * @Description
 * @Author Joan
 * @Date2020/9/3 13:25
 * @Version V1.0
 **/
@ApiModel(value = "岗位类型", description = "岗位列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionTypeResult extends GeneralResult {
    @ApiModelProperty(value = "id", required = true)
    private long positionId;

    @ApiModelProperty(value = "岗位名称", required = true)
    private String positionName;
}
