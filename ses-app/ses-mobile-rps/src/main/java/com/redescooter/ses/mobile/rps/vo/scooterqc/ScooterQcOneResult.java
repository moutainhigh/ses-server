package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "整车质检列表具体出参", description = "整车质检列表具体部件出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcOneResult extends GeneralResult {

    @ApiModelProperty(value = "组装单id")
    private Long id;

    @ApiModelProperty(value = "组装单号")
    private String scooterNum;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "质检时间")
    private Date checkTime;

    @ApiModelProperty(value = "待质检总数")
    private Integer waitQcNum;
}
