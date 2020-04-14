package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(value = "整车质检具体选项出参", description = "整车质检具体选项出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemResult extends GeneralResult {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "组装单号")
    private String scooterNum;

    @ApiModelProperty(value = "部件名称")
    private String partName;

    @ApiModelProperty(value = "质检项")
    private List<ScooterQcItemOptionsResult> scooterQcItemOptionsResultList;


}
