package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "质检产品剩余数量出参", description = "质检产品剩余数量出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcResidueNumResult extends GeneralResult {

    @ApiModelProperty(value = "质检成功/失败")
    private Boolean result;

    @ApiModelProperty(value = "整车质检剩余产品数量")
    private Integer residueNum;
}
