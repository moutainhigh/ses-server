package com.redescooter.ses.api.mobile.b.vo.express;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "快递统计图表", description = "快递统计图表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class EdMobileBExpressOrderChartResult extends GeneralResult {
    @ApiModelProperty(value = "非零统计")
    private Map<String, EdMonthlyExpressOrderChartResult> listMap = new HashMap<>();
    @ApiModelProperty(value = "全部统计")
    private Map<String, EdMonthlyExpressOrderChartResult> allMap = new HashMap<>();
}
