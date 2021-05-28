package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimDailyStatisticsResult
 * @Description SimDailyStatistics /daily_statistics
 * @Author Charles
 * @Date 2021/05/27 14:10
 */
@Data
@Builder
@ApiModel("SimDailyStatistics")
@NoArgsConstructor
@AllArgsConstructor
public class SimDailyStatisticsResult {

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty(value = "使用kb", hidden = true)
    private String usage_kb;

    @ApiModelProperty("使用mb")
    private Double usageMb;

}
