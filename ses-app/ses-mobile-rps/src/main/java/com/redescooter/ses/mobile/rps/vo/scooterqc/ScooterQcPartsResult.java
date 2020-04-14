package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

import io.swagger.annotations.*;

import java.util.List;

@ApiModel(value = "部件列表具体部件出参", description = "部件列表具体部件出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcPartsResult extends GeneralResult {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "具体部件集合")
    private List<ScooterQcPartResult> scooterQcPartsResultList;


}
