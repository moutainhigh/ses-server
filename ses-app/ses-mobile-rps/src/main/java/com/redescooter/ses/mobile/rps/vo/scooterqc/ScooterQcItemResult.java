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

    @ApiModelProperty(value = "组装单子表id")
    private Long id;

    @ApiModelProperty(value = "质检模板id")
    private Long qcTemplateId;

    @ApiModelProperty(value = "质检项名称")
    private String qcName;

    @ApiModelProperty(value = "质检详情集合")
    private List<ScooterQcItemOptionResult> scooterQcItemOptionResultList;



}
