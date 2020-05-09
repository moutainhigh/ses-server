package com.redescooter.ses.api.mobile.b.vo.express;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(value = "快递统计图表", description = "快递统计图表")

@Data
public class EdMonthlyExpressOrderChartResult extends GeneralResult {

    @ApiModelProperty(value = "完成总数")
    private Integer completed=0;

    @ApiModelProperty(value = "拒绝总数")
    private Integer refuse=0;

    @ApiModelProperty(value = "总数")
    private Integer total=0;

    @ApiModelProperty(value = "日期")
    private String times;
}
