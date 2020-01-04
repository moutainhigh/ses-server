package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @ClassName:MobileBScooterChartResult
 * @description: MobileBScooterChartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 18:44
 */
@ApiModel(value = "车辆图表统计出参", description = "车辆图表统计出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class MobileBScooterChartResult extends GeneralResult {

    @ApiModelProperty(value = "总公里数")
    private String totalMileage = "0";

    @ApiModelProperty(value = "总co2")
    private String totalCo2 = "0";

    @ApiModelProperty(value = "总节省钱")
    private String totalMoney = "0";

    @ApiModelProperty(value = "平均速度")
    private String avgSpeed = "0";

    @ApiModelProperty(value = "图表数据")
    Map<String, MonthlyScooterChartResult> monthlyScooterResults;
}
