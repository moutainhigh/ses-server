package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@ApiModel(value = "整车质检列表具体出参", description = "整车质检列表具体部件出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcOneResult extends GeneralResult {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "组装单号")
    private String scooterNum;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "质检时间")
    private Date checkTime;

    @ApiModelProperty(value = "待质检总数")
    private Integer waitInWHName;
}
