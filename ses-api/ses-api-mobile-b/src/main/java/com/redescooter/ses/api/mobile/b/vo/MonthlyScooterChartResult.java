package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description: MonthlyOrderResult
 * @author: Alex
 * @create: 2019/06/25 15:03
 */
@ApiModel(value = "scooter统计出参", description = "scooter统计出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MonthlyScooterChartResult extends GeneralResult {

    @ApiModelProperty(value = "公里数")
    private String mileage = "0";

    @ApiModelProperty(value = "平均时速")
    private String avgSpeed = "0";

    @ApiModelProperty(value = "co2")
    private String co2 = "0";

    @ApiModelProperty(value = "money")
    private String money = "0";

    @ApiModelProperty(value = "日期")
    private String times;
}
