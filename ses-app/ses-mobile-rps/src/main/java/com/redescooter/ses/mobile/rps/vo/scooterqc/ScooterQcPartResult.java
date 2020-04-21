package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;



@ApiModel(value = "部件列表具体部件出参", description = "部件列表具体部件出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcPartResult extends GeneralResult {

    @ApiModelProperty(value = "部件名称")
    private String partName;

    @ApiModelProperty(value = "组装单子表id")
    private Long scooterBId;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "部件编号")
    private String partStr;

    @ApiModelProperty(value = "部件数量")
    private Integer partNum;
}
